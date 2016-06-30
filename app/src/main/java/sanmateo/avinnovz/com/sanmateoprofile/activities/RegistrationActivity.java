package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiRequestHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;

/**
 * Created by rsbulanon on 6/23/16.
 */
public class RegistrationActivity extends BaseActivity implements OnApiRequestListener{

    @BindView(R.id.etFirstName) EditText etFirstName;
    @BindView(R.id.etLastName) EditText etLastName;
    @BindView(R.id.etContactNo) EditText etContactNo;
    @BindView(R.id.etAddress) EditText etAddress;
    @BindView(R.id.etEmail) EditText etEmail;
    @BindView(R.id.etPassword) EditText etPassword;
    @BindView(R.id.etConfirmPassword) EditText etConfirmPassword;

    private ApiRequestHelper apiRequestHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        apiRequestHelper = new ApiRequestHelper(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        animateToRight(this);
    }

    @OnClick({R.id.ivBack, R.id.btnCreateAccount})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btnCreateAccount:
                createAccount();
                break;
        }
    }

    private void createAccount() {
        if (isNetworkAvailable()) {
            showToast(AppConstants.WARN_CONNECTION);
        } else if (etFirstName.getText().toString().isEmpty()) {
            setError(etFirstName, AppConstants.WARN_FIELD_REQUIRED);
        } else if (etLastName.getText().toString().isEmpty()) {
            setError(etLastName, AppConstants.WARN_FIELD_REQUIRED);
        } else if (etContactNo.getText().toString().isEmpty()) {
            setError(etContactNo, AppConstants.WARN_FIELD_REQUIRED);
        } else if (etAddress.getText().toString().isEmpty()) {
            setError(etAddress, AppConstants.WARN_FIELD_REQUIRED);
        } else if (isValidEmail(etEmail.getText().toString())) {
            setError(etEmail, AppConstants.WARN_INVALID_EMAIL_FORMAT);
        } else if (etPassword.getText().toString().isEmpty()) {
            setError(etPassword, AppConstants.WARN_FIELD_REQUIRED);
        } else if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
            setError(etConfirmPassword, AppConstants.WARN_PASSWORD_NOT_MATCH);
        } else {
            String firstName = etFirstName.getText().toString();
            String lastName = etLastName.getText().toString();
            String contactNo = etContactNo.getText().toString();
            String address = etAddress.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            String gender = "Male";
            String userLevel = "admin";
            apiRequestHelper.createUser(firstName, lastName, contactNo, gender, email, address,
                    userLevel, password);
        }
    }

    @Override
    public void onApiRequestBegin(String action) {
        if (action.equals(AppConstants.ACTION_POST_CREATE_USER)) {
            showCustomProgress("Creating your account, Please wait...");
        }
    }

    @Override
    public void onApiRequestSuccess(String action, Object result) {
        dismissCustomProgress();
        if (action.equals(AppConstants.ACTION_POST_CREATE_USER)) {
            showToast("Registration Successful!");
            onBackPressed();
        }
    }

    @Override
    public void onApiRequestFailed(String action, Throwable t) {
        dismissCustomProgress();
    }
}
