package com.eanfang.base.kit.cache;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;

public class CacheKit extends LruCache<String, Object> {
    private static DisCacheKit disCacheKit;
    private static CacheKit cacheKit;
    private Class clazz;

    private CacheKit(int maxSize) {
        super(maxSize);
    }

    public static CacheKit get() {
        if (cacheKit == null) {
            throw new RuntimeException("请先初始化 CacheKit");
        }
        return cacheKit;
    }

    public static CacheKit init(Context context) {
        if (cacheKit == null) {
            synchronized (CacheKit.class) {
                if (cacheKit == null) {
                    cacheKit = new CacheKit(100);
                    disCacheKit = DisCacheKit.get(getAppVersion(context), getDiskCacheDir(context));
                }
            }
        }
        return cacheKit;
    }

    public String getStr(String key) {
        checkDue(key);
        this.clazz = String.class;
        return (String) super.get(key);
    }


    public Integer getInt(String key) {
        checkDue(key);
        this.clazz = Integer.TYPE;
        return (Integer) super.get(key);
    }

    public Long getLong(String key) {
        checkDue(key);
        this.clazz = Long.TYPE;
        return (Long) super.get(key);
    }

    public Boolean getBool(String key, boolean def) {
        checkDue(key);
        this.clazz = Boolean.TYPE;
        Boolean result = (Boolean) super.get(key);
        return result != null ? result : def;
    }

    public <T> T get(String key, Class<T> clazz) {
        checkDue(key);
        this.clazz = clazz;
        return (T) super.get(key);
    }

    public <T> List<T> getArr(String key, Class<T> clazz) {
        checkDue(key);
        this.clazz = clazz;
        return (List<T>) super.get(key);
    }

    /**
     * 判断是否是客户端 临时使用
     * 2019-05-05 14:24:15
     *
     * @return boolean
     */
    public boolean isClient() {
        return getStr("APP_TYPE").equals("client");
    }

    @Nullable
    @Override
    public Object put(@NonNull String key, @NonNull Object value) {
        disCacheKit.put(key, value);
        return super.put(key, value);
    }

    public Object putVo(String key, Object value) {
        JSONObject object = new JSONObject();

        Field[] fields = value.getClass().getDeclaredFields();
        for (Field field : fields) {
            //忽略掉transient
            if (field.toString().contains(" transient ")) {
                continue;
            }
            String fieldName = field.getName();
            Object val;
            //解决viewModel的Observable数据类型
            if ((field.getType().toString().contains("androidx.databinding.Observable"))) {
                Object obj = ReflectUtil.getFieldValue(value, fieldName);
                val = ReflectUtil.invoke(obj, "get");
            } else {
                val = ReflectUtil.getFieldValue(value, fieldName);
            }
            if (val != null) {
                //如果是空map  空list  空字符 则跳过参数
                if (val instanceof Map && ((Map) val).isEmpty()) {
                    continue;
                }
                if (val instanceof List && ((List) val).isEmpty()) {
                    continue;
                }
                if (StrUtil.isEmpty(val.toString())) {
                    continue;
                }
                object.put(fieldName, val);
            }
        }
        return put(key, object.toString(), 1);
    }

    public <T> T getVo(String key, Class<T> clazz) {
        Object object = null;
        try {
            object = clazz.newInstance();
            String json = (String) get(key, clazz);
            JSONObject jsonObject = JSON.parseObject(json);
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                if (jsonObject.get(fieldName) != null) {
                    //获取viewModel的Observable数据类型
                    if ((field.getType().toString().contains("androidx.databinding.Observable"))) {
                        Object objs = ReflectUtil.getFieldValue(object, fieldName);
                        ReflectUtil.invoke(objs, "set", new Object[]{jsonObject.get(fieldName)});
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return (T) object;
    }

    @Nullable
    public Object put(@NonNull String key, @NonNull Object value, boolean noDisk) {
        if (noDisk) {
            return super.put(key, value);
        }
        disCacheKit.put(key, value);
        return super.put(key, value);
    }


    /**
     * 可以设置一个过期时间
     *
     * @param key   key
     * @param value value
     * @param due   due过期时间 秒单位
     * @return
     */
    @Nullable
    public Object put(@NonNull String key, @NonNull Object value, Integer due) {
        if (due != null) {
            String timeKey = key + "_seconds";
            disCacheKit.put(timeKey, DateUtil.currentSeconds() + due);
            super.put(timeKey, DateUtil.currentSeconds() + due);
        }

        disCacheKit.put(key, value);
        return super.put(key, value);
    }


    @Nullable
    @Override
    public Object remove(@NonNull String key) {
        disCacheKit.remove(key);
        return super.remove(key);
    }

    public void clear() {
        disCacheKit.delete();
        super.evictAll();
    }

    /**
     * 如果从lrc中取不到数据 会调用此方法尝试获取值
     *
     * @param key key
     * @return Object
     */
    @Override
    protected Object create(@NonNull String key) {
        super.create(key);
        Object obj = disCacheKit.get(key, this.clazz);
        if (obj != null) {
            return obj;
        }
        return disCacheKit.getArr(key, this.clazz);
    }

    /**
     * 检查是否过期，如果过期了 就清除
     *
     * @param key key
     */
    private void checkDue(String key) {
        DateUtil.currentSeconds();
        String timeKey = key + "_seconds";
        this.clazz = Long.TYPE;
        Long dueTime = (Long) super.get(timeKey);
        //超时 清除
        if (dueTime != null && dueTime < DateUtil.currentSeconds()) {
            remove(key);
            remove(timeKey);
        }
    }

    /**
     * 获取缓存路径
     *
     * @param context context
     * @return File
     */
    public static File getDiskCacheDir(Context context) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = Objects.requireNonNull(context.getExternalCacheDir()).getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator);
    }

    /**
     * getAppVersion
     *
     * @param context context
     * @return int
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("eanfang.getAppVersion", e.getMessage());
        }
        return 1;
    }
}
