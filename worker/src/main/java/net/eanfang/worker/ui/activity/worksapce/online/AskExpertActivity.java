package net.eanfang.worker.ui.activity.worksapce.online;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.sys.entity.BaseDataEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AskExpertActivity extends BaseWorkerActivity {

    @BindView(R.id.iv_expert_header)
    SimpleDraweeView ivExpertHeader;
    @BindView(R.id.tv_expert_name)
    TextView tvExpertName;
    @BindView(R.id.tv_line_status)
    TextView tvLineStatus;
    @BindView(R.id.tv_expert_type)
    TextView tvExpertType;
    @BindView(R.id.tv_level)
    TextView tvLevel;
    @BindView(R.id.tv_major)
    TextView tvMajor;
    @BindView(R.id.tv_brand)
    TextView tvBrand;
    @BindView(R.id.tv_introduce)
    TextView tvIntroduce;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private BaseDataEntity mBrand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_expert);
        ButterKnife.bind(this);
        mBrand = (BaseDataEntity) getIntent().getSerializableExtra("brand");
        setTitle(mBrand.getDataName());
        setLeftBack();

        initViews();
    }

    private void initViews() {
        ivExpertHeader.setImageURI("http://");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        UserAppraiseAdapter mUserAppraiseAdapter = new UserAppraiseAdapter();
        mUserAppraiseAdapter.bindToRecyclerView(recyclerView);
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        mUserAppraiseAdapter.setNewData(list);
    }

    @OnClick({R.id.ll_good, R.id.ll_answer, R.id.ll_money, R.id.tv_ask_now})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_good:
                break;
            case R.id.ll_answer:
                break;
            case R.id.ll_money:
                break;
            case R.id.tv_ask_now:
                break;
        }
    }
}
