package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.ApiInterface;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by rsbulanon on 6/22/16.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        if (AppConstants.RETROFIT == null && AppConstants.API_INTERFACE == null) {
            AppConstants.RETROFIT = new Retrofit.Builder()
                    .baseUrl(AppConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            AppConstants.API_INTERFACE = AppConstants.RETROFIT.create(ApiInterface.class);
        }
    }
}
