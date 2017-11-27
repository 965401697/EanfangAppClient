package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.WebActivity;
import net.eanfang.client.ui.base.BaseFragment;

/**
 * Created by MrHou
 *
 * @on 2017/11/10  15:07
 * @email houzhongzhou@yeah.net
 * @desc 通讯录
 */

public class ContactsFragment extends BaseFragment {
    private TextView tv_safety;
    private ImageView iv_add;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_contact;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView() {
        tv_safety = findViewById(R.id.tv_safety);
        iv_add = findViewById(R.id.iv_add);

    }

    @Override
    protected void setListener() {
        tv_safety.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), WebActivity.class)
                    .putExtra("url", "http://www.baidu.com")
                    .putExtra("title", "安防圈"));
        });

        iv_add.setOnClickListener(v -> {
            showToast("测试一下");
        });
    }
}
