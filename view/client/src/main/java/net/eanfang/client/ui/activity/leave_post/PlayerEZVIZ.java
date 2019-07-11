package net.eanfang.client.ui.activity.leave_post;

import android.os.Handler;
import android.view.SurfaceView;

import com.eanfang.base.network.exception.base.BaseException;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.EZPlayer;
import com.videogo.openapi.bean.EZDeviceRecordFile;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2017/8/3.
 * 封装的萤石播放库
 */
public class PlayerEZVIZ {
    private SurfaceView surfaceView;
    private Handler handler;
    public EZPlayer ezPlayer_Live, ezPlayer_Playback;

    public PlayerEZVIZ(SurfaceView surfaceView, Handler handler) {
        this.surfaceView = surfaceView;
        this.handler = handler;
    }

    /**
     * 实时预览
     *
     * @param deviceSerial 设备序列号
     * @param channelNo    通道号
     */
    public void live(String deviceSerial, int channelNo) {
        if (ezPlayer_Live == null) {
            //根据设备序列号和通道号创建播放器（用于实时预览，如果需远程回放，最好再新建一个播放器）
            ezPlayer_Live = EZOpenSDK.getInstance().createPlayer(deviceSerial, channelNo);
        }
        //设置消息回调
        ezPlayer_Live.setHandler(handler);
        //设置显示视图
        ezPlayer_Live.setSurfaceHold(surfaceView.getHolder());
        //开始实时预览
        ezPlayer_Live.startRealPlay();
    }

    /**
     * 远程回放
     *
     * @param deviceSerial
     * @param channelNo
     * @param beginYear
     * @param beginMonth
     * @param beginDay
     * @param beginHour
     * @param beginMinute
     * @param beginSecond
     * @param endYear
     * @param endMonth
     * @param endDay
     * @param endHour
     * @param endMinute
     * @param endSecond
     */
    public void playback(String deviceSerial, int channelNo,
                         int beginYear, int beginMonth, int beginDay, int beginHour, int beginMinute, int beginSecond,
                         int endYear, int endMonth, int endDay, int endHour, int endMinute, int endSecond) {
        if (ezPlayer_Playback == null) {
            ezPlayer_Playback = EZOpenSDK.getInstance().createPlayer(deviceSerial, channelNo);
        }
        Calendar beginCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        beginCalendar.set(beginYear, beginMonth - 1, beginDay, beginHour, beginMinute, beginSecond);
        endCalendar.set(endYear, endMonth - 1, endDay, endHour, endMinute, endSecond);
        ezPlayer_Playback.setHandler(handler);
        ezPlayer_Playback.setSurfaceHold(surfaceView.getHolder());
        ezPlayer_Playback.startPlayback(beginCalendar, endCalendar);  //根据开始、结束时间开始远程回放
    }

    /**
     * 获取录像段文件
     *
     * @param deviceSerial
     * @param channelNo
     * @param beginYear
     * @param beginMonth
     * @param beginDay
     * @param beginHour
     * @param beginMinute
     * @param beginSecond
     * @param endYear
     * @param endMonth
     * @param endDay
     * @param endHour
     * @param endMinute
     * @param endSecond
     * @return
     */
    public List<EZDeviceRecordFile> getRecordFile(String deviceSerial, int channelNo,
                                                  int beginYear, int beginMonth, int beginDay, int beginHour, int beginMinute, int beginSecond,
                                                  int endYear, int endMonth, int endDay, int endHour, int endMinute, int endSecond) {
        List<EZDeviceRecordFile> recordList = null;
        Calendar beginCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        beginCalendar.set(beginYear, beginMonth - 1, beginDay, beginHour, beginMinute, beginSecond);
        endCalendar.set(endYear, endMonth - 1, endDay, endHour, endMinute, endSecond);
        try {
            recordList = EZOpenSDK.getInstance().searchRecordFileFromDevice(deviceSerial, channelNo, beginCalendar, endCalendar);
        } catch (BaseException e) {
            e.printStackTrace();
        } catch (com.videogo.exception.BaseException e) {
            e.printStackTrace();
        }
        return recordList;
    }

    /**
     * 停止实时预览
     */
    public void stopLive() {
        if (ezPlayer_Live != null) {
            ezPlayer_Live.stopRealPlay();
        }
    }

    /**
     * 停止远程回放
     */
    public void stopPlayback() {
        if (ezPlayer_Playback != null) {
            ezPlayer_Playback.stopPlayback();
        }
    }
}