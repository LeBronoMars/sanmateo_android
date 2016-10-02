package sanmateo.avinnovz.com.sanmateoprofile.singletons;

import java.util.ArrayList;

import sanmateo.avinnovz.com.sanmateoprofile.models.response.Announcement;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Incident;

/**
 * Created by rsbulanon on 7/9/16.
 */
public class AnnouncementsSingleton {

    private ArrayList<Announcement> announcements = new ArrayList<>();

    private static AnnouncementsSingleton ANNOUNCEMENTS = new AnnouncementsSingleton();

    private AnnouncementsSingleton() {}

    public static AnnouncementsSingleton getInstance() {
        return ANNOUNCEMENTS;
    }

    public ArrayList<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(ArrayList<Announcement> announcements) {
        this.announcements.clear();
        this.announcements.addAll(announcements);
    }

    public boolean isAnnouncementExisting(final int id) {
        for (Announcement a : getAnnouncements()) {
            if (a.getId() == id) {
                return true;
            }
        }
        return false;
    }
}
