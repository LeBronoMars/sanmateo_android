package sanmateo.avinnovz.com.sanmateoprofile.fragments.admin;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.http.Field;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;

/**
 * Created by rsbulanon on 6/30/16.
 */
public class CreateNewsDialogFragment extends DialogFragment {

    @BindView(R.id.etNewsTitle) EditText etNewsTitle;
    @BindView(R.id.etNewsBody) EditText etNewsBody;
    @BindView(R.id.etReportedBy) EditText etReportedBy;
    @BindView(R.id.etImageURL) EditText etImageURL;
    @BindView(R.id.etSourceURL) EditText etSourceURL;
    private View view;
    private Dialog mDialog;
    private BaseActivity activity;
    private OnCreateNewsListener onCreateNewsListener;

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
                onCreateNewsListener.onCreateNews(title,body,sourceUrl,imageUrl,reportedBy);
            }
        }
    }

    public interface OnCreateNewsListener {
        void onCreateNews(final String title, final String body, final String sourceUrl,
                          final String imageUrl, final String reportedBy);
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
}
