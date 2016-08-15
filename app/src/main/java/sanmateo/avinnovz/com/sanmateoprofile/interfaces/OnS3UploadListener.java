package sanmateo.avinnovz.com.sanmateoprofile.interfaces;

/**
 * Created by rsbulanon on 8/15/16.
 */
public interface OnS3UploadListener {

    void onUploadFinished(final String bucketName,final String imageUrl);
}
