/*
 * Copyright (c) 2016. Huangrui All rights reserved.
 */

package com.eanfang.kit.sdk.alisdk.alioss.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cn.hutool.core.io.FileUtil;

/**
 * @author hou
 *         Created at 2017/3/2
 * @desc
 */
public class FileUtils {
    public static final String PATH = Environment
            .getExternalStorageDirectory()
            .getAbsolutePath(); // 保存文件的路径
    public static String APP = PATH;                      // 应用路径
    public static String LOG = APP + ".Log/";
    public static String APK = APP + "app.apk";
    public static String DOWNLOAD_TEMP_PATH = APP + ".temp/";
    public static String IMAGE_CACHE = APP + "imageCache/";      // 图片缓存
    public static String USR = APP + ".usr/";
    public static String AUDIO_CACHE = USR + ".audio/";

    public static String DOWNLOAD = APP + "download/";
    public static final String VIDEO_STORAGE_DIR = Environment.getExternalStorageDirectory() + "/eanfang/";

    public static void init() {
        // 文件目录
        initDirectory(APP);
        // 日志目录
        initDirectory(LOG);
        initDirectory(USR);
        initDirectory(AUDIO_CACHE);
        // 图片缓存目录
        initDirectory(IMAGE_CACHE);
        initDownloadDir();
        // 删除apk文件
        deleteFile(APK);
    }

    public static void init(String name) {
        APP = PATH + "/" + name + "/";
        LOG = APP + ".Log/";
        APK = APP + name + ".apk";
        IMAGE_CACHE = APP + "img/";
        USR = APP + "usr/";
        AUDIO_CACHE = USR + ".audio/";
        DOWNLOAD_TEMP_PATH = APP + ".temp/";
        DOWNLOAD = APP + "download/";
        // 文件目录
        initDirectory(APP);
        // 日志目录
        initDirectory(LOG);
        initDirectory(USR);
        initDirectory(AUDIO_CACHE);
        initDirectory(IMAGE_CACHE);
        initDirectory(DOWNLOAD_TEMP_PATH);
        initDirectory(DOWNLOAD);
        deleteFile(APK);
    }

    /**
     * 初始化文件夹目录
     */
    public static void initDirectory(String path) {
        File file=FileUtil.touch(path);
        /*File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }*/
    }

    /**
     * 重置缓存目录
     */
    public static void resetCache() {
        deleteFile(IMAGE_CACHE);
        // deleteFile2(CACHE);
        deleteFile(DOWNLOAD_TEMP_PATH);
        initDirectory(IMAGE_CACHE);
        initDownloadDir();
    }

    /**
     * 初始化下载的目录
     */
    public static void initDownloadDir() {
        File f = new File(DOWNLOAD_TEMP_PATH);
        if (!f.exists()) {
            f.mkdir();
        }
    }


    /**
     * 判断文件是否存在
     *
     * @param path
     * @return
     */
    public static boolean isFileExit(String path) {
        File file = new File(path);
        return file.exists();
    }


    /**
     * 获取文件名
     *
     * @param file
     * @return
     */
    public static String getName(String file) {
        if (file != null && !"".equals(file)) {
            int index = file.lastIndexOf("/");
            if (index != -1) {
                return file.substring(index + 1);
            } else {
                return "";
            }
        }
        return null;
    }


    /**
     * 根据当前时间生成
     *
     * @param suffix
     */
    public static String newFileName(String suffix) {
        return PATH + "/" + System.currentTimeMillis() + "." + suffix;
    }

    /**
     * 删除升级apk文件
     */
    public static void delUpgradeAPKFile() {
        deleteFile(APP);
    }

    /**
     * 删除文件或文件夹
     *
     * @return
     */
    public static void deleteFile(String fileName) {
        FileUtil.del(fileName);
     /*   File f = new File(fileName);
        if (f.exists()) {
            if (f.isFile()) {
                f.delete();
            } else if (f.isDirectory()) {
                String[] filelist = f.list();
                for (int i = 0; i < filelist.length; i++) {
                    deleteFile(fileName + filelist[i]);
                }
            }
        }*/
    }

    public static void deleteFileDir(String fileName) {
        File f = new File(fileName);
        if (f.exists()) {
            if (f.isDirectory()) {
                String[] filelist = f.list();
                for (int i = 0; i < filelist.length; i++) {
                    deleteFile(fileName + filelist[i]);
                }
            }
        }
    }

    /**
     * 获取文件格式
     *
     * @param fileName
     * @return
     */
    public static String getExtension(String fileName) {
        int length = fileName.indexOf(".");
        return fileName.substring(length + 1);
    }

    /**
     * 判断文件夹是否存
     *
     * @param fileName
     * @return
     */
    public static boolean isFileExists(String fileName) {
        File f = new File(PATH + fileName);
        return f.isDirectory();
    }

    /**
     * 判断文件是否存在
     *
     * @param fileName
     * @return
     */
    public static boolean isExists(String fileName) {
        File f = new File(fileName);
        if (f.exists()) {
            if (f.length() == 0) {
                f.delete();
            }
        }
        return f.exists();
    }

    /**
     * 创建文件夹
     *
     * @param fileName
     */
    public static void createFile(String fileName) {
        File f = new File(fileName);
        if (f.exists()) {
            if (f.length() == 0) {
                f.delete();
            }
        } else {
            f.mkdirs();
        }
    }

    public static String newFileName(String fileName, String suffix) {
        return PATH + "/" + fileName + "." + suffix;
    }


    /**
     * 移动文件
     *
     * @param srcFile
     * @param destPath
     * @return
     */
    public static boolean Move(String srcFile, String destPath) {
        // File (or directory) to be moved
        File file = new File(srcFile);
        // Destination directory
        File dir = new File(destPath);
        // Move file to new directory
        boolean retCode = file.renameTo(new File(dir, file.getName()));
        return retCode;
    }


    public static byte[] toByteArray(String filename) {
        File f = new File(filename);
        if (!f.exists()) {
            return null;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 创建目录
     *
     * @param path_dir
     * @return
     */
    public static String makeDir(String path_dir) {
        // 新建目录
        File dir = new File(path_dir);
        if (!dir.exists()) {
            boolean success = dir.mkdirs();
            if (!success) {
                //LogUtils.e("makeDir", "创建目录失败: " + path_dir);
                return null;
            } else {
                //LogUtils.i("makeDir", "创建成功: " + path_dir);
            }
        }
        return path_dir;
    }

    /**
     * Bitmap保存成File
     *
     * @param bitmap input bitmap
     * @param name   output file's name
     * @return String output file's path
     */

    public static String bitmapToFile(Bitmap bitmap, String name) {

        if (bitmap == null) {
            return "";
        }

        File f = new File(VIDEO_STORAGE_DIR + name + ".jpg");

        if (f.exists()) {
            f.delete();
        }

        FileOutputStream fOut = null;

        try {

            fOut = new FileOutputStream(f);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);

            fOut.flush();

            fOut.close();

        } catch (IOException e) {

            return null;

        }

        return f.getAbsolutePath();

    }

    /**
     * 网络图片转为bitmap
     */
    public static Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


}
