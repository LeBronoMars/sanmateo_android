package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiRequestHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.PrefsHelper;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.IncidentsSingleton;

/**
 * Created by rsbulanon on 6/28/16.
 */
public class IncidentsActivity extends BaseActivity implements OnApiRequestListener {

    @BindView(R.id.rvIncidents) RecyclerView rvIncidents;
    private ApiRequestHelper apiRequestHelper;
    private IncidentsSingleton incidentsSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("Incidents");
        setContentView(R.layout.activity_incidents);
        ButterKnife.bind(this);
        apiRequestHelper = new ApiRequestHelper(this);
        incidentsSingleton = IncidentsSingleton.getInstance();

        //check if there are new incidents needed to be fetched from api
        if (PrefsHelper.getBoolean(this,"refresh_incidents") && incidentsSingleton.getIncidents().size() > 0) {
            
        }
    }

    @Override
    public void onApiRequestBegin(String action) {

    }

    @Override
    public void onApiRequestSuccess(String action, Object result) {

    }

    @Override
    public void onApiRequestFailed(String action, Throwable t) {

    }
}
