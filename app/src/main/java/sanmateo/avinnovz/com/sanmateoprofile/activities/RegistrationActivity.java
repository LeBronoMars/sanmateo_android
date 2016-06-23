package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import sanmateo.avinnovz.com.sanmateoprofile.R;

/**
 * Created by rsbulanon on 6/23/16.
 */
public class RegistrationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        animateToRight(this);
    }

    @OnClick(R.id.ivBack)
    public void onCustomBack() {
        onBackPressed();
    }
}
