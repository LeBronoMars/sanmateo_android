package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.materialleanback.MaterialLeanBack;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.GlideHelper;

/**
 * Created by rsbulanon on 6/29/16.
 */
public class IncidentImagesAdapter extends MaterialLeanBack.Adapter<IncidentImagesAdapter.ImagesViewHolder> {

    private ArrayList<String> imagesUrl;
    private Context context;

    public IncidentImagesAdapter(final Context context, final ArrayList<String> imagesUrl) {
        this.imagesUrl = imagesUrl;
        this.context = context;
    }

    @Override
    public int getLineCount() {
        return imagesUrl.size();
    }

    @Override
    public ImagesViewHolder onCreateViewHolder(ViewGroup viewGroup, int row) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R
                                            .layout.row_incident_image_item, viewGroup, false);
        return new ImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImagesViewHolder viewHolder, int i) {
        viewHolder.tvIncidentDesc.setText("Image " + (i+1) + "/" + imagesUrl.size());
        GlideHelper.loadImage(context,imagesUrl.get(i),viewHolder.ivIncidentImage);
    }

    public class ImagesViewHolder extends MaterialLeanBack.ViewHolder {

        @BindView(R.id.ivIncidentImage) ImageView ivIncidentImage;
        @BindView(R.id.tvIncidentDesc) TextView tvIncidentDesc;

        public ImagesViewHolder(final View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
