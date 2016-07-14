package sanmateo.avinnovz.com.sanmateoprofile.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import sanmateo.avinnovz.com.sanmateoprofile.dao.PanicContact;

import sanmateo.avinnovz.com.sanmateoprofile.dao.PanicContactDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig panicContactDaoConfig;

    private final PanicContactDao panicContactDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        panicContactDaoConfig = daoConfigMap.get(PanicContactDao.class).clone();
        panicContactDaoConfig.initIdentityScope(type);

        panicContactDao = new PanicContactDao(panicContactDaoConfig, this);

        registerDao(PanicContact.class, panicContactDao);
    }
    
    public void clear() {
        panicContactDaoConfig.getIdentityScope().clear();
    }

    public PanicContactDao getPanicContactDao() {
        return panicContactDao;
    }

}
