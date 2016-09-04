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
import sanmateo.avinnovz.com.sanmateoprofile.adapters.admin.ForReviewIncidentAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.BlockIncidentReportDialogFragment;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiErrorHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiRequestHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.PrefsHelper;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnConfirmDialogListener;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.ApiError;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.ForReviewIncident;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Incident;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.BusSingleton;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.CurrentUserSingleton;

/**
 * Created by rsbulanon on 9/4/16.
 */
public class ForReviewIncidentsDialogFragment extends Fragment implements OnApiRequestListener {

    @BindView(R.id.rvReviewIncidents) RecyclerView rvReviewIncidents;
    private ApiRequestHelper apiRequestHelper;
    private CurrentUserSingleton currentUserSingleton;
    private String token;
    private int selectedIndex;
    private BaseActivity activity;
    private String status = "for review";
    private ArrayList<ForReviewIncident> forReviewIncidents = new ArrayList<>();

    public static ForReviewIncidentsDialogFragment newInstance() {
        final ForReviewIncidentsDialogFragment fragment = new ForReviewIncidentsDialogFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_review_incidents, container, false);
        ButterKnife.bind(this, view);
        activity = (BaseActivity)getActivity();
        apiRequestHelper = new ApiRequestHelper(this);
        currentUserSingleton = CurrentUserSingleton.newInstance();
        token = currentUserSingleton.getCurrentUser().getToken();

        //check if there are new incidents needed to be fetched from api
        if (PrefsHelper.getBoolean(getActivity(),"refresh_incidents") && forReviewIncidents.size() > 0) {
            apiRequestHelper.getLatestIncidents(token,forReviewIncidents.get(0).getIncidentId());
        } else if (forReviewIncidents.size() == 0) {
            //if incidents is empty, fetch it from api
            apiRequestHelper.getAllIncidentsForReview(token,0,null);
        }
        initIncidents();
        return view;
    }

    private void initIncidents() {
        final ForReviewIncidentAdapter adapter = new ForReviewIncidentAdapter(getActivity(), forReviewIncidents);
        adapter.setOnReportListener((index, action) -> {
            selectedIndex = index;
            final ForReviewIncident incident = forReviewIncidents.get(index);
            if (action.equals("Approve")) {
                activity.showConfirmDialog("", "Approve malicious Report", "You are about to approve this" +
                        " malicious incident report. By doing so, this report will be tagged as blocked," +
                        " are you sure you want to proceed?", "Yes", "No", new OnConfirmDialogListener() {
                    @Override
                    public void onConfirmed(String action) {
                        apiRequestHelper.blockMaliciousReport(token,incident.getIncidentId(),
                                incident.getRemarks(), incident.getIncidentStatus());
                    }

                    @Override
                    public void onCancelled(String action) {}
                });
            } else {
                activity.showConfirmDialog("", "Disapprove malicious report", "You are about to "+ action.toLowerCase()
                                +" this malicious incident report, are you sure you want to proceed?",
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

                        LogHelper.log("block","action ---> " + action);
                        rvReviewIncidents.getAdapter().notifyDataSetChanged();
                    }
                } else if (map.containsKey("action")) {

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
        if (action.equals(AppConstants.ACTION_GET_ALL_FOR_REVIEWS)) {
            final ArrayList<ForReviewIncident> incidents = (ArrayList<ForReviewIncident>)result;
            forReviewIncidents.addAll(0, incidents);
        } else {
            if (action.equals(AppConstants.ACTION_PUT_BLOCK_REPORT)) {
                activity.showToast("Malicious incident report successfully blocked");
                forReviewIncidents.remove(selectedIndex);
            } else if (action.equals(AppConstants.ACTION_PUT_UNBLOCK_REPORT)) {
                activity.showToast("Incident report successfully unblocked!");
            } else if (action.equals(AppConstants.ACTION_PUT_APPROVE_REPORT)) {
                final Incident approvedReport = (Incident)result;
                activity.showToast("Incident report successfully approved!");
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
