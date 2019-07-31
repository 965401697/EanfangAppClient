package com.eanfang.base.kit.cache;

import android.content.Context;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.base.kit.utils.ApkUtils;
import com.eanfang.base.network.kit.VoKit;

import java.io.File;
import java.util.List;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;


/**
 * 缓存工具类
 *
 * @author jornl
 */
@SuppressWarnings("unchecked")
public class CacheKit extends LruCache<String, Object> {
    private static final String COM_EANFANG_BIZ_MODEL_VO = "com.eanfang.biz.model.vo";
    private static DisCacheKit disCacheKit;
    private static SpCacheKit spCacheKit;
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
                    disCacheKit = DisCacheKit.get(ApkUtils.getAppVersionCode(context), getDiskCacheDir(context));
                    spCacheKit = SpCacheKit.get(context);
                }
            }
        }
        return cacheKit;
    }

    /**
     * getStr
     *
     * @param key key
     * @return String
     */
    public String getStr(String key) {
        checkDue(key);
        this.clazz = String.class;
        Object obj = super.get(key);
        return obj != null ? obj.toString() : null;
    }

    /**
     * getInt
     *
     * @param key key
     * @param def def
     * @return Integer
     */
    public Integer getInt(String key, Integer def) {
        checkDue(key);
        this.clazz = Integer.class;
        Object obj = super.get(key);
        return obj != null ? Integer.parseInt(obj.toString()) : def;
    }

    /**
     * getLong
     *
     * @param key key
     * @param def def
     * @return Long
     */
    public Long getLong(String key, Long def) {
        checkDue(key);
        this.clazz = Long.class;
        Object obj = super.get(key);
        return obj != null ? Long.parseLong(obj.toString()) : def;
    }

    /**
     * getBool
     *
     * @param key key
     * @param def default
     * @return Boolean
     */
    public Boolean getBool(String key, Boolean def) {
        checkDue(key);
        this.clazz = Boolean.class;
        Object obj = super.get(key);
        return obj != null ? Boolean.parseBoolean(obj.toString()) : def;
    }


    /**
     * get
     *
     * @param key   key
     * @param clazz clazz
     * @param <T>   T
     * @return T
     */
    public <T> T get(String key, Class<T> clazz) {
        checkDue(key);
        this.clazz = clazz;
        if (this.clazz.getName().contains(COM_EANFANG_BIZ_MODEL_VO)) {
            this.clazz = JSONObject.class;
            Object result = super.get(key);
            if (result != null && (clazz.getName().equals(result.getClass().getName()))) {
                return (T) result;
            }
            JSONObject json = (JSONObject) result;
            return VoKit.obj2Vo(json, clazz);
        }
        return (T) super.get(key);
    }

    public <T> List<T> getArr(String key, Class<T> clazz) {
        checkDue(key);
        this.clazz = clazz;
        return (List<T>) super.get(key);
    }

    @Nullable
    @Override
    public Object put(@NonNull String key, @NonNull Object value) {
        return this.put(key, value, CacheMod.Disk, null);
    }

    /**
     * 是否磁盘缓存
     *
     * @param key   key
     * @param value value
     * @param mod   缓存模式
     * @return Object
     */
    @Nullable
    public Object put(@NonNull String key, @NonNull Object value, CacheMod mod) {
        return this.put(key, value, mod, null);
    }

    /**
     * 可以设置一个过期时间
     *
     * @param key   key
     * @param value value
     * @param due   due过期时间 秒单位
     * @return Object
     */
    public Object put(@NonNull String key, @NonNull Object value, Integer due) {
        return this.put(key, value, CacheMod.Disk, due);
    }

    /**
     * 缓存值
     *
     * @param key   key
     * @param value value
     * @param mod   true缓存磁盘，false不缓存磁盘
     * @param due   失效时间 秒为单位
     * @return Object
     */
    public Object put(@NonNull String key, @NonNull Object value, CacheMod mod, Integer due) {
        if (due != null) {
            String timeKey = key + "_seconds";
            disCacheKit.put(timeKey, DateUtil.currentSeconds() + due);
            super.put(timeKey, DateUtil.currentSeconds() + due);
        }
        if (mod.equals(CacheMod.Memory)) {
            return super.put(key, value);
        }
        if (mod.equals(CacheMod.Disk) || mod.equals(CacheMod.All)) {
            if (value.getClass().getName().contains(COM_EANFANG_BIZ_MODEL_VO)) {
                JSONObject jsonValue = VoKit.vo2Json(value);
                disCacheKit.put(key, jsonValue);
            } else {
                disCacheKit.put(key, value);
            }
        }
        if (mod.equals(CacheMod.SharePref) || mod.equals(CacheMod.All)) {
            if (value.getClass().getName().contains(COM_EANFANG_BIZ_MODEL_VO)) {
                JSONObject jsonValue = VoKit.vo2Json(value);
                spCacheKit.set(key, jsonValue);
            } else {
                spCacheKit.set(key, value);
            }
        }
        return super.put(key, value);
    }


    @Override
    public Object remove(@NonNull String key) {
        disCacheKit.remove(key);
        spCacheKit.remove(key);
        return super.remove(key);
    }

    public void clear() {
        disCacheKit.delete();
        spCacheKit.clear();
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
        if (obj != null || key.contains("_seconds")) {
            return obj;
        }
        obj = spCacheKit.get(key, clazz);
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
        this.clazz = Long.class;
        Object obj = super.get(timeKey);
        Long dueTime = obj != null ? Long.parseLong(obj + "") : null;
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
        String path = context.getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath();
        if (!FileUtil.exist(path)) {
            FileUtil.mkdir(path);
        }

        String oldPath = context.getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath();

        return new File(path + File.separator);
    }

}
