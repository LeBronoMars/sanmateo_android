package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.dao.LocalGallery;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;

/**
 * Created by ctmanalo on 8/5/16.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder>{

    private Context context;
    private List<LocalGallery> localGalleryList;
//    private List<GalleryPhoto> localGalleryList;
    private BaseActivity baseActivity;
    private int screenWidth;
    private OnGalleryClickListener onGalleryClickListener;

    public GalleryAdapter(Context context, List<LocalGallery> galleryPhotos) {
        this.context = context;
        this.localGalleryList = galleryPhotos;
//        this.localGalleryList = galleryPhotos;
        this.baseActivity = (BaseActivity) context;
        WindowManager wm = (WindowManager) baseActivity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
    }

    @Override
    public int getItemCount() {
        return localGalleryList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.row_gallery, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.ivPhoto) ImageView ivPhoto;
        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.pbLoadImage) ProgressBar pbLoadImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final LocalGallery localGallery = localGalleryList.get(position);

        holder.tvTitle.setText(localGallery.getTitle());
        holder.pbLoadImage.setVisibility(View.VISIBLE);
        LogHelper.log("image_url", "image url >>> " + localGallery.getImageUrl());
        AppConstants.PICASSO.load(localGallery.getImageUrl())
                .placeholder(R.drawable.placeholder_image)
                .fit()
                .into(holder.ivPhoto, new Callback() {
            @Override
            public void onSuccess() {
                holder.pbLoadImage.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                holder.pbLoadImage.setVisibility(View.GONE);
            }
        });
        holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGalleryClickListener.onPhotoSelected(localGallery);
            }
        });
    }

    public interface OnGalleryClickListener {
//        void onPhotoSelected(LocalGallery localGallery);
        void onPhotoSelected(LocalGallery localGallery);
    }

    public void setOnGalleryClickListener(OnGalleryClickListener onGalleryClickListener) {
        this.onGalleryClickListener = onGalleryClickListener;
    }
}
