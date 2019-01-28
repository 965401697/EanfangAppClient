package net.eanfang.worker.ui.activity.worksapce.online;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.eanfang.delegate.BGASortableDelegate;
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
    private static final int REQUEST_CODE_CHOOSE_EXPLAIN = 1;
    private static final int REQUEST_CODE_PHOTO_EXPLAIN = 2;


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


        snplPhotos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_EXPLAIN, REQUEST_CODE_PHOTO_EXPLAIN));


        recyclerViewAnswer.setLayoutManager(new LinearLayoutManager(this));
        FaultExplainAdapter mFaultExplainAdapter = new FaultExplainAdapter();
        mFaultExplainAdapter.bindToRecyclerView(recyclerViewAnswer);
        mFaultExplainAdapter.setNewData(list);
    }


    private void fillData() {
        ArrayList<String> picList = new ArrayList<>();
//
//        String[] pics = bean.getCardPics().split(",");
//
//        for (int i = 0; i < pics.length; i++) {
//            picList.add(BuildConfig.OSS_SERVER + pics[i]);
//        }
        snplPhotos.setData(picList);
    }

    @OnClick(R.id.tv_answer)
    public void onViewClicked() {
        Intent intent = new Intent(FaultExplainActivity.this, ExpertAnswerActivity.class);
        startActivity(intent);
    }
}
