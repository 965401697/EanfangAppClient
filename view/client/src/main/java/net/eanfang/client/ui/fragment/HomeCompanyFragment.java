package net.eanfang.client.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.biz.model.CompanyBean;
import com.eanfang.biz.model.HomeWorkerBean;
import com.eanfang.biz.model.security.HomeCompanyBean;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.JumpItent;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.SelectWorkerActivity;
import net.eanfang.client.ui.activity.worksapce.online.DividerItemDecoration;
import net.eanfang.client.ui.adapter.FragmentHomeCompanyAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import cn.hutool.core.thread.ThreadUtil;

/**
 * @author liangkailun
 * Date ：2019-06-18
 * Describe :安防公司、找技师页面
 */
public class HomeCompanyFragment extends BaseFragment {
    private static final String PAGE_TYPE = "pageType";
    @BindView(R.id.rec_home_company)
    RecyclerView mRecHomeCompany;
    @BindView(R.id.tv_home_company_more)
    TextView mTvHomeComanyMore;
    private FragmentHomeCompanyAdapter adapter;
    /**
     * 0 安防公司
     * 1 找技师
     */
    private int mPageType;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private int mAreaId = 102;
    private boolean mIsRequestOne = false;

    public static HomeCompanyFragment getInstance(int pageType) {
        HomeCompanyFragment homeCompanyFragment = new HomeCompanyFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(PAGE_TYPE, pageType);
        homeCompanyFragment.setArguments(arguments);
        return homeCompanyFragment;
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_home_company;
    }

    @Override
    protected void initData(Bundle arguments) {
        initLocal();
        if (arguments != null) {
            mPageType = arguments.getInt(PAGE_TYPE, 0);
        }
    }

    @Override
    protected void onLazyLoad() {
        if (adapter == null) {
            return;
        }
        initListData(mAreaId);
    }

    /**
     * 请求数据
     */
    private void initListData(int areaId) {
        if (mPageType == 0) {
            EanfangHttp.post(NewApiService.HOME_COMPANY_LIST).params("page", "1").params("size", "2").params("areaId", areaId).execute(new EanfangCallback<CompanyBean>(getActivity(), true, CompanyBean.class, bean -> {
                boolean toRequestNew = !mIsRequestOne && (bean.getList() == null || bean.getList().size() == 0);
                if (toRequestNew) {
                    mIsRequestOne = true;
                    initListData(102);
                    return;
                }
                mIsRequestOne = false;
                adapter.getData().clear();
                ArrayList<HomeCompanyBean> homeCompanyBeans = new ArrayList<>();
                for (CompanyBean.ListBean companyBean : bean.getList()) {
                    HomeCompanyBean homeCompanyBean = new HomeCompanyBean();
                    homeCompanyBean.setAreaCode(companyBean.getCompanyEntity().getAreaCode());
                    homeCompanyBean.setDesignCount(companyBean.getDesignCount());
                    homeCompanyBean.setGoodRate(companyBean.getGoodRate());
                    homeCompanyBean.setInstallCount(companyBean.getInstallCount());
                    homeCompanyBean.setLogoPic(companyBean.getCompanyEntity().getLogoPic());
                    homeCompanyBean.setName(companyBean.getCompanyEntity().getName());
                    homeCompanyBean.setRepairCount(companyBean.getRepairCount());
                    homeCompanyBean.setStatus(companyBean.getCompanyEntity().getStatus());
                    homeCompanyBean.setPageType(0);
                    homeCompanyBeans.add(homeCompanyBean);
                }
                adapter.setNewData(homeCompanyBeans);
            }));
        } else {
            EanfangHttp.post(NewApiService.HOME_WORKER_LIST).params("page", "1").params("size", "2").params("areaId", areaId).execute(new EanfangCallback<HomeWorkerBean>(getActivity(), true, HomeWorkerBean.class, bean -> {
                boolean toRequestNew = !mIsRequestOne && (bean.getList() == null || bean.getList().size() == 0);
                if (toRequestNew) {
                    mIsRequestOne = true;
                    initListData(102);
                    return;
                }
                mIsRequestOne = false;
                adapter.getData().clear();
                ArrayList<HomeCompanyBean> homeCompanyBeans = new ArrayList<>();
                for (HomeWorkerBean.ListBean companyBean : bean.getList()) {
                    HomeCompanyBean homeCompanyBean = new HomeCompanyBean();
                    homeCompanyBean.setAreaCode(companyBean.getPlaceCode());
                    homeCompanyBean.setDesignCount(companyBean.getDesignNum());
                    homeCompanyBean.setGoodRate(companyBean.getGoodRate());
                    homeCompanyBean.setInstallCount(companyBean.getInstallNum());
                    homeCompanyBean.setLogoPic(companyBean.getAccountEntity().getAvatar());
                    homeCompanyBean.setName(companyBean.getAccountEntity().getRealName());
                    homeCompanyBean.setRepairCount(companyBean.getRepairCount());
                    homeCompanyBean.setLevel(companyBean.getVerifyEntity().getWorkingLevel());
                    homeCompanyBean.setPractitionerYears(companyBean.getVerifyEntity().getWorkingYear());
                    homeCompanyBean.setPageType(1);
                    homeCompanyBeans.add(homeCompanyBean);
                }
                adapter.setNewData(homeCompanyBeans);
            }));
        }
    }

    @Override
    protected void initView() {
        mRecHomeCompany.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecHomeCompany.addItemDecoration(new DividerItemDecoration(getContext()));

        ThreadUtil.execute(() -> {
            for (int i = 0; i < 50; i++) {
                if (Config.get().getBaseDataBean() != null) {
                    getActivity().runOnUiThread(() -> {
                        adapter = new FragmentHomeCompanyAdapter();
                        adapter.bindToRecyclerView(mRecHomeCompany);
                        onLazyLoad();
                    });
                    return;
                }
                ThreadUtil.safeSleep(1000);
            }
        });

    }

    @Override
    protected void setListener() {
        /**
         * 0 安防公司
         * 1 找技师
         */
        mTvHomeComanyMore.setOnClickListener((v) -> {
            if (mPageType == 0) {
                JumpItent.jump(getActivity(), SelectWorkerActivity.class);
            } else {
                JumpItent.jump(getActivity(), SelectWorkerActivity.class);
            }
        });
    }

    /**
     * 初始化定位
     */
    private void initLocal() {
        //初始化client
        locationClient = new AMapLocationClient(getActivity());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(aMapLocation -> {
            if (null != aMapLocation) {
                aMapLocation.getCityCode();
                String code = Config.get().getAreaCodeByName(aMapLocation.getCity(), aMapLocation.getDistrict());
                int areaId = Config.get().getBaseIdByCode(code, 2, Constant.AREA);
                if (areaId != 0 && mAreaId != areaId) {
                    mAreaId = areaId;
                    initListData(mAreaId);
                }
            }
        });
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
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
}
