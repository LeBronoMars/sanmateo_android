package sanmateo.avinnovz.com.sanmateoprofile.helpers;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.OnApiRequestListener;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.AuthResponse;

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

}
