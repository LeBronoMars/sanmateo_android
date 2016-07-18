package sanmateo.avinnovz.com.sanmateoprofile.activities.admin;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.adapter.rxjava.HttpException;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.TabAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.admin.CreateNewsDialogFragment;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.admin.NewsEventsFragment;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.admin.PreviousNewsEventsFragment;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiErrorHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiRequestHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.ApiError;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.News;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.BusSingleton;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.CurrentUserSingleton;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.NewsSingleton;

/**
 * Created by rsbulanon on 7/6/16.
 */
public class NewsEventsManagementActivity extends BaseActivity implements OnApiRequestListener {

    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.btnCreateNews) FloatingActionButton btnCreateNews;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<String> tabNames = new ArrayList<>();
    private ApiRequestHelper apiRequestHelper;
    private CurrentUserSingleton currentUserSingleton;
    private NewsSingleton newsSingleton;
    private String token;
    private ArrayList<News> newsToday = new ArrayList<>();
    private ArrayList<News> newPrevious = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_event_management);
        ButterKnife.bind(this);
        apiRequestHelper = new ApiRequestHelper(this);
        currentUserSingleton = CurrentUserSingleton.newInstance();
        newsSingleton = NewsSingleton.getInstance();
        token = currentUserSingleton.getCurrentUser().getToken();
        initViewPager();

        if (newsSingleton.getNewsToday().size() == 0) {
            apiRequestHelper.getNews(token,0,10,"active","today");
        } else {
            newsToday.addAll(newsSingleton.getNewsToday());
            broadcastUpdate("today",newsToday);
        }

        if (newsSingleton.getNewsPrevious().size() == 0) {
            apiRequestHelper.getNews(token,0,10,"active","previous");
        } else {
            newPrevious.addAll(newsSingleton.getNewsPrevious());
            broadcastUpdate("previous",newPrevious);
        }
        setToolbarTitle("News & Events");
    }

    private void initViewPager() {
        /** set up viewpager and tab layout */
        tabNames.add("Today");
        tabNames.add("Previous");

        fragments.add(NewsEventsFragment.newInstance(newsToday));
        fragments.add(PreviousNewsEventsFragment.newInstance(newPrevious));

        viewPager.setAdapter(new TabAdapter(getSupportFragmentManager(), fragments, tabNames));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);
    }

    @Override
    public void onApiRequestBegin(String action) {
        LogHelper.log("news","start to fetch ---> " + action);
        if (action.equals("today") || action.equals("today")) {
            showCustomProgress("Fetching news, Please wait...");
        } else if (action.equals(AppConstants.ACTION_POST_NEWS)) {
            showCustomProgress("Creating news, Please wait...");
        }
    }

    @Override
    public void onApiRequestSuccess(String action, Object result) {
        dismissCustomProgress();
        if (action.equals(AppConstants.ACTION_POST_NEWS)) {
            LogHelper.log("news","new created!");
            final News n = (News)result;
            if (!newsSingleton.isNewsExisting(n.getId())) {
                newsSingleton.getNewsToday().add(0,n);
            }
            final HashMap<String,Object> map = new HashMap<>();
            map.put("action",action);
            map.put("result",n);
            BusSingleton.getInstance().post(map);
        } else {
            final ArrayList<News> n = (ArrayList<News>)result;
            if (action.equals("today")) {
                newsSingleton.getNewsToday().addAll(n);
            } else if (action.equals("previous")) {
                newsSingleton.getNewsPrevious().addAll(n);
            }
            broadcastUpdate(action,n);
        }
    }

    @Override
    public void onApiRequestFailed(String action, Throwable t) {
        dismissCustomProgress();
        LogHelper.log("err","error in ---> " + action + " cause ---> " + t.getMessage());
        if (t instanceof HttpException) {
            final ApiError apiError = ApiErrorHelper.parseError(((HttpException) t).response());
            if (action.equals(AppConstants.ACTION_LOGIN)) {
                showConfirmDialog(action,"Login Failed", apiError.getMessage(),"Close","",null);
            } else {
                showConfirmDialog(action,"Unable to create news", apiError.getMessage(),"Close","",null);
            }
        }
    }

    private void broadcastUpdate(final String action, final ArrayList<News> news) {
        LogHelper.log("news","must broadcast ---> " + action + " size --> " + news.size());
        final HashMap<String,Object> map = new HashMap<>();
        map.put("action",action);
        map.put("result",news);
        BusSingleton.getInstance().post(map);
    }

    @OnClick(R.id.btnCreateNews)
    public void createNews() {
        final CreateNewsDialogFragment fragment = CreateNewsDialogFragment.newInstance();
        fragment.setOnCreateNewsListener(new CreateNewsDialogFragment.OnCreateNewsListener() {

            @Override
            public void onCreateNews(String title, String body, String sourceUrl, String imageUrl, String reportedBy) {
                fragment.dismiss();
                if (isNetworkAvailable()) {
                    apiRequestHelper.createNews(token,title,body,sourceUrl,imageUrl,reportedBy);
                } else {
                    showSnackbar(btnCreateNews, AppConstants.WARN_CONNECTION);
                }
            }

            @Override
            public void onCancel() {
                fragment.dismiss();
            }
        });
        fragment.show(getFragmentManager(),"create news");
    }
}
