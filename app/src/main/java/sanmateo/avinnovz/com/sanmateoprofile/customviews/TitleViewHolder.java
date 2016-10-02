package sanmateo.avinnovz.com.sanmateoprofile.customviews;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;

/**
 * Created by ctmanalo on 9/1/16.
 */
public class TitleViewHolder extends ParentViewHolder {

    private static final float INITIAL_POSITION = 0.0f;
    private static final float ROTATED_POSITION = 180f;

    public TextView tvTitle;
    public ImageView ivArrow;

    public TitleViewHolder(View itemView) {
        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        ivArrow = (ImageView) itemView.findViewById(R.id.ivArrow);
    }

    @Override
    public void setExpanded(boolean isExpanded) {
        super.setExpanded(isExpanded);
        if (isExpanded) {
            ivArrow.setRotation(ROTATED_POSITION);
        } else {
            ivArrow.setRotation(INITIAL_POSITION);
        }
    }
}
