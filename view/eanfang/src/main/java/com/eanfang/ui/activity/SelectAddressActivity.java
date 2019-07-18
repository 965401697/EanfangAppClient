package com.eanfang.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.R;
import com.eanfang.R2;
import com.eanfang.base.kit.rx.RxPerm;
import com.eanfang.biz.model.SelectAddressItem;
import com.eanfang.config.Constant;
import com.eanfang.ui.adapter.SelectAddressAdapter;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.LocationUtil;
import com.eanfang.util.StringUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.amap.api.services.geocoder.GeocodeSearch.AMAP;

/**
 * 我要报修-选择位置
 * Created by Administrator on 2017/3/25.
 */

public class SelectAddressActivity extends BaseActivity implements PoiSearch.OnPoiSearchListener {
    public static final String TAG = SelectAddressActivity.class.getSimpleName();
    @BindView(R2.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R2.id.atv_text)
    EditText atvText;
    @BindView(R2.id.map)
    MapView mMapView;

    //    @BindView(R2.id.tv_fuwei)
//    Button btnFuwei;
    private ArrayList<SelectAddressItem> mDataList = new ArrayList<>();
    private SelectAddressAdapter evaluateAdapter;
    private PoiResult poiResult;
    private ArrayList<PoiItem> poiItems;
    private static final int RESULT_CODE = 1;

    private PoiSearch.Query query;
    private String queryType = Constant.mapScope;
    private PoiSearch poiSearch;

    private LocationUtil locationUtil;

    private boolean isSearch = false;
    private LatLng mLatlng;

    private static final int MSG_SEARCH = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_select_address);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        supprotToolbar();
        setTitle("选择地址");
        mMapView.onCreate(savedInstanceState);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        locationUtil = LocationUtil.get(this, mMapView);

        initView();
        initAdapter();
        runOnUiThread(() -> {
            RxPerm.get(this).locationPerm((isSuccess) -> locationUtil.startOnce());
        });
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        atvText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                //文字变动 ， 有未发出的搜索请求，应取消
                if (mHandler.hasMessages(MSG_SEARCH)) {
                    mHandler.removeMessages(MSG_SEARCH);
                }
                //如果为空 直接显示搜索历史
//                if (TextUtils.isEmpty(keyword)) {
                //showHistory();
//                } else {
                //否则延迟500ms开始搜索
                mHandler.sendEmptyMessageDelayed(MSG_SEARCH, 500); //自动搜索功能 删除
//                }
            }
        });
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String keyword = atvText.getText().toString().trim();
            if (!StringUtils.isEmpty(keyword)) {
                //搜索请求
                isSearch = true;
//                textChangeSearch(keyword);
                doSearchQuery(keyword, null);
            }

        }
    };

    private void initAdapter() {
        evaluateAdapter = new SelectAddressAdapter(R.layout.item_select_address, mDataList);
        evaluateAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                String address = mDataList.get(position).getName();
                if (!address.contains("(")) {
                    address += "(" + mDataList.get(position).getZone() + ")";
                    mDataList.get(position).setName(address);
                }
                intent.putExtra("data", mDataList.get(position));

                setResult(RESULT_CODE, intent);
                finishSelf();
            }
        });
        locationUtil.onChanged = (keywords, latLng) -> {
            mLatlng = latLng;
            locationUtil.setMarket(latLng);
            // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
            RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(latLng.latitude, latLng.longitude), 2000, AMAP);
            locationUtil.geocoderSearch.getFromLocationAsyn(query);
            doSearchQuery("", latLng);
        };

        mRecyclerView.setAdapter(evaluateAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        locationUtil.stop();
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
    public void onPoiSearched(PoiResult result, int rcode) {
        if (rcode == 1000) {
            // 搜索poi的结果
            if (result != null && result.getQuery() != null && result.getPois().size() > 0) {
                // 是否是同一条
                if (result.getQuery().equals(query)) {
                    mDataList.clear();
                    poiResult = result;
                    // 取得第一页的poiitem数据，页数从数字0开始
                    poiItems = poiResult.getPois();
                    // 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
//                    List<SuggestionCity> suggestionCities = poiResult.getSearchSuggestionCitys();

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

                    if (poiItems != null && isSearch) {
                        PoiItem poiItem = poiItems.get(0);
                        LatLng latLng = new LatLng(poiItem.getLatLonPoint().getLatitude(), poiItem.getLatLonPoint().getLongitude());
                        CameraUpdate update = CameraUpdateFactory.newLatLng(latLng);
                        locationUtil.mAMap.moveCamera(update);
                        locationUtil.isSearchMove = true;
                        locationUtil.setMarket(latLng);
                        isSearch = false;
//                        PoiItem poiItem = poiItems.get(0);
//                        LatLng latLng = new LatLng(poiItem.getLatLonPoint().getLatitude(), poiItem.getLatLonPoint().getLongitude());
//                        final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title(poiItem.getTitle()).snippet("DefaultMarker"));
//                        aMap.reloadMap();
                    }
                    hideInput();
                }
            } else {
                mDataList.clear();
                showToast("对不起，没有搜索到相关数据！");
            }

        } else {
            mDataList.clear();
            showToast("对不起，没有搜索到相关数据！");
        }
        evaluateAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    public void hideInput() {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(atvText.getWindowToken(), 0);
    }

    public void doSearchQuery(String keywords, LatLng latLng) {
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query = new PoiSearch.Query(keywords, queryType, " ");
        //设置城市限制
        query.setCityLimit(true);
        // 设置每页最多返回多少条poiitem
        query.setPageSize(20);
        // 设置查第一页
        query.setPageNum(0);

        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);

        if (latLng != null) {
            poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latLng.latitude, latLng.longitude), 2000, true));
        }
        // 设置搜索区域为以lp点为圆心，其周围5000米范围
        // 异步搜索
        poiSearch.searchPOIAsyn();
    }

}
