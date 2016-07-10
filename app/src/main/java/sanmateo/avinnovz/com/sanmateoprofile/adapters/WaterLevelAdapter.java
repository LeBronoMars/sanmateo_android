package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Announcement;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.WaterLevel;

/**
 * Created by rsbulanon on 6/28/16.
 */
public class WaterLevelAdapter extends RecyclerView.Adapter<WaterLevelAdapter.ViewHolder> {

    private ArrayList<WaterLevel> waterLevels;
    private Context context;
    private BaseActivity activity;

    public WaterLevelAdapter(final Context context, final ArrayList<WaterLevel> waterLevels) {
        this.waterLevels = waterLevels;
        this.context = context;
        this.activity = (BaseActivity) context;
    }

    @Override
    public int getItemCount() {
        return waterLevels.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_water_level, parent, false);
        final ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvWaterLevel) TextView tvWaterLevel;
        @BindView(R.id.tvAlarmLevel) TextView tvAlarmLevel;
        @BindView(R.id.tvDatePosted) TextView tvDatePosted;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {
        final WaterLevel w = waterLevels.get(i);

        holder.tvWaterLevel.setText(w.getWaterLevel()+"");
        holder.tvAlarmLevel.setText(w.getAlert());
        try {
            final Date dateReported = activity.getDateFormatter().parse(w.getCreatedAt());
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateReported);
            calendar.add(Calendar.HOUR_OF_DAY,8);
            holder.tvDatePosted.setText(activity.getSDF().format(calendar.getTime()));
            holder.tvDatePosted.setText(activity.getPrettyTime().format(calendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            LogHelper.log("err","error in parsing date --> " + e.getMessage());
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
