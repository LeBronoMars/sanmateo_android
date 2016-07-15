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

import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.MapActivity;
import sanmateo.avinnovz.com.sanmateoprofile.activities.NewMapActivty;

/**
 * Created by ctmanalo on 7/6/16.
 */
public class MapGovtAgencies extends Fragment {

    private NewMapActivty activity;
    private SupportMapFragment mapGovt;

    public static MapGovtAgencies newInstance() {
        return new MapGovtAgencies();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (NewMapActivty)getActivity();
        View view = inflater.inflate(R.layout.map_govt_agencies, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        mapGovt = (SupportMapFragment)fm.findFragmentById(R.id.mapContainer);
        if (mapGovt == null) {
            mapGovt = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.mapContainer, mapGovt).commit();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mapGovt.getMap().setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mapGovt.getMap().getUiSettings().setScrollGesturesEnabled(true);
        LatLng latLng = new LatLng( 14.696123, 121.117771);
        mapGovt.getMap().moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mapGovt.getMap().animateCamera(CameraUpdateFactory.zoomTo(12));
        activity.addMapMarker(mapGovt.getMap(), 14.695486, 121.118016,
                "Police Station", "San Mateo", R.drawable.police);
    }
}
