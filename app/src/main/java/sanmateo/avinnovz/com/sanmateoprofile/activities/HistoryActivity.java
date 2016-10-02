package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.os.Bundle;

import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;

/**
 * Created by ctmanalo on 7/7/16.
 */
public class HistoryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        setToolbarTitle("History");
    }
}
