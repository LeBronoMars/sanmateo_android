package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.widget.TextView;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.adapter.rxjava.HttpException;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.LoginDialogFragment;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AmazonS3Helper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiErrorHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiRequestHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.ApiError;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.AuthResponse;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.CurrentUserSingleton;

/**
 * Created by rsbulanon on 6/22/16.
 */
public class LoginActivity extends BaseActivity implements OnApiRequestListener, TransferListener {

    @BindView(R.id.tvCreateAccount) TextView tvCreateAccount;
    private ApiRequestHelper apiRequestHelper;
    private Intent dataToUpload = null;
    private static final int SELECT_IMAGE = 1;
    private AmazonS3Helper amazonS3Helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
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
            final CurrentUserSingleton currentUserSingleton = CurrentUserSingleton.newInstance();
            final AuthResponse authResponse = (AuthResponse)result;
            currentUserSingleton.setAuthResponse(authResponse);
            startActivity(new Intent(this, MainActivity.class));
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

    private void openGallery() {
        LogHelper.log("s3","open gallery");
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE) {
            try {
                final File file = getFile(data.getData());
                amazonS3Helper.uploadImage(file).setTransferListener(this);
            } catch (IOException e) {
                LogHelper.log("s3","error in getting file ---> " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStateChanged(int id, TransferState state) {
        LogHelper.log("s3","state ---> " + state.name());
    }

    @Override
    public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
        LogHelper.log("s3","process ---> " + bytesCurrent + "/" + bytesTotal);
    }

    @Override
    public void onError(int id, Exception ex) {

    }

    private File getFile(Uri uri) throws IOException {
        final ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
        final FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        final Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        final byte[] bitmapdata = bos.toByteArray();

        final File f = new File(getCacheDir(), "sample");
        f.createNewFile();

        final FileOutputStream fos = new FileOutputStream(f);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();

        return f;
    }
}
