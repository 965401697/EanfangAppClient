package net.eanfang.client.ui.activity.im;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eanfang.util.PermKit;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.defendlog.DefendLogWriteActivity;
import net.eanfang.client.ui.activity.worksapce.oa.check.AddNewCheckActivity;
import net.eanfang.client.ui.activity.worksapce.oa.task.TaskAssignmentCreationActivity;
import net.eanfang.client.ui.activity.worksapce.oa.workreport.CreationWorkReportActivity;
import net.eanfang.client.ui.activity.worksapce.openshop.OpenShopLogWriteActivity;
import net.eanfang.client.ui.activity.worksapce.worktalk.WorkTalkCreateActivity;
import net.eanfang.client.ui.activity.worksapce.worktransfer.WorkTransferCreateActivity;
import net.eanfang.client.ui.base.BaseClientActivity;

public class OAListActivity extends BaseClientActivity {

    private String conversationType;
    private String targetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oalist);
        setTitle("工作协同");
        setLeftBack();

        targetId = getIntent().getStringExtra("targetId");
        conversationType = getIntent().getStringExtra("conversationType");

        jump();
    }

    /**
     * 跳oa办公
     */

    private void jump() {

        if (!PermKit.get().getWorkReportListPrem()) {
            return;
        }
        onClick((findViewById(R.id.tv_work_report)), CreationWorkReportActivity.class);
        if (!PermKit.get().getWorkTaskListPrem()) {
            return;
        }
        onClick((findViewById(R.id.tv_work_task)), TaskAssignmentCreationActivity.class);
        if (!PermKit.get().getWorkInspectListPrem()) {
            return;
        }
        onClick((findViewById(R.id.tv_work_inspect)), AddNewCheckActivity.class);
        onClick((findViewById(R.id.tv_work_transfer)), WorkTransferCreateActivity.class);
        onClick((findViewById(R.id.tv_work_talk)), WorkTalkCreateActivity.class);
        onClick((findViewById(R.id.tv_shop_log)), OpenShopLogWriteActivity.class);
        onClick((findViewById(R.id.tv_defend_log)), DefendLogWriteActivity.class);

    }

    private void onClick(View v, Class clazz) {

        Intent intent = new Intent(this, clazz);
        intent.putExtra("targetId", targetId);
        intent.putExtra("conversationType", conversationType);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                finishSelf();
            }
        });
    }
}
