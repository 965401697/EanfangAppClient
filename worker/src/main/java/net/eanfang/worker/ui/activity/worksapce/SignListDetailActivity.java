package net.eanfang.worker.ui.activity.worksapce;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.eanfang.BuildConfig;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.model.SignListBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.LocationUtil;
import com.eanfang.util.NumberUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;
import net.eanfang.worker.util.ImagePerviewUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.iv_header)
    SimpleDraweeView ivHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_visit_name)
    TextView tvVisitName;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.sdv_pic)
    SimpleDraweeView sdvPic;
    private AMap aMap;
    private Marker marker;
    private LatLng latLng2;
    private float distance = -1;
    private GeocodeSearch geocoderSearch;
    private SignListBean.ListBean listBean;
    private String title;
    private int status;
    private LocationUtil locationUtil;

    private ArrayList<String> imageList;

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
        //禁止拖拽
        locationUtil.mAMap.getUiSettings().setAllGesturesEnabled(false);
        initLocation();
    }

    private void initView() {
        title = getIntent().getStringExtra("title");
        status = getIntent().getIntExtra("status", 0);
        listBean = (SignListBean.ListBean) getIntent().getSerializableExtra("bean");
        setTitle(title + "详情");
        setLeftBack();

        tvTime.setText(listBean.getSignTime());
        tvAddress.setText(Config.get().getAddressByCode(listBean.getZoneCode()) + listBean.getDetailPlace());
        tvCompanyName.setText(listBean.getCompany().getOrgName());
        tvName.setText(listBean.getCreateUser().getAccountEntity().getRealName());
        ivHeader.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + EanfangApplication.get().getUser().getAccount().getAvatar()));


        if (!TextUtils.isEmpty(listBean.getVisitorName())) {
            tvVisitName.setText(listBean.getVisitorName());
        } else {
            tvVisitName.setVisibility(View.GONE);
        }


        if (!TextUtils.isEmpty(listBean.getRemarkInfo())) {
            tvRemark.setText(listBean.getRemarkInfo());
        } else {
            tvRemark.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(listBean.getPictures())) {
            String[] urls = listBean.getPictures().split(",");
            sdvPic.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + urls[0]));
            imageList = new ArrayList<>();
            imageList.add(BuildConfig.OSS_SERVER + urls[0]);
        } else {
            sdvPic.setVisibility(View.GONE);
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

    @OnClick(R.id.sdv_pic)
    public void onViewClicked() {
        ImagePerviewUtil.perviewImage(this, imageList);
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
