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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;

/**
 * Created by user on 5/26/16.
 */
public class CustomProgressBarDialogFragment extends DialogFragment {

    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.tvProgress) TextView tvProgress;
    private View view;
    private Dialog mDialog;
    private String message;
    private int max;
    private int current;
    private int total;

    public static CustomProgressBarDialogFragment newInstance(final int max, final int current,
                                                              final int total) {
        final CustomProgressBarDialogFragment frag = new CustomProgressBarDialogFragment();
        frag.max = max;
        frag.current = current;
        frag.total = total;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        view = getActivity().getLayoutInflater().inflate(R.layout.dialog_custom_progress_bar, null);
        ButterKnife.bind(this, view);
        if (max == 0) {
            progressBar.setVisibility(View.GONE);
        } else {
            progressBar.setMax(max);
        }
        tvProgress.setText("Uploading " + current + "/" + total + ", Please wait...");
        mDialog = new Dialog(getActivity());
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);
        return mDialog;
    }

    public void updateProgress(final int progress, final int max) {
        if (progressBar != null) {
            if (!progressBar.isShown()) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setMax(max);
            }
            progressBar.setProgress(progress);
        }
    }
}
