package sanmateo.avinnovz.com.sanmateoprofile.singletons;

import sanmateo.avinnovz.com.sanmateoprofile.models.response.AuthResponse;

/**
 * Created by rsbulanon on 6/26/16.
 */
public class CurrentUserSingleton {

    private static CurrentUserSingleton CURR_USER = new CurrentUserSingleton();
    private AuthResponse authResponse;

    private CurrentUserSingleton() {}

    public static CurrentUserSingleton newInstance() {
        return CURR_USER;
    }

    public AuthResponse getAuthResponse() {
        return authResponse;
    }

    public void setAuthResponse(AuthResponse authResponse) {
        this.authResponse = authResponse;
    }
}
