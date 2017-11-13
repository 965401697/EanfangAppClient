package net.eanfang.client.ui.fragment;

import android.os.Bundle;
import android.view.View;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseFragment;

/**
 * Created by MrHou
 *
 * @on 2017/11/10  15:08
 * @email houzhongzhou@yeah.net
 * @desc 我的
 */

public class MyFragment extends BaseFragment {
    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_config;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView() {
        setTitle("我的");
        setLeftVisible(View.GONE);
    }

    @Override
    protected void setListener() {

    }
}
