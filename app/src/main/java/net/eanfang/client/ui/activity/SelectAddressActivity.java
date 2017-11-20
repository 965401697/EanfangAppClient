package net.eanfang.client.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.SelectAddressAdapter;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.model.SelectAddressItem;
import net.eanfang.client.util.LocationUtil;
import net.eanfang.client.util.LogUtils;
import net.eanfang.client.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.amap.api.services.geocoder.GeocodeSearch.AMAP;
import static com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;

/**
 * 我要报修-选择位置
 * Created by Administrator on 2017/3/25.
 */

public class SelectAddressActivity extends BaseActivity implements LocationUtil.OnLocationListener, PoiSearch.OnPoiSearchListener, LocationSource, View.OnClickListener, AMap.OnCameraChangeListener, AMap.OnMarkerDragListener, OnGeocodeSearchListener {
    public static final String TAG = SelectAddressActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private ArrayList<SelectAddressItem> mDataList;
    MapView mMapView = null;
    AMap aMap;
    private PoiSearch poiSearch;
    private Marker marker;
    private SelectAddressAdapter evaluateAdapter;
    private PoiSearch.Query query;
    private PoiResult poiResult;
    private ArrayList<PoiItem> poiItems;
    private TextView atv_text;
    private ImageButton btn_search;
    private LatLng mFinalChoosePosition;
    private String curCityCode;
    private GeocodeSearch geocoderSearch;
    private Button btn_fuwei;
    private boolean isSearchMove;
    private String queryType = "加油站|其它能源站|加气站|汽车养护/装饰|汽车配件销售|汽车租赁|二手车交易|充电站|汽车销售|汽车维修|" +
            "餐饮服务|购物服务|售票处|邮局|物流速递|电讯营业厅|人才市场|自来水营业厅|电力营业厅|体育休闲服务|医疗保健服务|住宿服务|风景名胜|" +
            "商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|金融保险服务|知名企业|地名地址信息|公共设施|通行设施";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);

        supprotToolbar();
        setTitle("选择地址");

        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
        }

        initView();
        initData();
        initAdapter();


        //禁止倾斜手势
        aMap.getUiSettings().setTiltGesturesEnabled(false);
        //禁止旋转收拾
        aMap.getUiSettings().setRotateGesturesEnabled(false);
        aMap.setOnCameraChangeListener(this);
        aMap.setOnMarkerDragListener(this);
        LocationUtil.get().addListener(this, TAG);
        LocationUtil.get().startOnce();
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        atv_text = (TextView) findViewById(R.id.atv_text);
        atv_text.setOnClickListener(this);
        btn_search = (ImageButton) findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);
        btn_fuwei = (Button) findViewById(R.id.tv_fuwei);
        btn_fuwei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationUtil.get().addListener(SelectAddressActivity.this, TAG);
                LocationUtil.get().startOnce();
            }
        });
    }

    private void initData() {
        mDataList = new ArrayList<SelectAddressItem>();
    }

    private void initAdapter() {
        evaluateAdapter = new SelectAddressAdapter(R.layout.item_select_address, mDataList);
        evaluateAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("data", mDataList.get(position));
                setResult(10001, intent);
                finishSelf();
            }
        });
        mRecyclerView.setAdapter(evaluateAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ==================================");
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        LocationUtil.get().remove(TAG);
        LocationUtil.get().stop();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLocation(LatLng latLng) {
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
//        addmark(latLng.latitude, latLng.latitude);
        drawMaker(latLng);
        LogUtils.d(TAG, "================================================");
//        aMap.setMyLocationStyle(new MyLocationStyle());
        doSearchQuery(latLng);
        geocoderSearch(latLng);
        LocationUtil.get().remove(TAG);
        LocationUtil.get().stop();
    }


    public void geocoderSearch(LatLng latLng) {
        aMap.setMyLocationStyle(new MyLocationStyle());
        RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(latLng.latitude, latLng.longitude), 200,
                AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);
    }

    @Override
    public void onPoiSearched(PoiResult result, int rcode) {
        LogUtils.d(TAG, "================================================" + rcode);
        if (rcode == 1000) {
            if (result != null && result.getQuery() != null && result.getPois().size() > 0) {// 搜索poi的结果
                LogUtils.d(TAG, "================================================" + result.toString() + "   " + result.getSearchSuggestionKeywords() + result.getPois().size());
                if (result.getQuery().equals(query)) {// 是否是同一条

                    mDataList.clear();
                    poiResult = result;
                    poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult.getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
                    for (PoiItem poiItem : poiItems) {

                        SelectAddressItem item = new SelectAddressItem();
                        item.setAddress(poiItem.getAdName());
                        item.setName(poiItem.getTitle());
                        item.setProvince(poiItem.getProvinceName());
                        item.setCity(poiItem.getCityName());
                        item.setZone(poiItem.getSnippet());
                        item.setLatitude(poiItem.getLatLonPoint().getLatitude());
                        item.setLongitude(poiItem.getLatLonPoint().getLongitude());
                        mDataList.add(item);
                    }

                    if (poiItems != null) {
                        PoiItem poiItem = poiItems.get(0);
                        LatLng latLng = new LatLng(poiItem.getLatLonPoint().getLatitude(), poiItem.getLatLonPoint().getLongitude());
                        CameraUpdate update = CameraUpdateFactory.newLatLng(latLng);
                        aMap.moveCamera(update);
                        isSearchMove = true;

//                        PoiItem poiItem = poiItems.get(0);
//                        LatLng latLng = new LatLng(poiItem.getLatLonPoint().getLatitude(), poiItem.getLatLonPoint().getLongitude());
//                        final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title(poiItem.getTitle()).snippet("DefaultMarker"));
//                        aMap.reloadMap();
                    }
                    evaluateAdapter.notifyDataSetChanged();

                }
            } else {
                showToast("对不起，没有搜索到相关数据！");
            }
        } else {
            showToast("对不起，没有搜索到相关数据！");
        }

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

        LogUtils.d(TAG, "================================================");

    }


    protected void doSearchQuery(LatLng latLng) {
        LogUtils.d(TAG, "================================");
        query = new PoiSearch.Query("", queryType, "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);// 设置查第一页

        if (latLng != null) {
            poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);  // 实现  onPoiSearched  和  onPoiItemSearched
            poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latLng.latitude, latLng.longitude), 500, true));//
            // 设置搜索区域为以lp点为圆心，其周围5000米范围
            poiSearch.searchPOIAsyn();// 异步搜索
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {


    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                String keyword = atv_text.getText().toString().trim();
                if (keyword.length() > 0) {
                    doSearchQuery(keyword);
                } else {
                    showToast("请输入要查找的地址");
                }

                break;
        }
    }

    protected void doSearchQuery(String keyword) {
        query = new PoiSearch.Query(keyword, "", "北京");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);// 设置查第一页

        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);  // 实现  onPoiSearched  和  onPoiItemSearched
        // 设置搜索区域为以lp点为圆心，其周围5000米范围
        poiSearch.searchPOIAsyn();// 异步搜索
    }


    private void drawMaker(LatLng latLng) {
        if (marker == null) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng)
//                    .title("位置").snippet(latLng.toString())
                    .draggable(true).
                    icon(new MyLocationStyle().getMyLocationIcon()).setFlat(true);
            marker = aMap.addMarker(markerOptions);
        } else {
            marker.setPosition(latLng);
            mMapView.invalidate();
        }


    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        LogUtils.d(TAG, "拖动地图=========");
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {

        mFinalChoosePosition = cameraPosition.target;
        drawMaker(mFinalChoosePosition);
        if (isSearchMove) {
            isSearchMove = false;
            return;
        }
        doSearchQuery(mFinalChoosePosition);
        geocoderSearch(mFinalChoosePosition);
        LogUtils.d(TAG, "拖动地图 Finish changeCenterMarker 经度" + mFinalChoosePosition.longitude + "   纬度：" + mFinalChoosePosition.latitude);
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        doSearchQuery(marker.getPosition());
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
        if (StringUtils.isEmpty(curCityCode)) {
            marker.setSnippet(geocodeResult.getGeocodeQuery().getLocationName());
        }
        if (geocodeResult.getGeocodeAddressList() != null && geocodeResult.getGeocodeAddressList().size() > 0) {
            curCityCode = geocodeResult.getGeocodeAddressList().get(0).getFormatAddress();
        }
    }
}
