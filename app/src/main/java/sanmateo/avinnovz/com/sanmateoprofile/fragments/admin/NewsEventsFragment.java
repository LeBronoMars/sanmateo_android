package sanmateo.avinnovz.com.sanmateoprofile.fragments.admin;

import android.content.Intent;
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
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.activities.NewsFullPreviewActivity;
import sanmateo.avinnovz.com.sanmateoprofile.activities.admin.NewsEventsManagementActivity;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.NewsAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.News;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.BusSingleton;

/**
 * Created by rsbulanon on 7/6/16.
 */
public class NewsEventsFragment extends Fragment {

    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rvNews) RecyclerView rvNews;
    private ArrayList<News> news;
    private BaseActivity activity;

    public static NewsEventsFragment newInstance(final ArrayList<News> news) {
        final NewsEventsFragment fragment = new NewsEventsFragment();
        fragment.news = news;
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_news_events_mgmt,container,false);
        ButterKnife.bind(this,view);
        activity = (BaseActivity)getActivity();
        final NewsAdapter newsAdapter = new NewsAdapter(getActivity(),news);
        rvNews.setAdapter(newsAdapter);
        newsAdapter.setOnSelectNewsListener(new NewsAdapter.OnSelectNewsListener() {
            @Override
            public void onSelectedNews(News n) {
                final Intent intent = new Intent(getActivity(), NewsFullPreviewActivity.class);
                intent.putExtra("news",n);
                startActivity(intent);
                activity.animateToLeft(activity);
            }
        });
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
        if (map.containsKey("action")) {
            LogHelper.log("news","contains action");
            if (map.get("action").equals("today")) {
                final ArrayList<News> news = (ArrayList<News>)map.get("result");
                this.news.addAll(news);
            } else if (map.get("action").equals(AppConstants.ACTION_POST_NEWS)) {
                this.news.add(0,(News)map.get("result"));
            }
            rvNews.getAdapter().notifyDataSetChanged();
        } else {
            LogHelper.log("news","not containing action key");
        }
    }
}
