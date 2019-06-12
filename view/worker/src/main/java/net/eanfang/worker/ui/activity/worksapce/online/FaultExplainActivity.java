package net.eanfang.worker.ui.activity.worksapce.online;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.StringUtils;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.AnswerListWithQuestionBean;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 故障解答
 */
public class FaultExplainActivity extends BaseWorkerActivity {
    private static final int REQUEST_CODE_CHOOSE_PHOTO = 1;

    private static final int REQUEST_CODE_CHOOSE_PHOTO_two = 1;
    @BindView(R.id.iv_user_header)
    CircleImageView ivUserHeader;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_major)
    TextView tvMajor;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.recycler_view_answer)
    RecyclerView recyclerViewAnswer;
    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;
    @BindView(R.id.snpl_pic)
    BGASortableNinePhotoLayout snplPic;
    private ArrayList<String> picList = new ArrayList<>();
    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";
    @BindView(R.id.tv_no_datas)
    TextView tvNoDatas;
    @BindView(R.id.collect)
    TextView collect;
    @BindView(R.id.recycler_answer_common)
    RecyclerView recyclerAnswerCommon;
    @BindView(R.id.tv_no_datas_common)
    TextView tvNoDatasCommon;

    private FaultExplainAdapter mFaultExplainAdapter;
    private String answerCompanyId;
    private String answerTopCompanyId;
    private int likeStatus;
    private long answerId;
    private String answerUserId;
    private String format1;
    private String questionUserId, questionCompanyId, questionTopCompanyId;
    private List<AnswerListWithQuestionBean.ExpertAnswersBean> answers;
    private Intent intent;
    private long questionIdZ;
    private FaultCommonAdapter mFaultCommonAdapter;
    private List<AnswerListWithQuestionBean.CommonAnswersBean> commonanswers;
    private String failureTypeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fault_explain);
        ButterKnife.bind(this);
        intent = getIntent();
        setTitle("故障解答");
        setLeftBack();
        initViews();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void initViews() {
        //布局管理器
        recyclerViewAnswer.setLayoutManager(new LinearLayoutManager(this));
        recyclerAnswerCommon.setLayoutManager(new LinearLayoutManager(this));
        //专家回复
        mFaultExplainAdapter = new FaultExplainAdapter();
        mFaultExplainAdapter.bindToRecyclerView(recyclerViewAnswer);
        //普通用户回复
        mFaultCommonAdapter = new FaultCommonAdapter();
        mFaultCommonAdapter.bindToRecyclerView(recyclerAnswerCommon);
        //解决滑动停滞问题
        recyclerViewAnswer.setNestedScrollingEnabled(false);
        //用户回复
        mFaultCommonAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                if (view.getId() == R.id.ll_zan) {
                    answerId = mFaultCommonAdapter.getData().get(position).getAnswerId();
                    answerUserId = mFaultCommonAdapter.getData().get(position).getAnswerUserId();
                    answerCompanyId = mFaultCommonAdapter.getData().get(position).getAnswerCompanyId();
                    answerTopCompanyId = mFaultCommonAdapter.getData().get(position).getAnswerTopCompanyId();
                    likeStatus = mFaultCommonAdapter.getData().get(position).getLikeStatus();
                    getZanData();
                } else if (view.getId() == R.id.linear_layout_all) {
                    //这个是专家回复的页面
                    Intent intent = new Intent(FaultExplainActivity.this, ExpertAnswerActivity.class);
                    intent.putExtra("format1", format(mFaultCommonAdapter.getData().get(position).getAnswerCreateTimeLong()));
                    intent.putExtra("answerId", mFaultCommonAdapter.getData().get(position).getAnswerId());
                    startActivity(intent);
                }
            }
        });
        //专家回复
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
                } else if (view.getId() == R.id.linear_layout_all) {
                    //这个是专家回复的页面
                    Intent intent = new Intent(FaultExplainActivity.this, ExpertAnswerActivity.class);
                    intent.putExtra("format1", format(mFaultExplainAdapter.getData().get(position).getAnswerCreateTimeLong()));
                    intent.putExtra("answerId", mFaultExplainAdapter.getData().get(position).getAnswerId());
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
        picList.clear();
        questionIdZ = (int) intent.getIntExtra("QuestionIdZ", 0);
        EanfangHttp.post(NewApiService.ANSWER_List_With_Question)
                .params("questionId", questionIdZ)
                .execute(new EanfangCallback<AnswerListWithQuestionBean>(this, true, AnswerListWithQuestionBean.class) {
                    @Override
                    public void onSuccess(AnswerListWithQuestionBean bean) {
                        if (bean.getQuestion() != null) {
                            failureTypeId = bean.getQuestion().getFailureTypeId();
                            questionUserId = bean.getQuestion().getQuestionUserId();
                            questionCompanyId = bean.getQuestion().getQuestionCompanyId();
                            questionTopCompanyId = bean.getQuestion().getQuestionTopCompanyId();
                            ivUserHeader.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + bean.getQuestion().getAccountEntity().getAvatar()));
                            tvUserName.setText(bean.getQuestion().getAccountEntity().getNickName());
                            //时间
                            format1 = format(bean.getQuestion().getQuestionCreateDateLong());
                            tvTime.setText(format1);
                            tvMajor.setText("系统:" + bean.getQuestion().getBusinessName() + "      品牌:" + bean.getQuestion().getModelName());
                            //snplPhotos.setDelegate(new BGASortableDelegate(this));
                            tvDesc.setText(bean.getQuestion().getQuestionContent());
                            if (!StringUtils.isEmpty(bean.getQuestion().getQuestionPics())) {
                                String[] pics = bean.getQuestion().getQuestionPics().split(",");
                                picList.addAll(Stream.of(Arrays.asList(pics)).map(url -> (BuildConfig.OSS_SERVER + url).toString()).toList());
                                snplPic.setDelegate(new BGASortableDelegate(FaultExplainActivity.this, REQUEST_CODE_CHOOSE_PHOTO, REQUEST_CODE_CHOOSE_PHOTO_two));
                                //            snplPic.init(this);
                                snplPic.setData(picList);
                                snplPic.setEditable(false);
                            } else {
                                snplPic.setVisibility(View.GONE);
                            }
                        }
                        //专家回答
                        answers = bean.getExpertAnswers();
                        commonanswers = bean.getCommonAnswers();
                        boolean answersShow = false;
                        boolean commonAnswersShow = false;
                        if (answers != null && commonanswers != null) {
                            if (answers.size() > 0 && commonanswers.size() <= 0) {
                                answersShow = true;
                                mFaultExplainAdapter.setNewData(answers);
                            } else if (answers.size() <= 0 && commonanswers.size() > 0) {
                                commonAnswersShow = true;
                                mFaultCommonAdapter.setNewData(commonanswers);
                            } else {
                                answersShow = true;
                                commonAnswersShow = true;
                                mFaultExplainAdapter.setNewData(answers);
                                mFaultCommonAdapter.setNewData(commonanswers);
                            }
                        }
                        setUIShow(answersShow, commonAnswersShow);
                        mFaultExplainAdapter.notifyDataSetChanged();
                        mFaultCommonAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onNoData(String message) {
                        setUIShow(false, false);
                    }

                    @Override
                    public void onCommitAgain() {
                    }

                });

    }

    /**
     * UI展示更新
     * @param answersShow 专家列表展示
     * @param commonAnswersShow 用户回复
     */
    private void setUIShow(boolean answersShow, boolean commonAnswersShow){
        recyclerViewAnswer.setVisibility(answersShow ? View.VISIBLE : View.GONE);
        recyclerAnswerCommon.setVisibility(commonAnswersShow ? View.VISIBLE : View.GONE);
        tvNoDatas.setVisibility(answersShow ? View.GONE : View.VISIBLE);
        tvNoDatasCommon.setVisibility(commonAnswersShow ? View.GONE : View.VISIBLE);
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

    @OnClick({R.id.collect, R.id.tv_answer_k})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.collect:
                //类似故障
                Intent intentCollect = new Intent(FaultExplainActivity.this, CommonFaultListActivity.class);
                intentCollect.putExtra("failureTypeId", failureTypeId);
                startActivity(intentCollect);
                finish();
                break;
            case R.id.tv_answer_k:
                //我来回答
                Intent intentAnswer = new Intent(FaultExplainActivity.this, MyFreeAskActivity.class);
                intentAnswer.putExtra("questionId", questionIdZ);
                intentAnswer.putExtra("questionUserId", questionUserId);
                intentAnswer.putExtra("questionCompanyId", questionCompanyId);
                intentAnswer.putExtra("questionTopCompanyId", questionTopCompanyId);
                startActivity(intentAnswer);
                break;
        }
    }
}
