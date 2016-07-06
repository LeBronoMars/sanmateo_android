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

/**
 * Created by rsbulanon on 7/6/16.
 */
public class NewsEventsManagementActivity extends BaseActivity {

    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<String> tabNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_event_management);
        ButterKnife.bind(this);
        initViewPager();
        setToolbarTitle("News & Events Management");
    }

    private void initViewPager() {
        /** set up viewpager and tab layout */
        tabNames.add("Today");
        tabNames.add("Previous");

        fragments.add(NewsEventsFragment.newInstance());
        fragments.add(NewsEventsFragment.newInstance());

        viewPager.setAdapter(new TabAdapter(getSupportFragmentManager(), fragments, tabNames));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);
    }
}
