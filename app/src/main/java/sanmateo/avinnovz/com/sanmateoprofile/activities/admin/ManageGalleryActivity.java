package sanmateo.avinnovz.com.sanmateoprofile.activities.admin;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.PhotosAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiRequestHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Photo;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.CurrentUserSingleton;

/**
 * Created by rsbulanon on 8/7/16.
 */
public class ManageGalleryActivity extends BaseActivity implements OnApiRequestListener {

    @BindView(R.id.rvGalleries) RecyclerView rvGalleries;
    private ApiRequestHelper apiRequestHelper;
    private CurrentUserSingleton currentUserSingleton;
    private String token;
    private ArrayList<Photo> photos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_gallery);
        ButterKnife.bind(this);
        currentUserSingleton = CurrentUserSingleton.newInstance();
        token = currentUserSingleton.getCurrentUser().getToken();
        apiRequestHelper = new ApiRequestHelper(this);
        apiRequestHelper.getPhotos(token);
        rvGalleries.setAdapter(new PhotosAdapter(this, photos));
        rvGalleries.setLayoutManager(new LinearLayoutManager(this));
        setToolbarTitle("Manage Gallery");
    }

    @Override
    public void onApiRequestBegin(String action) {
        if (action.equals(AppConstants.ACTION_GET_PHOTOS)) {
            showCustomProgress("Loading galleries, Please wait...");
        }
    }

    @Override
    public void onApiRequestSuccess(String action, Object result) {
        dismissCustomProgress();
        if (action.equals(AppConstants.ACTION_GET_PHOTOS)) {
            photos.clear();
            photos.addAll((ArrayList<Photo>)result);
            rvGalleries.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void onApiRequestFailed(String action, Throwable t) {
        dismissCustomProgress();
        handleApiException(t);
    }
}
