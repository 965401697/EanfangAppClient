package net.eanfang.worker.ui.activity.im;


import android.util.Log;

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
//        super.openFile(fileName, fileSavePath);
        Log.e("zzw", "fileName=" + fileName + "," + "fileSavePath" + fileSavePath);
        HashMap<String, String> params = new HashMap<>();
//        //“0”表示文件查看器使用默认的UI 样式。“1”表示文件查看器使用微信的UI 样式。不设置此key或设置错误值，则为默认UI 样式。
        params.put("style", "1");
//        //“true”表示是进入文件查看器，如果不设置或设置为“false”，则进入miniqb 浏览器模式。不是必须设置项
//        params.put("local", "true");
//        //定制文件查看器的顶部栏背景色。格式为“#xxxxxx”，例“#2CFC47”;不设置此key 或设置错误值，则为默认UI 样式。
//        params.put("topBarBgColor", "#ff8b3d");


        QbSdk.openFileReader(this, fileSavePath, null, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                Log.e("zzw", "s=" + s);
            }


        });

    }
}
