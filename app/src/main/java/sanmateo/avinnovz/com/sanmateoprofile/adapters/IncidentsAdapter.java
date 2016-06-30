package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.GlideHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
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
        this.context = context;
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

        @BindView(R.id.tvDescription) TextView tvDescription;
        @BindView(R.id.tvAddress) TextView tvAddress;
        @BindView(R.id.tvDateReported) TextView tvDateReported;
        @BindView(R.id.tvReportedBy) TextView tvReportedBy;
        @BindView(R.id.civReporterImage) CircleImageView civReporterImage;
        @BindView(R.id.rvImages) RecyclerView rvImages;

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
        try {
            final Date dateReported = activity.getDateFormatter().parse(incident.getIncidentDateReported());
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateReported);
            calendar.add(Calendar.HOUR_OF_DAY,8);
            holder.tvDateReported.setText(activity.getSDF().format(calendar.getTime()) + " - " +
                        activity.getPrettyTime().format(calendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            LogHelper.log("err","error in parsing date --> " + e.getMessage());
        }
        final ArrayList<String> incidentImages = new ArrayList<>();
        /**
         * if incident images contains '||' which acts as the delimiter
         * split incident.getImages() using '||' to get the list of image urls
         * */
        if (incident.getImages().contains("###")) {
            incidentImages.addAll(Arrays.asList(incident.getImages().split("###")));
        } else {
            incidentImages.add(incident.getImages());
        }

        for (String s : incidentImages) {
            LogHelper.log("img","URLS ---> " + s);
        }

        GlideHelper.loadImage(context,incident.getReporterPicUrl(),holder.civReporterImage);
        final IncidentImagesAdapter adapter = new IncidentImagesAdapter(context,incidentImages);
        holder.rvImages.setAdapter(adapter);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
