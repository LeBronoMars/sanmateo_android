package sanmateo.avinnovz.com.sanmateoprofile.helpers;

import com.squareup.picasso.Picasso;

import retrofit2.Retrofit;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.ApiInterface;

/**
 * Created by rsbulanon on 6/22/16.
 */
public class AppConstants {

    /** static instance of Picasso */
    public static Picasso PICASSO = null;

    public static final String BASE_URL = "http://192.168.1.3:9000";
    //public static final String BASE_URL = "http://192.168.10.5:9000";
    //public static final String BASE_URL = "http://54.169.153.164:9000";
    public static Retrofit RETROFIT = null;
    public static ApiInterface API_INTERFACE = null;
    public static boolean IS_FACEBOOK_APP_INSTALLED = false;

    /** s3 configs */
    public static final String AWS_POOL_ID = "us-east-1:66c534a3-218e-4cfc-934c-c5f24b53a88a";

    /** api actions */
    public static final String ACTION_LOGIN = "action login";
    public static final String ACTION_GET_INCIDENTS = "get incidents";
    public static final String ACTION_GET_LATEST_INCIDENTS = "get latest incidents";
    public static final String ACTION_GET_INCIDENT_BY_ID = "get incident by id";
    public static final String ACTION_POST_INCIDENT_REPORT = "create new incident report";
    public static final String ACTION_POST_CREATE_USER = "create new user account";
    public static final String ACTION_POST_REPORT_MALICIOUS_INCIDENT = "report malicious incident";
    public static final String ACTION_PUT_BLOCK_REPORT = "block malicious report";
    public static final String ACTION_POST_NEWS = "create news";
    public static final String ACTION_GET_NEWS = "get news";
    public static final String ACTION_GET_NEWS_BY_ID = "get news by id";
    public static final String ACTION_GET_ANNOUNCEMENTS = "get all announcements";
    public static final String ACTION_POST_ANNOUNCEMENTS = "create announcement";
    public static final String ACTION_GET_ANNOUNCEMENT_BY_ID = "get announcement by id";
    public static final String ACTION_GET_LATEST_ANNOUNCEMENTS = "get latest announcements";
    public static final String ACTION_GET_WATER_LEVEL_NOTIFS = "get water level notifications";
    public static final String ACTION_POST_WATER_LEVEL_NOTIFS = "create water level notifications";

    /** warning messages */
    public static final String WARN_CONNECTION = "Connection error, Check your network connection and try again.";
    public static final String WARN_FIELD_REQUIRED = "This field is required";
    public static final String WARN_PASSWORD_NOT_MATCH = "Password did not match";
    public static final String WARN_INVALID_EMAIL_FORMAT = "Invalid email format";
    public static final String WARN_CANT_GET_IMAGE = "We can't get your image. Please try again.";
    public static final String WARN_INVALID_ACCOUNT = "Invalid account!";
}
