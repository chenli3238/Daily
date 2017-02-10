package com.wqy.daily;

import android.app.Application;

import com.wqy.daily.model.DaoMaster;
import com.wqy.daily.model.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by wqy on 17-2-10.
 */

public class App extends Application {

    private DaoSession mDaoSession;
    public static final String DB_NAME = "daily.db";

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(
                getApplicationContext(), DB_NAME);
        Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
