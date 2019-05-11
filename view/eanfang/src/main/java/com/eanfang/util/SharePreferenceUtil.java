package com.eanfang.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.io.IOException;

/**
 * @author wen
 *         Created at 2017/3/2
 * @desc
 */
public class SharePreferenceUtil {
    static String NAME = "config";
    private static volatile SharePreferenceUtil util = new SharePreferenceUtil();
    Context mContext;
    SharedPreferences sp;

    private SharePreferenceUtil() {
    }

    public static SharePreferenceUtil get() {
        if (util == null) {
            synchronized (SharePreferenceUtil.class) {
                if (util == null) {
                    util = new SharePreferenceUtil();
                }
            }
        }
        return util;
    }

    public SharedPreferences init(Context context) {
        mContext = context;
        sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp;
    }

    public void set(String key, Object value) throws IOException {
        if (value instanceof Boolean) {
            sp.edit().putBoolean(key, (Boolean) value).apply();
        } else if (value instanceof String) {
            sp.edit().putString(key, (String) value).apply();
        } else if (value instanceof Integer) {
            sp.edit().putInt(key, (Integer) value).apply();
        } else if (value instanceof Float) {
            sp.edit().putFloat(key, (Float) value).apply();
        } else if (value instanceof Long) {
            sp.edit().putLong(key, (Long) value).apply();
        } else {
            sp.edit().putString(key, ObjectUtil.object2String(value)).apply();
        }
        //提交缓存
        sp.edit().commit();
    }

    public Object get(String key, Object object) throws Exception {
        if (object instanceof Boolean) {
            return getBoolean(key, (Boolean) object);
        }
        if (object instanceof String) {
            return getString(key, (String) object);
        }
        if (object instanceof Integer) {
            return getInt(key, (Integer) object);
        }
        if (object instanceof Float) {
            return getFloat(key, (Float) object);
        }
        if (object instanceof Long) {
            return getLong(key, (Long) object);
        }
        String str = getString(key, "");
        return TextUtils.isEmpty(str) ? object : ObjectUtil.string2Object(str);
    }

    public void clear() {
        sp.edit().clear().apply();
    }

    public boolean remove(String key) {
        return sp.edit().remove(key).commit();
    }

    public int getInt(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public float getFloat(String key, float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    public String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    public long getLong(String key, long defaultValue) {
        return sp.getLong(key, defaultValue);
    }
}
