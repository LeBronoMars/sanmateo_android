package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.dao.LocalOfficial;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;


/**
 * Created by rsbulanon on 8/23/16.
 */
public class OfficialsRecyclerViewAdapter extends RecyclerView.Adapter<OfficialsRecyclerViewAdapter.OfficialHolder> {

    private ArrayList<LocalOfficial> officials;

    public OfficialsRecyclerViewAdapter(ArrayList<LocalOfficial> officials) {
        this.officials = officials;
    }

    @Override
    public int getItemCount() {
        return officials.size();
    }

    @Override
    public OfficialHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_official, parent, false);
        return new OfficialHolder(v);
    }

    public static class OfficialHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.civPic) CircleImageView civPic;
        @BindView(R.id.tvOfficialName) TextView tvOfficialName;
        @BindView(R.id.tvPosition) TextView tvPosition;

        OfficialHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    @Override
    public void onBindViewHolder(OfficialHolder holder, final int i) {
        final LocalOfficial official = officials.get(i);

        final String nickName = official.getNickName().isEmpty() ? " " : " '"+official.getNickName()+"' ";
        holder.tvOfficialName.setText(official.getFirstName() + nickName + official.getLastName());
        holder.tvPosition.setText(official.getPosition());
        LogHelper.log("officials","name --> " + official.getFirstName());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}