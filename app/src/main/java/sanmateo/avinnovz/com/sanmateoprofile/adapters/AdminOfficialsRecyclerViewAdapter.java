package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.graphics.Color;
import android.support.v7.widget.CardView;
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
import sanmateo.avinnovz.com.sanmateoprofile.helpers.DaoHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.ItemTouchHelperViewHolder;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnStartDragListener;


/**
 * Created by rsbulanon on 8/23/16.
 */
public class AdminOfficialsRecyclerViewAdapter extends RecyclerView.Adapter<AdminOfficialsRecyclerViewAdapter.OfficialHolder>
                                            implements ItemTouchHelperAdapter {

    private ArrayList<LocalOfficial> officials;
    private final OnStartDragListener mDragStartListener;
    private OnSelectOfficialListener onSelectOfficialListener;

    public AdminOfficialsRecyclerViewAdapter(ArrayList<LocalOfficial> officials, OnStartDragListener dragStartListener) {
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

    public static class OfficialHolder extends RecyclerView.ViewHolder
                            implements ItemTouchHelperViewHolder {

        @BindView(R.id.cvRoot) CardView cvRoot;
        @BindView(R.id.civPic) CircleImageView civPic;
        @BindView(R.id.tvOfficialName) TextView tvOfficialName;
        @BindView(R.id.tvPosition) TextView tvPosition;
        @BindView(R.id.pbLoadImage) ProgressBar pbLoadImage;

        OfficialHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(Color.WHITE);
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
        holder.cvRoot.setOnClickListener(v -> {
            if (onSelectOfficialListener != null) {
                onSelectOfficialListener.onSelectedOfficial(official);
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
            LogHelper.log("swap","from pos --> " + fromPosition + " to pos --> " + toPosition);
            final int fromZIndex = officials.get(fromPosition).getZindex();
            final int toZIndex = officials.get(toPosition).getZindex();
            Collections.swap(officials, fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
            final LocalOfficial fromOfficial = officials.get(fromPosition);
            final LocalOfficial toOfficial = officials.get(toPosition);

            fromOfficial.setZindex(toZIndex);
            toOfficial.setZindex(fromZIndex);

            DaoHelper.updateOfficial(fromOfficial);
            DaoHelper.updateOfficial(toOfficial);
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

    public interface OnSelectOfficialListener {
        void onSelectedOfficial(final LocalOfficial localOfficial);
    }

    public void setOnSelectOfficialListener(OnSelectOfficialListener onSelectOfficialListener) {
        this.onSelectOfficialListener = onSelectOfficialListener;
    }
}