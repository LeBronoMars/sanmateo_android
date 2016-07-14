package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.adapter.rxjava.HttpException;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.LoginDialogFragment;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiErrorHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiRequestHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnConfirmDialogListener;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.ApiError;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.AuthResponse;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.CurrentUserSingleton;

/**
 * Created by rsbulanon on 6/22/16.
 */
public class LoginActivity extends BaseActivity implements OnApiRequestListener {

    @BindView(R.id.tvCreateAccount) TextView tvCreateAccount;
    private ApiRequestHelper apiRequestHelper;
    private static final int REQUEST_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final String[] requiredPermission = new String[]{
                    Manifest.permission.SEND_SMS,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_CONTACTS
            };
            requestPermissions(requiredPermission, REQUEST_PERMISSIONS);
        } else {
            initialize();
        }

    }

    private void initialize() {
        AppConstants.IS_FACEBOOK_APP_INSTALLED = isFacebookInstalled();
        apiRequestHelper = new ApiRequestHelper(this);
    }

    @OnClick(R.id.btnLogin)
    public void showLoginDialogFragment() {
        if (isNetworkAvailable()) {
            final LoginDialogFragment loginDialogFragment = LoginDialogFragment.newInstance();
            loginDialogFragment.setOnLoginListener(new LoginDialogFragment.OnLoginListener() {
                @Override
                public void OnLogin(String email, String password) {
                    loginDialogFragment.dismiss();
                    apiRequestHelper.authenticateUser(email,password);
                }
            });
            loginDialogFragment.show(getFragmentManager(),"login");
        } else {
            showSnackbar(tvCreateAccount, AppConstants.WARN_CONNECTION);
        }
    }

    @OnClick(R.id.tvCreateAccount)
    public void showRegistrationPage() {
        startActivity(new Intent(this, RegistrationActivity.class));
        animateToLeft(this);
    }

    @Override
    public void onApiRequestBegin(String action) {
        if (action.equals(AppConstants.ACTION_LOGIN)) {
            showCustomProgress("Logging in, Please wait...");
        }
    }

    @Override
    public void onApiRequestSuccess(String action, Object result) {
        dismissCustomProgress();
        if (action.equals(AppConstants.ACTION_LOGIN)) {
            final AuthResponse authResponse = (AuthResponse)result;
            if (authResponse.getUserLevel().equals("superadmin") ||
                    authResponse.getUserLevel().equals("admin")) {
                final CurrentUserSingleton currentUserSingleton = CurrentUserSingleton.newInstance();
                currentUserSingleton.setAuthResponse(authResponse);
                startActivity(new Intent(this, NewHomeActivity.class));
                animateToLeft(this);
                finish();
            } else {
                showSnackbar(tvCreateAccount, AppConstants.WARN_INVALID_ACCOUNT);
            }
        }
    }

    @Override
    public void onApiRequestFailed(String action, Throwable t) {
        dismissCustomProgress();
        LogHelper.log("err","error in ---> " + action + " cause ---> " + t.getMessage());
        if (t instanceof HttpException) {
            if (action.equals(AppConstants.ACTION_LOGIN)) {
                final ApiError apiError = ApiErrorHelper.parseError(((HttpException) t).response());
                showConfirmDialog(action,"Login Failed", apiError.getMessage(),"Close","",null);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        LogHelper.log("res","ON REQUEST PERMISSION");
        switch (requestCode) {
            case REQUEST_PERMISSIONS:
                final boolean smsPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                final boolean writeExternalPermitted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (smsPermission && writeExternalPermitted) {
                    LogHelper.log("res","must continue with initialization");
                    initialize();
                } else {
                    /** close the app since the user denied the required permissions */
                    showConfirmDialog("", "Permission Denied", "You need to grant San Mateo Profile " +
                            "   app with full permissions to use the app.", "Close", "", new OnConfirmDialogListener() {
                        @Override
                        public void onConfirmed(String action) {
                            finish();
                            System.exit(0);
                        }

                        @Override
                        public void onCancelled(String action) {

                        }
                    });
                    LogHelper.log("res","permission denied");
                }
                break;
        }
    }
}
