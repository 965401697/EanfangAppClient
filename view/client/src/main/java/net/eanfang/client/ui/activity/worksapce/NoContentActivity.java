package net.eanfang.client.ui.activity.worksapce;

import android.os.Bundle;

import com.eanfang.ui.base.BaseActivity;

import net.eanfang.client.R;

/**
 * @author guanluocang
 * @data 2018/8/23
 * @description 暂无内容
 */
public class NoContentActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_no_content);
        super.onCreate(savedInstanceState);
        setTitle("暂无数据");
        setLeftBack();
    }
}
