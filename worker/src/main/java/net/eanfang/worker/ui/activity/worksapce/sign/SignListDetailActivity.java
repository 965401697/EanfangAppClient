package net.eanfang.worker.ui.activity.worksapce.sign;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.annimon.stream.Stream;
import com.eanfang.BuildConfig;
import com.eanfang.config.Config;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.model.SignListBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.NumberUtil;
import com.eanfang.util.StringUtils;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.worker.R;

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
    @BindView(R.id.tv_sing_time_type)
    TextView tvSingTimeType;
    @BindView(R.id.tv_sing_person)
    TextView tvSingPerson;
    private AMap aMap;
    private Marker marker;
    private LatLng latLng2;
    private SignListBean.ListBean listBean;
    /**
     * 签到or 签退
     */
    private int status;

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
        //禁止拖拽
        aMap.getUiSettings().setAllGesturesEnabled(false);
        initLocation();
    }

    private void initView() {
        listBean = (SignListBean.ListBean) getIntent().getSerializableExtra("bean");
        setTitle("足迹");
        setLeftBack();
        status = getIntent().getIntExtra("status", 0);
        if (status == 1) {// 1 签退
            tvSingTimeType.setText("签退时间");
        } else {// 签到
            tvSingTimeType.setText("签到时间");
        }

        tvTime.setText(listBean.getSignTime());
        tvAddress.setText(Config.get().getAddressByCode(listBean.getZoneCode()) + listBean.getDetailPlace());


        if (!TextUtils.isEmpty(listBean.getVisitorName())) {
            tvVisitName.setText(listBean.getVisitorName());
        } else {
            tvVisitName.setVisibility(View.GONE);
        }

        tvSingPerson.setText(listBean.getCreateUser().getAccountEntity().getRealName());


        if (!TextUtils.isEmpty(listBean.getRemarkInfo())) {
            tvRemark.setText(listBean.getRemarkInfo());
        } else {
            tvRemark.setVisibility(View.GONE);
        }

        if (StringUtils.isValid(listBean.getPictures())) {
            String[] friontPic = listBean.getPictures().split(",");
            imageList.addAll(Stream.of(Arrays.asList(friontPic)).map(url -> (BuildConfig.OSS_SERVER + url).toString()).toList());
        }
        snplMomentPhotos.setDelegate(new BGASortableDelegate(this));
        snplMomentPhotos.setData(imageList);
        snplMomentPhotos.setEditable(false);
    }


    private void initLocation() {
        String latitude = listBean.getLatitude();
        String longitude = listBean.getLongitude();
        latLng2 = new LatLng(NumberUtil.parseDouble(latitude, 0), NumberUtil.parseDouble(longitude, 0));
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng2, 15));// 第二个参数： 地图缩放比例 4-20 越大范围越小 反之。
        drawMaker(latLng2);
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


    private void drawMaker(LatLng latLng) {
        if (marker == null) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng)
                    .draggable(false).//点标记是否可拖拽
                    icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.position_picker)));
            aMap.addMarker(markerOptions);
        } else {
            marker.setPosition(latLng);
            mapView.invalidate();
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mapView.onSaveInstanceState(outState);
    }
}
