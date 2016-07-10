package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;

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
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.ApiError;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.WaterLevel;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.CurrentUserSingleton;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.WaterLevelSingleton;

/**
 * Created by rsbulanon on 7/10/16.
 */
public class WaterAlertListActivity extends BaseActivity implements OnApiRequestListener {


    @BindView(R.id.barchart) BarChart barchart;
    @BindView(R.id.rvListing) RecyclerView rvListing;
    @BindView(R.id.btnAdd) FloatingActionButton btnAdd;
    private WaterLevelSingleton waterLevelSingleton;
    private CurrentUserSingleton currentUserSingleton;
    private String token;
    private ApiRequestHelper apiRequestHelper;
    private boolean loading = true;
    private int pastVisibleItems;
    private int visibleItemCount;
    private int totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_alert_list);
        ButterKnife.bind(this);
        waterLevelSingleton = WaterLevelSingleton.getInstance();
        currentUserSingleton = CurrentUserSingleton.newInstance();
        token = currentUserSingleton.getAuthResponse().getToken();
        apiRequestHelper = new ApiRequestHelper(this);
        initWaterListing();
        setToolbarTitle("Water Level Monitoring");

        if (PrefsHelper.getBoolean(this,"refresh_water_level") && waterLevelSingleton.getWaterLevels().size() > 0) {
            LogHelper.log("water","must refresh");
        } else if (waterLevelSingleton.getWaterLevels().size() == 0) {
            LogHelper.log("water","must call all");
            apiRequestHelper.getWaterLevelNotifications(token,0,10);
        }
    }

    private void initWaterListing() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        final WaterLevelAdapter adapter = new WaterLevelAdapter(this,waterLevelSingleton.getWaterLevels());
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
                            apiRequestHelper.getWaterLevelNotifications(token,waterLevelSingleton.getWaterLevels().size(),10);
                        }
                    }
                }
                //int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                //swipeRefreshItems.setEnabled(topRowVerticalPosition >= 0);
            }
        });
    }


    @Override
    public void onApiRequestBegin(String action) {
        if (action.equals(AppConstants.ACTION_GET_WATER_LEVEL_NOTIFS)) {
            showCustomProgress("Fetching water level notifications, Please wait...");
        } else if (action.equals(AppConstants.ACTION_POST_WATER_LEVEL_NOTIFS)) {
            showCustomProgress("Sending water level notification, Please wait...");
        }
    }

    @Override
    public void onApiRequestSuccess(String action, Object result) {
        dismissCustomProgress();
        if (action.equals(AppConstants.ACTION_GET_WATER_LEVEL_NOTIFS)) {
            final ArrayList<WaterLevel> waterLevels = (ArrayList<WaterLevel>)result;
            waterLevelSingleton.getWaterLevels().addAll(0,waterLevels);
            LogHelper.log("water","size ---> " + waterLevels.size());
        } else if (action.equals(AppConstants.ACTION_POST_WATER_LEVEL_NOTIFS)) {
            final WaterLevel waterLevel = (WaterLevel)result;
            waterLevelSingleton.getWaterLevels().add(0,waterLevel);
        }
        rvListing.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onApiRequestFailed(String action, Throwable t) {
        dismissCustomProgress();
        LogHelper.log("err","error in ---> " + action + " cause ---> " + t.getMessage());
        if (t instanceof HttpException) {
            if (action.equals(AppConstants.ACTION_LOGIN)) {
                final ApiError apiError = ApiErrorHelper.parseError(((HttpException) t).response());
                showConfirmDialog(action,"Login Failed", apiError.getMessage(),"Close","",null);
            }
        }
    }

    @OnClick(R.id.btnAdd)
    public void add() {
        final WaterLevelNotifDialogFragment fragment = WaterLevelNotifDialogFragment.newInstance();
        fragment.setOnWaterLevelNotificationListener(new WaterLevelNotifDialogFragment.OnWaterLevelNotificationListener() {
            @Override
            public void onAnnounceNotif(double level) {
                fragment.dismiss();
                apiRequestHelper.createWaterLevelNotification(token,level);
            }

            @Override
            public void onCancel() {
                fragment.dismiss();
            }
        });
        fragment.show(getFragmentManager(),"water level");
    }
}
