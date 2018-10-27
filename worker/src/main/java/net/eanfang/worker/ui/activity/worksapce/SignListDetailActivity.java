package net.eanfang.worker.ui.activity.worksapce;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.annimon.stream.Stream;
import com.eanfang.BuildConfig;
import com.eanfang.config.Config;
import com.eanfang.model.SignListBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.LocationUtil;
import com.eanfang.util.NumberUtil;
import com.eanfang.util.StringUtils;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.util.ImagePerviewUtil;

import java.util.ArrayList;
import java.util.Arrays;

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
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_visit_name)
    TextView tvVisitName;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.snpl_moment_photos)
    BGASortableNinePhotoLayout snplMomentPhotos;
    @BindView(R.id.iv_open)
    ImageView ivOpen;
    @BindView(R.id.ll_photos)
    LinearLayout llPhotos;
    private AMap aMap;
    private Marker marker;
    private LatLng latLng2;
    private float distance = -1;
    private GeocodeSearch geocoderSearch;
    private SignListBean.ListBean listBean;
    private int status;
    private LocationUtil locationUtil;


    private ArrayList<String> imageList = new ArrayList<>();

    // 是否是展开还是关闭
    private boolean isOpen = false;

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
        status = getIntent().getIntExtra("status", 0);
        listBean = (SignListBean.ListBean) getIntent().getSerializableExtra("bean");
        setTitle("足迹");
        setLeftBack();

        tvTime.setText(listBean.getSignTime());
        tvAddress.setText(Config.get().getAddressByCode(listBean.getZoneCode()) + listBean.getDetailPlace());


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

        if (StringUtils.isValid(listBean.getPictures())) {
            String[] friontPic = listBean.getPictures().split(",");
            imageList.addAll(Stream.of(Arrays.asList(friontPic)).map(url -> (BuildConfig.OSS_SERVER + url).toString()).toList());
        }
        snplMomentPhotos.setData(imageList);
        snplMomentPhotos.setEditable(false);
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

    @OnClick(R.id.iv_open)
    public void onViewClicked() {
        if (isOpen) {
            ivOpen.setImageResource(R.mipmap.ic_sign_detail_top);
            llPhotos.setVisibility(View.GONE);
            isOpen = false;
        } else {
            llPhotos.setVisibility(View.VISIBLE);
            ivOpen.setImageResource(R.mipmap.ic_sign_detail_down);
            isOpen = true;
        }
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
