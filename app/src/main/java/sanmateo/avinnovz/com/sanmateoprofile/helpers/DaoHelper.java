package sanmateo.avinnovz.com.sanmateoprofile.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import sanmateo.avinnovz.com.sanmateoprofile.dao.DaoMaster;
import sanmateo.avinnovz.com.sanmateoprofile.dao.DaoSession;
import sanmateo.avinnovz.com.sanmateoprofile.dao.PanicContact;
import sanmateo.avinnovz.com.sanmateoprofile.dao.PanicContactDao;


/**
 * Created by rsbulanon on 7/14/16.
 */
public class DaoHelper {

    private static SQLiteDatabase DB;
    private static PanicContactDao DAO_PANIC_CONTACT;

    public static void initialize(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "dev-profile-app-db-v1.0", null);
        DB = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(DB);
        DaoSession daoSession = daoMaster.newSession();
        DAO_PANIC_CONTACT = daoSession.getPanicContactDao();
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
}

