package sanmateo.avinnovz.com.sanmateoprofile.activities.admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.OfficialsRecyclerViewAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.dao.CurrentUser;
import sanmateo.avinnovz.com.sanmateoprofile.dao.LocalOfficial;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiRequestHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.DaoHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Official;

/**
 * Created by rsbulanon on 8/23/16.
 */
public class ManageOfficialsActivity extends BaseActivity implements OnApiRequestListener {

    @BindView(R.id.rvOfficials) RecyclerView rvOfficials;
    private CurrentUser currentUser;
    private String token;
    private ApiRequestHelper apiRequestHelper;
    private ArrayList<LocalOfficial> officialList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_officials);
        ButterKnife.bind(this);
        setToolbarTitle("Manage Officials");
        currentUser = DaoHelper.getCurrentUser();
        token = currentUser.getToken();
        apiRequestHelper = new ApiRequestHelper(this);
        initOfficialsListing();
    }

    private void initOfficialsListing() {
        rvOfficials.setAdapter(new OfficialsRecyclerViewAdapter(officialList));
        rvOfficials.setLayoutManager(new LinearLayoutManager(this));
        officialList.addAll(DaoHelper.getAllOfficials());
        if (officialList.size() > 0) {
            rvOfficials.getAdapter().notifyDataSetChanged();
        } else {
            apiRequestHelper.getOfficials(token);
        }
    }

    @OnClick(R.id.btnAdd)
    public void addNewOfficial() {

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
                final LocalOfficial localOfficial = new LocalOfficial(null,
                        o.getId(),o.getCreatedAt(),o.getUpdatedAt(),o.getDeletedAt(),
                        o.getFirstName(),o.getLastName(),o.getNickName(),o.getPosition(),
                        o.getBackground(),o.getPic(),o.getStatus());
                DaoHelper.createOfficial(localOfficial);
                officialList.add(localOfficial);
            }
            LogHelper.log("officials","new size --> " + officialList.size());
            rvOfficials.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void onApiRequestFailed(String action, Throwable t) {
        dismissCustomProgress();
        LogHelper.log("err","error in fetching list of officials --> " + t.getMessage());
    }
}
