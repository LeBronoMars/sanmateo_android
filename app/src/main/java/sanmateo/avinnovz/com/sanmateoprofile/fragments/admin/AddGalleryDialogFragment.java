package sanmateo.avinnovz.com.sanmateoprofile.fragments.admin;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rey.material.widget.Button;

import java.io.File;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Photo;

/**
 * Created by rsbulanon on 8/24/16.
 */
public class AddGalleryDialogFragment extends DialogFragment {

    @BindView(R.id.tvHeader) TextView tvHeader;
    @BindView(R.id.etTitle) EditText etTitle;
    @BindView(R.id.etDescription) EditText etDescription;
    @BindView(R.id.etImageURL) EditText etImageURL;
    @BindView(R.id.llManualInput) LinearLayout llManualInput;
    @BindView(R.id.rlImagePreview) RelativeLayout rlImagePreview;
    @BindView(R.id.ivImagePreview) ImageView ivImagePreview;
    @BindView(R.id.btnCreate) Button btnCreate;
    private View view;
    private Dialog mDialog;
    private BaseActivity activity;
    private OnCreateUpdateGalleryListener onCreateUpdateGalleryListener;
    private static final int SELECT_IMAGE = 1;
    private File fileToUpload = null;
    private Photo photo;

    public static AddGalleryDialogFragment newInstance(final Photo photo) {
        final AddGalleryDialogFragment fragment = new AddGalleryDialogFragment();
        fragment.photo = photo;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
        Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_create_gallery, null);
        ButterKnife.bind(this, view);
        if (photo != null) {
            etTitle.setText(photo.getTitle());
            etDescription.setText(photo.getDescription());
            tvHeader.setText("Update gallery record");
            btnCreate.setText("UPDATE");
            AppConstants.PICASSO.load(photo.getImageUrl()).fit().centerCrop().into(ivImagePreview);
            rlImagePreview.setVisibility(View.VISIBLE);
            llManualInput.setVisibility(View.GONE);
        }
        activity = (BaseActivity) getActivity();
        mDialog = new Dialog(getActivity());
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout
                .LayoutParams.WRAP_CONTENT);
        return mDialog;
    }

    @OnClick(R.id.btnCreate)
    public void createNews() {
        final String title = etTitle.getText().toString().trim();
        final String desc = etDescription.getText().toString().trim();
        final String imageUrl = etImageURL.getText().toString().trim();

        if (title.isEmpty()) {
            activity.setError(etTitle, AppConstants.WARN_FIELD_REQUIRED);
        } else if (desc.isEmpty()) {
            activity.setError(etDescription, AppConstants.WARN_FIELD_REQUIRED);
        } else {
            if (onCreateUpdateGalleryListener != null) {
                onCreateUpdateGalleryListener.onCreateUpdateOfficial(title,desc, imageUrl, fileToUpload);
            }
        }
    }

    public interface OnCreateUpdateGalleryListener {
        void onCreateUpdateOfficial(final String title, final String desc,
                                    final String picUrl, final File fileToUpload);
        void onCancel();
    }

    @OnClick(R.id.btnCancel)
    public void cancel() {
        if (onCreateUpdateGalleryListener != null) {
            onCreateUpdateGalleryListener.onCancel();
        }
    }

    @OnClick(R.id.btnSelectFromGallery)
    public void selectFromGallery() {
        final Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == activity.RESULT_OK) {
            if (requestCode == SELECT_IMAGE) {
                final String fileName = UUID.randomUUID().toString();
                fileToUpload = null;
                fileToUpload = activity.getFile(data.getData(),fileName+".jpg");
                AppConstants.PICASSO.load(fileToUpload).fit().centerCrop().into(ivImagePreview);
                rlImagePreview.setVisibility(View.VISIBLE);
                llManualInput.setVisibility(View.GONE);
            }
        }
    }

    @OnClick(R.id.ivRemove)
    public void removeSelectedImage() {
        if (rlImagePreview.isShown()) {
            fileToUpload = null;
            rlImagePreview.setVisibility(View.GONE);
            llManualInput.setVisibility(View.VISIBLE);
        }
    }

    public void setOnCreateUpdateGalleryListener(OnCreateUpdateGalleryListener onCreateUpdateGalleryListener) {
        this.onCreateUpdateGalleryListener = onCreateUpdateGalleryListener;
    }
}
