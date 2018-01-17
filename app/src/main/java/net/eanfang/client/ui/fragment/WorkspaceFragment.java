package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.ui.activity.CameraActivity;
import net.eanfang.client.ui.activity.worksapce.CheckActivity;
import net.eanfang.client.ui.activity.worksapce.DesignActivity;
import net.eanfang.client.ui.activity.worksapce.InstallActivity;
import net.eanfang.client.ui.activity.worksapce.OfferAndPayOrderActivity;
import net.eanfang.client.ui.activity.worksapce.PersonOfferAndPayOrderActivity;
import net.eanfang.client.ui.activity.worksapce.RepairActivity;
import net.eanfang.client.ui.activity.worksapce.RepairCtrlActivity;
import net.eanfang.client.ui.activity.worksapce.ReportActivity;
import net.eanfang.client.ui.activity.worksapce.TaskActivity;
import net.eanfang.client.ui.activity.worksapce.WebActivity;
import net.eanfang.client.ui.base.BaseFragment;
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


    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_workspace;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView() {
        //相机
        findViewById(R.id.iv_camera).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), CameraActivity.class));
        });
        //切换公司
        findViewById(R.id.ll_switch_company).setOnClickListener(v -> {
            new CompanyListView(getActivity()).show();
//            CompanyListView companyListView = new CompanyListView(getActivity());
//            Window window = companyListView.getWindow();
//            //重新设置
//            WindowManager.LayoutParams lp = window.getAttributes();
//            window.setGravity(Gravity.LEFT | Gravity.TOP);
//            window.setAttributes(lp);
//            companyListView.show();
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
            startActivity(new Intent(getActivity(), WebActivity.class)
                    .putExtra("url", "http://www.jianshu.com/u/0e0821e94979")
                    .putExtra("title", "数据统计"));
        });
    }

}
