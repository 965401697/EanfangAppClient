package net.eanfang.worker.ui.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    //系统类别
    @BindView(R.id.rl_systom_type)
    RelativeLayout rlSystomType;
    //业务类型
    @BindView(R.id.rl_business_type)
    RelativeLayout rlBusinessType;
    // 服务区域
    @BindView(R.id.rl_service_area)
    RelativeLayout rlServiceArea;
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
    }

    private void initView() {
        setTitle("技师认证");
        setLeftBack();

        workerInfoBean = (WorkerInfoBean) getIntent().getSerializableExtra("workerInfoBean");
    }

    @OnClick({R.id.rl_base_info, R.id.rl_systom_type, R.id.rl_business_type, R.id.rl_service_area, R.id.rl_other_info, R.id.tv_confim})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
}
