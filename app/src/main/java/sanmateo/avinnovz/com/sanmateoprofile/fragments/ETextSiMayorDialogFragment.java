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

import com.rey.material.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;

/**
 * Created by rsbulanon on 6/30/16.
 */
public class ETextSiMayorDialogFragment extends DialogFragment {

    @BindView(R.id.spnrClassification) Spinner spnrClassification;
    @BindView(R.id.etMessage) EditText etMessage;
    private View view;
    private Dialog mDialog;
    private BaseActivity activity;
    private OnTextMayorListener onTextMayorListener;

    public static ETextSiMayorDialogFragment newInstance() {
        final ETextSiMayorDialogFragment fragment = new ETextSiMayorDialogFragment();
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
        view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_etext_si_mayor, null);
        ButterKnife.bind(this, view);
        activity = (BaseActivity) getActivity();
        activity.initSpinner(spnrClassification, R.array.array_text_classification);
        mDialog = new Dialog(getActivity());
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout
                .LayoutParams.WRAP_CONTENT);
        return mDialog;
    }

    @OnClick(R.id.btnSend)
    public void sendSMSToMayor() {
        final String classification = spnrClassification.getSelectedItem().toString();
        final String message = etMessage.getText().toString().trim();

        if (message.isEmpty()) {
            activity.setError(etMessage, AppConstants.WARN_FIELD_REQUIRED);
        } else {
            if (onTextMayorListener != null) {
                onTextMayorListener.onSendText(classification,message);
            }
        }
    }

    @OnClick(R.id.btnCancel)
    public void cancel() {
        if (onTextMayorListener != null) {
            onTextMayorListener.onCancel();
        }
    }

    public interface OnTextMayorListener {
        void onSendText(final String classification, final String message);
        void onCancel();
    }

    public void setOnTextMayorListener(OnTextMayorListener onTextMayorListener) {
        this.onTextMayorListener = onTextMayorListener;
    }
}
