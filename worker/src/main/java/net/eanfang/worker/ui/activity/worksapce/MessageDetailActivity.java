package net.eanfang.worker.ui.activity.worksapce;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.ui.base.BaseActivity;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Guanluocang
 * @date on 2018/4/20  11:51
 * @decision 业务通知详情
 */
public class MessageDetailActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meaasge_detail);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * @date on 2018/4/20  11:47
     * @decision 初始化视图
     */
    private void initView() {
        setTitle("业务通知详情");
        setLeftBack();
    }

}
