package sanmateo.avinnovz.com.sanmateoprofile.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.activities.MapActivity;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.LocationsAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.models.others.Location;

/**
 * Created by ctmanalo on 7/19/16.
 */
public class MapLocations extends Fragment {

    @BindView(R.id.rvLocations) RecyclerView rvLocations;
    private MapActivity activity;
    private ArrayList<Location> locations;

    public static MapLocations newInstance(final ArrayList<Location> locations) {
        MapLocations fragment = new MapLocations();
        fragment.locations = locations;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (MapActivity) getActivity();
        View view = inflater.inflate(R.layout.map_locations, container, false);
        ButterKnife.bind(this, view);
        initRecycler();
        return view;
    }

    private void initRecycler() {
        LocationsAdapter adapter = new LocationsAdapter(activity, locations);
        rvLocations.setAdapter(adapter);
        rvLocations.setLayoutManager(new LinearLayoutManager(activity));
    }


}
