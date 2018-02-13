package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.eanfang.application.EanfangApplication;
import com.eanfang.ui.base.BaseFragment;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.CameraActivity;
import net.eanfang.client.ui.activity.worksapce.CheckActivity;
import net.eanfang.client.ui.activity.worksapce.DesignActivity;
import net.eanfang.client.ui.activity.worksapce.InstallActivity;
import net.eanfang.client.ui.activity.worksapce.OfferAndPayOrderActivity;
import net.eanfang.client.ui.activity.worksapce.PartnerActivity;
import net.eanfang.client.ui.activity.worksapce.PersonOfferAndPayOrderActivity;
import net.eanfang.client.ui.activity.worksapce.RepairActivity;
import net.eanfang.client.ui.activity.worksapce.RepairCtrlActivity;
import net.eanfang.client.ui.activity.worksapce.ReportActivity;
import net.eanfang.client.ui.activity.worksapce.SignActivity;
import net.eanfang.client.ui.activity.worksapce.TaskActivity;
import net.eanfang.client.ui.activity.worksapce.WebActivity;
import net.eanfang.client.ui.widget.CompanyListView;
import net.eanfang.client.ui.widget.DesignCtrlView;
import net.eanfang.client.ui.widget.InstallCtrlView;
import net.eanfang.client.ui.widget.ReportCtrlView;
import net.eanfang.client.ui.widget.TaskCtrlView;
import net.eanfang.client.ui.widget.WorkCheckCtrlView;

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
        //报修
        findViewById(R.id.ll_repair).setOnClickListener((v) -> {
            RepairActivity.jumpToActivity(getActivity());
        });

        //报装
        findViewById(R.id.ll_install).setOnClickListener((v) -> {
            InstallActivity.jumpActivity(getActivity());
        });
        //设计
        findViewById(R.id.ll_design).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), DesignActivity.class));
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
            if (EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyEntity().getIsVerify() == 1) {
                startActivity(new Intent(getActivity(), OfferAndPayOrderActivity.class));
            } else {
                startActivity(new Intent(getActivity(), PersonOfferAndPayOrderActivity.class));
            }

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
        //设计
        findViewById(R.id.ll_design_ctrl).setOnClickListener((v) -> {
            new DesignCtrlView(getActivity(), true).show();
        });
        //统计
        findViewById(R.id.ll_statistics_ctrl).setOnClickListener((v) -> {
            String token = EanfangApplication.getApplication().getUser().getToken();
            startActivity(new Intent(getActivity(), WebActivity.class)
                    .putExtra("url", "http://yaf.eanfang.net:8099/#/totalPhone?token="+token)
                    .putExtra("title", "数据统计"));
        });
        //合作公司
        findViewById(R.id.ll_partner_ctrl).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), PartnerActivity.class));
        });
    }
    private void jumpSign(String title, int status) {
        Intent intent = new Intent(getActivity(), SignActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("status", status);
        startActivity(intent);
    }
}
