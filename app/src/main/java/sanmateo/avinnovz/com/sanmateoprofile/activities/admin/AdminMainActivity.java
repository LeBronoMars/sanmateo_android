package sanmateo.avinnovz.com.sanmateoprofile.activities.admin;

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
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cocosw.bottomsheet.BottomSheet;
import com.squareup.picasso.Callback;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import butterknife.BindDimen;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import retrofit2.adapter.rxjava.HttpException;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.activities.DirectoriesActivity;
import sanmateo.avinnovz.com.sanmateoprofile.activities.GalleryActivity;
import sanmateo.avinnovz.com.sanmateoprofile.activities.GlobalDisasterActivity;
import sanmateo.avinnovz.com.sanmateoprofile.activities.HotlinesActivity;
import sanmateo.avinnovz.com.sanmateoprofile.activities.IncidentsActivity;
import sanmateo.avinnovz.com.sanmateoprofile.activities.InformationActivity;
import sanmateo.avinnovz.com.sanmateoprofile.activities.LoginActivity;
import sanmateo.avinnovz.com.sanmateoprofile.activities.MapActivity;
import sanmateo.avinnovz.com.sanmateoprofile.activities.TyphoonWatchActivity;
import sanmateo.avinnovz.com.sanmateoprofile.activities.WaterLevelMonitoringActivity;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.BannerAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.HomeMenuAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.dao.CurrentUser;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.BannerFragment;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.ChangePasswordDialogFragment;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.DisasterMgtMenuDialogFragment;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.SanMateoBannerFragment;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiErrorHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiRequestHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppBarStateListener;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.DaoHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.PicassoHelper;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnConfirmDialogListener;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnS3UploadListener;
import sanmateo.avinnovz.com.sanmateoprofile.models.others.HomeMenu;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.ApiError;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.GenericMessage;
import sanmateo.avinnovz.com.sanmateoprofile.services.PusherService;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.CurrentUserSingleton;

public class AdminMainActivity extends BaseActivity implements OnApiRequestListener, OnS3UploadListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.navigationView) NavigationView navigationView;
    @BindView(R.id.viewPager) AutoScrollViewPager viewPager;
    @BindView(R.id.drawerLayout) DrawerLayout drawerLayout;
    @BindView(R.id.rvHomeMenu) RecyclerView rvHomeMenu;
    @BindView(R.id.llLatestNewsAndEvents) LinearLayout llLatestNewsAndEvents;
    @BindView(R.id.appBarLayout) AppBarLayout appBarLayout;
    @BindView(R.id.tvNotification) TextView tvNotification;
    @BindView(R.id.llHeader) LinearLayout llHeader;
    @BindView(R.id.ivMayorImage) ImageView ivMayorImage;
    @BindString(R.string.disaster_mgmt) String headerDisasterManagement;
    @BindDimen(R.dimen._90sdp) int profilePicSize;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private CurrentUserSingleton currentUserSingleton;
    private ApiRequestHelper apiRequestHelper;
    private String token;
    private static final int SELECT_IMAGE = 1;
    private static final int CAPTURE_IMAGE = 2;
    private Uri fileUri;
    private File fileToUpload;
    private String uploadToBucket;
    private ImageView ivProfileImage;
    private ProgressBar pbLoadImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        currentUserSingleton = CurrentUserSingleton.newInstance();
        animateBanners();
        initNavigationDrawer();
        initHomeMenu();
        initAmazonS3Helper(this);
        token = currentUserSingleton.getCurrentUser().getToken();

        if (!isMyServiceRunning(PusherService.class)) {
            LogHelper.log("pusher","service not yet running");
            startService(new Intent(this, PusherService.class));
        } else {
            LogHelper.log("pusher","service already running");
        }
        llLatestNewsAndEvents.setVisibility(View.GONE);
        initAppBarLayoutListener();
        apiRequestHelper = new ApiRequestHelper(this);
    }

    private void animateBanners() {
        final ArrayList<Fragment> fragments = new ArrayList<>();
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
        final TextView tvProfileName = (TextView)view.findViewById(R.id.tvProfileName);
        pbLoadImage = (ProgressBar)view.findViewById(R.id.pbLoadImage);
        ivProfileImage = (ImageView)view.findViewById(R.id.ivProfileImage);
        ivProfileImage.setOnClickListener(view1 -> showChangeProfilePicMenu());

        final int screenHeight = getScreenDimension("height");
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                ((int)(screenHeight * .4)));

        navigationView.addHeaderView(view);
        navigationView.inflateMenu(R.menu.menu_side_drawer);
        navigationView.getHeaderView(0).setLayoutParams(params);

        navigationView.setNavigationItemSelectedListener(item -> {
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
                    menu.add("Emergency Flashlight");
                    menu.add("SOS Signal");
                    final DisasterMgtMenuDialogFragment fragment = DisasterMgtMenuDialogFragment
                            .newInstance(headerDisasterManagement,menu);
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
                            }
                        }

                        @Override
                        public void onClose() {
                            fragment.dismiss();
                        }
                    });
                    fragment.show(getFragmentManager(),"disaster menu");
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
                                    startActivity(new Intent(AdminMainActivity.this, LoginActivity.class));
                                    finish();
                                    animateToRight(AdminMainActivity.this);
                                }

                                @Override
                                public void onCancelled(String action) {

                                }
                            });
                    break;
            }
            drawerLayout.closeDrawers();
            return true;
        });

        AppConstants.PICASSO.load(currentUserSingleton.getCurrentUser().getPicUrl())
                .fit().centerCrop().into(ivProfileImage, new Callback() {
            @Override
            public void onSuccess() {
                pbLoadImage.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });
        tvProfileName.setText(currentUserSingleton.getCurrentUser().getFirstName() + " " +
                                    currentUserSingleton.getCurrentUser().getLastName());
    }

    private void initHomeMenu() {
        final ArrayList<HomeMenu> homeMenus = new ArrayList<>();
        homeMenus.add(new HomeMenu(getImageById(R.drawable.menu_ambulance),"Review Incidents"));
        homeMenus.add(new HomeMenu(getImageById(R.drawable.menu_news),"News/Events"));
        homeMenus.add(new HomeMenu(getImageById(R.drawable.menu_announcement),"Public Announcements"));
        homeMenus.add(new HomeMenu(getImageById(R.drawable.menu_water_level),"Water Level Monitoring"));
        homeMenus.add(new HomeMenu(getImageById(R.drawable.menu_gallery),"Manage Gallery"));
        homeMenus.add(new HomeMenu(getImageById(R.drawable.menu_officials),"Manage Officials"));

        final HomeMenuAdapter adapter = new HomeMenuAdapter(this,homeMenus);
        adapter.setOnSelectHomeMenuListener(position -> {
            if (position == 0) {
                moveToOtherActivity(ReviewIncidentsActivity.class);
            } else if (position == 1) {
                moveToOtherActivity(NewsEventsManagementActivity.class);
            } else if (position == 2) {
                moveToOtherActivity(PublicAnnouncementsActivity.class);
            } else if (position == 3) {
                moveToOtherActivity(WaterLevelMonitoringActivity.class);
            } else if (position == 4) {
                moveToOtherActivity(ManageGalleryActivity.class);
            } else if (position == 5) {
                moveToOtherActivity(ManageOfficialsActivity.class);
            }
        });
        rvHomeMenu.setAdapter(adapter);
        rvHomeMenu.setLayoutManager(new LinearLayoutManager(this));
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

    private void changePassword() {
        ChangePasswordDialogFragment fragment = ChangePasswordDialogFragment.newInstance();
        fragment.setOnChangePasswordListener((oldPassword, newPassword)
                -> apiRequestHelper.changePassword(token,currentUserSingleton.getCurrentUser().getEmail(),
                oldPassword,newPassword));
        fragment.show(getFragmentManager(),"chane password");
    }

    @Override
    public void onApiRequestBegin(String action) {
        if (action.equals(AppConstants.ACTION_PUT_CHANGE_PASSWORD)) {
            showCustomProgress("Changing password, Please wait...");
        } else if (action.equals(AppConstants.ACTION_PUT_CHANGE_PROFILE_PIC)) {
            showCustomProgress("Changing your profile pic, Please wait...");
        }
    }

    @Override
    public void onApiRequestSuccess(String action, Object result) {
        dismissCustomProgress();
        if (action.equals(AppConstants.ACTION_PUT_CHANGE_PASSWORD)) {
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
    }

    @Override
    public void onApiRequestFailed(String action, Throwable t) {
        dismissCustomProgress();
        handleApiException(t);
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
                fileToUpload = getFile(data.getData(), UUID.randomUUID().toString()+".png");
            } else if (requestCode == CAPTURE_IMAGE) {
                fileToUpload = rotateBitmap(fileUri.getPath());
            }
            /** delete previous profile pic from s3 if it's not the default profile pic using gravatar */
            if (!currentUserSingleton.getCurrentUser().getPicUrl()
                    .contains("http://www.gravatar.com/avatar/")) {
                deleteImage(AppConstants.BUCKET_ROOT, currentUserSingleton.getCurrentUser().getPicUrl());
            }
            uploadImageToS3(uploadToBucket, fileToUpload, 1, 1);
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

