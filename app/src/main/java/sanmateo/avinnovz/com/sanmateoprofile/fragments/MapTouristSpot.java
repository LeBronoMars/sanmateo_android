package sanmateo.avinnovz.com.sanmateoprofile.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.activities.MapActivity;
import sanmateo.avinnovz.com.sanmateoprofile.activities.NewMapActivty;

/**
 * Created by ctmanalo on 7/6/16.
 */
public class MapTouristSpot extends Fragment implements GoogleMap.OnMarkerClickListener {

    private BaseActivity activity;
    private SupportMapFragment mapTourist;

    public static MapTouristSpot newInstance() {
        return new MapTouristSpot();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (BaseActivity) getActivity();
        View view = inflater.inflate(R.layout.map_tourist_spots, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        mapTourist = (SupportMapFragment)fm.findFragmentById(R.id.mapContainer);
        if (mapTourist == null) {
            mapTourist = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.mapContainer, mapTourist).commit();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mapTourist.getMap().setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mapTourist.getMap().getUiSettings().setScrollGesturesEnabled(true);
        LatLng latLng = new LatLng(14.696123, 121.117771);
        mapTourist.getMap().moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mapTourist.getMap().animateCamera(CameraUpdateFactory.zoomTo(12));
        mapTourist.getMap().setOnMarkerClickListener(this);
        activity.addMapMarker(mapTourist.getMap(), 14.683345, 121.113245,
                "Ciudad Christhia Resort", "",R.drawable.resort);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        MapMarkerFragment fragment = MapMarkerFragment.newInstance();
        fragment.show(activity.getFragmentManager(), "municipal map details");
        return false;
    }
}
