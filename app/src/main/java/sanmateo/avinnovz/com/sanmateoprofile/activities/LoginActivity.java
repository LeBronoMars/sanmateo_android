package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.LoginDialogFragment;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
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
        if (action.equals(AppConstants.ACTION_LOGIN)) {
            showCustomProgress("Authenticating your credentials, Please wait...");
        }
    }

    @Override
    public void onApiRequestSuccess(String action, Object result) {

    }

    @Override
    public void onApiRequestFailed(String action, Throwable t) {
        dismissCustomProgress();
        if (t instanceof HttpException) {
            final ResponseBody body = ((HttpException) t).response().errorBody();
            
        }
    }
}
