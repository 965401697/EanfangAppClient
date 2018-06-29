package com.eanfang.ui.base;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.eanfang.application.EanfangApplication;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechSynthesizer;


public class SpeakTts {

    private SpeechSynthesizer mTts;

    private volatile static SpeakTts mSpeakTts;

    public SpeechSynthesizer mTts() {
        return mTts;
    }

    public static SpeakTts getInstance() {
        if (mSpeakTts == null) {
            synchronized (SpeakTts.class) {
                if (mSpeakTts == null) {
                    mSpeakTts = new SpeakTts();
                }
            }
        }
        return mSpeakTts;
    }

    private SpeakTts() {
    }

    public void initTts(Context context, InitListener listener) {
        if (mTts == null) {
            mTts = SpeechSynthesizer.createSynthesizer(context, new InitListener() {
                @Override
                public void onInit(int code) {
                    if (code != ErrorCode.SUCCESS) {
                        Toast.makeText(context, "初始化失败,错误码：" + code, Toast.LENGTH_LONG).show();
                    }
                }
            });
            mTts.setParameter(SpeechConstant.PARAMS, null);
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");// 设置在线合成发音人
            mTts.setParameter(SpeechConstant.SPEED, "60");//设置合成语速
            mTts.setParameter(SpeechConstant.PITCH, "50");//设置合成音调
            mTts.setParameter(SpeechConstant.VOLUME, "50");//设置合成音量
            mTts.setParameter(SpeechConstant.STREAM_TYPE, "1");//设置播放器音频流类型 1 系统
            mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");// 设置播放合成音频打断音乐播放，默认为true
        }
    }


}
