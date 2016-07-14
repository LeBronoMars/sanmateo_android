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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.DisasterMenuAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;

/**
 * Created by rsbulanon on 6/30/16.
 */
public class DisasterMgtMenuDialogFragment extends DialogFragment {

    @BindView(R.id.lvDisasterMenu) ListView lvDisasterMenu;
    private View view;
    private Dialog mDialog;
    private BaseActivity activity;
    private OnSelectDisasterMenuListener onSelectDisasterMenuListener;

    public static DisasterMgtMenuDialogFragment newInstance() {
        final DisasterMgtMenuDialogFragment fragment = new DisasterMgtMenuDialogFragment();
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
        view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_disaster_mgmt, null);
        ButterKnife.bind(this, view);
        activity = (BaseActivity) getActivity();
        mDialog = new Dialog(getActivity());

        final ArrayList<String> menu = new ArrayList<>();
        menu.add("Public Announcements");
        menu.add("Typhoon Watch");
        menu.add("Water Level Monitoring");
        menu.add("Global Disaster Monitoring");
        menu.add("Emergency Numbers");
        menu.add("Emergency Flashlight");
        menu.add("SOS Signal");

        final DisasterMenuAdapter adapter = new DisasterMenuAdapter(getActivity(),menu);
        lvDisasterMenu.setAdapter(adapter);
        lvDisasterMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                onSelectDisasterMenuListener.onSelectedMenu(i);
            }
        });

        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout
                .LayoutParams.WRAP_CONTENT);
        return mDialog;
    }

    @OnClick(R.id.btnClose)
    public void cancel() {
        if (onSelectDisasterMenuListener != null) {
            onSelectDisasterMenuListener.onClose();
        }
    }

    public interface OnSelectDisasterMenuListener {
        void onSelectedMenu(final int position);
        void onClose();
    }

    public void setOnSelectDisasterMenuListener(OnSelectDisasterMenuListener onSelectDisasterMenuListener) {
        this.onSelectDisasterMenuListener = onSelectDisasterMenuListener;
    }
}
