package net.eanfang.client.util;

import android.content.Context;
import android.content.SharedPreferences;

import net.eanfang.client.application.EanfangApplication;


/**
 * Created by houzhongzhou on 2016/11/14.
 * 单独对引导页进行存储
 */
public class PrefUtils {

    public static Context getContext() {
        return EanfangApplication.getApplication().getApplicationContext();
    }


    public static void setString(String key, String value) {
        SharedPreferences sp = getContext().getSharedPreferences("config",
                Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    public static String getString(String key, String defValue) {
        SharedPreferences sp = getContext().getSharedPreferences("config",
                Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    public static final String SHOWGUIDE = "showguid";//是否显示引导界面，true为显示，false为不显示

    //保存到preferences
    public static void setBoolean(Context context, String key, boolean vaule) {
        SharedPreferences preferences = getContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, vaule);
        editor.commit();
    }

    //从preferences取出来
    public static boolean getVBoolean(Context context, String key) {
        SharedPreferences preferences = getContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }
}