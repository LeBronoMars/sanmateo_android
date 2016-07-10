package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

        if (PrefsHelper.getBoolean(this, "refresh_water_level") && waterLevelSingleton.getWaterLevels().size() > 0) {
            LogHelper.log("water", "must refresh");
        } else if (waterLevelSingleton.getWaterLevels().size() == 0) {
            LogHelper.log("water", "must call all");
            apiRequestHelper.getWaterLevelNotifications(token, 0, 10);
        }
    }

    private void initWaterListing() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        final WaterLevelAdapter adapter = new WaterLevelAdapter(this, waterLevelSingleton.getWaterLevels());
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
                            apiRequestHelper.getWaterLevelNotifications(token, waterLevelSingleton.getWaterLevels().size(), 10);
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
            final ArrayList<WaterLevel> waterLevels = (ArrayList<WaterLevel>) result;
            waterLevelSingleton.getWaterLevels().addAll(0, waterLevels);
            LogHelper.log("water", "size ---> " + waterLevels.size());
        } else if (action.equals(AppConstants.ACTION_POST_WATER_LEVEL_NOTIFS)) {
            final WaterLevel waterLevel = (WaterLevel) result;
            waterLevelSingleton.getWaterLevels().add(0, waterLevel);
        }
        displayData(waterLevelSingleton.getWaterLevels(),barchart);
        rvListing.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onApiRequestFailed(String action, Throwable t) {
        dismissCustomProgress();
        LogHelper.log("err", "error in ---> " + action + " cause ---> " + t.getMessage());
        if (t instanceof HttpException) {
            if (action.equals(AppConstants.ACTION_LOGIN)) {
                final ApiError apiError = ApiErrorHelper.parseError(((HttpException) t).response());
                showConfirmDialog(action, "Login Failed", apiError.getMessage(), "Close", "", null);
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
                apiRequestHelper.createWaterLevelNotification(token, level);
            }

            @Override
            public void onCancel() {
                fragment.dismiss();
            }
        });
        fragment.show(getFragmentManager(), "water level");
    }

    private void displayData(final ArrayList<WaterLevel> waterLevels, final BarChart barChart) {
        final ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(getDataSet(waterLevels));

        BarData data = new BarData(dataSets);
        barChart.setData(data);
        barChart.animateXY(2000, 2000);

        barChart.getAxisLeft().setDrawLabels(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawAxisLine(false);

        barChart.getAxisRight().setDrawLabels(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisRight().setDrawAxisLine(false);
        barChart.setDrawGridBackground(false);
        barChart.setBackgroundColor(Color.WHITE);

        barChart.setDescription("");
        barChart.getBarData().setValueTextSize(5);
        barChart.getBarData().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return new DecimalFormat("#.##").format(value);
            }
        });

        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setDrawAxisLine(false);
        barChart.getXAxis().setGridColor(Color.RED);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setTextSize(7);

        barChart.getLegend().setEnabled(false);
        barChart.setTouchEnabled(false);
        barChart.setData(data);
        barChart.invalidate();
    }

    private BarDataSet getDataSet(final ArrayList<WaterLevel> waterLevels) {
        final ArrayList<Integer> colors = new ArrayList<>();
        final ArrayList<BarEntry> valueSet1 = new ArrayList<>();

        for (int i = 0; i < waterLevels.size(); i++) {
            final WaterLevel w = waterLevels.get(i);
            valueSet1.add(createBarEntry((float) w.getWaterLevel(), i));
            if (w.getWaterLevel() >= 18.01 && w.getWaterLevel() <= 19.00) {
                colors.add(ContextCompat.getColor(this,R.color.water_level_alarm));
            } else if (w.getWaterLevel() >= 19.01) {
                colors.add(ContextCompat.getColor(this,R.color.water_level_critical));
            } else {
                colors.add(ContextCompat.getColor(this,R.color.water_level_alert));
            }
        }
        return new BarDataSet(valueSet1, "");
    }

    private BarEntry createBarEntry(final float value, final int index) {
        final BarEntry barEntry = new BarEntry(value, index);
        return barEntry;
    }
}
