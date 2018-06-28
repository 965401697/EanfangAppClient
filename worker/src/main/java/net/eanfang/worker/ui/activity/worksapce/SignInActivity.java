package net.eanfang.worker.ui.activity.worksapce;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.LocationUtil;
import com.eanfang.util.NumberUtil;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.amap.api.services.geocoder.GeocodeSearch.AMAP;


/**
 * Created by wenyouyang on 2017/4/22.
 */

public class SignInActivity extends BaseWorkerActivity {

    public static final String TAG = SignInActivity.class.getSimpleName();

    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.rl_sign_in)
    RelativeLayout rlSignIn;
    private LatLng latLng2;
    private float distance = -1;
    private Long orderId;
    private LocationUtil locationUtil;
    // 纬度
    private double mLatitude;
    // 经度
    private double mLongitude;
    // 是否在签到范围内
    private String mSignScope = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mapView.onCreate(savedInstanceState);
        initView();
        initMap();
    }

    private void initView() {
        setTitle("选择地址");
        setLeftBack();
        tvTime.setText(GetDateUtils.dateToDateTimeStringForChinse(GetDateUtils.getDateNow()));
        String latitude = getIntent().getStringExtra("latitude");
        String longitude = getIntent().getStringExtra("longitude");
        orderId = getIntent().getLongExtra("orderId", 0);
        latLng2 = new LatLng(NumberUtil.parseDouble(latitude, 0), NumberUtil.parseDouble(longitude, 0));
        rlSignIn.setOnClickListener(v -> onViewClicked());
    }

    private void initMap() {
        locationUtil = LocationUtil.get(SignInActivity.this, mapView);
        PermissionUtils.get(this).getLocationPermission(() -> locationUtil.startOnce());
        //禁止所有手势
        locationUtil.mAMap.getUiSettings().setAllGesturesEnabled(false);
        locationUtil.onChanged = (keywords, latLng) -> {
            locationUtil.setMarket(latLng);
            distance = AMapUtils.calculateLineDistance(latLng, latLng2);
            // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
            RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(latLng.latitude, latLng.longitude), 200, AMAP);
            locationUtil.geocoderSearch.getFromLocationAsyn(query);
            // 获取技师当前经纬度
            mLatitude = latLng.latitude;
            mLongitude = latLng.longitude;
        };
        locationUtil.onSearched = (regeocodeResult, i) -> {
            regeocodeResult.getRegeocodeAddress();
            tvAddress.setText(regeocodeResult.getRegeocodeAddress().getFormatAddress());
        };

    }

    public void onViewClicked() {
        //修改可签到范围偏差
        if (distance >= 1000 || distance < 0) {
            mSignScope = "1";
            singNotAround();
        } else {
            mSignScope = "0";
            doHttp(orderId);
        }
    }

    /**
     * 不在范围内的签到
     */
    private void singNotAround() {
        new TrueFalseDialog(this, "当前不在签到范围内", "是否继续签到", () -> {
            doHttp(orderId);
        }).showDialog();
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


    private void doHttp(Long orderId) {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("orderId", orderId + "");
        queryEntry.getEquals().put("signInTime", GetDateUtils.dateToDateTimeString(GetDateUtils.getDateNow()));
        queryEntry.getEquals().put("signLongitude", mLongitude + "");
        queryEntry.getEquals().put("signLatitude", mLatitude + "");
        queryEntry.getEquals().put("signScope", mSignScope);
        EanfangHttp.post(RepairApi.POST_FLOW_SIGNIN)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    runOnUiThread(() -> {
                        showToast("签到成功");
                        rlSignIn.setEnabled(false);
                        finish();
                    });
                }));

    }
}
