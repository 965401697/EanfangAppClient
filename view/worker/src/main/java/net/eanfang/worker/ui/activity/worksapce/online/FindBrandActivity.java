package net.eanfang.worker.ui.activity.worksapce.online;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.repair.FaultLibraryActivity;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindBrandActivity extends BaseWorkerActivity {


    @BindView(R.id.tv_go)
    TextView tvGo;
    @BindView(R.id.iv_left)
    ImageView ivLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_find_brand);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("选择设备类型");




    }

    @OnClick({R.id.iv_left, R.id.tv_go})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.tv_go:
                Intent intent = new Intent(FindBrandActivity.this, FaultLibraryActivity.class);
                startActivity(intent);
                break;
        }
    }
}
