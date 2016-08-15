package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cocosw.bottomsheet.BottomSheet;
import com.squareup.otto.Subscribe;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import butterknife.BindDimen;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import retrofit2.adapter.rxjava.HttpException;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.admin.PublicAnnouncementsActivity;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.BannerAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.NewsAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.dao.CurrentUser;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.BannerFragment;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.ChangePasswordDialogFragment;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.DisasterMgtMenuDialogFragment;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.MayorMessageDialogFragment;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.SanMateoBannerFragment;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiErrorHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiRequestHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppBarStateListener;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.DaoHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.PicassoHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.PrefsHelper;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnConfirmDialogListener;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnS3UploadListener;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.ApiError;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.GenericMessage;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.News;
import sanmateo.avinnovz.com.sanmateoprofile.services.PusherService;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.CurrentUserSingleton;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.NewsSingleton;

/**
 * Created by rsbulanon on 7/12/16.
 */
public class NewHomeActivity extends BaseActivity implements OnApiRequestListener, OnS3UploadListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.navigationView) NavigationView navigationView;
    @BindView(R.id.viewPager) AutoScrollViewPager viewPager;
    @BindView(R.id.drawerLayout) DrawerLayout drawerLayout;
    @BindView(R.id.rvHomeMenu) RecyclerView rvHomeMenu;
    @BindView(R.id.appBarLayout) AppBarLayout appBarLayout;
    @BindView(R.id.tvNotification) TextView tvNotification;
    @BindView(R.id.llHeader) LinearLayout llHeader;
    @BindString(R.string.disaster_mgmt) String headerDisasterManagement;
    @BindString(R.string.message_alert_notifications) String headerAlertNotifications;
    @BindDimen(R.dimen._90sdp) int profilePicSize;
    private ImageView ivProfileImage;
    private ProgressBar pbLoadImage;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private CurrentUserSingleton currentUserSingleton;
    private NewsSingleton newsSingleton;
    private ApiRequestHelper apiRequestHelper;
    private String token;
    private boolean loading = true;
    private int pastVisibleItems;
    private int visibleItemCount;
    private int totalItemCount;
    private static final int SELECT_IMAGE = 1;
    private static final int CAPTURE_IMAGE = 2;
    private Uri fileUri;
    private File fileToUpload;
    private String uploadToBucket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initPanicContact();
        initAmazonS3Helper(this);
        currentUserSingleton = CurrentUserSingleton.newInstance();
        newsSingleton = NewsSingleton.getInstance();
        apiRequestHelper = new ApiRequestHelper(this);
        token = currentUserSingleton.getCurrentUser().getToken();

        animateBanners();
        initNavigationDrawer();
        initNews();

        if (!isMyServiceRunning(PusherService.class)) {
            startService(new Intent(this, PusherService.class));
        }

        if (newsSingleton.getNewsPrevious().size() == 0) {
            apiRequestHelper.getNews(token,0,10,"active",null);
        }

        initAppBarLayoutListener();

        /** display notification if there are any */
        if (PrefsHelper.getBoolean(this,"has_notifications")) {
            tvNotification.setVisibility(View.VISIBLE);
        }
    }

    private void animateBanners() {
        final ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(SanMateoBannerFragment.newInstance());
        fragments.add(SanMateoBannerFragment.newInstance());
        fragments.add(BannerFragment.newInstance(ContextCompat.getDrawable(this,R.drawable.image_1)));
        fragments.add(BannerFragment.newInstance(ContextCompat.getDrawable(this,R.drawable.image_2)));
        fragments.add(BannerFragment.newInstance(ContextCompat.getDrawable(this,R.drawable.image_3)));
        viewPager.setAdapter(new BannerAdapter(getSupportFragmentManager(), fragments));
        viewPager.setOffscreenPageLimit(fragments.size());
        viewPager.setInterval(2000);
        viewPager.startAutoScroll();
        viewPager.setScrollDurationFactor(10);
    }

    public void initNavigationDrawer() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.open_drawer,R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        initSideDrawerMenu();
    }

    private void initSideDrawerMenu() {
        final View view = getLayoutInflater().inflate(R.layout.navigation_header,null,false);
        ivProfileImage = (ImageView)view.findViewById(R.id.ivProfileImage);
        pbLoadImage = (ProgressBar)view.findViewById(R.id.pbLoadImage);
        final TextView tvProfileName = (TextView)view.findViewById(R.id.tvProfileName);

        final int screenHeight = getScreenDimension("height");
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                ((int)(screenHeight * .4)));

        ivProfileImage.setOnClickListener(view1 -> showChangeProfilePicMenu());

        navigationView.addHeaderView(view);
        navigationView.inflateMenu(R.menu.menu_side_drawer);
        navigationView.getHeaderView(0).setLayoutParams(params);

        PicassoHelper.loadImageFromURL(currentUserSingleton.getCurrentUser().getPicUrl(),
                profilePicSize, Color.TRANSPARENT,ivProfileImage,pbLoadImage);

        tvProfileName.setText(currentUserSingleton.getCurrentUser().getFirstName() + " " +
                currentUserSingleton.getCurrentUser().getLastName());

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_panic_emergency:
                        setPanicContacts();
                        break;
                    case R.id.menu_incident_report:
                        moveToOtherActivity(IncidentsActivity.class);
                        break;
                    case R.id.menu_information:
                        moveToOtherActivity(InformationActivity.class);
                        break;
                    case R.id.menu_map:
                        moveToOtherActivity(MapActivity.class);
                        break;
                    case R.id.menu_directories:
                        moveToOtherActivity(DirectoriesActivity.class);
                        break;
                    case R.id.menu_gallery:
                        moveToOtherActivity(GalleryActivity.class);
                        break;
                    /*case R.id.menu_news_events:
                        moveToOtherAcitivity(NewsEventsManagementActivity.class);
                        break;*/
                    case R.id.menu_social_media:
                        showToast("social media");
                        break;
                    case R.id.menu_disaster_management:
                        final ArrayList<String> menu = new ArrayList<>();
                        menu.add("Public Announcements");
                        menu.add("Typhoon Watch");
                        menu.add("Water Level Monitoring");
                        menu.add("Global Disaster Monitoring");
                        menu.add("Emergency Numbers");
                        menu.add("Emergency Kit");
                        menu.add("How to CPR");
                        final DisasterMgtMenuDialogFragment fragment = DisasterMgtMenuDialogFragment
                                .newInstance(headerDisasterManagement, menu);
                        fragment.setOnSelectDisasterMenuListener(new DisasterMgtMenuDialogFragment.OnSelectDisasterMenuListener() {
                            @Override
                            public void onSelectedMenu(int position) {
                                if (position == 0) {
                                    moveToOtherActivity(PublicAnnouncementsActivity.class);
                                } else if (position == 1) {
                                    moveToOtherActivity(TyphoonWatchActivity.class);
                                } else if (position == 2) {
                                    moveToOtherActivity(WaterLevelMonitoringActivity.class);
                                } else if (position == 3) {
                                    moveToOtherActivity(GlobalDisasterActivity.class);
                                } else if (position == 4) {
                                    moveToOtherActivity(HotlinesActivity.class);
                                } else if (position == 5) {
                                    moveToOtherActivity(EmergencyKitActivity.class);
                                } else if (position == 6) {
                                    moveToOtherActivity(CprActivity.class);
                                }

                            }


                            @Override
                            public void onClose() {
                                fragment.dismiss();
                            }
                        });
                        fragment.show(getFragmentManager(), "disaster menu");
                        break;
                    case R.id.menu_contact_us:
                        showToast("contact us");
                        break;
                    case R.id.menu_change_pass:
                        changePassword();
                        break;
                    case R.id.menu_logout:
                        showConfirmDialog("", "Logout", "Are you sure you want to logout from the app?",
                                "Yes", "No", new OnConfirmDialogListener() {
                                    @Override
                                    public void onConfirmed(String action) {
                                        DaoHelper.deleteCurrentUser();
                                        startActivity(new Intent(NewHomeActivity.this, LoginActivity.class));
                                        finish();
                                        animateToRight(NewHomeActivity.this);
                                    }

                                    @Override
                                    public void onCancelled(String action) {

                                    }
                                });
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void changePassword() {
        ChangePasswordDialogFragment fragment = ChangePasswordDialogFragment.newInstance();
        fragment.setOnChangePasswordListener((oldPassword, newPassword) ->
                apiRequestHelper.changePassword(token,currentUserSingleton.getCurrentUser().getEmail(),
                oldPassword,newPassword));
        fragment.show(getFragmentManager(),"chane password");
    }

    private void initNews() {
        final NewsAdapter newsAdapter = new NewsAdapter(this, newsSingleton.getAllNews());
        newsAdapter.setOnSelectNewsListener(n -> {
            final Intent intent = new Intent(NewHomeActivity.this, NewsFullPreviewActivity.class);
            intent.putExtra("news",n);
            startActivity(intent);
            animateToLeft(NewHomeActivity.this);
        });
        rvHomeMenu.setAdapter(newsAdapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvHomeMenu.setLayoutManager(linearLayoutManager);
        rvHomeMenu.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            LogHelper.log("paginate","1111");
                            loading = false;
                            apiRequestHelper.getNews(token,newsSingleton.getAllNews().size(),
                                    10,"active",null);
                        } else {
                            LogHelper.log("paginate","2222");
                        }
                    }
                } else {
                    LogHelper.log("paginate","3333");
                }
                //int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                //swipeRefreshItems.setEnabled(topRowVerticalPosition >= 0);Ø
            }
        });
    }

    @Override
    public void onApiRequestBegin(String action) {
        if (action.equals(AppConstants.ACTION_GET_NEWS)) {
            showCustomProgress("Fetching news, Please wait...");
        } else if (action.equals(AppConstants.ACTION_PUT_CHANGE_PASSWORD)) {
            showCustomProgress("Changing password, Please wait...");
        } else if (action.equals(AppConstants.ACTION_PUT_CHANGE_PROFILE_PIC)) {
            showCustomProgress("Changing your profile pic, Please wait...");
        }
    }

    @Override
    public void onApiRequestSuccess(String action, Object result) {
        dismissCustomProgress();
        if (action.equals(AppConstants.ACTION_GET_NEWS)) {
            final ArrayList<News> news = (ArrayList<News>)result;
            newsSingleton.getAllNews().addAll(news);
        } else if (action.equals(AppConstants.ACTION_GET_NEWS_BY_ID)) {
            final News news = (News)result;
            newsSingleton.getAllNews().add(0,news);
        } else if (action.equals(AppConstants.ACTION_PUT_CHANGE_PASSWORD)) {
            final GenericMessage genericMessage = (GenericMessage)result;
            showToast(genericMessage.getMessage());
        } else if (action.equals(AppConstants.ACTION_PUT_CHANGE_PROFILE_PIC)) {
            final GenericMessage genericMessage = (GenericMessage)result;
            showToast("You have successfully changed your profile pic");
            /** save new profile pic url */
            final CurrentUser currentUser = currentUserSingleton.getCurrentUser();
            currentUser.setPicUrl(genericMessage.getMessage());
            DaoHelper.updateCurrentUser(currentUser);
            fileToUpload = null;
            fileUri = null;
            PicassoHelper.loadImageFromURL(currentUserSingleton.getCurrentUser().getPicUrl(),
                    profilePicSize, Color.TRANSPARENT,ivProfileImage,pbLoadImage);
        }

        if (!action.equals(AppConstants.ACTION_PUT_CHANGE_PASSWORD)) {
            rvHomeMenu.getAdapter().notifyDataSetChanged();
            rvHomeMenu.smoothScrollToPosition(0);
        }
    }

    @Override
    public void onApiRequestFailed(String action, Throwable t) {
        dismissCustomProgress();
        LogHelper.log("err","error in ---> " + action + " cause ---> " + t.getMessage());
        if (t instanceof HttpException) {
            final ApiError apiError = ApiErrorHelper.parseError(((HttpException) t).response());
            if (action.equals(AppConstants.ACTION_LOGIN)) {
                showConfirmDialog(action,"Login Failed", apiError.getMessage(),"Close","",null);
            } else if (action.equals(AppConstants.ACTION_PUT_CHANGE_PASSWORD)) {
                showConfirmDialog(action,"Change Password Failed", apiError.getMessage(),"Close","",null);
            }
        }
    }

    @Subscribe
    public void handleResponse(final HashMap<String,Object> map) {
        if (map.containsKey("data")) {
            try {
                final JSONObject json = new JSONObject(map.get("data").toString());
                if (json.has("action")) {
                    if (json.getString("action").equals("news created")) {
                        apiRequestHelper.getNewsById(token,json.getInt("id"));
                    } else if (json.getString("action").equals("announcements") ||
                            json.getString("action").equals("water level")) {
                        runOnUiThread(() -> {
                            if (!tvNotification.isShown()) {
                                tvNotification.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                }
            } catch (JSONException e) {

            }
        }
    }

    @OnClick(R.id.ivMayorImage)
    public void showMayorImage() {
        final MayorMessageDialogFragment fragment = MayorMessageDialogFragment.newInstance();
        fragment.show(getFragmentManager(),"mayor message");
    }

    private void initAppBarLayoutListener() {
        appBarLayout.addOnOffsetChangedListener(new AppBarStateListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state.name().equals("COLLAPSED")) {
                    llHeader.setVisibility(View.VISIBLE);
                } else {
                    if (llHeader.isShown()) {
                        llHeader.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
    }

    @OnClick(R.id.rlNotifications)
    public void showNotifications() {
        final ArrayList<String> menu = new ArrayList<>();
        menu.add("Public Announcements");
        menu.add("Water Level Monitoring");
        final DisasterMgtMenuDialogFragment fragment = DisasterMgtMenuDialogFragment
                .newInstance(headerAlertNotifications,menu);
        fragment.setOnSelectDisasterMenuListener(new DisasterMgtMenuDialogFragment.OnSelectDisasterMenuListener() {
            @Override
            public void onSelectedMenu(int position) {
                if (tvNotification.isShown()) {
                    tvNotification.setVisibility(View.INVISIBLE);
                    PrefsHelper.setBoolean(NewHomeActivity.this,"has_notifications",false);
                }
                fragment.dismiss();
                if (position == 0) {
                    moveToOtherActivity(PublicAnnouncementsActivity.class);
                } else {
                    moveToOtherActivity(WaterLevelMonitoringActivity.class);
                }
            }

            @Override
            public void onClose() {
                fragment.dismiss();
            }
        });
        fragment.show(getFragmentManager(),"show notifications");
    }

    private void showChangeProfilePicMenu() {
        uploadToBucket = AppConstants.BUCKET_PROFILE_PIC;
        new BottomSheet.Builder(this)
                .title("Change Profile Pic").sheet(R.menu.menu_upload_image)
                .listener((dialog, which) -> {
                    switch (which) {
                        case R.id.open_gallery:
                            final Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);//
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
                            break;
                        case R.id.open_camera:
                            final Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            try {
                                fileToUpload = createImageFile();
                                fileUri = Uri.fromFile(fileToUpload);
                                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                                startActivityForResult(cameraIntent, CAPTURE_IMAGE);
                            } catch (Exception ex) {
                                showConfirmDialog("","Capture Image",
                                        "We can't get your image. Please try again.","Close","",null);
                            }
                            break;
                    }
                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_IMAGE) {
                fileToUpload = getFile(data.getData(),UUID.randomUUID().toString()+".png");
            } else if (requestCode == CAPTURE_IMAGE) {
                fileToUpload = rotateBitmap(fileUri.getPath());
            }
            /** delete previous profile pic from s3 if it's not the default profile pic using gravatar */
            if (!currentUserSingleton.getCurrentUser().getPicUrl()
                    .contains("http://www.gravatar.com/avatar/")) {
                deleteImage(AppConstants.BUCKET_ROOT, currentUserSingleton.getCurrentUser().getPicUrl());
            }
            uploadImageToS3(uploadToBucket,fileToUpload);
        }
    }

    @Override
    public void onUploadFinished(String bucketName, String imageUrl) {
        if (bucketName.equals(AppConstants.BUCKET_PROFILE_PIC)) {
            /** upload new pic url */
            apiRequestHelper.changeProfilePic(token, currentUserSingleton
                    .getCurrentUser().getUserId(), imageUrl);
        }
        uploadToBucket = "";
    }
}
