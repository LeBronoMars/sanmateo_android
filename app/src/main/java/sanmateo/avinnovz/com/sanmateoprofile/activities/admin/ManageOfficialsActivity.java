package sanmateo.avinnovz.com.sanmateoprofile.activities.admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.AdminOfficialsRecyclerViewAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.dao.CurrentUser;
import sanmateo.avinnovz.com.sanmateoprofile.dao.LocalOfficial;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.admin.AddOfficialDialogFragment;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiRequestHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.DaoHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.SimpleItemTouchHelperCallback;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnS3UploadListener;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnStartDragListener;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Official;

/**
 * Created by rsbulanon on 8/23/16.
 */
public class ManageOfficialsActivity extends BaseActivity implements OnApiRequestListener,
                                                                    OnS3UploadListener,
                                                                    OnStartDragListener {

    @BindView(R.id.rvOfficials) RecyclerView rvOfficials;
    private CurrentUser currentUser;
    private String token;
    private ApiRequestHelper apiRequestHelper;
    private ArrayList<LocalOfficial> officialList = new ArrayList<>();
    private Bundle bundle = new Bundle();
    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_officials);
        ButterKnife.bind(this);
        setToolbarTitle("Manage Officials");
        initAmazonS3Helper(this);
        currentUser = DaoHelper.getCurrentUser();
        token = currentUser.getToken();
        apiRequestHelper = new ApiRequestHelper(this);
        initOfficialsListing();
    }

    private void initOfficialsListing() {
        final AdminOfficialsRecyclerViewAdapter adapter = new AdminOfficialsRecyclerViewAdapter(officialList, this);
        rvOfficials.setAdapter(adapter);
        rvOfficials.setHasFixedSize(true);

        rvOfficials.setLayoutManager(new LinearLayoutManager(this));
        officialList.addAll(DaoHelper.getAllOfficials());
        if (officialList.size() > 0) {
            rvOfficials.getAdapter().notifyDataSetChanged();
        } else {
            apiRequestHelper.getOfficials(token);
        }

        final ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(rvOfficials);
    }

    @OnClick(R.id.btnAdd)
    public void addNewOfficial() {
        final AddOfficialDialogFragment addOfficialDialogFragment = AddOfficialDialogFragment.newInstance();
        addOfficialDialogFragment.setOnCreateNewsListener(new AddOfficialDialogFragment.OnCreateOfficialListener() {
            @Override
            public void onCreateNews(String firstName, String lastName, String nickName,
                                     String position, String background, String picUrl,
                                     File fileToUpload) {
                addOfficialDialogFragment.dismiss();
                if (fileToUpload != null) {
                    bundle.putString("firstName",firstName);
                    bundle.putString("lastName",lastName);
                    bundle.putString("nickName",nickName);
                    bundle.putString("position",position);
                    bundle.putString("background",background);
                    bundle.putString("picUrl",picUrl);
                    uploadImageToS3(AppConstants.BUCKET_OFFICIALS_PIC, fileToUpload,1,1);
                } else {
                    apiRequestHelper.createOfficial(token,firstName,lastName,nickName,position,
                            officialList.size()+1,background,picUrl);
                }
            }

            @Override
            public void onCancel() {

            }
        });
        addOfficialDialogFragment.show(getFragmentManager(),"add official");
    }

    @Override
    public void onApiRequestBegin(String action) {
        if (action.equals(AppConstants.ACTION_GET_OFFICIALS)) {
            showCustomProgress("Fetching list of officials, Please wait...");
        } else if (action.equals(AppConstants.ACTION_CREATE_OFFICIAL_RECORD)) {
            showCustomProgress("Creating official record, Please wait...");
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
        } else if (action.equals(AppConstants.ACTION_CREATE_OFFICIAL_RECORD)) {
            final Official official = (Official)result;
            addOfficialToList(official);
        }
        rvOfficials.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onApiRequestFailed(String action, Throwable t) {
        dismissCustomProgress();
        handleApiException(t);
        LogHelper.log("err","error in fetching list of officials --> " + t.getMessage());
    }

    @Override
    public void onUploadFinished(String bucketName, String imageUrl) {
        if (bucketName.equals(AppConstants.BUCKET_OFFICIALS_PIC)) {
            apiRequestHelper.createOfficial(token,bundle.getString("firstName"),
                    bundle.getString("lastName"),bundle.getString("nickName"),bundle.getString("position"),
                    officialList.size()+1,bundle.getString("background"),imageUrl);
        }
    }

    private void addOfficialToList(final Official o) {
        final LocalOfficial localOfficial = new LocalOfficial(null,
                o.getId(),o.getCreatedAt(),o.getUpdatedAt(),o.getDeletedAt(),
                o.getFirstName(),o.getLastName(),o.getNickName(),o.getPosition(),
                o.getZindex(),o.getBackground(),o.getPic(),o.getStatus());
        DaoHelper.createOfficial(localOfficial);
        officialList.add(localOfficial);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
