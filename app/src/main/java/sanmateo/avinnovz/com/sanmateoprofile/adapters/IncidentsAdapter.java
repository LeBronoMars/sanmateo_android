package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.ImageFullViewActivity;
import sanmateo.avinnovz.com.sanmateoprofile.activities.IncidentsActivity;
import sanmateo.avinnovz.com.sanmateoprofile.dao.CurrentUser;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.DaoHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Incident;

/**
 * Created by rsbulanon on 6/28/16.
 */
public class IncidentsAdapter extends RecyclerView.Adapter<IncidentsAdapter.ViewHolder> {

    private ArrayList<Incident> incidents;
    private Context context;
    private IncidentsActivity activity;
    private OnShareAndReportListener onShareAndReportListener;
    private CurrentUser currentUser;

    public IncidentsAdapter(final Context context, final ArrayList<Incident> incidents) {
        this.incidents = incidents;
        this.context = context;
        this.activity = (IncidentsActivity) context;
        this.currentUser = DaoHelper.getCurrentUser();
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
        @BindView(R.id.tvTimeAgo) TextView tvTimeAgo;
        @BindView(R.id.tvReportedBy) TextView tvReportedBy;
        @BindView(R.id.civReporterImage) CircleImageView civReporterImage;
        @BindView(R.id.rvImages) RecyclerView rvImages;
        @BindView(R.id.llShareViaFb) LinearLayout llShareViaFb;
        @BindView(R.id.llReport) LinearLayout llReport;

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
            holder.tvDateReported.setText(activity.getSDF().format(calendar.getTime()));
            holder.tvTimeAgo.setText(activity.getPrettyTime().format(calendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            LogHelper.log("err","error in parsing date --> " + e.getMessage());
        }

        AppConstants.PICASSO.load(incident.getReporterPicUrl())
                .placeholder(R.drawable.placeholder_image)
                .centerCrop()
                .fit()
                .into(holder.civReporterImage);

        if (incident.getImages().isEmpty()) {
            holder.rvImages.setVisibility(View.GONE);
        } else {
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

            final IncidentImagesAdapter adapter = new IncidentImagesAdapter(context,incidentImages);
            adapter.setOnSelectImageListener(position -> {
                final Intent intent = new Intent(context, ImageFullViewActivity.class);
                intent.putExtra("incident",incident);
                intent.putExtra("selectedImagePosition",position);
                activity.startActivity(intent);
                activity.animateToLeft(activity);
            });
            holder.rvImages.setAdapter(adapter);
            holder.rvImages.setVisibility(View.VISIBLE);
        }

        holder.llReport.setVisibility(incident.getReporterId() == currentUser.getUserId() ?
                                        View.GONE : View.VISIBLE);

        /** share via facebook */
        holder.llShareViaFb.setOnClickListener(view -> {
            if (onShareAndReportListener != null) {
                onShareAndReportListener.onShare(i);
            }
        });

        /** report */
        holder.llReport.setOnClickListener(view -> {
            if (onShareAndReportListener != null) {
                onShareAndReportListener.onReport(i);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public interface OnShareAndReportListener {
        void onShare(final int position);
        void onReport(final int position);
    }

    public void setOnShareAndReportListener(OnShareAndReportListener onShareAndReportListener) {
        this.onShareAndReportListener = onShareAndReportListener;
    }
}
