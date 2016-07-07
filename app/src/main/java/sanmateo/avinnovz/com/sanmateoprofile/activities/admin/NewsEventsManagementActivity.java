package sanmateo.avinnovz.com.sanmateoprofile.activities.admin;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.TabAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.admin.NewsEventsFragment;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiRequestHelper;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.CurrentUserSingleton;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.NewsSingleton;

/**
 * Created by rsbulanon on 7/6/16.
 */
public class NewsEventsManagementActivity extends BaseActivity implements OnApiRequestListener {

    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<String> tabNames = new ArrayList<>();
    private ApiRequestHelper apiRequestHelper;
    private CurrentUserSingleton currentUserSingleton;
    private NewsSingleton newsSingleton;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_event_management);
        ButterKnife.bind(this);
        initViewPager();
        apiRequestHelper = new ApiRequestHelper(this);
        currentUserSingleton = CurrentUserSingleton.newInstance();
        newsSingleton = NewsSingleton.getInstance();
        token = currentUserSingleton.getAuthResponse().getToken();

        if (newsSingleton.getIncidents().size() == 0) {

        } else {

        }
        setToolbarTitle("News & Events Management");
    }

    private void initViewPager() {
        /** set up viewpager and tab layout */
        tabNames.add("Today");
        tabNames.add("Previous");

        fragments.add(NewsEventsFragment.newInstance("Today"));
        fragments.add(NewsEventsFragment.newInstance("Previous"));

        viewPager.setAdapter(new TabAdapter(getSupportFragmentManager(), fragments, tabNames));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);


    }

    @Override
    public void onApiRequestBegin(String action) {

    }

    @Override
    public void onApiRequestSuccess(String action, Object result) {

    }

    @Override
    public void onApiRequestFailed(String action, Throwable t) {

    }
}
