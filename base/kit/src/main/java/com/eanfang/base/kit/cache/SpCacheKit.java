package com.eanfang.base.kit.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.util.StrUtil;

/**
 * SharePreferenceUtil
 *
 * @author jornl
 * @date 2019年7月21日
 */
class SpCacheKit {
    private static SharedPreferences sp;

    private SpCacheKit() {
    }

    public static SpCacheKit get(Context context) {
        if (sp == null) {
            synchronized (SpCacheKit.class) {
                if (sp == null) {
                    String NAME = "config";
                    sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
                }
            }
        }
        return new SpCacheKit();
    }

    public void set(String key, Object value) {
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
            sp.edit().putString(key, JSON.toJSONString(value)).apply();
        }
        //提交缓存
        sp.edit().apply();
    }

    public <T> Object get(String key, Class<T> clazz) {
        if (!sp.contains(key)) {
            return null;
        }
//        String str = getString(key, "");
        if (clazz.getName().equals(Boolean.class.getName())) {
            return sp.getBoolean(key, false);
        }
        if (clazz.getName().equals(String.class.getName())) {
            return sp.getString(key, "");
        }
        if (clazz.getName().equals(Integer.class.getName())) {
            return sp.getInt(key, 0);
        }
        if (clazz.getName().equals(Float.class.getName())) {
            return sp.getFloat(key, 0);
        }
        if (clazz.getName().equals(Double.class.getName())) {
            return sp.getFloat(key, 0);
        }
        if (clazz.getName().equals(Long.class.getName())) {
            return sp.getLong(key, 0);
        }
        String str = sp.getString(key, "");
        if (clazz.getName().equals(JSONObject.class.getName())) {
            return !StrUtil.isEmpty(str) ? JSON.parseObject(str) : null;
        }
        return !StrUtil.isEmpty(str) ? JSON.parseObject(str, clazz) : null;
    }

    public void clear() {
        sp.edit().clear().apply();
    }

    public boolean remove(String key) {
        return sp.edit().remove(key).commit();
    }

//    public String getString(String key, String defaultValue) {
//        return sp.get(key, defaultValue);
//    }
}