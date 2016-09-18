package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.AdminOfficialsRecyclerViewAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.UserOfficialsRecyclerViewAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.dao.CurrentUser;
import sanmateo.avinnovz.com.sanmateoprofile.dao.LocalOfficial;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiRequestHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.DaoHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Official;

/**
 * Created by rsbulanon on 9/15/16.
 */
public class OfficialsActivity extends BaseActivity implements OnApiRequestListener{

    @BindView(R.id.rvOfficials) RecyclerView rvOfficials;
    private CurrentUser currentUser;
    private String token;
    private ApiRequestHelper apiRequestHelper;
    private ArrayList<Official> officialList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officials);
        ButterKnife.bind(this);
        setToolbarTitle("Officials");
        currentUser = DaoHelper.getCurrentUser();
        token = currentUser.getToken();
        apiRequestHelper = new ApiRequestHelper(this);
        initOfficialsListing();
    }

    private void initOfficialsListing() {
        final UserOfficialsRecyclerViewAdapter adapter = new UserOfficialsRecyclerViewAdapter(officialList,null);
        adapter.setOnSelectOfficialListener(official -> {
            final Intent intent = new Intent(OfficialsActivity.this, OfficialFullInfoActivity.class);
            intent.putExtra("localOfficial", official);
            startActivity(intent);
        });
        rvOfficials.setAdapter(adapter);
        rvOfficials.setLayoutManager(new LinearLayoutManager(this));

        if (officialList.size() > 0) {
            rvOfficials.getAdapter().notifyDataSetChanged();
        } else {
            apiRequestHelper.getOfficials(token);
        }
    }

    @Override
    public void onApiRequestBegin(String action) {
        if (action.equals(AppConstants.ACTION_GET_OFFICIALS)) {
            showCustomProgress("Fetching list of officials, Please wait...");
        }
    }

    @Override
    public void onApiRequestSuccess(String action, Object result) {
        dismissCustomProgress();
        officialList.addAll((ArrayList<Official>)result);
        LogHelper.log("official", "success ---> " + officialList.size());
        rvOfficials.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onApiRequestFailed(String action, Throwable t) {
        LogHelper.log("official", "error ---> " + t.getMessage());
        dismissCustomProgress();
        handleApiException(t);
    }
}
