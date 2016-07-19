package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.MapPagerAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.ImagePreviewDialogFragment;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.MapGovtAgencies;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.MapLocations;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.MapMunicipal;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.MapTouristSpot;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.models.others.Location;

/**
 * Created by ctmanalo on 7/6/16.
 */
public class MapActivity extends BaseActivity implements OnMapReadyCallback {

    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsable_map);
        ButterKnife.bind(this);
        initTabs();
        initMap();
    }

    private void initTabs() {
        fragments.add(MapLocations.newInstance(getLocalGovernmentOffice()));
        fragments.add(MapLocations.newInstance(getNationalGovernmentOffice()));
        fragments.add(MapLocations.newInstance(getTouristAndLeisurePlaces()));
        viewPager.setAdapter(new MapPagerAdapter(getSupportFragmentManager(), fragments));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(3);
    }

    private void initMap() {
        final MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
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

    public void showImage(String imageUrl) {
        ImagePreviewDialogFragment fragment = ImagePreviewDialogFragment.newInstance(imageUrl);
        fragment.show(getFragmentManager(), "Image Preview");
    }

    public void focusOnMap(LatLng latLng) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (map == null) {
            map = googleMap;
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            map.getUiSettings().setScrollGesturesEnabled(true);
            map.getUiSettings().setZoomGesturesEnabled(true);
            map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(14.695179, 121.117852)));
            map.animateCamera(CameraUpdateFactory.zoomBy(11));
        }
    }

    private ArrayList<Location> getLocalGovernmentOffice() {
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(new Location("San Mateo Municipal Hall", "General Luna St., Guitnangbayan I San Mateo, Rizal Philippines",
                "+6327067920", "http://ichef-1.bbci.co.uk/news/660/cpsprodpb/025B/production/_85730600_monkey2.jpg", new LatLng(14.695179, 121.117852),
//                "+6327067920", "https://goo.gl/maps/nwp1Dt6pDWu", new LatLng(14.695179, 121.117852),
                AppConstants.CATEGORY_MUNICIPAL));
        locations.add(new Location("Barangay Hall Ampid 1", "E Delos Santos Rd, Ampid-1, San Mateo, 1830 Rizal",
                "+6327067920", "https://goo.gl/maps/LcWV7iuEWTt", new LatLng(14.6811298, 121.1186296),
                AppConstants.CATEGORY_BARANGAY));
        locations.add(new Location("Barangay Hall Ampid 2", "San Mateo, Rizal", "", "", null,
                AppConstants.CATEGORY_BARANGAY));
        locations.add(new Location("Barangay Banaba", "San Mateo, Rizal", "", "", null,
                AppConstants.CATEGORY_BARANGAY));
        locations.add(new Location("Barangay Dulong Bayan 1", "San Mateo, Rizal", "", "",
                new LatLng(14.7010861, 121.124803), AppConstants.CATEGORY_BARANGAY));
        locations.add(new Location("Barangay Dulong Bayan 2", "San Mateo, Rizal", "", "",
                new LatLng(14.7005727, 121.1259859), AppConstants.CATEGORY_BARANGAY));
        locations.add(new Location("Barangay Guitnang Bayan 1", "San Mateo, Rizal", "",
                "https://goo.gl/maps/FYVyzzMtUw92", new LatLng(14.6955274, 121.1213578),
                AppConstants.CATEGORY_BARANGAY));
        locations.add(new Location("Barangay Guitnang Bayan 2", "E Florencio St, Gutinang Bayan 2, San Mateo, 1850 Rizal",
                "+6329974910", "", new LatLng(14.697426, 121.12347), AppConstants.CATEGORY_BARANGAY));
        locations.add(new Location("Barangay Guinayang", "Patiis Rd, San Mateo, Rizal", "", "",
                new LatLng(14.7017485, 121.1281675), AppConstants.CATEGORY_BARANGAY));
        locations.add(new Location("Barangay Gulod Malaya", "San Mateo, Rizal", "", "",
                new LatLng(14.6715113, 121.1289078), AppConstants.CATEGORY_BARANGAY));
        locations.add(new Location("Barangay Malanday", "Tubo Kanan Cor Patiis Road, San Mateo, 1850 Rizal",
                "+6326317662", "https://goo.gl/maps/3muHcy26AcD2", new LatLng(14.7018432, 121.1314569),
                AppConstants.CATEGORY_BARANGAY));
        locations.add(new Location("Barangay Maly", "GSIS Street, Maly, San Mateo, 1850 Rizal",
                "", "https://goo.gl/maps/fSyz5aPqUmr", new LatLng(14.709703, 121.133885),
                AppConstants.CATEGORY_BARANGAY));
        locations.add(new Location("Barangay Pitong Bukawe", "San Mateo, Rizal", "", "",
                new LatLng(14.6809045, 121.1348223), AppConstants.CATEGORY_BARANGAY));
        locations.add(new Location("Barangay Silangan", "Old Army Rd., Silangan, San Mateo, 1850 Rizal",
                "+6329974704", "https://goo.gl/maps/qqzrrVVXGiE2", new LatLng(14.656925, 121.152136),
                AppConstants.CATEGORY_BARANGAY));
        locations.add(new Location("Barangay Sta. Ana", "San Mateo, Rizal", "", "",
                new LatLng(14.6912382, 121.114642), AppConstants.CATEGORY_BARANGAY));
        locations.add(new Location("Barangay Sta. Nino", "Sampaguita Street, San Mateo, Rizal", "",
                "https://goo.gl/maps/n9tiupXPLG62", new LatLng(14.6690019, 121.1342816),
                AppConstants.CATEGORY_BARANGAY));
        return locations;
    }

    private ArrayList<Location> getNationalGovernmentOffice() {
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(new Location("Police Station", "Gen. Luna Ave, San Mateo, Rizal", "+6322126558",
                "https://goo.gl/maps/Agtu6Fqg5QP2", new LatLng(14.6952113, 121.1158597),
                AppConstants.CATEGORY_PEACE_AND_SECURITY));
        locations.add(new Location("SSS", "Max's Building, 15 P. Burgos St., Sta. Ana, San Mateo, 1850 Rizal",
                "+6329976237", "https://goo.gl/maps/Agtu6Fqg5QP2", new LatLng(14.6949305, 121.1172338),
                AppConstants.CATEGORY_SOCIAL_AND_DEVELOPMENT));
        locations.add(new Location("PhilHealth", "Daang Tubo, San Mateo, Rizal", "", "",
                new LatLng(14.6693369, 121.0939795), AppConstants.CATEGORY_HEALTH));
        return locations;
    }

    private ArrayList<Location> getTouristAndLeisurePlaces() {
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(new Location("Ciudad Christhia Resort 9 Waves", "SM San Mateo, Gen. Luna Ave, Ampid 1, San Mateo, 1850 Rizal",
                "+6329976009", "https://goo.gl/maps/GH7UqUsjsdA2",
                new LatLng(14.6693369, 121.0939795), AppConstants.CATEGORY_RESORT));
        locations.add(new Location("SM San Mateo", "Carrieland Country Homes II Ampid San Mateo, Rizal, San Mateo, 1850 Rizal",
                "+639204414806", "", new LatLng(14.6790502, 121.1113273), AppConstants.CATEGORY_SHOPPING_MALL));
        locations.add(new Location("PureGold", "L4587 General Luna, Banaba, San Mateo, 1850 Rizal",
                "+6325701972", "https://goo.gl/maps/e19hF8gi7Er",
                new LatLng(14.6736949, 121.0793558), AppConstants.CATEGORY_SHOPPING_MALL));
        locations.add(new Location("Dap-ayan Azul Resto", "Divine Mercy village, Guitnang Bayan Uno, San Mateo Rizal, C6 Rd, San Mateo, Rizal",
                "+6328611743", "https://goo.gl/maps/cgU15BjH3S42",
                new LatLng(14.6851939, 121.1353706), AppConstants.CATEGORY_AUTHENTIC_RESTAURANT));
        locations.add(new Location("Diocesan Shrine and Parish of Nuestra Señora de Aranzazu",
                "Gen. Luna Ave, San Mateo, 1850 Rizal", "+6325709220", "https://goo.gl/maps/yA1sKvQug2w",
                new LatLng(14.6956092, 121.1153468), AppConstants.CATEGORY_HERITAGE_SITE));
        return locations;
    }
}
