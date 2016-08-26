package sanmateo.avinnovz.com.sanmateoprofile.activities.admin;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
import sanmateo.avinnovz.com.sanmateoprofile.singletons.CurrentUserSingleton;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.IncidentsSingleton;

/**
 * Created by rsbulanon on 6/28/16.
 */
public class ReviewIncidentsActivity extends BaseActivity implements OnApiRequestListener {

    @BindView(R.id.rvReviewIncidents) RecyclerView rvReviewIncidents;
    private ApiRequestHelper apiRequestHelper;
    private IncidentsSingleton incidentsSingleton;
    private CurrentUserSingleton currentUserSingleton;
    private String token;
    private int selectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("Incidents");
        setContentView(R.layout.activity_review_incidents);
        ButterKnife.bind(this);
        apiRequestHelper = new ApiRequestHelper(this);
        incidentsSingleton = IncidentsSingleton.getInstance();
        currentUserSingleton = CurrentUserSingleton.newInstance();
        token = currentUserSingleton.getCurrentUser().getToken();

        //check if there are new incidents needed to be fetched from api
        if (PrefsHelper.getBoolean(this,"refresh_incidents") && incidentsSingleton.getIncidents().size() > 0) {
            apiRequestHelper.getLatestIncidents(token,incidentsSingleton
                    .getIncidents().get(0).getIncidentId());
        } else if (incidentsSingleton.getIncidents().size() == 0) {
            //if incidents is empty, fetch it from api
            LogHelper.log("api","must get all");
            apiRequestHelper.getAllIncidents(token,0,null,null);
        }
        initIncidents();
    }

    @Override
    public void onApiRequestBegin(String action) {
        LogHelper.log("api","begin --> " + action);
        if (action.equals(AppConstants.ACTION_GET_INCIDENTS)) {
            showCustomProgress("Fetching all incident reports, Please wait...");
        } else if (action.equals(AppConstants.ACTION_GET_LATEST_INCIDENTS)) {
            showCustomProgress("Fetching latest incident reports, Please wait...");
        } else if (action.equals(AppConstants.ACTION_PUT_BLOCK_REPORT)) {
            showCustomProgress("Blocking malicious report, Please wait...");
        } else if (action.equals(AppConstants.ACTION_PUT_APPROVE_REPORT)) {
            showCustomProgress("Approving report, Please wait...");
        } else if (action.equals(AppConstants.ACTION_PUT_UNBLOCK_REPORT)) {
            showCustomProgress("Unblocking report, Please wait...");
        }
    }

    @Override
    public void onApiRequestSuccess(final String action,final Object result) {
        dismissCustomProgress();
        if (action.equals(AppConstants.ACTION_GET_INCIDENTS) || action.equals(AppConstants.ACTION_GET_LATEST_INCIDENTS)) {
            final ArrayList<Incident> incidents = (ArrayList<Incident>)result;
            LogHelper.log("api","success size --> " + incidents);
            incidentsSingleton.getIncidents().addAll(0,incidents);

            if (action.equals(AppConstants.ACTION_GET_LATEST_INCIDENTS)) {
                PrefsHelper.setBoolean(this,"refresh_incidents",false);
            }
        } else {
            final Incident incident = (Incident)result;
            incidentsSingleton.getIncidents().set(selectedIndex, incident);
            if (action.equals(AppConstants.ACTION_PUT_BLOCK_REPORT)) {
                showToast("Malicious incident report successfully blocked");
            } else if (action.equals(AppConstants.ACTION_PUT_UNBLOCK_REPORT)) {
                showToast("Incident report successfully unblocked!");
            } else if (action.equals(AppConstants.ACTION_PUT_APPROVE_REPORT)) {
                showToast("Incident report successfully approved!");
            }
        }
        rvReviewIncidents.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onApiRequestFailed(String action, Throwable t) {
        dismissCustomProgress();
        handleApiException(t);
        LogHelper.log("err","error in ---> " + action + " cause ---> " + t.getMessage());
        if (t instanceof HttpException) {
            if (action.equals(AppConstants.ACTION_LOGIN)) {
                final ApiError apiError = ApiErrorHelper.parseError(((HttpException) t).response());
                showConfirmDialog(action,"Failed", apiError.getMessage(),"Close","",null);
            }
        }
    }

    private void initIncidents() {
        final ReviewIncidentsAdapter adapter = new ReviewIncidentsAdapter(this,incidentsSingleton.getIncidents());
        adapter.setOnReportListener((index, action) -> {
            selectedIndex = index;
            final Incident incident = incidentsSingleton.getIncidents().get(index);
            if (action.equals("Block")) {
                final BlockIncidentReportDialogFragment fragment = BlockIncidentReportDialogFragment.newInstance();
                fragment.setOnBlockReportListener(remarks -> {
                    fragment.dismiss();
                    showConfirmDialog("", "Block Incident Report", "You are about to block this incident report," +
                            " are you sure you want to proceed?", "Yes", "No", new OnConfirmDialogListener() {
                        @Override
                        public void onConfirmed(String action) {
                            apiRequestHelper.blockMaliciousReport(token,incident.getIncidentId(),remarks);
                        }

                        @Override
                        public void onCancelled(String action) {}
                    });
                });
                fragment.show(getFragmentManager(),"block report");
            } else {
                showConfirmDialog("", action +" Incident Report", "You are about to "+ action.toLowerCase()
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
        rvReviewIncidents.setLayoutManager(new LinearLayoutManager(this));
        rvReviewIncidents.setAdapter(adapter);
    }

    @Subscribe
    public void handleApiResponse(final HashMap<String,Object> map) {
        runOnUiThread(() -> {
            try {
                final JSONObject json = new JSONObject(map.get("data").toString());
                if (json.has("action")) {
                    final String action = json.getString("action");

                    /** new incident notification */
                    if (action.equals("incident_approval")) {
                        LogHelper.log("api","must fetch latest incident reports");
                        if (incidentsSingleton.getIncidents().size() == 0) {
                            //if incidents is empty, fetch it from api
                            LogHelper.log("api","must get all");
                            apiRequestHelper.getAllIncidents(token,0,null,"pending");
                        } else {
                            apiRequestHelper.getLatestIncidents(token,
                                    incidentsSingleton.getIncidents().get(0).getIncidentId());
                        }
                    }
                    rvReviewIncidents.getAdapter().notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }
}
