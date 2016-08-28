package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

import butterknife.BindDrawable;
import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.TabPagerAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.AlertLevelFragment;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.AppMarkerDialogFragment;
import sanmateo.avinnovz.com.sanmateoprofile.models.others.AppMarker;

/**
 * Created by ctmanalo on 8/28/16.
 */
public class AlertLevelActivity extends BaseActivity implements OnMapReadyCallback, OnMarkerClickListener{

    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.appBarLayout) AppBarLayout appBarLayout;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<AppMarker> appMarkers = new ArrayList<>();
    private GoogleMap map;
    private int tabPosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_level);
        ButterKnife.bind(this);
        initMapMarker();
        initTabs();
        initMap();
    }

    private void initMapMarker() {
        appMarkers.clear();
        appMarkers.add(new AppMarker(14.6969916, 121.1197769, "San Mateo Public Market", "", ""));
        appMarkers.add(new AppMarker(14.6776636, 121.1113037, "Batasan-San Mateo Bridge", "", ""));
    }

    private void initTabs() {
        int markers = appMarkers.size();
        String[] titles = new String[markers];
        for (int i = 0; i < markers; i++) {
            fragments.add(AlertLevelFragment.newInstance());
            titles[i] = appMarkers.get(i).getTitle();
        }
        viewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(), fragments, titles));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPosition = tab.getPosition();
                focusOnMap(appMarkers.get(tabPosition).getLatLng());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.setOffscreenPageLimit(2);
    }

    private void initMap() {
        final MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @OnClick(R.id.llHeader)
    public void goBack() {
        onBackPressed();
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

    public void focusOnMap(LatLng latLng) {
        appBarLayout.setExpanded(true);
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (map == null) {
            map = googleMap;
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            map.getUiSettings().setScrollGesturesEnabled(true);
            map.getUiSettings().setZoomGesturesEnabled(true);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(appMarkers.get(0).getLatLng(), 15));
            map.setOnMarkerClickListener(this);
            initMarkers();
        }
    }

    private void initMarkers() {
        for (int i = 0; i < appMarkers.size(); i++) {
            double latitude = appMarkers.get(i).getLatitude();
            double longitude = appMarkers.get(i).getLongitude();
            String title = appMarkers.get(i).getTitle();
            String snippet = appMarkers.get(i).getSnippet();
            addMapMarker(map, latitude, longitude, title, snippet, -1);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        viewMarkerDetails(marker);
        return false;
    }

    private void viewMarkerDetails(Marker marker) {
        int tabPosition = marker.getTitle().equals("San Mateo Public Market") ? 0 : 1;
        tabLayout.getTabAt(tabPosition).select();

        AppMarkerDialogFragment fragment = AppMarkerDialogFragment
                .newInstance(appMarkers.get(tabPosition));
        fragment.show(getFragmentManager(), "Marker Details");
    }
}
