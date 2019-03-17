package net.eanfang.worker.ui.activity.worksapce.online;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
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
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.zan_count)
    TextView zanCount;
    @BindView(R.id.tu_like)
    ImageView tuLike;
    @BindView(R.id.tu_no_like)
    ImageView tuNoLike;
    @BindView(R.id.huan)
    LinearLayout huan;
    @BindView(R.id.tv_no_datas)
    TextView tvNoDatas;
    private Long answerId;
    private ReplyListAdapter replyListAdapter;
    private String replyContent;
    private int answerStatus;
    private int answerCompanyId;
    private int answerTopCompanyId;
    private String answerUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_answer);
        ButterKnife.bind(this);
        setTitle("专家回复");
        SharedPreferences sp = getSharedPreferences("userXinxi", MODE_PRIVATE);
        String format1 = sp.getString("format1", "");
        int answerLikes = sp.getInt("answerLikes", 1);
        answerId = sp.getLong("answerId", 1);
        tvTime.setText(format1);
        setLeftBack();


        initView();
        //回答
        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replyContent = etContent.getText().toString();
                if (!TextUtils.isEmpty(replyContent)) {
                    getAddData();
                } else {
                    Toast.makeText(ExpertAnswerActivity.this, "消息不可为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        replyListAdapter = new ReplyListAdapter();
        replyListAdapter.bindToRecyclerView(recyclerView);
        getData();
    }

    //添加回答
    private void getAddData() {
        EanfangHttp.post(NewApiService.Reply_Add)
                .params("areAnswerId", answerId)
                .params("replyContent", replyContent)
                .execute(new EanfangCallback<MyReplyAddBean>(this, true, MyReplyAddBean.class) {
                    @Override
                    public void onSuccess(MyReplyAddBean bean) {
                        Toast.makeText(ExpertAnswerActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                        etContent.setText("");
                        getData();
                    }

                    @Override
                    public void onNoData(String message) {

                    }

                    @Override
                    public void onCommitAgain() {
                    }
                });
    }

    //网络请求---列表展示
    private void getData() {
        EanfangHttp.post(NewApiService.Reply_List)
                .params("answerId", answerId)
                .execute(new EanfangCallback<MyReplyListBean>(this, true, MyReplyListBean.class) {
                    @Override
                    public void onSuccess(MyReplyListBean bean) {

                        ivExpertHeader.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + bean.getAnswerInfo().getExpertsCertificationEntity().getAvatarPhoto()));
                        tvExpertName.setText(bean.getAnswerInfo().getExpertsCertificationEntity().getApproveUserName());
                        tvMajor.setText(bean.getAnswerInfo().getExpertsCertificationEntity().getCompany());
                        tvDesc.setText(bean.getAnswerInfo().getAnswerContent());
                        zanCount.setText("点赞量 " + bean.getAnswerInfo().getAnswerLikes());
                        answerStatus = bean.getAnswerInfo().getLikeStatus();
                        answerCompanyId = bean.getAnswerInfo().getAnswerCompanyId();
                        answerTopCompanyId = bean.getAnswerInfo().getAnswerTopCompanyId();
                        answerUserId = bean.getAnswerInfo().getAnswerUserId();
                        if (answerStatus % 2 == 0) {
                            tuLike.setVisibility(View.VISIBLE);
                            tuNoLike.setVisibility(View.GONE);
                        } else {
                            tuNoLike.setVisibility(View.VISIBLE);
                            tuLike.setVisibility(View.GONE);
                        }
                        tvComment.setText("评论 " + bean.getReplyCount());
                        if (bean.getReplyList().size() > 0) {
                            recyclerView.setVisibility(View.VISIBLE);
                            tvNoDatas.setVisibility(View.GONE);
                            replyListAdapter.setNewData(bean.getReplyList());
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

    //点赞
    @OnClick(R.id.huan)
    public void onViewClicked() {
        EanfangHttp.post(NewApiService.ANSWER_Change_Like_Status)
                .params("asId", answerId)
                .params("asUserId", answerUserId)
                .params("asCompanyId", answerCompanyId + "")
                .params("asTopCompanyId", answerTopCompanyId + "")
                .params("likeStatus", answerStatus + "")
                .execute(new EanfangCallback<AnswerChangeLikeStatusBean>(this, true, AnswerChangeLikeStatusBean.class) {
                    @Override
                    public void onSuccess(AnswerChangeLikeStatusBean bean) {
                        if (answerStatus % 2 != 0) {
                            getData();
                            Toast.makeText(ExpertAnswerActivity.this, "点赞成功", Toast.LENGTH_SHORT).show();
                        } else {
                            getData();
                            Toast.makeText(ExpertAnswerActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
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
}
