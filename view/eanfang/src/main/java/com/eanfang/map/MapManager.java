package com.eanfang.map;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.eanfang.R;

public class MapManager implements IMap ,AMap.OnMarkerClickListener,
        LocationSource,
        AMapLocationListener,
        GeocodeSearch.OnGeocodeSearchListener,
        AMap.OnCameraChangeListener{
    private MapView mMapView;
    private Activity activity;
    private AMap mAMap;
    private GeocodeSearch geocoderSearch;
    public AMapLocationClient mLocationClient = null;
    public AMapLocationClientOption mLocationOption = null;
    private LatLng latLng;
    /**
     * 定位位置显示
     */
    private AMapLocation location;
    public static onChanged onChanged;
    public static OnLocationed onLocationed;
    public static OnSearched onSearched;
    @Override
    public void onCreate(Activity activity, MapView mMapView, Bundle savedInstanceState) {
        this.activity = activity;
        this.mMapView = mMapView;
        mMapView.onCreate(savedInstanceState);
        init();
    }

    @Override
    public void onDestroy() {
        stop();
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mMapView.onSaveInstanceState(outState);
    }

    private void init() {
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

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        latLng = cameraPosition.target;
        setMarket(latLng);
    }
    private Marker mGPSMarker;
    @Override
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


    @Override
    public OnLocationed getOnLocationed() {
        return onLocationed;
    }

    @Override
    public void getFromLocationAsyn(RegeocodeQuery regeocodeQuery) {
        geocoderSearch.getFromLocationAsyn(regeocodeQuery);
    }

    @Override
    public void moveCamera(CameraUpdate cameraUpdate) {
        mAMap.moveCamera(cameraUpdate);
    }

    @Override
    public void isSearchMove(boolean bl) {
        isSearchMove=bl;
    }

    @Override
    public void setAllGesturesEnabled(boolean b) {
        mAMap.getUiSettings().setAllGesturesEnabled(b);
    }

    public boolean isSearchMove;
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

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

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

    }

    public void stop() {
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
        mLocationOption = null;
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
    @Override
    public void startOnce() {
        if (mLocationClient == null) {
        }
        mLocationOption.setOnceLocation(true);
        mLocationClient.startLocation();

    }
}
