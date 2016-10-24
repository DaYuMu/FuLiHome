package cn.ucai.fulihome.dao;

import android.content.Context;

import cn.ucai.fulihome.bean.User;

/**
 * Created by Administrator on 2016/10/24 0024.
 */
public class UserDao {
    public static final String USER_TABLE_NAME = "t_superwechat_user";
    public static final String USER_COLUMN_NAME = "m_user_name";
    public static final String USER_COLUMN_NICK = "m_user_nick";
    public static final String USER_COLUMN_AVATAR_ID = "m_avatar_id";
    public static final String USER_COLUMN_AVATAR_TYPE = "m_avatar_type";
    public static final String USER_COLUMN_AVATAR_PATH= "m_avatar_path";
    public static final String USER_COLUMN_AVATAR_SUFFIX = "m_avatar_suffix";
    public static final String USER_COLUMN_AVATAR_LASTUPDATE_TIME = "m_avatar_lastupdata_time";

    public UserDao(Context context) {
        DBManager.getInstance().onInit(context);
    }
    public boolean saceUser(User user) {
        return DBManager.getInstance().saveUser(user);
    }

    public User getUser(String name) {
        return DBManager.getInstance().getUser(name);
    }
    public boolean update(User user) {
        return DBManager.getInstance().update(user);
    }
}
