package sanmateo.avinnovz.com.sanmateoprofile.interfaces;

import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Announcement;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.AuthResponse;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Incident;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.News;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.User;

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
    @FormUrlEncoded
    Observable<Incident> fileNewIncidentReport(@Header("Authorization") String token,
                                               @Field("address") String address,
                                               @Field("description") String description,
                                               @Field("incident_type") String incidentType,
                                               @Field("latitude") double latitude,
                                               @Field("longitude") double longitude,
                                               @Field("reported_by") int reportedBy,
                                               @Field("images") String images);

    /**
     * report malicious or improper incident report
     *
     * @param token represents the user that trying to make the request
     * @param incidentId unique identification of posted incident report
     * @param postedBy user who posted the incident report
     * @param reportedBy user who is reported the possible malicious incident report
     * @param remarks brief description or explanation of the case
     *
     * */
    @POST("/api/v1/report")
    @FormUrlEncoded
    Observable<ResponseBody> reportMaliciousIncidentReport(@Header("Authorization") String token,
                                                           @Field("incident_id") int incidentId,
                                                           @Field("posted_by") int postedBy,
                                                           @Field("reported_by") int reportedBy,
                                                           @Field("remarks") String remarks);

    /**
     * create user
     * */
    @POST("/api/v1/user")
    @FormUrlEncoded
    Observable<AuthResponse> createUser(@Field("first_name") String firstName,
                                        @Field("last_name") String lastName,
                                        @Field("contact_no") String contactNo,
                                        @Field("gender") String gender,
                                        @Field("email") String email,
                                        @Field("address") String address,
                                        @Field("user_level") String userLevel,
                                        @Field("password") String password);

    /**
     * block a malicious incident report
     *
     * @param token represents the user that trying to make the request
     * @param incidentId identification of incident report
     * @param remarks brief description why the incident report must be blocked
     *
     * */
    @PUT("/api/v1/incidents/block/{incident_id}")
    @FormUrlEncoded
    Observable<Incident> blockReport(@Header("Authorization") String token,
                                     @Path("incident_id") int incidentId,
                                     @Field("remarks") String remarks);

    /**
     * get all news
     *
     * @param token represents the user that trying to make the request
     * @param start defines the offset of query (for pagination)
     * @param limit size of expected result
     * @param status filter news by their status
     * @param when to segregate news for today and previous
     *
     * */
    @GET("/api/v1/news")
    Observable<List<News>> getNews(@Header("Authorization") String token,
                                   @Query("start") int start,
                                   @Query("limit") int limit,
                                   @Query("status") String status,
                                   @Query("when") String when);

    /**
     * create news
     *
     * @param token represents the user that trying to make the request
     * @param title title of the news
     * @param body body of the news
     * @param sourceUrl source of the news
     * @param imageUrl image url of news banner image
     * @param reportedBy who reports the news
     * */
    @POST("/api/v1/news")
    @FormUrlEncoded
    Observable<News> createNews(@Header("Authorization") String token,
                                @Field("title") String title,
                                @Field("body") String body,
                                @Field("source_url") String sourceUrl,
                                @Field("image_url") String imageUrl,
                                @Field("reported_by") String reportedBy);


    /**
     * get all announcements
     *
     * @param token represents the user that trying to make the request
     * @param start defines the offset of query (for pagination)
     * @param limit size of expected result
     * */
    @GET("/api/v1/announcements")
    Observable<List<Announcement>> getAnnouncements(@Header("Authorization") String token,
                                                    @Query("start") int start,
                                                    @Query("limit") int limit);

    /**
     * create announcement
     *
     * @param token represents the user that trying to make the request
     * @param title title of the announcement
     * @param message message of the announcement
     * */
    @POST("/api/v1/announcements")
    @FormUrlEncoded
    Observable<Announcement> createAnnouncement(@Header("Authorization") String token,
                                                @Field("title") String title,
                                                @Field("message") String message);
}

