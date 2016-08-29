package sanmateo.avinnovz.com.sanmateoprofile.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.adapter.rxjava.HttpException;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.WaterLevelAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiErrorHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiRequestHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.PrefsHelper;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.ApiError;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.WaterLevel;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.CurrentUserSingleton;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.WaterLevelSingleton;

/**
 * Created by ctmanalo on 8/28/16.
 */
public class AlertLevelFragment extends Fragment implements OnApiRequestListener{

    @BindView(R.id.rvListing) RecyclerView rvListing;
    private BaseActivity activity;
    private WaterLevelSingleton waterLevelSingleton;
    private CurrentUserSingleton currentUserSingleton;
    private String token;
    private ApiRequestHelper apiRequestHelper;
    private boolean loading = true;
    private int pastVisibleItems;
    private int visibleItemCount;
    private int totalItemCount;

    public static AlertLevelFragment newInstance() {
        return new AlertLevelFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alert_level, container, false);
        ButterKnife.bind(this, view);
        initResources();
        initWaterListing();
        return view;
    }

    private void initResources() {
        activity = (BaseActivity) getActivity();
        waterLevelSingleton = WaterLevelSingleton.getInstance();
        currentUserSingleton = CurrentUserSingleton.newInstance();
        token = currentUserSingleton.getCurrentUser().getToken();
        apiRequestHelper = new ApiRequestHelper(this);

        if (PrefsHelper.getBoolean(activity, "refresh_water_level")
                && waterLevelSingleton.getWaterLevels().size() > 0) {
            apiRequestHelper.getLatestWaterLevelNotifications(token,
                    waterLevelSingleton.getWaterLevels().get(0).getId());
        } else if (waterLevelSingleton.getWaterLevels().size() == 0) {
            apiRequestHelper.getWaterLevelNotifications(token, 0, 10);
        }
    }

    private void initWaterListing() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        final WaterLevelAdapter adapter = new WaterLevelAdapter(activity,
                waterLevelSingleton.getWaterLevels());
        rvListing.setAdapter(adapter);
        rvListing.setLayoutManager(linearLayoutManager);
        rvListing.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = false;
                            apiRequestHelper.getWaterLevelNotifications(token,
                                    waterLevelSingleton.getWaterLevels().size(), 10);
                        }
                    }
                }
            }
        });
    }


    @Override
    public void onApiRequestBegin(String action) {
        if (action.equals(AppConstants.ACTION_GET_WATER_LEVEL_NOTIFS)) {
            activity.showCustomProgress("Fetching water level notifications, Please wait...");
        } else if (action.equals(AppConstants.ACTION_POST_WATER_LEVEL_NOTIFS_LATEST)) {
            activity.showCustomProgress("Fetching latest notifications, Please wait...");
        }
    }

    @Override
    public void onApiRequestSuccess(String action, Object result) {
        activity.dismissCustomProgress();
        if (action.equals(AppConstants.ACTION_GET_WATER_LEVEL_NOTIFS) ||
                action.equals(AppConstants.ACTION_POST_WATER_LEVEL_NOTIFS_LATEST)) {
            final ArrayList<WaterLevel> waterLevels = (ArrayList<WaterLevel>) result;
            waterLevelSingleton.getWaterLevels().addAll(0, waterLevels);
            if (action.equals(AppConstants.ACTION_POST_WATER_LEVEL_NOTIFS_LATEST)) {
                PrefsHelper.setBoolean(activity,"refresh_water_level",false);
            }
        }
        rvListing.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onApiRequestFailed(String action, Throwable t) {
        activity.dismissCustomProgress();
        activity.handleApiException(t);
        if (t instanceof HttpException) {
            if (action.equals(AppConstants.ACTION_LOGIN)) {
                final HttpException ex = (HttpException) t;
                final ApiError apiError = ApiErrorHelper.parseError((ex).response());
                activity.showConfirmDialog(action, "Login Failed", apiError.getMessage(), "Close",
                        "", null);
            }
        }
    }

    @Subscribe
    public void handleEventBus(final HashMap<String,Object> map) {
        if (map.containsKey("data")) {
            try {
                final JSONObject json = new JSONObject(map.get("data").toString());
                if (json.has("action")) {
                    if (json.getString("action").equals("water level")) {
                        LogHelper.log("water","action ----> " + json.getString("action"));
                        apiRequestHelper.getLatestWaterLevelNotifications(token,
                                waterLevelSingleton.getWaterLevels().get(0).getId());
                    }
                }
            } catch (JSONException e) {

            }
        }
    }
}
