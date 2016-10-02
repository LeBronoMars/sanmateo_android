package sanmateo.avinnovz.com.sanmateoprofile.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.BannerAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.models.others.AppMarker;

/**
 * Created by ctmanalo on 8/28/16.
 */
public class AppMarkerDialogFragment extends DialogFragment {

    private View mView;
    private Dialog mDialog;
    private AppMarker appMarker;
    private BaseActivity activity;

    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.viewPager) AutoScrollViewPager viewPager;

    public static AppMarkerDialogFragment newInstance(Context context, AppMarker appMarker) {
        final AppMarkerDialogFragment fragment = new AppMarkerDialogFragment();
        fragment.appMarker = appMarker;
        fragment.activity = (BaseActivity) context;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_app_marker, null);
        ButterKnife.bind(this, mView);
        initViews();
        mDialog = new Dialog(getActivity());
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(mView);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout
                .LayoutParams.WRAP_CONTENT);
        return mDialog;
    }

    private void initViews() {
        tvTitle.setText(appMarker.getTitle());
        initBanner();
    }

    private void initBanner() {

        final ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(BannerFragment.newInstance(appMarker.getImageUrl1()));
        fragments.add(BannerFragment.newInstance(appMarker.getImageUrl2()));
        fragments.add(BannerFragment.newInstance(appMarker.getImageUrl3()));
        viewPager.setAdapter(new BannerAdapter(activity.getSupportFragmentManager(), fragments));
        viewPager.setOffscreenPageLimit(fragments.size());
        viewPager.setInterval(2000);
        viewPager.startAutoScroll();
        viewPager.setScrollDurationFactor(10);

        /*final ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(SanMateoBannerFragment.newInstance());
        fragments.add(SanMateoBannerFragment.newInstance());
        fragments.add(BannerFragment.newInstance(ContextCompat.getDrawable(activity,R.drawable.image_1)));
        fragments.add(BannerFragment.newInstance(ContextCompat.getDrawable(activity,R.drawable.image_2)));
        fragments.add(BannerFragment.newInstance(ContextCompat.getDrawable(activity,R.drawable.image_3)));
        viewPager.setAdapter(new BannerAdapter(activity.getSupportFragmentManager(), fragments));
        viewPager.setOffscreenPageLimit(fragments.size());
        viewPager.setInterval(2000);
        viewPager.startAutoScroll();
        viewPager.setScrollDurationFactor(10);*/
    }



    @OnClick(R.id.rlClose)
    public void closeDialog() {
        mDialog.dismiss();
    }
}
