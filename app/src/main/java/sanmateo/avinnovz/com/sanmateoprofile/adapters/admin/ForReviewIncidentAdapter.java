package sanmateo.avinnovz.com.sanmateoprofile.adapters.admin;

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
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.activities.ImageFullViewActivity;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.IncidentImagesAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.ForReviewIncident;

/**
 * Created by rsbulanon on 6/28/16.
 */
public class ForReviewIncidentAdapter extends RecyclerView.Adapter<ForReviewIncidentAdapter.ViewHolder> {

    private ArrayList<ForReviewIncident> incidents;
    private Context context;
    private BaseActivity activity;
    private OnReportListener onReportListener;

    public ForReviewIncidentAdapter(final Context context, final ArrayList<ForReviewIncident> incidents) {
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
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .row_for_review_incident, parent, false);
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
        @BindView(R.id.civMaliciousReportBy) CircleImageView civMaliciousReportBy;
        @BindView(R.id.rvImages) RecyclerView rvImages;
        @BindView(R.id.llBlock) LinearLayout llBlock;
        @BindView(R.id.llApprove) LinearLayout llApprove;
        @BindView(R.id.llUnblockReport) LinearLayout llUnblockReport;
        @BindView(R.id.llApproveBlock) LinearLayout llApproveBlock;
        @BindView(R.id.tvMaliciousReportBy) TextView tvMaliciousReportBy;
        @BindView(R.id.tvRemarks) TextView tvRemarks;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {
        final ForReviewIncident incident = incidents.get(i);
        holder.tvDescription.setText(incident.getIncidentDescription());
        holder.tvAddress.setText(incident.getIncidentAddress());
        holder.tvReportedBy.setText(incident.getReporterName());
        try {
            final Date dateReported = activity.getDateFormatter().parse(incident.getCreatedAt());
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateReported);
            calendar.add(Calendar.HOUR_OF_DAY,8);
            holder.tvDateReported.setText(activity.getSDF().format(calendar.getTime()));
            holder.tvTimeAgo.setText(activity.getPrettyTime().format(calendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            LogHelper.log("err","error in parsing date --> " + e.getMessage());
        }

        /** load profile pic of user who created the incident report */
        AppConstants.PICASSO.load(incident.getUploaderPicUrl())
                .placeholder(R.drawable.placeholder_image)
                .centerCrop()
                .fit()
                .into(holder.civReporterImage);

        /** load profile pic of user who files malicious report about the incident */
        AppConstants.PICASSO.load(incident.getReporterPicUrl())
                .placeholder(R.drawable.placeholder_image)
                .centerCrop()
                .fit()
                .into(holder.civMaliciousReportBy);

        if (incident.getIncidentImages().isEmpty()) {
            holder.rvImages.setVisibility(View.GONE);
        } else {
            holder.rvImages.setVisibility(View.VISIBLE);
            final ArrayList<String> incidentImages = new ArrayList<>();
            /**
             * if incident images contains '||' which acts as the delimiter
             * split incident.getImages() using '||' to get the list of image urls
             * */
            if (incident.getIncidentImages().contains("###")) {
                incidentImages.addAll(Arrays.asList(incident.getIncidentImages().split("###")));
            } else {
                incidentImages.add(incident.getIncidentImages());
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
        }

        holder.llBlock.setOnClickListener(view -> {
            if (onReportListener != null) {
                onReportListener.onUpdateReport(i,"Block");
            }
        });
        holder.llApprove.setOnClickListener(view -> {
            if (onReportListener != null) {
                onReportListener.onUpdateReport(i,"Approve");
            }
        });
        holder.llUnblockReport.setOnClickListener(view -> {
            if (onReportListener != null) {
                onReportListener.onUpdateReport(i,"Disapprove");
            }
        });
        holder.tvMaliciousReportBy.setText(incident.getReporterName());
        holder.tvRemarks.setText(incident.getRemarks());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public interface OnReportListener {
        void onUpdateReport(int index, String action);
    }

    public void setOnReportListener(OnReportListener onReportListener) {
        this.onReportListener = onReportListener;
    }
}
