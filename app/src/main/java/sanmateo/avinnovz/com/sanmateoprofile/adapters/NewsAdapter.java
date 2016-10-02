package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import sanmateo.avinnovz.com.sanmateoprofile.activities.IncidentsActivity;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Incident;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.News;

/**
 * Created by rsbulanon on 6/28/16.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<News> news;
    private Context context;
    private BaseActivity activity;
    private OnSelectNewsListener onSelectNewsListener;

    public NewsAdapter(final Context context, final ArrayList<News> news) {
        this.news = news;
        this.context = context;
        this.activity = (BaseActivity) context;
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_news, parent, false);
        final ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.llNews) LinearLayout llNews;
        @BindView(R.id.ivImageUrl) ImageView ivImageUrl;
        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tvReportedBy) TextView tvReportedBy;
        @BindView(R.id.tvDateReported) TextView tvDateReported;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {
        final News n = news.get(i);
        holder.tvTitle.setText(n.getTitle());
        holder.tvReportedBy.setText(n.getReportedBy());
        try {
            final Date dateReported = activity.getDateFormatter().parse(n.getCreatedAt());
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateReported);
            calendar.add(Calendar.HOUR_OF_DAY,8);
            holder.tvDateReported.setText(activity.getSDF().format(calendar.getTime()));
            holder.tvDateReported.setText(activity.getPrettyTime().format(calendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            LogHelper.log("err","error in parsing date --> " + e.getMessage());
        }
        AppConstants.PICASSO.load(n.getImageUrl())
                .placeholder(R.drawable.placeholder_image)
                .centerCrop()
                .fit()
                .into(holder.ivImageUrl);

        holder.llNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onSelectNewsListener != null) {
                    onSelectNewsListener.onSelectedNews(n);
                }
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public interface OnSelectNewsListener {
        void onSelectedNews(final News n);
    }

    public void setOnSelectNewsListener(OnSelectNewsListener onSelectNewsListener) {
        this.onSelectNewsListener = onSelectNewsListener;
    }
}
