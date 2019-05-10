package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.AllMessageBean;
import com.eanfang.ui.activity.kpbs.KPBSActivity;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermKit;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.eanfang.model.sys.OrgEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.CameraActivity;
import net.eanfang.client.ui.activity.worksapce.CustomerServiceActivity;
import net.eanfang.client.ui.activity.worksapce.FaultRecordListActivity;
import net.eanfang.client.ui.activity.worksapce.NoContentActivity;
import net.eanfang.client.ui.activity.worksapce.OfferAndPayOrderActivity;
import net.eanfang.client.ui.activity.worksapce.PersonOfferAndPayOrderActivity;
import net.eanfang.client.ui.activity.worksapce.WebActivity;
import net.eanfang.client.ui.activity.worksapce.defendlog.DefendLogParentActivity;
import net.eanfang.client.ui.activity.worksapce.equipment.EquipmentListActivity;
import net.eanfang.client.ui.activity.worksapce.maintenance.MaintenanceActivity;
import net.eanfang.client.ui.activity.worksapce.oa.check.CheckListActivity;
import net.eanfang.client.ui.activity.worksapce.oa.task.TaskAssignmentListActivity;
import net.eanfang.client.ui.activity.worksapce.oa.workreport.WorkReportListActivity;
import net.eanfang.client.ui.activity.worksapce.openshop.OpenShopLogParentActivity;
import net.eanfang.client.ui.activity.worksapce.worktalk.WorkTalkControlActivity;
import net.eanfang.client.ui.activity.worksapce.worktransfer.WorkTransferControlActivity;
import net.eanfang.client.ui.widget.CompanyListView;
import net.eanfang.client.ui.widget.SignCtrlView;

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
 * @desc 工作台
 */
public class WorkspaceFragment extends BaseFragment {

    /**
     * 箭头
     */
    private ImageView mIvDownIcon;
    private TextView tvCompanyName;
    private SimpleDraweeView iv_company_logo;

    /**
     * 消息气泡
     */
    private QBadgeView qBadgeViewReport = new QBadgeView(EanfangApplication.get().getApplicationContext());
    private QBadgeView qBadgeViewTask = new QBadgeView(EanfangApplication.get().getApplicationContext());
    private QBadgeView qBadgeViewInspect = new QBadgeView(EanfangApplication.get().getApplicationContext());
    private int mReport = 0;
    private int mTask = 0;
    private int mInspect = 0;
    /**
     * 切换公司 pop
     */
    private CompanyListView selectCompanyPop;
    List<OrgEntity> mList = new ArrayList<>();

    private RotateAnimation rotate;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_workspace;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    public void onResume() {
        super.onResume();
        doHttpOrderNums();
        String companyName = EanfangApplication.getApplication().getUser()
                .getAccount().getDefaultUser().getCompanyEntity().getOrgName();
        if ("个人".equals(companyName)) {
            tvCompanyName.setText(companyName + "(点击切换公司)");
        } else {
            tvCompanyName.setText(companyName);
        }
    }

    @Override
    protected void initView() {
        tvCompanyName = findViewById(R.id.tv_company_name);
        mIvDownIcon = findViewById(R.id.iv_down_icon);

        iv_company_logo = findViewById(R.id.iv_company_logo);
        setLogpic();
        //切换公司
        findViewById(R.id.ll_switch_company).setOnClickListener(v -> {
            doChangeCompany();
        });

        // 汇报
        qBadgeViewReport.bindTarget(findViewById(R.id.tv_work_report))
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(5, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(11, 0, true)
                .setBadgeTextSize(11, true);
        // 任务
        qBadgeViewTask.bindTarget(findViewById(R.id.tv_work_task))
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(5, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(11, 0, true)
                .setBadgeTextSize(11, true);
        //检查
        qBadgeViewInspect.bindTarget(findViewById(R.id.tv_work_inspect))
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(5, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(11, 0, true)
                .setBadgeTextSize(11, true);

    }

    /**
     * 切换公司
     */
    private void doChangeCompany() {

        EanfangHttp.post(NewApiService.GET_COMPANY_ALL_LIST)
                .params("accId", EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getAccId() + "")
                // 公司类型（单位类型0平台总公司1城市平台公司2企事业单位3安防公司）
                .params("orgType", "2")
                .execute(new EanfangCallback<OrgEntity>(getActivity(), false, OrgEntity.class, true, bean -> {
                    mList = bean;
                    if (mList == null || mList.size() <= 0) {
                        showToast("暂无客户公司");
                        return;
                    }
                    rotate = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF,
                            0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotate.setDuration(300);
                    rotate.setFillAfter(true);
                    selectCompanyPop = new CompanyListView(getActivity(), mList, ((name, url) -> {
                        if ("个人".equals(name)) {
                            tvCompanyName.setText(name + "(点击切换公司)");
                        } else {
                            tvCompanyName.setText(name);
                        }
                        if (url != null) {
                            iv_company_logo.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + url));
                        } else {
                            iv_company_logo.setImageURI("");
                        }
                        selectCompanyPop.dismiss();
                        doHttpOrderNums();
                    }));
                    selectCompanyPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            selectCompanyPop.backgroundAlpha(1.0f);
                            mIvDownIcon.clearAnimation();
                        }
                    });
                    selectCompanyPop.showAsDropDown(findViewById(R.id.ll_company_top));
                }));
    }

    private void setLogpic() {
        List<OrgEntity> orgUnitEntityList = new ArrayList<>(EanfangApplication.getApplication().getUser().getAccount().getBelongCompanys());
        Long defaultOrgid = EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgId();
        List<String> defaultPic = Stream.of(orgUnitEntityList).filter(bean -> bean.getOrgUnitEntity() != null
                && bean.getOrgUnitEntity().getLogoPic() != null
                && bean.getOrgUnitEntity().getOrgId().equals(defaultOrgid)).map(be -> v(() -> be.getOrgUnitEntity().getLogoPic())).toList();
        String imgUrl = v(() -> defaultPic.get(0));
        if (!StringUtils.isEmpty(imgUrl)) {
            iv_company_logo.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + imgUrl));
        }

    }

    @Override
    protected void setListener() {
        progressCtrl();
        helpTools();
        teamWork();
        //客服
        findViewById(R.id.iv_service).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), CustomerServiceActivity.class));
        });

//        //遗留故障
//        findViewById(R.id.ll_leave_bug).setOnClickListener((v) -> {
//            new LeaveBugView(getActivity(), true).show();
//        });
//
//        //合作公司
//        findViewById(R.id.ll_partner_ctrl).setOnClickListener((v) -> {
//            startActivity(new Intent(getActivity(), PartnerActivity.class));
//        });


    }

    /**
     * 过程管控
     */
    private void progressCtrl() {
        //报价管控
        findViewById(R.id.tv_work_price).setOnClickListener((v) -> {
            if (PermKit.get().getQuoteListPrem()) {
                // 当前登陆人公司是否认证
                if (EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyEntity().getVerifyStatus() == 2) {
                    startActivity(new Intent(getActivity(), OfferAndPayOrderActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), PersonOfferAndPayOrderActivity.class));
                }
            }
        });
        //维保管控
        findViewById(R.id.tv_work_maintain).setOnClickListener((v) -> {
//            new MaintainCtrlView(getActivity(), true).show();
            if (PermKit.get().getMaintenanceListPrem()) {
                startActivity(new Intent(getActivity(), MaintenanceActivity.class));
            }
        });


    }

    /**
     * 辅助工具
     */
    private void helpTools() {
        //相机
        findViewById(R.id.tv_work_camera).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), CameraActivity.class));
        });
        //码率
        findViewById(R.id.tv_work_calculate).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), KPBSActivity.class));
        });
        //天猫安防
        findViewById(R.id.tv_work_tm).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), WebActivity.class)
                    .putExtra("url", "https://list.tmall.com/search_product.htm?q=%B0%B2%B7%C0&type=p&vmarket=&spm=875.7931836%2FB.a2227oh.d100&from=mallfp..pc_1_searchbutton")
                    .putExtra("title", "天猫安防"));
        });
        //京东安防
        findViewById(R.id.tv_work_jd).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), WebActivity.class)
                    .putExtra("url", "https://list.jd.com/list.html?cat=670,716,7374")
                    .putExtra("title", "京东安防"));
        });
        //专家解答
        findViewById(R.id.tv_work_answer).setOnClickListener(v -> JumpItent.jump(getActivity(), NoContentActivity.class));
        //行业知识
        findViewById(R.id.tv_work_knowledge).setOnClickListener(v ->
                startActivity(new Intent(getActivity(), WebActivity.class)
                        .putExtra("url", "http://www.1anfang.com/news")
                        .putExtra("title", "行业知识")));
        //变更记录
        findViewById(R.id.tv_work_change).setOnClickListener(v -> JumpItent.jump(getActivity(), NoContentActivity.class));
        //在线文档
        findViewById(R.id.tv_work_file).setOnClickListener(v -> JumpItent.jump(getActivity(), NoContentActivity.class));

    }

    /**
     * 协同办公
     */
    private void teamWork() {
        //签到
        findViewById(R.id.tv_work_sign).setOnClickListener((v) -> {
            // 检查有无权限
            List<String> ss = new ArrayList<>();
            new SignCtrlView(getActivity()).show();
        });

        //工作汇报
        findViewById(R.id.tv_work_report).setOnClickListener((v) -> {
//            new ReportCtrlView(getActivity(), true).show();
//            Intent intent = new Intent(getActivity(), ReportParentActivity.class);
            if (PermKit.get().getWorkReportListPrem()) {
                Intent intent = new Intent(getActivity(), WorkReportListActivity.class);
                startActivity(intent);
            }
        });

        //布置任务
        findViewById(R.id.tv_work_task).setOnClickListener((v) -> {
//            new TaskCtrlView(getActivity(), true).show();
//            Intent intent = new Intent(getActivity(), TaskParentActivity.class);
            if (PermKit.get().getWorkTaskListPrem()) {
                Intent intent = new Intent(getActivity(), TaskAssignmentListActivity.class);
                startActivity(intent);
            }
        });

        //设备点检
        findViewById(R.id.tv_work_inspect).setOnClickListener((v) -> {
//            new WorkCheckCtrlView(getActivity(), true).show();
            if (!PermKit.get().getWorkInspectListPrem()) {
                return;
            }
            Intent intent = new Intent(getActivity(), CheckListActivity.class);
            startActivity(intent);
        });

        //开店日志
        findViewById(R.id.tv_shop_log).setOnClickListener((v) -> {
//            new OpenShopLogView(getActivity(), true).show();
            Intent intent = new Intent(getActivity(), OpenShopLogParentActivity.class);
            startActivity(intent);
        });

        //布防日志
        findViewById(R.id.tv_defend_log).setOnClickListener((v) -> {
//            new DefendLogView(getActivity(), true).show();
            Intent intent = new Intent(getActivity(), DefendLogParentActivity.class);
            startActivity(intent);
        });


        //故障记录
        findViewById(R.id.tv_work_fault).setOnClickListener((v) -> {
            if (PermKit.get().getFailureListPerm()) {
                Intent intent = new Intent(getActivity(), FaultRecordListActivity.class);
                startActivity(intent);
            }
        });

        //设备库
        findViewById(R.id.tv_work_library).setOnClickListener((v) -> {
            if (!PermKit.get().getDeviceArchiveListPerm()) {
                return;
            }
            Intent intent = new Intent(getActivity(), EquipmentListActivity.class);
            startActivity(intent);
        });
        //交接班
        findViewById(R.id.tv_work_transfer).setOnClickListener((v) -> {
            Intent intent = new Intent(getActivity(), WorkTransferControlActivity.class);
            startActivity(intent);
        });
        //面谈员工
        findViewById(R.id.tv_work_talk).setOnClickListener((v) -> {
            Intent intent = new Intent(getActivity(), WorkTalkControlActivity.class);
            startActivity(intent);
        });

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
        // 汇报
        if (bean.getReport() > 0) {
            mReport = bean.getReport();
        } else {
            mReport = 0;
        }
        qBadgeViewReport.setBadgeNumber(mReport);
        // 任务
        if (bean.getTask() > 0) {
            mTask = bean.getTask();
        } else {
            mTask = 0;
        }
        qBadgeViewTask.setBadgeNumber(mTask);
        //检查
        if (bean.getInspect() > 0) {
            mInspect = bean.getInspect();
        } else {
            mInspect = 0;
        }
        qBadgeViewInspect.setBadgeNumber(mInspect);
        /**
         * 底部红点更新
         * */
        EventBus.getDefault().post(bean);
    }

}
