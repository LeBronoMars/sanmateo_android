package sanmateo.avinnovz.com.sanmateoprofile.fragments;

import android.support.v4.app.Fragment;

/**
 * Created by ctmanalo on 8/31/16.
 */
public class BannerFromUrlFragment extends Fragment {

    private String url;

    public static BannerFromUrlFragment newInstance(final String url) {
        final BannerFromUrlFragment fragment = new BannerFromUrlFragment();
        fragment.url = url;
        return fragment;
    }
}
