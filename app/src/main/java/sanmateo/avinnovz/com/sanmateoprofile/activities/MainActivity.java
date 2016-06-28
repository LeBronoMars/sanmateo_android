package sanmateo.avinnovz.com.sanmateoprofile.activities;

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
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.BannerAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.HomeMenuAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.BannerFragment;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.SanMateoBannerFragment;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.GlideHelper;
import sanmateo.avinnovz.com.sanmateoprofile.models.others.HomeMenu;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.CurrentUserSingleton;

public class MainActivity extends BaseActivity {

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
        navigationView.addHeaderView(view);
        navigationView.inflateMenu(R.menu.menu_side_drawer);
        GlideHelper.loadImage(this, currentUserSingleton.getAuthResponse().getPicUrl(),ivProfileImage);
        tvProfileName.setText(currentUserSingleton.getAuthResponse().getFirstName() + " " +
                                    currentUserSingleton.getAuthResponse().getLastName());
    }

    private void initHomeMenu() {
        final ArrayList<HomeMenu> homeMenus = new ArrayList<>();
        homeMenus.add(new HomeMenu(getImageById(R.drawable.menu_alarm),"Panic/Emergency"));
        homeMenus.add(new HomeMenu(getImageById(R.drawable.menu_ambulance),"Incident Report"));
        homeMenus.add(new HomeMenu(getImageById(R.drawable.menu_info),"Information"));
        homeMenus.add(new HomeMenu(getImageById(R.drawable.menu_locations),"Map"));
        homeMenus.add(new HomeMenu(getImageById(R.drawable.menu_directories),"Directories"));
        homeMenus.add(new HomeMenu(getImageById(R.drawable.menu_gallery),"Gallery"));
        homeMenus.add(new HomeMenu(getImageById(R.drawable.menu_news),"News/Events"));
        homeMenus.add(new HomeMenu(getImageById(R.drawable.menu_fb),"Social Media"));
        homeMenus.add(new HomeMenu(getImageById(R.drawable.menu_announcement),"Disaster Management"));
        homeMenus.add(new HomeMenu(getImageById(R.drawable.menu_contact_us),"Contact Us"));
        final HomeMenuAdapter adapter = new HomeMenuAdapter(this,homeMenus);
        adapter.setOnSelectHomeMenuListener(new HomeMenuAdapter.OnSelectHomeMenuListener() {
            @Override
            public void onSelectedMenu(int position) {
                if (position == 1) {
                    startActivity(new Intent(MainActivity.this, IncidentsActivity.class));
                }
            }
        });
        rvHomeMenu.setAdapter(adapter);
        rvHomeMenu.setLayoutManager(new LinearLayoutManager(this));
    }
}

