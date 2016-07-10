package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;

/**
 * Created by rsbulanon on 7/10/16.
 */
public class WaterAlertListActivity extends BaseActivity {


    @BindView(R.id.barchart) BarChart barchart;
    @BindView(R.id.rvListing) RecyclerView rvListing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_alert_list);
        ButterKnife.bind(this);
    }

    private void initWaterListing() {

    }


}
