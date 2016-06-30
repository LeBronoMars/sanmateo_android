package sanmateo.avinnovz.com.sanmateoprofile.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.rey.material.widget.Spinner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;

/**
 * Created by rsbulanon on 6/30/16.
 */
public class FileIncidentReportDialogFragment extends DialogFragment {

    @BindView(R.id.spnrIncidentType) Spinner spnrIncidentType;
    @BindView(R.id.rvImages) RecyclerView rvImages;
    private View view;
    private Dialog mDialog;
    private BaseActivity activity;

    public static FileIncidentReportDialogFragment newInstance() {
        final FileIncidentReportDialogFragment fragment = new FileIncidentReportDialogFragment();
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
        view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_file_incident_report, null);
        ButterKnife.bind(this, view);
        initIncidentTypeList();
        activity = (BaseActivity) getActivity();
        mDialog = new Dialog(getActivity());
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout
                .LayoutParams.WRAP_CONTENT);
        return mDialog;
    }

    private void initIncidentTypeList() {
        final ArrayList<String> items = new ArrayList<>();
        items.add("Traffic road report");
        items.add("Accident report");
        items.add("Waste disposal report");
        items.add("Crime/Illegal drug report");
        items.add("Flood report");
        items.add("Environmental report");
        items.add("Disaster report");
        initSpinner(spnrIncidentType, items);
    }

    private void initSpinner(final Spinner spinner, final ArrayList<String> items) {
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.row_spinner, items);
        adapter.setDropDownViewResource(R.layout.row_spinner_dropdown);
        spinner.setAdapter(adapter);
    }

    @OnClick(R.id.llAddPhoto)
    public void addPhoto() {

    }
}
