package com.eanfang.sdk.msc;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.eanfang.util.JsonParser;
import com.eanfang.util.ToastUtil;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

public class MSCManager implements IMSC {
    private Context context;
    private EditText editText;
    private RecognizerDialog iatDialog;
    private final String TAG = "MSCManager";
    private static MSCManager mscManager;
    private SpeechRecognizer mIat;

    public static MSCManager getInstance(){
        if(mscManager==null){
            mscManager=new MSCManager();
        }
        return mscManager;
    }
    @Override
    public void init(Context context, String appid) {
        this.context = context;
        // 将“12345678”替换成您申请的APPID，申请地址：http://www.xfyun.cn
        // 请勿在“=”与appid之间添加任何空字符或者转义符
        SpeechUtility.createUtility(context, SpeechConstant.APPID + "=" + appid);
        createRecognizer();
    }

    @Override
    public void startRecognitionWithDialog(Context mContext,EditText mEditText) {
        this.editText = mEditText;
        // ②初始化有交互动画的语音识别器
        iatDialog = new RecognizerDialog(mContext, mInitListener);
        //③设置监听，实现听写结果的回调
        iatDialog.setListener(mRecognizersDialog);
        //开始听写，需将sdk中的assets文件下的文件夹拷入项目的assets文件夹下（没有的话自己新建）
        iatDialog.show();
    }

    @Override
    public void startRecognitionWithDialog(Context mContext,onRecognitionListen listen) {
        this.listen = listen;
        // ②初始化有交互动画的语音识别器
        iatDialog = new RecognizerDialog(mContext, mInitListener);
        //③设置监听，实现听写结果的回调
        iatDialog.setListener(mRecognizersDialog);
        //开始听写，需将sdk中的assets文件下的文件夹拷入项目的assets文件夹下（没有的话自己新建）
        iatDialog.show();
    }

    @Override
    public void destroy() {
        getRecognizer().destroy();
    }

    private SpeechRecognizer getRecognizer() {
        return SpeechRecognizer.getRecognizer();
    }

    private void createRecognizer() {
        if (mIat == null) {
            //1.创建SpeechRecognizer对象，第二个参数：本地识别时传InitListener
            mIat = SpeechRecognizer.createRecognizer(context, null);
        }
    }

    private InitListener mInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Log.d(TAG, "初始化失败，错误码：" + code);
            }
        }
    };

    private RecognizerDialogListener mRecognizersDialog = new RecognizerDialogListener() {
        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            analyzeJson(recognizerResult, b);
                  /*  etText.setText(result);
                    //获取焦点
                    etText.requestFocus();
                    //将光标定位到文字最后，以便修改
                    etText.setSelection(result.length());*/
        }

        @Override
        public void onError(SpeechError speechError) {
            if (listen == null) {
                ToastUtil.get().showToast(editText.getContext(), "识别错误");
            } else {
                listen.error(speechError.getMessage());
            }
            speechError.getPlainDescription(true);
        }
    };
    private String resultJson = "[";//放置在外边做类的变量则报错，会造成json格式不对（？）
    private String result = "";

    private void analyzeJson(RecognizerResult recognizerResult, boolean isLast) {
        String content = editText.getText().toString();
        if (!isLast) {
            resultJson += recognizerResult.getResultString() + ",";
        } else {
            resultJson += recognizerResult.getResultString() + "]";
        }
        if (!isLast) {
            result = JsonParser.parseIatResult(recognizerResult.getResultString());
            if (listen == null) {
                content += result;
                editText.setText(content + "");
                //获取焦点
                editText.requestFocus();
                //将光标定位到文字最后，以便修改
                editText.setSelection(content.length());
            } else {
                listen.result(result);
            }
        }
    }

    /**
     * 语音识别回调监听
     */
    private onRecognitionListen listen;

    public interface onRecognitionListen {
        void result(String msg);

        void error(String errorMsg);
    }


    //听写监听器
    private RecognizerListener mRecoListener = new RecognizerListener() {
        //听写结果回调接口(返回Json格式结果，用户可参见附录13.1)；
        // 一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d(TAG, "result:" + results.getResultString());
            analyzeJson(results, isLast);
        }

        //会话发生错误回调接口
        public void onError(SpeechError error) {
            //打印错误码描述
            Log.d(TAG, "error:" + error.getPlainDescription(true));
//            listen.result(error.getMessage());
        }

        //开始录音
        public void onBeginOfSpeech() {
            Log.d(TAG, "开始录音");
//            listen.onBeginOfSpeech();
        }

        //    volume音量值0~30，data音频数据
        public void onVolumeChanged(int volume, byte[] data) {
            Log.d(TAG, "音量为：" + volume);
//            listen.onVolumeChanged(volume, data);
        }

        //结束录音
        public void onEndOfSpeech() {
            Log.d(TAG, "结束录音");
//            listen.onEndOfSpeech();
        }

        //扩展用接口
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        }
    };

    /**
     * 关闭语音识别对话框;
     */
    public void closeRecognitionDialog() {
        if (iatDialog != null) {
            iatDialog.dismiss();
            Log.d(TAG, "closeRecognitionDialog");
        } else {
            Log.d(TAG, "closeRecognitionDialog,iatDialog=null");
        }
    }
}
