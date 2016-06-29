package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;

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
    public int getCellsCount(int row) {
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
        //GlideHelper.loadImage(context,imagesUrl.get(i),viewHolder.ivIncidentImage);
    }

    @Override
    public String getTitleForRow(int row) {
        return "Line " + row;
    }

    //region customView
    @Override
    public RecyclerView.ViewHolder getCustomViewForRow(ViewGroup viewgroup, int row) {
        if (row == 3) {
            final View view = LayoutInflater.from(viewgroup.getContext()).inflate(R.layout.row_incident_item_header, viewgroup, false);
            return new RecyclerView.ViewHolder(view) {
            };
        } else
            return null;
    }

    @Override
    public boolean isCustomView(int row) {
        return row == 3;
    }

    @Override
    public void onBindCustomView(RecyclerView.ViewHolder viewHolder, int row) {
        super.onBindCustomView(viewHolder, row);
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
