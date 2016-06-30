package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.app.Dialog;
import com.squareup.otto.Subscribe;

import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.CustomProgressDialogFragment;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.PrefsHelper;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnConfirmDialogListener;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.BusSingleton;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by rsbulanon on 6/22/16.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void setError(final TextView textView, final String message) {
        textView.setError(message);
        textView.requestFocus();
    }

    public void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    public boolean isValidEmail(final String email) {
        return email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private CustomProgressDialogFragment customProgressDialogFragment;

    public void showCustomProgress(final String message) {
        if (customProgressDialogFragment == null) {
            customProgressDialogFragment = CustomProgressDialogFragment.newInstance(message);
            customProgressDialogFragment.setCancelable(false);
            customProgressDialogFragment.show(getFragmentManager(),"progress");
        }
    }

    public void dismissCustomProgress() {
        if (customProgressDialogFragment != null) {
            customProgressDialogFragment.dismiss();
            customProgressDialogFragment = null;
        }
    }


    public void showConfirmDialog(final String action, final String header, final String message,
                                  final String positiveText, final String negativeText,
                                  final OnConfirmDialogListener onConfirmDialogListener) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_error_confirm);
        ((TextView)dialog.findViewById(R.id.tvHeader)).setText(header);
        ((TextView)dialog.findViewById(R.id.tvMessage)).setText(message);
        final Display display = getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        dialog.layoutParams((int) (size.x * .70), LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        if (!positiveText.isEmpty()) {
            dialog.positiveAction(positiveText);
            dialog.positiveActionTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            dialog.positiveActionClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onConfirmDialogListener != null) {
                        onConfirmDialogListener.onConfirmed(action);
                    }
                    dialog.dismiss();
                }
            });
        }
        dialog.negativeAction(negativeText);
        dialog.negativeActionTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        dialog.negativeActionClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onConfirmDialogListener != null) {
                    onConfirmDialogListener.onCancelled(action);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showSnackbar(final View parent,final String message) {
        Snackbar.make(parent,message,Snackbar.LENGTH_LONG).show();
    }

    /**
     * check network connection availability
     */
    public boolean isNetworkAvailable() {
        boolean isConnected = false;
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWifi.isConnected()) {
            isConnected = true;
        } else {
            NetworkInfo mData = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mData == null) {
                isConnected = false;
            } else {
                boolean isDataEnabled = mData.isConnected();
                isConnected = isDataEnabled ? true : false;
            }
        }
        return isConnected;
    }

    public void animateToLeft(Activity activity) {
        activity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    public static void animateToRight(Activity activity) {
        activity.overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    public Drawable getImageById(final int id) {
        return ContextCompat.getDrawable(this,id);
    }

    public void setToolbarTitle(final String title) {
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void updateToolbarTitle(final String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onPause() {
        BusSingleton.getInstance().unregister(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        BusSingleton.getInstance().register(this);
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        animateToRight(this);
    }

    public SimpleDateFormat getDateFormatter() { return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); }

    public SimpleDateFormat getSDF() { return  new SimpleDateFormat("EEE, yyyy-MM-dd hh:mm a"); }

    public PrettyTime getPrettyTime() {
        return new PrettyTime();
    }

    public String getFilePath(final Intent data) {
        final Uri selectedImage = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
        cursor.moveToFirst();
        final int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        final String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }

    public Bitmap getBitmapFromURI(final Uri uri) {
        final ParcelFileDescriptor parcelFileDescriptor;
        try {
            parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
            final FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            final Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LogHelper.log("img","file not found exception --> " + e.getMessage());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            LogHelper.log("img","io exception ---> " + ioe.getMessage());
        }
        return null;
    }

}

