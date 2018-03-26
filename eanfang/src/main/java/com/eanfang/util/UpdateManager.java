package com.eanfang.util;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.capton.easyupdate.EasyUpdate;
import com.capton.easyupdate.UpdateInfo;
import com.eanfang.R;
import com.eanfang.ui.base.BaseActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;


/**
 * Created by jornl on 2017/6/23.
 */
public class UpdateManager {
    private final static int DOWNLOAD_FAIL = -1;
    /* 下载中 */
    private static final int DOWNLOAD = 1;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 2;
    /* 保存解析的XML信息 */
    HashMap<String, String> mHashMap;
    /* 下载保存路径 */
    private String mSavePath;
    /* 记录进度条数量 */
    private int progress;
    /* 是否取消更新 */
    private boolean cancelUpdate = false;

    //版本信息xml地址
    private String xmlPath = "http://eanfang.net:8080/eanfang/" + "file/_type2.xml";

    // 自定义通知栏类
//    UploadNotification myNotification;
    private Context mContext;
    /* 更新进度条 */
    private ProgressBar mProgress;
    private Dialog mDownloadDialog;
    private int isMust;
    private int versionCode;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 正在下载
                case DOWNLOAD:
                    // 设置进度条位置
                    mProgress.setProgress(progress);
                    // myNotification.changeProgressStatus(progress);
                    break;
                case DOWNLOAD_FINISH:
                    // 安装文件
                    installApk();
                    //  PendingIntent updatePendingIntent = PendingIntent.getActivity(mContext, 0, installIntent(), 0);
                    // myNotification.changeContentIntent(updatePendingIntent);
                    // myNotification.notification.defaults = Notification.DEFAULT_SOUND;// 铃声提醒
                    // myNotification.removeNotification();
                    break;
                default:
                    //myNotification.changeProgressStatus(DOWNLOAD_FAIL);
                    break;
            }
        }
    };

    public UpdateManager(Context context, String type) {
        this.mContext = context;
        xmlPath = xmlPath.replace("_type", type);


    }

    /**
     * 检测软件更新
     */
    public void checkUpdate() {

//        UpdateInfo info = new UpdateInfo();
//        info.setUrl("http://oss.eanfang.net/eanfang/eanfang.client2.apk");
//        info.setMd5("7cac63e600ad623389346eadac4811ac");
//        info.setFileName("eanfang.client2.apk");
//        info.setVersionCode(1803262);
//        info.setVersionName("2.1.2");
//        info.setCharacters("全新版本");
//        info.setForceUpate(true);
//        info.setAutoInstall(false);
//
//        EasyUpdate.debug(false).check(mContext, info);

        if (isUpdate()) {
            // 显示提示对话框
            showNoticeDialog();
        } else {
            Toast.makeText(mContext, "当前版本为最新版本", Toast.LENGTH_LONG).show();
        }
    }

    public void update() {
        showNoticeDialog();
    }

    /**
     * 检查软件是否有更新版本
     *
     * @return
     */
    public boolean isUpdate() {
        // 获取当前软件版本
        versionCode = ApkUtils.getAppVersionCode(mContext);

//        Log.e("---------------------", xmlPath);
        //如果本地调试 则跳过检查更新
        if (xmlPath.contains("192.168.0")) {
            return false;
        }

        // 获取服务器中的版本信息文件
        URL url = null;
        InputStream inStream = null;
        try {
            url = new URL(xmlPath);
            HttpURLConnection conn = (HttpURLConnection) url
                    .openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                // 从服务器获得一个输入流
                inStream = conn.getInputStream();
            }
            // 解析XML文件。 由于XML文件比较小，因此使用DOM方式进行解析
            ParseXmlServiceToVersion service = new ParseXmlServiceToVersion();
            mHashMap = service.parseXml(inStream);

            if (null != mHashMap) {
                int check = Integer.valueOf(mHashMap.get("check"));
                int serviceCode = Integer.valueOf(mHashMap.get("version"));
                isMust = Integer.valueOf(mHashMap.get("ismust"));
                //是否需要检测更新
                if (check == 0) {
                    return false;
                }

                // 版本判断
                if (serviceCode > versionCode) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */

    /**
     * 显示软件更新对话框
     */
    private void showNoticeDialog() {
        // 构造对话框
        Builder builder = new Builder(mContext);
        builder.setTitle("软件更新");
        builder.setMessage("检测到新版本，请更新后使用\n" + mHashMap.get("info"));
        // 更新
        builder.setPositiveButton("立即更新", (dialog, which) -> {
            dialog.dismiss();
            // 显示下载对话框
            showDownloadDialog();
            // myNotification = new UploadNotification(mContext, null, 1);
            // myNotification.showCustomizeNotification(R.drawable.client_logo,"开始下载", R.layout.notification);
        });

        //屏蔽返回按键
        builder.setOnKeyListener((dialog, keyCode, event) -> {
            //默认返回 false
            return keyCode == KeyEvent.KEYCODE_SEARCH;
        });
        builder.setCancelable(false);
        // 稍后更新
        if (isMust == 0) {
            builder.setNegativeButton("稍后更新", (dialog, which) -> dialog.dismiss());
        }
        Dialog noticeDialog = builder.create();
        noticeDialog.show();
    }

    /**
     * 显示软件下载对话框
     */
    private void showDownloadDialog() {
        // 构造软件下载对话框
        Builder builder = new Builder(mContext);
        builder.setTitle("正在更新");
// 给下载对话框增加进度条
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.softupdate_progress, null);
        mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
        builder.setView(v);
        //屏蔽 返回按键
        builder.setOnKeyListener((dialog, keyCode, event) -> {
            //默认返回 false
            return keyCode == KeyEvent.KEYCODE_SEARCH;
        });
        builder.setCancelable(false);
        mDownloadDialog = builder.create();
        mDownloadDialog.show();
        // 下载文件
        downloadApk();
    }

    /**
     * 下载apk文件
     */
    private void downloadApk() {
        // 启动新线程下载软件
        new downloadApkThread().start();
    }

    /**
     * 安装APK文件
     */
    private void installApk() {
        File apkfile = new File(mSavePath, mHashMap.get("name"));
        if (!apkfile.exists()) {
            return;
        }
        ApkUtils.install(mContext, apkfile);
    }

    /**
     * 下载文件线程
     */
    private class downloadApkThread extends Thread {
        @Override
        public void run() {
            try {
                // 判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // 获得存储卡的路径
                    String sdpath = Environment.getExternalStorageDirectory() + "/";
                    mSavePath = sdpath + "download";
                    URL url = new URL(mHashMap.get("url"));
                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    // 获取文件大小
                    int length = conn.getContentLength();
                    // 创建输入流
                    InputStream is = conn.getInputStream();

                    File file = new File(mSavePath);
                    // 判断文件目录是否存在
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    File apkFile = new File(mSavePath, mHashMap.get("name"));
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // 缓存
                    byte buf[] = new byte[1024];
                    // 写入到文件中
                    do {
                        int numread = is.read(buf);
                        count += numread;
                        // 计算进度条位置
                        progress = (int) (((float) count / length) * 100);
                        // 更新进度
                        mHandler.sendEmptyMessage(DOWNLOAD);
                        if (numread <= 0) {
                            // 下载完成
                            mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                            break;
                        }
                        // 写入文件
                        fos.write(buf, 0, numread);
                    } while (!cancelUpdate);// 点击取消就停止下载.
                    fos.close();
                    is.close();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 取消下载对话框显示
            mDownloadDialog.dismiss();
        }

    }
}

