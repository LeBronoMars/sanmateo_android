package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;

/**
 * Created by rsbulanon on 7/14/16.
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_water_level,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_alert_list:
                startActivity(new Intent(this, AlertLevelActivity.class));
                animateToLeft(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
