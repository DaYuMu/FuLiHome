package cn.ucai.fulihome.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import cn.ucai.fulihome.I;
import cn.ucai.fulihome.bean.User;
import cn.ucai.fulihome.utils.L;

/**
 * Created by Administrator on 2016/10/24 0024.
 */
public class DBManager {
    private static final String TAG = DBManager.class.getSimpleName();
    private static DBManager dbManager = new DBManager();
    DBOpenHelper dbOpenHelper;

    void onInit(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    public static synchronized DBManager getInstance() {
        return dbManager;
    }

    public DBManager() {
    }


    public synchronized void closeDB() {
        if (dbOpenHelper != null) {
            dbOpenHelper.closeDB();
        }
    }

    public synchronized boolean saveUser(User user) {
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserDao.USER_COLUMN_NAME, user.getMuserName());
        contentValues.put(UserDao.USER_COLUMN_NICK, user.getMuserNick());
        contentValues.put(UserDao.USER_COLUMN_AVATAR_ID, user.getMavatarId());
        contentValues.put(UserDao.USER_COLUMN_AVATAR_TYPE, user.getMavatarType());
        contentValues.put(UserDao.USER_COLUMN_AVATAR_PATH, user.getMavatarPath());
        contentValues.put(UserDao.USER_COLUMN_AVATAR_SUFFIX, user.getMavatarSuffix());
        contentValues.put(UserDao.USER_COLUMN_AVATAR_LASTUPDATE_TIME, user.getMavatarLastUpdateTime());
        Log.e(TAG,"user="+user);
        if (database.isOpen()) {
            return database.replace(UserDao.USER_TABLE_NAME, null, contentValues) != -1;
        }
        return false;
    }

    public synchronized User getUser(String name) {
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        String sql = "select * from " + UserDao.USER_TABLE_NAME + " where " + UserDao.USER_COLUMN_NAME + " +?";
        L.e(TAG,"sql="+sql);
        User user = null;
        Cursor cursor = database.rawQuery(sql, new String[]{name});
        if (cursor.moveToNext()) {
            user = new User();
            user.setMuserName(name);
            user.setMuserNick(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_NICK)));
            user.setMavatarId(cursor.getInt(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_ID)));
            user.setMavatarType(cursor.getInt(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_TYPE)));
            user.setMavatarPath(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_PATH)));
            user.setMavatarSuffix(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_SUFFIX)));
            user.setMavatarLastUpdateTime(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_LASTUPDATE_TIME)));
            return user;
        }
        return user;
    }

    public synchronized boolean update(User user) {
        int resule = -1;
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        String sql = UserDao.USER_COLUMN_NAME + " =?";
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserDao.USER_COLUMN_NICK, user.getMuserNick());
        if (database.isOpen()) {
            resule = database.update(UserDao.USER_TABLE_NAME, contentValues, sql, new String[]{user.getMuserName()});
        }
        return resule > 0;
    }
}
