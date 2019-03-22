package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.EanfangConst;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.AllMessageBean;
import com.eanfang.model.NoticeEntity;
import com.eanfang.model.datastatistics.HomeDatastisticeBean;
import com.eanfang.model.security.SecurityHotListBean;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.StringUtils;
import com.eanfang.util.V;
import com.eanfang.witget.BannerView;
import com.eanfang.witget.HomeScanPopWindow;
import com.eanfang.witget.RollTextView;
import com.photopicker.com.util.BGASpaceItemDecoration;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.CameraActivity;
import net.eanfang.client.ui.activity.worksapce.CustomerServiceActivity;
import net.eanfang.client.ui.activity.worksapce.DesignOrderActivity;
import net.eanfang.client.ui.activity.worksapce.NoContentActivity;
import net.eanfang.client.ui.activity.worksapce.RealTimeMonitorActivity;
import net.eanfang.client.ui.activity.worksapce.datastatistics.DataDesignActivity;
import net.eanfang.client.ui.activity.worksapce.datastatistics.DataInstallActivity;
import net.eanfang.client.ui.activity.worksapce.datastatistics.DataStaticsticsListActivity;
import net.eanfang.client.ui.activity.worksapce.datastatistics.DataStatisticsActivity;
import net.eanfang.client.ui.activity.worksapce.install.InstallOrderParentActivity;
import net.eanfang.client.ui.activity.worksapce.repair.RepairTypeActivity;
import net.eanfang.client.ui.activity.worksapce.scancode.ScanCodeActivity;
import net.eanfang.client.ui.activity.worksapce.security.SecurityDetailActivity;
import net.eanfang.client.ui.activity.worksapce.security.SecurityListActivity;
import net.eanfang.client.ui.adapter.HomeDataAdapter;
import net.eanfang.client.ui.adapter.security.SecurityHotListAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.badgeview.QBadgeView;

import static com.eanfang.util.V.v;

/**
 * Created by MrHou
 *
 * @on 2017/11/10  15:07
 * @email houzhongzhou@yeah.net
 * @desc 首页
 */

public class HomeFragment extends BaseFragment {

    // 实时监控
    private TextView tvMonitor;
    //报修数量
    private TextView tvReapirTotal;
    //报装数量
    TextView tvInstallTotal;
    // 设计总数
    TextView tvDesitnTotal;

    private BannerView bannerView;

    private RollTextView rollTextView;

    //头部标题
    private TextView tvHomeTitle;

    // 扫码Popwindow
    private HomeScanPopWindow homeScanPopWindow;

    private RecyclerView rvData;
    private List<HomeDatastisticeBean.GroupBean> clientDataList = new ArrayList<>();
    private HomeDataAdapter homeDataAdapter;
    private RelativeLayout rlAllData;

    /**
     * 图标数量
     */
    private QBadgeView qBadgeViewRepair = new QBadgeView(EanfangApplication.get().getApplicationContext());
    private QBadgeView qBadgeViewDesign = new QBadgeView(EanfangApplication.get().getApplicationContext());
    private QBadgeView qBadgeViewInstall = new QBadgeView(EanfangApplication.get().getApplicationContext());
    private int mRepair = 0;
    private int mDesign = 0;
    private int mInstall = 0;
    /**
     * 安防圈
     */
    private RecyclerView rvSecurity;
    private SecurityHotListAdapter securityHotListAdapter;
    private TextView tvNoSecurity;

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
        String orgName = v(() -> (EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgName()));
        if (("个人").equals(orgName)) {
            tvHomeTitle.setText("易安防");
        } else {
            tvHomeTitle.setText(orgName);
        }
        doHttpOrderNums();
        doGetSecurityData();
    }

    @Override
    protected void initView() {
        tvMonitor = (TextView) findViewById(R.id.tv_monitor);
        rvData = (RecyclerView) findViewById(R.id.rv_reapir_data);
        tvHomeTitle = (TextView) findViewById(R.id.tv_homeTitle);
        rlAllData = (RelativeLayout) findViewById(R.id.rl_allData);
        tvReapirTotal = findViewById(R.id.tv_reapir_total);
        tvInstallTotal = findViewById(R.id.tv_install_total);
        tvDesitnTotal = findViewById(R.id.tv_desitn_total);
        rvSecurity = findViewById(R.id.rv_security);
        tvNoSecurity = findViewById(R.id.tv_noSecurity);
        homeScanPopWindow = new HomeScanPopWindow(getActivity(), false, scanSelectItemsOnClick);
        homeScanPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                homeScanPopWindow.backgroundAlpha(1.0f);
            }
        });
        initIconClick();
        initLoopView();

        //设置布局样式
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rvData.setLayoutManager(gridLayoutManager);
        initCount();

        doHttpNews();
        doHttpDatastatistics();
        //报修
        qBadgeViewRepair.bindTarget(findViewById(R.id.tv_reparir))
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(5, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(11, 0, true)
                .setBadgeTextSize(11, true);
        // 报装
        qBadgeViewInstall.bindTarget(findViewById(R.id.tv_install))
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(5, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(11, 0, true)
                .setBadgeTextSize(11, true);
        //设计
        qBadgeViewDesign.bindTarget(findViewById(R.id.tv_design))
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(5, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(11, 0, true)
                .setBadgeTextSize(11, true);

        initSecurity();
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
//            new InstallCtrlView(getActivity(), true).show();
            startActivity(new Intent(getActivity(), InstallOrderParentActivity.class));

        });
        //免费设计
        findViewById(R.id.tv_design).setOnClickListener((v) -> {
//            new DesignCtrlView(getActivity(), true).show();
            startActivity(new Intent(getActivity(), DesignOrderActivity.class));
        });
        //开锁
        findViewById(R.id.tv_unlock).setOnClickListener(v -> JumpItent.jump(getActivity(), NoContentActivity.class));
        //实时监控
        findViewById(R.id.tv_monitor).setOnClickListener(v -> JumpItent.jump(getActivity(), RealTimeMonitorActivity.class));
        //客服
        findViewById(R.id.tv_service).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), CustomerServiceActivity.class));
        });
        //扫描二维码
        findViewById(R.id.iv_scan).setOnClickListener((v) -> {
            homeScanPopWindow.showAsDropDown(findViewById(R.id.iv_scan));
            homeScanPopWindow.backgroundAlpha(0.5f);
        });

        //签到
//        findViewById(R.id.ll_sign).setOnClickListener((v) -> {
//            new SignCtrlView(getActivity()).show();
//        });
        // 安防圈
        findViewById(R.id.rl_security).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), SecurityListActivity.class));
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
                case R.id.rl_scan_device:   // 扫设备
                    Bundle bundle = new Bundle();
                    bundle.putString("from", EanfangConst.QR_CLIENT);
                    bundle.putString("scanType", "scan_device");
                    JumpItent.jump(getActivity(), ScanCodeActivity.class, bundle);
                    homeScanPopWindow.dismiss();
                    break;
                case R.id.rl_scan_reapir:   // 扫客户/ 技师 报修
                    Bundle bundle_repair = new Bundle();
                    bundle_repair.putString("from", EanfangConst.QR_CLIENT);
                    bundle_repair.putString("scanType", "scan_person");
                    homeScanPopWindow.dismiss();
                    JumpItent.jump(getActivity(), ScanCodeActivity.class, bundle_repair);
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
        bannerView.startLoop(true);
        bannerView.setViewList(viewList);
    }

    /**
     * 初始化rolltext显示的文本
     */
    private void initRollTextView(List<NoticeEntity> list) {
        rollTextView = findViewById(R.id.home_recommand_ad_text);
        List<View> views = new ArrayList<>();
        List<String> data = new ArrayList<>();
        List<String> titleList = new ArrayList<>();

        String repairStr = "通过易安防进行了报修。";
        String quoteStr = "通过易安防收到了一个报价单。";
        String maintainStr = "通过易安防进行了一次日常维保。";

        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                NoticeEntity noticeEntity = list.get(i);

                String realName = V.v(() -> noticeEntity.getReciveAccEntity().getRealName());
                if (StringUtils.isEmpty(realName)) {
                    continue;
                }
                if (noticeEntity.getNoticeType() == 23) {
                    data.add(repairStr + "\r\n" + GetDateUtils.dateToDateTimeString(noticeEntity.getCreateTime()));
                } else if (noticeEntity.getNoticeType() == 42) {
                    data.add(quoteStr + "\r\n" + GetDateUtils.dateToDateTimeString(noticeEntity.getCreateTime()));
                } else if (noticeEntity.getNoticeType() == 56) {
                    data.add(maintainStr + "\r\n" + GetDateUtils.dateToDateTimeString(noticeEntity.getCreateTime()));
                } else {
                    continue;
                }

                StringBuilder showName = new StringBuilder();
                if (realName.length() >= 1) {
                    showName.append(realName.charAt(0));
                }
                if (realName.length() >= 2) {
                    showName.append("*");
                }
                if (realName.length() >= 3) {
                    showName.append(realName.charAt(2));
                }
                titleList.add(showName + "先生");
            }

        }


        try {
            for (int i = 0; i < data.size(); i++) {
                View view = View.inflate(getContext(), R.layout.rolltext_item, null);
                TextView content = (TextView) view.findViewById(R.id.tv_roll_item_text);
                TextView title = (TextView) view.findViewById(R.id.tv_roll_item_title);
                title.setText(titleList.get(i).toString());
                content.setText(data.get(i).toString());
                views.add(view);
            }
        } catch (NullPointerException e) {
        }


        rollTextView.setViews(views);
        rollTextView.setOnItemClickListener((position, view) -> {
//            showToast("暂无可点");
        });
    }

    /**
     * 安防圈
     */
    private void initSecurity() {
        securityHotListAdapter = new SecurityHotListAdapter(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvSecurity.setLayoutManager(layoutManager);
        rvSecurity.setNestedScrollingEnabled(false);
        rvSecurity.addItemDecoration(new BGASpaceItemDecoration(20));
        securityHotListAdapter.bindToRecyclerView(rvSecurity);
        doGetSecurityData();
    }

    private void doGetSecurityData() {
        QueryEntry mQueryEntry = new QueryEntry();
        mQueryEntry.setPage(1);
        mQueryEntry.setSize(3);
        EanfangHttp.post(NewApiService.SECURITY_RECOMMEND)
                .upJson(JsonUtils.obj2String(mQueryEntry))
                .execute(new EanfangCallback<SecurityHotListBean>(getActivity(), true, SecurityHotListBean.class) {

                    @Override
                    public void onSuccess(SecurityHotListBean bean) {
                        securityHotListAdapter.getData().clear();
                        securityHotListAdapter.setNewData(bean.getList());
                        if (bean.getList().size() > 0) {
                            tvNoSecurity.setVisibility(View.GONE);
                        } else {
                            tvNoSecurity.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    /**
     * 获取新闻
     */
    public void doHttpNews() {
        EanfangHttp.get(NewApiService.GET_PUSH_NEWS_CLIENT).execute(new EanfangCallback<NoticeEntity>(getActivity(), false, NoticeEntity.class, true, (list -> {
            initRollTextView(list);
        })));
    }

    /**
     * 获取统计数据
     */
    private void doHttpDatastatistics() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("companyId", EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgId() + "");
        EanfangHttp.post(NewApiService.HOME_DATASTASTISTICS)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<HomeDatastisticeBean>(getActivity(), false, HomeDatastisticeBean.class, bean -> {
                    initDatastatisticsData(bean);
                }));
    }

    /**
     * 获取订单数量
     */
    private void doHttpOrderNums() {
        EanfangHttp.get(UserApi.ALL_MESSAGE).execute(new EanfangCallback<AllMessageBean>(getActivity(), false, AllMessageBean.class, (bean -> {
            doSetOrderNums(bean);
        })));
    }

    /**
     * 統計填充数据
     */
    private void initDatastatisticsData(HomeDatastisticeBean bean) {
        clientDataList = bean.getGroup();// 报修
        homeDataAdapter = new HomeDataAdapter(R.layout.layout_home_data);
        rvData.setAdapter(homeDataAdapter);
        homeDataAdapter.bindToRecyclerView(rvData);
        homeDataAdapter.setNewData(clientDataList);
        tvReapirTotal.setText(bean.getAll() + "");
        tvDesitnTotal.setText(bean.getDesign().getNum() + "");
        tvInstallTotal.setText(bean.getInstall().getNum() + "");
    }

    @Override
    protected void setListener() {
        findViewById(R.id.iv_camera).setOnClickListener(v -> startActivity(new Intent(getActivity(), CameraActivity.class)));
        findViewById(R.id.ll_repair_datasticstics).setOnClickListener(v -> startActivity(new Intent(getActivity(), DataStatisticsActivity.class)));
        rvData.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(), DataStatisticsActivity.class));
            }
        });
        findViewById(R.id.ll_repair_install).setOnClickListener(v -> startActivity(new Intent(getActivity(), DataInstallActivity.class)));
        findViewById(R.id.ll_design).setOnClickListener(v -> startActivity(new Intent(getActivity(), DataDesignActivity.class)));
        securityHotListAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("bean", securityHotListAdapter.getData().get(position));
            bundle.putString("type", "hot");
            bundle.putInt("friend", securityHotListAdapter.getData().get(position).getFriend());
            JumpItent.jump(getActivity(), SecurityDetailActivity.class, bundle);
        });
        securityHotListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_isFocus:
                case R.id.ll_like:
                case R.id.ll_comments:
                case R.id.ll_pic:
                case R.id.iv_share:
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bean", securityHotListAdapter.getData().get(position));
                    bundle.putInt("friend", securityHotListAdapter.getData().get(position).getFriend());
                    bundle.putString("type", "hot");
                    JumpItent.jump(getActivity(), SecurityDetailActivity.class, bundle);
                    break;
                default:
                    break;
            }
        });
    }

    public void doSetOrderNums(AllMessageBean bean) {
        // 报修
        if (bean.getRepair() > 0) {
            mRepair = bean.getRepair();
        } else {
            mRepair = 0;
        }
        qBadgeViewRepair.setBadgeNumber(mRepair);
        // 报装
        if (bean.getInstall() > 0) {
            mInstall = bean.getInstall();
        } else {
            mInstall = 0;
        }
        qBadgeViewInstall.setBadgeNumber(mInstall);
        //设计
        if (bean.getDesign() > 0) {
            mDesign = bean.getDesign();
        } else {
            mDesign = 0;
        }
        qBadgeViewDesign.setBadgeNumber(mDesign);
        /**
         * 底部红点更新
         * */
        EventBus.getDefault().post(bean);
    }

}
