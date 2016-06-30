package sanmateo.avinnovz.com.sanmateoprofile.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.GlideHelper;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by rsbulanon on 6/30/16.
 */
public class ImageFullViewFragment extends Fragment {

    @BindView(R.id.ivFullImage) ImageView ivFullImage;
    private Context context;
    private String url;

    public static ImageFullViewFragment newInstance(final Context context,final String url) {
        final ImageFullViewFragment fragment = new ImageFullViewFragment();
        fragment.context = context;
        fragment.url = url;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_image_full_view,container,false);
        ButterKnife.bind(this,view);
        GlideHelper.loadImage(context,url,ivFullImage);
        return view;
    }
}
