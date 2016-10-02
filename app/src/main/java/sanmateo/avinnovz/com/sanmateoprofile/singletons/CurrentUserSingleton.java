package sanmateo.avinnovz.com.sanmateoprofile.singletons;

import sanmateo.avinnovz.com.sanmateoprofile.dao.CurrentUser;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.DaoHelper;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.AuthResponse;

/**
 * Created by rsbulanon on 6/26/16.
 */
public class CurrentUserSingleton {

    private static CurrentUserSingleton CURR_USER = new CurrentUserSingleton();
    private static CurrentUser currentUser;

    private CurrentUserSingleton() {}

    public static CurrentUserSingleton newInstance() {
        return CURR_USER;
    }

    public CurrentUser getCurrentUser() {
        if (currentUser == null) {
            currentUser = DaoHelper.getCurrentUser();
        }
        return currentUser;
    }

    public static void setCurrentUser(CurrentUser currentUser) {
        CurrentUserSingleton.currentUser = currentUser;
    }
}
