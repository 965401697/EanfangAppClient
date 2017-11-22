package net.eanfang.client.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.camera.model.PermissionsModel;
import com.camera.util.BitmapUtil;
import com.camera.util.ImageUtil;
import com.camera.view.TakePhotoActivity;
import com.eanfang.util.ConnectivityChangeReceiver;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.ToastUtil;

import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.util.LogUtils;

import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Mr.hou
 *
 * @on 2017/9/26  15:12
 * @email houzhongzhou@yeah.net
 * @desc 相机助手
 */

public class CameraActivity extends BaseActivity implements AMapLocationListener, RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.et_project_name)
    EditText etProjectName;
    @BindView(R.id.et_region_name)
    EditText etRegionName;
    @BindView(R.id.et_project_conment)
    EditText etProjectConment;
    @BindView(R.id.showTakePhotoImg)
    ImageView showTakePhotoImg;
    @BindView(R.id.fl_camera)
    FrameLayout flCamera;
    @BindView(R.id.color_white)
    RadioButton colorWhite;
    @BindView(R.id.color_red)
    RadioButton colorRed;
    @BindView(R.id.rg_color)
    RadioGroup rgColor;
    @BindView(R.id.tv_repair)
    RadioButton tvRepair;
    @BindView(R.id.tv_check)
    RadioButton tvCheck;
    @BindView(R.id.tv_do)
    RadioButton tvDo;
    @BindView(R.id.tv_accept)
    RadioButton tvAccept;
    @BindView(R.id.tv_care)
    RadioButton tvCare;
    @BindView(R.id.tv_task)
    RadioButton tvTask;
    @BindView(R.id.rg_type)
    RadioGroup rgType;
    @BindView(R.id.tv_type_sel)
    TextView tvTypeSel;
    @BindView(R.id.et_address)
    EditText etAddress;

    private String time, weather, city_address;

    WeatherSearchQuery query;
    WeatherSearch search;
    LocalWeatherLive weatherlive;
    private String project_name;
    private String region_name;
    private String project_content;
    private String project_type;
    private String address;
    private String creatUser;
    private int color;

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
        initView();
        initLocal();
    }

    /**
     * 初始化定位
     */
    private void initLocal() {
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(this);
        startLocation();
    }

    /**
     * 默认的定位参数
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(true);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyLocation();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startLocation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopLocation();
    }

    /**
     * 开始定位
     */
    private void startLocation() {
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 停止定位
     */
    private void stopLocation() {
        // 停止定位
        locationClient.stopLocation();
    }

    /**
     * 销毁定位
     */
    private void destroyLocation() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    private void initView() {
        setTitle("专业相机");
        setLeftBack();
        initGPS();
        rgColor.setOnCheckedChangeListener(this);
        rgType.setOnCheckedChangeListener(this);
        //项目名称
        project_name = etProjectName.getText().toString().trim();
        //部位名称
        region_name = etRegionName.getText().toString().trim();
        //项目内容
        project_content = etProjectConment.getText().toString().trim();
        //项目类型
        project_type = tvTypeSel.getText().toString().trim();

        //创建者
        creatUser = EanfangApplication.get().getUser().getName();

    }

    /**
     * 返回拍照页面
     */
    public void backTakePhoto(View v) {
        flCamera.setVisibility(View.GONE);
    }

    /**
     * 开始拍照
     */
    public void startTakePhoto(View v) {
        PermissionsModel permissionsModel = new PermissionsModel(this);
        permissionsModel.checkCameraPermission(isPermission -> {
            if (isPermission) {
                //如果没有添加属性
                if (!checkCameraData(true)) {
                    return;
                }
                Intent intent = new Intent(CameraActivity.this, TakePhotoActivity.class);
                startActivityForResult(intent, TakePhotoActivity.REQUEST_CAPTRUE_CODE);
            }
        });
    }

    /**
     * 查询天气
     */
    private void queryWeather(String address) {
        query = new WeatherSearchQuery(address
                , WeatherSearchQuery.WEATHER_TYPE_LIVE);
        search = new WeatherSearch(this);
        search.setOnWeatherSearchListener(new WeatherSearch.OnWeatherSearchListener() {
            @Override
            public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult, int rCode) {
                if (rCode == 1000) {
                    if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
                        weatherlive = weatherLiveResult.getLiveResult();
                        weather = weatherlive.getWeather()
                                + weatherlive.getTemperature() + "°\n"
                                + weatherlive.getWindDirection() + "风 \n "
                                + weatherlive.getWindPower() + "级";
                    }
                }
            }

            @Override
            public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {

            }
        });
        search.setQuery(query);
        search.searchWeatherAsyn(); //异步搜索
    }

    /**
     * 检查有没有输入属性
     */
    private boolean checkCameraData(boolean check) {
        if (check) {
            //项目名称
            project_name = etProjectName.getText().toString().trim();
            if (TextUtils.isEmpty(project_name)) {
                showToast("请输入项目名称");
                return false;
            }

            //部位名称
            region_name = etRegionName.getText().toString().trim();
            if (TextUtils.isEmpty(region_name)) {
                showToast("请输入部位名称/区域名称");
                return false;
            }

            //字体颜色
            if (color == 0) {
                showToast("请选择字体颜色");
                return false;
            }

            //项目类型
            project_type = tvTypeSel.getText().toString().trim();
            if (TextUtils.isEmpty(project_type)) {
                showToast("请选择项目类型");
                return false;
            }

            //项目内容
            project_content = etProjectConment.getText().toString().trim();
            if (TextUtils.isEmpty(project_content)) {
                showToast("请输入项目内容");
                return false;
            }
        }
        return true;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;//当回掉无数据时，保持app正常
        switch (requestCode) {
            case TakePhotoActivity.REQUEST_CAPTRUE_CODE:
                //往图片绘制文字
                time = GetDateUtils.dateToDateTimeString(GetDateUtils.getDateNow());
                String path = data.getStringExtra(TakePhotoActivity.RESULT_PHOTO_PATH);
                Bitmap waterBitmap = BitmapUtil.getBitmap(path);
                Bitmap watermarkBitmap = ImageUtil.createWaterMaskCenter(waterBitmap, waterBitmap);

                if (ConnectivityChangeReceiver.isNetConnected(this) == true) {
                    Bitmap textBitmap = ImageUtil.drawTextToRightBottom(this, watermarkBitmap, "地址：" + address, 16, color, 5, 8);
                    textBitmap = ImageUtil.drawTextToRightBottom(this, textBitmap, "内容：" + project_content, 16, color, 5, 28);
                    textBitmap = ImageUtil.drawTextToRightBottom(this, textBitmap, "部位/区域：" + region_name, 16, color, 5, 48);
                    textBitmap = ImageUtil.drawTextToRightBottom(this, textBitmap, "类型：" + project_type, 16, color, 5, 68);
                    textBitmap = ImageUtil.drawTextToRightBottom(this, textBitmap, "名称：" + project_name, 16, color, 5, 88);
                    textBitmap = ImageUtil.drawTextToRightBottom(this, textBitmap, "创建者:" + creatUser, 16, color, 5, 108);
                    textBitmap = ImageUtil.drawTextToRightBottom(this, textBitmap, "天气：" + weather, 16, color, 5, 128);
                    textBitmap = ImageUtil.drawTextToRightBottom(this, textBitmap, "时间：" + time, 16, color, 5, 148);
                    showTakePhotoImg.setImageBitmap(textBitmap);
                    flCamera.setVisibility(View.VISIBLE);
                    try {
                        //保存图片
                        BitmapUtil.saveImage(textBitmap, path);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    String address = etAddress.getText().toString().trim();
                    Bitmap textBitmap = ImageUtil.drawTextToRightBottom(this, watermarkBitmap, "地址：" + address, 16, color, 5, 8);
                    textBitmap = ImageUtil.drawTextToRightBottom(this, textBitmap, "内容：" + project_content, 16, color, 5, 28);
                    textBitmap = ImageUtil.drawTextToRightBottom(this, textBitmap, "部位/区域：" + region_name, 16, color, 5, 48);
                    textBitmap = ImageUtil.drawTextToRightBottom(this, textBitmap, "类型：" + project_type, 16, color, 5, 68);
                    textBitmap = ImageUtil.drawTextToRightBottom(this, textBitmap, "名称：" + project_name, 16, color, 5, 88);
                    textBitmap = ImageUtil.drawTextToRightBottom(this, textBitmap, "创建者:" + creatUser, 16, color, 5, 108);
                    textBitmap = ImageUtil.drawTextToRightBottom(this, textBitmap, "时间：" + time, 16, color, 5, 128);
                    showTakePhotoImg.setImageBitmap(textBitmap);
                    flCamera.setVisibility(View.VISIBLE);
                    try {
                        //保存图片
                        BitmapUtil.saveImage(textBitmap, path);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (null != amapLocation) {
            StringBuffer sb = new StringBuffer();
            sb.append(amapLocation.getProvince());//省信息
            sb.append(amapLocation.getCity());//城市信息
            sb.append(amapLocation.getDistrict());//城区信息
            sb.append(amapLocation.getStreet());//街道信息
            amapLocation.getStreetNum();//街道门牌号信息
            sb.append(amapLocation.getAoiName());//获取当前定位点的AOI信息
            sb.append(amapLocation.getBuildingId());//获取当前室内定位的建筑物Id
            sb.append(amapLocation.getFloor());//获取当前室内定位的楼层
            LogUtils.e("amapSuccess", sb.toString());

            address = sb.toString();
            //获取定位时间
//            time = GetDateUtils.dateToDateTimeString(GetDateUtils.getDate(amapLocation.getTime()));
            city_address = amapLocation.getCity();
            city_address.substring(0, 1);
            queryWeather(city_address);
        } else {
            LogUtils.e("amapfill", "定位失败");
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (group.getCheckedRadioButtonId()) {
            case R.id.color_white:
                color = Color.parseColor("#ffffff");
                break;
            case R.id.color_red:
                color = Color.parseColor("#ff0000");
                break;
            case R.id.tv_repair://维修
                tvTypeSel.setText("维修");
                break;
            case R.id.tv_check://检查
                tvTypeSel.setText("检查");
                break;
            case R.id.tv_task://任务
                tvTypeSel.setText("任务");
                break;
            case R.id.tv_do://施工
                tvTypeSel.setText("施工");
                break;
            case R.id.tv_accept://验收
                tvTypeSel.setText("验收");
                break;
            case R.id.tv_care://保养
                tvTypeSel.setText("保养");
                break;

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (flCamera.getVisibility() == View.VISIBLE) {
                flCamera.setVisibility(View.GONE);
            } else {
                finishSelf();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 初始化gps
     */

    private void initGPS() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // 判断GPS模块是否开启，如果没有则开启
        if (!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            ToastUtil.get().showToast(this, "请打开GPS,定位更准确");
        }
        if (ConnectivityChangeReceiver.isNetConnected(this) == false) {
            ToastUtil.get().showToast(this, "没有网络，请检查网络");
        }
    }
}


