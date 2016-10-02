package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.MapPagerAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.MapGovtAgencies;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.MapMunicipal;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.MapTouristSpot;

/**
 * Created by ctmanalo on 7/15/16.
 */
public class NewMapActivty extends BaseActivity {

    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.tabLayout) TabLayout tabLayout;

    private ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_map);
        ButterKnife.bind(this);
        setToolbarTitle("Map");
        initViewPager();
    }

    private void initViewPager() {
        fragments.add(MapMunicipal.newInstance());
        fragments.add(MapTouristSpot.newInstance());
        fragments.add(MapGovtAgencies.newInstance());
        viewPager.setAdapter(new MapPagerAdapter(getSupportFragmentManager(), fragments));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        animateToRight(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        animateToRight(this);
    }
}
