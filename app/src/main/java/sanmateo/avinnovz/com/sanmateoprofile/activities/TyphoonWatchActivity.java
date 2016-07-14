package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;

/**
 * Created by rsbulanon on 7/14/16.
 */
public class TyphoonWatchActivity extends BaseActivity {

    @BindView(R.id.webView) WebView webView;
    @BindView(R.id.tvSource) TextView tvSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typhoon_watch);
        ButterKnife.bind(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://kidlat.pagasa.dost.gov.ph/");
        tvSource.setText("Source : www.pagasa.dost.gov.ph");
        setToolbarTitle("Typhoon Watch");
    }
}
