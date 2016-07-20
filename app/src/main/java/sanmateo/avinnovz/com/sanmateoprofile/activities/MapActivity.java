package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
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
    @BindView(R.id.appBarLayout) AppBarLayout appBarLayout;
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
        appBarLayout.setExpanded(true);
        double lat = latLng.latitude;
        double longi = latLng.longitude;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, longi), 18));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (map == null) {
            map = googleMap;
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            map.getUiSettings().setScrollGesturesEnabled(true);
            map.getUiSettings().setZoomGesturesEnabled(true);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(14.695179, 121.117852), 18));
            addMapMarkers(getLocalGovernmentOffice(), true);
            addMapMarkers(getNationalGovernmentOffice(), true);
            addMapMarkers(getTouristAndLeisurePlaces(), false);
        }
    }

    private void addMapMarkers(ArrayList<Location> locations, boolean isGovernment) {
        int marker = isGovernment ? R.drawable.hall : R.drawable.resort;
        if (locations.size() > 0) {
            for (Location l : locations) {
                if (l.getLatLng() != null) {
                    double lat = l.getLatLng().latitude;
                    double longi = l.getLatLng().longitude;
                    String title = l.getLocationName();
                    String snippet = l.getLocationAddress();
                    addMapMarker(map, lat, longi, title, snippet, marker);
                }
            }
        }
    }

    private ArrayList<Location> getLocalGovernmentOffice() {
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(new Location("San Mateo Municipal Hall", "General Luna St., Guitnangbayan I San Mateo, Rizal Philippines",
                "+6327067920", "http://2.bp.blogspot.com/-FB1vuzJ97l8/UQU-uUzSk3I/AAAAAAAABFs/vxK_9Y3fftA/s1600/rr+%252844%2529.JPG", new LatLng(14.695179, 121.117852),
                AppConstants.CATEGORY_MUNICIPAL));
        locations.add(new Location("Barangay Hall Ampid 1", "E Delos Santos Rd, Ampid-1, San Mateo, 1830 Rizal",
                "+6327067920", "https://lh6.googleusercontent.com/proxy/liJgfvGIXWj49cNThzgbJiklXqNuywCCt028xoHIpFUIIa3RKaQPVJl7UUKgl5NxkpiHa3gf7cALoUWD7nLGxI9YBO33K1sr1FUC6SbMPwMmoV2ZOImAU6GEJj12alvD6Try9vioaFyDMlp1CzDF37SI3w=w455-h256", new LatLng(14.6811298, 121.1186296),
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
                "https://geo2.ggpht.com/cbk?panoid=XNQVyRqna3i94wRCM3kUhw&output=thumbnail&cb_client=search.TACTILE.gps&thumb=2&w=408&h=256&yaw=110.2782&pitch=0", new LatLng(14.6955274, 121.1213578),
                AppConstants.CATEGORY_BARANGAY));
        locations.add(new Location("Barangay Guitnang Bayan 2", "E Florencio St, Gutinang Bayan 2, San Mateo, 1850 Rizal",
                "+6329974910", "", new LatLng(14.697426, 121.12347), AppConstants.CATEGORY_BARANGAY));
        locations.add(new Location("Barangay Guinayang", "Patiis Rd, San Mateo, Rizal", "", "",
                new LatLng(14.7017485, 121.1281675), AppConstants.CATEGORY_BARANGAY));
        locations.add(new Location("Barangay Gulod Malaya", "San Mateo, Rizal", "", "",
                new LatLng(14.6715113, 121.1289078), AppConstants.CATEGORY_BARANGAY));
        locations.add(new Location("Barangay Malanday", "Tubo Kanan Cor Patiis Road, San Mateo, 1850 Rizal",
                "+6326317662", "https://lh6.googleusercontent.com/proxy/InPFwYg2Nc35lcbwl8YQSpxaOwXs3IMqS7XiQbg6TVBsXjTumhZ1BE9lpVAYzY8Uc8wuu2KdZVNma_SjGxy0lt2jDFI2-qQom4sKpInlhzov_nUwyq97OfOCFP7XvHOwDCrj59eNBUCV0yW9Ivy9OQpM0w=w408-h725", new LatLng(14.7018432, 121.1314569),
                AppConstants.CATEGORY_BARANGAY));
        locations.add(new Location("Barangay Maly", "GSIS Street, Maly, San Mateo, 1850 Rizal",
                "", "https://lh3.googleusercontent.com/proxy/bfMNreae3Q0hv6AqE5aoWmyWdB4-1ELcs1vq5iX50Bs99sSwyjIHCgzytTY8lJDdr8YuYLB2iZhVd4-KE9NsSbUhmI_uQuCPy3e75SVz4iMCIFlPHWaQE4e4zXKWQ70KMDN1mew34qsHcR50_6l40DM_ZA=w455-h256", new LatLng(14.709703, 121.133885),
                AppConstants.CATEGORY_BARANGAY));
        locations.add(new Location("Barangay Pitong Bukawe", "San Mateo, Rizal", "", "",
                new LatLng(14.6809045, 121.1348223), AppConstants.CATEGORY_BARANGAY));
        locations.add(new Location("Barangay Silangan", "Old Army Rd., Silangan, San Mateo, 1850 Rizal",
                "+6329974704", "https://lh6.googleusercontent.com/proxy/toAMieO2pLtfWV_eisR4JK9EjFXAOb-kvnTkD0gEnwn2tkK1VyDGXvYJJeTQIEFd0gOPZ0XfpK76orX1RrOuxnUw8Bm6UGUctU6lMfFWt8dEtHcwfXzPpmNeEEDz4F8sP-_sLfU78Fy7F4EpI844zldbiw=w408-h725", new LatLng(14.656925, 121.152136),
                AppConstants.CATEGORY_BARANGAY));
        locations.add(new Location("Barangay Sta. Ana", "San Mateo, Rizal", "", "",
                new LatLng(14.6912382, 121.114642), AppConstants.CATEGORY_BARANGAY));
        locations.add(new Location("Barangay Sta. Nino", "Sampaguita Street, San Mateo, Rizal", "",
                "https://geo3.ggpht.com/cbk?panoid=FKfqmSQIB8tONj6x30YuLg&output=thumbnail&cb_client=search.TACTILE.gps&thumb=2&w=408&h=256&yaw=70.554657&pitch=0", new LatLng(14.6690019, 121.1342816),
                AppConstants.CATEGORY_BARANGAY));
        return locations;
    }

    private ArrayList<Location> getNationalGovernmentOffice() {
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(new Location("Police Station", "Gen. Luna Ave, San Mateo, Rizal", "+6322126558",
                "", new LatLng(14.6952113, 121.1158597),
                AppConstants.CATEGORY_PEACE_AND_SECURITY));
        locations.add(new Location("SSS", "Max's Building, 15 P. Burgos St., Sta. Ana, San Mateo, 1850 Rizal",
                "+6329976237", "https://lh5.googleusercontent.com/proxy/YM_kCxOb149D905ivThSLyf_46C8lw90EMGRzcMq5frycB3f6--8N0ZHzLmpFYMvTs3lPpeKwVHleOf8x5Dc7YJQzsqfmpgfODL8VnKx-cXlhONkMc_v0l9DdKuzOVdQFwkL3rRY3rFu7S-URz5r8tI4JQ=w455-h256", new LatLng(14.6949305, 121.1172338),
                AppConstants.CATEGORY_SOCIAL_AND_DEVELOPMENT));
        locations.add(new Location("PhilHealth", "Daang Tubo, San Mateo, Rizal", "", "",
                new LatLng(14.6693369, 121.0939795), AppConstants.CATEGORY_HEALTH));
        return locations;
    }

    private ArrayList<Location> getTouristAndLeisurePlaces() {
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(new Location("Ciudad Christhia Resort 9 Waves", "SM San Mateo, Gen. Luna Ave, Ampid 1, San Mateo, 1850 Rizal",
                "+6329976009", "http://www.9wavesphilippines.com/images/ciudad-christhia-9-waves.jpg",
                new LatLng(14.6693369, 121.0939795), AppConstants.CATEGORY_RESORT));
        locations.add(new Location("SM San Mateo", "Carrieland Country Homes II Ampid San Mateo, Rizal, San Mateo, 1850 Rizal",
                "+639204414806", "", new LatLng(14.6790502, 121.1113273), AppConstants.CATEGORY_SHOPPING_MALL));
        locations.add(new Location("PureGold", "L4587 General Luna, Banaba, San Mateo, 1850 Rizal",
                "+6325701972", "http://2.bp.blogspot.com/_rmzf_x0bkFs/TCODTadYdEI/AAAAAAAAKSc/Lqjs5PwDP8A/s1600/Picture+022.jpg",
                new LatLng(14.6736949, 121.0793558), AppConstants.CATEGORY_SHOPPING_MALL));
        locations.add(new Location("Dap-ayan Azul Resto", "Divine Mercy village, Guitnang Bayan Uno, San Mateo Rizal, C6 Rd, San Mateo, Rizal",
                "+6328611743", "https://lh3.googleusercontent.com/-X60fdUAxh2k/UewWOiTg8nI/AAAAAAAAAQQ/mx99b36fcjAU_pLJcLPM-uvR0A4GcrrdQ/s408-k-no/",
                new LatLng(14.6851939, 121.1353706), AppConstants.CATEGORY_AUTHENTIC_RESTAURANT));
        locations.add(new Location("Diocesan Shrine and Parish of Nuestra Señora de Aranzazu",
                "Gen. Luna Ave, San Mateo, 1850 Rizal", "+6325709220", "https://lh6.googleusercontent.com/-sJb31HW9g2A/VDnQfIok2FI/AAAAAAAAABs/QtXgtDu_8bw1eNC22OzmE3MjgjV0gBBdg/s408-k-no/",
                new LatLng(14.6956092, 121.1153468), AppConstants.CATEGORY_HERITAGE_SITE));
        return locations;
    }
}
