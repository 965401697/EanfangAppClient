package net.eanfang.client.ui.activity.im;


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
                params.put("local", "true");
        QbSdk.openFileReader(this, fileSavePath, params, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                Log.e("zzw", "fileName=============" + fileName + "," + "fileSavePath============" + fileSavePath);
            }
        });

    }
}
