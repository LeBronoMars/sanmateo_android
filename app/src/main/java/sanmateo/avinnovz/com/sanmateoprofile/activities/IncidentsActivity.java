package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
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
import sanmateo.avinnovz.com.sanmateoprofile.adapters.IncidentsAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.FileIncidentReportDialogFragment;
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
public class IncidentsActivity extends BaseActivity implements OnApiRequestListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("Incidents");
        setContentView(R.layout.activity_incidents);
        ButterKnife.bind(this);
        amazonS3Helper = new AmazonS3Helper(this);
        apiRequestHelper = new ApiRequestHelper(this);
        incidentsSingleton = IncidentsSingleton.getInstance();
        currentUserSingleton = CurrentUserSingleton.newInstance();
        token = currentUserSingleton.getAuthResponse().getToken();

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
        } else if (action.equals(AppConstants.ACTION_POST_INCIDENT_REPORT)) {
            showSnackbar(btnAdd,"Your report was successfully created!");
        }
    }

    @Override
    public void onApiRequestFailed(String action, Throwable t) {
        LogHelper.log("api","failed --> " + action);
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

    @OnClick(R.id.btnAdd)
    public void fileIncidentReport() {
        final FileIncidentReportDialogFragment fragment = FileIncidentReportDialogFragment.newInstance();
        fragment.setOnFileIncidentReportListener(new FileIncidentReportDialogFragment.OnFileIncidentReportListener() {
            @Override
            public void onFileReport(String incidentDescription, String incidentLocation,
                                     String incidentType, final ArrayList<File> files) {
                LogHelper.log("s3","MUST UPLOAD FILES TO AWS S3 --> " + files.size());
                fragment.dismiss();
                bundle.putString("incidentDescription",incidentDescription);
                bundle.putString("incidentLocation",incidentLocation);
                bundle.putString("incidentType",incidentType);
                if (files.size() > 0) {
                    filesToUpload.clear();
                    filesToUpload.addAll(files);
                    filesToUploadCtr = 0;
                    uploadedFilesUrl.setLength(0);
                    showCustomProgress("Processing Images, Please wait...");
                    uploadImageToS3();
                }
            }

            @Override
            public void onCancelReport(ArrayList<File> filesToUpload) {
                if (filesToUpload.size() > 0) {
                    for (File f : filesToUpload) {
                        f.delete();
                    }
                }
                fragment.dismiss();
            }
        });
        fragment.setCancelable(false);
        fragment.show(getFragmentManager(),"file incident report");
    }

    private void uploadImageToS3() {
        if (filesToUploadCtr < filesToUpload.size()) {
            amazonS3Helper.uploadImage(filesToUpload.get(filesToUploadCtr)).setTransferListener(new TransferListener() {
                @Override
                public void onStateChanged(int id, TransferState state) {
                    if (state.name().equals("COMPLETED")) {
                        uploadedFilesUrl.append(amazonS3Helper.getResourceUrl(filesToUpload.get(filesToUploadCtr).getName())+"###");
                        if (filesToUploadCtr < filesToUpload.size()) {
                            filesToUploadCtr++;
                            uploadImageToS3();
                        }
                    }
                }

                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                    updateCustomProgress("Uploading image " + (filesToUploadCtr+1) + "/"
                            + filesToUpload.size() + " - " + bytesCurrent + "/" + bytesTotal);
                }

                @Override
                public void onError(int id, Exception ex) {

                }
            });
        } else {
            for (File f : filesToUpload) {
                f.delete();
            }
            dismissCustomProgress();
            LogHelper.log("s3","uploading of all files successfully finished!");
            LogHelper.log("s3","FINAL URL --->  " + uploadedFilesUrl.toString());
            apiRequestHelper.fileIncidentReport(token,bundle.getString("incidentLocation"),
                    bundle.getString("incidentDescription"),bundle.getString("incidentType"),
                    1,1,currentUserSingleton.getAuthResponse().getId(),
                    uploadedFilesUrl.toString());
        }
    }
}
