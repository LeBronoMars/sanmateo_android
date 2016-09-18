package sanmateo.avinnovz.com.sanmateoprofile.activities.admin;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.PhotosAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.admin.AddGalleryDialogFragment;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.ApiRequestHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnS3UploadListener;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Photo;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.CurrentUserSingleton;

/**
 * Created by rsbulanon on 8/7/16.
 */
public class ManageGalleryActivity extends BaseActivity implements OnApiRequestListener,
                                                                     OnS3UploadListener {

    @BindView(R.id.rvGalleries) RecyclerView rvGalleries;
    private ApiRequestHelper apiRequestHelper;
    private CurrentUserSingleton currentUserSingleton;
    private String token;
    private ArrayList<Photo> photos = new ArrayList<>();
    private Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_gallery);
        ButterKnife.bind(this);
        isOnline();
        currentUserSingleton = CurrentUserSingleton.newInstance();
        token = currentUserSingleton.getCurrentUser().getToken();
        apiRequestHelper = new ApiRequestHelper(this);
        apiRequestHelper.getPhotos(token);
        rvGalleries.setAdapter(new PhotosAdapter(this, photos));
        rvGalleries.setLayoutManager(new LinearLayoutManager(this));
        setToolbarTitle("Manage Gallery");
        initAmazonS3Helper(this);
    }

    @Override
    public void onApiRequestBegin(String action) {
        if (action.equals(AppConstants.ACTION_GET_PHOTOS)) {
            showCustomProgress("Loading galleries, Please wait...");
        } else if (action.equals(AppConstants.ACTION_POST_CREATE_GALLERY)) {
            showCustomProgress("Creating gallery, Please wait...");
        }
    }

    @Override
    public void onApiRequestSuccess(String action, Object result) {
        dismissCustomProgress();
        if (action.equals(AppConstants.ACTION_GET_PHOTOS)) {
            photos.clear();
            photos.addAll((ArrayList<Photo>)result);
        } else if (action.equals(AppConstants.ACTION_GET_GALLERY_PHOTOS)) {
            final Photo photo = (Photo) result;
            photos.add(0,photo);
        }
        rvGalleries.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onApiRequestFailed(String action, Throwable t) {
        dismissCustomProgress();
        handleApiException(t);
    }

    @OnClick(R.id.btnAdd)
    public void addGallery() {
        final AddGalleryDialogFragment fragment = AddGalleryDialogFragment.newInstance();
        fragment.setOnCreateUpdateGalleryListener(new AddGalleryDialogFragment.OnCreateUpdateGalleryListener() {
            @Override
            public void onCreateUpdateOfficial(String title, String desc, String imageUrl, File fileToUpload) {
                fragment.dismiss();
                if (isNetworkAvailable()) {
                    if (fileToUpload != null) {
                        bundle.putString("title", title);
                        bundle.putString("desc", desc);
                        uploadImageToS3(AppConstants.BUCKET_GALLERY, fileToUpload, 1, 1);
                    } else {
                        apiRequestHelper.createGallery(token, title, desc, imageUrl);
                    }
                } else {
                    showToast(AppConstants.WARN_CONNECTION);
                }
            }

            @Override
            public void onCancel() {
                fragment.dismiss();
            }
        });
        fragment.show(getFragmentManager(), "gallery");
    }

    @Override
    public void onUploadFinished(String bucketName, String imageUrl) {
        if (bucketName.equals(AppConstants.BUCKET_GALLERY)) {
            apiRequestHelper.createGallery(token, bundle.getString("title"), bundle.getString("desc"),
                    imageUrl);
        }
    }
}
