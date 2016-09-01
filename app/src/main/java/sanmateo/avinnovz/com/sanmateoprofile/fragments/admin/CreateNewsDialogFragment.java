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

import com.squareup.picasso.Callback;

import java.io.File;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.http.Field;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;

/**
 * Created by rsbulanon on 6/30/16.
 */
public class CreateNewsDialogFragment extends DialogFragment {

    @BindView(R.id.etNewsTitle) EditText etNewsTitle;
    @BindView(R.id.etNewsBody) EditText etNewsBody;
    @BindView(R.id.etReportedBy) EditText etReportedBy;
    @BindView(R.id.etImageURL) EditText etImageURL;
    @BindView(R.id.etSourceURL) EditText etSourceURL;
    @BindView(R.id.llManualInput) LinearLayout llManualInput;
    @BindView(R.id.rlImagePreview) RelativeLayout rlImagePreview;
    @BindView(R.id.ivImagePreview) ImageView ivImagePreview;
    private View view;
    private Dialog mDialog;
    private BaseActivity activity;
    private OnCreateNewsListener onCreateNewsListener;
    private static final int SELECT_IMAGE = 1;
    private File fileToUpload = null;

    public static CreateNewsDialogFragment newInstance() {
        final CreateNewsDialogFragment fragment = new CreateNewsDialogFragment();
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
        view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_create_news, null);
        ButterKnife.bind(this, view);
        activity = (BaseActivity) getActivity();
        mDialog = new Dialog(getActivity());
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout
                .LayoutParams.WRAP_CONTENT);

        etNewsTitle.setText("San Mateo Profile App now available");
        etNewsBody.setText("Lorem ipsum dolor");
        etImageURL.setText("https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/gallery/san_mateo_app_1.png");
        etReportedBy.setText("San Mateo Government");
        return mDialog;
    }

    @OnClick(R.id.btnCreate)
    public void createNews() {
        final String title = etNewsTitle.getText().toString().trim();
        final String body = etNewsBody.getText().toString().trim();
        final String reportedBy = etReportedBy.getText().toString().trim();
        final String sourceUrl = etSourceURL.getText().toString().trim();
        final String imageUrl = etImageURL.getText().toString().trim();

        if (title.isEmpty()) {
            activity.setError(etNewsTitle, AppConstants.WARN_FIELD_REQUIRED);
        } else if (body.isEmpty()) {
            activity.setError(etNewsBody, AppConstants.WARN_FIELD_REQUIRED);
        } else if (reportedBy.isEmpty()) {
            activity.setError(etReportedBy, AppConstants.WARN_FIELD_REQUIRED);
        } else {
            if (onCreateNewsListener != null) {
                onCreateNewsListener.onCreateNews(title,body,sourceUrl,imageUrl,reportedBy, fileToUpload);
            }
        }
    }

    public interface OnCreateNewsListener {
        void onCreateNews(final String title, final String body, final String sourceUrl,
                          final String imageUrl, final String reportedBy, final File file);
        void onCancel();
    }

    public void setOnCreateNewsListener(OnCreateNewsListener onCreateNewsListener) {
        this.onCreateNewsListener = onCreateNewsListener;
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
                final String fileName = "news_banner_img_"+ activity.getSDF().format(Calendar.getInstance().getTime());
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
}
