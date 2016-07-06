package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.os.Bundle;
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
 * Created by ctmanalo on 7/6/16.
 */
public class MapActivity extends BaseActivity {

    @BindView(R.id.pager) ViewPager pager;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        setToolbarTitle("Maps");
        fragments.add(MapMunicipal.newInstance());
        fragments.add(MapTouristSpot.newInstance());
        fragments.add(MapGovtAgencies.newInstance());
        pager.setAdapter(new MapPagerAdapter(getSupportFragmentManager(), fragments));
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
