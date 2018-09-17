package com.eanfang.listener;

import android.os.Bundle;

import com.camera.util.LogUtil;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SynthesizerListener;

/**
 * Created by dell on 2017/9/20.
 * 语音合成
 */

public class MySynthesizerListener implements SynthesizerListener {

    public MySynthesizerListener() {
    }

    @Override
    public void onSpeakBegin() {
        LogUtil.e("GG", "onSpeakBeginonSpeakBeginonSpeakBegin");
    }

    @Override
    public void onBufferProgress(int i, int i1, int i2, String s) {
        LogUtil.e("GG", "onBufferProgressonBufferProgress");
    }

    @Override
    public void onSpeakPaused() {
        LogUtil.e("GG", "onSpeakPausedonSpeakPausedonSpeakPausedonSpeakPaused");
    }

    @Override
    public void onSpeakResumed() {
        LogUtil.e("GG", "onSpeakResumedonSpeakResumedonSpeakResumed");
    }

    @Override
    public void onSpeakProgress(int i, int i1, int i2) {
        LogUtil.e("GG", "onSpeakProgressonSpeakProgress");
    }

    @Override
    public void onCompleted(SpeechError speechError) {
        LogUtil.e("GG", "onCompletedonCompletedonCompleted");

    }

    @Override
    public void onEvent(int i, int i1, int i2, Bundle bundle) {
        LogUtil.e("GG", "onEventonEventonEvent");
    }


}
