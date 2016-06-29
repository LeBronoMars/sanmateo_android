package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.florent37.materialleanback.MaterialLeanBack;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.GlideHelper;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Incident;

/**
 * Created by rsbulanon on 6/28/16.
 */
public class IncidentsAdapter extends RecyclerView.Adapter<IncidentsAdapter.ViewHolder> {

    private ArrayList<Incident> incidents;
    private Context context;
    private BaseActivity activity;

    public IncidentsAdapter(final Context context, final ArrayList<Incident> incidents) {
        this.incidents = incidents;
        this.activity = (BaseActivity) context;
    }

    @Override
    public int getItemCount() {
        return incidents.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_incident, parent, false);
        final ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.mlbImages) MaterialLeanBack mlbImages;
        @BindView(R.id.tvDescription) TextView tvDescription;
        @BindView(R.id.tvAddress) TextView tvAddress;
        @BindView(R.id.tvReportedBy) TextView tvReportedBy;
        @BindView(R.id.civReporterImage) CircleImageView civReporterImage;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {
        final Incident incident = incidents.get(i);
        holder.tvDescription.setText(incident.getIncidentDescription());
        holder.tvAddress.setText(incident.getIncidentAddress());
        holder.tvReportedBy.setText(incident.getReporterName());
        final ArrayList<String> incidentImages = new ArrayList<>();
        /**
         * if incident images contains '||' which acts as the delimiter
         * split incident.getImages() using '||' to get the list of image urls
         * */
        if (incident.getImages().contains("||")) {
            incidentImages.addAll(Arrays.asList(incident.getImages().split("||")));
        } else {
            incidentImages.add(incident.getImages());
        }
        final IncidentImagesAdapter adapter = new IncidentImagesAdapter(context,incidentImages);
        GlideHelper.loadImage(context,incident.getReporterPicUrl(),holder.civReporterImage);
        //holder.mlbImages.setAdapter(adapter);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
