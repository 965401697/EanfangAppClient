package net.eanfang.client.ui.activity.worksapce.worktalk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eanfang.ui.base.BaseActivity;

import net.eanfang.client.R;

/**
 * @author Guanluocang
 * @date on 2018/7/11  16:38
 * @decision 面谈员工 详情
 */
public class WorkTalkDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_talk_detail);
        initView();
    }

    private void initView() {
        setLeftBack();
        setTitle("面谈员工");
    }
}
