package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.PicassoHelper;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Photo;

/**
 * Created by rsbulanon on 6/27/16.
 */
public class GalleryActivity extends BaseActivity {

    @BindView(R.id.appBarLayout) AppBarLayout appBarLayout;
    @BindView(R.id.rvPhotos) RecyclerView rvPhotos;
    @BindView(R.id.ivSelectedPhoto) ImageView ivSelectedPhoto;
    @BindView(R.id.pbLoadImage) ProgressBar pbLoadImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        //initCollapsableImage(mockPhoto());
    }

//    private Photo mockPhoto() {
//        String id = "1";
//        String title = "General Luna Highway";
//        String imageUrl = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/gallery/highway.jpg";
//        String description = "In terms of geography, the town of  San Mateo is just a heartbeat" +
//                " away from Metro Manila.Barangay Banaba is its gateway, whether approaching it" +
//                " from either Nangka in Marikina City or Batasan Road in Quezon City. Upon" +
//                " driving through the arch above General Luna Highwaymarking the entry point to" +
//                " San Mateo, a marker can be seen by the side of the road saying something like" +
//                " you are now leaving the National Capital Region!  \\n\\nGeneral Luna Highway" +
//                " is San Mateo’s main thoroughfare. It must be a historic road where Andres" +
//                " Bonifacio could have passed by on his way to Pamitinan Cave. The legendary" +
//                " General Licerio Geronimo and his band of tiradores could have marched this" +
//                " road on their way to the victorious Battle of San Mateo.";
//
//        Photo photo = new Photo(id, title, description, imageUrl);
//        return photo;
//    }

    private void initCollapsableImage(Photo photo) {
        PicassoHelper.loadImageFromURL(photo.getImageUrl(), ivSelectedPhoto,
                pbLoadImage);
    }
}
