package net.eanfang.worker.ui.fragment;

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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.biz.model.security.SecurityLikeBean;
import com.eanfang.config.EanfangConst;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.AllMessageBean;
import com.eanfang.biz.model.NoticeEntity;
import com.eanfang.biz.model.security.SecurityListBean;
import com.eanfang.model.security.SecurityLikeStatusBean;
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
import com.flyco.tablayout.SlidingTabLayout;
import com.photopicker.com.util.BGASpaceItemDecoration;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.MapTestActivity;
import net.eanfang.worker.ui.activity.CameraActivity;
import net.eanfang.worker.ui.activity.worksapce.InstallOrderParentActivity;
import net.eanfang.worker.ui.activity.worksapce.MineTaskPublishListSendParentActivity;
import net.eanfang.worker.ui.activity.worksapce.OfferAndPayOrderParentActivity;
import net.eanfang.worker.ui.activity.worksapce.TakeTaskListActivity;
import net.eanfang.worker.ui.activity.worksapce.WebActivity;
import net.eanfang.worker.ui.activity.worksapce.design.DesignActivity;
import net.eanfang.worker.ui.activity.worksapce.maintenance.MaintenanceActivity;
import net.eanfang.worker.ui.activity.worksapce.online.ExpertOnlineActivity;
import net.eanfang.worker.ui.activity.worksapce.online.FaultExplainActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.RepairCtrlActivity;
import net.eanfang.worker.ui.activity.worksapce.scancode.ScanCodeActivity;
import net.eanfang.worker.ui.activity.worksapce.security.SecurityDetailActivity;
import net.eanfang.worker.ui.activity.worksapce.security.SecurityListActivity;
import net.eanfang.worker.ui.activity.worksapce.security.SecurityPersonalActivity;
import net.eanfang.worker.ui.activity.worksapce.tender.WorkerTenderControlActivity;
import net.eanfang.worker.ui.adapter.HomeWaitAdapter;
import net.eanfang.worker.ui.adapter.security.SecurityListAdapter;
import net.eanfang.worker.ui.widget.CustomHomeViewPager;
import net.eanfang.worker.ui.widget.HomeWaitIndicator;
import net.eanfang.worker.ui.widget.SignCtrlView;
import net.eanfang.worker.util.ImagePerviewUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
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

    private BannerView bannerView;
    //头部标题
    private TextView tvHomeTitle;
    private RollTextView rollTextView;

    private RelativeLayout rlAllData;


    // 扫码Popwindow
    private HomeScanPopWindow homeScanPopWindow;

    /**
     * 图标数量
     */
    private QBadgeView qBadgeViewRepair = new QBadgeView(WorkerApplication.get().getApplicationContext());
    private QBadgeView qBadgeViewDesign = new QBadgeView(WorkerApplication.get().getApplicationContext());
    private QBadgeView qBadgeViewMaintain = new QBadgeView(WorkerApplication.get().getApplicationContext());
    private QBadgeView qBadgeViewQuota = new QBadgeView(WorkerApplication.get().getApplicationContext());
    private QBadgeView qBadgeViewInstall = new QBadgeView(WorkerApplication.get().getApplicationContext());
    private int mRepair = 0;
    private int mDesign = 0;
    private int mInstall = 0;
    private int mMaintain = 0;
    private int mQuota = 0;

    /**
     * 统计
     */
    private SlidingTabLayout tlDataStatisticsList;
    private CustomHomeViewPager customHomeViewPager;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"当日维修", "当日安装", "当日设计"};
    private MyPagerAdapter mAdapter;

    /**
     * 今日待办
     */
    private RecyclerView rvWait;
    private HomeWaitAdapter waitAdapter;
    private List mWaitList = new ArrayList();
    private HomeWaitIndicator homeWaitIndicator;
    private LinearLayoutManager layoutManager;
    /**
     * 安防圈
     */
    private RecyclerView rvSecurity;
    private SecurityListAdapter securityListAdapter;
    private TextView tvNoSecurity;
    private TextView mTvSecurityNewMessage;
    private RelativeLayout rlSecurityNewMessage;
    private int mSecurityNum;
    private ArrayList<String> picList = new ArrayList<>();
    private String[] pics = null;
    @Override
    protected void initData(Bundle arguments) {
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        rvWait = findViewById(R.id.rv_wait);
        rvSecurity = findViewById(R.id.rv_security);
        homeWaitIndicator = findViewById(R.id.indicator);
        tlDataStatisticsList = (SlidingTabLayout) findViewById(R.id.tl_datastatistics);
        rlAllData = (RelativeLayout) findViewById(R.id.rl_allData);
        customHomeViewPager = (CustomHomeViewPager) findViewById(R.id.vp_datastatistics);
        tvHomeTitle = (TextView) findViewById(R.id.tv_homeTitle);
        tvNoSecurity = findViewById(R.id.tv_noSecurity);
        mTvSecurityNewMessage = findViewById(R.id.tv_security_count);
        rlSecurityNewMessage = findViewById(R.id.rl_security_message);
        homeScanPopWindow = new HomeScanPopWindow(getActivity(), true, scanSelectItemsOnClick);
        homeScanPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                homeScanPopWindow.backgroundAlpha(1.0f);
            }
        });

        initIconClick();
        initLoopView();

        /**
         * 统计
         * */
        mFragments.clear();
        mFragments.add(HomeDataStatisticsFragment.getInstance(mTitles[0], 1));
        mFragments.add(HomeDataStatisticsFragment.getInstance(mTitles[1], 2));
        mFragments.add(HomeDataStatisticsFragment.getInstance(mTitles[2], 0));

        mAdapter = new MyPagerAdapter(this.getChildFragmentManager());
        customHomeViewPager.setAdapter(mAdapter);
        // 设置不可滑动
        customHomeViewPager.setScanScroll(false);
        tlDataStatisticsList.setViewPager(customHomeViewPager, mTitles, getActivity(), mFragments);
        customHomeViewPager.setCurrentItem(0);
        tlDataStatisticsList.setCurrentTab(0);

        initCount();
        doHttpNews();
        initNum();
        initWait();
        initSecurity();
    }

    private void initNum() {
        // 报修
        qBadgeViewRepair.bindTarget(findViewById(R.id.tv_reparir_order))
                .setBadgeNumber(mRepair)
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(5, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(11, 0, true)
                .setBadgeTextSize(11, true);
        // 报装
        qBadgeViewInstall.bindTarget(findViewById(R.id.tv_install_order))
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(5, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(11, 0, true)
                .setBadgeTextSize(11, true);
        //设计
        qBadgeViewDesign.bindTarget(findViewById(R.id.tv_design_order))
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(5, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(11, 0, true)
                .setBadgeTextSize(11, true);
        //维保
        qBadgeViewMaintain.bindTarget(findViewById(R.id.tv_maintain_order))
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(5, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(11, 0, true)
                .setBadgeTextSize(11, true);
        /**
         * 报价
         * */
        qBadgeViewQuota.bindTarget(findViewById(R.id.tv_inside_price))
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(5, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(11, 0, true)
                .setBadgeTextSize(11, true);
    }

    private void initWait() {

        /**
         * 今日待办
         * */
        mWaitList.add("报修订单MO111111111111111");
        mWaitList.add("报修订单MO222222222222222");
        mWaitList.add("报修订单MO333333333333333");
        mWaitList.add("报修订单MO444444444444444");
        mWaitList.add("报修订单MO555555555555555");
        mWaitList.add("报修订单MO666666666666666");
        waitAdapter = new HomeWaitAdapter();
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvWait.setLayoutManager(layoutManager);
        waitAdapter.bindToRecyclerView(rvWait);
        waitAdapter.setNewData(mWaitList);
        rvWait.setHasFixedSize(true);
        rvWait.scrollToPosition(mWaitList.size() * 10);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rvWait);
        homeWaitIndicator.setNumber(mWaitList.size());
    }


    /**
     * 安防圈
     */
    private void initSecurity() {
        securityListAdapter = new SecurityListAdapter( false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvSecurity.setLayoutManager(layoutManager);
        rvSecurity.setNestedScrollingEnabled(false);
        rvSecurity.addItemDecoration(new BGASpaceItemDecoration(20));
        securityListAdapter.bindToRecyclerView(rvSecurity);
        doGetSecurityData();
    }

    private void doGetSecurityData() {
        QueryEntry mQueryEntry = new QueryEntry();
        mQueryEntry.setPage(1);
        mQueryEntry.setSize(3);
        EanfangHttp.post(NewApiService.SECURITY_RECOMMEND)
                .upJson(JsonUtils.obj2String(mQueryEntry))
                .execute(new EanfangCallback<SecurityListBean>(getActivity(), true, SecurityListBean.class) {

                    @Override
                    public void onSuccess(SecurityListBean bean) {
                        securityListAdapter.getData().clear();
                        securityListAdapter.setNewData(bean.getList());
                        if (bean.getList().size() > 0) {
                            tvNoSecurity.setVisibility(View.GONE);
                        } else {
                            tvNoSecurity.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        String orgName = v(() -> (WorkerApplication.get().getLoginBean().getAccount().getDefaultUser().getCompanyEntity().getOrgName()));
        if (("个人").equals(orgName)) {
            tvHomeTitle.setText("易安防");
        } else {
            tvHomeTitle.setText(orgName);
        }
        doHttpOrderNums();
        doGetSecurityData();
    }


    /**
     * 工作按钮
     */
    private void initIconClick() {
        //报修订单
        findViewById(R.id.tv_reparir_order).setOnClickListener((v) -> {
//            if (!PermKit.get().getRepairListPerm()) return;
            if (workerApprove()) {
                startActivity(new Intent(getActivity(), RepairCtrlActivity.class));
            }
        });
        //专家问答
        findViewById(R.id.tv_onLine).setOnClickListener((v) -> {//wq==
//            if (!PermKit.get().getRepairListPerm()) return;
            if (workerApprove()) {
                startActivity(new Intent(getActivity(), ExpertOnlineActivity.class));
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
                if (!PermKit.get().getBidListPrem()) {
                    return;
                }
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
        //招标信息
        findViewById(R.id.tv_tender).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), WorkerTenderControlActivity.class));
        });
        //扫描二维码
        findViewById(R.id.iv_scan).setOnClickListener((v) -> {
            homeScanPopWindow.showAsDropDown(findViewById(R.id.iv_scan));
            homeScanPopWindow.backgroundAlpha(0.5f);
        });
        // 安防圈
        findViewById(R.id.rl_security).setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("mSecurityNum", mSecurityNum);
            JumpItent.jump(getActivity(), SecurityListActivity.class, bundle);
        });
        findViewById(R.id.iv_security_cancle).setOnClickListener((v) -> {
            rlSecurityNewMessage.setVisibility(View.GONE);
        });
        findViewById(R.id.ll_security_new).setOnClickListener((v) -> {
            JumpItent.jump(getActivity(), SecurityPersonalActivity.class);
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

    @Override
    protected void setListener() {
        findViewById(R.id.iv_camera).setOnClickListener(v -> startActivity(new Intent(getActivity(), CameraActivity.class)));
        rvWait.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int i = layoutManager.findFirstVisibleItemPosition() % mWaitList.size();
                    homeWaitIndicator.setPosition(i);
                }
            }
        });
        securityListAdapter.setOnItemClickListener((adapter, view, position) -> {
            doJump(position, false);
        });
        securityListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.ll_comments:
                    doJump(position, true);
                    break;
                case R.id.ll_pic:
                    picList.clear();
                    pics = securityListAdapter.getData().get(position).getSpcImg().split(",");
                    picList.addAll(Stream.of(Arrays.asList(pics)).map(url -> BuildConfig.OSS_SERVER + (url).toString()).toList());
                    ImagePerviewUtil.perviewImage(getActivity(), picList);
                    break;
                case R.id.ll_like:
                    doLike(position, securityListAdapter.getData().get(position));
                    break;
                case R.id.tv_isFocus:
                case R.id.iv_share:
                case R.id.ll_question:
                case R.id.rl_video:
                    doJump(position, false);
                    break;
                default:
                    break;
            }
        });
    }
    /**
     * 进行点赞
     */
    private void doLike(int position, SecurityListBean.ListBean listBean) {
        SecurityLikeBean securityLikeBean = new SecurityLikeBean();
        securityLikeBean.setAsId(listBean.getSpcId());
        securityLikeBean.setType("0");
        /**
         *状态：0 点赞 1 未点赞
         * */
        if (listBean.getLikeStatus() == 0) {
            listBean.setLikeStatus(1);
            listBean.setLikesCount(listBean.getLikesCount());
            securityLikeBean.setLikeStatus("1");
        } else {
            listBean.setLikeStatus(0);
            listBean.setLikesCount(listBean.getLikesCount());
            securityLikeBean.setLikeStatus("0");
        }
        securityLikeBean.setLikeUserId(WorkerApplication.get().getUserId());
        securityLikeBean.setLikeCompanyId(WorkerApplication.get().getLoginBean().getAccount().getDefaultUser().getCompanyId());
        securityLikeBean.setLikeTopCompanyId(WorkerApplication.get().getLoginBean().getAccount().getDefaultUser().getTopCompanyId());
        EanfangHttp.post(NewApiService.SERCURITY_LIKE)
                .upJson(JSONObject.toJSONString(securityLikeBean))
                .execute(new EanfangCallback<SecurityLikeStatusBean>(getActivity(), true, SecurityLikeStatusBean.class, bean -> {
                    getActivity().runOnUiThread(() -> {
                        securityListAdapter.getData().get(position).setLikeStatus(bean.getLikeStatus());
                        securityListAdapter.getData().get(position).setLikesCount(bean.getLikesCount());
                        securityListAdapter.notifyItemChanged(position);
                    });
                }));
    }

    public void doJump(int position, boolean isCommon) {
        //专家问答
        if (securityListAdapter.getData().get(position).getType() == 1) {
            Bundle bundle_question = new Bundle();
            bundle_question.putInt("QuestionIdZ", Integer.parseInt(securityListAdapter.getData().get(position).getQuestionId()));
            JumpItent.jump(getActivity(), FaultExplainActivity.class, bundle_question);
        } else {
            Bundle bundle = new Bundle();
            bundle.putLong("spcId", securityListAdapter.getData().get(position).getSpcId());
            bundle.putBoolean("isCommon", isCommon);
            JumpItent.jump(getActivity(), SecurityDetailActivity.class, bundle);
        }
    }


    /**
     * 统计
     */

    private void initCount() {
        rlAllData.setOnClickListener((v) -> {
//            startActivity(new Intent(getActivity(), DataStaticsticsListActivity.class));
        });
    }

    private void jumpWebview() {
        boolean isHave = WorkerApplication.get().getLoginBean().getPerms().contains("top:statistics:count");
        if (isHave == true) {
            String token = WorkerApplication.get().getLoginBean().getToken();
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

                if (noticeEntity.getNoticeType() == 22) {
                    data.add(repairStr + "\r\n" + GetDateUtils.dateToDateTimeString(noticeEntity.getCreateTime()));
                } else if (noticeEntity.getNoticeType() == 40) {
                    data.add(installStr + "\r\n" + GetDateUtils.dateToDateTimeString(noticeEntity.getCreateTime()));
                } else if (noticeEntity.getNoticeType() == 42) {
                    data.add(quoteStr + "\r\n" + GetDateUtils.dateToDateTimeString(noticeEntity.getCreateTime()));
                } else if (noticeEntity.getNoticeType() == 51) {
                    data.add(designStr + "\r\n" + GetDateUtils.dateToDateTimeString(noticeEntity.getCreateTime()));
                } else if (noticeEntity.getNoticeType() == 62) {
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
     * 获取订单数量
     */
    private void doHttpOrderNums() {
        EanfangHttp.get(UserApi.ALL_MESSAGE).execute(new EanfangCallback<AllMessageBean>(getActivity(), false, AllMessageBean.class, (bean -> {
            doSetOrderNums(bean);
        })));
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
        //维保
        if (bean.getMaintain() > 0) {
            mMaintain = bean.getMaintain();
        } else {
            mMaintain = 0;
        }
        qBadgeViewMaintain.setBadgeNumber(mMaintain);
        //报价
        if (bean.getQuote() > 0) {
            mQuota = bean.getQuote();
        } else {
            mQuota = 0;
        }
        qBadgeViewQuota.setBadgeNumber(mQuota);
        // @我的和评论未读
        if (bean.getCommentNoRead() + bean.getNoReadCount() > 0) {
            mSecurityNum = bean.getCommentNoRead() + bean.getNoReadCount();
            mTvSecurityNewMessage.setText(mSecurityNum + "");
            rlSecurityNewMessage.setVisibility(View.VISIBLE);
        } else {
            mSecurityNum = 0;
            rlSecurityNewMessage.setVisibility(View.GONE);
        }
        /**
         * 底部红点更新
         * */
        EventBus.getDefault().post(bean);
    }

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
}

