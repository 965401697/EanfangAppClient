package com.eanfang.util;

import android.app.Activity;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.network.config.HttpConfig;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateCallback;
import com.vector.update_app.utils.AppUpdateUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 0 u r on 2018/4/4.
 */

public class UpdateAppManager {
    /**
     * 自定义接口协议
     *
     * @param activity activity
     */
    public static void update(Activity activity, boolean isAbout) {
        Map<String, String> params = new HashMap<String, String>();
        String updateUrl;
        if (HttpConfig.get().isClient()) {
            updateUrl = UserApi.GET_CILENT_UPDATE_APP + "?versionCode=" + AppUpdateUtils.getVersionCode(activity);
        } else {
            updateUrl = UserApi.GET_WORKER_UPDATE_APP + "?versionCode=" + AppUpdateUtils.getVersionCode(activity);
        }

        new com.vector.update_app.UpdateAppManager
                .Builder()
                //必须设置，当前Activity
                .setActivity(activity)
                //必须设置，实现httpManager接口的对象
                .setHttpManager(new OkGoUpdateHttpUtil())
                //必须设置，更新地址
                .setUpdateUrl(updateUrl)
                //以下设置，都是可选
                //设置请求方式，默认get
                .setPost(false)
                //不显示通知栏进度条
//                .dismissNotificationProgress()
                //是否忽略版本
//                .showIgnoreVersion()
                //添加自定义参数，默认version=1.0.0（app的versionName）；apkKey=唯一表示（在AndroidManifest.xml配置）
                .setParams(params)
                //设置点击升级后，消失对话框，默认点击升级后，对话框显示下载进度
                .hideDialogOnDownloading()
                //设置头部，不设置显示默认的图片，设置图片后自动识别主色调，然后为按钮，进度条设置颜色
//                .setTopPic(R.mipmap.ic_launcher)
                //为按钮，进度条设置颜色。
                .setThemeColor(0xffffac5d)
                //设置apk下砸路径，默认是在下载到sd卡下/Download/1.0.0/test.apk
//                .setTargetPath(path)
                //设置appKey，默认从AndroidManifest.xml获取，如果，使用自定义参数，则此项无效
//                .setAppKey("ab55ce55Ac4bcP408cPb8c1Aaeac179c5f6f")
                .build()
                //检测是否有新版本
                .checkNewApp(new UpdateCallback() {
                    /**
                     * 解析json,自定义协议
                     *
                     * @param json 服务器返回的json
                     * @return UpdateAppBean
                     */
                    @Override
                    protected UpdateAppBean parseJson(String json) {
                        UpdateAppBean updateAppBean = new UpdateAppBean();
                        try {
                            JSONObject jsonObject = JSON.parseObject(json);
                            JSONObject data = JSON.parseObject(jsonObject.getString("data"));
                            final String newVersion = data.getString("newVersion");
                            updateAppBean
                                    //（必须）是否更新Yes,No
                                    .setUpdate(data.getString("update"))
                                    //（必须）新版本号，
                                    .setNewVersion(newVersion)
                                    //（必须）下载地址
                                    .setApkFileUrl(data.getString("apkFileUrl"))
                                    //测试下载路径是重定向路径
//                                    .setApkFileUrl("http://openbox.mobilem.360.cn/index/d/sid/3282847")
//                                    .setUpdateDefDialogTitle(String.format("AppUpdate 是否升级到%s版本？", newVersion))
                                    //（必须）更新内容
                                    .setUpdateLog(data.getString("updateLog"))
                                    //大小，不设置不显示大小，可以不设置
                                    .setTargetSize(data.getString("targetSize"))
                                    //是否强制更新，可以不设置
                                    .setConstraint(Boolean.parseBoolean(data.getString("constraint")))
                                    //设置md5，可以不设置
                                    .setNewMd5(data.getString("newMd5"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return updateAppBean;
                    }

                    @Override
                    protected void hasNewApp(UpdateAppBean updateApp, com.vector.update_app.UpdateAppManager updateAppManager) {
                        updateAppManager.showDialogFragment();
                    }

                    /**
                     * 网络请求之前
                     */
                    @Override
                    public void onBefore() {
                    }

                    /**
                     * 网路请求之后
                     */
                    @Override
                    public void onAfter() {
                    }

                    /**
                     * 没有新版本
                     */
                    @Override
                    public void noNewApp(String error) {
                        if (isAbout) {
                            Toast.makeText(activity, "没有新版本", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
