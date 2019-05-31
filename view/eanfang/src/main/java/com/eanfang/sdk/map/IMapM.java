package com.eanfang.sdk.map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.poisearch.PoiSearch;

public interface IMapM {
    IMapM setStroke(boolean isShowStroke);

    IMapM init(MapView mapView, Bundle savedInstanceState);

    void location(AMap.OnMyLocationChangeListener listener, AMap.OnCameraChangeListener cameraChangeListener);

    void zoomTO(float v);

    void poiSearch(Context context, String keyWord, PoiSearch.OnPoiSearchListener listener);

    void poiSearch(Context context, LatLng latLng, PoiSearch.OnPoiSearchListener listener);

    void setMarker(Context context, double latitude, double longitude, String title);

    void setCenter(double latitude, double longitude);

    void onCreate( Bundle savedInstanceState);

    void onDestroy();

    void onResume();

    void onPause();

    void onSaveInstanceState(Bundle outState);

}
