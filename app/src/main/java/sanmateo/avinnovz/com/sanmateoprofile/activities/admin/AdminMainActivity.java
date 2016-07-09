package sanmateo.avinnovz.com.sanmateoprofile.activities.admin;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.BannerAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.HomeMenuAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.BannerFragment;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.SanMateoBannerFragment;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.models.others.HomeMenu;
import sanmateo.avinnovz.com.sanmateoprofile.services.PusherService;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.CurrentUserSingleton;

public class AdminMainActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.navigationView) NavigationView navigationView;
    @BindView(R.id.viewPager) AutoScrollViewPager viewPager;
    @BindView(R.id.drawerLayout) DrawerLayout drawerLayout;
    @BindView(R.id.rvHomeMenu) RecyclerView rvHomeMenu;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private CurrentUserSingleton currentUserSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        currentUserSingleton = CurrentUserSingleton.newInstance();
        animateBanners();
        initNavigationDrawer();
        initHomeMenu();

        if (!isMyServiceRunning(PusherService.class)) {
            LogHelper.log("pusher","service not yet running");
            startService(new Intent(this, PusherService.class));
        } else {
            LogHelper.log("pusher","service already running");
        }
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

        AppConstants.PICASSO.load(currentUserSingleton.getAuthResponse().getPicUrl())
                .fit().centerCrop().into(ivProfileImage);
        tvProfileName.setText(currentUserSingleton.getAuthResponse().getFirstName() + " " +
                                    currentUserSingleton.getAuthResponse().getLastName());
    }

    private void initHomeMenu() {
        final ArrayList<HomeMenu> homeMenus = new ArrayList<>();
        homeMenus.add(new HomeMenu(getImageById(R.drawable.menu_ambulance),"Review Incidents"));
        homeMenus.add(new HomeMenu(getImageById(R.drawable.menu_news),"News/Events"));
        homeMenus.add(new HomeMenu(getImageById(R.drawable.menu_announcement),"Public Announcements"));

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
                }
                animateToLeft(AdminMainActivity.this);
            }
        });
        rvHomeMenu.setAdapter(adapter);
        rvHomeMenu.setLayoutManager(new LinearLayoutManager(this));
    }
}

