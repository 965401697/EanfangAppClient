package net.eanfang.worker.ui.activity.worksapce;

import android.os.Bundle;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.eanfang.model.SignListBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.LocationUtil;
import com.eanfang.util.NumberUtil;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/25  10:18
 * @email houzhongzhou@yeah.net
 * @desc 签到详情
 */

public class SignListDetailActivity extends BaseActivity {
    public static final String TAG = SignListDetailActivity.class.getSimpleName();
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.tv_first_name)
    TextView tvFirstName;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    private AMap aMap;
    private Marker marker;
    private LatLng latLng2;
    private float distance = -1;
    private GeocodeSearch geocoderSearch;
    private SignListBean.ListBean listBean;
    private String title;
    private int status;
    private LocationUtil locationUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_list_detail);
        ButterKnife.bind(this);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        initView();
        locationUtil = LocationUtil.get(this, mapView);
        initLocation();
    }

    private void initView() {
        title = getIntent().getStringExtra("title");
        status = getIntent().getIntExtra("status", 0);
        listBean = (SignListBean.ListBean) getIntent().getSerializableExtra("bean");
        setTitle(title + "详情");
        setLeftBack();

        tvTime.setText(listBean.getSignTime());
        tvAddress.setText(listBean.getDetailPlace());
        tvCompanyName.setText(listBean.getCompany().getOrgName());
        tvName.setText(listBean.getCreateUser().getAccountEntity().getRealName());
        if (listBean.getCreateUser().getAccountEntity().getRealName() != null) {
            String first_name = listBean.getCreateUser().getAccountEntity().getRealName();
            if (first_name.length() == 2) {
                tvFirstName.setText(first_name);
            } else if (first_name.length() == 3) {
                first_name = first_name.substring(1, 2);
                tvFirstName.setText(first_name);
            } else if (first_name.length() == 4) {
                first_name = first_name.substring(2, 3);
                tvFirstName.setText(first_name);
            }
        }
    }


    private void initLocation() {
//        LocationUtil.get().addListener(this, TAG);
        locationUtil.startOnce();
        String latitude = listBean.getLatitude();
        String longitude = listBean.getLongitude();
        latLng2 = new LatLng(NumberUtil.parseDouble(latitude, 0), NumberUtil.parseDouble(longitude, 0));


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
        locationUtil.stop();
        mapView.onDestroy();
    }

//    @Override
//    public void onLocation(LatLng latLng) {
//        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
//        drawMaker(latLng);
//        distance = AMapUtils.calculateLineDistance(latLng, latLng2);
//        aMap.setMyLocationStyle(new MyLocationStyle());
//        geocoderSearch = new GeocodeSearch(this);
//        geocoderSearch.setOnGeocodeSearchListener(this);
//        LatLonPoint latLonPoint = new LatLonPoint(latLng.latitude, latLng.longitude);
//        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
//
//        geocoderSearch.getFromLocationAsyn(query);
//    }

//    private void drawMaker(LatLng latLng) {
//        if (marker == null) {
//            MarkerOptions markerOptions = new MarkerOptions();
//            markerOptions.position(latLng)
//                    .draggable(true).
//                    icon(new MyLocationStyle().getMyLocationIcon()).setFlat(true);
//            marker = aMap.addMarker(markerOptions);
//        } else {
//            marker.setPosition(latLng);
//            mapView.invalidate();
//        }
//
//
//    }
//
//    @Override
//    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
//        regeocodeResult.getRegeocodeAddress();
//        tvAddress.setText(regeocodeResult.getRegeocodeAddress().getFormatAddress());
//    }
//
//    @Override
//    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
//
//    }

}
