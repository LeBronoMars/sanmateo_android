package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;


/**
 * Created by rsbulanon on 6/30/16.
 */
public class FileToUploadAdapter extends RecyclerView.Adapter<FileToUploadAdapter.ImageViewHolder> {

    private ArrayList<File> files;
    private Context context;
    private OnSelectImageListener onSelectImageListener;

    public FileToUploadAdapter(final Context context, final ArrayList<File> files) {
        this.files = files;
        this.context = context;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_files_to_upload, parent, false);
        ImageViewHolder holder = new ImageViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, final int position) {
        AppConstants.PICASSO.load(files.get(position)).fit().centerCrop().into(holder.ivImage);
        holder.ivRemove.setOnClickListener(new View.OnClickListener() {
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
        return files.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivImage) ImageView ivImage;
        @BindView(R.id.ivRemove) ImageView ivRemove;

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
