package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.HotlinesActivity;
import sanmateo.avinnovz.com.sanmateoprofile.customviews.ContactView;
import sanmateo.avinnovz.com.sanmateoprofile.customviews.OverflowLayout;
import sanmateo.avinnovz.com.sanmateoprofile.models.others.Hotlines;


/**
 * Created by rsbulanon on 5/14/15.
 */
public class HotlinesAdapter extends RecyclerView.Adapter<HotlinesAdapter.ContactHolder> {

    private ArrayList<Hotlines> hotlines;
    private Context context;
    private HotlinesActivity activity;
    private LayoutInflater inflater;

    public HotlinesAdapter(Context context, ArrayList<Hotlines> hotlines) {
        this.context = context;
        this.hotlines = hotlines;
        this.inflater = LayoutInflater.from(context);
        this.activity = (HotlinesActivity) context;
    }

    @Override
    public int getItemCount() {
        return hotlines.size();
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_card_hotline, parent, false);
        ContactHolder holder = new ContactHolder(v);
        return holder;
    }

    public static class ContactHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView tvHeader;
        TextView tvSubheader;
        OverflowLayout llOverFlow;

        ContactHolder(View view) {
            super(view);
            cv = (CardView)view.findViewById(R.id.cardView);
            tvHeader = (TextView)view.findViewById(R.id.tvHeader);
            tvSubheader = (TextView)view.findViewById(R.id.tvSubheader);
            llOverFlow = (OverflowLayout)view.findViewById(R.id.llOverFlow);
        }
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, final int i) {
        holder.tvHeader.setText(hotlines.get(i).getHeader());
        holder.tvSubheader.setText(hotlines.get(i).getSubHeader());
        ArrayList<String> no = hotlines.get(i).getNumbers();
        for (String s : no) {
            ContactView cv = new ContactView(context,s);
            holder.llOverFlow.addView(cv);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}