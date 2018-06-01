package net.eanfang.client.ui.activity.worksapce;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.annimon.stream.Stream;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.LocationUtil;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.V;
import com.eanfang.util.ViewFindUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yaf.base.entity.RepairOrderEntity;
import com.yaf.base.entity.WorkerEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.ui.fragment.CollectionWorkerFragment;
import net.eanfang.client.ui.fragment.customservice.CompanyServiceFragment;
import net.eanfang.client.ui.fragment.customservice.PersonalServiceFragment;
import net.eanfang.client.ui.fragment.selectworker.AllWorkerFragment;
import net.eanfang.client.ui.fragment.selectworker.CollectWorkerFragment;
import net.eanfang.client.ui.fragment.selectworker.ServicedWorkerFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/20  15:04
 * @email houzhongzhou@yeah.net
 * @desc 技师列表
 */

public class SelectWorkerActivity extends BaseActivity implements OnTabSelectListener {

//    @BindView(R.id.tv_all)
//    TextView tv_all;
//    @BindView(R.id.tv_serviced)
//    TextView tv_serviced;
//    @BindView(R.id.tv_collection)
//    TextView tv_collection;

    private final String[] mTitles = {
            "附近技师", "收藏的技师", "服务的技师"
    };
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;

    boolean isDestroy = false;
    private RepairOrderEntity toRepairBean;
    private List<WorkerEntity> selectWorkerList = new ArrayList<>();
    private ArrayList<String> businessIds = new ArrayList<>();

    private int mDoorFee;
    // 添加海量点时
    //private ProgressDialog progDialog = null;
    //@BindView(R.id.aMapview)
    //MapView aMapview;
    //private LocationUtil locationUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_worker);
        ButterKnife.bind(this);
//        aMapview.onCreate(savedInstanceState);
//        locationUtil = LocationUtil.get(this, aMapview);
        initView();
//        runOnUiThread(() -> {
//            PermissionUtils.get(this).getLocationPermission(() -> {
//                        locationUtil.startOnce();
//                    }
//            );
//        });
//        initWorker(0, 0);
        setListener();

    }

    private void initView() {
        setLeftBack();
        setTitle("选择技师");

        toRepairBean = (RepairOrderEntity) getIntent().getSerializableExtra("bean");
        businessIds = getIntent().getStringArrayListExtra("businessIds");
        mDoorFee = getIntent().getIntExtra("doorFee",0);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图

        mFragments.add(AllWorkerFragment.getInstance(toRepairBean, businessIds,mDoorFee));
        mFragments.add(CollectWorkerFragment.getInstance(toRepairBean, businessIds,mDoorFee));
        mFragments.add(ServicedWorkerFragment.getInstance(toRepairBean, businessIds,mDoorFee));
        View decorView = getWindow().getDecorView();
        ViewPager vp = ViewFindUtils.find(decorView, R.id.vp_selectWork);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);
        /**自定义部分属性*/
        SlidingTabLayout tabLayout_2 = ViewFindUtils.find(decorView, R.id.slidingtablayout);
        tabLayout_2.setViewPager(vp, mTitles, this, mFragments);
        tabLayout_2.setOnTabSelectListener(this);
        vp.setCurrentItem(0);
    }

    private void setListener() {
//        tv_all.setOnClickListener(v -> initWorker(0, 0));
//        tv_serviced.setOnClickListener((v) -> {
//            initWorker(1, 0);
//        });
//        tv_collection.setOnClickListener(v -> initWorker(0, 1));


//        locationUtil.mAMap.setOnMarkerClickListener((marker) -> {
//            if (marker.getObject() != null) {
//                JSONObject jsonObject = (JSONObject) marker.getObject();
//                lookWorkerDetail(V.v(() -> jsonObject.getString("companyUserId")), V.v(() -> jsonObject.getString("workerId")));
//            }
//            return true;
//        });
    }


//    //加载技师
//    private void initWorker(int serviceId, int collectId) {
//        QueryEntry queryEntry = new QueryEntry();
//        queryEntry.getEquals().put("regionCode", toRepairBean.getPlaceCode());
//        queryEntry.getIsIn().put("serviceId", Arrays.asList(Config.get().getBaseIdByCode("2.1", 1, Constant.BIZ_TYPE) + ""));
//        queryEntry.getIsIn().put("businessId", Stream.of(businessIds).distinct().toList());
//        queryEntry.getEquals().put("served", serviceId + "");
//        queryEntry.getEquals().put("collect", collectId + "");
//        queryEntry.getEquals().put("userId", EanfangApplication.getApplication().getUserId() + "");
//        EanfangHttp.post(RepairApi.GET_REPAIR_SEARCH)
//                .upJson(JsonUtils.obj2String(queryEntry))
//                .execute(new EanfangCallback<WorkerEntity>(this, true, WorkerEntity.class, true, (list) -> {
//                    selectWorkerList = list;
//
////                    initMarker();
//                }));
//    }
//
//
//    //查看技师详情
//    private void lookWorkerDetail(String companyUserId, String workerId) {
//        Intent intent = new Intent(SelectWorkerActivity.this, WorkerDetailActivity.class);
//        intent.putExtra("toRepairBean", toRepairBean);
//        intent.putExtra("companyUserId", companyUserId);
//        intent.putExtra("workerId", workerId);
//        startActivity(intent);
//    }

    /**
     * 滑动复写方法
     */
    @Override
    public void onTabSelect(int position) {

    }

    @Override
    public void onTabReselect(int position) {

    }


    /**
     * viewpager Adapter
     */
    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
//        aMapview.onDestroy();
        isDestroy = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
//        aMapview.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
//        aMapview.onPause();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        aMapview.onSaveInstanceState(outState);
    }

//    //绘制海量点
//    private void initMarker() {
////        locationUtil.mAMap.clear();
////        for (int i = 0; i < selectWorkerList.size(); i++) {
////            //保证经纬度没有问题的时候可以填false
////            Double lat = Double.parseDouble(selectWorkerList.get(i).getLat()) + new Random().nextDouble() / 100;
////            Double lon = Double.parseDouble(selectWorkerList.get(i).getLon()) + new Random().nextDouble() / 100;
////            selectWorkerList.get(i).getId();
////            selectWorkerList.get(i).getCompanyUserId();
////            LatLng latLng = new LatLng(lat, lon, false);
////            //创建MultiPointItem存放，海量点中某单个点的位置及其他信息
////            String avatar = BuildConfig.OSS_SERVER + selectWorkerList.get(i).getAccountEntity().getAvatar();
////            String realName = selectWorkerList.get(i).getAccountEntity().getRealName();
////            String companyName = selectWorkerList.get(i).getCompanyEntity().getOrgName();
////            JSONObject jsonObject = new JSONObject();
////            jsonObject.put("companyUserId", selectWorkerList.get(i).getCompanyUserId());
////            jsonObject.put("workerId", selectWorkerList.get(i).getId());
////            locationUtil.addMarket(latLng, avatar, realName, companyName, jsonObject);
////        }
//    }

}
