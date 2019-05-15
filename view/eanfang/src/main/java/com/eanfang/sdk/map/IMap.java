package com.eanfang.sdk.map;

import android.app.Activity;
import android.os.Bundle;

import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.geocoder.RegeocodeQuery;

public interface IMap {
    // 此方法必须重写
    void onCreate(Activity activity, MapView mMapView, Bundle savedInstanceState);

    //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
    void onDestroy();

    //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
    void onResume();

    //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
    void onPause();

    //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
    void onSaveInstanceState(Bundle outState);

    void startOnce();
    void setMarket(LatLng latLng);
    MapManager.OnLocationed getOnLocationed();
    void getFromLocationAsyn(RegeocodeQuery regeocodeQuery);
    void moveCamera(CameraUpdate cameraUpdate);
    void isSearchMove(boolean bl);
    void setAllGesturesEnabled(boolean b);



}
