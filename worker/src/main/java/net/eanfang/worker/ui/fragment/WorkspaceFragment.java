package net.eanfang.worker.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.eanfang.application.EanfangApplication;
import com.eanfang.ui.base.BaseFragment;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.CameraActivity;
import net.eanfang.worker.ui.activity.worksapce.CheckActivity;
import net.eanfang.worker.ui.activity.worksapce.RepairCtrlActivity;
import net.eanfang.worker.ui.activity.worksapce.ReportActivity;
import net.eanfang.worker.ui.activity.worksapce.SignActivity;
import net.eanfang.worker.ui.activity.worksapce.TaskActivity;
import net.eanfang.worker.ui.widget.CompanyListView;
import net.eanfang.worker.ui.widget.InstallCtrlView;
import net.eanfang.worker.ui.widget.MaintainCtrlView;
import net.eanfang.worker.ui.widget.PayOrderListCtrlView;


/**
 * Created by MrHou
 *
 * @on 2017/11/10  15:07
 * @email houzhongzhou@yeah.net
 * @desc 工作台
 */
public class WorkspaceFragment extends BaseFragment {

    private TextView tvCompanyName;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_workspace;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView() {
        tvCompanyName = (TextView) findViewById(R.id.tv_company_name);
        tvCompanyName.setText(EanfangApplication.getApplication().getUser()
                .getAccount().getDefaultUser().getCompanyEntity().getOrgName());

        //切换公司
        findViewById(R.id.ll_switch_company).setOnClickListener(v -> {
            new CompanyListView(getActivity(), name -> tvCompanyName.setText(name)).show();
        });
//        //报价
//        findViewById(R.id.ll_quote).setOnClickListener((v) -> {
//            startActivity(new Intent(getActivity(), QuotationActivity.class));
//        });
//        //维保
//        findViewById(R.id.ll_maintain).setOnClickListener((v) -> {
//            startActivity(new Intent(getActivity(), MaintenanceActivity.class));
//
//        });
//        //发包
//        findViewById(R.id.ll_send_package).setOnClickListener((v) -> {
//            startActivity(new Intent(getActivity(), TaskPublishActivity.class));
//
//        });
//        //接包
//        findViewById(R.id.ll_receive_package).setOnClickListener((v) -> {
//            startActivity(new Intent(getActivity(), TakeTaskListActivity.class));
//
//        });


    }

    private void jumpSign(String title, int status) {
        Intent intent = new Intent(getActivity(), SignActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("status", status);
        startActivity(intent);
    }

    @Override
    protected void setListener() {
        progressCtrl();
        helpTools();
        teamWork();

//        //发包
//        findViewById(R.id.ll_project_ctrl).setOnClickListener((v) -> {
//            new TaskPubCtrlView(getActivity(), true).show();
//        });
//        //接包
//        findViewById(R.id.ll_task_rev_ctrl).setOnClickListener((v) -> {
//            new TakePubCtrlView(getActivity(), true).show();
//        });
//

//        //遗留故障
//        findViewById(R.id.ll_leave_bug).setOnClickListener((v) -> {
//            new LeaveBugView(getActivity(), true).show();
//        });
//        //统计
//        findViewById(R.id.ll_statistics_ctrl).setOnClickListener((v) -> {
//            boolean isHave = EanfangApplication.getApplication().getUser().getPerms().contains("top:statistics:count");
//            if (isHave == true) {
//                String token = EanfangApplication.getApplication().getUser().getToken();
//                startActivity(new Intent(getActivity(), WebActivity.class)
//                        .putExtra("url", "http:/worker.eanfang.net:8099/#/totalPhone?token=" + token)
//                        .putExtra("title", "数据统计"));
//            } else {
//                showToast("您没有权限");
//            }
//
//
//        });
//        //合作公司
//        findViewById(R.id.ll_cooperation_ctrl).setOnClickListener((v) -> {
//            startActivity(new Intent(getActivity(), PartnerActivity.class));
//        });
//

    }

    /**
     * 过程管控
     */
    private void progressCtrl() {
        //报修管控
        findViewById(R.id.ll_repair_manage).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), RepairCtrlActivity.class));
        });
        //报装管控
        findViewById(R.id.ll_install_manage).setOnClickListener((v) -> {
            new InstallCtrlView(getActivity(), true).show();
        });
        //报价管控
        findViewById(R.id.ll_quote_manage).setOnClickListener((v) -> {
            new PayOrderListCtrlView(getActivity(), true).show();
        });
        //维保
        findViewById(R.id.ll_order_manage).setOnClickListener((v) -> {
            new MaintainCtrlView(getActivity(), true).show();
        });
        //免费设计
        findViewById(R.id.ll_design_add).setOnClickListener((v) -> {
//            new MaintainCtrlView(getActivity(), true).show();
            showToast("制造中。。。");
        });
    }

    /**
     * 协同办公
     */
    private void teamWork() {
        //签到
        findViewById(R.id.ll_sign).setOnClickListener((v) -> {
            jumpSign("签到", 0);
        });
//        findViewById(R.id.ll_sign_out).setOnClickListener((v) -> {
//            jumpSign("签退", 1);
//        });

        //工作汇报
        findViewById(R.id.ll_work_report).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), ReportActivity.class));
        });
        //        //汇报
//        findViewById(R.id.ll_report_ctrl).setOnClickListener((v) -> {
//            new ReportCtrlView(getActivity(), true).show();
//        });
        //工作任务
        findViewById(R.id.ll_assignment_task).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), TaskActivity.class));
        });
        //        //任务
//        findViewById(R.id.ll_task_ctrl).setOnClickListener((v) -> {
//            new TaskCtrlView(getActivity(), true).show();
//        });
        //检查
        findViewById(R.id.ll_job_check).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), CheckActivity.class));
        });

//        //检查
//        findViewById(R.id.ll_check_ctrl).setOnClickListener((v) -> {
//            new WorkCheckCtrlView(getActivity(), true).show();
//        });

    }

    private void helpTools() {
        //相机
        findViewById(R.id.ll_camera).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), CameraActivity.class));
        });
    }

}
