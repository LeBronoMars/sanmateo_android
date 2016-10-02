package sanmateo.avinnovz.com.sanmateoprofile.activities.admin;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.adapter.rxjava.HttpException;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.LoginDialogFragment;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiErrorHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiRequestHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.DaoHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnConfirmDialogListener;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.ApiError;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.AuthResponse;

/**
 * Created by rsbulanon on 6/22/16.
 */
public class AdminLoginActivity extends BaseActivity implements OnApiRequestListener {

    @BindView(R.id.btnSignIn) Button btnSignIn;
    @BindView(R.id.btnCreateAccount) Button btnCreateAccount;
    private ApiRequestHelper apiRequestHelper;
    private static final int REQUEST_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_video);
        ButterKnife.bind(this);

        isOnline();

        btnCreateAccount.setVisibility(View.GONE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final String[] requiredPermission = new String[]{
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_CONTACTS,
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.READ_SMS,
                    android.Manifest.permission.SEND_SMS
            };
            requestPermissions(requiredPermission, REQUEST_PERMISSIONS);
        } else {
            initialize();
        }
    }

    private void initialize() {
        if (!DaoHelper.haCurrentUser()) {
            AppConstants.IS_FACEBOOK_APP_INSTALLED = isFacebookInstalled();
            apiRequestHelper = new ApiRequestHelper(this);
        } else {
            moveToHome();
        }
    }

    @OnClick(R.id.btnSignIn)
    public void showLoginDialogFragment() {
        if (isNetworkAvailable()) {
            final LoginDialogFragment loginDialogFragment = LoginDialogFragment.newInstance();
            loginDialogFragment.setOnLoginListener((email, password) -> {
                loginDialogFragment.dismiss();
                apiRequestHelper.authenticateUser(email,password);
            });
            loginDialogFragment.show(getFragmentManager(),"login");
        } else {
            showSnackbar(btnSignIn, AppConstants.WARN_CONNECTION);
        }
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
            if (authResponse.getUserLevel().equals("Regular User")) {
                showSnackbar(btnSignIn, AppConstants.WARN_INVALID_ACCOUNT);
            } else {
                DaoHelper.saveCurrentUser(authResponse);
                moveToHome();
            }
        }
    }

    @Override
    public void onApiRequestFailed(String action, Throwable t) {
        dismissCustomProgress();
        handleApiException(t);
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
                final boolean writeExternalPermitted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                final boolean readContactsPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                final boolean cameraPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                final boolean readSMSPermission = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                final boolean sendSMSPermission = grantResults[4] == PackageManager.PERMISSION_GRANTED;

                if (writeExternalPermitted && readContactsPermission && cameraPermission
                        && readSMSPermission && sendSMSPermission) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void moveToHome() {
        startActivity(new Intent(this, AdminMainActivity.class));
        animateToLeft(this);
        finish();
    }
}
