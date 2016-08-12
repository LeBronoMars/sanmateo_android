package sanmateo.avinnovz.com.sanmateoprofile.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.dao.LocalGallery;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.GalleryPhoto;

import com.squareup.picasso.Callback;

/**
 * Created by ctmanalo on 8/12/16.
 */
public class GalleryDetailFragment extends DialogFragment {

    private View mView;
    private Dialog mDialog;
    private LocalGallery localGallery;
//    private GalleryPhoto localGallery;

    @BindView(R.id.ivImage) ImageView ivImage;
    @BindView(R.id.pbLoadImage) ProgressBar pbLoadImage;
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.tvDescription) TextView tvDescription;

    public static GalleryDetailFragment newInstance(final LocalGallery localGallery) {
        final GalleryDetailFragment fragment = new GalleryDetailFragment();
        fragment.localGallery = localGallery;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_photo_detail, null);
        ButterKnife.bind(this, mView);
        initDetails();
        mDialog = new Dialog(getActivity());
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(mView);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout
                .LayoutParams.WRAP_CONTENT);
        return mDialog;
    }

    private void initDetails() {
        initPhoto();
        tvTitle.setText(localGallery.getTitle());
        tvDescription.setText(localGallery.getDescription());
    }

    private void initPhoto() {
        pbLoadImage.setVisibility(View.VISIBLE);
        AppConstants.PICASSO.load(localGallery.getImageUrl())
                .placeholder(R.drawable.placeholder_image)
                .fit()
                .into(ivImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        pbLoadImage.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        pbLoadImage.setVisibility(View.GONE);
                    }
                });
    }

    @OnClick(R.id.rlClose)
    public void closeDialog() {
        mDialog.dismiss();
    }
}
