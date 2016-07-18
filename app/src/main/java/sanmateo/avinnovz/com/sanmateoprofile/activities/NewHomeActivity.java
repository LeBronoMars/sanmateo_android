package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import it.gmariotti.recyclerview.itemanimator.SlideInOutLeftItemAnimator;
import retrofit2.adapter.rxjava.HttpException;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.admin.PublicAnnouncementsActivity;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.BannerAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.NewsAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.BannerFragment;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.ChangePasswordDialogFragment;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.DisasterMgtMenuDialogFragment;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.MayorMessageDialogFragment;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.PanicSettingsDialogFragment;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.SanMateoBannerFragment;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiErrorHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiRequestHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppBarStateListener;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.PrefsHelper;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnConfirmDialogListener;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.ApiError;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.GenericMessage;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.News;
import sanmateo.avinnovz.com.sanmateoprofile.services.PusherService;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.CurrentUserSingleton;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.NewsSingleton;

/**
 * Created by rsbulanon on 7/12/16.
 */
public class NewHomeActivity extends BaseActivity implements OnApiRequestListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.navigationView) NavigationView navigationView;
    @BindView(R.id.viewPager) AutoScrollViewPager viewPager;
    @BindView(R.id.drawerLayout) DrawerLayout drawerLayout;
    @BindView(R.id.rvHomeMenu) RecyclerView rvHomeMenu;
    @BindView(R.id.appBarLayout) AppBarLayout appBarLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private CurrentUserSingleton currentUserSingleton;
    private NewsSingleton newsSingleton;
    private ApiRequestHelper apiRequestHelper;
    private String token;
    private boolean loading = true;
    private int pastVisibleItems;
    private int visibleItemCount;
    private int totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initPanicContact();

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
    }

    private void animateBanners() {
        final ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(SanMateoBannerFragment.newInstance());
        fragments.add(BannerFragment.newInstance(ContextCompat.getDrawable(this,R.drawable.banner1)));
        fragments.add(BannerFragment.newInstance(ContextCompat.getDrawable(this,R.drawable.banner2)));
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
        final ImageView ivProfileImage = (ImageView)view.findViewById(R.id.ivProfileImage);
        final TextView tvProfileName = (TextView)view.findViewById(R.id.tvProfileName);

        final int screenHeight = getScreenDimension("height");
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                ((int)(screenHeight * .4)));

        navigationView.addHeaderView(view);
        navigationView.inflateMenu(R.menu.menu_side_drawer);
        navigationView.getHeaderView(0).setLayoutParams(params);

        AppConstants.PICASSO.load(currentUserSingleton.getCurrentUser().getPicUrl())
                .fit().centerCrop().into(ivProfileImage);
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
                        moveToOtherAcitivity(IncidentsActivity.class);
                        break;
                    case R.id.menu_information:
                        moveToOtherAcitivity(InformationActivity.class);
                        break;
                    case R.id.menu_map:
                        moveToOtherAcitivity(NewMapActivty.class);
                        break;
                    case R.id.menu_directories:
                        moveToOtherAcitivity(DirectoriesActivity.class);
                        break;
                    case R.id.menu_gallery:
                        moveToOtherAcitivity(GalleryActivity.class);
                        break;
                    /*case R.id.menu_news_events:
                        moveToOtherAcitivity(NewsEventsManagementActivity.class);
                        break;*/
                    case R.id.menu_social_media:
                        showToast("social media");
                        break;
                    case R.id.menu_disaster_management:
                        final DisasterMgtMenuDialogFragment fragment = DisasterMgtMenuDialogFragment.newInstance();
                        fragment.setOnSelectDisasterMenuListener(new DisasterMgtMenuDialogFragment.OnSelectDisasterMenuListener() {
                            @Override
                            public void onSelectedMenu(int position) {
                                if (position == 0) {
                                    moveToOtherAcitivity(PublicAnnouncementsActivity.class);
                                } else if (position == 1) {
                                    moveToOtherAcitivity(TyphoonWatchActivity.class);
                                } else if (position == 2) {
                                    moveToOtherAcitivity(WaterLevelMonitoringActivity.class);
                                } else if (position == 3) {
                                    moveToOtherAcitivity(GlobalDisasterActivity.class);
                                } else if (position == 4) {
                                    moveToOtherAcitivity(HotlinesActivity.class);
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
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void changePassword() {
        ChangePasswordDialogFragment fragment = ChangePasswordDialogFragment.newInstance();
        fragment.setOnChangePasswordListener(new ChangePasswordDialogFragment.OnChangePasswordListener() {
            @Override
            public void onConfirm(String oldPassword, String newPassword) {
                apiRequestHelper.changePassword(token,currentUserSingleton.getCurrentUser().getEmail(),
                        oldPassword,newPassword);
            }
        });
        fragment.show(getFragmentManager(),"chane password");
    }

    private void changeUserPassword(final String oldPassword, final String newPassword) {
        final String email = currentUserSingleton.getCurrentUser().getEmail();
        apiRequestHelper.changePassword(token, email, oldPassword, newPassword);
    }

    private void initNews() {
        final NewsAdapter newsAdapter = new NewsAdapter(this, newsSingleton.getAllNews());
        newsAdapter.setOnSelectNewsListener(new NewsAdapter.OnSelectNewsListener() {
            @Override
            public void onSelectedNews(News n) {
                final Intent intent = new Intent(NewHomeActivity.this, NewsFullPreviewActivity.class);
                intent.putExtra("news",n);
                startActivity(intent);
                animateToLeft(NewHomeActivity.this);
            }
        });
        rvHomeMenu.setAdapter(newsAdapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvHomeMenu.setLayoutManager(linearLayoutManager);
        rvHomeMenu.setItemAnimator(new SlideInOutLeftItemAnimator(rvHomeMenu));
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
                //swipeRefreshItems.setEnabled(topRowVerticalPosition >= 0);
            }
        });
    }

    @Override
    public void onApiRequestBegin(String action) {
        if (action.equals(AppConstants.ACTION_GET_NEWS)) {
            showCustomProgress("Fetching news, Please wait...");
        } else if (action.equals(AppConstants.ACTION_PUT_CHANGE_PASSWORD)) {
            showCustomProgress("Changing password, Please wait...");
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
                    }
                }
            } catch (JSONException e) {

            }
        }
    }

    private void initPanicContact() {
        if (PrefsHelper.getInt(this, "panicContactSize") == 0) {
            LogHelper.log("book","show");
            showConfirmDialog("", "Emergency Contacts", "Please add at least one contact person" +
                    " for emergency purposes", "Ok", "", new OnConfirmDialogListener() {
                @Override
                public void onConfirmed(String action) {
                    setPanicContacts();
                }

                @Override
                public void onCancelled(String action) {

                }
            });
        } else {
            LogHelper.log("book","do not show");
        }
    }

    private void moveToOtherAcitivity(Class clz) {
        startActivity(new Intent(this, clz));
        animateToLeft(this);
    }

    public void setPanicContacts() {
        final PanicSettingsDialogFragment panicSettingsFragment = PanicSettingsDialogFragment.newInstance();
        panicSettingsFragment.setOnPanicContactListener(new PanicSettingsDialogFragment.OnPanicContactListener() {
            @Override
            public void onDismiss() {
                panicSettingsFragment.dismiss();
                initPanicContact();
            }
        });
        panicSettingsFragment.show(getSupportFragmentManager(),"panic");
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
                LogHelper.log("toolbar","state --> " + state.name());
            }
        });
    }
}
