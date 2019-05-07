package com.eanfang.kit.cache;

import androidx.core.util.Consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

class DisCacheKit {

    private static int appVersion;
    private static File cacheDir;

    private static DiskLruCache diskLruCache;

    public static DisCacheKit get(int appVersion, File cacheDir) {
        DisCacheKit.appVersion = appVersion;
        DisCacheKit.cacheDir = cacheDir;
        return new DisCacheKit();
    }

    /**
     * 放值
     *
     * @param key   key
     * @param value value
     * @return Object
     */
    public Object put(String key, Object value) {
        if (key == null || value == null) {
            return null;
        }
        Observable.create(emitter -> {
            DiskLruCache.Editor editor = getDisk().edit(hashKeyForDisk(key));
            if (editor != null) {
                editor.set(0, JSONObject.toJSONString(value));
                editor.commit();
            }
            getDisk().flush();
            emitter.onComplete();
        }).subscribeOn(Schedulers.io())
                .subscribe();
        return value;
    }

    /**
     * 异步取值 大量数据时使用
     *
     * @param key      key
     * @param clazz    clazz
     * @param callBack callBack
     * @param <T>      T
     */
    public <T> void get(String key, Class<T> clazz, Consumer<T> callBack) {
        Observable.create((ObservableOnSubscribe<T>) emitter -> {
            T result = null;
            DiskLruCache.Snapshot shot = getDisk().get(hashKeyForDisk(key));
            if (shot != null) {
                String json = shot.getString(0);
                //如果是json格式
                if (json.startsWith("{") && json.endsWith("}")) {
                    result = JSON.parseObject(json, clazz);
                } else {
                    result = (T) json;
                }
            }
            getDisk().flush();
            emitter.onNext(result);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack::accept);
    }

    /**
     * 同步获取数据 小量数据时
     *
     * @param key   key
     * @param clazz clazz
     * @param <T>   T
     * @return T
     */
    public <T> T get(String key, Class<T> clazz) {
        DiskLruCache.Snapshot shot;
        T result = null;
        try {
            shot = getDisk().get(hashKeyForDisk(key));
            if (shot != null) {
                String json = shot.getString(0);
                if (json.startsWith("{") && json.endsWith("}")) {
                    result = JSON.parseObject(json, clazz);
                } else if (json.startsWith("[") && json.endsWith("]")) {
                    result = null;
                } else {
                    result = (T) json;
                }
            }
            getDisk().flush();
            return result;
        } catch (IOException e) {
            // e.printStackTrace();
        }
        return null;
    }

    /**
     * 异步取list 大量数据时使用
     *
     * @param key      key
     * @param clazz    clazz
     * @param callBack callBack
     * @param <T>      T
     */
    public <T> void getArr(String key, Class<T> clazz, Consumer<List<T>> callBack) {
        Observable.create((ObservableOnSubscribe<List<T>>) emitter -> {
            List<T> result = null;
            DiskLruCache.Snapshot shot = getDisk().get(hashKeyForDisk(key));
            if (shot != null) {
                String json = shot.getString(0);
                //如果是json格式
                if (json.startsWith("[") && json.endsWith("]")) {
                    result = JSON.parseArray(json, clazz);
                } else {
                    result = Collections.singletonList((T) json);
                }
            }
            getDisk().flush();
            emitter.onNext(result);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack::accept);
    }

    /**
     * 取list
     *
     * @param key   key
     * @param clazz clazz
     * @param <T>   T
     * @return List<T>
     */
    public <T> List<T> getArr(String key, Class<T> clazz) {
        List<T> result = null;
        DiskLruCache.Snapshot shot = null;
        try {
            shot = getDisk().get(hashKeyForDisk(key));
            if (shot != null) {
                String json = shot.getString(0);
                //如果是json格式
                if (json.startsWith("[") && json.endsWith("]")) {
                    result = JSON.parseArray(json, clazz);
                } else {
                    result = Collections.singletonList((T) json);
                }
                getDisk().flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 移除数据
     *
     * @param key key
     */
    public void remove(String key) {
        Observable.create(emitter -> {
            getDisk().remove(hashKeyForDisk(key));
            getDisk().flush();
            emitter.onComplete();
        }).subscribeOn(Schedulers.io())
                .subscribe();
    }

    private synchronized DiskLruCache getDisk() {
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        try {
            if (diskLruCache == null) {
                diskLruCache = DiskLruCache.open(cacheDir, appVersion, 1, 10 * 1024 * 1024);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return diskLruCache;
    }

    private String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        // http://stackoverflow.com/questions/332079
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xFF & aByte);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

}
