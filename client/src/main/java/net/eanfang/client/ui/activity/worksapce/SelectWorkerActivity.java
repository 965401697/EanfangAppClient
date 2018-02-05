package net.eanfang.client.ui.activity.worksapce;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.annimon.stream.Stream;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.LocationUtil;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.QueryEntry;
import com.yaf.base.entity.RepairOrderEntity;
import com.yaf.base.entity.WorkerEntity;

import net.eanfang.client.BuildConfig;
import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/20  15:04
 * @email houzhongzhou@yeah.net
 * @desc 技师列表
 */

public class SelectWorkerActivity extends BaseClientActivity {

    Activity activity = this;

    @BindView(R.id.aMapview)
    MapView aMapview;
    @BindView(R.id.tv_all)
    TextView tv_all;
    @BindView(R.id.tv_serviced)
    TextView tv_serviced;
    @BindView(R.id.tv_collection)
    TextView tv_collection;
    boolean isDestroy = false;
    private RepairOrderEntity toRepairBean;
    private List<WorkerEntity> selectWorkerList;
    // 添加海量点时
    private ProgressDialog progDialog = null;
    private List<String> businessId;

    private LocationUtil locationUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_worker);
        ButterKnife.bind(this);
        aMapview.onCreate(savedInstanceState);
        locationUtil = LocationUtil.get(this, aMapview);

        PermissionUtils.get(this).getLocationPermission(() -> locationUtil.startOnce());
        initView();
        initWorker(0, 0);
        setListener();

    }

    private void setListener() {
        tv_all.setOnClickListener(v -> initWorker(0, 0));
        tv_serviced.setOnClickListener((v) -> {
            initWorker(1, 0);
        });
        tv_collection.setOnClickListener(v -> initWorker(0, 1));


        locationUtil.mAMap.setOnMarkerClickListener((marker) -> {
            if (marker.getObject() != null) {
                JSONObject jsonObject = (JSONObject) marker.getObject();
                lookWorkerDetail(jsonObject.getString("companyUserId"), jsonObject.getString("workerId"));
            }
            return true;
        });
        locationUtil.mAMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                LinearLayout lineLayout = new LinearLayout(activity);
                TextView title = new TextView(activity);
                title.setTextSize(18);
                TextView info = new TextView(activity);
                title.setTextSize(16);

                lineLayout.addView(title);
                lineLayout.addView(info);
                return lineLayout;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });

        locationUtil.mAMap.setOnInfoWindowClickListener((marker) -> {
            if (marker.getObject() != null) {
                JSONObject jsonObject = (JSONObject) marker.getObject();
                lookWorkerDetail(jsonObject.getString("companyUserId"), jsonObject.getString("workerId"));
            }
        });

    }


    //加载技师
    private void initWorker(int serviceId, int collectId) {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("regionCode", toRepairBean.getPlaceCode());
        queryEntry.getIsIn().put("serviceId", Arrays.asList(Config.get().getBaseIdByCode("2.1", Constant.BIZ_TYPE)));
        queryEntry.getIsIn().put("businessId", businessId);
        queryEntry.getEquals().put("served", serviceId + "");
        queryEntry.getEquals().put("collect", collectId + "");
        queryEntry.getEquals().put("userId", EanfangApplication.getApplication().getUserId() + "");
        EanfangHttp.post(RepairApi.GET_REPAIR_SEARCH)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<WorkerEntity>(this, true, WorkerEntity.class, true, (list) -> {
                    selectWorkerList = list;
                    initMarker();
                }));
    }


    private void initView() {
        setLeftBack();
        setTitle("选择技师");
        toRepairBean = (RepairOrderEntity) getIntent().getSerializableExtra("bean");
        businessId = getIntent().getStringArrayListExtra("businessId");
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图

    }

    //绘制海量点
    private void initMarker() {

        for (int i = 0; i < selectWorkerList.size(); i++) {
            //保证经纬度没有问题的时候可以填false
            Double lat = Double.parseDouble(selectWorkerList.get(i).getLat());
            Double lon = Double.parseDouble(selectWorkerList.get(i).getLon());
            Double finalLat = lat;
            Double finalLon = lon;
            long existsCount = Stream.of(selectWorkerList).filter(worker -> worker.getLat().equals(finalLat.toString()) && worker.getLon().equals(finalLon.toString())).count();
            if (existsCount > 0) {
                lat += 0.001;
                lon += 0.001;
            }
            selectWorkerList.get(i).getId();
            selectWorkerList.get(i).getCompanyUserId();
            LatLng latLng = new LatLng(lat, lon, false);
            //创建MultiPointItem存放，海量点中某单个点的位置及其他信息
            String avatar = BuildConfig.OSS_SERVER + selectWorkerList.get(i).getAccountEntity().getAvatar();
            String realName = selectWorkerList.get(i).getAccountEntity().getRealName();
            String companyName = selectWorkerList.get(i).getCompanyEntity().getOrgName();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("companyUserId", selectWorkerList.get(i).getCompanyUserId());
            jsonObject.put("workerId", selectWorkerList.get(i).getId());
            locationUtil.addMarket(latLng, avatar, realName, companyName, jsonObject);
        }
    }

    //查看技师详情
    private void lookWorkerDetail(String companyUserId, String workerId) {
        Intent intent = new Intent(SelectWorkerActivity.this, WorkerDetailActivity.class);
        intent.putExtra("toRepairBean", toRepairBean);
        intent.putExtra("companyUserId", companyUserId);
        intent.putExtra("workerId", workerId);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        aMapview.onDestroy();
        isDestroy = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        aMapview.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        aMapview.onPause();

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        aMapview.onSaveInstanceState(outState);
    }

//    /**
//     * 显示进度框
//     */
//    private void showProgressDialog() {
//        if (progDialog == null) {
//            progDialog = new ProgressDialog(this);
//        }
//        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progDialog.setIndeterminate(false);
//        progDialog.setCancelable(true);
//        progDialog.setMessage("努力查找技师中，请稍后...");
//        progDialog.show();
//    }
//
//    /**
//     * 隐藏进度框
//     */
//    private void dissmissProgressDialog() {
//        if (progDialog != null) {
//            progDialog.dismiss();
//        }
//    }

}
