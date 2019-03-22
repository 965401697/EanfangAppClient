package net.eanfang.worker.ui.activity.im;


import android.os.Bundle;
import android.util.Log;

import com.eanfang.takevideo.PlayVideoActivity;
import com.eanfang.util.FileDisplayActivity;
import com.eanfang.util.JumpItent;
import com.eanfang.util.ToastUtil;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.ValueCallback;

import java.util.HashMap;

import io.rong.imkit.activity.FilePreviewActivity;

/**
 * Created by O u r on 2018/5/21.
 */

public class FilePreviewExActivity extends FilePreviewActivity {

    @Override
    public void openFile(String fileName, String fileSavePath) {
        Log.e("zzw", "fileName=" + fileName + "," + "fileSavePath" + fileSavePath);
        HashMap<String, String> params = new HashMap<>();
        /**
         *  “0”表示文件查看器使用默认的UI 样式。“1”表示文件查看器使用微信的UI 样式。不设置此key或设置错误值，则为默认UI 样式。
         * */
        params.put("style", "1");

        /**
         *      “true”表示是进入文件查看器，如果不设置或设置为“false”，则进入miniqb 浏览器模式。不是必须设置项params.put("local", "true");
         *      定制文件查看器的顶部栏背景色。格式为“#xxxxxx”，例“#2CFC47”;不设置此key 或设置错误值，则为默认UI 样式。
         *      params.put("topBarBgColor", "#ff8b3d");
         * */

        if (fileName.substring(fileName.lastIndexOf(".") + 1).equals("mp4")) {
            Bundle bundle_takevideo = new Bundle();
            bundle_takevideo.putString("videoPath", fileSavePath);
            JumpItent.jump(this, PlayVideoActivity.class, bundle_takevideo);
        } else {
            if (!fileSavePath.contains("http")) {
                FileDisplayActivity.actionStart(FilePreviewExActivity.this, fileSavePath);
            } else {
                QbSdk.canOpenFile(this, fileSavePath, new ValueCallback<Boolean>() {
                    @Override
                    public void onReceiveValue(Boolean aBoolean) {
                        if (!aBoolean) {
                            ToastUtil.get().showToast(FilePreviewExActivity.this, "此文件无法查看");
                            finish();
                            return;
                        }
                    }
                });
            }
        }
    }
}
