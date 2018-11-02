package net.eanfang.worker.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.eanfang.model.HomeOrderNumBean;
import com.eanfang.model.NoticeEntity;
import com.eanfang.model.datastatistics.HomeDatastisticeBean;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermKit;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.StringUtils;
import com.eanfang.util.V;
import com.eanfang.witget.BannerView;
import com.eanfang.witget.HomeScanPopWindow;
import com.eanfang.witget.RollTextView;
import com.eanfang.witget.SetQBadgeView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.CameraActivity;
import net.eanfang.worker.ui.activity.worksapce.InstallOrderParentActivity;
import net.eanfang.worker.ui.activity.worksapce.MineTaskPublishListSendParentActivity;
import net.eanfang.worker.ui.activity.worksapce.OfferAndPayOrderParentActivity;
import net.eanfang.worker.ui.activity.worksapce.TakeTaskListActivity;
import net.eanfang.worker.ui.activity.worksapce.WebActivity;
import net.eanfang.worker.ui.activity.worksapce.datastatistics.DataDesignActivity;
import net.eanfang.worker.ui.activity.worksapce.datastatistics.DataInstallActivity;
import net.eanfang.worker.ui.activity.worksapce.datastatistics.DataStaticsticsListActivity;
import net.eanfang.worker.ui.activity.worksapce.datastatistics.DataStatisticsActivity;
import net.eanfang.worker.ui.activity.worksapce.design.DesignActivity;
import net.eanfang.worker.ui.activity.worksapce.maintenance.MaintenanceActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.RepairCtrlActivity;
import net.eanfang.worker.ui.activity.worksapce.scancode.ScanCodeActivity;
import net.eanfang.worker.ui.adapter.HomeDataAdapter;
import net.eanfang.worker.ui.widget.SignCtrlView;

import java.util.ArrayList;
import java.util.List;

import static com.eanfang.util.V.v;


/**
 * Created by MrHou
 *
 * @on 2017/11/10  15:07
 * @email houzhongzhou@yeah.net
 * @desc 首页
 */

public class HomeFragment extends BaseFragment {
    private BannerView bannerView;
    //头部标题
    private TextView tvHomeTitle;
    private RollTextView rollTextView;

    private RecyclerView rvData;
    private List<HomeDatastisticeBean.GroupBean> clientDataList = new ArrayList<>();
    private HomeDataAdapter homeDataAdapter;

    //报修数量
    TextView tvReapirTotal;
    LinearLayout llRepairDatasticstics;
    //报装数量
    TextView tvInstallTotal;
    // 设计总数
    TextView tvDesitnTotal;

    private RelativeLayout rlAllData;


    // 扫码Popwindow
    private HomeScanPopWindow homeScanPopWindow;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView() {
        rvData = (RecyclerView) findViewById(R.id.rv_reapir_data);

        rlAllData = (RelativeLayout) findViewById(R.id.rl_allData);
        tvReapirTotal = findViewById(R.id.tv_reapir_total);
        tvInstallTotal = findViewById(R.id.tv_install_total);
        tvDesitnTotal = findViewById(R.id.tv_desitn_total);
        tvHomeTitle = (TextView) findViewById(R.id.tv_homeTitle);
        llRepairDatasticstics = (LinearLayout) findViewById(R.id.ll_repair_datasticstics);
        homeScanPopWindow = new HomeScanPopWindow(getActivity(), true, scanSelectItemsOnClick);
        homeScanPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                homeScanPopWindow.backgroundAlpha(1.0f);
            }
        });
        initIconClick();
        initLoopView();
        //设置布局样式
        //设置布局样式
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rvData.setLayoutManager(gridLayoutManager);
        initCount();
        initFalseData();
        doHttpNews();
        doHttpDatastatistics();
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
    }

    /**
     * 工作按钮
     */
    private void initIconClick() {
        //报修订单
        findViewById(R.id.tv_reparir_order).setOnClickListener((v) -> {
            if (!PermKit.get().getRepairListPerm()) return;
            if (workerApprove()) {
                startActivity(new Intent(getActivity(), RepairCtrlActivity.class));
            }
        });
        //报装订单
        findViewById(R.id.tv_install_order).setOnClickListener((v) -> {
            if (workerApprove()) {
//                new InstallCtrlView(getActivity(), true).show();
                startActivity(new Intent(getActivity(), InstallOrderParentActivity.class));
            }
        });
        //设计订单
        findViewById(R.id.tv_design_order).setOnClickListener((v) -> {
            if (workerApprove()) {
                JumpItent.jump(getActivity(), DesignActivity.class);
            }
        });
        //维保订单
        findViewById(R.id.tv_maintain_order).setOnClickListener((v) -> {
            if (workerApprove()) {
//                new MaintainCtrlView(getActivity(), true).show();
                startActivity(new Intent(getActivity(), MaintenanceActivity.class));
            }
        });
        //找工人
        findViewById(R.id.tv_project_send).setOnClickListener((v) -> {
            if (workerApprove()) {
//                new TaskPubCtrlView(getActivity(), true).show();
                startActivity(new Intent(getActivity(), MineTaskPublishListSendParentActivity.class));
            }
        });
        //找活
        findViewById(R.id.tv_project_receive).setOnClickListener((v) -> {
            if (workerApprove()) {
//                new TakePubCtrlView(getActivity(), true).show();
                if (!PermKit.get().getBidListPrem()) return;
                startActivity(new Intent(getActivity(), TakeTaskListActivity.class));

            }
        });
        //内部报价
        findViewById(R.id.tv_inside_price).setOnClickListener((v) -> {
            if (workerApprove()) {
//                new PayOrderListCtrlView(getActivity(), true).show();
                startActivity(new Intent(getActivity(), OfferAndPayOrderParentActivity.class));
            }
        });
        //签到
        findViewById(R.id.tv_sign).setOnClickListener((v) -> {
            if (workerApprove()) {
                // 检查有无权限
                List<String> ss = new ArrayList<>();
                new SignCtrlView(getActivity()).show();
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
                case R.id.rl_scan_device:   // 扫描设备
                    Bundle bundle = new Bundle();
                    bundle.putString("from", EanfangConst.QR_CLIENT);
                    bundle.putString("scanType", "scan_device");
                    JumpItent.jump(getActivity(), ScanCodeActivity.class, bundle);
                    homeScanPopWindow.dismiss();
                    break;
            }
        }
    };

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
    }

    /**
     * 统计
     */
    private void initCount() {
        rlAllData.setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), DataStaticsticsListActivity.class));
        });
    }

    private void jumpWebview() {
        boolean isHave = EanfangApplication.getApplication().getUser().getPerms().contains("top:statistics:count");
        if (isHave == true) {
            String token = EanfangApplication.getApplication().getUser().getToken();
            startActivity(new Intent(getActivity(), WebActivity.class)
                    .putExtra("url", "http:/worker.eanfang.net:8099/#/totalPhone?token=" + token)
                    .putExtra("title", "数据统计"));
        } else {
            showToast("您没有权限");
        }
    }

    /**
     * 初始化轮播控件
     */
    private void initLoopView() {
        bannerView = findViewById(R.id.bv_loop);
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

        String repairStr = "通过易安防接到了一个报修订单。";
        String installStr = "通过易安防接到了一个报装订单。";
        String quoteStr = "通过易安防发送了一次报价。";
        String designStr = "通过易安防接到了一个设计订单。";
        String maintainStr = "通过易安防进行了一次日常维保。";

        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                NoticeEntity noticeEntity = list.get(i);

                String realName = V.v(() -> noticeEntity.getReciveAccEntity().getRealName());
                if (StringUtils.isEmpty(realName)) {
                    continue;
                }

                if (noticeEntity.getNoticeType() == 12) {
                    data.add(repairStr + "\r\n" + GetDateUtils.dateToDateTimeString(noticeEntity.getCreateTime()));
                } else if (noticeEntity.getNoticeType() == 27) {
                    data.add(installStr + "\r\n" + GetDateUtils.dateToDateTimeString(noticeEntity.getCreateTime()));
                } else if (noticeEntity.getNoticeType() == 29) {
                    data.add(quoteStr + "\r\n" + GetDateUtils.dateToDateTimeString(noticeEntity.getCreateTime()));
                } else if (noticeEntity.getNoticeType() == 37) {
                    data.add(designStr + "\r\n" + GetDateUtils.dateToDateTimeString(noticeEntity.getCreateTime()));
                } else if (noticeEntity.getNoticeType() == 61) {
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

    public void doHttpNews() {

        EanfangHttp.get(NewApiService.GET_PUSH_NEWS_WORKER).execute(new EanfangCallback<NoticeEntity>(getActivity(), false, NoticeEntity.class, true, (list -> {
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
        EanfangHttp.get(UserApi.HOME_GET_ORDER_NUM).execute(new EanfangCallback<HomeOrderNumBean>(getActivity(), false, HomeOrderNumBean.class, (bean -> {
            SetQBadgeView.getSingleton().setBadgeView(getActivity(), findViewById(R.id.tv_reparir_order), bean.getRepair());// 报修
            SetQBadgeView.getSingleton().setBadgeView(getActivity(), findViewById(R.id.tv_install_order), bean.getInstall());// 报装
            SetQBadgeView.getSingleton().setBadgeView(getActivity(), findViewById(R.id.tv_design_order), bean.getDesign());//设计
            SetQBadgeView.getSingleton().setBadgeView(getActivity(), findViewById(R.id.tv_maintain_order), bean.getMaintain());//维保
            SetQBadgeView.getSingleton().setBadgeView(getActivity(), findViewById(R.id.tv_inside_price), bean.getQuote());//报价

        })));
    }

    /**
     * 填充
     */
    private void initDatastatisticsData(HomeDatastisticeBean bean) {
        clientDataList = bean.getGroup();
        homeDataAdapter = new HomeDataAdapter(R.layout.layout_home_data);
        rvData.setAdapter(homeDataAdapter);
        homeDataAdapter.bindToRecyclerView(rvData);
        homeDataAdapter.setNewData(clientDataList);
        tvReapirTotal.setText(bean.getAll() + "");
        tvDesitnTotal.setText(bean.getDesign().getNum() + "");
        tvInstallTotal.setText(bean.getInstall().getNum() + "");
    }

    private void initFalseData() {
        homeDataAdapter = new HomeDataAdapter(R.layout.layout_home_data);
        rvData.setAdapter(homeDataAdapter);
        homeDataAdapter.bindToRecyclerView(rvData);
        homeDataAdapter.setNewData(clientDataList);
    }
}
