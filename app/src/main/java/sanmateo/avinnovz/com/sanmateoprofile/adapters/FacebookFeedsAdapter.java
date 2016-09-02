package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.models.others.FacebookFeeds;

/**
 * Created by rsbulanon on 6/28/16.
 */
public class FacebookFeedsAdapter extends RecyclerView.Adapter<FacebookFeedsAdapter.ViewHolder> {

    private ArrayList<FacebookFeeds> feeds;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ssZ");
    private SimpleDateFormat readableDate = new SimpleDateFormat("EEE, MMM dd, yyyy hh:mm a");

    public FacebookFeedsAdapter(final ArrayList<FacebookFeeds> feeds) {
        this.feeds = feeds;
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_fb_feeds, parent, false);
        final ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvMessage) TextView tvMessage;
        @BindView(R.id.tvDatePosted) TextView tvDatePosted;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {
        final FacebookFeeds f = feeds.get(i);
        holder.tvMessage.setText(f.getMessage());
        try {
            holder.tvDatePosted.setText(readableDate.format(sdf.parse(f.getDatePosted())));
        } catch (ParseException e) {
            LogHelper.log("fb","unable to parse date --> " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
