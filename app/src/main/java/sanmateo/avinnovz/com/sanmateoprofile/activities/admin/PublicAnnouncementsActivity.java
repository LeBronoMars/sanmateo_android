package sanmateo.avinnovz.com.sanmateoprofile.activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.squareup.otto.Subscribe;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.adapter.rxjava.HttpException;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.AnnouncementsAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.admin.CreateAnnouncementDialogFragment;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiErrorHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiRequestHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.PrefsHelper;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.EndlessRecyclerViewScrollListener;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Announcement;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.ApiError;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.AnnouncementsSingleton;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.CurrentUserSingleton;

/**
 * Created by rsbulanon on 7/9/16.
 */
public class PublicAnnouncementsActivity extends BaseActivity implements OnApiRequestListener {

    @BindView(R.id.rvAnnouncements) RecyclerView rvAnnouncements;
    @BindView(R.id.btnAdd) FloatingActionButton btnAdd;
    private AnnouncementsSingleton announcementsSingleton;
    private CurrentUserSingleton currentUserSingleton;
    private ApiRequestHelper apiRequestHelper;
    private String token;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_announcements);
        ButterKnife.bind(this);
        setToolbarTitle("Public Announcements");
        announcementsSingleton = AnnouncementsSingleton.getInstance();
        currentUserSingleton = CurrentUserSingleton.newInstance();
        apiRequestHelper = new ApiRequestHelper(this);
        token = currentUserSingleton.getCurrentUser().getToken();

        if (PrefsHelper.getBoolean(this,"refresh_announcements") && announcementsSingleton.getAnnouncements().size() > 0) {
            apiRequestHelper.getLatestAnnouncements(token,announcementsSingleton.getAnnouncements().get(0).getId());
        } else if (announcementsSingleton.getAnnouncements().size() == 0) {
            apiRequestHelper.getAnnouncements(token,0,10);
        }
        initAnnouncements();

        if (currentUserSingleton.getCurrentUser().getUserLevel().equals("Regular User")) {
            btnAdd.setVisibility(View.INVISIBLE);
        }
        seen();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogHelper.log("intent","on new intent called");
        seen();
    }

    private void initAnnouncements() {
        final AnnouncementsAdapter adapter = new AnnouncementsAdapter(this,announcementsSingleton.getAnnouncements());
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvAnnouncements.setAdapter(adapter);
        rvAnnouncements.setLayoutManager(linearLayoutManager);
        rvAnnouncements.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                apiRequestHelper.getAnnouncements(token,announcementsSingleton.getAnnouncements().size(),10);
            }
        });
    }

    @Override
    public void onApiRequestBegin(String action) {
        if (action.equals(AppConstants.ACTION_GET_ANNOUNCEMENTS)) {
            showCustomProgress("Getting announcements, Please wait...");
        } else if (action.equals(AppConstants.ACTION_POST_ANNOUNCEMENTS)) {
            showCustomProgress("Broadcasting announcement, Please wait...");
        } else if (action.equals(AppConstants.ACTION_GET_LATEST_ANNOUNCEMENTS)) {
            showCustomProgress("Fetching latest announcements, Please wait...");
        }
    }

    @Override
    public void onApiRequestSuccess(String action, Object result) {
        dismissCustomProgress();
        if (action.equals(AppConstants.ACTION_GET_ANNOUNCEMENTS)) {
            final ArrayList<Announcement> announcements = (ArrayList<Announcement>)result;
            announcementsSingleton.getAnnouncements().addAll(announcements);
        } else if (action.equals(AppConstants.ACTION_POST_ANNOUNCEMENTS)) {
            final Announcement announcement = (Announcement)result;
            if (!announcementsSingleton.isAnnouncementExisting(announcement.getId())) {
                announcementsSingleton.getAnnouncements().add(0,announcement);
            }
        } else if (action.equals(AppConstants.ACTION_GET_LATEST_ANNOUNCEMENTS)) {
            final ArrayList<Announcement> announcements = (ArrayList<Announcement>)result;
            announcementsSingleton.getAnnouncements().addAll(0,announcements);
            PrefsHelper.setBoolean(this,"refresh_announcements",false);
        }
        rvAnnouncements.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onApiRequestFailed(String action, Throwable t) {
        dismissCustomProgress();
        handleApiException(t);
        LogHelper.log("err","error in ---> " + action + " cause ---> " + t.getMessage());
        if (t instanceof HttpException) {
            if (action.equals(AppConstants.ACTION_LOGIN)) {
                final ApiError apiError = ApiErrorHelper.parseError(((HttpException) t).response());
                showConfirmDialog(action,"Login Failed", apiError.getMessage(),"Close","",null);
            }
        }
    }

    @OnClick(R.id.btnAdd)
    public void createAnnouncement() {
        final CreateAnnouncementDialogFragment fragment = CreateAnnouncementDialogFragment.newInstance();
        fragment.setOnCreateAnnouncementListener(new CreateAnnouncementDialogFragment.OnCreateAnnouncementListener() {
            @Override
            public void onCreateAnnouncement(String title, String message) {
                fragment.dismiss();
                if (isNetworkAvailable()) {
                    apiRequestHelper.createAnnouncement(token,title,message);
                } else {
                    showSnackbar(btnAdd, AppConstants.WARN_CONNECTION);
                }
            }

            @Override
            public void onCancel() {
                fragment.dismiss();
            }
        });
        fragment.show(getFragmentManager(),"announcements");
    }

    @Subscribe
    public void handleApiResponse(final HashMap<String,Object> map) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (map.containsKey("data")) {
                    try {
                        final JSONObject json = new JSONObject(map.get("data").toString());
                        if (json.has("action")) {
                            final String action = json.getString("action");

                            /** new incident notification */
                            if (action.equals("announcements")) {
                                if (announcementsSingleton.getAnnouncements().size() == 0) {
                                    apiRequestHelper.getAnnouncements(token,0,10);
                                } else {
                                    seen();
                                    apiRequestHelper.getLatestAnnouncements(token,announcementsSingleton.getAnnouncements().get(0).getId());
                                }
                            }
                            rvAnnouncements.getAdapter().notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void seen() {
        if (PrefsHelper.getBoolean(this,"has_notifications")) {
            PrefsHelper.setBoolean(this, "has_notifications", false);
        }
    }
}
