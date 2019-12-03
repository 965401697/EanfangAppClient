package net.eanfang.worker.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModel;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.BaseFragment;
import com.eanfang.biz.model.bean.AllMessageBean;
import com.eanfang.biz.model.entity.HomeNewsBean;
import com.eanfang.biz.model.entity.HomeToDoOrderBean;
import com.eanfang.biz.model.entity.OrgEntity;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.config.Config;
import com.eanfang.config.EanfangConst;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.CallUtils;
import com.eanfang.util.DateKit;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermKit;
import com.eanfang.witget.HomeScanPopWindow;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.databinding.FragmentHomeNewBinding;
import net.eanfang.worker.ui.activity.CameraActivity;
import net.eanfang.worker.ui.activity.NewOrderActivity;
import net.eanfang.worker.ui.activity.WorkspaceActivity;
import net.eanfang.worker.ui.activity.worksapce.OfferAndPayOrderParentActivity;
import net.eanfang.worker.ui.activity.worksapce.oa.workreport.WorkReportListActivity;
import net.eanfang.worker.ui.activity.worksapce.online.ExpertOnlineActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.RepairCtrlActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.SolveModeActivity;
import net.eanfang.worker.ui.activity.worksapce.scancode.ScanCodeActivity;
import net.eanfang.worker.ui.widget.CompanyListView;
import net.eanfang.worker.ui.widget.SignCtrlView;
import net.eanfang.worker.viewmodle.tender.TenderViewModle;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.date.DateUtil;
import q.rorbin.badgeview.QBadgeView;

import static com.eanfang.base.kit.V.v;


/**
 * Created by MrHou
 *
 * @on 2017/11/10  15:07
 * @email houzhongzhou@yeah.net
 * @desc 首页
 */

public class HomeNewFragment extends BaseFragment implements AMapLocationListener {

    private FragmentHomeNewBinding fragmentHomeNewBinding;
    //头部标题

    // 扫码Popwindow
    private HomeScanPopWindow homeScanPopWindow;

    private Long mOrderId;
    private String mOrderPhone;
    /**
     * 图标数量
     */
    private QBadgeView qBadgeViewQuota = new QBadgeView(WorkerApplication.get().getApplicationContext());
    private int mQuota = 0;

    /**
     * 切换公司 pop
     */
    private CompanyListView selectCompanyPop;
    List<OrgEntity> mList = new ArrayList<>();
    private RotateAnimation rotate;


    private MyPagerAdapter mAdapter;
    private String[] mTitles = {"标讯", "找活", "安防圈"};

    private TenderViewModle mTenderViewModle;

    private WeatherSearchQuery query;
    private WeatherSearch search;
    private LocalWeatherLive weatherlive;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;

    @Override
    protected ViewModel initViewModel() {
        mTenderViewModle = LViewModelProviders.of(getActivity(), TenderViewModle.class);
        return mTenderViewModle;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        fragmentHomeNewBinding = FragmentHomeNewBinding.inflate(getLayoutInflater());
        homeScanPopWindow = new HomeScanPopWindow(getActivity(), true, scanSelectItemsOnClick);
        homeScanPopWindow.setOnDismissListener(() -> homeScanPopWindow.backgroundAlpha(1.0f));
        initLocal();
        initIconClick();
        initLoopView();
        doHttpNews();
        initNum();
        initViewPager();

        fragmentHomeNewBinding.ivCamera.setOnClickListener(v -> startActivity(new Intent(getActivity(), CameraActivity.class)));
        return fragmentHomeNewBinding.getRoot();
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (null != amapLocation) {
            StringBuffer sb = new StringBuffer();
            //省信息
            sb.append(amapLocation.getProvince());
            //城市信息
            sb.append(amapLocation.getCity());
            //城区信息
            sb.append(amapLocation.getDistrict());
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
            fragmentHomeNewBinding.tvAddress.setText(amapLocation.getCity().substring(0, 2));
            queryWeather(amapLocation.getCity().substring(0, 2));
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
        search = new WeatherSearch(getActivity());
        search.setOnWeatherSearchListener(new WeatherSearch.OnWeatherSearchListener() {
            @Override
            public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult, int rCode) {
                if (rCode == 1000) {
                    if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
                        weatherlive = weatherLiveResult.getLiveResult();
                        fragmentHomeNewBinding.tvWeather.setText(weatherlive.getWeather() + " " + weatherlive.getTemperature() + "°   " + weatherlive.getWindDirection() + "风   " + weatherlive.getWindPower() + "级");
                        locationClient.stopLocation();
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
     * 切换公司
     */
    private void doChangeCompany() {

        EanfangHttp.post(NewApiService.GET_COMPANY_ALL_LIST)
                .params("accId", WorkerApplication.get().getLoginBean().getAccount().getDefaultUser().getAccId() + "")
                // 公司类型（单位类型0平台总公司1城市平台公司2企事业单位3安防公司）
                .params("orgType", "3")
                .execute(new EanfangCallback<OrgEntity>(getActivity(), false, OrgEntity.class, true, bean -> {
                    mList = bean;
                    if (mList == null || mList.size() <= 0) {
                        showToast("暂无安防公司");
                        return;
                    }
                    rotate = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF,
                            0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotate.setDuration(300);
                    rotate.setFillAfter(true);
                    selectCompanyPop = new CompanyListView(getActivity(), mList, ((name, url) -> {
                        if ("个人".equals(name)) {
                            fragmentHomeNewBinding.tvHomeTitle.setText(name);
                        } else {
                            fragmentHomeNewBinding.tvHomeTitle.setText(name);
                        }
                        selectCompanyPop.dismiss();
                        doHttpOrderNums();
                    }));
                    selectCompanyPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            selectCompanyPop.backgroundAlpha(1.0f);
                        }
                    });
                    selectCompanyPop.showAsDropDown(fragmentHomeNewBinding.tvHomeTitle);
                }));
    }

    private void initNum() {
        /**
         * 报价
         * */
        qBadgeViewQuota.bindTarget(fragmentHomeNewBinding.tvInsidePrice)
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(5, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(11, 0, true)
                .setBadgeTextSize(10, true);
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentHomeNewBinding.llOne.setFocusable(true);
        fragmentHomeNewBinding.llOne.setFocusableInTouchMode(true);
        fragmentHomeNewBinding.llOne.requestFocus();
        String orgName = v(() -> (WorkerApplication.get().getLoginBean().getAccount().getDefaultUser().getCompanyEntity().getOrgName()));
        if (("个人").equals(orgName)) {
            fragmentHomeNewBinding.tvHomeTitle.setText("易安防");
        } else {
            fragmentHomeNewBinding.tvHomeTitle.setText(orgName);
        }
        doGetToDoOrder();
        doHttpOrderNums();
    }

    /**
     * 新订单
     */
    private void doGetToDoOrder() {
        EanfangHttp.get(NewApiService.GET_HOME_TO_DO_ORDER).execute(new EanfangCallback<HomeToDoOrderBean>(getActivity(), false, HomeToDoOrderBean.class, (bean -> {
            if (bean != null) {
                fragmentHomeNewBinding.tvToDoOrderTitle.setText(bean.getMsg());
                fragmentHomeNewBinding.tvOrderAddress.setText(Config.get().getAddressByCode(bean.getOrder().getPlaceCode()) + bean.getOrder().getAddress());
                fragmentHomeNewBinding.tvOrderCompany.setText(bean.getOrder().getContactCompany());
                fragmentHomeNewBinding.tvOrderTime.setText(DateUtil.date(bean.getOrder().getCreateTime()).toString());
                mOrderId = bean.getOrder().getId();
                mOrderPhone = bean.getOrder().getContactPhone();
                fragmentHomeNewBinding.llToDoOrder.setVisibility(View.VISIBLE);
            } else {
                fragmentHomeNewBinding.llToDoOrder.setVisibility(View.GONE);
            }
        })));

    }

    private void initViewPager() {
        ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.clear();
        fragments.add(HomeTenderFragment.newInstance(mTenderViewModle));
        fragments.add(HomeFindFragment.getInstance(mTenderViewModle));
        fragments.add(HomeSecurityFragment.getInstance(true));

        mAdapter = new MyPagerAdapter(mTitles, fragments);
        fragmentHomeNewBinding.vpHomeBusiness.setAdapter(mAdapter);
        // 设置不可滑动
        fragmentHomeNewBinding.vpHomeBusiness.setScanScroll(false);
        fragmentHomeNewBinding.vpHomeBusiness.setCurrentItem(0);
    }

    /**
     * 工作按钮
     */
    private void initIconClick() {
        // 标讯
        fragmentHomeNewBinding.llTender.setOnClickListener((v) -> {
            fragmentHomeNewBinding.tvTender.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_new_order_back));
            fragmentHomeNewBinding.tvFind.setTextColor(ContextCompat.getColor(getActivity(), R.color.roll_title));
            fragmentHomeNewBinding.tvSecurity.setTextColor(ContextCompat.getColor(getActivity(), R.color.roll_title));

            fragmentHomeNewBinding.viewTender.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryW));
            fragmentHomeNewBinding.viewFind.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_white));
            fragmentHomeNewBinding.viewSecurity.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_white));

            fragmentHomeNewBinding.ivTender.setImageResource(R.mipmap.ic_home_tender_pressed);
            fragmentHomeNewBinding.ivFind.setImageResource(R.mipmap.ic_home_find);
            fragmentHomeNewBinding.ivSecurity.setImageResource(R.mipmap.ic_home_security);

            fragmentHomeNewBinding.vpHomeBusiness.setCurrentItem(0);
        });
        // 找活
        fragmentHomeNewBinding.llFind.setOnClickListener((v) -> {
            fragmentHomeNewBinding.tvTender.setTextColor(ContextCompat.getColor(getActivity(), R.color.roll_title));
            fragmentHomeNewBinding.tvFind.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_new_order_back));
            fragmentHomeNewBinding.tvSecurity.setTextColor(ContextCompat.getColor(getActivity(), R.color.roll_title));

            fragmentHomeNewBinding.viewTender.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_white));
            fragmentHomeNewBinding.viewFind.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryW));
            fragmentHomeNewBinding.viewSecurity.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_white));

            fragmentHomeNewBinding.ivTender.setImageResource(R.mipmap.ic_home_tender);
            fragmentHomeNewBinding.ivFind.setImageResource(R.mipmap.ic_home_find_pressed);
            fragmentHomeNewBinding.ivSecurity.setImageResource(R.mipmap.ic_home_security);

            fragmentHomeNewBinding.vpHomeBusiness.setCurrentItem(1);

        });
        // 安防圈
        fragmentHomeNewBinding.llSecurity.setOnClickListener((v) -> {
            fragmentHomeNewBinding.tvTender.setTextColor(ContextCompat.getColor(getActivity(), R.color.roll_title));
            fragmentHomeNewBinding.tvFind.setTextColor(ContextCompat.getColor(getActivity(), R.color.roll_title));
            fragmentHomeNewBinding.tvSecurity.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_new_order_back));


            fragmentHomeNewBinding.viewTender.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_white));
            fragmentHomeNewBinding.viewFind.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_white));
            fragmentHomeNewBinding.viewSecurity.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryW));

            fragmentHomeNewBinding.ivTender.setImageResource(R.mipmap.ic_home_tender);
            fragmentHomeNewBinding.ivFind.setImageResource(R.mipmap.ic_home_find);
            fragmentHomeNewBinding.ivSecurity.setImageResource(R.mipmap.ic_home_security_pressed);

            fragmentHomeNewBinding.vpHomeBusiness.setCurrentItem(2);

        });
        // 电话解决
        fragmentHomeNewBinding.tvDoPhone.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putLong("orderId", mOrderId);
            JumpItent.jump(getActivity(), SolveModeActivity.class, bundle, RepairCtrlActivity.REFREST_ITEM);
            //给客户联系人打电话
            CallUtils.call(getActivity(), v(() -> mOrderPhone));
        });

        //全部应用
        fragmentHomeNewBinding.tvAllApp.setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), WorkspaceActivity.class));
        });
        //报价
        fragmentHomeNewBinding.tvInsidePrice.setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), OfferAndPayOrderParentActivity.class));
        });
        //工作日报
        fragmentHomeNewBinding.tvWorkReport.setOnClickListener((v) -> {
            if (!PermKit.get().getWorkReportListPrem()) {
                return;
            }
            Intent intent = new Intent(getActivity(), WorkReportListActivity.class);
            startActivity(intent);
        });
        //切换公司
        fragmentHomeNewBinding.tvHomeTitle.setOnClickListener((v) -> {
            doChangeCompany();
        });
        //专家问答
        fragmentHomeNewBinding.tvOnLine.setOnClickListener((v) -> {//wq==
            startActivity(new Intent(getActivity(), ExpertOnlineActivity.class));
        });
        //签到
        fragmentHomeNewBinding.tvSign.setOnClickListener((v) -> {
            // 检查有无权限
            List<String> ss = new ArrayList<>();
            new SignCtrlView(getActivity()).show();
        });
        //扫描二维码
        fragmentHomeNewBinding.ivScan.setOnClickListener((v) -> {
            homeScanPopWindow.showAsDropDown(fragmentHomeNewBinding.ivScan);
            homeScanPopWindow.backgroundAlpha(0.5f);
        });
    }

    View.OnClickListener scanSelectItemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rl_scan_login:
                    Bundle bundle_login = new Bundle();
                    bundle_login.putString("from", EanfangConst.QR_CLIENT);
                    bundle_login.putString("scanType", "scan_login");
                    JumpItent.jump(getActivity(), ScanCodeActivity.class, bundle_login);
                    homeScanPopWindow.dismiss();
                    break;
                case R.id.rl_scan_addfriend:
                    Bundle bundle_addfriend = new Bundle();
                    bundle_addfriend.putString("from", "home_addfriend");
                    bundle_addfriend.putString("scanType", "scan_addfriend");
                    bundle_addfriend.putString(EanfangConst.QR_ADD_FRIEND, "add_friend");
                    JumpItent.jump(getActivity(), ScanCodeActivity.class, bundle_addfriend);
                    homeScanPopWindow.dismiss();
                    break;
                // 扫描设备
                case R.id.rl_scan_device:
                    Bundle bundle = new Bundle();
                    bundle.putString("from", EanfangConst.QR_CLIENT);
                    bundle.putString("scanType", "scan_device");
                    JumpItent.jump(getActivity(), ScanCodeActivity.class, bundle);
                    homeScanPopWindow.dismiss();
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 初始化轮播控件
     */
    private void initLoopView() {
        fragmentHomeNewBinding.tvData.setText((DateKit.get().weekName() + "  " + DateUtil.date().toDateStr()));
        int[] images = {R.mipmap.ic_worker_banner_1, R.mipmap.ic_worker_banner_2, R.mipmap.ic_worker_banner_3, R.mipmap.ic_worker_banner_4, R.mipmap.ic_worker_banner_5};
        List<View> viewList = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            ImageView image = new ImageView(getActivity());
            image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //设置显示格式
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            image.setImageResource(images[i]);
            viewList.add(image);
        }
        fragmentHomeNewBinding.bvLoop.startLoop(true);
        fragmentHomeNewBinding.bvLoop.setViewList(viewList);
    }

    /**
     * 初始化rolltext显示的文本
     */
    private void initRollTextView(List<String> list) {
        if (list == null || list.isEmpty()) {
            fragmentHomeNewBinding.llHomeRecommandAd.setVisibility(View.GONE);
        } else {
            fragmentHomeNewBinding.llHomeRecommandAd.setVisibility(View.VISIBLE);
        }

        List<View> views = new ArrayList<>();
        try {
            for (int i = 0; i < list.size(); i++) {
                View view = View.inflate(getContext(), R.layout.rolltext_item, null);
                TextView content = view.findViewById(R.id.tv_roll_item_text);
                TextView title = view.findViewById(R.id.tv_roll_item_title);
//                title.setText(titleList.get(i));
                content.setText(list.get(i).toString());
                views.add(view);
            }
        } catch (NullPointerException e) {

        }
        fragmentHomeNewBinding.homeRecommandAdText.setViews(views);
        fragmentHomeNewBinding.homeRecommandAdText.setOnItemClickListener((position, view) -> {
//            showToast("暂无可点");
            startActivity(new Intent(getActivity(), NewOrderActivity.class));
        });
    }

    public void doHttpNews() {

        EanfangHttp.get(NewApiService.GET_HOME_ORDER).execute(new EanfangCallback<HomeNewsBean>(getActivity(), false, HomeNewsBean.class, (list -> {
            if (list != null && list.getList() != null) {
                initRollTextView(list.getList());
            }
        })));


    }


    /**
     * 获取订单数量
     */
    private void doHttpOrderNums() {
        EanfangHttp.get(UserApi.ALL_MESSAGE).execute(new EanfangCallback<AllMessageBean>(getActivity(), false, AllMessageBean.class, (bean -> {
            if (bean == null) {
                return;
            }
            doSetOrderNums(bean);
        })));
    }

    public void doSetOrderNums(AllMessageBean bean) {
        //报价
        if (bean.getQuote() > 0) {
            mQuota = bean.getQuote();
        } else {
            mQuota = 0;
        }
        qBadgeViewQuota.setBadgeNumber(mQuota);
        /**
         * 底部红点更新
         * */
        EventBus.getDefault().post(bean);
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        private final String[] titles;
        private final ArrayList<Fragment> fragments;

        private MyPagerAdapter(String[] titles, ArrayList<Fragment> fragments) {
            super(getChildFragmentManager());
            this.titles = titles;
            this.fragments = fragments;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
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
     * 初始化定位
     */
    private void initLocal() {
        //初始化client
        locationClient = new AMapLocationClient(WorkerApplication.get().getApplicationContext());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(this);
        startLocation();
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
     * 默认的定位参数
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        //可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setGpsFirst(true);
        //可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setHttpTimeOut(30000);
        //可选，设置定位间隔。默认为2秒
        mOption.setInterval(2000);
        //可选，设置是否返回逆地理地址信息。默认是true
        mOption.setNeedAddress(true);
        //可选，设置是否单次定位。默认是false
        mOption.setOnceLocation(false);
        //可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        mOption.setOnceLocationLatest(false);
        //可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);
        //可选，设置是否使用传感器。默认是false
        mOption.setSensorEnable(false);
        //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setWifiScan(true);
        //可选，设置是否使用缓存定位，默认为true
        mOption.setLocationCacheEnable(true);
        return mOption;
    }


    @Override
    public void onStop() {
        super.onStop();
        stopLocation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyLocation();
    }

    /**
     * 停止定位
     */
    private void stopLocation() {
        // 停止定位
        locationClient.stopLocation();
    }
}

