package com.eanfang.util;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * User: Mr.hou
 * Date&Time: 2017/3/25
 * Describe: 获取经纬度工具类
 * <p>
 * 需要权限
 * <!--用于进行网络定位-->
 * <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"></uses-permission>
 * <!--用于访问GPS定位-->
 * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"></uses-permission>
 * <!--获取运营商信息，用于支持提供运营商信息相关的接口-->
 * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
 * <!--用于访问wifi网络信息，wifi信息会用于进行网络定位-->
 * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
 * <!--这个权限用于获取wifi的获取权限，wifi信息会用来进行网络定位-->
 * <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"></uses-permission>
 * <!--用于访问网络，网络定位需要上网-->
 * <uses-permission android:name="android.permission.INTERNET"></uses-permission>
 * <!--用于读取手机当前的状态-->
 * <uses-permission android:name="android.permission.READ_PHONE_STATE"></uses-permission>
 * <!--写入扩展存储，向扩展卡写入数据，用于写入缓存定位数据-->
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"></uses-permission>
 * 需要在application 配置的mate-data 和sevice
 * <service android:name="com.amap.api.location.APSService" >
 * </service>
 * <meta-data
 * android:name="com.amap.api.v2.apikey"
 * android:value="60f458d237f0494627e196293d49db7e"/>
 * 另外，还需要一个key xxx.jks
 */
public class AMapLocUtils implements AMapLocationListener {
    private AMapLocationClient locationClient = null;  // 定位
    private AMapLocationClientOption locationOption = null;  // 定位设置
    private LonLatListener mLonLatListener;

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        mLonLatListener.getLonLat(aMapLocation);
        locationClient.stopLocation();
        locationClient.onDestroy();
        locationClient = null;
        locationOption = null;
    }

    public void getLonLat(Context context, LonLatListener lonLatListener) {
        locationClient = new AMapLocationClient(context);
        locationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 设置定位监听
        locationClient.setLocationListener(this);
        // 单次定位
        locationOption.setOnceLocation(false);
        //逆地理编码
        locationOption.setNeedAddress(true);
        //接口
        mLonLatListener = lonLatListener;
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    public interface LonLatListener {
        void getLonLat(AMapLocation aMapLocation);
    }
}
