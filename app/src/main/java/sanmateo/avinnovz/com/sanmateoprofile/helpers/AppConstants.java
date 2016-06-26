package sanmateo.avinnovz.com.sanmateoprofile.helpers;

import retrofit2.Retrofit;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.ApiInterface;

/**
 * Created by rsbulanon on 6/22/16.
 */
public class AppConstants {

    //public static final String BASE_URL = "http://192.168.1.8:9000";
    public static final String BASE_URL = "http://192.168.10.4:9000";
    public static Retrofit RETROFIT = null;
    public static ApiInterface API_INTERFACE = null;

    /** s3 configs */
    public static final String AWS_POOL_ID = "us-east-1:66c534a3-218e-4cfc-934c-c5f24b53a88a";

    /** api actions */
    public static final String ACTION_LOGIN = "action login";

    /** warning messages */
    public static final String WARN_CONNECTION = "Connection error, Check your network connection and try again.";
    public static final String WARN_FIELD_REQUIRED = "This field is required";
    public static final String WARN_INVALID_EMAIL_FORMAT = "Invalid email format";
}
