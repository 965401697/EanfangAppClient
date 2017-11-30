package net.eanfang.client.util;

import android.content.Context;
import android.content.SharedPreferences;

import net.eanfang.client.application.EanfangApplication;


/**
 * Created by houzhongzhou on 2016/11/14.
 * 单独对引导页进行存储
 */
public class PrefUtils {
    //是否显示引导界面，true为显示，false为不显示
    public static final String SHOWGUIDE = "showguid";
    public static final String PERSON_REPAIR_SWITCH_CHECK = "person_repair_switch_check";
    public static final String COMPANY_REPAIR_SWITCH_CHECK = "company_repair_switch_check";
    public static final String COMPANY_INSTALL_SWITCH_CHECK = "company_install_switch_check";
    public static final String RECEIVE_MSG_SWITCH_CHECK = "receive_msg_switch_check";

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
