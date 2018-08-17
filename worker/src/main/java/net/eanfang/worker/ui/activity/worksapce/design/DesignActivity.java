package net.eanfang.worker.ui.activity.worksapce.design;

import android.os.Bundle;
import android.view.View;

import com.eanfang.util.JumpItent;
import com.eanfang.util.PermKit;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

public class DesignActivity extends BaseWorkerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design2);
        setTitle("设计订单");
        setLeftBack();

        findViewById(R.id.iv_design).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!PermKit.get().getDesignListPrem())return;
                JumpItent.jump(DesignActivity.this, DesignOrderActivity.class);
            }
        });
    }
}
