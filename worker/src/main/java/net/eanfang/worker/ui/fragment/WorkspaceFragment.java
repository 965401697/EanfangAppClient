package net.eanfang.worker.ui.fragment;

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

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.CameraActivity;
import net.eanfang.worker.ui.activity.worksapce.CustomerServiceActivity;
import net.eanfang.worker.ui.activity.worksapce.RepairCtrlActivity;
import net.eanfang.worker.ui.activity.worksapce.WebActivity;
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
        tvCompanyName.setText(EanfangApplication.getApplication().getUser()
                .getAccount().getDefaultUser().getCompanyEntity().getOrgName());
        iv_company_logo = findViewById(R.id.iv_company_logo);
        setLogpic();
        //切换公司
        findViewById(R.id.ll_switch_company).setOnClickListener(v -> {
            new CompanyListView(getActivity(), (name, url) -> {
                tvCompanyName.setText(name);
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

//        //遗留故障
//        findViewById(R.id.ll_leave_bug).setOnClickListener((v) -> {
//            new LeaveBugView(getActivity(), true).show();
//        });

    }

    /**
     * 过程管控
     */
    private void progressCtrl() {
        findViewById(R.id.tv_work_service).setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), CustomerServiceActivity.class));
        });

    }

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
        findViewById(R.id.tv_sign).setOnClickListener((v) -> {

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
