package sanmateo.avinnovz.com.sanmateoprofile.fragments.admin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.adapter.rxjava.HttpException;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.admin.ReviewIncidentsAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.BlockIncidentReportDialogFragment;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiErrorHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiRequestHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.PrefsHelper;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnConfirmDialogListener;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.ApiError;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Incident;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.BusSingleton;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.CurrentUserSingleton;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.IncidentsSingleton;

/**
 * Created by rsbulanon on 9/4/16.
 */
public class ManageIncidentReportsFragment extends Fragment implements OnApiRequestListener {

    @BindView(R.id.rvReviewIncidents) RecyclerView rvReviewIncidents;
    private ApiRequestHelper apiRequestHelper;
    private IncidentsSingleton incidentsSingleton;
    private CurrentUserSingleton currentUserSingleton;
    private String token;
    private int selectedIndex;
    private BaseActivity activity;
    private String status;

    public static ManageIncidentReportsFragment newInstance(final String status) {
        final ManageIncidentReportsFragment fragment = new ManageIncidentReportsFragment();
        fragment.status = status;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_review_incidents, container, false);
        ButterKnife.bind(this, view);
        activity = (BaseActivity)getActivity();
        apiRequestHelper = new ApiRequestHelper(this);
        incidentsSingleton = IncidentsSingleton.getInstance();
        currentUserSingleton = CurrentUserSingleton.newInstance();
        token = currentUserSingleton.getCurrentUser().getToken();

        //check if there are new incidents needed to be fetched from api
        if (PrefsHelper.getBoolean(getActivity(),"refresh_incidents") && incidentsSingleton
                                    .getIncidents(status).size() > 0) {
            apiRequestHelper.getLatestIncidents(token,incidentsSingleton
                    .getIncidents("for approval").get(0).getIncidentId());
        } else if (incidentsSingleton.getIncidents(status).size() == 0) {
            //if incidents is empty, fetch it from api
            LogHelper.log("approval",status + " must get all");
            apiRequestHelper.getAllIncidents(token,0,null,status);
        }
        initIncidents();
        return view;
    }

    private void initIncidents() {
        final ReviewIncidentsAdapter adapter = new ReviewIncidentsAdapter(getActivity(),
                incidentsSingleton.getIncidents(status));
        adapter.setOnReportListener((index, action) -> {
            selectedIndex = index;
            final Incident incident = incidentsSingleton.getIncidents(status).get(index);
            if (action.equals("Block")) {
                final BlockIncidentReportDialogFragment fragment = BlockIncidentReportDialogFragment.newInstance();
                fragment.setOnBlockReportListener(new BlockIncidentReportDialogFragment.OnBlockReportListener() {
                    @Override
                    public void onBlockReport(String remarks) {
                        fragment.dismiss();
                        activity.showConfirmDialog("", "Block Incident Report", "You are about to block this incident report," +
                                " are you sure you want to proceed?", "Yes", "No", new OnConfirmDialogListener() {
                            @Override
                            public void onConfirmed(String action) {
                                apiRequestHelper.blockMaliciousReport(token,incident.getIncidentId(),
                                        remarks, incident.getStatus());
                            }

                            @Override
                            public void onCancelled(String action) {}
                        });
                    }

                    @Override
                    public void onCancelReport() {
                        fragment.dismiss();
                    }
                });
                fragment.show(getActivity().getFragmentManager(),"block report");
            } else {
                activity.showConfirmDialog("", action +" Incident Report", "You are about to "+ action.toLowerCase()
                                +" this incident report, are you sure you want to proceed?",
                        "Yes", "No", new OnConfirmDialogListener() {
                            @Override
                            public void onConfirmed(String a) {
                                if (action.equals("Unblock")) {
                                    apiRequestHelper.unblockMaliciousReport(token, incident.getIncidentId());
                                } else if (action.equals("Approve")) {
                                    apiRequestHelper.approveReport(token,incident.getIncidentId());
                                }
                            }

                            @Override
                            public void onCancelled(String action) {}
                        });
            }
        });
        rvReviewIncidents.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvReviewIncidents.setAdapter(adapter);
    }

    @Override
    public void onPause() {
        BusSingleton.getInstance().unregister(this);
        super.onPause();
    }

    @Override
    public void onResume() {
        BusSingleton.getInstance().register(this);
        super.onResume();
    }

    @Subscribe
    public void handleApiResponse(final HashMap<String,Object> map) {
        getActivity().runOnUiThread(() -> {
            try {
                if (map.containsKey("data")) {
                    final JSONObject json = new JSONObject(map.get("data").toString());
                    if (json.has("action")) {
                        final String action = json.getString("action");

                        /** new incident notification */
                        if (action.equals("incident_approval") || action.equals("for review")) {
                            LogHelper.log("api","must fetch latest incident reports");
                            if (incidentsSingleton.getIncidents(status).size() == 0) {
                                LogHelper.log("api","must get all STATUS --> " + status);
                                apiRequestHelper.getAllIncidents(token,0,null,status);
                            } else {
                                apiRequestHelper.getLatestIncidents(token,
                                        incidentsSingleton.getIncidents(status)
                                                .get(0).getIncidentId());
                            }
                        }
                        rvReviewIncidents.getAdapter().notifyDataSetChanged();
                    }
                } else if (map.containsKey("action")) {
                    if (map.get("action").equals("newly approved report") && status.equals("active")) {
                        /** add newly approved incident report to list of active reports */
                        final Incident incident = (Incident)map.get("result");
                        incidentsSingleton.getIncidents(status).add(0,incident);
                        rvReviewIncidents.getAdapter().notifyDataSetChanged();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onApiRequestBegin(String action) {
        LogHelper.log("api","begin --> " + action);
        if (action.equals(AppConstants.ACTION_GET_INCIDENTS)) {
            activity.showCustomProgress("Fetching all incident reports, Please wait...");
        } else if (action.equals(AppConstants.ACTION_GET_LATEST_INCIDENTS)) {
            activity.showCustomProgress("Fetching latest incident reports, Please wait...");
        } else if (action.equals(AppConstants.ACTION_PUT_BLOCK_REPORT)) {
            activity.showCustomProgress("Blocking malicious report, Please wait...");
        } else if (action.equals(AppConstants.ACTION_PUT_APPROVE_REPORT)) {
            activity.showCustomProgress("Approving report, Please wait...");
        } else if (action.equals(AppConstants.ACTION_PUT_UNBLOCK_REPORT)) {
            activity.showCustomProgress("Unblocking report, Please wait...");
        }
    }

    @Override
    public void onApiRequestSuccess(final String action,final Object result) {
        activity.dismissCustomProgress();
        if (action.equals(AppConstants.ACTION_GET_INCIDENTS) || action.equals(AppConstants.ACTION_GET_LATEST_INCIDENTS)) {
            final ArrayList<Incident> incidents = (ArrayList<Incident>)result;
            LogHelper.log("approval","success size --> " + incidents);
            incidentsSingleton.getIncidents(status).addAll(0,incidents);

            if (action.equals(AppConstants.ACTION_GET_LATEST_INCIDENTS)) {
                PrefsHelper.setBoolean(getActivity(),"refresh_incidents",false);
            }
        } else {
            final Incident incident = (Incident)result;
            incidentsSingleton.getIncidents(status).set(selectedIndex, incident);
            if (action.equals(AppConstants.ACTION_PUT_BLOCK_REPORT)) {
                activity.showToast("Malicious incident report successfully blocked");
            } else if (action.equals(AppConstants.ACTION_PUT_UNBLOCK_REPORT)) {
                activity.showToast("Incident report successfully unblocked!");
            } else if (action.equals(AppConstants.ACTION_PUT_APPROVE_REPORT)) {
                final Incident approvedReport = (Incident)result;
                activity.showToast("Incident report successfully approved!");
                /** transfer newly approved incident report to list of active reports */
                if (status.equals("for approval")) {
                    final HashMap<String,Object> map = new HashMap<>();
                    map.put("action","newly approved report");
                    map.put("result",approvedReport);
                    BusSingleton.getInstance().post(map);
                    incidentsSingleton.getIncidents(status).remove(selectedIndex);
                    rvReviewIncidents.getAdapter().notifyDataSetChanged();
                }
            }
        }
        rvReviewIncidents.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onApiRequestFailed(String action, Throwable t) {
        activity.dismissCustomProgress();
        activity.handleApiException(t);
        LogHelper.log("err","error in ---> " + action + " cause ---> " + t.getMessage());
        if (t instanceof HttpException) {
            final ApiError apiError = ApiErrorHelper.parseError(((HttpException) t).response());
            activity.showConfirmDialog(action,"Failed", apiError.getMessage(),"Close","",null);
        }
    }
}
