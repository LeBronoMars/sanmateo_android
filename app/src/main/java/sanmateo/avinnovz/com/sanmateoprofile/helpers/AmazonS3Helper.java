package sanmateo.avinnovz.com.sanmateoprofile.helpers;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;

/**
 * Created by rsbulanon on 6/26/16.
 */
public class AmazonS3Helper {

    private Context context;
    private AmazonS3Client amazonS3Client;
    private TransferUtility transferUtility;
    private CognitoCachingCredentialsProvider sCredProvider;
    private static final String BUCKET_NAME = "sanmateoprofileapp/incidents";
    private static final String JOBS = "jobs";

    public AmazonS3Helper(final Context context) {
        this.context = context;
        this.amazonS3Client = new AmazonS3Client(getCredProvider(context));
        this.amazonS3Client.setRegion(Region.getRegion(Regions.US_WEST_1));
        this.transferUtility = new TransferUtility(amazonS3Client,context);
    }

    private  CognitoCachingCredentialsProvider getCredProvider(Context context) {
        if (sCredProvider == null) {
            sCredProvider = new CognitoCachingCredentialsProvider(
                    context,AppConstants.AWS_POOL_ID, Regions.US_EAST_1);
        }
        return sCredProvider;
    }

    public TransferObserver uploadImage(final Intent data) {
        final Uri selectedImageUri = data.getData();
        final File file = new File(getPath(selectedImageUri));
        return transferUtility.upload(
                BUCKET_NAME,          /* The bucket to upload to */
                file.getName(),       /* The key for the uploaded object */
                file                  /* The file where the data to upload exists */
        );
    }

    public TransferObserver uploadImage(final File file) {
        return transferUtility.upload(
                BUCKET_NAME,          /* The bucket to upload to */
                file.getName(),       /* The key for the uploaded object */
                file                  /* The file where the data to upload exists */
        );
    }

    public String getPath(final Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }

    public String getResourceUrl(final String fileName) {
        return amazonS3Client.getResourceUrl(BUCKET_NAME,fileName);
    }
}
