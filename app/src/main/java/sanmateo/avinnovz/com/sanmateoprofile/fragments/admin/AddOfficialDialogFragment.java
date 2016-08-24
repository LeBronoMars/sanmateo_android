package sanmateo.avinnovz.com.sanmateoprofile.fragments.admin;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.File;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;

/**
 * Created by rsbulanon on 8/24/16.
 */
public class AddOfficialDialogFragment extends DialogFragment {

    @BindView(R.id.etFirstName) EditText etFirstName;
    @BindView(R.id.etLastName) EditText etLastName;
    @BindView(R.id.etNickName) EditText etNickName;
    @BindView(R.id.etPosition) EditText etPosition;
    @BindView(R.id.etBackground) EditText etBackground;
    @BindView(R.id.etImageURL) EditText etImageURL;
    @BindView(R.id.llManualInput) LinearLayout llManualInput;
    @BindView(R.id.rlImagePreview) RelativeLayout rlImagePreview;
    @BindView(R.id.ivImagePreview) ImageView ivImagePreview;
    private View view;
    private Dialog mDialog;
    private BaseActivity activity;
    private OnCreateOfficialListener onCreateNewsListener;
    private static final int SELECT_IMAGE = 1;
    private File fileToUpload = null;

    public static AddOfficialDialogFragment newInstance() {
        final AddOfficialDialogFragment fragment = new AddOfficialDialogFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
        Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_create_official, null);
        ButterKnife.bind(this, view);
        activity = (BaseActivity) getActivity();
        mDialog = new Dialog(getActivity());
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout
                .LayoutParams.WRAP_CONTENT);
        return mDialog;
    }

    @OnClick(R.id.btnCreate)
    public void createNews() {
        final String firstName = etFirstName.getText().toString().trim();
        final String lastName = etLastName.getText().toString().trim();
        final String nickName = etNickName.getText().toString().trim();
        final String position = etPosition.getText().toString().trim();
        final String background = etBackground.getText().toString().trim();
        final String picUrl = etImageURL.getText().toString().trim();

        if (firstName.isEmpty()) {
            activity.setError(etFirstName, AppConstants.WARN_FIELD_REQUIRED);
        } else if (lastName.isEmpty()) {
            activity.setError(etLastName, AppConstants.WARN_FIELD_REQUIRED);
        } else if (position.isEmpty()) {
            activity.setError(etPosition, AppConstants.WARN_FIELD_REQUIRED);
        } else {
            if (onCreateNewsListener != null) {
                onCreateNewsListener.onCreateNews(firstName,lastName,nickName,
                                                position,background, picUrl, fileToUpload);
            }
        }
    }

    public interface OnCreateOfficialListener {
        void onCreateNews(final String firstName, final String lastName, final String nickName,
                          final String position, final String background, final String picUrl,
                          final File fileToUpload);
        void onCancel();
    }

    @OnClick(R.id.btnCancel)
    public void cancel() {
        if (onCreateNewsListener != null) {
            onCreateNewsListener.onCancel();
        }
    }

    @OnClick(R.id.btnSelectFromGallery)
    public void selectFromGallery() {
        final Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == activity.RESULT_OK) {
            if (requestCode == SELECT_IMAGE) {
                final String fileName = "official_profile_pic_"+ activity.getSDF().format(Calendar.getInstance().getTime());
                fileToUpload = null;
                fileToUpload = activity.getFile(data.getData(),fileName+".jpg");
                AppConstants.PICASSO.load(fileToUpload).fit().centerCrop().into(ivImagePreview);
                rlImagePreview.setVisibility(View.VISIBLE);
                llManualInput.setVisibility(View.GONE);
            }
        }
    }

    @OnClick(R.id.ivRemove)
    public void removeSelectedImage() {
        if (rlImagePreview.isShown()) {
            fileToUpload = null;
            rlImagePreview.setVisibility(View.GONE);
            llManualInput.setVisibility(View.VISIBLE);
        }
    }

    public void setOnCreateNewsListener(OnCreateOfficialListener onCreateNewsListener) {
        this.onCreateNewsListener = onCreateNewsListener;
    }
}
