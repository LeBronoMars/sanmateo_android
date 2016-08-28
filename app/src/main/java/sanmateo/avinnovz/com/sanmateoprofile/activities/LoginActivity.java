package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.adapter.rxjava.HttpException;
import sanmateo.avinnovz.com.sanmateoprofile.R;
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
public class LoginActivity extends BaseActivity implements OnApiRequestListener, SurfaceHolder.Callback {

    @BindView(R.id.btnSignIn) Button btnSignIn;
    @BindView(R.id.surfaceView) SurfaceView surfaceView;
    private ApiRequestHelper apiRequestHelper;
    private static final int REQUEST_PERMISSIONS = 1;
    private SurfaceHolder surfaceHolder;
    private MediaPlayer mp;
    //private int video_bg = R.raw.login_bg;
    private int video_bg = R.raw.new_video_bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_video);
        ButterKnife.bind(this);

        mp = new MediaPlayer();
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final String[] requiredPermission = new String[]{
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_CONTACTS,
                    android.Manifest.permission.CAMERA
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
            loginDialogFragment.setOnLoginListener(new LoginDialogFragment.OnLoginListener() {
                @Override
                public void OnLogin(String email, String password) {
                    loginDialogFragment.dismiss();
                    apiRequestHelper.authenticateUser(email,password);
                }
            });
            loginDialogFragment.show(getFragmentManager(),"login");
        } else {
            showSnackbar(btnSignIn, AppConstants.WARN_CONNECTION);
        }
    }

    @OnClick(R.id.btnCreateAccount)
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

                LogHelper.log("res","write external --> " + writeExternalPermitted);
                LogHelper.log("res","read contacts --> " + readContactsPermission);
                LogHelper.log("res","camera  --> " + cameraPermission);

                if (writeExternalPermitted && readContactsPermission && cameraPermission) {
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

    @Override
    public void surfaceCreated(final SurfaceHolder surfaceHolder) {
        final Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + video_bg);

        try {
            mp.setDataSource(this,video);
            mp.setDisplay(surfaceHolder);
            mp.prepare();
            mp.setLooping(true);

            final Display display = getWindowManager().getDefaultDisplay();
            final Point size = new Point();
            display.getSize(size);

            //Get the SurfaceView layout parameters
            final android.view.ViewGroup.LayoutParams lp = surfaceView.getLayoutParams();

            //Set the width of the SurfaceView to the width of the screen
            lp.width = size.x;

            //Set the height of the SurfaceView to match the aspect ratio of the video
            //be sure to cast these as floats otherwise the calculation will likely be 0
            //lp.height = (int) (((float)videoHeight / (float)videoWidth) * (float)size.x);
            lp.height = size.y;

            //Commit the layout parameters
            surfaceView.setLayoutParams(lp);

            //Start video
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    protected void onDestroy() {
        mp.stop();
        super.onDestroy();
    }

    private void moveToHome() {
        startActivity(new Intent(this, NewHomeActivity.class));
        animateToLeft(this);
        finish();
    }
}
