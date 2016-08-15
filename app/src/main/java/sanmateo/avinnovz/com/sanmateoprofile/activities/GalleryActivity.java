package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.GalleryAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.dao.LocalGallery;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.GalleryDetailFragment;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiRequestHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.DaoHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.GalleryPhoto;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.CurrentUserSingleton;

/**
 * Created by rsbulanon on 6/27/16.
 */
public class GalleryActivity extends BaseActivity implements OnApiRequestListener{

    @BindView(R.id.rvPhotos) RecyclerView rvPhotos;

    private CurrentUserSingleton currentUserSingleton;
    private ApiRequestHelper apiRequestHelper;
    private String token;
    private List<LocalGallery> localGalleryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        setToolbarTitle("Gallery");
        apiRequestHelper = new ApiRequestHelper(this);
        currentUserSingleton = CurrentUserSingleton.newInstance();
        token = currentUserSingleton.getCurrentUser().getToken();
        apiRequestHelper.getGalleryPhotos(token);
        initPhotos();
    }


    private void initRecycler() {
        GalleryAdapter adapter = new GalleryAdapter(this, localGalleryList);
        adapter.setOnGalleryClickListener(localGallery -> {
            GalleryDetailFragment fragment = GalleryDetailFragment.newInstance(localGallery);
            fragment.show(getFragmentManager(), "Photo Details");
        });
        rvPhotos.setAdapter(adapter);
        rvPhotos.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initPhotos() {
        localGalleryList = new ArrayList<>();
        if (DaoHelper.hasGalleryPhotos()) {
            localGalleryList.clear();
            localGalleryList.addAll(DaoHelper.getAllGalleryPhotos());
        }
        initRecycler();
    }

    @Override
    public void onApiRequestBegin(String action) {
        if (action.equals(AppConstants.ACTION_GET_GALLERY_PHOTOS)) {
            showCustomProgress("Fetching gallery photos, Please wait...");
        }
    }

    @Override
    public void onApiRequestSuccess(String action, Object result) {
        if (action.equals(AppConstants.ACTION_GET_GALLERY_PHOTOS)) {
            final List<GalleryPhoto> galleryPhotos = (List<GalleryPhoto>) result;
            if (galleryPhotos.size() != localGalleryList.size()) {
                localGalleryList.clear();
                localGalleryList.addAll(toLocalGallery(galleryPhotos));
                DaoHelper.saveFromGalleryPhotos(galleryPhotos);
                rvPhotos.getAdapter().notifyDataSetChanged();
            }
        }
        dismissCustomProgress();
    }

    @Override
    public void onApiRequestFailed(String action, Throwable t) {
        dismissCustomProgress();
    }
}
