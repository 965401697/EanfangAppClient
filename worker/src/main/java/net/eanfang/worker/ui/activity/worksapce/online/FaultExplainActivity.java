package net.eanfang.worker.ui.activity.worksapce.online;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 故障解答
 */
public class FaultExplainActivity extends BaseWorkerActivity {

    @BindView(R.id.iv_user_header)
    SimpleDraweeView ivUserHeader;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_fault_desc)
    TextView tvFaultDesc;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_major)
    TextView tvMajor;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.snpl_photos)
    BGASortableNinePhotoLayout snplPhotos;
    @BindView(R.id.recycler_view_answer)
    RecyclerView recyclerViewAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fault_explain);
        ButterKnife.bind(this);
        setTitle("故障解答");
        setLeftBack();

        initViews();

    }

    private void initViews() {

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("1");

        recyclerViewAnswer.setLayoutManager(new LinearLayoutManager(this));
        FaultExplainAdapter mFaultExplainAdapter=new FaultExplainAdapter();
        mFaultExplainAdapter.bindToRecyclerView(recyclerViewAnswer);
        mFaultExplainAdapter.setNewData(list);
    }

    @OnClick(R.id.tv_answer)
    public void onViewClicked() {
        Intent intent = new Intent(FaultExplainActivity.this, ExpertAnswerActivity.class);
        startActivity(intent);
    }
}
