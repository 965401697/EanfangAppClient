package net.eanfang.client.ui.activity.worksapce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MultiPointItem;
import com.amap.api.maps.model.MultiPointOverlay;
import com.amap.api.maps.model.MultiPointOverlayOptions;

import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.config.Config;
import net.eanfang.client.network.apiservice.RepairApi;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.model.SelectWorkerBean;
import net.eanfang.client.ui.model.repair.RepairOrderEntity;
import net.eanfang.client.util.JsonUtils;
import net.eanfang.client.util.QueryEntry;

import java.util.ArrayList;
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

public class SelectWorkerActivity extends BaseActivity {


    @BindView(R.id.aMapview)
    MapView aMapview;
    @BindView(R.id.tv_all)
    TextView tv_all;
    @BindView(R.id.tv_serviced)
    TextView tv_serviced;
    @BindView(R.id.tv_collection)
    TextView tv_collection;
    private RepairOrderEntity toRepairBean;
    private List<SelectWorkerBean> selectWorkerList;
    private SelectWorkerBean selectWorkerBean;
    private AMap aMap;
    // 添加海量点时
    private ProgressDialog progDialog = null;
    boolean isDestroy = false;

    private List<String> businessId;
    private Long workerId,userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_worker);
        ButterKnife.bind(this);
        initView(savedInstanceState);
        initWorker("");

    }


    //加载技师
    private void initWorker(String serOrcolId) {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("regionCode", toRepairBean.getPlaceCode());
        queryEntry.getEquals().put("serviceId", Config.getConfig().getServId("2.1"));
        queryEntry.getIsIn().put("businessId", businessId);
        queryEntry.getEquals().put("served", serOrcolId);
        queryEntry.getEquals().put("collect", serOrcolId);
        queryEntry.getEquals().put("userId", EanfangApplication.getApplication().getUserId() + "");
        EanfangHttp.post(RepairApi.GET_REPAIR_SEARCH)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<SelectWorkerBean>(this, true, SelectWorkerBean.class, true, (list) -> {
                    selectWorkerList = list;
                    initMarker();
                }));
    }


    private void initView(Bundle savedInstanceState) {
        setLeftBack();
        setTitle("选择技师");
        toRepairBean = (RepairOrderEntity) getIntent().getSerializableExtra("bean");
        businessId = getIntent().getStringArrayListExtra("businessId");
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        aMapview.onCreate(savedInstanceState);
        //初始化地图控制器对象

        if (aMap == null) {
            aMap = aMapview.getMap();
        }
    }

    //绘制海量点
    private void initMarker() {
        //添加一个Marker用来展示海量点点击效果
        final Marker marker = aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_workers);

        MultiPointOverlayOptions overlayOptions = new MultiPointOverlayOptions();
        overlayOptions.icon(bitmapDescriptor);
        overlayOptions.anchor(0.5f, 0.5f);

        final MultiPointOverlay multiPointOverlay = aMap.addMultiPointOverlay(overlayOptions);

//        aMap.moveCamera(CameraUpdateFactory.zoomTo(3));

        showProgressDialog();
        List<MultiPointItem> list = new ArrayList<MultiPointItem>();

        for (int i = 0; i <selectWorkerList.size();i++ ){
                selectWorkerBean=selectWorkerList.get(i);
                //保证经纬度没有问题的时候可以填false
                Double lat = Double.parseDouble(selectWorkerList.get(i).getLat());
                Double lon = Double.parseDouble(selectWorkerList.get(i).getLon());
                workerId=selectWorkerList.get(i).getId();
                userId=selectWorkerList.get(i).getVerifyEntity().getUserId();
                LatLng latLng = new LatLng(lat, lon, false);
                //创建MultiPointItem存放，海量点中某单个点的位置及其他信息
                MultiPointItem multiPointItem = new MultiPointItem(latLng);
                list.add(multiPointItem);
                dissmissProgressDialog();
            }
        //将规范化的点集交给海量点管理对象设置，待加载完毕即可看到海量点信息
        multiPointOverlay.setItems(list);

        aMap.setOnMultiPointClickListener(new AMap.OnMultiPointClickListener() {
            @Override
            public boolean onPointClick(MultiPointItem pointItem) {
                //添加一个Marker用来展示海量点点击效果
                marker.setPosition(pointItem.getLatLng());
                marker.setToTop();
                //点击头像的事件
                lookWorkerDetail();
                return false;
            }
        });
//        //开启子线程读取文件
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                List<MultiPointItem> list = new ArrayList<MultiPointItem>();
//                String styleName = "style_json.json";
//                FileOutputStream outputStream = null;
//                InputStream inputStream = null;
//                String filePath = null;
//                try {
//                    inputStream = SelectWorkerActivity.this.getResources().openRawResource(R.raw.point10w);
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                    String line = "";
//                    while((line = bufferedReader.readLine()) != null) {
//                        if(isDestroy) {
//                            //已经销毁地图了，退出循环
//                            return;
//                        }
//
//                        String[] str = line.split(",");
//                        if(str == null) {
//                            continue;
//                        }
//                        double lat = Double.parseDouble(str[1].trim());
//                        double lon = Double.parseDouble(str[0].trim());
//
//                        LatLng latLng = new LatLng(lat, lon, false);//保证经纬度没有问题的时候可以填false
//
//                        MultiPointItem multiPointItem = new MultiPointItem(latLng);
//                        list.add(multiPointItem);
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    try {
//                        if (inputStream != null) {
//                            inputStream.close();
//                        }
//
//                        if (outputStream != null) {
//                            outputStream.close();
//                        }
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                if(multiPointOverlay != null) {
//                    multiPointOverlay.setItems(list);
//                    multiPointOverlay.setEnable(true);
//                }
//
//                dissmissProgressDialog();
//
//                //
//            }
//        }).start();
    }
    //查看技师详情
    private void lookWorkerDetail() {
        // TODO: 2018/1/2 报修的
        Intent intent=new Intent(SelectWorkerActivity.this,WorkerDetailActivity.class);
        intent.putExtra("toRepairBean",toRepairBean);
        intent.putExtra("selectBean",selectWorkerBean);
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

    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (progDialog == null) {
            progDialog = new ProgressDialog(this);
        }
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(true);
        progDialog.setMessage("努力查找技师中，请稍后...");
        progDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }
}
