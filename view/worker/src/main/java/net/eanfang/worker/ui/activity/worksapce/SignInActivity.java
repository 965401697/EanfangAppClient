package net.eanfang.worker.ui.activity.worksapce;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.config.Config;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseEvent;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.LocationUtil;
import com.eanfang.util.NumberUtil;
import com.eanfang.base.kit.rx.RxPerm;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hutool.core.date.DateUtil;

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
    // 城市
    private String city = "";
    private String county = "";

    private int mIsFromType;  // 1：维保签到

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mapView.onCreate(savedInstanceState);
        initView();
        initMap();
    }

    private void initView() {
        setTitle("选择地址");
        setLeftBack();
        tvTime.setText(DateUtil.date().toString("yyyy年MM月dd日 HH:mm:ss"));
        String latitude = getIntent().getStringExtra("latitude");
        String longitude = getIntent().getStringExtra("longitude");
        orderId = getIntent().getLongExtra("orderId", 0);
        mIsFromType = getIntent().getIntExtra("isFromType", 0);
        latLng2 = new LatLng(NumberUtil.parseDouble(latitude, 0), NumberUtil.parseDouble(longitude, 0));
        rlSignIn.setOnClickListener(v -> onViewClicked());
    }

    private void initMap() {
        locationUtil = LocationUtil.get(SignInActivity.this, mapView);
        RxPerm.get(this).locationPerm((isSuccess) -> {
            locationUtil.startOnce();
        });
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

        locationUtil.onLocationed = (location) -> {
            city = location.getCity();
            county = location.getDistrict();
            String address = location.getAddress().replace(city, "").replace(county, "");
            if (!address.contains("(")) {
                address += " (" + location.getDescription() + ")";
            }
            tvAddress.setText(address);
        };

    }

    public void onViewClicked() {
        //修改可签到范围偏差
        if (distance >= 1000 || distance < 0) {
            if (mLongitude <= 0 || mLatitude <= 0) {
                showToast("定位失败，请检查定位或返回后重试。");
                return;
            }
            mSignScope = "1";
            singNotAround();
        } else {
            mSignScope = "0";
//            doHttp(orderId);
            if (mIsFromType == 1) {
                doMaintenanceHttp(orderId);
            } else {
                doHttp(orderId);
            }
        }
    }

    /**
     * 不在范围内的签到
     */
    private void singNotAround() {
        new TrueFalseDialog(this, "当前不在签到范围内", "是否继续签到", () -> {
            if (mIsFromType == 1) {
                doMaintenanceHttp(orderId);
            } else {
                doHttp(orderId);
            }
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
//        QueryEntry queryEntry = new QueryEntry();
//        queryEntry.getEquals().put("orderId", orderId + "");
//        queryEntry.getEquals().put("signInTime", DateUtil.date().toString());
//        queryEntry.getEquals().put("signLongitude", mLongitude + "");
//        queryEntry.getEquals().put("signLatitude", mLatitude + "");
//        queryEntry.getEquals().put("signScope", mSignScope);
//        queryEntry.getEquals().put("signCode", Config.get().getAreaCodeByName(city, county));
//        queryEntry.getEquals().put("signAddress", tvAddress.getText().toString().trim());

        EanfangHttp.post(RepairApi.POST_FLOW_SIGNIN)
                .params("orderId", orderId + "")
                .params("signInTime", DateUtil.date().toString())
                .params("signLongitude", mLongitude + "")
                .params("signLatitude", mLatitude + "")
                .params("signCode", Config.get().getAreaCodeByName(city, county))
                .params("signScope", mSignScope)
                .params("signAddress", tvAddress.getText().toString().trim())
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    runOnUiThread(() -> {
                        showToast("签到成功");
                        rlSignIn.setEnabled(false);
                        setResult(RESULT_OK);
                        finish();
                    });
                }));

    }

    /**
     * 维保签到
     *
     * @param orderId
     */
    private void doMaintenanceHttp(Long orderId) {
//        QueryEntry queryEntry = new QueryEntry();
//        queryEntry.getEquals().put("orderId", orderId + "");
//        queryEntry.getEquals().put("signInTime", DateUtil.date().toString());
//        queryEntry.getEquals().put("signLongitude", mLongitude + "");
//        queryEntry.getEquals().put("signLatitude", mLatitude + "");
//        queryEntry.getEquals().put("signScope", mSignScope);
//        queryEntry.getEquals().put("signCode", Config.get().getAreaCodeByName(city, county));
//        queryEntry.getEquals().put("signAddress", tvAddress.getText().toString().trim());

        JSONObject object = new JSONObject();
        object.put("id", orderId + "");
        object.put("signTime", DateUtil.date().toString());
        object.put("signLongitude", mLongitude + "");
        object.put("signLatitude", mLatitude + "");
        object.put("signScope", mSignScope);
        object.put("signCode", Config.get().getAreaCodeByName(city, county));
        object.put("signAddress", tvAddress.getText().toString().trim());

        EanfangHttp.post(NewApiService.MAINTENANCE_SINGIN)
                .upJson(JsonUtils.obj2String(object))
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    runOnUiThread(() -> {
                        showToast("签到成功");
                        EventBus.getDefault().post(new BaseEvent());//刷新item
                        rlSignIn.setEnabled(false);
                        finish();
                    });
                }));

    }
}
