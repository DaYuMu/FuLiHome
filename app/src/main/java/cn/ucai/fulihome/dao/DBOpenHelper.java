package cn.ucai.fulihome.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.ucai.fulihome.I;
import cn.ucai.fulihome.bean.User;

/**
 * Created by Administrator on 2016/10/24 0024.
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static DBOpenHelper instance;

    /**
     * 注意SQL语句中的空格不能忽略，，否则会造成SQL语句错误
     */
    private static final String FULIHOME_USER_TABLE_NAME = "CREATE TABLE "
            +UserDao.USER_TABLE_NAME+" （"
            +UserDao.USER_COLUMN_NAME+" TEXT PRIMARY KEY,"
            +UserDao.USER_COLUMN_NICK+" TEXT,"
            +UserDao.USER_COLUMN_AVATAR_ID+" INTEGER,"
            +UserDao.USER_COLUMN_AVATAR_PATH+" TEXT,"
            +UserDao.USER_COLUMN_AVATAR_TYPE+" INTEGER,"
            +UserDao.USER_COLUMN_AVATAR_SUFFIX+" TEXT,"
            +UserDao.USER_COLUMN_AVATAR_LASTUPDATE_TIME+" TEXT)";


    public static DBOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBOpenHelper(context.getApplicationContext());
        }
        return instance;
    }

    public DBOpenHelper(Context context) {
        super(context, getUserDatabaseName(), null, DATABASE_VERSION);
    }

    private static String getUserDatabaseName() {
        return I.User.TABLE_NAME+"_demo.db";
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(FULIHOME_USER_TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public static void closeDB() {
        if (instance != null) {
            SQLiteDatabase db = instance.getWritableDatabase();
            db.close();
            instance=null;
        }
    }
}
