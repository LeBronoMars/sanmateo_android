package sanmateo.avinnovz.com.sanmateoprofile.helpers;

import android.util.Log;

import sanmateo.avinnovz.com.sanmateoprofile.BuildConfig;

/**
 * Created by rsbulanon on 6/23/16.
 */
public class LogHelper {

    public static void log(final String key, final String message) {
        if (BuildConfig.DEBUG) {
            Log.d(key,message);
        }
    }
}
