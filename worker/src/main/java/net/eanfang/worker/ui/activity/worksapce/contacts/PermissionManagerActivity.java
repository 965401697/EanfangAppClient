package net.eanfang.worker.ui.activity.worksapce.contacts;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PermissionManagerActivity extends BaseWorkerActivity {

    @BindView(R.id.iv_user_header)
    SimpleDraweeView ivUserHeader;
    @BindView(R.id.tv_name_phone)
    TextView tvNamePhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_section)
    LinearLayout llSection;
    @BindView(R.id.tv_role)
    TextView tvRole;
    @BindView(R.id.tv_sure)
    TextView tvSure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff_next);
        ButterKnife.bind(this);
        setTitle("权限管理");
        setLeftBack();

        llSection.setVisibility(View.GONE);
        tvSure.setText("确定授权");


        ivUserHeader.setImageURI(BuildConfig.OSS_SERVER + EanfangApplication.get().getUser().getAccount().getAvatar());
        tvNamePhone.setText(EanfangApplication.get().getUser().getAccount().getNickName() + "(" + EanfangApplication.get().getUser().getAccount().getMobile() + ")");
        tvAddress.setText(Config.get().getAddressByCode(EanfangApplication.get().getUser().getAccount().getAreaCode()) + EanfangApplication.get().getUser().getAccount().getAddress());

        tvRole.setText("CEO");
    }

    @OnClick(R.id.ll_role)
    public void onViewClicked() {

    }
}
