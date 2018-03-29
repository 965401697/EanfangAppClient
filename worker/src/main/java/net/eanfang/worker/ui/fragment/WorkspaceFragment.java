package net.eanfang.worker.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.eanfang.application.EanfangApplication;
import com.eanfang.ui.base.BaseFragment;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.CameraActivity;
import net.eanfang.worker.ui.activity.worksapce.RepairCtrlActivity;
import net.eanfang.worker.ui.widget.CompanyListView;
import net.eanfang.worker.ui.widget.InstallCtrlView;
import net.eanfang.worker.ui.widget.MaintainCtrlView;
import net.eanfang.worker.ui.widget.PayOrderListCtrlView;
import net.eanfang.worker.ui.widget.ReportCtrlView;
import net.eanfang.worker.ui.widget.SignCtrlView;
import net.eanfang.worker.ui.widget.TakePubCtrlView;
import net.eanfang.worker.ui.widget.TaskCtrlView;
import net.eanfang.worker.ui.widget.TaskPubCtrlView;
import net.eanfang.worker.ui.widget.WorkCheckCtrlView;


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
    }


    @Override
    protected void setListener() {
        progressCtrl();
        helpTools();
        teamWork();

//        //遗留故障
//        findViewById(R.id.ll_leave_bug).setOnClickListener((v) -> {
//            new LeaveBugView(getActivity(), true).show();
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
        //维保管控
        findViewById(R.id.ll_order_manage).setOnClickListener((v) -> {
            new MaintainCtrlView(getActivity(), true).show();
        });
        //发包管控
        findViewById(R.id.ll_assign_packpage).setOnClickListener((v) -> {
            new TaskPubCtrlView(getActivity(), true).show();
        });
        //接包管控
        findViewById(R.id.ll_accept_packpage).setOnClickListener((v) -> {
            new TakePubCtrlView(getActivity(), true).show();
        });
        //免费设计
        findViewById(R.id.ll_design_add).setOnClickListener((v) -> {
//            new MaintainCtrlView(getActivity(), true).show();
            showToast("制造中。。。");
        });
        //维保
//        startActivity(new Intent(getActivity(), MaintenanceActivity.class));
    }

    /**
     * 协同办公
     */
    private void teamWork() {
        //签到
        findViewById(R.id.ll_sign).setOnClickListener((v) -> {
            new SignCtrlView(getActivity()).show();
        });

        //工作汇报
        findViewById(R.id.ll_work_report).setOnClickListener((v) -> {
            new ReportCtrlView(getActivity(), true).show();
        });

        //工作任务
        findViewById(R.id.ll_assignment_task).setOnClickListener((v) -> {
            new TaskCtrlView(getActivity(), true).show();
        });

        //检查
        findViewById(R.id.ll_job_check).setOnClickListener((v) -> {
            new WorkCheckCtrlView(getActivity(), true).show();
        });


    }

    private void helpTools() {
        //相机
        findViewById(R.id.ll_camera).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), CameraActivity.class));
        });
    }

}
