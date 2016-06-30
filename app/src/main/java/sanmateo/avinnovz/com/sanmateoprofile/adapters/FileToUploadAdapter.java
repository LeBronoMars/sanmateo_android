package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.GlideHelper;


/**
 * Created by rsbulanon on 6/30/16.
 */
public class FileToUploadAdapter extends RecyclerView.Adapter<FileToUploadAdapter.ImageViewHolder> {

    private ArrayList<Drawable> drawables;
    private Context context;
    private OnSelectImageListener onSelectImageListener;

    public FileToUploadAdapter(final Context context, final ArrayList<Drawable> drawables) {
        this.drawables = drawables;
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
        holder.ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onSelectImageListener != null) {
                    onSelectImageListener.onSelectedImage(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return drawables.size();
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
