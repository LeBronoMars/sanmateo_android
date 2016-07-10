package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.os.Bundle;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;

/**
 * Created by rsbulanon on 7/10/16.
 */
public class WaterLevelMonitoringActivity extends BaseActivity {

    @BindView(R.id.wvSource) WebView wvSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_level_monitoring);
        ButterKnife.bind(this);
        setToolbarTitle("Water Level");
        wvSource.loadUrl("http://121.58.193.221:8080/html/wl/wl_map.html");
    }
}
