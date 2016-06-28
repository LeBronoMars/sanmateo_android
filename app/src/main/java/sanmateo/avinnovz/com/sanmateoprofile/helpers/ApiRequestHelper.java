package sanmateo.avinnovz.com.sanmateoprofile.helpers;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.AuthResponse;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Incident;

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
}
