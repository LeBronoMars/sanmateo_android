package sanmateo.avinnovz.com.sanmateoprofile.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import sanmateo.avinnovz.com.sanmateoprofile.R;


/**
 * Created by rsbulanon on 6/26/16.
 */
public class BannerFragment extends Fragment {

    private Drawable drawable;

    public static BannerFragment newInstance(final Drawable drawable) {
        final BannerFragment bannerFragment = new BannerFragment();
        bannerFragment.drawable = drawable;
        return bannerFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_banner, container, false);
        final ImageView imageView = (ImageView)view.findViewById(R.id.ivImage);
        imageView.setImageDrawable(drawable);
        return view;
    }
}
