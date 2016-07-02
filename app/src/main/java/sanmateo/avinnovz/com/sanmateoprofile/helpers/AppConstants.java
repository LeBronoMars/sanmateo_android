package sanmateo.avinnovz.com.sanmateoprofile.helpers;

import retrofit2.Retrofit;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.ApiInterface;

/**
 * Created by rsbulanon on 6/22/16.
 */
public class AppConstants {

    //public static final String BASE_URL = "http://192.168.1.7:9000";
    public static final String BASE_URL = "http://192.168.10.4:9000";
    public static Retrofit RETROFIT = null;
    public static ApiInterface API_INTERFACE = null;

    /** s3 configs */
    public static final String AWS_POOL_ID = "us-east-1:66c534a3-218e-4cfc-934c-c5f24b53a88a";

    /** api actions */
    public static final String ACTION_LOGIN = "action login";
    public static final String ACTION_GET_INCIDENTS = "get incidents";
    public static final String ACTION_GET_LATEST_INCIDENTS = "get latest incidents";
    public static final String ACTION_GET_INCIDENT_BY_ID = "get incident by id";
    public static final String ACTION_POST_INCIDENT_REPORT = "create new incident report";

    /** warning messages */
    public static final String WARN_CONNECTION = "Connection error, Check your network connection and try again.";
    public static final String WARN_FIELD_REQUIRED = "This field is required";
    public static final String WARN_INVALID_EMAIL_FORMAT = "Invalid email format";
    public static final String WARN_CANT_GET_IMAGE = "We can't get your image. Please try again.";
}
