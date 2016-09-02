package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.OfficialsRecyclerViewAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.dao.CurrentUser;
import sanmateo.avinnovz.com.sanmateoprofile.dao.LocalOfficial;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiRequestHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.DaoHelper;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Official;

/**
 * Created by ctmanalo on 7/7/16.
 */
public class OfficialsActivity extends BaseActivity implements OnApiRequestListener{

    @BindView(R.id.rvOfficials) RecyclerView rvOfficials;
    private CurrentUser currentUser;
    private String token;
    private ApiRequestHelper apiRequestHelper;
    private ArrayList<LocalOfficial> officialList = new ArrayList<>();

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
        final OfficialsRecyclerViewAdapter adapter = new OfficialsRecyclerViewAdapter(officialList,null);
        adapter.setOnSelectOfficialListener(official -> {
            final Intent intent = new Intent(OfficialsActivity.this, OfficialFullInfoActivity.class);
            intent.putExtra("localOfficial", official);
            startActivity(intent);
        });
        rvOfficials.setAdapter(adapter);
        rvOfficials.setLayoutManager(new LinearLayoutManager(this));
        officialList.addAll(DaoHelper.getAllOfficials());
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
        if (action.equals(AppConstants.ACTION_GET_OFFICIALS)) {
            final ArrayList<Official> officials = (ArrayList<Official>)result;
            for (Official o : officials) {
                addOfficialToList(o);
            }
        }
        rvOfficials.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onApiRequestFailed(String action, Throwable t) {
        dismissCustomProgress();
        handleApiException(t);
    }

    private void addOfficialToList(final Official o) {
        final LocalOfficial localOfficial = new LocalOfficial(null,
                o.getId(),o.getCreatedAt(),o.getUpdatedAt(),o.getDeletedAt(),
                o.getFirstName(),o.getLastName(),o.getNickName(),o.getPosition(),
                o.getZindex(),o.getBackground(),o.getPic(),o.getStatus());
        DaoHelper.createOfficial(localOfficial);
        officialList.add(localOfficial);
    }
}
