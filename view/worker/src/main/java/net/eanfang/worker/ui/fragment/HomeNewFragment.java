package net.eanfang.worker.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.kit.V;
import com.eanfang.biz.model.bean.AllMessageBean;
import com.eanfang.biz.model.entity.HomeNewsBean;
import com.eanfang.biz.model.entity.HomeToDoOrderBean;
import com.eanfang.biz.model.entity.OrgEntity;
import com.eanfang.config.Config;
import com.eanfang.config.EanfangConst;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.CallUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermKit;
import com.eanfang.witget.BannerView;
import com.eanfang.witget.HomeScanPopWindow;
import com.eanfang.witget.RollTextView;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.CameraActivity;
import net.eanfang.worker.ui.activity.NewOrderActivity;
import net.eanfang.worker.ui.activity.worksapce.OfferAndPayOrderParentActivity;
import net.eanfang.worker.ui.activity.worksapce.oa.workreport.WorkReportListActivity;
import net.eanfang.worker.ui.activity.worksapce.online.ExpertOnlineActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.RepairCtrlActivity;
import net.eanfang.worker.ui.activity.worksapce.repair.SolveModeActivity;
import net.eanfang.worker.ui.activity.worksapce.scancode.ScanCodeActivity;
import net.eanfang.worker.ui.widget.CompanyListView;
import net.eanfang.worker.ui.widget.CustomHomeViewPager;
import net.eanfang.worker.ui.widget.SignCtrlView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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

public class HomeNewFragment extends BaseFragment {

    @BindView(R.id.tv_homeTitle)
    TextView tvHomeTitle;
    @BindView(R.id.tv_to_do_order_title)
    TextView tvToDoOrderTitle;
    @BindView(R.id.tv_order_address)
    TextView tvOrderAddress;
    @BindView(R.id.tv_order_company)
    TextView tvOrderCompany;
    @BindView(R.id.ll_to_do_order)
    LinearLayout llToDoOrder;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_inside_price)
    TextView tvInsidePrice;
    @BindView(R.id.tv_work_report)
    TextView tvWorkReport;
    @BindView(R.id.tv_do_phone)
    TextView tvDoPhone;
    private BannerView bannerView;
    //头部标题
    private RollTextView rollTextView;

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

    @Override
    protected void initData(Bundle arguments) {
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_home_new;
    }

    @Override
    protected void initView() {
        homeScanPopWindow = new HomeScanPopWindow(getActivity(), true, scanSelectItemsOnClick);
        homeScanPopWindow.setOnDismissListener(() -> homeScanPopWindow.backgroundAlpha(1.0f));
        initIconClick();
        initLoopView();
        doHttpNews();
        initNum();
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
                            tvHomeTitle.setText(name);
                        } else {
                            tvHomeTitle.setText(name);
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
                    selectCompanyPop.showAsDropDown(findViewById(R.id.tv_homeTitle));
                }));
    }

    private void initNum() {
        /**
         * 报价
         * */
        qBadgeViewQuota.bindTarget(findViewById(R.id.tv_inside_price))
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(5, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(11, 0, true)
                .setBadgeTextSize(10, true);
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
        doGetToDoOrder();
        doHttpOrderNums();
    }

    /**
     * 新订单
     */
    private void doGetToDoOrder() {
        EanfangHttp.get(NewApiService.GET_HOME_TO_DO_ORDER).execute(new EanfangCallback<HomeToDoOrderBean>(getActivity(), false, HomeToDoOrderBean.class, (bean -> {
            if (bean != null) {
                tvToDoOrderTitle.setText(bean.getMsg());
                tvOrderAddress.setText(Config.get().getAddressByCode(bean.getOrder().getPlaceCode()) + bean.getOrder().getAddress());
                tvOrderCompany.setText(bean.getOrder().getContactCompany());
                tvOrderTime.setText(DateUtil.date(bean.getOrder().getCreateTime()).toString());
                mOrderId = bean.getOrder().getId();
                mOrderPhone = bean.getOrder().getContactPhone();
                llToDoOrder.setVisibility(View.VISIBLE);
            } else {
                llToDoOrder.setVisibility(View.GONE);
            }
        })));

    }

    private void initViewPager1() {
        String[] titles = {"当日维修", "当日安装", "当日设计"};
        ArrayList<Fragment> fragments = new ArrayList<>();
        CustomHomeViewPager vpDataStatis = findViewById(R.id.vp_datastatistics);

        fragments.clear();
        fragments.add(HomeDataStatisticsFragment.getInstance(titles[0], 1));
        fragments.add(HomeDataStatisticsFragment.getInstance(titles[1], 2));
        fragments.add(HomeDataStatisticsFragment.getInstance(titles[2], 3));

        mAdapter = new MyPagerAdapter(titles, fragments);
        vpDataStatis.setAdapter(mAdapter);
        // 设置不可滑动
        vpDataStatis.setScanScroll(false);
        vpDataStatis.setCurrentItem(0);
    }


    /**
     * 工作按钮
     */
    private void initIconClick() {

        // 电话解决
        tvDoPhone.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putLong("orderId", mOrderId);
            JumpItent.jump(getActivity(), SolveModeActivity.class, bundle, RepairCtrlActivity.REFREST_ITEM);
            //给客户联系人打电话
            CallUtils.call(getActivity(), V.v(() -> mOrderPhone));
        });
        //报价
        tvInsidePrice.setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), OfferAndPayOrderParentActivity.class));
        });
        //工作日报
        tvWorkReport.setOnClickListener((v) -> {
            if (!PermKit.get().getWorkReportListPrem()) {
                return;
            }
            Intent intent = new Intent(getActivity(), WorkReportListActivity.class);
            startActivity(intent);
        });
        //切换公司
        tvHomeTitle.setOnClickListener((v) -> {
            doChangeCompany();
        });
        //专家问答
        findViewById(R.id.tv_onLine).setOnClickListener((v) -> {//wq==
            if (workerApprove()) {
                startActivity(new Intent(getActivity(), ExpertOnlineActivity.class));
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
    private void initRollTextView(List<String> list) {
        rollTextView = findViewById(R.id.home_recommand_ad_text);
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
        rollTextView.setViews(views);
        rollTextView.setOnItemClickListener((position, view) -> {
//            showToast("暂无可点");
            startActivity(new Intent(getActivity(), NewOrderActivity.class));
        });
    }

    public void doHttpNews() {

        EanfangHttp.get(NewApiService.GET_HOME_ORDER).execute(new EanfangCallback<HomeNewsBean>(getActivity(), false, HomeNewsBean.class, (list -> {
            initRollTextView(list.getList());
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
}

