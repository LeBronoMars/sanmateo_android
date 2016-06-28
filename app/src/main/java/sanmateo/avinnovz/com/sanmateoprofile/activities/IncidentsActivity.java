package sanmateo.avinnovz.com.sanmateoprofile.activities;

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
import sanmateo.avinnovz.com.sanmateoprofile.adapters.IncidentsAdapter;
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
    private ApiRequestHelper apiRequestHelper;
    private IncidentsSingleton incidentsSingleton;
    private CurrentUserSingleton currentUserSingleton;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("Incidents");
        setContentView(R.layout.activity_incidents);
        ButterKnife.bind(this);
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
}
