package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.CameraActivity;
import net.eanfang.client.ui.base.BaseFragment;

/**
 * Created by MrHou
 *
 * @on 2017/11/10  15:07
 * @email houzhongzhou@yeah.net
 * @desc 工作台
 */

public class WorkspaceFragment extends BaseFragment {
    private ImageView iv_take_photo;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_workspace;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView() {
        iv_take_photo = findViewById(R.id.iv_camera);
        iv_take_photo.setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), CameraActivity.class));
        });
    }

    @Override
    protected void setListener() {

    }
}
