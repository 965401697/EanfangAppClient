package net.eanfang.worker.ui.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.AuthStatusBean;
import com.eanfang.model.WorkerInfoBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Guanluocang
 * @date on 2018/6/19  17:21
 * @decision 技师认证 列表
 */
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


    private WorkerInfoBean workerInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_list);
        ButterKnife.bind(this);
        initView();
        initData();
    }


    private void initView() {
        setTitle("技师认证");
        setLeftBack();
        workerInfoBean = (WorkerInfoBean) getIntent().getSerializableExtra("workerInfoBean");
    }

    private void initData() {
        // 获取认证状态
        EanfangHttp.post(UserApi.POST_WORKER_AUTH_STATUS)
                .params("accId", EanfangApplication.getApplication().getAccId())
                .execute(new EanfangCallback<AuthStatusBean>(this, true, AuthStatusBean.class, (bean) -> {
                    doChange(bean.getBase(), bean.getService(), bean.getBiz(), bean.getArea());
                }));
    }

    @OnClick({R.id.rl_base_info, R.id.rl_systom_type, R.id.rl_business_type, R.id.rl_service_area, R.id.rl_other_info, R.id.tv_confim})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // 基本资料
            case R.id.rl_base_info:
                Bundle bundle = new Bundle();
                bundle.putSerializable("workerInfoBean", workerInfoBean);
                JumpItent.jump(this, AuthWorkerInfoActivity.class, bundle);
                break;
            case R.id.rl_systom_type:
                startActivity(new Intent(this, AuthWorkerSysTypeActivity.class).putExtra("status", workerInfoBean.getStatus()));
                break;
            case R.id.rl_business_type:
                startActivity(new Intent(this, AuthWorkerBizActivity.class).putExtra("status", workerInfoBean.getStatus()));
                break;
            case R.id.rl_service_area:
                startActivity(new Intent(this, AuthWorkerAreaActivity.class).putExtra("status", workerInfoBean.getStatus()));
                break;
            case R.id.rl_other_info:
                break;
            case R.id.tv_confim:
                break;
        }
    }

    private void doChange(int baseStatus, int serviceStatus, int bizStatus, int areaStatus) {
        if (baseStatus > 0) {
            tvBaseInfo.setText("已完善");
            tvBaseInfo.setTextColor(ContextCompat.getColor(this, R.color.color_bottom));
        } else {
            tvBaseInfo.setText("待完善");
            tvBaseInfo.setTextColor(ContextCompat.getColor(this, R.color.color_auth_list_unfinish));
        }
        if (serviceStatus > 0) {
            tvSysType.setText("已完善");
            tvSysType.setTextColor(ContextCompat.getColor(this, R.color.color_bottom));
        } else {
            tvSysType.setText("待完善");
            tvSysType.setTextColor(ContextCompat.getColor(this, R.color.color_auth_list_unfinish));
        }
        if (bizStatus > 0) {
            tvBusinessType.setText("已完善");
            tvBusinessType.setTextColor(ContextCompat.getColor(this, R.color.color_bottom));
        } else {
            tvBusinessType.setText("待完善");
            tvBusinessType.setTextColor(ContextCompat.getColor(this, R.color.color_auth_list_unfinish));
        }
        if (areaStatus > 0) {
            tvServiceArea.setText("已完善");
            tvServiceArea.setTextColor(ContextCompat.getColor(this, R.color.color_bottom));
        } else {
            tvServiceArea.setText("待完善");
            tvServiceArea.setTextColor(ContextCompat.getColor(this, R.color.color_auth_list_unfinish));
        }

    }

}
