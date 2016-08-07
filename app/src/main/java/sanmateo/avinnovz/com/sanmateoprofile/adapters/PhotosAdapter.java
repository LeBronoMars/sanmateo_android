package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Photo;


/**
 * Created by rsbulanon on 6/30/16.
 */
public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ImageViewHolder> {

    private ArrayList<Photo> photos;
    private Context context;
    private OnSelectImageListener onSelectImageListener;

    public PhotosAdapter(final Context context, final ArrayList<Photo> photos) {
        this.photos = photos;
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
        AppConstants.PICASSO.load(photos.get(position).getImageUrl())
                .placeholder(R.drawable.placeholder_image)
                .fit().centerCrop().into(holder.ivPhoto);
        holder.ivPhoto.setOnClickListener(v -> {
            if (onSelectImageListener != null) {
                onSelectImageListener.onSelectedImage(position);
            }
        });
        holder.tvTitle.setText(photos.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivPhoto) ImageView ivPhoto;
        @BindView(R.id.tvTitle) TextView tvTitle;

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
