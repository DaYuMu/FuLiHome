package cn.ucai.fulihome.dao;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/10/24 0024.
 */
public class SharePreferenceUtils  {
    private static final String SHARE_NAME = "saveUserInfo";
    public static final String SHARE_KEY_USER_NAME = "share_key_user_name";
    private static SharePreferenceUtils instance;
    private SharedPreferences sharePreference;
    private SharedPreferences.Editor mEditor;


    public SharePreferenceUtils(Context context) {
        sharePreference= context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        mEditor = sharePreference.edit();
    }

    public static SharePreferenceUtils getInstance(Context context) {
        if (instance == null) {
            instance = new SharePreferenceUtils(context);
        }
        return instance;
    }

    public void saveUser(String name) {
        mEditor.putString(SHARE_KEY_USER_NAME, name);
        mEditor.commit();
    }

    public String getUser() {
        return sharePreference.getString(SHARE_KEY_USER_NAME, null);
    }

    public void removeuser() {
        mEditor.remove(SHARE_KEY_USER_NAME);
        mEditor.commit();
    }
}
