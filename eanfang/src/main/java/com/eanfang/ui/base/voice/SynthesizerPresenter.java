package com.eanfang.ui.base.voice;

import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.view.View;

import com.camera.util.LogUtil;
import com.eanfang.application.CustomeApplication;
import com.eanfang.application.EanfangApplication;
import com.eanfang.listener.MySynthesizerListener;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.StringUtils;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechSynthesizer;

/**
 * 描述：
 *
 * @author Guanluocang
 * @date on 2018/7/3$  10:43$
 */
public class SynthesizerPresenter extends MySynthesizerListener {

    private SpeechSynthesizer mTts;
    private MySynthesizerListener mTtsListener;

    private AudioManager mAudioManager;

    private volatile static SynthesizerPresenter mSynthesizerPresenter;

    public SynthesizerPresenter() {
        mTtsListener = new MySynthesizerListener();
    }

    public static SynthesizerPresenter getInstance() {
        if (mSynthesizerPresenter == null) {
            synchronized (SynthesizerPresenter.class) {
                if (mSynthesizerPresenter == null) {
                    mSynthesizerPresenter = new SynthesizerPresenter();
                }
            }
        }
        return mSynthesizerPresenter;
    }

    public void start() {
        initTts("");
    }

    public void finish() {
        if (mTts != null) {
            mTts.destroy();
        }
        mTtsListener = null;
    }

    public void initTts(String answer) {
        if (mTts == null) {
            mTts = SpeechSynthesizer.createSynthesizer(EanfangApplication.get().getApplicationContext(), new InitListener() {
                @Override
                public void onInit(int code) {
                    if (code != ErrorCode.SUCCESS) {
                        LogUtil.e("GG", "初始化失败，错误码：" + code);
                    }
                    buildTts(answer);
                }
            });
        } else {
            buildTts(answer);
        }

    }

    /**
     * 初始化参数
     */
    public void buildTts(String answer) {
        mTts.setParameter(SpeechConstant.PARAMS, null);
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);

        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");// 设置在线合成发音人
        mTts.setParameter(SpeechConstant.SPEED, "75");//设置合成语速
        mTts.setParameter(SpeechConstant.PITCH, "45");//设置合成音调
        mTts.setParameter(SpeechConstant.VOLUME, "100");//设置合成音量
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");//设置播放器音频流类型 1 系统
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");// 设置播放合成音频打断音乐播放，默认为true
        //把音乐音量强制设置为最大音量
        mAudioManager = (AudioManager) EanfangApplication.get().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        int mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC); // 获取当前音乐音量
        int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);// 获取最大声音
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);

        if (!StringUtils.isEmpty(answer)) {
            doAnswer(answer);
        }
    }

    public void doAnswer(String answer) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /**
                 *要执行的操作
                 */
                mTts.startSpeaking(answer, mTtsListener);
            }
        }, 300);//3秒后执行Runnable中的run方法

    }

}
