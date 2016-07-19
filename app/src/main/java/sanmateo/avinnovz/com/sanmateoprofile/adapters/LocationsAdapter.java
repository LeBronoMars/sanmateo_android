package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.activities.MapActivity;
import sanmateo.avinnovz.com.sanmateoprofile.models.others.Location;

/**
 * Created by ctmanalo on 7/19/16.
 */
public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.ViewHolder>{

    private ArrayList<Location> locations;
    private Context context;
    private MapActivity activity;

    public LocationsAdapter(final Context context, final ArrayList<Location> locations) {
        this.context = context;
        this.activity = (MapActivity) context;
        this.locations = locations;
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.row_locations, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.llLocation) LinearLayout llLocation;
        @BindView(R.id.rlViewImage) RelativeLayout rlImageView;
        @BindView(R.id.tvLocationName) TextView tvLocationName;
        @BindView(R.id.tvCategory) TextView tvCategory;
        @BindView(R.id.tvLocationAddress) TextView tvLocationAddress;
        @BindView(R.id.tvContactNo) TextView tvContactNo;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Location location = locations.get(position);
        holder.tvLocationName.setText(location.getLocationName());
        holder.tvCategory.setText(location.getCategory());
        holder.tvLocationAddress.setText(location.getLocationAddress());
        holder.tvContactNo.setText(location.getContactNo());
        holder.rlImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!location.getImageUrl().equals("")) {
                    activity.showImage(location.getImageUrl());
                } else {
                    activity.showToast("No image found...");
                }
            }
        });
        holder.llLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (location.getLatLng() != null) {
                    activity.focusOnMap(location.getLatLng());
                } else {
                    activity.showToast("Cannot find location on map...");
                }
            }
        });
    }

}
