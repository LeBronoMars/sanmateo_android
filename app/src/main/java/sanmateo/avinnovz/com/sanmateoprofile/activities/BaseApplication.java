package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.app.Application;

import java.io.IOException;

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
        if (AppConstants.RETROFIT == null && AppConstants.API_INTERFACE == null) {

            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(
                            new Interceptor() {
                                @Override
                                public Response intercept(Interceptor.Chain chain) throws IOException {
                                    Request request = chain.request();
                                    LogHelper.log("api","performing url --> " + request.url());
                                    return chain.proceed(request);
                                }
                            }).build();
            AppConstants.RETROFIT = new Retrofit.Builder()
                    .baseUrl(AppConstants.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            AppConstants.API_INTERFACE = AppConstants.RETROFIT.create(ApiInterface.class);
        }
    }
}
