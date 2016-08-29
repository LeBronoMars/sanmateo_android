package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.dao.LocalOfficial;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnStartDragListener;


/**
 * Created by rsbulanon on 8/23/16.
 */
public class OfficialsRecyclerViewAdapter extends RecyclerView.Adapter<OfficialsRecyclerViewAdapter.OfficialHolder>
                                            implements ItemTouchHelperAdapter {

    private ArrayList<LocalOfficial> officials;
    private final OnStartDragListener mDragStartListener;

    public OfficialsRecyclerViewAdapter(ArrayList<LocalOfficial> officials,OnStartDragListener dragStartListener) {
        this.officials = officials;
        this.mDragStartListener = dragStartListener;
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
        @BindView(R.id.pbLoadImage) ProgressBar pbLoadImage;

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
        holder.pbLoadImage.setVisibility(View.VISIBLE);
        AppConstants.PICASSO.load(official.getPic())
                .placeholder(R.drawable.placeholder_image)
                .centerCrop()
                .fit()
                .into(holder.civPic, new Callback() {
                    @Override
                    public void onSuccess() {
                        LogHelper.log("pic","ON LOAD SUCCESS --> " + official.getFirstName());
                        holder.pbLoadImage.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        LogHelper.log("pic","ON LOAD FAILED --> " + official.getFirstName());
                        holder.pbLoadImage.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (mDragStartListener != null) {
            Collections.swap(officials, fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onItemDismiss(int position) {
        if (mDragStartListener != null) {
            officials.remove(position);
            notifyItemRemoved(position);
        }
    }
}