
package com.eanfang.ui.base.voice;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;

import com.eanfang.sdk.msc.MSCManager;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

/**
 * @author guanluocang
 * @data 2018/9/17
 * @description 语音听写
 */

public class RecognitionManager {

    private final String TAG = "RecognitionManager";
    private SpeechRecognizer mIat;

    private RecognitionManager() {
    }

    //通过静态内部类获取实例
    public static RecognitionManager getSingleton() {
        return SingletonHolder.singleton;
    }

    private InitListener mInitListener = code -> {
        Log.d(TAG, "SpeechRecognizer init() code = " + code);
        if (code != ErrorCode.SUCCESS) {
            Log.d(TAG, "初始化失败，错误码：" + code);
        }
    };


    public RecognitionManager init(Context mContext, String appID) {
        SpeechUtility.createUtility(mContext, SpeechConstant.APPID + "=" + appID);
        if (mIat == null) {
            //1.创建SpeechRecognizer对象，第二个参数：本地识别时传InitListener
            mIat = SpeechRecognizer.createRecognizer(mContext, null);
        }
        return this;
    }

    private onRecognitionListen listen;

    /**
     * 语音识别回调监听
     */
    public interface onRecognitionListen {
        void result(String msg);

        void error(String errorMsg);
    }


    //有动画效果
    private RecognizerDialog iatDialog;

    private EditText editText;
    private RecognizerDialogListener mRecognizersDialog = new RecognizerDialogListener() {
        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            analyzeJson(recognizerResult, b);
        }

        @Override
        public void onError(SpeechError speechError) {
            if (listen == null) {
//                ToastUtil.get().showToast(editText.getContext(), "识别错误");
            } else {
                listen.error(speechError.getMessage());
            }
            speechError.getPlainDescription(true);
        }
    };

    public void startRecognitionWithDialog(Context mContext, final onRecognitionListen listen) {
        this.listen = listen;
        // ②初始化有交互动画的语音识别器
        iatDialog = new RecognizerDialog(mContext, mInitListener);
        //③设置监听，实现听写结果的回调
        iatDialog.setListener(mRecognizersDialog);
        //开始听写，需将sdk中的assets文件下的文件夹拷入项目的assets文件夹下（没有的话自己新建）
        iatDialog.show();
    }

    public void startRecognitionWithDialog(Context mContext, EditText mEditText) {
        this.editText = mEditText;

        // ②初始化有交互动画的语音识别器
        iatDialog = new RecognizerDialog(mContext, mInitListener);
        //③设置监听，实现听写结果的回调
        iatDialog.setListener(mRecognizersDialog);
        //开始听写，需将sdk中的assets文件下的文件夹拷入项目的assets文件夹下（没有的话自己新建）
        if (iatDialog != null) {
            iatDialog.show();
        }
    }

    private void analyzeJson(RecognizerResult recognizerResult, boolean isLast) {
        String content = editText.getText().toString();
        String result = MSCManager.parseIatResult(recognizerResult.getResultString());
            if (listen == null) {
                content += result;
                editText.setText(content);
                //获取焦点
                editText.requestFocus();
                //将光标定位到文字最后，以便修改
                editText.setSelection(content.length());
            } else {
                listen.result(result);
            }
    }

    //静态内部类里实例化StaticClassSingleton对象
    private static class SingletonHolder {
        private static final RecognitionManager singleton = new RecognitionManager();
    }
}