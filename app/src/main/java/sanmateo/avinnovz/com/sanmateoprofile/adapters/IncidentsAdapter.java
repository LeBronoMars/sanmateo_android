package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
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

        @BindView(R.id.tvAddress) TextView tvAddress;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {
        final Incident incident = incidents.get(i);
        holder.tvAddress.setText(incident.getIncidentAddress());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
