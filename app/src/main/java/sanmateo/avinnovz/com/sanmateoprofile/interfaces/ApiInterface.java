package sanmateo.avinnovz.com.sanmateoprofile.interfaces;

import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.AuthResponse;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Incident;

/**
 * Created by rsbulanon on 6/22/16.
 */
public interface ApiInterface {

    /**
     * Authenticate user
     *
     * @param email email of user trying to login
     * @param password password of user trying to
     *
     * @return AuthResponse which includes the basic info of info of successfully
     *                      authenticated user together with Token
     * */
    @POST("/api/v1/login")
    @FormUrlEncoded
    Observable<AuthResponse> authenticateUser(@Field("email") String email,
                                              @Field("password") String password);

    /**
     * Get all incidents
     *
     * @param token represents the user that trying to make the request
     * @param start defines the offset of query (for pagination)
     * @param incidentType filter incidents by incident type
     * @param status filter incidents by status
     *
     * @return List of Incidents that matches all the given parameters above
     * */
    @GET("/api/v1/incidents")
    Observable<List<Incident>> getIncidents(@Header("Authorization") String token,
                                            @Query("start") int start,
                                            @Query("incident_type") String incidentType,
                                            @Query("status") String status);

    /**
     * Get all latest incidents
     *
     * @param token represents the user that trying to make the request
     * @param incidentId unique identification of incident report
     *
     * @return List of Incident whose id is greater than or equal to provided incident_id
     * */
    @GET("/api/v1/incidents/latest/{incident_id}")
    Observable<List<Incident>> getLatestIncidents(@Header("Authorization") String token,
                                                  @Path("incident_id") int incidentId);

    /**
     * get incident by id
     *
     * @param token represents the user that trying to make the request
     * @param incidentId unique identification of incident report
     *
     * @return Incident model that matches the given incidentId
     * */
    @GET("/api/v1/incidents/show/{incident_id}")
    Observable<Incident> getIncidentById(@Header("Authorization") String token,
                                         @Path("incident_id") int incidentId);

    /**
     * file new incident report
     *
     * @param token represents the user that trying to make the request
     * @param address location of the incident
     * @param description a brief description or details of the incident
     * @param incidentType classification of incident
     * @param latitude x coordinate of incident
     * @param longitude y coordinate of incident
     * @param reportedBy id of the user who reported the incident
     * @param images url of uploaded images of the incident
     *
     * @return Incident model which contains the full detail of the newly created incident report
     * */
    @POST("/api/v1/incident")
    Observable<Incident> fileNewIncidentReport(@Header("Authorization") String token,
                                                   @Path("address") String address,
                                                   @Path("description") String description,
                                                   @Path("incident_type") String incidentType,
                                                   @Path("latitude") double latitude,
                                                   @Path("longitude") double longitude,
                                                   @Path("reported_by") int reportedBy,
                                                   @Path("images") String images);
}

