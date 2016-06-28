package sanmateo.avinnovz.com.sanmateoprofile.interfaces;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.AuthResponse;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Incident;

/**
 * Created by rsbulanon on 6/22/16.
 */
public interface ApiInterface {

    @POST("/api/v1/login")
    @FormUrlEncoded
    Observable<AuthResponse> authenticateUser(@Field("email") String username,
                                              @Field("password") String password);

    @GET("/api/v1/incidents")
    Observable<List<Incident>> getIncidents(@Header("Authorization") String token,
                                            @Query("start") int start,
                                            @Query("incident_type") String incidentType,
                                            @Query("status") String status);
}
