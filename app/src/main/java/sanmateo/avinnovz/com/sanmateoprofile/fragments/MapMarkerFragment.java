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

import butterknife.ButterKnife;
import butterknife.OnClick;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;

/**
 * Created by ctmanalo on 7/15/16.
 */
public class MapMarkerFragment extends DialogFragment {

    private Dialog mDialog;
    private View view;
    private BaseActivity activity;

    public static MapMarkerFragment newInstance() {
        final MapMarkerFragment fragment = new MapMarkerFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_marker_details, null);
        ButterKnife.bind(this, view);

        activity = (BaseActivity) getActivity();
        mDialog = new Dialog(activity);

        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setCancelable(false);
        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout
                .LayoutParams.WRAP_CONTENT);
        return mDialog;
    }

    @OnClick(R.id.btnClose)
    public void closeDialog() {
        mDialog.dismiss();
    }
}
