package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnS3UploadListener;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.ApiError;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Incident;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.CurrentUserSingleton;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.IncidentsSingleton;

/**
 * Created by rsbulanon on 6/28/16.
 */
public class IncidentsActivity extends BaseActivity implements OnApiRequestListener, OnS3UploadListener {

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
    private String status = "active";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("Incidents");
        setContentView(R.layout.activity_incidents);
        ButterKnife.bind(this);
        initAmazonS3Helper(this);
        amazonS3Helper = new AmazonS3Helper(this);
        apiRequestHelper = new ApiRequestHelper(this);
        incidentsSingleton = IncidentsSingleton.getInstance();
        currentUserSingleton = CurrentUserSingleton.newInstance();
        token = currentUserSingleton.getCurrentUser().getToken();
        shareCallBackManager = CallbackManager.Factory.create();

        //check if there are new incidents needed to be fetched from api
        if (PrefsHelper.getBoolean(this,"refresh_incidents") && incidentsSingleton.getIncidents(status).size() > 0) {
            apiRequestHelper.getLatestIncidents(token,incidentsSingleton.getIncidents(status).get(0).getIncidentId());
        } else if (incidentsSingleton.getIncidents(status).size() == 0) {
            //if incidents is empty, fetch it from api
            LogHelper.log("api","must get all");
            apiRequestHelper.getAllIncidents(token,0,null,"active");
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
            showCustomProgress("Submitting your report, Please wait...");
        }
    }

    @Override
    public void onApiRequestSuccess(final String action,final Object result) {
        dismissCustomProgress();
        if (action.equals(AppConstants.ACTION_GET_INCIDENTS) || action.equals(AppConstants.ACTION_GET_LATEST_INCIDENTS)) {
            final ArrayList<Incident> incidents = (ArrayList<Incident>)result;
            LogHelper.log("api","success size --> " + incidents);
            incidentsSingleton.getIncidents(status).addAll(0,incidents);
            rvIncidents.getAdapter().notifyDataSetChanged();
            if (action.equals(AppConstants.ACTION_GET_LATEST_INCIDENTS)) {
                PrefsHelper.setBoolean(this,"refresh_incidents",false);
            }
        } else if (action.equals(AppConstants.ACTION_POST_REPORT_MALICIOUS_INCIDENT)) {
            showConfirmDialog("","Malicious Incident Report","You have successfully filed a complaint about this " +
                    "incident report.","Close","",null);
        } else if (action.equals(AppConstants.ACTION_POST_INCIDENT_REPORT)) {
            showConfirmDialog("","Incident Report","You have successfully filed an incident report" +
                    ". Admins will review it first for publication.","Close","",null);
        }
    }

    @Override
    public void onApiRequestFailed(String action, Throwable t) {
        dismissCustomProgress();
        handleApiException(t);
        LogHelper.log("err","error in ---> " + action + " cause ---> " + t.getMessage());
        if (t instanceof HttpException) {
            final ApiError apiError = ApiErrorHelper.parseError(((HttpException) t).response());
            showConfirmDialog(action,"Failed", apiError.getMessage(),"Close","",null);
        }
    }

    private void initIncidents() {
        final IncidentsAdapter adapter = new IncidentsAdapter(this,incidentsSingleton.getIncidents(status));
        adapter.setOnShareAndReportListener(new IncidentsAdapter.OnShareAndReportListener() {
            @Override
            public void onShare(int position) {
                if (isNetworkAvailable()) {
                    final Incident incident = incidentsSingleton.getIncidents(status).get(position);
                    final String imageUrl = incident.getImages().contains("###") ?
                            incident.getImages().split("###")[0] : "";
                    final ShareDialog shareDialog = new ShareDialog(IncidentsActivity.this);
                    shareDialog.registerCallback(shareCallBackManager, new FacebookCallback<Sharer.Result>() {
                        @Override
                        public void onSuccess(Sharer.Result result) {
                            LogHelper.log("fb","Successfully shared in facebook ---> " + result.getPostId());
                        }

                        @Override
                        public void onCancel() {

                        }

                        @Override
                        public void onError(FacebookException error) {
                            LogHelper.log("fb","Unable to share with error --> " + error.getMessage());
                        }
                    });
                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                        final ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                .setContentTitle(incident.getIncidentDescription())
                                .setImageUrl(Uri.parse(imageUrl))
                                .setContentUrl(Uri.parse("www.google.com"))
                                .setContentDescription(incident.getIncidentAddress())
                                .build();
                        shareDialog.show(linkContent, AppConstants.IS_FACEBOOK_APP_INSTALLED
                                ? ShareDialog.Mode.NATIVE : ShareDialog.Mode.FEED);
                    }
                } else {
                    showSnackbar(btnAdd, AppConstants.WARN_CONNECTION);
                }
            }

            @Override
            public void onReport(int position) {
                final Incident incident = incidentsSingleton.getIncidents(status).get(position);
                final ReportIncidentReportDialogFragment fragment = ReportIncidentReportDialogFragment.newInstance();
                fragment.setOnReportIncidentListener(new ReportIncidentReportDialogFragment.OnReportIncidentListener() {
                    @Override
                    public void onReportIncident(String remarks) {
                        fragment.dismiss();
                        apiRequestHelper.reportMaliciousIncidentReport(token,incident.getIncidentId(),
                                incident.getReporterId(),currentUserSingleton.getCurrentUser().getUserId(),
                                remarks);
                    }

                    @Override
                    public void onReportCancel() {
                        fragment.dismiss();
                    }
                });
                fragment.show(getFragmentManager(),"report incident");
            }
        });
        rvIncidents.setLayoutManager(new LinearLayoutManager(this));
        rvIncidents.setAdapter(adapter);
    }

    @Subscribe
    public void handleApiResponse(final HashMap<String,Object> map) {
        runOnUiThread(() -> {
            try {
                final JSONObject json = new JSONObject(map.get("data").toString());
                if (json.has("action")) {
                    final String action = json.getString("action");

                    /** new incident notification */
                    if (action.equals("new incident")) {
                        LogHelper.log("api","must fetch latest incident reports");
                        if (incidentsSingleton.getIncidents(status).size() == 0) {
                            //if incidents is empty, fetch it from api
                            LogHelper.log("api","must get all");
                            apiRequestHelper.getAllIncidents(token,0,null,"active");
                        } else {
                            apiRequestHelper.getLatestIncidents(token,incidentsSingleton
                                    .getIncidents(status).get(0).getIncidentId());
                        }
                    }
                    rvIncidents.getAdapter().notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    @OnClick(R.id.btnAdd)
    public void fileIncidentReport() {
        final FileIncidentReportDialogFragment fragment = FileIncidentReportDialogFragment.newInstance();
        fragment.setOnFileIncidentReportListener(new FileIncidentReportDialogFragment.OnFileIncidentReportListener() {
            @Override
            public void onFileReport(String incidentDescription, String incidentLocation,
                                     String incidentType, final ArrayList<File> files) {
                fragment.dismiss();
                if (files.size() > 0) {
                    bundle.putString("incidentDescription",incidentDescription);
                    bundle.putString("incidentLocation",incidentLocation);
                    bundle.putString("incidentType",incidentType);
                    filesToUpload.clear();
                    filesToUpload.addAll(files);
                    filesToUploadCtr = 0;
                    uploadedFilesUrl.setLength(0);
                    uploadImage();
                } else {
                    apiRequestHelper.fileIncidentReport(token,incidentLocation,
                            incidentDescription,incidentType, 1,1,
                            currentUserSingleton.getCurrentUser().getUserId(), "");
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

    private void uploadImage() {
        if (filesToUploadCtr < filesToUpload.size()) {
            uploadImageToS3(AppConstants.BUCKET_INCIDENTS,filesToUpload.get(filesToUploadCtr),
                    filesToUploadCtr+1,filesToUpload.size());
        } else {
            for (File f : filesToUpload) {
                f.delete();
            }
            dismissCustomProgress();
            LogHelper.log("s3","uploading of all files successfully finished!");
            LogHelper.log("s3","FINAL URL --->  " + uploadedFilesUrl.toString());
            apiRequestHelper.fileIncidentReport(token,bundle.getString("incidentLocation"),
                    bundle.getString("incidentDescription"),bundle.getString("incidentType"),
                    1,1,currentUserSingleton.getCurrentUser().getUserId(),
                    uploadedFilesUrl.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        shareCallBackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onUploadFinished(String bucketName, String imageUrl) {
        if (bucketName.equals(AppConstants.BUCKET_INCIDENTS)) {
            filesToUploadCtr++;
        }
        uploadImage();
    }
}
