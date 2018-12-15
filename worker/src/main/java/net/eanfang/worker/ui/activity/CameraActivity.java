package net.eanfang.worker.ui.activity;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.camera.util.BitmapUtil;
import com.camera.util.ImageUtil;
import com.camera.view.TakePhotoActivity;
import com.eanfang.application.EanfangApplication;
import com.eanfang.model.CameraBean;
import com.eanfang.model.SelectAddressItem;
import com.eanfang.ui.activity.SelectAddressActivity;
import com.eanfang.ui.base.voice.RecognitionManager;
import com.eanfang.util.ConnectivityChangeReceiver;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.SharePreferenceUtil;
import com.eanfang.util.StringUtils;
import com.eanfang.util.ToastUtil;
import com.eanfang.util.V;
import com.eanfang.witget.CustomRadioGroup;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Mr.hou
 *
 * @on 2017/9/26  15:12
 * @email houzhongzhou@yeah.net
 * @desc 相机助手
 */

public class CameraActivity extends BaseWorkerActivity implements AMapLocationListener, RadioGroup.OnCheckedChangeListener {
    //选择其他地址回调 code
    private final int REPAIR_ADDRESS_CALLBACK_CODE = 1;
    @BindView(R.id.et_project_name)
    EditText etProjectName;
    @BindView(R.id.et_region_name)
    EditText etRegionName;
    @BindView(R.id.et_project_conment)
    EditText etProjectConment;
    @BindView(R.id.showTakePhotoImg)
    ImageView showTakePhotoImg;
    @BindView(R.id.fl_camera)
    LinearLayout flCamera;
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
    CustomRadioGroup rgType;
    @BindView(R.id.et_address)
    EditText etAddress;
    WeatherSearchQuery query;
    WeatherSearch search;
    LocalWeatherLive weatherlive;
    @BindView(R.id.tv_location_address)
    TextView tvLocationAddress;
    private CameraBean cameraBean;
    private String time, weather, city_address;
    private String project_name;
    private String region_name;
    private String project_content;
    private String project_type;
    private String address;
    private String creatUser;
    private int color = Color.parseColor("#ffffff");
    //项目类型
    private String selectProjectType = "维修";
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
        initView();
        initLocal();
        getData();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        startLocation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyLocation();
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

    /**
     * 停止定位
     */
    private void stopLocation() {
        // 停止定位
        locationClient.stopLocation();
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
        project_type = selectProjectType;


        //创建者
        creatUser = V.v(() -> EanfangApplication.get().getUser().getAccount().getRealName());
        if (StringUtils.isEmpty(creatUser)) {
            creatUser = "--";
        }

        if (ConnectivityChangeReceiver.isNetConnected(this) == true) {
            etAddress.setVisibility(View.GONE);
            tvLocationAddress.setVisibility(View.VISIBLE);
        } else {
            etAddress.setVisibility(View.VISIBLE);
            tvLocationAddress.setVisibility(View.GONE);
        }
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
     * 取出本地数据
     */
    private void getData() {
        try {
            if (SharePreferenceUtil.get().get(CameraBean.class.getName(), CameraBean.class) != null) {
                cameraBean = (CameraBean) SharePreferenceUtil.get().get(CameraBean.class.getName(), CameraBean.class);

                etProjectName.setText(cameraBean.getProjectName());
                etProjectConment.setText(cameraBean.getProjectContent());
                etRegionName.setText(cameraBean.getLocalPosition());
                etAddress.setText(cameraBean.getLocalAddress());
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        if (ConnectivityChangeReceiver.isNetConnected(this) == false) {
            ToastUtil.get().showToast(this, "没有网络，请检查网络");
        }
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
     * 返回拍照页面
     */
    public void backTakePhoto(View v) {
        flCamera.setVisibility(View.GONE);
    }

    /**
     * 开始拍照
     */
    public void startTakePhoto(View v) {
        PermissionUtils.get(this).getCameraPermission(() -> {
            if (!checkCameraData(true)) {
                return;
            }
            setData();
            Intent intent = new Intent(CameraActivity.this, TakePhotoActivity.class);
            startActivityForResult(intent, TakePhotoActivity.REQUEST_CAPTRUE_CODE);
        });
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
            project_type = selectProjectType;
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

    private void setData() {
        try {
            cameraBean = new CameraBean();
            cameraBean.setLocalPosition(V.v(() -> etRegionName.getText().toString()));
            cameraBean.setNetAddress(V.v(() -> tvLocationAddress.getText().toString()));
            cameraBean.setProjectName(V.v(() -> etProjectName.getText().toString()));
            cameraBean.setProjectContent(V.v(() -> etProjectConment.getText().toString()));
            cameraBean.setLocalAddress(V.v(() -> etAddress.getText().toString()));
            SharePreferenceUtil.get().set(CameraBean.class.getName(), cameraBean);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 选择其他地址
     */
    public void selectOtherAddress(View v) {
        Intent intent = new Intent(this, SelectAddressActivity.class);
        startActivityForResult(intent, REPAIR_ADDRESS_CALLBACK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;//当回掉无数据时，保持app正常
        }
        switch (requestCode) {
            case TakePhotoActivity.REQUEST_CAPTRUE_CODE:
                //往图片绘制文字
                time = GetDateUtils.dateToDateTimeString(GetDateUtils.getDateNow());
                String path = data.getStringExtra(TakePhotoActivity.RESULT_PHOTO_PATH);
                Bitmap waterBitmap = BitmapUtil.getBitmap(path);
                Bitmap watermarkBitmap = ImageUtil.createWaterMaskCenter(waterBitmap, waterBitmap);

                if (ConnectivityChangeReceiver.isNetConnected(this) == true) {
                    String netAddress = tvLocationAddress.getText().toString();
                    drawBitmap(path, watermarkBitmap, netAddress, time);
                } else {
                    String address = etAddress.getText().toString().trim();
                    drawBitmap(path, watermarkBitmap, address, time);
                }
                break;
            case REPAIR_ADDRESS_CALLBACK_CODE:
                locationClient.stopLocation();
                SelectAddressItem item = (SelectAddressItem) data.getSerializableExtra("data");
                address = item.getCity() + item.getAddress() + item.getName();
                //将选择的地址 取 显示值
                tvLocationAddress.setText(address);

                break;
            default:
                break;
        }
    }

    /**
     * 绘制水印
     */
    private void drawBitmap(String path, Bitmap watermarkBitmap, String lAddress, String time) {
        String mContent = "时间：" + time + "\n" + "天气：" + weather + "\n" + "创建者:" +
                creatUser + "\n" + "类型：" + project_type + "\n" + "名称：" + project_name + "\n" + "部位/区域：" + region_name + "\n" + "内容：" + project_content + "\n" +
                "地址：" + lAddress;
        Bitmap textBitmap = ImageUtil.drawTextToRightBottom(this, watermarkBitmap, mContent, 16, color, 5, 180);
        showTakePhotoImg.setImageBitmap(textBitmap);
        flCamera.setVisibility(View.VISIBLE);
        try {
            //保存图片
            BitmapUtil.saveImage(textBitmap, path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
//            LogUtils.e("amapSuccess", sb.toString());

            address = sb.toString();
            tvLocationAddress.setText(address);
            //获取定位时间
//            time = GetDateUtils.dateToDateTimeString(GetDateUtils.getDate(amapLocation.getTime()));
            city_address = amapLocation.getCity();
            city_address.substring(0, 1);
            queryWeather(city_address);
        } else {
//            LogUtils.e("amapfill", "定位失败");
        }
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
                                + weatherlive.getTemperature() + "°"
                                + weatherlive.getWindDirection() + "风 "
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
                selectProjectType = "维修";
                break;
            case R.id.tv_check://检查
                selectProjectType = "检查";
                break;
            case R.id.tv_task://任务
                selectProjectType = "任务";
                break;
            case R.id.tv_do://施工
                selectProjectType = "施工";
                break;
            case R.id.tv_accept://验收
                selectProjectType = "验收";
                break;
            case R.id.tv_care://保养
                selectProjectType = "保养";
                break;
            default:
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

    @OnClick({R.id.iv_project_voice, R.id.iv_area_voice, R.id.iv_content_voice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_project_voice:
                inputVoice(etProjectName);
                break;
            case R.id.iv_area_voice:
                inputVoice(etRegionName);
                break;
            case R.id.iv_content_voice:
                inputVoice(etProjectConment);
                break;
        }
    }

    private void inputVoice(EditText editText) {
        PermissionUtils.get(this).getVoicePermission(() -> {
            RecognitionManager.getSingleton().startRecognitionWithDialog(CameraActivity.this, editText);
        });
    }
}


