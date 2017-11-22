package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.ui.activity.CameraActivity;
import net.eanfang.client.ui.activity.worksapce.CheckActivity;
import net.eanfang.client.ui.activity.worksapce.DesignActivity;
import net.eanfang.client.ui.activity.worksapce.InstallActivity;
import net.eanfang.client.ui.activity.worksapce.RepairActivity;
import net.eanfang.client.ui.activity.worksapce.RepairCtrlActivity;
import net.eanfang.client.ui.activity.worksapce.ReportActivity;
import net.eanfang.client.ui.activity.worksapce.TaskActivity;
import net.eanfang.client.ui.base.BaseFragment;
import net.eanfang.client.ui.widget.CompanyQuoteView;
import net.eanfang.client.ui.widget.DesignCtrlView;
import net.eanfang.client.ui.widget.InstallCtrlView;
import net.eanfang.client.ui.widget.PersonQuoteView;
import net.eanfang.client.ui.widget.ReportCtrlView;
import net.eanfang.client.ui.widget.TaskCtrlView;

/**
 * Created by MrHou
 *
 * @on 2017/11/10  15:07
 * @email houzhongzhou@yeah.net
 * @desc 工作台
 */
// TODO: 2017/11/22 报修管控，检查管控,数据统计
public class WorkspaceFragment extends BaseFragment {


    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_workspace;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView() {
        findViewById(R.id.iv_camera).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), CameraActivity.class));
        });
        //快捷工作
        findViewById(R.id.ll_repair).setOnClickListener((v) -> {
            RepairActivity.jumpToActivity(getActivity());
        });
        findViewById(R.id.ll_install).setOnClickListener((v) -> {
            InstallActivity.jumpActivity(getActivity());
        });
        findViewById(R.id.ll_design).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), DesignActivity.class));
        });
        findViewById(R.id.ll_report).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), ReportActivity.class));
        });
        findViewById(R.id.ll_task).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), TaskActivity.class));
        });
        findViewById(R.id.ll_check).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), CheckActivity.class));
        });

    }

    @Override
    protected void setListener() {
        //工作管控
        findViewById(R.id.ll_repair_ctrl).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), RepairCtrlActivity.class));
        });
        findViewById(R.id.ll_install_ctrl).setOnClickListener((v) -> {
            new InstallCtrlView(getActivity(), true).show();
        });
        findViewById(R.id.ll_quote_ctrl).setOnClickListener((v) -> {
            if ("1".equals(EanfangApplication.get().getUser().getCompanyverify())) {
                new CompanyQuoteView(getActivity(), true).show();
            } else {
                new PersonQuoteView(getActivity(), true).show();
            }
        });
        findViewById(R.id.ll_report_ctrl).setOnClickListener((v) -> {
            new ReportCtrlView(getActivity(), true).show();
        });
        findViewById(R.id.ll_check_ctrl).setOnClickListener((v) -> {

        });
        findViewById(R.id.ll_task_ctrl).setOnClickListener((v) -> {
            new TaskCtrlView(getActivity(), true).show();
        });
        findViewById(R.id.ll_design_ctrl).setOnClickListener((v) -> {
            new DesignCtrlView(getActivity(), true).show();
        });
        findViewById(R.id.ll_statistics_ctrl).setOnClickListener((v) -> {
        });
    }
}
