package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.admin.WaterLevelNotifDialogFragment;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiRequestHelper;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_alert_list);
        ButterKnife.bind(this);
        waterLevelSingleton = WaterLevelSingleton.getInstance();
        currentUserSingleton = CurrentUserSingleton.newInstance();
        token = currentUserSingleton.getAuthResponse().getToken();
        apiRequestHelper = new ApiRequestHelper(this);
    }

    private void initWaterListing() {

    }

    @Override
    public void onApiRequestBegin(String action) {

    }

    @Override
    public void onApiRequestSuccess(String action, Object result) {

    }

    @Override
    public void onApiRequestFailed(String action, Throwable t) {

    }

    @OnClick(R.id.btnAdd)
    public void add() {
        final WaterLevelNotifDialogFragment fragment = WaterLevelNotifDialogFragment.newInstance();
        fragment.setOnWaterLevelNotificationListener(new WaterLevelNotifDialogFragment.OnWaterLevelNotificationListener() {
            @Override
            public void onAnnounceNotif(double level) {
                fragment.dismiss();
            }

            @Override
            public void onCancel() {
                fragment.dismiss();
            }
        });
        fragment.show(getFragmentManager(),"water level");
    }
}
