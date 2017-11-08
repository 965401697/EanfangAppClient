/*
 * Copyright (c) 2016. Huangrui All rights reserved.
 */

package net.eanfang.client.util;

import android.content.Context;
import android.support.v4.util.ArrayMap;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.LatLng;

import java.util.Iterator;

/**
 * @author Mr.hou
 *         Created at 2017/3/2
 * @desc
 */
public class LocationUtil implements AMapLocationListener {
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    static LocationUtil instance;
    ArrayMap<Object, OnLocationListener> mOnLocationListeners = new ArrayMap<>();
    Context mContext;

    private LocationUtil() {
    }

    public static LocationUtil get() {
        if (instance == null) instance = new LocationUtil();
        return instance;
    }

    public void init(Context context) {
        mContext = context;
        mlocationClient = new AMapLocationClient(context);
    //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
    //设置定位监听
    //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
    //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setOnceLocation(true);
        mlocationClient.setLocationListener(this);
        mLocationOption.setInterval(30 * 1000);

    //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
    // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
    // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
    // 在定位结束后，在合适的生命周期调用onDestroy()方法
    // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
    //启动定位
    }

    public void start() {
        if (mlocationClient == null) init(mContext);
        mLocationOption.setOnceLocation(false);
        mlocationClient.startLocation();
    }

    public void startOnce() {
        if (mlocationClient == null) init(mContext);
        mLocationOption.setOnceLocation(true);
        mlocationClient.startLocation();

    }

    public void addListener(OnLocationListener l, Object tag) {
        mOnLocationListeners.put(tag, l);
    }

    public void stop() {
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
        mLocationOption = null;
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {

        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息

                Iterator<Object> iterator = mOnLocationListeners.keySet().iterator();
                while (iterator.hasNext()) {
                    OnLocationListener locationListener = mOnLocationListeners.get(iterator.next());
                    if (locationListener != null)
                        locationListener.onLocation(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()));
                }
            }
        }
    }

    public void remove(Object object) {
        mOnLocationListeners.remove(object);

    }

    public interface OnLocationListener {
        void onLocation(LatLng latLng);
    }
}
