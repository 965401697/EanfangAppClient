package com.eanfang.ui.activity;

import android.os.Bundle;

import com.eanfang.R;
import com.eanfang.ui.base.BaseActivity;


/**
 * @author liangkailun
 * Date ：2019-05-17
 * Describe :提取成功页面
 */
public class ExtractSuccessActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extract_success);
        setLeftBack();
        setTitle("充值");
    }
}
