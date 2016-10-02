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
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;

/**
 * Created by rsbulanon on 6/30/16.
 */
public class CreateAnnouncementDialogFragment extends DialogFragment {

    @BindView(R.id.etTitle) EditText etTitle;
    @BindView(R.id.etMessage) EditText etMessage;
    private View view;
    private Dialog mDialog;
    private BaseActivity activity;
    private OnCreateAnnouncementListener onCreateAnnouncementListener;

    public static CreateAnnouncementDialogFragment newInstance() {
        final CreateAnnouncementDialogFragment fragment = new CreateAnnouncementDialogFragment();
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
        view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_create_announcement, null);
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

    @OnClick(R.id.btnAnnounce)
    public void createNews() {
        final String title = etTitle.getText().toString().trim();
        final String message = etMessage.getText().toString().trim();

        if (title.isEmpty()) {
            activity.setError(etTitle, AppConstants.WARN_FIELD_REQUIRED);
        } else if (message.isEmpty()) {
            activity.setError(etMessage, AppConstants.WARN_FIELD_REQUIRED);
        } else {
            if (onCreateAnnouncementListener != null) {
                onCreateAnnouncementListener.onCreateAnnouncement(title,message);
            }
        }
    }

    @OnClick(R.id.btnCancel)
    public void cancel() {
        if (onCreateAnnouncementListener != null) {
            onCreateAnnouncementListener.onCancel();
        }
    }

    public interface OnCreateAnnouncementListener {
        void onCreateAnnouncement(final String title, final String message);
        void onCancel();
    }

    public void setOnCreateAnnouncementListener(OnCreateAnnouncementListener onCreateAnnouncementListener) {
        this.onCreateAnnouncementListener = onCreateAnnouncementListener;
    }
}
