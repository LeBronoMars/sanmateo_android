package sanmateo.avinnovz.com.sanmateoprofile.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sanmateo.avinnovz.com.sanmateoprofile.dao.CurrentUser;
import sanmateo.avinnovz.com.sanmateoprofile.dao.CurrentUserDao;
import sanmateo.avinnovz.com.sanmateoprofile.dao.DaoMaster;
import sanmateo.avinnovz.com.sanmateoprofile.dao.DaoSession;
import sanmateo.avinnovz.com.sanmateoprofile.dao.LocalGallery;
import sanmateo.avinnovz.com.sanmateoprofile.dao.LocalGalleryDao;
import sanmateo.avinnovz.com.sanmateoprofile.dao.LocalOfficial;
import sanmateo.avinnovz.com.sanmateoprofile.dao.LocalOfficialDao;
import sanmateo.avinnovz.com.sanmateoprofile.dao.PanicContact;
import sanmateo.avinnovz.com.sanmateoprofile.dao.PanicContactDao;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.AuthResponse;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Photo;


/**
 * Created by rsbulanon on 7/14/16.
 */
public class DaoHelper {

    private static SQLiteDatabase DB;
    private static PanicContactDao DAO_PANIC_CONTACT;
    private static CurrentUserDao DAO_CURRENT_USER;
    private static LocalGalleryDao DAO_LOCAL_GALLERY;
    private static LocalOfficialDao DAO_LOCAL_OFFICIAL;

    public static void initialize(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "dev-profile-app-db-v1.0", null);
        DB = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(DB);
        DaoSession daoSession = daoMaster.newSession();
        DAO_PANIC_CONTACT = daoSession.getPanicContactDao();
        DAO_CURRENT_USER = daoSession.getCurrentUserDao();
        DAO_LOCAL_GALLERY = daoSession.getLocalGalleryDao();
        DAO_LOCAL_OFFICIAL = daoSession.getLocalOfficialDao();
    }

    public static void addContact(PanicContact panicContact) {
        DAO_PANIC_CONTACT.insert(panicContact);
    }

    public static boolean isContactExisting(String number) {
        return DAO_PANIC_CONTACT.queryBuilder().where(PanicContactDao.Properties.ContactNo
                .eq(number)).limit(1).count() > 0;
    }

    public static List<PanicContact> getAllPanicContacs() {
        return DAO_PANIC_CONTACT.loadAll();
    }

    public static void updatePanicContact(PanicContact panicContact) {
        DAO_PANIC_CONTACT.update(panicContact);
    }

    public static void deletePanicContact(PanicContact panicContact) {
        DAO_PANIC_CONTACT.delete(panicContact);
    }

    public static long getPanicContactSize() {
        return DAO_PANIC_CONTACT.count();
    }

    public static boolean haCurrentUser() {
        return DAO_CURRENT_USER.loadAll().size() > 0;
    }

    public static CurrentUser getCurrentUser() {
        return DAO_CURRENT_USER.loadAll().get(0);
    }

    public static void saveCurrentUser(AuthResponse authResponse) {
        DAO_CURRENT_USER.deleteAll();
        final CurrentUser currentUser = new CurrentUser(null,authResponse.getId(),
                authResponse.getCreatedAt(),authResponse.getUpdatedAt(),authResponse.getFirstName(),
                authResponse.getLastName(),authResponse.getEmail(),authResponse.getAddress(),
                authResponse.getContactNo(),authResponse.getStatus(),authResponse.getUserLevel(),
                authResponse.getGender(), authResponse.getPicUrl(),authResponse.getToken());
        DAO_CURRENT_USER.insert(currentUser);
    }

    public static void updateCurrentUser(final CurrentUser currentUser) {
        DAO_CURRENT_USER.update(currentUser);
    }

    public static void deleteCurrentUser() {
        DAO_CURRENT_USER.deleteAll();
    }

    public static boolean hasGalleryPhotos() {
        return DAO_LOCAL_GALLERY.loadAll().size() > 0;
    }

    public static void saveGalleryPhotos(List<LocalGallery> localGalleries) {
        DAO_LOCAL_GALLERY.deleteAll();
        for (LocalGallery localGallery: localGalleries) {
            DAO_LOCAL_GALLERY.insert(localGallery);
        }
    }

    public static void saveFromGalleryPhotos(ArrayList<Photo> galleryPhotos) {
        DAO_LOCAL_GALLERY.deleteAll();
        for (Photo gp: galleryPhotos) {
            String id = gp.getId();
            String createdAt = gp.getCreatedAt();
            String updatedAt = gp.getUpdatedAt();
            String deletedAt = gp.getDeletedAt();
            String title = gp.getTitle();
            String imageUrl = gp.getImageUrl();
            String description = gp.getDescription();

            DAO_LOCAL_GALLERY.insert(new LocalGallery(null, id, createdAt, updatedAt, deletedAt,
                    title, imageUrl, description));
        }
    }

    public static List<LocalGallery> getAllGalleryPhotos() {
        return DAO_LOCAL_GALLERY.loadAll();
    }

    public static List<LocalOfficial> getAllOfficials() { return DAO_LOCAL_OFFICIAL.loadAll(); }

    public static void createOfficial(final LocalOfficial localOfficial) {
        DAO_LOCAL_OFFICIAL.insert(localOfficial);
    }
}

