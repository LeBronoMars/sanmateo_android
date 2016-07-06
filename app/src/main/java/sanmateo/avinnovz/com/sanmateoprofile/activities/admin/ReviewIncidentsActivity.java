package sanmateo.avinnovz.com.sanmateoprofile.activities.admin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.otto.Subscribe;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.adapter.rxjava.HttpException;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.IncidentsAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.FileIncidentReportDialogFragment;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.ReportIncidentReportDialogFragment;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AmazonS3Helper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiErrorHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiRequestHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.PrefsHelper;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.ApiError;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Incident;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.CurrentUserSingleton;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.IncidentsSingleton;

/**
 * Created by rsbulanon on 6/28/16.
 */
public class ReviewIncidentsActivity extends BaseActivity implements OnApiRequestListener {

    @BindView(R.id.rvIncidents) RecyclerView rvIncidents;
    @BindView(R.id.btnAdd) FloatingActionButton btnAdd;
    private ApiRequestHelper apiRequestHelper;
    private IncidentsSingleton incidentsSingleton;
    private CurrentUserSingleton currentUserSingleton;
    private String token;
    private Bundle bundle = new Bundle();
    private AmazonS3Helper amazonS3Helper;
    private int filesToUploadCtr = 0;
    private ArrayList<File> filesToUpload = new ArrayList<>();
    private StringBuilder uploadedFilesUrl = new StringBuilder();
    private CallbackManager shareCallBackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("Incidents");
        setContentView(R.layout.activity_review_incidents);
        ButterKnife.bind(this);
        amazonS3Helper = new AmazonS3Helper(this);
        apiRequestHelper = new ApiRequestHelper(this);
        incidentsSingleton = IncidentsSingleton.getInstance();
        currentUserSingleton = CurrentUserSingleton.newInstance();
        token = currentUserSingleton.getAuthResponse().getToken();
        shareCallBackManager = CallbackManager.Factory.create();

        //check if there are new incidents needed to be fetched from api
        if (PrefsHelper.getBoolean(this,"refresh_incidents") && incidentsSingleton.getIncidents().size() > 0) {
            apiRequestHelper.getLatestIncidents(token,incidentsSingleton.getIncidents().get(0).getIncidentId());
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
        } else if (action.equals(AppConstants.ACTION_POST_INCIDENT_REPORT)) {
            showCustomProgress("Filing your incident report, Please wait...");
        } else if (action.equals(AppConstants.ACTION_POST_REPORT_MALICIOUS_INCIDENT)) {
            showCustomProgress("Submiting your report, Please wait...");
        }
    }

    @Override
    public void onApiRequestSuccess(final String action,final Object result) {
        dismissCustomProgress();
        if (action.equals(AppConstants.ACTION_GET_INCIDENTS) || action.equals(AppConstants.ACTION_GET_LATEST_INCIDENTS)) {
            final ArrayList<Incident> incidents = (ArrayList<Incident>)result;
            LogHelper.log("api","success size --> " + incidents);
            incidentsSingleton.getIncidents().addAll(0,incidents);
            rvIncidents.getAdapter().notifyDataSetChanged();
            if (action.equals(AppConstants.ACTION_GET_LATEST_INCIDENTS)) {
                PrefsHelper.setBoolean(this,"refresh_incidents",false);
            }
        } else if (action.equals(AppConstants.ACTION_POST_INCIDENT_REPORT) ||
                action.equals(AppConstants.ACTION_POST_REPORT_MALICIOUS_INCIDENT)) {
            showSnackbar(btnAdd,"Your report was successfully created!");
        }
    }

    @Override
    public void onApiRequestFailed(String action, Throwable t) {
        dismissCustomProgress();
        LogHelper.log("err","error in ---> " + action + " cause ---> " + t.getMessage());
        if (t instanceof HttpException) {
            if (action.equals(AppConstants.ACTION_LOGIN)) {
                final ApiError apiError = ApiErrorHelper.parseError(((HttpException) t).response());
                showConfirmDialog(action,"Failed", apiError.getMessage(),"Close","",null);
            }
        }
    }

    private void initIncidents() {
        final IncidentsAdapter adapter = new IncidentsAdapter(this,incidentsSingleton.getIncidents());
        rvIncidents.setLayoutManager(new LinearLayoutManager(this));
        rvIncidents.setAdapter(adapter);
    }

    @Subscribe
    public void handleApiResponse(final HashMap<String,Object> map) {
        try {
            final JSONObject json = new JSONObject(map.get("data").toString());
            if (json.has("action")) {
                final String action = json.getString("action");

                /** new incident notification */
                if (action.equals("new incident")) {
                    LogHelper.log("api","must fetch latest incident reports");
                    if (incidentsSingleton.getIncidents().size() == 0) {
                        //if incidents is empty, fetch it from api
                        LogHelper.log("api","must get all");
                        apiRequestHelper.getAllIncidents(token,0,null,null);
                    } else {
                        apiRequestHelper.getLatestIncidents(token,incidentsSingleton.getIncidents().get(0).getIncidentId());
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
