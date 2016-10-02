package sanmateo.avinnovz.com.sanmateoprofile.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;

/**
 * Created by rsbulanon on 6/30/16.
 */
public class ImageFullViewFragment extends Fragment {

    @BindView(R.id.ivFullImage) ImageView ivFullImage;
    private String url;

    public static ImageFullViewFragment newInstance(final String url) {
        final ImageFullViewFragment fragment = new ImageFullViewFragment();
        fragment.url = url;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_image_full_view,container,false);
        ButterKnife.bind(this,view);
        AppConstants.PICASSO.load(url).placeholder(R.drawable.placeholder_image)
                .centerCrop().fit().into(ivFullImage);
        return view;
    }
}
