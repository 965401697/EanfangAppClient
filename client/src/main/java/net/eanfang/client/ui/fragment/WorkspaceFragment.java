package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.eanfang.BuildConfig;
import com.eanfang.application.CustomeApplication;
import com.eanfang.application.EanfangApplication;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.CheckSignPermission;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.sys.entity.OrgEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.CameraActivity;
import net.eanfang.client.ui.activity.worksapce.CustomerServiceActivity;
import net.eanfang.client.ui.activity.worksapce.OfferAndPayOrderActivity;
import net.eanfang.client.ui.activity.worksapce.PersonOfferAndPayOrderActivity;
import net.eanfang.client.ui.activity.worksapce.WebActivity;
import net.eanfang.client.ui.widget.CompanyListView;
import net.eanfang.client.ui.widget.ReportCtrlView;
import net.eanfang.client.ui.widget.SignCtrlView;
import net.eanfang.client.ui.widget.TaskCtrlView;
import net.eanfang.client.ui.widget.WorkCheckCtrlView;

import java.util.ArrayList;
import java.util.List;

import static com.eanfang.util.V.v;

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
        String companyName = EanfangApplication.getApplication().getUser()
                .getAccount().getDefaultUser().getCompanyEntity().getOrgName();
        if ("个人".equals(companyName)) {
            tvCompanyName.setText(companyName + "(点击切换公司)");
        } else {
            tvCompanyName.setText(companyName);
        }
        iv_company_logo = findViewById(R.id.iv_company_logo);
        setLogpic();
        //切换公司
        findViewById(R.id.ll_switch_company).setOnClickListener(v -> {
            new CompanyListView(getActivity(), (name, url) -> {
                if ("个人".equals(name)) {
                    tvCompanyName.setText(name + "(点击切换公司)");
                }else{
                    tvCompanyName.setText(name);
                }
                if (url != null) {
                    iv_company_logo.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + url));
                }
            }).show();
        });

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
            if (EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyEntity().getVerifyStatus() == 2) {
                startActivity(new Intent(getActivity(), OfferAndPayOrderActivity.class));
            } else {
                startActivity(new Intent(getActivity(), PersonOfferAndPayOrderActivity.class));
            }
        });
        //维保管控
        findViewById(R.id.tv_work_maintain).setOnClickListener((v) -> {
//            new MaintainCtrlView(getActivity(), true).show();
            showToast("暂缓开通");
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
    }

    /**
     * 协同办公
     */
    private void teamWork() {
        //签到
        findViewById(R.id.tv_work_sign).setOnClickListener((v) -> {
            // 检查有无权限
            List<String> ss = new ArrayList<>();
            if (CheckSignPermission.isCheckSign(CustomeApplication.get().getUser().getPerms())) {
                new SignCtrlView(getActivity()).show();
            } else {
                showToast("暂无权限");
            }
        });

        //工作汇报
        findViewById(R.id.tv_work_report).setOnClickListener((v) -> {
            new ReportCtrlView(getActivity(), true).show();
        });

        //布置任务
        findViewById(R.id.tv_work_task).setOnClickListener((v) -> {
            new TaskCtrlView(getActivity(), true).show();
        });

        //设备点检
        findViewById(R.id.tv_work_inspect).setOnClickListener((v) -> {
            new WorkCheckCtrlView(getActivity(), true).show();
        });


    }


}
