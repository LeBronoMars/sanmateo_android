package sanmateo.avinnovz.com.sanmateoprofile.customviews;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;

/**
 * Created by ctmanalo on 9/1/16.
 */
public class ImageViewHolder extends ChildViewHolder {

    public ImageView ivImage;
    public ProgressBar pbLoadImage;

    public ImageViewHolder(View itemView) {
        super(itemView);
        ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
        pbLoadImage = (ProgressBar) itemView.findViewById(R.id.pbLoadImage);
    }
}
