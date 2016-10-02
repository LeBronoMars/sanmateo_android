package sanmateo.avinnovz.com.sanmateoprofile.fragments;

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
public class ReportIncidentReportDialogFragment extends DialogFragment {

    @BindView(R.id.etRemarks) EditText etRemarks;
    private View view;
    private Dialog mDialog;
    private BaseActivity activity;
    private OnReportIncidentListener onReportIncidentListener;

    public static ReportIncidentReportDialogFragment newInstance() {
        final ReportIncidentReportDialogFragment fragment = new ReportIncidentReportDialogFragment();
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
        view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_report_incident_report, null);
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

    @OnClick(R.id.btnSubmit)
    public void submitReport() {
        final String remarks = etRemarks.getText().toString().trim();

        if (remarks.isEmpty()) {
            activity.setError(etRemarks, AppConstants.WARN_FIELD_REQUIRED);
        } else {
            if (onReportIncidentListener != null) {
                onReportIncidentListener.onReportIncident(remarks);
            }
        }
    }

    @OnClick(R.id.btnCancelReport)
    public void cancel() {
        if (onReportIncidentListener != null) {
            onReportIncidentListener.onReportCancel();
        }
    }

    public interface OnReportIncidentListener {
        void onReportIncident(final String remarks);
        void onReportCancel();
    }

    public void setOnReportIncidentListener(OnReportIncidentListener onReportIncidentListener) {
        this.onReportIncidentListener = onReportIncidentListener;
    }
}
