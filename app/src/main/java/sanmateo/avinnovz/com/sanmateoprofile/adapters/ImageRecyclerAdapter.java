package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.squareup.picasso.Callback;

import java.util.List;

import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.customviews.ImageViewHolder;
import sanmateo.avinnovz.com.sanmateoprofile.customviews.TitleViewHolder;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.models.others.ImageUrl;
import sanmateo.avinnovz.com.sanmateoprofile.models.others.ImageUrlChild;
import sanmateo.avinnovz.com.sanmateoprofile.models.others.ImageUrlParent;

/**
 * Created by ctmanalo on 9/1/16.
 */
public class ImageRecyclerAdapter extends ExpandableRecyclerAdapter<TitleViewHolder, ImageViewHolder> {

    private LayoutInflater inflater;

    public ImageRecyclerAdapter(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public TitleViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.row_title, viewGroup, false);
        return new TitleViewHolder(view);
    }

    @Override
    public ImageViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.row_image, viewGroup, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(TitleViewHolder titleViewHolder, int i, Object o) {
        ImageUrlParent imageUrl = (ImageUrlParent) o;
        titleViewHolder.tvTitle.setText(imageUrl.getTitle());
    }

    @Override
    public void onBindChildViewHolder(ImageViewHolder imageViewHolder, int i, Object o) {
        ImageUrlChild imageUrlChild = (ImageUrlChild) o;
        AppConstants.PICASSO.load(imageUrlChild.getImageUrl())
                .placeholder(R.drawable.placeholder_image)
                .fit()
                .into(imageViewHolder.ivImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        imageViewHolder.pbLoadImage.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        imageViewHolder.pbLoadImage.setVisibility(View.GONE);
                    }
                });
    }
}
