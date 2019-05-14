package com.eanfang.sdk.msc;

import android.content.Context;
import android.widget.EditText;

public interface IMSC {
    void init(Context context, String appid);
    void startRecognitionWithDialog( Context mContext,EditText mEditText);
    void startRecognitionWithDialog( Context mContext,MSCManager.onRecognitionListen listen);
    void destroy();
}
