package sanmateo.avinnovz.com.sanmateoprofile.activities.admin;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.TextView;

import java.util.ArrayList;

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
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnConfirmDialogListener;
import sanmateo.avinnovz.com.sanmateoprofile.models.others.HomeMenu;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.ApiError;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.GenericMessage;
import sanmateo.avinnovz.com.sanmateoprofile.services.PusherService;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.CurrentUserSingleton;

public class AdminMainActivity extends BaseActivity implements OnApiRequestListener {

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
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private CurrentUserSingleton currentUserSingleton;
    private ApiRequestHelper apiRequestHelper;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        currentUserSingleton = CurrentUserSingleton.newInstance();
        animateBanners();
        initNavigationDrawer();
        initHomeMenu();

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
            }
        });
        AppConstants.PICASSO.load(currentUserSingleton.getCurrentUser().getPicUrl())
                .fit().centerCrop().into(ivProfileImage);
        tvProfileName.setText(currentUserSingleton.getCurrentUser().getFirstName() + " " +
                                    currentUserSingleton.getCurrentUser().getLastName());
    }

    private void initHomeMenu() {
        final ArrayList<HomeMenu> homeMenus = new ArrayList<>();
        homeMenus.add(new HomeMenu(getImageById(R.drawable.menu_ambulance),"Review Incidents"));
        homeMenus.add(new HomeMenu(getImageById(R.drawable.menu_news),"News/Events"));
        homeMenus.add(new HomeMenu(getImageById(R.drawable.menu_announcement),"Public Announcements"));
        homeMenus.add(new HomeMenu(getImageById(R.drawable.menu_water_level),"Water Level Monitoring"));

        final HomeMenuAdapter adapter = new HomeMenuAdapter(this,homeMenus);
        adapter.setOnSelectHomeMenuListener(new HomeMenuAdapter.OnSelectHomeMenuListener() {
            @Override
            public void onSelectedMenu(int position) {
                if (position == 0) {
                    startActivity(new Intent(AdminMainActivity.this, ReviewIncidentsActivity.class));
                } else if (position == 1) {
                    startActivity(new Intent(AdminMainActivity.this, NewsEventsManagementActivity.class));
                } else if (position == 2) {
                    startActivity(new Intent(AdminMainActivity.this, PublicAnnouncementsActivity.class));
                } else if (position == 3) {
                    startActivity(new Intent(AdminMainActivity.this, WaterLevelMonitoringActivity.class));
                }
                animateToLeft(AdminMainActivity.this);
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
        fragment.setOnChangePasswordListener(new ChangePasswordDialogFragment.OnChangePasswordListener() {
            @Override
            public void onConfirm(String oldPassword, String newPassword) {
                apiRequestHelper.changePassword(token,currentUserSingleton.getCurrentUser().getEmail(),
                        oldPassword,newPassword);
            }
        });
        fragment.show(getFragmentManager(),"chane password");
    }

    @Override
    public void onApiRequestBegin(String action) {
        if (action.equals(AppConstants.ACTION_PUT_CHANGE_PASSWORD)) {
            showCustomProgress("Changing password, Please wait...");
        }
    }

    @Override
    public void onApiRequestSuccess(String action, Object result) {
        dismissCustomProgress();
        if (action.equals(AppConstants.ACTION_PUT_CHANGE_PASSWORD)) {
            final GenericMessage genericMessage = (GenericMessage)result;
            showToast(genericMessage.getMessage());
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
}

