package sanmateo.avinnovz.com.sanmateoprofile.helpers;

import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Announcement;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.AuthResponse;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.GenericMessage;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Incident;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.News;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.User;
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
        Observable<AuthResponse> observable = AppConstants.API_INTERFACE.authenticateUser(email, password);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<AuthResponse>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        onApiRequestListener.onApiRequestFailed(AppConstants.ACTION_LOGIN, e);
                    }

                    @Override
                    public void onNext(AuthResponse authResponse) {
                        onApiRequestListener.onApiRequestSuccess(AppConstants.ACTION_LOGIN, authResponse);
                    }
                });
    }

    public void getAllIncidents(final String token,final int start,final String incidentType,final String status) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_GET_INCIDENTS);
        Observable<List<Incident>> observable = AppConstants.API_INTERFACE.getIncidents(token,start,incidentType,status);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Incident>>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        onApiRequestListener.onApiRequestFailed(AppConstants.ACTION_GET_INCIDENTS, e);
                    }

                    @Override
                    public void onNext(List<Incident> incidents) {
                        onApiRequestListener.onApiRequestSuccess(AppConstants.ACTION_GET_INCIDENTS, incidents);
                    }
                });
    }

    public void getLatestIncidents(final String token, final int incidentId) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_GET_LATEST_INCIDENTS);
        Observable<List<Incident>> observable = AppConstants.API_INTERFACE.getLatestIncidents(token,incidentId);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Incident>>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        onApiRequestListener.onApiRequestFailed(AppConstants.ACTION_GET_LATEST_INCIDENTS, e);
                    }

                    @Override
                    public void onNext(List<Incident> incidents) {
                        onApiRequestListener.onApiRequestSuccess(AppConstants.ACTION_GET_LATEST_INCIDENTS, incidents);
                    }
                });
    }

    public void getIncidentById(final String token, final int incidentId) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_GET_INCIDENT_BY_ID);
        Observable<Incident> observable = AppConstants.API_INTERFACE.getIncidentById(token,incidentId);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Incident>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        onApiRequestListener.onApiRequestFailed(AppConstants.ACTION_GET_INCIDENT_BY_ID, e);
                    }

                    @Override
                    public void onNext(Incident incident) {
                        onApiRequestListener.onApiRequestSuccess(AppConstants.ACTION_GET_INCIDENT_BY_ID, incident);
                    }
                });
    }

    public void fileIncidentReport(final String token, final String address, final String description,
                                   final String incidentType, final double latitude, final double longitude,
                                   final int reportedBy, final String images) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_POST_INCIDENT_REPORT);
        Observable<Incident> observable = AppConstants.API_INTERFACE.fileNewIncidentReport(token,address,
                                        description,incidentType,latitude,longitude,reportedBy,images);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Incident>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        onApiRequestListener.onApiRequestFailed(AppConstants.ACTION_POST_INCIDENT_REPORT, e);
                    }

                    @Override
                    public void onNext(Incident incident) {
                        onApiRequestListener.onApiRequestSuccess(AppConstants.ACTION_POST_INCIDENT_REPORT, incident);
                    }
                });
    }

    public void createUser(final String firstName, final String lastName, final String contactNo,
                           final String gender, final String email, final String address,
                           final String userLevel, final String password) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_POST_CREATE_USER);
        Observable<AuthResponse> observable = AppConstants.API_INTERFACE.createUser(firstName, lastName,
                contactNo, gender, email, address, userLevel, password);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<AuthResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        onApiRequestListener.onApiRequestFailed(AppConstants.ACTION_POST_CREATE_USER, e);
                    }

                    @Override
                    public void onNext(AuthResponse authResponse) {
                        onApiRequestListener.onApiRequestSuccess(AppConstants.ACTION_POST_CREATE_USER,
                                authResponse);
                    }
                });
    }

    public void reportMaliciousIncidentReport(final String token, final int incidentId, final int postedBy,
                                              final int reportedBy, final String remarks) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_POST_REPORT_MALICIOUS_INCIDENT);
        Observable<ResponseBody> observable = AppConstants.API_INTERFACE
                .reportMaliciousIncidentReport(token,incidentId,postedBy,reportedBy,remarks);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ResponseBody>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        onApiRequestListener.onApiRequestFailed(AppConstants.ACTION_POST_REPORT_MALICIOUS_INCIDENT, e);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        onApiRequestListener.onApiRequestSuccess(AppConstants.ACTION_POST_REPORT_MALICIOUS_INCIDENT, responseBody);
                    }
                });
    }

    public void blockMaliciousReport(final String token, final int incidentId, final String remarks) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_PUT_BLOCK_REPORT);
        Observable<Incident> observable = AppConstants.API_INTERFACE.blockReport(token,incidentId,remarks);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Incident>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        onApiRequestListener.onApiRequestFailed(AppConstants.ACTION_PUT_BLOCK_REPORT, e);
                    }

                    @Override
                    public void onNext(Incident incident) {
                        onApiRequestListener.onApiRequestSuccess(AppConstants.ACTION_PUT_BLOCK_REPORT, incident);
                    }
                });
    }

    public void getNews(final String token, final int start, final int limit, final String status, final String when) {
        final String action = when == null ? AppConstants.ACTION_GET_NEWS : when;
        onApiRequestListener.onApiRequestBegin(action);
        Observable<List<News>> observable = AppConstants.API_INTERFACE.getNews(token,start,limit,status,when);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<News>>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        onApiRequestListener.onApiRequestFailed(action, e);
                    }

                    @Override
                    public void onNext(List<News> news) {
                        onApiRequestListener.onApiRequestSuccess(action, news);
                    }
                });
    }

    public void createNews(final String token, final String title, final String body,
                           final String sourceUrl, final String imageUrl, final String reportedBy) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_POST_NEWS);
        Observable<News> observable = AppConstants.API_INTERFACE.createNews(token,title,body,
                                                    sourceUrl,imageUrl,reportedBy);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<News>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        onApiRequestListener.onApiRequestFailed(AppConstants.ACTION_POST_NEWS, e);
                    }

                    @Override
                    public void onNext(News news) {
                        onApiRequestListener.onApiRequestSuccess(AppConstants.ACTION_POST_NEWS, news);
                    }
                });
    }

    public void getNewsById(final String token, final int id) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_GET_NEWS_BY_ID);
        Observable<News> observable = AppConstants.API_INTERFACE.getNewsById(token,id);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<News>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        onApiRequestListener.onApiRequestFailed(AppConstants.ACTION_GET_NEWS_BY_ID, e);
                    }

                    @Override
                    public void onNext(News news) {
                        onApiRequestListener.onApiRequestSuccess(AppConstants.ACTION_GET_NEWS_BY_ID, news);
                    }
                });
    }

    public void getAnnouncements(final String token, final int start, final int limit) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_GET_ANNOUNCEMENTS);
        Observable<List<Announcement>> observable = AppConstants.API_INTERFACE.getAnnouncements(token,start,limit);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Announcement>>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        onApiRequestListener.onApiRequestFailed(AppConstants.ACTION_GET_ANNOUNCEMENTS, e);
                    }

                    @Override
                    public void onNext(List<Announcement> news) {
                        onApiRequestListener.onApiRequestSuccess(AppConstants.ACTION_GET_ANNOUNCEMENTS, news);
                    }
                });
    }

    public void createAnnouncement(final String token, final String title, final String message) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_POST_ANNOUNCEMENTS);
        Observable<Announcement> observable = AppConstants.API_INTERFACE.createAnnouncement(token,title,message);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Announcement>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        onApiRequestListener.onApiRequestFailed(AppConstants.ACTION_POST_ANNOUNCEMENTS, e);
                    }

                    @Override
                    public void onNext(Announcement announcement) {
                        onApiRequestListener.onApiRequestSuccess(AppConstants.ACTION_POST_ANNOUNCEMENTS, announcement);
                    }
                });
    }

    public void getAnnouncementById(final String token, final int announcementId) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_GET_ANNOUNCEMENT_BY_ID);
        Observable<Announcement> observable = AppConstants.API_INTERFACE.getAnnouncementById(token,announcementId);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Announcement>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        onApiRequestListener.onApiRequestFailed(AppConstants.ACTION_GET_ANNOUNCEMENT_BY_ID, e);
                    }

                    @Override
                    public void onNext(Announcement announcement) {
                        onApiRequestListener.onApiRequestSuccess(AppConstants.ACTION_GET_ANNOUNCEMENT_BY_ID, announcement);
                    }
                });
    }

    public void getLatestAnnouncements(final String token, final int announcementId) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_GET_LATEST_ANNOUNCEMENTS);
        Observable<List<Announcement>> observable = AppConstants.API_INTERFACE.getLatestAnnouncements(token,announcementId);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Announcement>>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        onApiRequestListener.onApiRequestFailed(AppConstants.ACTION_GET_LATEST_ANNOUNCEMENTS, e);
                    }

                    @Override
                    public void onNext(List<Announcement> announcements) {
                        onApiRequestListener.onApiRequestSuccess(AppConstants.ACTION_GET_LATEST_ANNOUNCEMENTS, announcements);
                    }
                });
    }

    public void getWaterLevelNotifications(final String token, final int start, final int limit) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_GET_WATER_LEVEL_NOTIFS);
        Observable<List<WaterLevel>> observable = AppConstants.API_INTERFACE.getWaterLevels(token,start,limit);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<WaterLevel>>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        onApiRequestListener.onApiRequestFailed(AppConstants.ACTION_GET_WATER_LEVEL_NOTIFS, e);
                    }

                    @Override
                    public void onNext(List<WaterLevel> waterLevels) {
                        onApiRequestListener.onApiRequestSuccess(AppConstants.ACTION_GET_WATER_LEVEL_NOTIFS, waterLevels);
                    }
                });
    }

    public void getLatestWaterLevelNotifications(final String token, final int id) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_POST_WATER_LEVEL_NOTIFS_LATEST);
        Observable<List<WaterLevel>> observable = AppConstants.API_INTERFACE.getLatestWaterLevels(token,id);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<WaterLevel>>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        onApiRequestListener.onApiRequestFailed(AppConstants.ACTION_POST_WATER_LEVEL_NOTIFS_LATEST, e);
                    }

                    @Override
                    public void onNext(List<WaterLevel> waterLevels) {
                        onApiRequestListener.onApiRequestSuccess(AppConstants.ACTION_POST_WATER_LEVEL_NOTIFS_LATEST, waterLevels);
                    }
                });
    }

    public void createWaterLevelNotification(final String token, final double level) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_POST_WATER_LEVEL_NOTIFS);
        Observable<WaterLevel> observable = AppConstants.API_INTERFACE.createWaterLevelNotification(token,level);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<WaterLevel>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        onApiRequestListener.onApiRequestFailed(AppConstants.ACTION_POST_WATER_LEVEL_NOTIFS, e);
                    }

                    @Override
                    public void onNext(WaterLevel waterLevel) {
                        onApiRequestListener.onApiRequestSuccess(AppConstants.ACTION_POST_WATER_LEVEL_NOTIFS, waterLevel);
                    }
                });
    }

    public void changePassword(final String token, final String email, final String oldPassword,
                               final String newPassword) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_PUT_CHANGE_PASSWORD);
        Observable<GenericMessage> observable = AppConstants.API_INTERFACE.changePassword(token, email,
                oldPassword, newPassword);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<GenericMessage>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onApiRequestListener.onApiRequestFailed(AppConstants.ACTION_PUT_CHANGE_PASSWORD, e);
                    }

                    @Override
                    public void onNext(GenericMessage genericMessage) {
                        onApiRequestListener.onApiRequestSuccess(AppConstants.ACTION_PUT_CHANGE_PASSWORD, genericMessage);
                    }
                });
    }

    public void changeProfilePic(final String token, final int userId, final String newPicUrl) {
        onApiRequestListener.onApiRequestBegin(AppConstants.ACTION_PUT_CHANGE_PROFILE_PIC);
        Observable<GenericMessage> observable = AppConstants.API_INTERFACE.changeProfilePic(token,userId,newPicUrl);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<GenericMessage>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onApiRequestListener.onApiRequestFailed(AppConstants.ACTION_PUT_CHANGE_PROFILE_PIC, e);
                    }

                    @Override
                    public void onNext(GenericMessage genericMessage) {
                        onApiRequestListener.onApiRequestSuccess(AppConstants.ACTION_PUT_CHANGE_PROFILE_PIC, genericMessage);
                    }
                });
    }
}
