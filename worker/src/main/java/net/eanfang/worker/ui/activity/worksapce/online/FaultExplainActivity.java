package net.eanfang.worker.ui.activity.worksapce.online;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.AnswerListWithQuestionBean;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.oa.check.DealWithFirstActivity;
import net.eanfang.worker.ui.base.BaseWorkerActivity;
import net.eanfang.worker.util.ImagePerviewUtil;

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
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_major)
    TextView tvMajor;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    //@BindView(R.id.snpl_photos)
    //BGASortableNinePhotoLayout snplPhotos;
    @BindView(R.id.recycler_view_answer)
    RecyclerView recyclerViewAnswer;
    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;

    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";
    @BindView(R.id.iv_pic1)
    SimpleDraweeView ivPic1;
    @BindView(R.id.iv_pic2)
    SimpleDraweeView ivPic2;
    @BindView(R.id.iv_pic3)
    SimpleDraweeView ivPic3;
    @BindView(R.id.ll_pic)
    LinearLayout llPic;

    //private static final int REQUEST_CODE_CHOOSE_EXPLAIN = 1;
    //private static final int REQUEST_CODE_PHOTO_EXPLAIN = 2;
    private int questionId;
    private FaultExplainAdapter mFaultExplainAdapter;
    private int answerCompanyId, answerTopCompanyId, likeStatus;
    private long answerId;
    private String answerUserId;
    private String format1;
    private long b;
    private String questionUserId, questionCompanyId, questionTopCompanyId;
    private List<AnswerListWithQuestionBean.AnswersBean> answers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fault_explain);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        questionId = intent.getIntExtra("QuestionId", 0);
        setTitle("故障解答");
        setLeftBack();
        getData();
        initViews();

    }

    private void initViews() {
        recyclerViewAnswer.setLayoutManager(new LinearLayoutManager(this));
        //专家回复
        mFaultExplainAdapter = new FaultExplainAdapter();
        mFaultExplainAdapter.bindToRecyclerView(recyclerViewAnswer);

        mFaultExplainAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                if (view.getId() == R.id.ll_zan) {
                    answerId = mFaultExplainAdapter.getData().get(position).getAnswerId();
                    answerUserId = mFaultExplainAdapter.getData().get(position).getAnswerUserId();
                    answerCompanyId = mFaultExplainAdapter.getData().get(position).getAnswerCompanyId();
                    answerTopCompanyId = mFaultExplainAdapter.getData().get(position).getAnswerTopCompanyId();
                    likeStatus = mFaultExplainAdapter.getData().get(position).getLikeStatus();
                    getZanData();
                } else if (view.getId() == R.id.ll_comment) {
                    SharedPreferences sp = getSharedPreferences("userXinxi", MODE_PRIVATE);
                    sp.edit().putString("avatarPhoto", mFaultExplainAdapter.getData().get(position).getExpertsCertificationEntity().getAvatarPhoto())
                            .putString("approveUserName", mFaultExplainAdapter.getData().get(position).getExpertsCertificationEntity().getApproveUserName())
                            .putString("company", mFaultExplainAdapter.getData().get(position).getExpertsCertificationEntity().getCompany())
                            .putString("answerContent", mFaultExplainAdapter.getData().get(position).getAnswerContent())
                            .putString("format1", format1)
                            .putLong("answerId", mFaultExplainAdapter.getData().get(position).getAnswerId())
                            .putInt("answerLikes", mFaultExplainAdapter.getData().get(position).getAnswerLikes())
                            .commit();
                    //这个是专家回复的页面
                    Intent intent = new Intent(FaultExplainActivity.this, ExpertAnswerActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    //网络请求--赞
    private void getZanData() {

        EanfangHttp.post(NewApiService.ANSWER_Change_Like_Status)
                .params("asId", answerId)
                .params("asUserId", answerUserId)
                .params("asCompanyId", answerCompanyId + "")
                .params("asTopCompanyId", answerTopCompanyId + "")
                .params("likeStatus", likeStatus + "")
                .execute(new EanfangCallback<AnswerChangeLikeStatusBean>(this, true, AnswerChangeLikeStatusBean.class) {
                    @Override
                    public void onSuccess(AnswerChangeLikeStatusBean bean) {
                        if (likeStatus % 2 != 0) {
                            getData();
                            Toast.makeText(FaultExplainActivity.this, "点赞成功", Toast.LENGTH_SHORT).show();
                        } else {
                            getData();
                            Toast.makeText(FaultExplainActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
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

    //网络请求--展示问题信息
    private void getData() {
        b = (int) questionId;
        EanfangHttp.post(NewApiService.ANSWER_List_With_Question)
                .params("questionId", b)
                .execute(new EanfangCallback<AnswerListWithQuestionBean>(this, true, AnswerListWithQuestionBean.class) {
                    @Override
                    public void onSuccess(AnswerListWithQuestionBean bean) {
                        questionUserId = bean.getQuestion().getQuestionUserId();
                        questionCompanyId = bean.getQuestion().getQuestionCompanyId();
                        questionTopCompanyId = bean.getQuestion().getQuestionTopCompanyId();
                        ivUserHeader.setImageURI(Uri.parse(BuildConfig.OSS_SERVER +bean.getQuestion().getAccountEntity().getAvatar()));
                        tvUserName.setText(bean.getQuestion().getAccountEntity().getNickName());
                        //时间
                        format1 = format(bean.getQuestion().getQuestionCreateDateLong());
                        tvTime.setText(format1);
                        tvMajor.setText("系统:" + bean.getQuestion().getBusinessName() + "      品牌:" + bean.getQuestion().getModelName());
                        //snplPhotos.setDelegate(new BGASortableDelegate(this));
                        tvDesc.setText(bean.getQuestion().getQuestionContent());
                        answers = bean.getAnswers();
                        mFaultExplainAdapter.setNewData(answers);

                        if (!StringUtils.isEmpty(bean.getQuestion().getQuestionPics())) {
                            setPhoto(bean.getQuestion().getQuestionPics());
                        }

                        if (bean.getAnswers().size() < 10) {
                            mFaultExplainAdapter.loadMoreEnd();
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

    //我来回答
    @OnClick(R.id.tv_answer)
    public void onViewClicked() {
        Intent intent = new Intent(FaultExplainActivity.this, MyFreeAskActivity.class);
        intent.putExtra("questionId", b);
        intent.putExtra("questionUserId", questionUserId);
        intent.putExtra("questionCompanyId", questionCompanyId);
        intent.putExtra("questionTopCompanyId", questionTopCompanyId);
        startActivity(intent);
    }

//图片展示
    public void setPhoto(String photoPath) {
        String[] urls = photoPath.split(",");
        ArrayList<String> picList = new ArrayList<String>();
        if (urls.length >= 1) {
            ivPic1.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[0]));
            ivPic1.setVisibility(View.VISIBLE);
            ivPic1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    picList.clear();
                    picList.add(BuildConfig.OSS_SERVER + Uri.parse(urls[0]));
                    ImagePerviewUtil.perviewImage(FaultExplainActivity.this, picList);
                }
            });
        } else {
            ivPic1.setVisibility(View.GONE);
        }

        if (urls.length >= 2) {
            ivPic2.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[1]));
            ivPic2.setVisibility(View.VISIBLE);
            ivPic2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    picList.clear();
                    picList.add(BuildConfig.OSS_SERVER + Uri.parse(urls[1]));
                    ImagePerviewUtil.perviewImage(FaultExplainActivity.this, picList);
                }
            });
        } else {
            ivPic2.setVisibility(View.GONE);
        }
        if (urls.length >= 3) {
            ivPic3.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[2]));
            ivPic3.setVisibility(View.VISIBLE);
            ivPic3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    picList.clear();
                    picList.add(BuildConfig.OSS_SERVER + Uri.parse(urls[2]));
                    ImagePerviewUtil.perviewImage(FaultExplainActivity.this, picList);
                }
            });
        } else {
            ivPic3.setVisibility(View.GONE);
        }
    }


    //时间转化
    public static String format(long millis) {
        long delta = System.currentTimeMillis() - millis;
        if (delta < 1L * ONE_MINUTE) {
//            long seconds = toSeconds(delta);
//            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
            return 1 + ONE_MINUTE_AGO;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天";
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        }
        if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
        } else {
            long years = toYears(delta);
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }

}
