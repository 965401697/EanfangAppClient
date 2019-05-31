package com.eanfang.sdk.map;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.poisearch.PoiSearch;
import com.eanfang.R;
import com.eanfang.config.Constant;

public class MapM implements IMapM{

    private MapView mapView;
    private static MapM mapM;
    private AMap aMap;
    //定义一个UiSettings对象
    private UiSettings mUiSettings;
    //定位样式
    private MyLocationStyle myLocationStyle;
    //是否显示精度范围
    private boolean isShowStroke = false;

    public MapM setStroke(boolean isShowStroke) {
        this.isShowStroke = isShowStroke;
        return this;
    }

    public static MapM getInstance() {
        if (mapM == null) {
            mapM = new MapM();
        }
        return mapM;
    }

    public MapM init(MapView mapView, Bundle savedInstanceState) {
        this.mapView = mapView;
        aMap = mapView.getMap();
        onCreate(savedInstanceState);
        return this;
    }

    /**
     * map定位初始化设置
     * @param listener 定位监听
     * @param cameraChangeListener 滑动地图监听
     */
    public void location(AMap.OnMyLocationChangeListener listener, AMap.OnCameraChangeListener cameraChangeListener) {
        aMap.setOnMyLocationChangeListener(listener);
        aMap.setOnCameraChangeListener(cameraChangeListener);
        //设置定位蓝点的Style
        locationStyle();
        aMap.setMyLocationStyle(myLocationStyle);
        uiSetting();

        int zoom=isShowStroke?18:14;
        zoomTO(zoom);
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
    }

    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);

    /**
     * 定位样式
     */
    public void locationStyle() {
        //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        // 连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle = new MyLocationStyle();
        //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.interval(2000);
        // 设置小蓝点的图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.position_picker));
        // 精度圈边框颜色
        myLocationStyle.strokeColor(STROKE_COLOR);
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(5f);
        // 精度圈填充颜色
        myLocationStyle.radiusFillColor(FILL_COLOR);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        myLocationStyle.showMyLocation(isShowStroke);

        /*
        myLocationStyle.anchor(0.5f, 0.7f);
        */
    }

    /**
     * 操纵控件设置
     */
    public void uiSetting() {
        //实例化UiSettings类对象
        mUiSettings = aMap.getUiSettings();
        //设置默认定位按钮是否显示，非必需设置。
        mUiSettings.setMyLocationButtonEnabled(true);
        //控制比例尺控件是否显示
        mUiSettings.setScaleControlsEnabled(true);
        mUiSettings.setGestureScaleByMapCenter(true);
        mUiSettings.setRotateGesturesEnabled(false);
        mUiSettings.setTiltGesturesEnabled(false);
    }

    /**
     * 缩放地图到指定的缩放级别
     *
     * @param v 地图的缩放级别一共分为 17 级，从 3 到 19。数字越大，展示的图面信息越精细。
     */
    public void zoomTO(float v) {
        //设置希望展示的地图缩放级别
        CameraUpdate mCameraUpdate = CameraUpdateFactory.zoomTo(v);
        aMap.moveCamera(mCameraUpdate);
    }

    private String queryType = Constant.mapScope;

    /**
     * 关键字检索POI
     * //keyWord表示搜索字符串，
     * //第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
     * //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
     */
    public void poiSearch(Context context, String keyWord, PoiSearch.OnPoiSearchListener listener) {
        poisearch(context, keyWord, null, listener);
    }

    /**
     * 周边检索POI
     *
     * @param context
     * @param latLng
     * @param listener
     */
    public void poiSearch(Context context, LatLng latLng, PoiSearch.OnPoiSearchListener listener) {
        poisearch(context, "", latLng, listener);
    }

    private void poisearch(Context context, String keyWord, LatLng latLng, PoiSearch.OnPoiSearchListener listener) {
        PoiSearch.Query query = new PoiSearch.Query(keyWord, queryType, "");
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);//设置查询页码
        PoiSearch poiSearch = new PoiSearch(context, query);
        poiSearch.setOnPoiSearchListener(listener);
        if (latLng != null) {
            //设置周边搜索的中心点以及半径
            poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latLng.latitude,
                    latLng.longitude), 2000));
        }
        poiSearch.searchPOIAsyn();
    }

    /**
     * 添加Marker点
     */
    private Marker marker;
    public void setMarker(Context context, double latitude, double longitude, String title) {
        if (marker != null) {
            marker.remove();
        }

        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(new LatLng(latitude, longitude));
        markerOption.title(title).snippet("DefaultMarker");

//        markerOption.draggable(true);//设置Marker可拖动
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(context.getResources(), R.drawable.position_picker)));
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
//        markerOption.setFlat(true);//设置marker平贴地图效果
        marker = aMap.addMarker(markerOption);
        setCenter(latitude, longitude);
    }

    /**
     * 设置中心坐标
     * @param latitude
     * @param longitude
     */
    public void setCenter(double latitude, double longitude) {
        CameraUpdate update = CameraUpdateFactory.newLatLng(new LatLng(latitude,
                longitude));
        aMap.moveCamera(update);

        //参数依次是：视角调整区域的中心点坐标、希望调整到的缩放级别、俯仰角0°~45°（垂直与地图时为0）、偏航角 0~360° (正北方为0)
//        CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(39.977290,116.337000),18,30,0));
    }

    /**
     * 在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
     *
     * @param savedInstanceState
     */
    public void onCreate(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
    }

    /**
     * 在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
     */
    public void onDestroy() {
        mapView.onDestroy();
    }

    /**
     * 在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
     */
    public void onResume() {
        mapView.onResume();
    }

    /**
     * 在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
     */
    public void onPause() {
        mapView.onPause();
    }

    /**
     * 在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
     *
     * @param outState
     */
    public void onSaveInstanceState(Bundle outState) {
        mapView.onSaveInstanceState(outState);
    }
}
