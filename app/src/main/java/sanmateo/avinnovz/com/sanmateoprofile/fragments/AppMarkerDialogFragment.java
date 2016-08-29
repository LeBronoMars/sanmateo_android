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

import com.squareup.picasso.Callback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.models.others.AppMarker;

/**
 * Created by ctmanalo on 8/28/16.
 */
public class AppMarkerDialogFragment extends DialogFragment {

    private View mView;
    private Dialog mDialog;
    private AppMarker appMarker;

    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.ivImage) ImageView ivImage;
    @BindView(R.id.pbLoadImage) ProgressBar pbLoadImage;

    public static AppMarkerDialogFragment newInstance(AppMarker appMarker) {
        final AppMarkerDialogFragment fragment = new AppMarkerDialogFragment();
        fragment.appMarker = appMarker;
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
        initPhoto();
    }

    private void initPhoto() {
        pbLoadImage.setVisibility(View.VISIBLE);
        AppConstants.PICASSO.load(appMarker.getImageUrl())
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
