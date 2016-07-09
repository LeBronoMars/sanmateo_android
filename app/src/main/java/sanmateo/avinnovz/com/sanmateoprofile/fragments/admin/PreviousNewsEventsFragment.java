package sanmateo.avinnovz.com.sanmateoprofile.fragments.admin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
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
import sanmateo.avinnovz.com.sanmateoprofile.adapters.NewsAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.News;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.BusSingleton;

/**
 * Created by rsbulanon on 7/6/16.
 */
public class PreviousNewsEventsFragment extends Fragment {

    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rvNews) RecyclerView rvNews;
    private ArrayList<News> news;

    public static PreviousNewsEventsFragment newInstance(final ArrayList<News> news) {
        final PreviousNewsEventsFragment fragment = new PreviousNewsEventsFragment();
        fragment.news = news;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_news_events_mgmt,container,false);
        ButterKnife.bind(this,view);
        final NewsAdapter newsAdapter = new NewsAdapter(getActivity(),news);
        rvNews.setAdapter(newsAdapter);
        rvNews.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        LogHelper.log("news","MUST HANDLE PREVIOUS ---> " + map.get("action").toString());
        if (map.get("action").equals("previous")) {
            LogHelper.log("news","AAAAA");
            final ArrayList<News> news = (ArrayList<News>)map.get("result");
            this.news.addAll(news);
            LogHelper.log("news","PREVIOUS ----> " + news.size());
            rvNews.getAdapter().notifyDataSetChanged();
        } else {
            LogHelper.log("news","BBBB");
        }
    }
}