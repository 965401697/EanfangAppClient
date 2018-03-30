package com.eanfang.localcache;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.eanfang.util.ApkUtils;
import com.jakewharton.disklrucache.DiskLruCache;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;


/**
 * Created by xudingbing on 2018/3/29.
 * 使用说明：
 * 1、写缓存：
 * CacheUtil.put(getContext(),"com.yaf.client",keyStr,jsonStr)
 * 2、批量写缓存
 * CacheUtil.put(getContext(),"com.yaf.client",keyStr,jsonStrList)
 * 3、读缓存
 * CacheUtil.get(getContext(),"com.yaf.client",keyStr,(result)->{ System.out.println(result); })
 * 4、批量读缓存
 * CacheUtil.get(getContext(),"com.yaf.client",keyStrList,(result)->{ for(String val:result){System.out.println(val);} })
 * 5、清空缓存
 * CacheUtil.remove(getContext(),"com.yaf.client",keyStr)
 * 6、批量清空缓存
 * * CacheUtil.remove(getContext(),"com.yaf.client",keyStrList)
 * 7、删除特定缓存
 * CacheUtil.removeAll(getContext(),"com.yaf.client")
 */

public class CacheUtil {

    private static ConcurrentHashMap<String, DiskLruCache> caches = new ConcurrentHashMap<String, DiskLruCache>();

    private static DiskLruCache getDiskLruCache(Context context, String uniqueName) {
        DiskLruCache cache = caches.get(uniqueName);
        if (cache == null) {
            cache = initCache(context, uniqueName);
            caches.put(uniqueName, cache);
        }
        return cache;
    }

    public static void put(Context context, String uniqueName, List<String> keys, List<String> values) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DiskLruCache mDiskLruCache = getDiskLruCache(context, uniqueName);
                    for (int i = 0; i < keys.size(); i++) {
                        String keyHash = hashKeyForDisk(keys.get(i));
                        DiskLruCache.Editor editor = mDiskLruCache.edit(keyHash);
                        if (editor != null) {
                            editor.set(0, values.get(i));
                            editor.commit();
                        }
                    }
                    mDiskLruCache.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void put(Context context, String uniqueName, String key, String value) {
        Log.e("input", "onstart");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DiskLruCache mDiskLruCache = getDiskLruCache(context, uniqueName);
                    String keyHash = hashKeyForDisk(key);
                    DiskLruCache.Editor editor = mDiskLruCache.edit(keyHash);
                    if (editor != null) {
                        editor.set(0, value);
                        editor.commit();
                    }
                    mDiskLruCache.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e("returnput", "start");
            }
        }).start();
    }

    public static void get(Context context, String uniqueName, String key, CacheGetCallBack callBack) {
        Log.e("inget", "onget");
        new Thread(() -> {
            try {
                DiskLruCache mDiskLruCache = getDiskLruCache(context, uniqueName);
                String keyHash = hashKeyForDisk(key);
                DiskLruCache.Snapshot shot = mDiskLruCache.get(keyHash);
                if (shot == null) {
                    callBack.readValue(null);
                } else {
                    String result = mDiskLruCache.get(keyHash).getString(0);
                    callBack.readValue(result);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.e("returnget", "123");
        }).start();
    }

    public static String get(Context context, String uniqueName, String key) {
        String info = null;
        try {
            DiskLruCache mDiskLruCache = getDiskLruCache(context, uniqueName);
            String keyHash = hashKeyForDisk(key);
            DiskLruCache.Snapshot shot = mDiskLruCache.get(keyHash);
            if (shot != null) {
                String result = mDiskLruCache.get(keyHash).getString(0);
                info = result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return info;
    }

    public static void getMulti(Context context, String uniqueName, List<String> keys, CacheGetCallBackMulti callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DiskLruCache mDiskLruCache = getDiskLruCache(context, uniqueName);
                    List<String> result = new LinkedList<String>();
                    for (String key : keys) {
                        String keyHash = hashKeyForDisk(key);
                        String value = mDiskLruCache.get(keyHash).getString(0);
                        result.add(value);
                    }
                    callBack.readValue(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void remove(Context context, String uniqueName, List<String> keys) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DiskLruCache mDiskLruCache = getDiskLruCache(context, uniqueName);
                    for (String key : keys) {
                        String keyHash = hashKeyForDisk(key);
                        mDiskLruCache.remove(keyHash);
                    }
                    mDiskLruCache.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void remove(Context context, String uniqueName, String key) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DiskLruCache mDiskLruCache = getDiskLruCache(context, uniqueName);
                    String keyHash = hashKeyForDisk(key);
                    mDiskLruCache.remove(keyHash);
                    mDiskLruCache.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void removeAll(Context context, String uniqueName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DiskLruCache mDiskLruCache = getDiskLruCache(context, uniqueName);
                    mDiskLruCache.delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

//    private static int getAppVersion(Context context) {
//        try {
//            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
//            return info.versionCode;
//        } catch (NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        return 1;
//    }

    private static DiskLruCache initCache(Context context, String uniqueName) {
        DiskLruCache mDiskLruCache = null;
        try {
            File cacheDir = getDiskCacheDir(context, uniqueName);
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir, ApkUtils.getAppVersionCode(context), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mDiskLruCache;
    }

    public static String hashKeyForDisk(String key) {
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

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
