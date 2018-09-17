package net.eanfang.worker.ui.activity.im;


import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.ValueCallback;


import io.rong.imkit.activity.FilePreviewActivity;

/**
 * Created by O u r on 2018/5/21.
 */

public class FilePreviewExActivity extends FilePreviewActivity {

    @Override
    public void openFile(String fileName, String fileSavePath) {
//        super.openFile(fileName, fileSavePath);
        Log.e("zzw", "fileName=" + fileName + "," + "fileSavePath" + fileSavePath);
        QbSdk.openFileReader(this, fileSavePath, null, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {

            }
        });

    }
}
