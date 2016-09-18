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

    //public static final String BASE_URL = "http://54.169.17.222:7000";
    public static final String BASE_URL = "http://192.168.10.10:7000";
    public static Retrofit RETROFIT = null;
    public static ApiInterface API_INTERFACE = null;
    public static boolean IS_FACEBOOK_APP_INSTALLED = false;

    public static String SAN_MATEO_LOGO = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/banners/san_mateo_logo.png";
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
    public static final String ACTION_PUT_UNBLOCK_REPORT = "unblock malicious report";
    public static final String ACTION_PUT_APPROVE_REPORT = "approve incident report";
    public static final String ACTION_POST_NEWS = "create news";
    public static final String ACTION_GET_NEWS = "get news";
    public static final String ACTION_GET_NEWS_BY_ID = "get news by id";
    public static final String ACTION_GET_ANNOUNCEMENTS = "get all announcements";
    public static final String ACTION_POST_ANNOUNCEMENTS = "create announcement";
    public static final String ACTION_GET_ANNOUNCEMENT_BY_ID = "get announcement by id";
    public static final String ACTION_GET_LATEST_ANNOUNCEMENTS = "get latest announcements";
    public static final String ACTION_GET_WATER_LEVEL_NOTIFS = "get water level notifications";
    public static final String ACTION_POST_WATER_LEVEL_NOTIFS = "create water level notifications";
    public static final String ACTION_GET_WATER_LEVEL_NOTIFS_LATEST = "get water level notifications latest";
    public static final String ACTION_PUT_CHANGE_PASSWORD = "change password";
    public static final String ACTION_PUT_CHANGE_PROFILE_PIC = "change profile pic";
    public static final String ACTION_GET_PHOTOS = "get photos";
    public static final String ACTION_GET_GALLERY_PHOTOS = "get all gallery photo";
    public static final String ACTION_GET_OFFICIALS = "get all official";
    public static final String ACTION_CREATE_OFFICIAL_RECORD = "create official record";
    public static final String ACTION_UPDATE_OFFICIAL_RECORD = "update official record";
    public static final String ACTION_GET_WATER_LEVEL_BY_AREA = "get all water levels by area";
    public static final String ACTION_GET_ALL_FOR_REVIEWS = "get all for reviews";
    public static final String ACTION_DELETE_DISAPPROVE_MALICIOUS_REPORT = "disapprove malicious report";
    public static final String ACTION_GET_FOR_REVIEW_REPORT_BY_ID = "get for review report by id";

    /** warning messages */
    public static final String WARN_CONNECTION = "Connection error, Check your network connection and try again.";
    public static final String WARN_FIELD_REQUIRED = "This field is required";
    public static final String WARN_PASSWORD_NOT_MATCH = "Password did not match";
    public static final String WARN_INVALID_EMAIL_FORMAT = "Invalid email format";
    public static final String WARN_CANT_GET_IMAGE = "We can't get your image. Please try again.";
    public static final String WARN_INVALID_ACCOUNT = "Invalid account!";
    public static final String WARN_INVALID_CONTACT_NO = "Invalid contact no";

    /** categories */
    public static final String CATEGORY_PEACE_AND_SECURITY = "Peace & Security";
    public static final String CATEGORY_SOCIAL_AND_DEVELOPMENT = "Social & Development Sector";
    public static final String CATEGORY_HEALTH = "Health Sector";
    public static final String CATEGORY_RESORT = "Resort";
    public static final String CATEGORY_SHOPPING_MALL = "Shopping Mall";
    public static final String CATEGORY_AUTHENTIC_RESTAURANT = "Authentic Restaurant";
    public static final String CATEGORY_HERITAGE_SITE = "Heritage Site";
    public static final String CATEGORY_MUNICIPAL = "Municipal Government";
    public static final String CATEGORY_BARANGAY = "Barangay Government";

    /** aws s3 buckets */
    public static final String BUCKET_ROOT = "sanmateoprofileapp";
    public static final String BUCKET_INCIDENTS = BUCKET_ROOT + "/incidents";
    public static final String BUCKET_PROFILE_PIC = BUCKET_ROOT + "/profilepics";
    public static final String BUCKET_OFFICIALS_PIC = BUCKET_ROOT + "/officials";
    public static final String BUCKET_NEWS = BUCKET_ROOT + "/news";

    /** prefs key */
    public static final String PREFS_LOCAL_EMERGENCY_KITS = "local emergency kits";

    /** image url */
    public static final String IMAGE_URL_FLOOD_CAUSE = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/causes_flood.png";
    public static final String IMAGE_URL_FLOOD_BEFORE = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/before_flood.png";
    public static final String IMAGE_URL_FLOOD_DURING = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/during_flood.png";
    public static final String IMAGE_URL_FLOOD_AFTER = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/after_flood.png";
    public static final String IMAGE_URL_TIPS_EMERGENCY_KIT = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/emergencykit.png";
    public static final String IMAGE_URL_TIPS_REMINDERS = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/Reminder.png";
    public static final String IMAGE_URL_EQ_HAZARDS = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/HAZARDS_EQ.png";
    public static final String IMAGE_URL_EQ_GRAPHIC_AID = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/Graphic_AID.png";
    public static final String IMAGE_URL_EQ_BEFORE = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/BEFORE_EQ.png";
    public static final String IMAGE_URL_EQ_DURING = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/DURING_EQ.png";
    public static final String IMAGE_URL_EQ_AFTER = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/disaster_management/AFTER_EQ.png";
    public static final String IMAGE_URL_MARKER_BRIDGE_1 = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/alert_level/Batasan-SanMateoBridge1.png";
    public static final String IMAGE_URL_MARKER_BRIDGE_2 = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/alert_level/Batasan-SanMateoBridge2.png";
    public static final String IMAGE_URL_MARKER_BRIDGE_3 = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/alert_level/Batasan-SanMateoBridge3.png";
    public static final String IMAGE_URL_MARKER_MARKET_1 = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/alert_level/PublicMarket_pic1.png";
    public static final String IMAGE_URL_MARKER_MARKET_2 = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/alert_level/PublicMarket_pic2.png";
    public static final String IMAGE_URL_MARKER_MARKET_3 = "https://s3-us-west-1.amazonaws.com/sanmateoprofileapp/alert_level/PublicMarket_pic3.png";
}
