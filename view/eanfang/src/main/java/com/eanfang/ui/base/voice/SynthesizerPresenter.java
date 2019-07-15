package com.eanfang.ui.base.voice;

import android.content.Context;
import android.media.AudioManager;

import com.baidu.ocr.sdk.utils.LogUtil;
import com.eanfang.base.BaseApplication;
import com.eanfang.listener.MySynthesizerListener;
import com.eanfang.util.StringUtils;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechSynthesizer;

/**
 * 描述：语音合成
 *
 * @author Guanluocang.
 * @date on 2018/7/3$  10:43$
 */
public class SynthesizerPresenter extends MySynthesizerListener {

    private SpeechSynthesizer mTts;
    private MySynthesizerListener mTtsListener;

    private AudioManager mAudioManager;

    private volatile static SynthesizerPresenter mSynthesizerPresenter;
    private int maxVolume;// 最大音量

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

    public void start(String answer) {
        initTts();
        buildTts(answer);
    }

    public void finish() {
        if (mTts != null) {
            mTts.destroy();
        }
        mTtsListener = null;
    }

    public void initTts() {
        //把音乐音量强制设置为最大音量
        mAudioManager = (AudioManager) BaseApplication.get().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);// 获取最大声音
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
        if (mTts == null) {
            mTts = SpeechSynthesizer.createSynthesizer(BaseApplication.get().getApplicationContext(), new InitListener() {
                @Override
                public void onInit(int code) {
                    if (code != ErrorCode.SUCCESS) {
                        LogUtil.e("GG", "初始化失败，错误码：" + code);
                    }
                }
            });
            mTts.setParameter(SpeechConstant.PARAMS, null);
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);

            mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");// 设置在线合成发音人
            mTts.setParameter(SpeechConstant.SPEED, "75");//设置合成语速
            mTts.setParameter(SpeechConstant.PITCH, "45");//设置合成音调
            mTts.setParameter(SpeechConstant.VOLUME, "100");//设置合成音量
            mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");//设置播放器音频流类型 1 系统
            mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");// 设置播放合成音频打断音乐播放，默认为true
        }
    }

    /**
     * 初始化参数
     */

    public void buildTts(String answer) {
        if (!StringUtils.isEmpty(answer)) {
            doAnswer(answer);
        }
    }

    public void doAnswer(String answer) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(1500);//休眠3秒
                    mTts.startSpeaking(answer, mTtsListener);
                } catch (InterruptedException e) {
                }
            }
        }.start();
    }

}
