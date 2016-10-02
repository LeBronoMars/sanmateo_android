package sanmateo.avinnovz.com.sanmateoprofile.interfaces;

/**
 * Created by rsbulanon on 6/23/16.
 */
public interface OnApiRequestListener {

    void onApiRequestBegin(final String action);
    void onApiRequestSuccess(final String action, final Object result);
    void onApiRequestFailed(final String action, final Throwable t);
}
