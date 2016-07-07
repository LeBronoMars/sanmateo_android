package sanmateo.avinnovz.com.sanmateoprofile.fragments.admin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.News;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.BusSingleton;

/**
 * Created by rsbulanon on 7/6/16.
 */
public class NewsEventsFragment extends Fragment {

    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rvNews) RecyclerView rvNews;
    private String title;
    private ArrayList<News> news;

    public static NewsEventsFragment newInstance(final String title,final ArrayList<News> news) {
        final NewsEventsFragment fragment = new NewsEventsFragment();
        fragment.title = title;
        fragment.news = news;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_news_events_mgmt,container,false);
        ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void onResume() {
        BusSingleton.getInstance().register(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        BusSingleton.getInstance().unregister(this);
        super.onPause();
    }

    @Subscribe
    public void handleBusEvent(final HashMap<String,Object> map) {
        if (map.get("action").equals("refresh")) {
            if (title.equals("Today")) {

            } else {

            }
        }
    }
}
