package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.hardware.SensorManager;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rey.material.app.Dialog;
import com.rey.material.widget.Spinner;
import com.squareup.seismic.ShakeDetector;

import org.ocpsoft.prettytime.PrettyTime;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.adapter.rxjava.HttpException;
import sanmateo.avinnovz.com.sanmateoprofile.BuildConfig;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.admin.AdminLoginActivity;
import sanmateo.avinnovz.com.sanmateoprofile.dao.LocalGallery;
import sanmateo.avinnovz.com.sanmateoprofile.dao.PanicContact;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.CustomProgressBarDialogFragment;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.CustomProgressDialogFragment;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.PanicSettingsDialogFragment;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AmazonS3Helper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.DaoHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.PrefsHelper;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnConfirmDialogListener;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnS3UploadListener;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Photo;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.BusSingleton;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by rsbulanon on 6/22/16.
 */
public class BaseActivity extends AppCompatActivity implements ShakeDetector.Listener {

    private CustomProgressDialogFragment customProgressDialogFragment;
    private CustomProgressBarDialogFragment customProgressBarDialogFragment;
    private AmazonS3Helper amazonS3Helper;
    private OnS3UploadListener onS3UploadListener;
    private static SensorManager sensorManager;
    private static ShakeDetector shakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initShakeDetector();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void initAmazonS3Helper(final OnS3UploadListener onS3UploadListener) {
        amazonS3Helper = new AmazonS3Helper(this);
        this.onS3UploadListener = onS3UploadListener;
    }

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

    public void showCustomProgress(final String message) {
        if (customProgressDialogFragment == null) {
            customProgressDialogFragment = CustomProgressDialogFragment.newInstance(message);
            customProgressDialogFragment.setCancelable(false);
            customProgressDialogFragment.show(getFragmentManager(),"progress");
        }
    }

    public void showCustomProgressBar(final int max, final int current, final int total) {
        if (customProgressBarDialogFragment == null) {
            customProgressBarDialogFragment = CustomProgressBarDialogFragment
                    .newInstance(max, current, total);
            customProgressBarDialogFragment.setCancelable(false);
            customProgressBarDialogFragment.show(getFragmentManager(),"progress");
        }
    }

    public void updateCustomProgress(final String message) {
        if (customProgressDialogFragment != null) {
            customProgressDialogFragment.updateMessage(message);
        }
    }

    public void updateCustomProgressBar(final int progress, final int max) {
        if (customProgressBarDialogFragment != null) {
            customProgressBarDialogFragment.updateProgress(progress,max);
        }
    }

    public void dismissCustomProgress() {
        if (customProgressDialogFragment != null) {
            customProgressDialogFragment.dismiss();
            customProgressDialogFragment = null;
        }
    }

    public void dismissCustomProgressBar() {
        if (customProgressBarDialogFragment != null) {
            customProgressBarDialogFragment.dismiss();
            customProgressBarDialogFragment = null;
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
                    dialog.dismiss();
                    if (onConfirmDialogListener != null) {
                        onConfirmDialogListener.onConfirmed(action);
                    }
                }
            });
        }
        dialog.negativeAction(negativeText);
        dialog.negativeActionTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        dialog.negativeActionClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (onConfirmDialogListener != null) {
                    onConfirmDialogListener.onCancelled(action);
                }
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

    public File getFile(final Uri uri, final String fileName) {
        try {
            final ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
            final FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            final Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
            final ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            final byte[] bitmapdata = bos.toByteArray();

            final File f = new File(getCacheDir(), fileName);
            final FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return f;
        } catch (IOException e) {
            LogHelper.log("select_image","unable to select image --> " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public File rotateBitmap(String path) {
        final File file = new File(path);

        try {
            final ExifInterface ei = new ExifInterface(path);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int rotation = 0;
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotation = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotation = 180;
                    break;
            }

            final Matrix matrix = new Matrix();
            matrix.postRotate(rotation);
            resizeImage(file, matrix);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return file;
    }

    private void resizeImage(final File file,final Matrix matrix) {
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 0; //try to decrease decoded image
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
            FileOutputStream fos = new FileOutputStream(file);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
        } catch (Exception ex) {}
    }

    public File createImageFile() {
        final String imageFileName = UUID.randomUUID().toString() + ".png";

        final File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                imageFileName);
        return mediaStorageDir;
    }

    public boolean isFacebookInstalled() {
        try {
            getPackageManager().getApplicationInfo("com.facebook.katana", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public int getScreenDimension(final String what) {
        final Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return what.equals("height") ? size.y : size.x;
    }

    public void addMapMarker(GoogleMap map, double lat, double longi, String title,
                             String snippet, int marker) {
        map.addMarker(new MarkerOptions().position(new LatLng(lat, longi)).
                title(title).snippet(snippet).icon(
                marker == -1 ? null : BitmapDescriptorFactory.fromResource(marker))).showInfoWindow();
    }

    public boolean isMyServiceRunning(Class<?> serviceClass) {
        final ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void moveToOtherActivity(Class clz) {
        startActivity(new Intent(this, clz));
        animateToLeft(this);
    }

    public void setPanicContacts() {
        final PanicSettingsDialogFragment panicSettingsFragment = PanicSettingsDialogFragment.newInstance();
        panicSettingsFragment.setOnPanicContactListener(() -> {
            panicSettingsFragment.dismiss();
            initPanicContact();
        });
        panicSettingsFragment.show(getSupportFragmentManager(),"panic");
    }

    public void initPanicContact() {
        if (PrefsHelper.getInt(this, "panicContactSize") == 0) {
            LogHelper.log("book","show");
            showConfirmDialog("", "Emergency Contacts", "Please add at least one contact person" +
                    " for emergency purposes", "Ok", "", new OnConfirmDialogListener() {
                @Override
                public void onConfirmed(String action) {
                    setPanicContacts();
                }

                @Override
                public void onCancelled(String action) {

                }
            });
        } else {
            LogHelper.log("book","do not show");
        }
    }

    public List<LocalGallery> toLocalGallery(List<Photo> photoList) {
        List<LocalGallery> localGalleryList = new ArrayList<>();
        for (Photo p: photoList) {
            String id = p.getId();
            String createdAt = p.getCreatedAt();
            String updatedAt = p.getUpdatedAt();
            String deletedAt = p.getDeletedAt();
            String title = p.getTitle();
            String imageUrl = p.getImageUrl();
            String description = p.getDescription();
            localGalleryList.add(new LocalGallery(null, id, createdAt, updatedAt, deletedAt, title,
                    imageUrl, description));
        }

        return localGalleryList;
    }

    public void uploadImageToS3(final String bucketName, final File fileToUpload, final int current,
                                final int total) {
        if (isNetworkAvailable()) {
            showCustomProgressBar(0,current,total);
            amazonS3Helper.uploadImage(bucketName,fileToUpload).setTransferListener(new TransferListener() {
                @Override
                public void onStateChanged(int id, TransferState state) {
                    if (state.name().equals("COMPLETED")) {
                        dismissCustomProgressBar();
                        final String url = amazonS3Helper.getResourceUrl(bucketName,fileToUpload.getName());
                        onS3UploadListener.onUploadFinished(bucketName, url);
                    }
                }

                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                    updateCustomProgressBar((int)bytesCurrent,(int)bytesTotal);
                }

                @Override
                public void onError(int id, Exception ex) {
                    dismissCustomProgressBar();
                }
            });
        } else {
            showConfirmDialog("","No Connection",AppConstants.WARN_CONNECTION,"Close","",null);
        }
    }

    public void deleteImage(final String bucketName, final String fileName) {
        new DeleteImageFromS3(bucketName,fileName).execute();
    }

    private class DeleteImageFromS3 extends AsyncTask<Void,Void,Void> {

        private String bucketName;
        private String fileName;

        public DeleteImageFromS3(String bucketName, String fileName) {
            this.bucketName = bucketName;
            this.fileName = fileName.replace("https://s3-us-west-1.amazonaws.com/"+AppConstants.BUCKET_ROOT+"/","");
            LogHelper.log("aa","file name to delete --> " + this.fileName);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            amazonS3Helper.deleteImage(bucketName,fileName);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            LogHelper.log("aa","image successfully deleted from s3");
        }
    }

    public AmazonS3Helper getAmazonS3Helper() {
        return amazonS3Helper;
    }

    public void initSpinner(Spinner spinner, int userRoles) {
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                userRoles, R.layout.row_spinner);
        adapter.setDropDownViewResource(R.layout.row_spinner_dropdown);
        spinner.setAdapter(adapter);
    }

    public void handleApiException(final Throwable t) {
        final HttpException ex = (HttpException) t;
        LogHelper.log("err","CODE ---> " + ex.code() + " message --> " + ex.getMessage());
        if (ex.code() == 401) {
            showConfirmDialog("", "Session Expired", "Sorry, but your session has expired", "Close",
                    "", new OnConfirmDialogListener() {
                        @Override
                        public void onConfirmed(String action) {
                           Intent intent = null;
                            if (BuildConfig.FLAVOR.equals("admin")) {
                                intent = new Intent(BaseActivity.this, AdminLoginActivity.class);
                            } else {
                                intent = new Intent(BaseActivity.this, LoginActivity.class);
                            }
                            DaoHelper.deleteCurrentUser();
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(String action) {

                        }
                    });
        }
    }

    public void sendSMS(String phoneNumber, String message) {
        final String SENT = "SMS_SENT";
        final String DELIVERED = "SMS_DELIVERED";
        final PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        final PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

        /** when the SMS has been sent */
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        LogHelper.log("sms","sms sent!");
                        showToast("Your concern has been successfully sent to Mayor Tina!");
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        showToast("Unable to send your text concern, Please check your balance");
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        showToast("Unable to send your text concern, Please check your network connection");
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        LogHelper.log("sms","null pdu");
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        LogHelper.log("sms","radio off");
                        break;
                }
            }
        }, new IntentFilter(SENT));

        /** when the SMS has been delivered */
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        LogHelper.log("sms","sms delivered!");
                        break;
                    case Activity.RESULT_CANCELED:
                        LogHelper.log("sms","sms not delivered");
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        final SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }

    @Override
    public void hearShake() {
        for (PanicContact panicContact : DaoHelper.getAllPanicContacts()) {
            showToast("Sending SOS to all contacts in your panic phone book...");
            sendSMS(panicContact.getContactNo(),"Help me!");
        }
    }

    private void initShakeDetector() {
        if (sensorManager == null) {
            sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
            shakeDetector = new ShakeDetector(this);
            shakeDetector.start(sensorManager);
            LogHelper.log("shake","on shake initialized!");
        }
    }
}

