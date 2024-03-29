package net.eanfang.worker.ui.activity.my;

import android.content.Intent;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.AuthStatusBean;
import com.eanfang.biz.model.bean.Message;
import com.eanfang.biz.model.bean.WorkerInfoBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.worksapce.StateChangeActivity;
import net.eanfang.worker.ui.widget.CommitVerfiyView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Guanluocang
 * @date on 2018/6/19  17:21
 * @decision 技师认证 列表
 */
@Deprecated
public class AuthListActivity extends BaseActivity {

    // 基本资料
    @BindView(R.id.rl_base_info)
    RelativeLayout rlBaseInfo;
    @BindView(R.id.tv_base_info)
    TextView tvBaseInfo;
    //系统类别
    @BindView(R.id.rl_systom_type)
    RelativeLayout rlSystomType;
    @BindView(R.id.tv_sys_type)
    TextView tvSysType;
    //业务类型
    @BindView(R.id.rl_business_type)
    RelativeLayout rlBusinessType;
    @BindView(R.id.tv_business_type)
    TextView tvBusinessType;
    // 服务区域
    @BindView(R.id.rl_service_area)
    RelativeLayout rlServiceArea;
    @BindView(R.id.tv_service_area)
    TextView tvServiceArea;
    // 补充资料
    @BindView(R.id.rl_other_info)
    RelativeLayout rlOtherInfo;
    @BindView(R.id.tv_confim)
    TextView tvConfim;
    @BindView(R.id.rl_is_auth)
    RelativeLayout rlIsAuth;

    private WorkerInfoBean workerInfoBean;

    //提交认证 弹框
    private CommitVerfiyView verfiyView;

    private int verify = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_auth_list);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        setTitle("技师认证");
        setLeftBack();
    }

    private void initData() {
        // 获取认证状态
        EanfangHttp.post(UserApi.POST_WORKER_AUTH_STATUS)
                .params("accId", WorkerApplication.get().getAccId())
                .execute(new EanfangCallback<AuthStatusBean>(this, true, AuthStatusBean.class, (bean) -> {
                    verify = bean.getVerify();
//                    doChange(bean.getBase(), bean.getBiz(), bean.getService(), bean.getArea(), bean.getVerify());
                }));
        // 获取技师信息
        EanfangHttp.get(UserApi.GET_WORKER_INFO)
                .execute(new EanfangCallback<WorkerInfoBean>(AuthListActivity.this, true, WorkerInfoBean.class, (bean) -> {
                    workerInfoBean = bean;
                }));
    }

    @OnClick({R.id.rl_base_info, R.id.rl_systom_type, R.id.rl_business_type, R.id.rl_service_area, R.id.rl_other_info, R.id.tv_confim})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // 基本资料
            case R.id.rl_base_info:
                Bundle bundle = new Bundle();
                bundle.putSerializable("workerInfoBean", workerInfoBean);
                bundle.putInt("status", verify);
                JumpItent.jump(this, AuthWorkerInfoActivity.class, bundle);
                break;
            //系统类别
            case R.id.rl_systom_type:
                startActivity(new Intent(this, AuthWorkerSysTypeActivity.class).putExtra("status", verify));
                break;
            // 业务类型
            case R.id.rl_business_type:
                startActivity(new Intent(this, AuthWorkerBizActivity.class).putExtra("status", verify));
                break;
            //服务区域
            case R.id.rl_service_area:
//                startActivity(new Intent(this, SelectAreaActivity.class).putExtra("status", verify));
                break;
            case R.id.rl_other_info:
                break;
            case R.id.tv_confim:
                doVerify();
                break;
        }
    }

    private void doChange(int baseStatus, int serviceStatus, int bizStatus, int areaStatus, int verify) {
        // 正在认证中 给予提示
        if (verify == 1) {
            rlIsAuth.setVisibility(View.VISIBLE);
        } else {
            rlIsAuth.setVisibility(View.GONE);
        }
        // 基本资料
        if (baseStatus > 0) {
            tvBaseInfo.setText("已完善");
            tvBaseInfo.setTextColor(ContextCompat.getColor(this, R.color.color_bottom));
        } else {
            tvBaseInfo.setText("待完善");
            tvBaseInfo.setTextColor(ContextCompat.getColor(this, R.color.color_auth_list_unfinish));
        }
        //系统类别
        if (serviceStatus > 0) {
            tvSysType.setText("已完善");
            tvSysType.setTextColor(ContextCompat.getColor(this, R.color.color_bottom));
        } else {
            tvSysType.setText("待完善");
            tvSysType.setTextColor(ContextCompat.getColor(this, R.color.color_auth_list_unfinish));
        }
        //业务类型
        if (bizStatus > 0) {
            tvBusinessType.setText("已完善");
            tvBusinessType.setTextColor(ContextCompat.getColor(this, R.color.color_bottom));
        } else {
            tvBusinessType.setText("待完善");
            tvBusinessType.setTextColor(ContextCompat.getColor(this, R.color.color_auth_list_unfinish));
        }
        //服务区域
        if (areaStatus > 0) {
            tvServiceArea.setText("已完善");
            tvServiceArea.setTextColor(ContextCompat.getColor(this, R.color.color_bottom));
        } else {
            tvServiceArea.setText("待完善");
            tvServiceArea.setTextColor(ContextCompat.getColor(this, R.color.color_auth_list_unfinish));
        }
        // 当认证都大于0
        if (baseStatus > 0 && serviceStatus > 0 && bizStatus > 0 && areaStatus > 0 && (verify != 1 && verify != 2)) {
            tvConfim.setVisibility(View.VISIBLE);
        } else {
            tvConfim.setVisibility(View.GONE);
        }
    }

    /**
     * 是否提交认证
     */
    private void doVerify() {
        verfiyView = new CommitVerfiyView(this, view -> commitVerfiy(verfiyView));
        verfiyView.show();
    }

    // 提交认证
    private void commitVerfiy(CommitVerfiyView verfiyView) {
        EanfangHttp.post(UserApi.POST_TECH_WORKER_SEND_VERIFY)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    verfiyView.dismiss();
                    doJumpConfirm();
                }));
    }

    // 认证提交完毕 提示页面
    public void doJumpConfirm() {
        Bundle bundle = new Bundle();
        Message message = new Message();
        message.setMsgContent("认证提交成功");
        message.setTip("确定");
        bundle.putSerializable("message", message);
        JumpItent.jump(AuthListActivity.this, StateChangeActivity.class, bundle);
        finishSelf();
    }

}
