package net.eanfang.client.ui.activity.worksapce.worktransfer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.eanfang.ui.base.BaseActivity;

import net.eanfang.client.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Guanluocang
 * @date on 2018/7/11  16:38
 * @decision 面谈员工 选择中心
 */
public class WorkTransferControlActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_transfer_control);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("面谈员工");
        setLeftBack();
    }

    @OnClick({R.id.iv_work_receiver, R.id.iv_work_create, R.id.iv_repairNew})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_work_receiver:
                break;
            case R.id.iv_work_create:
                break;
            case R.id.iv_repairNew:
                break;
        }
    }
}
