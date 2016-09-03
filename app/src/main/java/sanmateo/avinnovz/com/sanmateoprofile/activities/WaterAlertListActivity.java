package sanmateo.avinnovz.com.sanmateoprofile.activities;

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
import sanmateo.avinnovz.com.sanmateoprofile.adapters.WaterLevelAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.admin.WaterLevelNotifDialogFragment;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiErrorHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiRequestHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.PrefsHelper;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.EndlessRecyclerViewScrollListener;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.ApiError;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.WaterLevel;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.CurrentUserSingleton;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.WaterLevelSingleton;

/**
 * Created by rsbulanon on 7/14/16.
 */
public class WaterAlertListActivity extends BaseActivity implements OnApiRequestListener {

    @BindView(R.id.rvListing) RecyclerView rvListing;
    @BindView(R.id.btnAdd) FloatingActionButton btnAdd;
    private WaterLevelSingleton waterLevelSingleton;
    private CurrentUserSingleton currentUserSingleton;
    private String token;
    private ApiRequestHelper apiRequestHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_alert_list);
        ButterKnife.bind(this);
        waterLevelSingleton = WaterLevelSingleton.getInstance();
        currentUserSingleton = CurrentUserSingleton.newInstance();
        token = currentUserSingleton.getCurrentUser().getToken();
        apiRequestHelper = new ApiRequestHelper(this);
        initWaterListing();
        setToolbarTitle("Water Level Monitoring");

        if (PrefsHelper.getBoolean(this, "refresh_water_level") && waterLevelSingleton.getWaterLevels().size() > 0) {
            LogHelper.log("water", "must refresh");
            apiRequestHelper.getLatestWaterLevelNotifications(token,
                    waterLevelSingleton.getWaterLevels().get(0).getId());
        } else if (waterLevelSingleton.getWaterLevels().size() == 0) {
            LogHelper.log("water", "must call all");
            apiRequestHelper.getWaterLevelNotifications(token, 0, 10);
        }

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

    private void initWaterListing() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        final WaterLevelAdapter adapter = new WaterLevelAdapter(this, waterLevelSingleton.getWaterLevels());
        rvListing.setAdapter(adapter);
        rvListing.setLayoutManager(linearLayoutManager);
        rvListing.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                apiRequestHelper.getWaterLevelNotifications(token, waterLevelSingleton.getWaterLevels().size(), 10);
            }
        });
    }

    @Override
    public void onApiRequestBegin(String action) {
        if (action.equals(AppConstants.ACTION_GET_WATER_LEVEL_NOTIFS)) {
            showCustomProgress("Fetching water level notifications, Please wait...");
        } else if (action.equals(AppConstants.ACTION_POST_WATER_LEVEL_NOTIFS)) {
            showCustomProgress("Sending water level notification, Please wait...");
        } else if (action.equals(AppConstants.ACTION_POST_WATER_LEVEL_NOTIFS_LATEST)) {
            showCustomProgress("Fetching latest notifications, Please wait...");
        }
    }

    @Override
    public void onApiRequestSuccess(String action, Object result) {
        dismissCustomProgress();
        if (action.equals(AppConstants.ACTION_GET_WATER_LEVEL_NOTIFS) ||
                action.equals(AppConstants.ACTION_POST_WATER_LEVEL_NOTIFS_LATEST)) {
            final ArrayList<WaterLevel> waterLevels = (ArrayList<WaterLevel>) result;
            waterLevelSingleton.getWaterLevels().addAll(0, waterLevels);
            LogHelper.log("water", "action ---> "+ action+" size ---> " + waterLevels.size());
            if (action.equals(AppConstants.ACTION_POST_WATER_LEVEL_NOTIFS_LATEST)) {
                PrefsHelper.setBoolean(this,"refresh_water_level",false);
            }
        } else if (action.equals(AppConstants.ACTION_POST_WATER_LEVEL_NOTIFS)) {
            final WaterLevel waterLevel = (WaterLevel) result;
            waterLevelSingleton.getWaterLevels().add(0, waterLevel);
        }
        rvListing.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onApiRequestFailed(String action, Throwable t) {
        dismissCustomProgress();
        handleApiException(t);
        if (t instanceof HttpException) {
            if (action.equals(AppConstants.ACTION_LOGIN)) {
                final HttpException ex = (HttpException) t;
                final ApiError apiError = ApiErrorHelper.parseError((ex).response());
                showConfirmDialog(action, "Login Failed", apiError.getMessage(), "Close", "", null);
            }
        }
    }

    @OnClick(R.id.btnAdd)
    public void add() {
        final WaterLevelNotifDialogFragment fragment = WaterLevelNotifDialogFragment.newInstance();
        fragment.setOnWaterLevelNotificationListener(new WaterLevelNotifDialogFragment.OnWaterLevelNotificationListener() {
            @Override
            public void onAnnounceNotif(final String area, double level) {
                fragment.dismiss();
                apiRequestHelper.createWaterLevelNotification(token,area,level);
            }

            @Override
            public void onCancel() {
                fragment.dismiss();
            }
        });
        fragment.show(getFragmentManager(), "water level");
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

    private void seen() {
        if (PrefsHelper.getBoolean(this,"has_notifications")) {
            PrefsHelper.setBoolean(this, "has_notifications", false);
        }
    }
}
