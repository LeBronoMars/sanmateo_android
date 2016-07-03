package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.app.Application;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;

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
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
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
        if (AppConstants.RETROFIT == null && AppConstants.API_INTERFACE == null && AppConstants.PICASSO == null) {

            File cacheDir = getExternalCacheDir();
            if (cacheDir == null) {
                cacheDir = getCacheDir();
            }
            final Cache cache = new Cache(cacheDir, 10 * 1024 * 1024);

            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(
                            new Interceptor() {
                                @Override
                                public Response intercept(Interceptor.Chain chain) throws IOException {
                                    Request request = chain.request();
                                    LogHelper.log("api","performing url --> " + request.url());
                                    return chain.proceed(request);
                                }
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
