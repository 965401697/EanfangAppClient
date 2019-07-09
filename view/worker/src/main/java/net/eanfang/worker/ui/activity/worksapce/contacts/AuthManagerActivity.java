package net.eanfang.worker.ui.activity.worksapce.contacts;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.contacts.verifyqualify.AuthQualifySecondActivity;
import net.eanfang.worker.ui.activity.worksapce.contacts.verifyqualify.AuthQualifyFirstActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author guanluocang
 * @data 2018/9/10
 * @description 安防公司资质认证列表页面
 */

public class AuthManagerActivity extends BaseActivity {

    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rl_systom_type)
    RelativeLayout rlSystomType;
    @BindView(R.id.rl_business_type)
    RelativeLayout rlBusinessType;
    @BindView(R.id.rl_service_area)
    RelativeLayout rlServiceArea;
    @BindView(R.id.tv_confim)
    TextView tvConfim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_auth_managere);
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

    }


    @OnClick({R.id.titles_bar, R.id.rl_systom_type, R.id.rl_business_type, R.id.rl_service_area, R.id.tv_confim})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.titles_bar:
                break;
            // 系统类别
            case R.id.rl_systom_type:
                JumpItent.jump(AuthManagerActivity.this, AuthQualifyFirstActivity.class);
                break;
            // 业务类别
            case R.id.rl_business_type:
                JumpItent.jump(AuthManagerActivity.this, AuthQualifySecondActivity.class);
                break;
            // 服务区域
            case R.id.rl_service_area:
                JumpItent.jump(AuthManagerActivity.this, AuthAreaActivity.class);
                break;
            // 确定
            case R.id.tv_confim:
                break;
        }
    }

}
