package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.BaseApplication;
import com.eanfang.base.kit.cache.CacheKit;
import com.eanfang.biz.model.bean.AllMessageBean;
import com.eanfang.biz.model.bean.Ys7SubAccountBean;
import com.eanfang.biz.model.bean.datastatistics.HomeDatastisticeBean;
import com.eanfang.biz.model.entity.Ys7AccountParam;
import com.eanfang.config.EanfangConst;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.JumpItent;
import com.eanfang.witget.BannerView;
import com.eanfang.witget.HomeScanPopWindow;
import com.flyco.tablayout.SlidingTabLayout;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.activity.CameraActivity;
import net.eanfang.client.ui.activity.leave_post.LeavePostDetailActivity;
import net.eanfang.client.ui.activity.leave_post.LeavePostHomeActivity;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostLatestAlertBean;
import net.eanfang.client.ui.activity.worksapce.DesignOrderActivity;
import net.eanfang.client.ui.activity.worksapce.RealTimeMonitorActivity;
import net.eanfang.client.ui.activity.worksapce.datastatistics.DataStaticsticsListActivity;
import net.eanfang.client.ui.activity.worksapce.install.InstallOrderParentActivity;
import net.eanfang.client.ui.activity.worksapce.online.ExpertOnlineActivity;
import net.eanfang.client.ui.activity.worksapce.repair.RepairTypeActivity;
import net.eanfang.client.ui.activity.worksapce.scancode.ScanCodeActivity;
import net.eanfang.client.ui.activity.worksapce.security.SecurityListActivity;
import net.eanfang.client.ui.widget.CustomHomeViewPager;

import org.greenrobot.eventbus.EventBus;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
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

public class HomeFragment extends BaseFragment {
    //报装数量
    TextView tvInstallTotal;
    // 设计总数
    TextView tvDesitnTotal;

    private BannerView bannerView;

    //头部标题
    private TextView tvHomeTitle;

    // 扫码Popwindow
    private HomeScanPopWindow homeScanPopWindow;

    private List<HomeDatastisticeBean.GroupBean> clientDataList = new ArrayList<>();
    private RelativeLayout rlAllData;

    private MyPagerAdapter mAdapter;

    private int mRepair = 0;
    private int mDesign = 0;
    private int mInstall = 0;
    private int mAlert = 0;
    private QBadgeView qBadgeViewReapir = new QBadgeView(ClientApplication.get().getApplicationContext());
    private QBadgeView qBadgeViewInstall = new QBadgeView(ClientApplication.get().getApplicationContext());
    private QBadgeView qBadgeViewDesign = new QBadgeView(ClientApplication.get().getApplicationContext());
    private QBadgeView qBadgeViewAlert = new QBadgeView(ClientApplication.get().getApplicationContext());
    private QBadgeView qBadgeViewCircle = new QBadgeView(ClientApplication.get().getApplicationContext());

    private int mSecurityNum;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    public void onResume() {
        super.onResume();
        String orgName = v(() -> (ClientApplication.get().getLoginBean().getAccount().getDefaultUser().getCompanyEntity().getOrgName()));
        if (("个人").equals(orgName)) {
            tvHomeTitle.setText("易安防");
        } else {
            tvHomeTitle.setText(orgName);
        }
        doHttpOrderNums();
        initLeavePostAlert();
    }

    @Override
    protected void initView() {
        tvHomeTitle = findViewById(R.id.tv_homeTitle);
        rlAllData = findViewById(R.id.rl_allData);
        tvInstallTotal = findViewById(R.id.tv_install_total);
        tvDesitnTotal = findViewById(R.id.tv_desitn_total);
//        mTvSecurityNewMessage = findViewById(R.id.tv_security_count);
//        rlSecurityNewMessage = findViewById(R.id.rl_security_message);
        homeScanPopWindow = new HomeScanPopWindow(getActivity(), false, scanSelectItemsOnClick);
        homeScanPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                homeScanPopWindow.backgroundAlpha(1.0f);
            }
        });
        initIconClick();
        initLoopView();
//        doHttpOrderNums();

        initCount();

        initViewPager1();
        initViewPager2();
        initViewPager3();
        initViewPager4();

    }

    private void initLeavePostAlert() {
        String dateString = CacheKit.get().getStr("offDuty");
        EanfangHttp.post(NewApiService.HOME_LATEST_ALERT).params("companyId", ClientApplication.get().getCompanyId()).params("date", dateString)
                .execute(new EanfangCallback<LeavePostLatestAlertBean>(getActivity(), false, LeavePostLatestAlertBean.class, bean -> {
                    if (bean != null) {
                        findViewById(R.id.ll_off_duty).setVisibility(View.VISIBLE);
                        ((TextView) findViewById(R.id.tv_msg_off_duty)).setText(MessageFormat.format("{0}在{1}脱岗，请查看> >", bean.getAlertName(), bean.getAlertTime()));
                        findViewById(R.id.tv_msg_off_duty).setOnClickListener(view -> startActivity(new Intent(getActivity(), LeavePostDetailActivity.class).putExtra("alertId", bean.getAlertId())));
                        findViewById(R.id.img_cancel).setOnClickListener(view -> {
                            Date date1 = new Date();
                            findViewById(R.id.ll_off_duty).setVisibility(View.GONE);
                            CacheKit.get().put("offDuty", DateUtil.date(date1).toString());
                        });
                    } else {
                        findViewById(R.id.ll_off_duty).setVisibility(View.GONE);
                    }
                }));

    }

    private void initViewPager1() {
        String[] titles = {"当日维修", "当日安装", "当日设计"};
        ArrayList<Fragment> fragments = new ArrayList<>();
        CustomHomeViewPager vpDataStatis = findViewById(R.id.vp_datastatistics);
        SlidingTabLayout tlDataStatisticsList = findViewById(R.id.tl_datastatistics);

        fragments.clear();
        fragments.add(HomeDataStatisticsFragment.getInstance(titles[0], 1));
        fragments.add(HomeDataStatisticsFragment.getInstance(titles[1], 2));
        fragments.add(HomeDataStatisticsFragment.getInstance(titles[2], 3));

        mAdapter = new MyPagerAdapter(titles, fragments);
        vpDataStatis.setAdapter(mAdapter);
        // 设置不可滑动
        vpDataStatis.setScanScroll(false);
        tlDataStatisticsList.setViewPager(vpDataStatis, titles, getActivity(), fragments);
        vpDataStatis.setCurrentItem(0);
        tlDataStatisticsList.setCurrentTab(0);
    }

    private void initViewPager2() {
        String[] titles = {"快速报修", "免费报装", "免费设计"};
        ArrayList<Fragment> fragments = new ArrayList<>();
        CustomHomeViewPager vpDataStatis = findViewById(R.id.vp_home_repair);
        SlidingTabLayout tlDataStatisticsList = findViewById(R.id.tl_home_repair);

        fragments.clear();
        fragments.add(HomeRepairFragment.getInstance(0, null, null, null, null));
        fragments.add(HomeRepairFragment.getInstance(1, null, null, null, null));
        fragments.add(HomeRepairFragment.getInstance(2, null, null, null, null));

        mAdapter = new MyPagerAdapter(titles, fragments);
        vpDataStatis.setAdapter(mAdapter);
        // 设置不可滑动
        vpDataStatis.setScanScroll(false);
        tlDataStatisticsList.setViewPager(vpDataStatis, titles, getActivity(), fragments);
        vpDataStatis.setCurrentItem(0);
        tlDataStatisticsList.setCurrentTab(0);
    }

    private void initViewPager3() {
        String[] titles = {"最新订单", "全部品牌", "全部业务"};
        ArrayList<Fragment> fragments = new ArrayList<>();
        CustomHomeViewPager vpDataStatis = findViewById(R.id.vp_home_business);
        SlidingTabLayout tlDataStatisticsList = findViewById(R.id.tl_home_business);

        fragments.clear();
        fragments.add(new HomeNewOrderFragment());
        fragments.add(new HomeAllBrandFragment());
        fragments.add(new HomeAllBusinessTypeFragment());

        mAdapter = new MyPagerAdapter(titles, fragments);
        vpDataStatis.setAdapter(mAdapter);
        // 设置不可滑动
        vpDataStatis.setScanScroll(false);
        tlDataStatisticsList.setViewPager(vpDataStatis, titles, getActivity(), fragments);
        vpDataStatis.setCurrentItem(0);
        tlDataStatisticsList.setCurrentTab(0);
    }

    private void initViewPager4() {
        String[] titles = {"安防公司", "找技师"};
        ArrayList<Fragment> fragments = new ArrayList<>();
        CustomHomeViewPager vpDataStatis = findViewById(R.id.vp_home_worker);
        SlidingTabLayout tlDataStatisticsList = findViewById(R.id.tl_home_worker);

        fragments.clear();
        fragments.add(HomeCompanyFragment.getInstance(0));
        fragments.add(HomeWorkerFragment.getInstance(1));

        mAdapter = new MyPagerAdapter(titles, fragments);
        vpDataStatis.setAdapter(mAdapter);
        // 设置不可滑动
        vpDataStatis.setScanScroll(false);
        tlDataStatisticsList.setViewPager(vpDataStatis, titles, getActivity(), fragments);
        vpDataStatis.setCurrentItem(0);
        tlDataStatisticsList.setCurrentTab(0);
    }


    /**
     * 工作按钮
     */
    private void initIconClick() {
        //我要报修
        findViewById(R.id.tv_reparir).setOnClickListener((v) -> {
            JumpItent.jump(getActivity(), RepairTypeActivity.class);
        });

        //我要报装
        findViewById(R.id.tv_install).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), InstallOrderParentActivity.class));
        });
        //免费设计
        findViewById(R.id.tv_design).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), DesignOrderActivity.class));
        });
        //安防头条
        findViewById(R.id.tv_lead_news).setOnClickListener(view -> {

        });
        //安防圈
        findViewById(R.id.tv_circle).setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("mSecurityNum", mSecurityNum);
            JumpItent.jump(getActivity(), SecurityListActivity.class, bundle);
        });
        //专家问答
        findViewById(R.id.tv_onLine).setOnClickListener((v) -> {
            if (workerApprove()) {
                startActivity(new Intent(getActivity(), ExpertOnlineActivity.class));
            }
        });
        findViewById(R.id.tv_lead_news).setOnClickListener(view -> {
            showToast("暂未开通");
        });
        //实时监控
        findViewById(R.id.tv_monitor).setOnClickListener(v -> JumpItent.jump(getActivity(), RealTimeMonitorActivity.class));
        //脱岗监测
        findViewById(R.id.tv_out_post).setOnClickListener(view -> {
            Ys7SubAccountBean bean = CacheKit.get().get("subAccountInfoList", Ys7SubAccountBean.class);
            if (bean != null) {
                Ys7AccountParam param = bean.getSubAccountInfoList().get(BaseApplication.get().getCompanyId());
                if (param != null && param.getStationDetect()) {
                    startActivity(new Intent(getActivity(), LeavePostHomeActivity.class));
                    // CacheKit.get().put("YingShiYunToken", value);
//                ClientApplication.get().set("YingShiYunToken", value);
                }
            } else {
                showToast(R.string.text_leave_post_no_open_toast);
            }
        });

        //扫描二维码
        findViewById(R.id.iv_scan).setOnClickListener((v) -> {
            homeScanPopWindow.showAsDropDown(findViewById(R.id.iv_scan));
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
                // 扫设备
                case R.id.rl_scan_device:
                    Bundle bundle = new Bundle();
                    bundle.putString("from", EanfangConst.QR_CLIENT);
                    bundle.putString("scanType", "scan_device");
                    JumpItent.jump(getActivity(), ScanCodeActivity.class, bundle);
                    homeScanPopWindow.dismiss();
                    break;
                // 扫客户/ 技师 报修
                case R.id.rl_scan_reapir:
                    Bundle bundle_repair = new Bundle();
                    bundle_repair.putString("from", EanfangConst.QR_CLIENT);
                    bundle_repair.putString("scanType", "scan_person");
                    homeScanPopWindow.dismiss();
                    JumpItent.jump(getActivity(), ScanCodeActivity.class, bundle_repair);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 统计
     */
    private void initCount() {
        rlAllData.setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), DataStaticsticsListActivity.class));
        });
    }

    /**
     * 初始化轮播控件
     */
    private void initLoopView() {
        bannerView = findViewById(R.id.bv_loop);
        int[] images = {R.mipmap.ic_client_banner_1, R.mipmap.ic_client_banner_2, R.mipmap.ic_client_banner_3, R.mipmap.ic_client_banner_4, R.mipmap.ic_client_banner_5};
        List<View> viewList = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            ImageView image = new ImageView(getActivity());
            image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //设置显示格式
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            image.setImageResource(images[i]);
            viewList.add(image);
        }
        bannerView.setCurrentDownImage(R.drawable.shape_circle_home_choose);
        bannerView.setDefaultDownImage(R.drawable.shape_circle_home_unchoose);
        bannerView.startLoop(true);
        bannerView.setViewList(viewList);
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

    @Override
    protected void setListener() {
        findViewById(R.id.iv_camera).setOnClickListener(v -> startActivity(new Intent(getActivity(), CameraActivity.class)));
    }

    private void doSetOrderNums(AllMessageBean bean) {
        // 报修
        if (bean.getRepair() > 0) {
            mRepair = bean.getRepair();
        } else {
            mRepair = 0;
        }
        qBadgeViewReapir.bindTarget(findViewById(R.id.tv_reparir))
                .setBadgeNumber(mRepair)
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(2, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(11, 0, true)
                .setBadgeTextSize(10, true);
        // 报装
        if (bean.getInstall() > 0) {
            mInstall = bean.getInstall();
        } else {
            mInstall = 0;
        }
        qBadgeViewInstall.bindTarget(findViewById(R.id.tv_install))
                .setBadgeNumber(mInstall)
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(2, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(11, 0, true)
                .setBadgeTextSize(10, true);
        //设计
        if (bean.getDesign() > 0) {
            mDesign = bean.getDesign();
        } else {
            mDesign = 0;
        }
        qBadgeViewDesign.bindTarget(findViewById(R.id.tv_design))
                .setBadgeNumber(mDesign)
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(2, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(11, 0, true)
                .setBadgeTextSize(10, true);
        // 脱岗监测
        if (bean.getAlert() > 0) {
            mAlert = bean.getAlert();
        } else {
            mAlert = 0;
        }
        qBadgeViewAlert.bindTarget(findViewById(R.id.tv_out_post))
                .setBadgeNumber(mAlert)
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(2, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(11, 0, true)
                .setBadgeTextSize(10, true);
        // @我的和评论未读
        if (bean.getCommentNoRead() + bean.getNoReadCount() > 0) {
            mSecurityNum = bean.getCommentNoRead() + bean.getNoReadCount();
        } else {
            mSecurityNum = 0;
        }
        qBadgeViewCircle.bindTarget(findViewById(R.id.tv_circle))
                .setBadgeNumber(mSecurityNum)
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(2, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(11, 0, true)
                .setBadgeTextSize(10, true);
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

}
