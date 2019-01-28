package net.eanfang.worker.ui.activity.worksapce.online;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExpertAnswerActivity extends BaseWorkerActivity {

    @BindView(R.id.iv_expert_header)
    SimpleDraweeView ivExpertHeader;
    @BindView(R.id.tv_expert_name)
    TextView tvExpertName;
    @BindView(R.id.tv_major)
    TextView tvMajor;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.et_content)
    EditText etContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_answer);
        ButterKnife.bind(this);
        setTitle("专家回复");
        setLeftBack();
    }

    @OnClick(R.id.tv_send)
    public void onViewClicked() {
    }
}
