package com.chy.qrcode;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.chy.qrcode.dao.DaoMaster;
import com.chy.qrcode.dao.DaoSession;
import com.chy.qrcode.service.GeTuiPushService;
import com.chy.qrcode.service.GeTuiService;
import com.chy.qrcode.util.MetaUtil;
import com.chy.qrcode.util.UnCrashThread;
import com.igexin.sdk.PushManager;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

/**
 * Created by 47250 on 2017/6/26.
 */
public class Mapp extends Application {
    public static Mapp instances;

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static String channelType;
    public static Mapp getInstances() {
        return instances;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;
        if (isProcess(getPackageName())) {
            // 初始化
            channelType = MetaUtil.getChannelType(this);
            MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(this, "5953368bc62dca498c00096d", channelType));
            // 个推
            PushManager.getInstance().initialize(this.getApplicationContext(), GeTuiService.class);
            PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), GeTuiPushService.class);
           // initRestart();
        }
        initDatabase();
    }

    private void initDatabase() {
        /**
         * 设置greenDao
         */
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "qrcode-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    private boolean isProcess(String processName) {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && processName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 异常崩溃，重新启动应用
     */
    private void initRestart() {
        UnCrashThread unCrashThread = new UnCrashThread(this);
        Thread.setDefaultUncaughtExceptionHandler(unCrashThread);
    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }


}
