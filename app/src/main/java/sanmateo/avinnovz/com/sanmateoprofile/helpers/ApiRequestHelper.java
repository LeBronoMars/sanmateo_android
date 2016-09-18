package sanmateo.avinnovz.com.sanmateoprofile.helpers;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Announcement;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.AuthResponse;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.ForReviewIncident;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.GenericMessage;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Incident;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.News;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Official;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Photo;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.WaterLevel;

/**
 * Created by rsbulanon on 6/23/16.
 */
public class ApiRequestHelper {

    private OnApiRequestListener onApiRequestListener;

    public ApiRequestHelper(OnApiRequestListener onApiRequestListener) {
        this.onApiRequestListener = onApiRequestListener;
    }

    public void authenticateUser(final String email, final String password) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_LOGIN);
        final Observable<AuthResponse> observable = AppConstants.API_INTERFACE.authenticateUser(email, password);
        handleObservableResult(AppConstants.ACTION_LOGIN, observable);
    }

    public void getAllIncidents(final String token,final int start,final String incidentType,final String status) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_GET_INCIDENTS);
        final Observable<List<Incident>> observable = AppConstants.API_INTERFACE.getIncidents(token,start,incidentType,status);
        handleObservableResult(AppConstants.ACTION_GET_INCIDENTS, observable);
    }

    public void getLatestIncidents(final String token, final int incidentId) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_GET_LATEST_INCIDENTS);
        final Observable<List<Incident>> observable = AppConstants.API_INTERFACE
                .getLatestIncidents(token,incidentId);
        handleObservableResult(AppConstants.ACTION_GET_LATEST_INCIDENTS, observable);
    }

    public void getIncidentById(final String token, final int incidentId) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_GET_INCIDENT_BY_ID);
        final Observable<Incident> observable = AppConstants.API_INTERFACE.getIncidentById(token,incidentId);
        handleObservableResult(AppConstants.ACTION_GET_INCIDENT_BY_ID, observable);
    }

    public void fileIncidentReport(final String token, final String address, final String description,
                                   final String incidentType, final double latitude, final double longitude,
                                   final int reportedBy, final String images) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_POST_INCIDENT_REPORT);
        final Observable<Incident> observable = AppConstants.API_INTERFACE.fileNewIncidentReport(token,address,
                                        description,incidentType,latitude,longitude,reportedBy,images);
        handleObservableResult(AppConstants.ACTION_POST_INCIDENT_REPORT, observable);
    }

    public void createUser(final String firstName, final String lastName, final String contactNo,
                           final String gender, final String email, final String address,
                           final String userLevel, final String password) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_POST_CREATE_USER);
        final Observable<AuthResponse> observable = AppConstants.API_INTERFACE.createUser(firstName, lastName,
                contactNo, gender, email, address, userLevel, password);
        handleObservableResult(AppConstants.ACTION_POST_CREATE_USER, observable);
    }

    public void reportMaliciousIncidentReport(final String token, final int incidentId, final int postedBy,
                                              final int reportedBy, final String remarks) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_POST_REPORT_MALICIOUS_INCIDENT);
        final Observable<ResponseBody> observable = AppConstants.API_INTERFACE
                .reportMaliciousIncidentReport(token,incidentId,postedBy,reportedBy,remarks);
        handleObservableResult(AppConstants.ACTION_POST_REPORT_MALICIOUS_INCIDENT, observable);
    }

    public void blockMaliciousReport(final String token, final int incidentId, final String remarks,
                                     final String status) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_PUT_BLOCK_REPORT);
        final Observable<Incident> observable = AppConstants.API_INTERFACE.blockReport(token,
                                                incidentId, remarks, status);
        handleObservableResult(AppConstants.ACTION_PUT_BLOCK_REPORT, observable);
    }

    public void unblockMaliciousReport(final String token, final int incidentId) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_PUT_UNBLOCK_REPORT);
        final Observable<Incident> observable = AppConstants.API_INTERFACE.unblockReport(token,incidentId);
        handleObservableResult(AppConstants.ACTION_PUT_UNBLOCK_REPORT, observable);
    }

    public void approveReport(final String token, final int incidentId) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_PUT_APPROVE_REPORT);
        final Observable<Incident> observable = AppConstants.API_INTERFACE.approveReport(token,incidentId);
        handleObservableResult(AppConstants.ACTION_PUT_APPROVE_REPORT, observable);
    }

    public void getNews(final String token, final int start, final int limit, final String status, final String when) {
        final String action = when == null ? AppConstants.ACTION_GET_NEWS : when;
        onApiRequestListener.onApiRequestBegin(action);
        final Observable<List<News>> observable = AppConstants.API_INTERFACE.getNews(token,start,limit,status,when);
        handleObservableResult(action, observable);
    }

    public void createNews(final String token, final String title, final String body,
                           final String sourceUrl, final String imageUrl, final String reportedBy) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_POST_NEWS);
        final Observable<News> observable = AppConstants.API_INTERFACE.createNews(token,title,body,
                                                    sourceUrl,imageUrl,reportedBy);
        handleObservableResult(AppConstants.ACTION_POST_NEWS, observable);
    }

    public void getNewsById(final String token, final int id) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_GET_NEWS_BY_ID);
        final Observable<News> observable = AppConstants.API_INTERFACE.getNewsById(token,id);
        handleObservableResult(AppConstants.ACTION_GET_NEWS_BY_ID, observable);
    }

    public void getAnnouncements(final String token, final int start, final int limit) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_GET_ANNOUNCEMENTS);
        final Observable<List<Announcement>> observable = AppConstants.API_INTERFACE.getAnnouncements(token,start,limit);
        handleObservableResult(AppConstants.ACTION_GET_ANNOUNCEMENTS, observable);
    }

    public void createAnnouncement(final String token, final String title, final String message) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_POST_ANNOUNCEMENTS);
        final Observable<Announcement> observable = AppConstants.API_INTERFACE.createAnnouncement(token,title,message);
        handleObservableResult(AppConstants.ACTION_POST_ANNOUNCEMENTS, observable);
    }

    public void getAnnouncementById(final String token, final int announcementId) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_GET_ANNOUNCEMENT_BY_ID);
        final Observable<Announcement> observable = AppConstants.API_INTERFACE.getAnnouncementById(token,announcementId);
        handleObservableResult(AppConstants.ACTION_GET_INCIDENT_BY_ID, observable);
    }

    public void getLatestAnnouncements(final String token, final int announcementId) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_GET_LATEST_ANNOUNCEMENTS);
        final Observable<List<Announcement>> observable = AppConstants.API_INTERFACE
                .getLatestAnnouncements(token,announcementId);
        handleObservableResult(AppConstants.ACTION_GET_LATEST_ANNOUNCEMENTS, observable);
    }

    public void getWaterLevelNotifications(final String token, final int start, final int limit) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_GET_WATER_LEVEL_NOTIFS);
        final Observable<List<WaterLevel>> observable = AppConstants.API_INTERFACE.getWaterLevels(token,start,limit);
        handleObservableResult(AppConstants.ACTION_GET_WATER_LEVEL_NOTIFS, observable);
    }

    public void getLatestWaterLevelNotifications(final String token, final int id, final String area) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_GET_WATER_LEVEL_NOTIFS_LATEST);
        final Observable<List<WaterLevel>> observable = AppConstants.API_INTERFACE.
                getLatestWaterLevels(token,id, area);
        handleObservableResult(AppConstants.ACTION_GET_WATER_LEVEL_NOTIFS_LATEST, observable);
    }

    public void createWaterLevelNotification(final String token, final String area, final double level) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_POST_WATER_LEVEL_NOTIFS);
        final Observable<WaterLevel> observable = AppConstants.API_INTERFACE
                .createWaterLevelNotification(token,area,level);
        handleObservableResult(AppConstants.ACTION_POST_WATER_LEVEL_NOTIFS, observable);
    }

    public void changePassword(final String token, final String email, final String oldPassword,
                               final String newPassword) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_PUT_CHANGE_PASSWORD);
        final Observable<GenericMessage> observable = AppConstants.API_INTERFACE.changePassword(token, email,
                oldPassword, newPassword);
        handleObservableResult(AppConstants.ACTION_PUT_CHANGE_PASSWORD, observable);
    }

    public void changeProfilePic(final String token, final int userId, final String newPicUrl) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_PUT_CHANGE_PROFILE_PIC);
        final Observable<GenericMessage> observable = AppConstants.API_INTERFACE.changeProfilePic(token,userId,newPicUrl);
        handleObservableResult(AppConstants.ACTION_PUT_CHANGE_PROFILE_PIC, observable);
    }

    public void getPhotos(final String token) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_GET_PHOTOS);
        final Observable<ArrayList<Photo>> observable = AppConstants.API_INTERFACE.getPhotos(token);
        handleObservableResult(AppConstants.ACTION_GET_PHOTOS, observable);
    }

    public void getOfficials(final String token) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_GET_OFFICIALS);
        final Observable<List<Official>> observable = AppConstants.API_INTERFACE.getOfficials(token);
        handleObservableResult(AppConstants.ACTION_GET_OFFICIALS, observable);
    }

    public void createOfficial(final String token, final String firstName, final String lastName,
                               final String nickName, final String position, final int zindex,
                               final String background, final String picurl) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_CREATE_OFFICIAL_RECORD);
        final Observable<Official> observable = AppConstants.API_INTERFACE.createOfficialRecord(token,
                firstName,lastName,nickName, position, zindex, background, picurl);
        handleObservableResult(AppConstants.ACTION_CREATE_OFFICIAL_RECORD, observable);
    }

    public void updateOfficial(final String token, final String firstName, final String lastName,
                               final String nickName, final String position, final String background,
                               final String picurl) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_UPDATE_OFFICIAL_RECORD);
        final Observable<Official> observable = AppConstants.API_INTERFACE.updateOfficialRecord(token,
                firstName,lastName,nickName, position, background, picurl);
        handleObservableResult(AppConstants.ACTION_UPDATE_OFFICIAL_RECORD, observable);
    }

    public void getWaterLevelByArea(final String token, final String area) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_GET_WATER_LEVEL_BY_AREA);
        final Observable<List<WaterLevel>> observable = AppConstants.API_INTERFACE.getWaterLevelByArea(token,
                area);
        handleObservableResult(AppConstants.ACTION_GET_WATER_LEVEL_BY_AREA, observable);
    }

    public void getAllIncidentsForReview(final String token,final int start,
                                         final String incidentType) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_GET_ALL_FOR_REVIEWS);
        final Observable<List<ForReviewIncident>> observable = AppConstants.API_INTERFACE
                                            .getIncidentsForReview(token,start,incidentType);
        handleObservableResult(AppConstants.ACTION_GET_ALL_FOR_REVIEWS, observable);
    }

    public void disapproveMaliciousReport(final String token, final int id) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_DELETE_DISAPPROVE_MALICIOUS_REPORT);
        final Observable<GenericMessage> observable = AppConstants.API_INTERFACE.disapproveMaliciousReport(token,id);
        handleObservableResult(AppConstants.ACTION_DELETE_DISAPPROVE_MALICIOUS_REPORT, observable);
    }

    public void getForReviewReportById(final String token, final int id) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_GET_FOR_REVIEW_REPORT_BY_ID);
        final Observable<ForReviewIncident> observable = AppConstants.API_INTERFACE.getForReviewReportById(token,id);
        handleObservableResult(AppConstants.ACTION_GET_FOR_REVIEW_REPORT_BY_ID, observable);
    }

    /**
     * handle api result using lambda
     *
     * @param action identification of the current api request
     * @param observable actual process of the api request
     * */
    private void handleObservableResult(final String action, final Observable observable) {
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(result -> onApiRequestListener.onApiRequestSuccess(action,result),
                        throwable -> onApiRequestListener.onApiRequestFailed(action, (Throwable) throwable),
                        () -> LogHelper.log("api","Api request completed --> " + action));
    }
}
