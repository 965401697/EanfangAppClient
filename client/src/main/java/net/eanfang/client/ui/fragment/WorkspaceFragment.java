package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.eanfang.BuildConfig;
import com.eanfang.application.EanfangApplication;
import com.eanfang.ui.base.BaseFragment;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.sys.entity.OrgEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.CameraActivity;
import net.eanfang.client.ui.activity.worksapce.OfferAndPayOrderActivity;
import net.eanfang.client.ui.activity.worksapce.PersonOfferAndPayOrderActivity;
import net.eanfang.client.ui.activity.worksapce.RepairCtrlActivity;
import net.eanfang.client.ui.widget.CompanyListView;
import net.eanfang.client.ui.widget.DesignCtrlView;
import net.eanfang.client.ui.widget.InstallCtrlView;
import net.eanfang.client.ui.widget.ReportCtrlView;
import net.eanfang.client.ui.widget.SignCtrlView;
import net.eanfang.client.ui.widget.TaskCtrlView;
import net.eanfang.client.ui.widget.WorkCheckCtrlView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2017/11/10  15:07
 * @email houzhongzhou@yeah.net
 * @desc 工作台
 */
public class WorkspaceFragment extends BaseFragment {
    private TextView tvCompanyName;
    private SimpleDraweeView iv_company_logo;

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
        iv_company_logo = findViewById(R.id.iv_company_logo);
        setLogpic();
        //切换公司
        findViewById(R.id.ll_switch_company).setOnClickListener(v -> {
            new CompanyListView(getActivity(), name -> {
                tvCompanyName.setText(name);
                setLogpic();
            }).show();
        });

    }

    private void setLogpic() {
        List<OrgEntity> orgUnitEntityList = new ArrayList<>(EanfangApplication.getApplication().getUser().getAccount().getBelongCompanys());
        Long defaultOrgid = EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyEntity().getOrgId();
        List<String> defaultPic = Stream.of(orgUnitEntityList).filter(bean -> bean.getOrgUnitEntity() != null && bean.getOrgUnitEntity().getOrgId().equals(defaultOrgid)).map(be -> be.getOrgUnitEntity().getLogoPic()).toList();
        if (defaultPic!=null&&!defaultPic.isEmpty()){
            iv_company_logo.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + defaultPic.get(0)));
        }

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
            if (EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyEntity().getVerifyStatus() == 2) {
                startActivity(new Intent(getActivity(), OfferAndPayOrderActivity.class));
            } else {
                startActivity(new Intent(getActivity(), PersonOfferAndPayOrderActivity.class));
            }
        });
        //维保管控
        findViewById(R.id.ll_order_manage).setOnClickListener((v) -> {
//            new MaintainCtrlView(getActivity(), true).show();
            showToast(".....");
        });

        //免费设计
        findViewById(R.id.ll_design_add).setOnClickListener((v) -> {
            new DesignCtrlView(getActivity(), true).show();
        });
    }

    private void helpTools() {
        //相机
        findViewById(R.id.ll_camera).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), CameraActivity.class));
        });
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


}
