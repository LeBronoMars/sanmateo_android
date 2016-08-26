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
import android.widget.ArrayAdapter;
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
public class WaterLevelNotifDialogFragment extends DialogFragment {

    @BindView(R.id.spnrArea) Spinner spnrArea;
    @BindView(R.id.etWaterLevel) EditText etWaterLevel;
    private View view;
    private Dialog mDialog;
    private BaseActivity activity;
    private OnWaterLevelNotificationListener onWaterLevelNotificationListener;

    public static WaterLevelNotifDialogFragment newInstance() {
        final WaterLevelNotifDialogFragment fragment = new WaterLevelNotifDialogFragment();
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
        view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_water_level_notif, null);
        ButterKnife.bind(this, view);
        activity = (BaseActivity) getActivity();
        activity.initSpinner(spnrArea,R.array.array_flood_prone_areas);
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
    public void sendNotif() {
        final String area = spnrArea.getSelectedItem().toString();
        final String level = etWaterLevel.getText().toString().trim();

        if (level.isEmpty()) {
            activity.setError(etWaterLevel, AppConstants.WARN_FIELD_REQUIRED);
        } else {
            if (onWaterLevelNotificationListener != null) {
                onWaterLevelNotificationListener.onAnnounceNotif(area,Double.valueOf(level));
            }
        }
    }

    @OnClick(R.id.btnCancel)
    public void cancel() {
        if (onWaterLevelNotificationListener != null) {
            onWaterLevelNotificationListener.onCancel();
        }
    }

    public interface OnWaterLevelNotificationListener {
        void onAnnounceNotif(final String area, final double level);
        void onCancel();
    }

    public void setOnWaterLevelNotificationListener(OnWaterLevelNotificationListener onWaterLevelNotificationListener) {
        this.onWaterLevelNotificationListener = onWaterLevelNotificationListener;
    }
}
