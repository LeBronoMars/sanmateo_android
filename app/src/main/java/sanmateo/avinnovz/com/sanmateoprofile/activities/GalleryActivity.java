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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        setToolbarTitle("Gallery");
        apiRequestHelper = new ApiRequestHelper(this);
        currentUserSingleton = CurrentUserSingleton.newInstance();
        token = currentUserSingleton.getCurrentUser().getToken();
        initPhotos();
    }

    private List<LocalGallery> mockPhoto() {
        String id = "1";
        String title = "General Luna Highway";
        String imageUrl = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/gallery/highway.jpg";
        String description = "In terms of geography, the town of  San Mateo is just a heartbeat" +
                " away from Metro Manila.Barangay Banaba is its gateway, whether approaching it" +
                " from either Nangka in Marikina City or Batasan Road in Quezon City. Upon" +
                " driving through the arch above General Luna Highwaymarking the entry point to" +
                " San Mateo, a marker can be seen by the side of the road saying something like" +
                " you are now leaving the National Capital Region!  \\n\\nGeneral Luna Highway" +
                " is San Mateo’s main thoroughfare. It must be a historic road where Andres" +
                " Bonifacio could have passed by on his way to Pamitinan Cave. The legendary" +
                " General Licerio Geronimo and his band of tiradores could have marched this" +
                " road on their way to the victorious Battle of San Mateo.";

        LocalGallery localGallery = new LocalGallery(id, title, imageUrl, description);
        List<LocalGallery> localGalleryList = new ArrayList<>();
        localGalleryList.add(localGallery);
        localGalleryList.add(localGallery);
        localGalleryList.add(localGallery);
        localGalleryList.add(localGallery);
        localGalleryList.add(localGallery);
        localGalleryList.add(localGallery);
        return localGalleryList;
    }

    private void initRecycler(List<LocalGallery> localGalleryList) {
        GalleryAdapter adapter = new GalleryAdapter(this, localGalleryList);
        adapter.setOnGalleryClickListener(new GalleryAdapter.OnGalleryClickListener() {
            @Override
            public void onPhotoSelected(LocalGallery localGallery) {
                GalleryDetailFragment fragment = GalleryDetailFragment.newInstance(localGallery);
                fragment.show(getFragmentManager(), "Photo Details");
            }
        });
        rvPhotos.setAdapter(adapter);
        rvPhotos.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initPhotos() {
        if (DaoHelper.hasGalleryPhotos()) {
            LogHelper.log("image_url", "has gallery");
            initRecycler(DaoHelper.getAllGalleryPhotos());
        } else {
            LogHelper.log("image_url", "no gallery");
            apiRequestHelper.getGalleryPhotos(token);
        }
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
            DaoHelper.saveFromGalleryPhotos(galleryPhotos);
            rvPhotos.getAdapter().notifyDataSetChanged();
        }
        dismissCustomProgress();
    }

    @Override
    public void onApiRequestFailed(String action, Throwable t) {
        dismissCustomProgress();
    }
}
