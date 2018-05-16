/*
 * Copyright (c) 2016. Huangrui All rights reserved.
 */

package com.eanfang.util;

import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.eanfang.R;
import com.eanfang.ui.base.BaseActivity;

/**
 * @author Mr.hou
 * Created at 2017/3/2
 * @desc
 */
public class LocationUtil implements AMap.OnMarkerClickListener,
        LocationSource,
        AMapLocationListener,
        GeocodeSearch.OnGeocodeSearchListener,
        AMap.OnCameraChangeListener {
    private static LocationUtil instance = new LocationUtil();
    public AMap mAMap;
    public AMapLocationClient mLocationClient = null;
    public AMapLocationClientOption mLocationOption = null;
    public GeocodeSearch geocoderSearch;
    public boolean isSearchMove;
    public onChanged onChanged;
    private BaseActivity activity;
    private MapView mMapView;
    private Marker mGPSMarker;
    /**
     * 定位位置显示
     */
    private AMapLocation location;

    private OnLocationed onLocationed;

    public OnSearched onSearched;
    private LatLng latLng;
    private String curCityCode;

    private LocationUtil() {
    }

    public static LocationUtil get(BaseActivity activity, MapView mMapView) {
        if (instance == null) {
            instance = new LocationUtil();
        }
        instance.activity = activity;
        instance.mMapView = mMapView;
        instance.init();
        return instance;
    }

    //普通定位方法
    public static void location(BaseActivity activity, OnLocationed onLocationed) {
        if (instance == null) {
            instance = new LocationUtil();
        }
        instance.activity = activity;
        instance.onLocationed = onLocationed;
        instance.initLocation();
    }

    private void initLocation() {
        mLocationClient = new AMapLocationClient(activity);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mLocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mLocationClient.startLocation();
    }

    private void init() {
//        if (mAMap == null) {
//            mAMap = mMapView.getMap();
//        }
        mAMap = mMapView.getMap();

        geocoderSearch = new GeocodeSearch(activity);
        ////逆编码监听事件
        geocoderSearch.setOnGeocodeSearchListener(this);

        // 设置定位监听
//        mAMap.setOnMapLoadedListener(this);
        mAMap.setOnMarkerClickListener(this);
        mAMap.setLocationSource(this);

        // 绑定marker拖拽事件
//        mAMap.setOnMarkerDragListener(this);
        //禁止倾斜手势
        mAMap.getUiSettings().setTiltGesturesEnabled(false);
        //禁止旋转收拾
        mAMap.getUiSettings().setRotateGesturesEnabled(false);
        mAMap.setOnCameraChangeListener(this);
//        mAMap.setOnMarkerDragListener(this);
//        LocationUtil.get().addListener(this, TAG);
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 设置小蓝点的图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.position_picker));
        // 设置圆形的边框颜色
        myLocationStyle.strokeColor(Color.BLACK);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));
        //设置小蓝点的锚点
        // myLocationStyle.anchor(int,int)
//         设置圆形的边框粗细
        myLocationStyle.strokeWidth(10f);
        myLocationStyle.anchor(0.5f, 0.7f);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);
        myLocationStyle.showMyLocation(true);
//
        mAMap.setMyLocationStyle(myLocationStyle);


        //缩放比例
//         mAMap.moveCamera(CameraUpdateFactory.zoomTo(MapUtils.ZOOM));

//        //添加一个圆
//        CircleOptions circleOptions = new CircleOptions();
//        circleOptions.radius(20.0f);
//        mAMap.addCircle(circleOptions);

        //设置amap的属性
        UiSettings settings = mAMap.getUiSettings();
        // 设置默认定位按钮是否显示
        settings.setMyLocationButtonEnabled(true);
        //指南针 是否显示
//        settings.setCompassEnabled(true);
        //控制比例尺控件是否显示
        settings.setScaleControlsEnabled(true);

        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        mAMap.setMyLocationEnabled(true);

        mLocationClient = new AMapLocationClient(activity);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setOnceLocation(true);
        mLocationClient.setLocationListener(this);
//        mLocationOption.setInterval(30 * 1000);

        //设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mLocationClient.startLocation();
    }

    public void start() {
        if (mLocationClient == null) {
        }
        mLocationOption.setOnceLocation(false);
        mLocationClient.startLocation();
    }

    public void startOnce() {
        if (mLocationClient == null) {
        }
        mLocationOption.setOnceLocation(true);
        mLocationClient.startLocation();

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        location = aMapLocation;
        if (location != null) {
            if (location != null && location.getErrorCode() == 0) {
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
                mLocationClient.stopLocation();
                if (mAMap != null) {
                    mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                }
                if (onChanged != null) {
                    onChanged.change("", latLng);
                }
                if (onLocationed != null) {
                    onLocationed.location(location);
                    onLocationed = null;
                }

            }
        }

    }

    public void stop() {
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
        mLocationOption = null;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.isInfoWindowShown()) {
            marker.hideInfoWindow();

        } else {
            marker.showInfoWindow();
        }
        return false;
    }

//    @Override
//    public void onMarkerDragStart(Marker marker) {
//        Log.e("marker", "marker正在拖拽");
//        setMarket(marker.getPosition());
//    }
//
//    @Override
//    public void onMarkerDrag(Marker marker) {
//        setMarket(marker.getPosition());
//    }
//
//    @Override
//    public void onMarkerDragEnd(Marker marker) {
//        Log.e("marker", "marker拖拽完成");
//        setMarket(latLng);
//
//        // 销毁定位
//        if (mLocationClient != null) {
//            mLocationClient.stopLocation();
//            mLocationClient.onDestroy();
//        }
//        doSearchQuery("", marker.getPosition());
//    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
//        mListener = onLocationChangedListener;
        //初始化定位
        mLocationClient = new AMapLocationClient(activity);
        //设置定位回调监听
        mLocationClient.setLocationListener(this);

        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        //   mLocationOption.setInterval(2000 * 10);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    public void deactivate() {
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        if (onSearched != null) {
            onSearched.searched(regeocodeResult, i);
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
        if (StringUtils.isEmpty(curCityCode)) {
            mGPSMarker.setSnippet(geocodeResult.getGeocodeQuery().getLocationName());
        }
        if (geocodeResult.getGeocodeAddressList() != null && geocodeResult.getGeocodeAddressList().size() > 0) {
            curCityCode = geocodeResult.getGeocodeAddressList().get(0).getFormatAddress();
        }
    }

    public void setMarket(LatLng latLng) {
        if (mGPSMarker != null) {
            mGPSMarker.remove();
        }
        MarkerOptions markOptions = new MarkerOptions();
        //设置Marker可拖动
        markOptions.draggable(true);
        markOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.position_picker)));
        //设置一个角标
        mGPSMarker = mAMap.addMarker(markOptions);
        //设置marker在屏幕的像素坐标
        mGPSMarker.setPosition(latLng);
        mMapView.invalidate();
        mAMap.reloadMap();
    }

    /**
     * 向当前地图中 添加 标记点
     */
    public void addMarket(LatLng latLng, String icon, String title, String content, Object tag) {
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.title(title).snippet(content);

        markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.worker_icon));
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        //设置marker平贴地图效果
        markerOption.setFlat(true);
        mAMap.addMarker(markerOption).setObject(tag);
    }


    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        latLng = cameraPosition.target;
        setMarket(latLng);
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        latLng = cameraPosition.target;
        if (isSearchMove) {
            isSearchMove = false;
            return;
        }
        if (onChanged != null) {
            onChanged.change("", latLng);
        }
    }

    /**
     * 当拖动地图位置发生改变是，调用此方法
     */
    public interface onChanged {
        void change(String keywords, LatLng latLng);
    }

    public interface OnLocationed {
        void location(AMapLocation aMapLocation);
    }

    public interface OnSearched {
        void searched(RegeocodeResult regeocodeResult, int i);
    }
}
