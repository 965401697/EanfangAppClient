package net.eanfang.worker.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.eanfang.application.EanfangApplication;
import com.eanfang.ui.base.BaseFragment;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.CameraActivity;
import net.eanfang.worker.ui.activity.worksapce.CheckActivity;
import net.eanfang.worker.ui.activity.worksapce.MaintenanceActivity;
import net.eanfang.worker.ui.activity.worksapce.PartnerActivity;
import net.eanfang.worker.ui.activity.worksapce.QuotationActivity;
import net.eanfang.worker.ui.activity.worksapce.RepairCtrlActivity;
import net.eanfang.worker.ui.activity.worksapce.ReportActivity;
import net.eanfang.worker.ui.activity.worksapce.SignActivity;
import net.eanfang.worker.ui.activity.worksapce.TakeTaskListActivity;
import net.eanfang.worker.ui.activity.worksapce.TaskActivity;
import net.eanfang.worker.ui.activity.worksapce.TaskPublishActivity;
import net.eanfang.worker.ui.activity.worksapce.WebActivity;
import net.eanfang.worker.ui.widget.CompanyListView;
import net.eanfang.worker.ui.widget.InstallCtrlView;
import net.eanfang.worker.ui.widget.LeaveBugView;
import net.eanfang.worker.ui.widget.MaintainCtrlView;
import net.eanfang.worker.ui.widget.PayOrderListCtrlView;
import net.eanfang.worker.ui.widget.ReportCtrlView;
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
        //相机
        findViewById(R.id.iv_camera).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), CameraActivity.class));
        });
        //切换公司
        findViewById(R.id.ll_switch_company).setOnClickListener(v -> {
            new CompanyListView(getActivity(), name -> tvCompanyName.setText(name)).show();
        });
        //报价
        findViewById(R.id.ll_quote).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), QuotationActivity.class));
        });
        //维保
        findViewById(R.id.ll_maintain).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), MaintenanceActivity.class));

        });
        //发包
        findViewById(R.id.ll_send_package).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), TaskPublishActivity.class));

        });
        //接包
        findViewById(R.id.ll_receive_package).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), TakeTaskListActivity.class));

        });
        //汇报
        findViewById(R.id.ll_report).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), ReportActivity.class));
        });
        //任务
        findViewById(R.id.ll_task).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), TaskActivity.class));
        });
        //检查
        findViewById(R.id.ll_check).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), CheckActivity.class));
        });
        findViewById(R.id.ll_sign).setOnClickListener((v) -> {
            jumpSign("签到", 0);
        });
        findViewById(R.id.ll_sign_out).setOnClickListener((v) -> {
            jumpSign("签退", 1);
        });


    }

    private void jumpSign(String title, int status) {
        Intent intent = new Intent(getActivity(), SignActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("status", status);
        startActivity(intent);
    }

    @Override
    protected void setListener() {
        //维修
        findViewById(R.id.ll_repair_ctrl).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), RepairCtrlActivity.class));
        });
        //报装
        findViewById(R.id.ll_install_ctrl).setOnClickListener((v) -> {
            new InstallCtrlView(getActivity(), true).show();
        });
        //报价
        findViewById(R.id.ll_quote_ctrl).setOnClickListener((v) -> {
            new PayOrderListCtrlView(getActivity(), true).show();
        });
        //汇报
        findViewById(R.id.ll_report_ctrl).setOnClickListener((v) -> {
            new ReportCtrlView(getActivity(), true).show();
        });
        //检查
        findViewById(R.id.ll_check_ctrl).setOnClickListener((v) -> {
            new WorkCheckCtrlView(getActivity(), true).show();
        });
        //任务
        findViewById(R.id.ll_task_ctrl).setOnClickListener((v) -> {
            new TaskCtrlView(getActivity(), true).show();
        });
        //发包
        findViewById(R.id.ll_project_ctrl).setOnClickListener((v) -> {
            new TaskPubCtrlView(getActivity(), true).show();
        });
        findViewById(R.id.ll_task_rev_ctrl).setOnClickListener((v) -> {
            new TakePubCtrlView(getActivity(), true).show();
        });

        //维保
        findViewById(R.id.ll_maintain_ctrl).setOnClickListener((v) -> {
            new MaintainCtrlView(getActivity(), true).show();
        });

        //遗留故障
        findViewById(R.id.ll_leave_bug).setOnClickListener((v) -> {
            new LeaveBugView(getActivity(), true).show();
        });
        //统计
        findViewById(R.id.ll_statistics_ctrl).setOnClickListener((v) -> {
            boolean isHave = EanfangApplication.getApplication().getUser().getPerms().contains("top:statistics:count");
            if (isHave == true) {
                String token = EanfangApplication.getApplication().getUser().getToken();
                startActivity(new Intent(getActivity(), WebActivity.class)
                        .putExtra("url", "http:/worker.eanfang.net:8099/#/totalPhone?token=" + token)
                        .putExtra("title", "数据统计"));
            } else {
                showToast("您没有权限");
            }


        });
        //合作公司
        findViewById(R.id.ll_cooperation_ctrl).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), PartnerActivity.class));
        });


    }

}
