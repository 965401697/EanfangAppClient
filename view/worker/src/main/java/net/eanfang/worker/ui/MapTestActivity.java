package net.eanfang.worker.ui;

import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.eanfang.sdk.map.MapM;
import com.eanfang.ui.base.BaseActivity;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapTestActivity extends BaseActivity implements AMap.OnMyLocationChangeListener,
        PoiSearch.OnPoiSearchListener,AMap.OnCameraChangeListener {
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.editext)
    EditText editext;
    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_maptest);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        MapM.getInstance()
                .init(mapView, savedInstanceState)
                .location(this,this);
        editext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String key = editext.getText().toString();
                if (!key.equals("")) {
                    MapM.getInstance().poiSearch(MapTestActivity.this, key, MapTestActivity.this);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MapM.getInstance().onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MapM.getInstance().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MapM.getInstance().onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        MapM.getInstance().onSaveInstanceState(outState);
    }

    @Override
    public void onMyLocationChange(Location location) {
        //从location对象中获取经纬度信息，地址描述信息，建议拿到位置之后调用逆地理编码接口获取（获取地址描述数据章节有介绍）
        if (location != null) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());//39.925597 116.608144
            MapM.getInstance().setCenter(location.getLatitude(),location.getLongitude());
            MapM.getInstance().poiSearch(MapTestActivity.this, latLng, MapTestActivity.this);
        }
    }

    /**
     * @param poiResult 解析result获取POI信息
     * @param code      返回结果成功或者失败的响应码。1000为成功，其他为失败
     */
    @Override
    public void onPoiSearched(PoiResult poiResult, int code) {
        if (code == 1000) {
           PoiItem poiItem= poiResult.getPois().get(0);
            if (poiItem!=null) {
                MapM.getInstance().setMarker(MapTestActivity.this,poiItem.getLatLonPoint().getLatitude(),
                        poiItem.getLatLonPoint().getLongitude(), poiItem.getTitle());

            }
            tv.setText(poiResult.getPois().toString());
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        MapM.getInstance().setMarker(this,cameraPosition.target.latitude,cameraPosition.target.longitude,"");
        MapM.getInstance().poiSearch(this,cameraPosition.target,this);
    }
}
