package sanmateo.avinnovz.com.sanmateoprofile.interfaces;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.AuthResponse;

/**
 * Created by rsbulanon on 6/22/16.
 */
public interface ApiInterface {

    @POST("/api/v1/login")
    @FormUrlEncoded
    Observable<AuthResponse> authenticateUser(@Field("email") String username,
                                              @Field("password") String password);
}
