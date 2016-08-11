package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.app.Application;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;

import io.fabric.sdk.android.Fabric;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.DaoHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.ApiInterface;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by rsbulanon on 6/22/16.
 */
public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        MultiDex.install(this);
        /** initialize DAO Helper */
        DaoHelper.initialize(this);

        /** initialize facebook sdk */
        FacebookSdk.sdkInitialize(this);

        if (AppConstants.RETROFIT == null && AppConstants.API_INTERFACE == null && AppConstants.PICASSO == null) {

            File cacheDir = getExternalCacheDir();
            if (cacheDir == null) {
                cacheDir = getCacheDir();
            }
            final Cache cache = new Cache(cacheDir, 10 * 1024 * 1024);

            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(
                            chain -> {
                                Request request = chain.request();
                                LogHelper.log("api","performing url --> " + request.url());
                                return chain.proceed(request);
                            })
                    .cache(cache)
                    .build();

            /** initialize picasso */
            AppConstants.PICASSO = new Picasso.Builder(this)
                    .executor(Executors.newSingleThreadExecutor())
                    .downloader(new OkHttp3Downloader(okHttpClient)).build();

            /** initialize retrofit */
            AppConstants.RETROFIT = new Retrofit.Builder()
                    .baseUrl(AppConstants.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            /** initialize retrofit interface */
            AppConstants.API_INTERFACE = AppConstants.RETROFIT.create(ApiInterface.class);
        }
    }
}
