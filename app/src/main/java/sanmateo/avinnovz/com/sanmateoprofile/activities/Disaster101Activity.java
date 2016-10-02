package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.squareup.picasso.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.TabPagerAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.ImageUrlRecyclerFragment;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.models.others.ImageUrl;
import sanmateo.avinnovz.com.sanmateoprofile.models.others.ImageUrlChild;
import sanmateo.avinnovz.com.sanmateoprofile.models.others.ImageUrlParent;

/**
 * Created by ctmanalo on 8/31/16.
 */
public class Disaster101Activity extends BaseActivity {

    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.ivPaalala) ImageView ivPaalala;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] titles = new String[]{"Earthquake", "Flood", "Tips"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster_101);
        ButterKnife.bind(this);
        setToolbarTitle("Disaster 101");
        initTabs();
        AppConstants.PICASSO.load("https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/banners/paalala.png")
                .placeholder(R.drawable.placeholder_image)
                .fit()
                .into(ivPaalala, new Callback() {
                    @Override
                    public void onSuccess() {}

                    @Override
                    public void onError() {}
                });
    }

    private void initTabs() {
        fragments.add(ImageUrlRecyclerFragment.newInstance(constructParentObject(getEQsUrl())));
        fragments.add(ImageUrlRecyclerFragment.newInstance(constructParentObject(getFloodsUrl())));
        fragments.add(ImageUrlRecyclerFragment.newInstance(constructParentObject(getTipsUrl())));
        viewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(), fragments, titles));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(fragments.size());
    }

    private ArrayList<ImageUrl> getEQsUrl() {
        ArrayList<ImageUrl> urls = new ArrayList<>();
        urls.add(new ImageUrl("Graphic Aid", AppConstants.IMAGE_URL_EQ_GRAPHIC_AID));
        urls.add(new ImageUrl("Hazards", AppConstants.IMAGE_URL_EQ_HAZARDS));
        urls.add(new ImageUrl("Things to do: Before", AppConstants.IMAGE_URL_EQ_BEFORE));
        urls.add(new ImageUrl("Things to do: During", AppConstants.IMAGE_URL_EQ_DURING));
        urls.add(new ImageUrl("Things to do: After", AppConstants.IMAGE_URL_EQ_AFTER));
        return urls;
    }

    private ArrayList<ImageUrl> getFloodsUrl() {
        ArrayList<ImageUrl> urls = new ArrayList<>();
        urls.add(new ImageUrl("Flood Cause", AppConstants.IMAGE_URL_FLOOD_CAUSE));
        urls.add(new ImageUrl("Things to do: Before", AppConstants.IMAGE_URL_FLOOD_BEFORE));
        urls.add(new ImageUrl("Things to do: During", AppConstants.IMAGE_URL_FLOOD_DURING));
        urls.add(new ImageUrl("Things to do: After", AppConstants.IMAGE_URL_FLOOD_AFTER));
        return urls;
    }

    private ArrayList<ImageUrl> getTipsUrl() {
        ArrayList<ImageUrl> urls = new ArrayList<>();
        urls.add(new ImageUrl("Reminders", AppConstants.IMAGE_URL_TIPS_REMINDERS));
        urls.add(new ImageUrl("Emergency Kit", AppConstants.IMAGE_URL_TIPS_EMERGENCY_KIT));
        return urls;
    }

    private ArrayList<ParentObject> constructParentObject(ArrayList<ImageUrl> imageUrls) {
        ArrayList<ParentObject> parentObjects = new ArrayList<>();
        for (ImageUrl iu: imageUrls) {
            ArrayList<Object> childList = new ArrayList<>();
            childList.add(new ImageUrlChild(iu.getUrl()));
            parentObjects.add(new ImageUrlParent(childList, iu.getTitle()));
        }
        return parentObjects;
    }
}
