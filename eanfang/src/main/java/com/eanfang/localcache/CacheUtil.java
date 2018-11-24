package com.eanfang.localcache;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


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

    private static DiskLruCache getDiskLruCache(Context context, String uniqueName) {
        DiskLruCache cache = initCache(context, uniqueName);
        return cache;
    }

    public static void put(Context context, String uniqueName, List<String> keys, List<String> values) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try (DiskLruCache mDiskLruCache = getDiskLruCache(context, uniqueName)){
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
                    Log.e("eanfang.putCache",e.getMessage());
                }
            }
        }).start();
    }

    public static void put(Context context, String uniqueName, String key, String value) {
        String keyHash = hashKeyForDisk(key);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try (DiskLruCache mDiskLruCache = getDiskLruCache(context, uniqueName)){
                    DiskLruCache.Editor editor = mDiskLruCache.edit(keyHash);
                    if (editor != null) {
                        editor.set(0, value);
                        editor.commit();
                    }
                    mDiskLruCache.flush();
                } catch (IOException e) {
                    Log.e("eanfang.putCache",e.getMessage());
                }
            }
        }).start();
    }

    public static void get(Context context, String uniqueName, String key, CacheGetCallBack callBack) {
        String keyHash = hashKeyForDisk(key);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try (DiskLruCache mDiskLruCache = getDiskLruCache(context, uniqueName);
                     DiskLruCache.Snapshot shot = mDiskLruCache.get(keyHash);
                ){
                    if (shot == null) {
                        callBack.readValue(null);
                    } else {
                        String result = shot.getString(0);
                        callBack.readValue(result);
                    }
                } catch (IOException e) {
                    Log.e("eanfang.getCache",e.getMessage());
                }

            }
        }).start();
    }
    public static String get(Context context, String uniqueName, String key) {
        String keyHash = hashKeyForDisk(key);
        try (DiskLruCache mDiskLruCache = getDiskLruCache(context, uniqueName);
             DiskLruCache.Snapshot shot = mDiskLruCache.get(keyHash);
        ){
           if (shot == null) {
                return null;
            } else {
                return shot.getString(0);
            }
        } catch (IOException e) {
            Log.e("eanfang.getCache",e.getMessage());
        }

        return null;
    }
    public static void getMulti(Context context, String uniqueName, List<String> keys, CacheGetCallBackMulti callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try (DiskLruCache mDiskLruCache = getDiskLruCache(context, uniqueName)){
                    List<String> result = new LinkedList<String>();
                    for (String key : keys) {
                        String keyHash = hashKeyForDisk(key);
                        try(DiskLruCache.Snapshot shot = mDiskLruCache.get(keyHash);) {
                            if (shot == null) {
                                result.add(null);
                            } else {
                                String value = mDiskLruCache.get(keyHash).getString(0);
                                result.add(value);
                            }
                        }
                    }
                    callBack.readValue(result);
                } catch (IOException e) {
                    Log.e("eanfang.getMulti",e.getMessage());
                }
            }
        }).start();
    }
    public static List<String> getMulti(Context context, String uniqueName, List<String> keys) {
        try (DiskLruCache mDiskLruCache = getDiskLruCache(context, uniqueName)){
            List<String> result = new LinkedList<String>();
            for (String key : keys) {
                String keyHash = hashKeyForDisk(key);
                try(DiskLruCache.Snapshot shot =mDiskLruCache.get(keyHash);) {
                    if (shot == null) {
                        result.add(null);
                    } else {
                        String value = shot.getString(0);
                        result.add(value);
                    }
                }
            }
            return result;
        } catch (IOException e) {
            Log.e("eanfang.getMulti",e.getMessage());
        }
        return null;
    }
    public static Object getObject(Context context, String uniqueName, String key) {
        Log.e("inget", "onget");
        try (DiskLruCache mDiskLruCache = getDiskLruCache(context, uniqueName)){
            String keyHash = hashKeyForDisk(key);
            try(DiskLruCache.Snapshot shot = mDiskLruCache.get(keyHash);) {
                if (shot == null) {
                    return null;
                } else {
                    try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(shot.getInputStream(0)))) {
                        Object javaBean = in.readObject();
                    } catch (Exception e) {
                        Log.e("eanfang.cacheReadObj", e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            Log.e("eanfang.cacheReadObj",e.getMessage());
        }

        Log.e("returnget", "pk");
        return null;
    }
    public static void putObject(Context context, String uniqueName, String key, Object javaBean) {
        Log.e("input", "onstart");
        String keyHash = hashKeyForDisk(key);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try (DiskLruCache mDiskLruCache = getDiskLruCache(context, uniqueName)){
                    DiskLruCache.Editor editor = mDiskLruCache.edit(keyHash);
                    if (editor != null) {
                        try(ObjectOutputStream out=new ObjectOutputStream(new BufferedOutputStream(editor.newOutputStream(0)))){
                            out.writeObject(javaBean);
                        }catch (Exception e) {
                            Log.e("eanfang.cacheObj",e.getMessage());
                        }
                        editor.commit();
                    }
                    mDiskLruCache.flush();
                } catch (IOException e) {
                    Log.e("eanfang.cacheObj",e.getMessage());
                }
                Log.e("returnput", "start");
            }
        }).start();
    }

    public static void remove(Context context, String uniqueName, List<String> keys) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try (DiskLruCache mDiskLruCache = getDiskLruCache(context, uniqueName)){
                    for (String key : keys) {
                        String keyHash = hashKeyForDisk(key);
                        mDiskLruCache.remove(keyHash);
                    }
                    mDiskLruCache.flush();
                } catch (IOException e) {
                    Log.e("eanfang.cacheRemove",e.getMessage());
                }
            }
        }).start();
    }

    public static void remove(Context context, String uniqueName, String key) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try (DiskLruCache mDiskLruCache = getDiskLruCache(context, uniqueName)){
                    String keyHash = hashKeyForDisk(key);
                    mDiskLruCache.remove(keyHash);
                    mDiskLruCache.flush();
                } catch (IOException e) {
                    Log.e("eanfang.cacheRemove",e.getMessage());
                }
            }
        }).start();
    }

    public static void removeAll(Context context, String uniqueName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try (DiskLruCache mDiskLruCache = getDiskLruCache(context, uniqueName)){
                    mDiskLruCache.delete();
                } catch (IOException e) {
                    Log.e("eanfang.removeAll",e.getMessage());
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

    private static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            Log.e("eanfang.getAppVersion",e.getMessage());
        }
        return 1;
    }

    private static DiskLruCache initCache(Context context, String uniqueName) {
        DiskLruCache mDiskLruCache = null;
        try {
            File cacheDir = getDiskCacheDir(context, uniqueName);
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(context), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            Log.e("eanfang.initCache",e.getMessage());
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
