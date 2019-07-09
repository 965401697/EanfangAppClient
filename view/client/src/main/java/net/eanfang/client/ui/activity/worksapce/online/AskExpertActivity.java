package net.eanfang.client.ui.activity.worksapce.online;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
/**
 * @author
 * @data 2018/11/9
 * @description 专家详情
 */
public class AskExpertActivity extends BaseClientActivity {

    @BindView(R.id.iv_expert_header)
    CircleImageView ivExpertHeader;
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
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.titles_bar)
    RelativeLayout titlesBar;
    @BindView(R.id.ll_good)
    TextView llGood;
    @BindView(R.id.ll_answer)
    TextView llAnswer;
    @BindView(R.id.ll_money)
    LinearLayout llMoney;
    @BindView(R.id.tv_ask_now)
    TextView tvAskNow;
    @BindView(R.id.num_zyr)
    TextView numZyr;
    @BindView(R.id.tv_no_datas)
    TextView tvNoDatas;
    private String mBrand, mUserId;
    private UserAppraiseAdapter mUserAppraiseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ask_expert);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        mBrand = getIntent().getStringExtra("com2");
        mUserId = getIntent().getStringExtra("com");
        if (mBrand != null) {
            setTitle(mBrand);
        } else {
            setTitle("专家在线");
        }
        setLeftBack();

        initViews();
        getData();
    }

    private void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        mUserAppraiseAdapter = new UserAppraiseAdapter();
        mUserAppraiseAdapter.bindToRecyclerView(recyclerView);
    }


    //网络请求--展示问题信息
    private void getData() {
        EanfangHttp.post(NewApiService.ANSWER_Expert_More_Details)
                .params("userId", mUserId)
                .execute(new EanfangCallback<AnswerExpertMoreDetailsBean>(this, true, AnswerExpertMoreDetailsBean.class) {
                    @Override
                    public void onSuccess(AnswerExpertMoreDetailsBean bean) {
                        GlideUtil.intoImageView(AskExpertActivity.this,Uri.parse(BuildConfig.OSS_SERVER + bean.getExpert().getAvatarPhoto()),ivExpertHeader);
                        if (!TextUtils.isEmpty(bean.getExpert().getCertificateName())){
                            tvLevel.setText(bean.getExpert().getCertificateName());
                        }else {
                            tvLevel.setText("无");
                        }
                        if (!TextUtils.isEmpty(bean.getExpert().getExpertName())){
                            tvExpertName.setText(bean.getExpert().getExpertName());
                        }else {
                            tvExpertName.setText("无");
                        }
                        llGood.setText("好评率:" + bean.getExpert().getFavorableRate() * 100 + "%");
                        llAnswer.setText("回答:" + bean.getAnswerNums());
                        if (!TextUtils.isEmpty(bean.getExpert().getSystemType())){
                            tvMajor.setText("擅长专业:  " + bean.getExpert().getSystemType());
                        }else {
                            tvExpertName.setText("无");
                        }
                        if (!TextUtils.isEmpty(bean.getExpert().getResponsibleBrand())){
                            tvBrand.setText("擅长品牌:  " + bean.getExpert().getResponsibleBrand());
                        }else {
                            tvExpertName.setText("无");
                        }
                        if (!TextUtils.isEmpty(bean.getExpert().getIntro())){
                            tvIntroduce.setText("专家介绍:  " + bean.getExpert().getIntro());
                        }else {
                            tvExpertName.setText("无");
                        }
                        if (!TextUtils.isEmpty(bean.getExpert().getCompany())){
                            tvCompany.setText("就职单位:  " + bean.getExpert().getCompany());
                        }else {
                            tvExpertName.setText("无");
                        }

                        if (!TextUtils.isEmpty(bean.getExpert().getSystemType())){
                            tvExpertType.setText(bean.getExpert().getSystemType());
                        }else {
                            tvExpertName.setText("无");
                        }

                        numZyr.setText("用户评价(" + bean.getEvaluateUsers() + ")");
                        if (bean.getEvaluateList().size() > 0) {
                            recyclerView.setVisibility(View.VISIBLE);
                            tvNoDatas.setVisibility(View.GONE);
                            mUserAppraiseAdapter.setNewData(bean.getEvaluateList());
                        }else {
                            recyclerView.setVisibility(View.GONE);
                            tvNoDatas.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onNoData(String message) {
                    }

                    @Override
                    public void onCommitAgain() {
                    }
                });


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
                Intent intent = getIntent();
                String accId = intent.getStringExtra("com3");
                String expertName = intent.getStringExtra("com4");
                RongIM.getInstance().startConversation(AskExpertActivity.this, Conversation.ConversationType.PRIVATE, accId, expertName);
                break;
        }
    }
}
