package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;


/**
 * Created by rsbulanon on 6/30/16.
 */
public class IncidentImagesAdapter extends RecyclerView.Adapter<IncidentImagesAdapter.ImageViewHolder> {

    private ArrayList<String> imageUrls;
    private Context context;
    private OnSelectImageListener onSelectImageListener;

    public IncidentImagesAdapter(final Context context,final ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
        this.context = context;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_incident_image_item, parent, false);
        ImageViewHolder holder = new ImageViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, final int position) {
        if (!imageUrls.get(position).isEmpty()) {
            AppConstants.PICASSO.load(imageUrls.get(position))
                    .placeholder(R.drawable.placeholder_image)
                    .fit().centerCrop().into(holder.ivGallery);
            holder.ivGallery.setOnClickListener(view -> {
                if (onSelectImageListener != null) {
                    onSelectImageListener.onSelectedImage(position);
                }
            });
            holder.ivGallery.setVisibility(View.VISIBLE);
        } else {
            holder.ivGallery.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivGallery) ImageView ivGallery;

        public ImageViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    public interface OnSelectImageListener {
        void onSelectedImage(final int position);
    }

    public void setOnSelectImageListener(OnSelectImageListener onSelectImageListener) {
        this.onSelectImageListener = onSelectImageListener;
    }
}
