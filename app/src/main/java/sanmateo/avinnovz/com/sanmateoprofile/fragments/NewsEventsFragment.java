package sanmateo.avinnovz.com.sanmateoprofile.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sanmateo.avinnovz.com.sanmateoprofile.R;

/**
 * Created by rsbulanon on 7/6/16.
 */
public class NewsEventsFragment extends Fragment {

    public static NewsEventsFragment newInstance() {
        final NewsEventsFragment fragment = new NewsEventsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_news_events_mgmt,container,false);
        return view;
    }
}
