package net.eanfang.client.ui.activity.worksapce.online;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Stream;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.StringUtils;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExpertAnswerActivity extends BaseClientActivity {
    private static final int REQUEST_CODE_CHOOSE_PHOTO = 1;

    private static final int REQUEST_CODE_CHOOSE_PHOTO_two = 1;
    @BindView(R.id.iv_expert_header)
    CircleImageView ivExpertHeader;
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
    @BindView(R.id.snpl_pic)
    BGASortableNinePhotoLayout snplPic;
    private int answerId;
    private ReplyListAdapter replyListAdapter;
    private ArrayList<String> picList = new ArrayList<>();
    private String replyContent;
    private int answerStatus;
    private String answerCompanyId;
    private String answerTopCompanyId;
    private String answerUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_expert_answer);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("专家回复");
        Intent intent = getIntent();
        String format1 = intent.getStringExtra("format1");
        answerId = intent.getIntExtra("answerId", 0);
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
    }

    //添加回答
    private void getAddData() {
        EanfangHttp.post(NewApiService.Reply_Add)
                .params("areAnswerId", Long.valueOf(answerId))
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

    /**
     * /网络请求---列表展示
     */
    private void getData() {
        picList.clear();
        EanfangHttp.post(NewApiService.Reply_List)
                .params("answerId", Long.valueOf(answerId))
                .execute(new EanfangCallback<MyReplyListBean>(this, true, MyReplyListBean.class) {
                    @Override
                    public void onSuccess(MyReplyListBean bean) {
                        if (bean.getAnswerInfo().getAnswerUserType() == 4 || bean.getAnswerInfo().getAnswerUserType() == 5) {
                            GlideUtil.intoImageView(ExpertAnswerActivity.this,Uri.parse(BuildConfig.OSS_SERVER
                                    + bean.getAnswerInfo().getExpertsCertificationEntity().getAvatarPhoto()),ivExpertHeader);
                            tvExpertName.setText(bean.getAnswerInfo().getExpertsCertificationEntity().getApproveUserName());
                            tvMajor.setText(bean.getAnswerInfo().getExpertsCertificationEntity().getCompany());
                        } else if (bean.getAnswerInfo().getAnswerUserType() == 0 || bean.getAnswerInfo().getAnswerUserType() == 2) {
                            setTitle("用户回复");
                            GlideUtil.intoImageView(ExpertAnswerActivity.this,
                                    Uri.parse(BuildConfig.OSS_SERVER + bean.getAnswerInfo().getAccountEntity().getAvatar()),ivExpertHeader);
                            tvExpertName.setText(bean.getAnswerInfo().getAccountEntity().getNickName());
                            tvMajor.setVisibility(View.GONE);
                        }
                        if (!StringUtils.isEmpty(bean.getAnswerInfo().getAnswerPics())) {
                            String[] pics = bean.getAnswerInfo().getAnswerPics().split(",");
                            picList.addAll(Stream.of(Arrays.asList(pics)).map(url -> (BuildConfig.OSS_SERVER + url)).toList());
                            snplPic.setDelegate(new BGASortableDelegate(ExpertAnswerActivity.this, REQUEST_CODE_CHOOSE_PHOTO, REQUEST_CODE_CHOOSE_PHOTO_two));
                            //            snplPic.init(this);
                            snplPic.setData(picList);
                            snplPic.setEditable(false);
                        }else {
                            snplPic.setVisibility(View.GONE);
                        }
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
                        } else {
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
