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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import it.gmariotti.recyclerview.itemanimator.SlideInOutLeftItemAnimator;
import retrofit2.adapter.rxjava.HttpException;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.BannerAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.NewsAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.BannerFragment;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.SanMateoBannerFragment;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiErrorHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiRequestHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.ApiError;
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
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private CurrentUserSingleton currentUserSingleton;
    private NewsSingleton newsSingleton;
    private ApiRequestHelper apiRequestHelper;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        currentUserSingleton = CurrentUserSingleton.newInstance();
        newsSingleton = NewsSingleton.getInstance();
        apiRequestHelper = new ApiRequestHelper(this);
        token = currentUserSingleton.getAuthResponse().getToken();

        animateBanners();
        initNavigationDrawer();
        initNews();

        if (!isMyServiceRunning(PusherService.class)) {
            startService(new Intent(this, PusherService.class));
        }

        if (newsSingleton.getNewsPrevious().size() == 0) {
            apiRequestHelper.getNews(token,0,10,"active",AppConstants.ACTION_GET_NEWS);
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

        navigationView.inflateMenu(R.menu.menu_side_drawer);
        AppConstants.PICASSO.load(currentUserSingleton.getAuthResponse().getPicUrl())
                .fit().centerCrop().into(ivProfileImage);
        tvProfileName.setText(currentUserSingleton.getAuthResponse().getFirstName() + " " +
                currentUserSingleton.getAuthResponse().getLastName());
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
        rvHomeMenu.setLayoutManager(new LinearLayoutManager(this));
        rvHomeMenu.setItemAnimator(new SlideInOutLeftItemAnimator(rvHomeMenu));
    }

    @Override
    public void onApiRequestBegin(String action) {
        if (action.equals(AppConstants.ACTION_GET_NEWS)) {
            showCustomProgress("Fetching news, Please wait...");
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
        }
        rvHomeMenu.getAdapter().notifyDataSetChanged();
        rvHomeMenu.smoothScrollToPosition(0);
    }

    @Override
    public void onApiRequestFailed(String action, Throwable t) {
        dismissCustomProgress();
        LogHelper.log("err","error in ---> " + action + " cause ---> " + t.getMessage());
        if (t instanceof HttpException) {
            if (action.equals(AppConstants.ACTION_LOGIN)) {
                final ApiError apiError = ApiErrorHelper.parseError(((HttpException) t).response());
                showConfirmDialog(action,"Login Failed", apiError.getMessage(),"Close","",null);
            }
        }
    }

    @Subscribe
    public void handleResponse(final HashMap<String,Object> map) {
        if (map.containsKey("data")) {
            try {
                final JSONObject json = new JSONObject(map.get("data").toString());
                apiRequestHelper.getNewsById(token,json.getInt("id"));
            } catch (JSONException e) {

            }
        }
    }
}
