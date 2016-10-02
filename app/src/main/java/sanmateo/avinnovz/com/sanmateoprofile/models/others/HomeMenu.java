package sanmateo.avinnovz.com.sanmateoprofile.models.others;

import android.graphics.drawable.Drawable;

/**
 * Created by rsbulanon on 6/26/16.
 */
public class HomeMenu {

    private Drawable menuImage;
    private String menuTitle;

    public HomeMenu(Drawable menuImage, String menuTitle) {
        this.menuImage = menuImage;
        this.menuTitle = menuTitle;
    }

    public Drawable getMenuImage() {
        return menuImage;
    }

    public String getMenuTitle() {
        return menuTitle;
    }
}
