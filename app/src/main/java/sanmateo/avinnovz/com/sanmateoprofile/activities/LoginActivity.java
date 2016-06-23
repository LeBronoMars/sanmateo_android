package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.LoginDialogFragment;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;

/**
 * Created by rsbulanon on 6/22/16.
 */
public class LoginActivity extends BaseActivity implements OnApiRequestListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnLogin)
    public void showLoginDialogFragment() {
        final LoginDialogFragment loginDialogFragment = LoginDialogFragment.newInstance();
        loginDialogFragment.setOnLoginListener(new LoginDialogFragment.OnLoginListener() {
            @Override
            public void OnLogin(String email, String password) {

            }
        });
        loginDialogFragment.show(getFragmentManager(),"login");
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
}
