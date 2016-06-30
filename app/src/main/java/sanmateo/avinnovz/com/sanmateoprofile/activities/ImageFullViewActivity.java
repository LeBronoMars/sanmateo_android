package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.FullImageViewAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.ImageFullViewFragment;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Incident;

/**
 * Created by rsbulanon on 6/30/16.
 */
public class ImageFullViewActivity extends BaseActivity {

    @BindView(R.id.viewPager) ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_fullview);
        ButterKnife.bind(this);
        final Incident incident = getIntent().getParcelableExtra("incident");
        final int selectedImagePosition = getIntent().getIntExtra("selectedImagePosition",1);
        final ArrayList<String> incidentImages = new ArrayList<>();
        /**
         * if incident images contains '||' which acts as the delimiter
         * split incident.getImages() using '||' to get the list of image urls
         * */
        if (incident.getImages().contains("###")) {
            incidentImages.addAll(Arrays.asList(incident.getImages().split("###")));
        } else {
            incidentImages.add(incident.getImages());
        }
        setToolbarTitle("Selected Image " + (selectedImagePosition+1) + "/"+incidentImages.size());

        /** initialize view pager */
        final ArrayList<Fragment> fragments = new ArrayList<>();
        for (String  s : incidentImages) {
            fragments.add(ImageFullViewFragment.newInstance(this,s));
        }
        final FullImageViewAdapter adapter = new FullImageViewAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(fragments.size());
        viewPager.setCurrentItem(selectedImagePosition);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateToolbarTitle("Selected Image " + (position + 1) + "/" + incidentImages.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
