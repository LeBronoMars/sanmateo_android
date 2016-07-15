package sanmateo.avinnovz.com.sanmateoprofile.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.MapActivity;
import sanmateo.avinnovz.com.sanmateoprofile.activities.NewMapActivty;

/**
 * Created by ctmanalo on 7/6/16.
 */
public class MapMunicipal extends Fragment implements GoogleMap.OnMarkerClickListener{

    private NewMapActivty activity;
    public SupportMapFragment mapMunicipal;

    public static MapMunicipal newInstance() {
        return new MapMunicipal();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (NewMapActivty) getActivity();
        View view = inflater.inflate(R.layout.map_municipal, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        mapMunicipal = (SupportMapFragment)fm.findFragmentById(R.id.mapContainer);
        if (mapMunicipal == null) {
            mapMunicipal = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.mapContainer, mapMunicipal).commit();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        mapMunicipal.getMap().setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mapMunicipal.getMap().getUiSettings().setScrollGesturesEnabled(true);
        LatLng latLng = new LatLng( 14.696123, 121.117771);
        mapMunicipal.getMap().moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mapMunicipal.getMap().animateCamera(CameraUpdateFactory.zoomTo(11));
        mapMunicipal.getMap().setOnMarkerClickListener(this);
        activity.addMapMarker(mapMunicipal.getMap(), 14.696123, 121.117771,
                "Municipal Hall", "San Mateo",R.drawable.hall);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
//        activity.showToast(marker.getTitle() + " " + marker.getSnippet());
        MapMarkerFragment fragment = MapMarkerFragment.newInstance();
        fragment.show(activity.getFragmentManager(), "municipal map details");
        return false;
    }
}
