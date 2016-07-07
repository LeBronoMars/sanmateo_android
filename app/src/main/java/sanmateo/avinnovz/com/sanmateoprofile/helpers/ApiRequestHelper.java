package sanmateo.avinnovz.com.sanmateoprofile.helpers;

import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.AuthResponse;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Incident;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.News;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.User;

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

    public void getNews(final String token, final int start, final int limit, final String status,
                        final String when) {
        onApiRequestListener.onApiRequestBegin(when);
        Observable<List<News>> observable = AppConstants.API_INTERFACE.getNews(token,start,limit,status,when);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<News>>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        onApiRequestListener.onApiRequestFailed(when, e);
                    }

                    @Override
                    public void onNext(List<News> news) {
                        onApiRequestListener.onApiRequestSuccess(when, news);
                    }
                });
    }

}
