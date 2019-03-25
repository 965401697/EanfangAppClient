package net.eanfang.worker.ui.activity.worksapce.sign;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;
import com.eanfang.apiservice.UserApi;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.SignCountBean;
import com.eanfang.model.SigninBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.ConnectivityChangeUtil;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermKit;
import com.eanfang.util.ToastUtil;

import net.eanfang.worker.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/24  15:38
 * @email houzhongzhou@yeah.net
 * @desc 签到
 */

public class SignActivity extends BaseActivity implements LocationSource, AMapLocationListener {


    private static final int SIGN_REQUEST = 100;
    private static final int SIGN_RESULT = 200;

    public static final String TAG = SignActivity.class.getSimpleName();
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.et_visit_name)
    EditText etVisitName;
    @BindView(R.id.text_clock)
    TextView textClock;
    @BindView(R.id.ll_signin)
    LinearLayout llSignin;
    @BindView(R.id.tv_sign_times)
    TextView tvSignTimes;
    @BindView(R.id.ll_footer)
    LinearLayout llFooter;
    @BindView(R.id.ll_sign_times)
    LinearLayout llSignTimes;
    @BindView(R.id.tv_sign_name)
    TextView tvSignName;
    @BindView(R.id.tv_sign_had_time)
    TextView tvSignHadTime;
    @BindView(R.id.tv_sign_or_signout)
    TextView tvSignOrSignout;

    private AMap aMap;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private Double latitude, longitude;
    String cityAddress, detailPlace, contry;
    private String title;
    private int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        ButterKnife.bind(this);
        initView();
        // 此方法必须重写
        mapView.onCreate(savedInstanceState);
        initGPS();
        initMap();
        setClick();
        signCount();
    }

    private void initView() {
        title = getIntent().getStringExtra("title");
        status = getIntent().getIntExtra("status", 0);
        setTitle(title);
        setLeftBack();
        tvSignHadTime.setText("你已经" + title);
        tvSignOrSignout.setText(title);
        tvSignName.setText(title);
        textClock.setText(new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(new Date()));
    }

    private void setClick() {

        llSignin.setOnClickListener(v -> fillData());
        llFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status == 0) {
                    if (!PermKit.get().getSignInListPrem()) {
                        return;
                    }
                } else {
                    if (!PermKit.get().getSignOutListPrem()) {
                        return;
                    }
                }

                startActivity(new Intent(SignActivity.this, SignListActivity.class)
                        .putExtra("title", title)
                        .putExtra("status", status));
            }
        });
    }

    private void signCount() {
        EanfangHttp.get(UserApi.GET_SIGN_COUNT)
                .params("status", status)
                .execute(new EanfangCallback<SignCountBean>(this, true, SignCountBean.class, (bean) -> {
                    if (bean.getCount() != 0) {
                        tvSignTimes.setText(bean.getCount() + "次");
                        llSignTimes.setVisibility(View.VISIBLE);
                    } else {
                        llSignTimes.setVisibility(View.GONE);
                    }

                }));
    }

    private void fillData() {
        SigninBean signinBean = new SigninBean();
        signinBean.setLatitude(latitude + "");
        signinBean.setLongitude(longitude + "");
        signinBean.setSignTime(GetDateUtils.dateToDateTimeString(new Date()));
        signinBean.setDetailPlace(detailPlace);
        signinBean.setVisitorName(etVisitName.getText().toString().trim());
        signinBean.setZoneCode(Config.get().getAreaCodeByName(cityAddress, contry));
        signinBean.setStatus(status);
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("status", status);
        bundle.putSerializable("bean", signinBean);
        JumpItent.jump(SignActivity.this, SignInCommitActivity.class, bundle, SIGN_REQUEST);
    }

    private void initMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        // 设置定位监听
        aMap.setLocationSource(this);
        // 设置默认定位按钮是否显示
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
        setupLocationStyle();
    }

    private void setupLocationStyle() {
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.drawable.blue_point));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(STROKE_COLOR);
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(5);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(FILL_COLOR);
        // 将自定义的 myLocationStyle 对象添加到地图上
        aMap.setMyLocationStyle(myLocationStyle);
        // 缩放等级
        aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }


    /**
     * 初始化gps
     */

    private void initGPS() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // 判断GPS模块是否开启，如果没有则开启
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            ToastUtil.get().showToast(this, "请打开GPS,定位更准确");
        }
        if (ConnectivityChangeUtil.isNetConnected(this) == false) {
            ToastUtil.get().showToast(this, "没有网络，请检查网络");
        }
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null && amapLocation.getErrorCode() == 0) {
                // 显示系统小蓝点
                mListener.onLocationChanged(amapLocation);
                StringBuffer sb = new StringBuffer();
                //省信息
//                sb.append(amapLocation.getProvince());
                //城市信息
//                sb.append(amapLocation.getCity());
                //城区信息
//                sb.append(amapLocation.getDistrict());
                //街道信息
                sb.append(amapLocation.getStreet());
                //街道门牌号信息
                amapLocation.getStreetNum();
                //获取当前定位点的AOI信息
                sb.append(amapLocation.getAoiName());
                //获取当前室内定位的建筑物Id
                sb.append(amapLocation.getBuildingId());
                //获取当前室内定位的楼层
                sb.append(amapLocation.getFloor());
//                LogUtils.e("amapSuccess", sb.toString());
                latitude = amapLocation.getLatitude();
                longitude = amapLocation.getLongitude();
                cityAddress = amapLocation.getCity();
                contry = amapLocation.getDistrict();
                detailPlace = sb.toString();

            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);

            }
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == SIGN_REQUEST && resultCode == SIGN_RESULT) {
            title = data.getStringExtra("title");
            status = data.getIntExtra("status", 0);
            tvSignHadTime.setText("你已经" + title);
            tvSignOrSignout.setText(title);
            tvSignName.setText(title);
            etVisitName.setText("");
            signCount();
        }
    }

}
