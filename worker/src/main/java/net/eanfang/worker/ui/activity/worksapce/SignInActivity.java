package net.eanfang.worker.ui.activity.worksapce;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.NumberUtil;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by wenyouyang on 2017/4/22.
 */

public class SignInActivity extends BaseWorkerActivity implements GeocodeSearch.OnGeocodeSearchListener {

    public static final String TAG = SignInActivity.class.getSimpleName();

    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_title)
    ImageView ivTitle;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.btn_sign_in)
    Button btnSignIn;
    private AMap aMap;
    private Marker marker;
    private LatLng latLng2;
    private float distance = -1;
    private GeocodeSearch geocoderSearch;
    private Long orderId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        supprotToolbar();
        setTitle("选择地址");

        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        tvTime.setText(GetDateUtils.dateToDateTimeString(GetDateUtils.getDateNow()));
//        LocationUtil.get().addListener(this, TAG);
//        LocationUtil.get().startOnce();
        String latitude = getIntent().getStringExtra("latitude");
        String longitude = getIntent().getStringExtra("longitude");
        orderId = getIntent().getLongExtra("orderId", 0);
        latLng2 = new LatLng(NumberUtil.parseDouble(latitude, 0), NumberUtil.parseDouble(longitude, 0));
        btnSignIn.setOnClickListener(v -> onViewClicked());

    }

    public void onViewClicked() {
        //修改可签到范围偏差
        if (distance >= 1000 || distance < 0) {
            showToast("不在可签到范围内" + distance);
            return;
        }

        doHttp(orderId);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        LocationUtil.get().remove(TAG);
//        LocationUtil.get().stop();
        mapView.onDestroy();
    }

//    @Override
//    public void onLocation(LatLng latLng) {
//        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
//        drawMaker(latLng);
//        distance = AMapUtils.calculateLineDistance(latLng, latLng2);
//        LogUtils.d(TAG, "================================================");
//        aMap.setMyLocationStyle(new MyLocationStyle());
//        geocoderSearch = new GeocodeSearch(this);
//        geocoderSearch.setOnGeocodeSearchListener(this);
//        LatLonPoint latLonPoint = new LatLonPoint(latLng.latitude, latLng.longitude);
//        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
//
//        geocoderSearch.getFromLocationAsyn(query);
//    }

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
            mapView.invalidate();
        }


    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        regeocodeResult.getRegeocodeAddress();
        tvAddress.setText(regeocodeResult.getRegeocodeAddress().getFormatAddress());
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }


    private void doHttp(Long orderId) {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("orderId", orderId + "");
        queryEntry.getEquals().put("signInTime", tvTime.getText().toString().trim());
        EanfangHttp.post(RepairApi.POST_FLOW_SIGNIN)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    runOnUiThread(() -> {
                        showToast("签到成功");
                        btnSignIn.setEnabled(false);
                        finish();
                    });
                }));

    }
}
