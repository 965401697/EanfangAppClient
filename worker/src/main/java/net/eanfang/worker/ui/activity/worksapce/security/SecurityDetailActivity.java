package net.eanfang.worker.ui.activity.worksapce.security;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.security.SecurityCommentBean;
import com.eanfang.model.security.SecurityDetailBean;
import com.eanfang.model.security.SecurityFoucsBean;
import com.eanfang.model.security.SecurityLikeBean;
import com.eanfang.model.security.SecurityListBean;
import com.eanfang.takevideo.PlayVideoActivity;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.ETimeUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.StringUtils;
import com.eanfang.util.V;
import com.eanfang.witget.DefaultPopWindow;
import com.eanfang.witget.mentionedittext.edit.util.FormatRangeManager;
import com.eanfang.witget.mentionedittext.text.MentionTextView;
import com.eanfang.witget.mentionedittext.text.listener.Parser;
import com.eanfang.witget.mentionedittext.util.SecurityItemUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.im.SelectIMContactActivity;
import net.eanfang.worker.ui.activity.my.UserHomeActivity;
import net.eanfang.worker.ui.adapter.security.SecurityCommentAdapter;
import net.eanfang.worker.ui.widget.DividerItemDecoration;
import net.eanfang.worker.ui.widget.GeneralSDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author guanluocang
 * @data 2019/2/15
 * @description 安防圈详情
 */

public class SecurityDetailActivity extends BaseActivity implements Parser.OnParseAtClickListener,
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {


    private static final int REQUEST_CODE_CHOOSE_PHOTO = 1;

    private static final int REQUEST_CODE_COMMENT_DETAIL = 100;

    private static final int REQUEST_CODE_CHOOSE_PHOTO_two = 1;
    @BindView(R.id.iv_seucrity_header)
    SimpleDraweeView ivSeucrityHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_isFocus)
    TextView tvIsFocus;
    @BindView(R.id.snpl_pic)
    BGASortableNinePhotoLayout snplPic;
    @BindView(R.id.iv_like)
    ImageView ivLike;
    @BindView(R.id.iv_comment)
    ImageView ivComment;
    //    @BindView(R.id.tv_comments_num)
//    TextView tvCommentsNum;
    @BindView(R.id.ll_comments)
    LinearLayout llComments;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.rv_comments)
    RecyclerView rvComments;
    @BindView(R.id.ll_edit_comments)
    LinearLayout llEditComments;
    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.tv_friend)
    TextView tvFriend;
    @BindView(R.id.iv_certifi)
    ImageView ivCertifi;
    @BindView(R.id.tv_readCount)
    TextView tvReadCount;
    @BindView(R.id.tv_question_content)
    TextView tvQuestionContent;
    @BindView(R.id.ll_question)
    LinearLayout llQuestion;
    @BindView(R.id.tv_comment_count)
    TextView tvCommentCount;
    @BindView(R.id.iv_show_video)
    SimpleDraweeView ivShowVideo;
    @BindView(R.id.rl_video)
    RelativeLayout rlVideo;
    @BindView(R.id.tv_content)
    MentionTextView tvContent;
    @BindView(R.id.swipe_fresh)
    SwipeRefreshLayout swipeFresh;

    private SecurityDetailBean.SpcListBean securityDetailBean;
    private ArrayList<String> picList = new ArrayList<>();
    private Long mId;

    private SecurityCommentAdapter securityCommentAdapter;
    private List<SecurityDetailBean.PageUtilBean.ListBean> commentList = new ArrayList<>();

    /**
     * 点赞状态
     */
    private int mLikeStatus = 100;
    private int mLikeCount;
    private boolean isLikeEdit = false;

    private GeneralSDialog generalDialog;

    /**
     * 是否关注
     */
    private boolean isFoucus = false;
    private boolean isFoucsEdit = false;
    /**
     * 是否评论
     */
    private boolean isCommont = false;
    private boolean isFirstCome = true;
    private boolean isCommentEdit = false;

    private Parser mTagParser = new Parser(this);
    protected FormatRangeManager mRangeManager = new FormatRangeManager();
    private String mContent = "";
    private String atName = "";

    /**
     * 跳转用户首页的是否是发布内容的人
     */
    private boolean mIsPubUid;

    /**
     * 修改item状态
     */
    private SecurityListBean.ListBean mItenSecurityDetailBean;
    /**
     * 删除安防圈
     */
    private View mPopWindowContent;
    private TextView mTvDelete;

    private QueryEntry mQueryEntry;
    private int page = 1;
    /**
     * 是否回复
     */
    private boolean isReply = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_detail);
        ButterKnife.bind(this);
        initView();
        initListener();
    }


    private void initView() {
        setTitle("安防圈");
        mPopWindowContent = LayoutInflater.from(this).inflate(R.layout.layout_pop_security_delete, null);
        mTvDelete = mPopWindowContent.findViewById(R.id.tv_delete);
        mTvDelete.setText("删除");
        securityCommentAdapter = new SecurityCommentAdapter();
        rvComments.setLayoutManager(new LinearLayoutManager(this));
        securityCommentAdapter.bindToRecyclerView(rvComments);
        swipeFresh.setOnRefreshListener(this);
        securityCommentAdapter.setOnLoadMoreListener(this, rvComments);
        securityCommentAdapter.disableLoadMoreIfNotFullPage();
        rvComments.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        isCommont = getIntent().getBooleanExtra("isCommon", false);
        mId = getIntent().getLongExtra("spcId", 0);
        mItenSecurityDetailBean = new SecurityListBean.ListBean();
        initData();
    }

    private void initData() {
        tvContent.setMovementMethod(new LinkMovementMethod());
        tvContent.setParserConverter(mTagParser);
        if (isCommont) {
            ShowKeyboard();
        }
        getComments();
    }

    private void initListener() {
        securityCommentAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("mCommentId", securityCommentAdapter.getData().get(position).getId());
            bundle.putLong("mAsId", securityCommentAdapter.getData().get(position).getAsId());
            bundle.putSerializable("mComment", securityCommentAdapter.getData().get(position));
            JumpItent.jump(this, SecurityCommentDetailActivity.class, bundle, REQUEST_CODE_COMMENT_DETAIL);
        });
        securityCommentAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            if (!EanfangApplication.get().getAccId().equals(Long.valueOf(securityCommentAdapter.getData().get(position).getCommentsAnswerAccId()))) {
                showToast("只可删除自己评论");
                return false;
            }
            generalDialog = new GeneralSDialog(this, "确定删除此评论吗", new GeneralSDialog.OnConfimClickListener() {
                @Override
                public void onConfim() {
                    doDeleteComment(securityCommentAdapter.getData().get(position).getId());
                }
            });
            generalDialog.show();
            return false;
        });

        securityCommentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            SecurityDetailBean.PageUtilBean.ListBean listBean = (SecurityDetailBean.PageUtilBean.ListBean) adapter.getData().get(position);
            if (listBean != null && listBean.getCommentUser() != null &&
                    listBean.getCommentUser().getAccId() != null) {
                mIsPubUid = listBean.getCommentUser().getAccId().
                        equals(String.valueOf(securityDetailBean.getAccountEntity().getAccId()));
                gotoUserHomeActivity(listBean.getCommentUser().getAccId());
            }
        });
        setLeftBack((v) -> {
            Intent intent = new Intent();
            intent.putExtra("itemStatus", mItenSecurityDetailBean);
            intent.putExtra("isLikeEdit", isLikeEdit);
            intent.putExtra("isFoucsEdit", isFoucsEdit);
            intent.putExtra("isCommentEdit", isCommentEdit);
            setResult(RESULT_OK, intent);
            finishSelf();
        });
        DefaultPopWindow popWindow = new DefaultPopWindow(mPopWindowContent);
        popWindow.setOnDismissListener(() -> popWindow.backgroundAlpha(SecurityDetailActivity.this, 1.0f));
        mTvDelete.setOnClickListener(v -> {
            new TrueFalseDialog(this, "系统提示", "是否删除?", () -> {
                doDelete();
            }).showDialog();
            popWindow.dismiss();
        });
        setRightImageOnClickListener(v -> {
            popWindow.showAsDropDown(findViewById(R.id.iv_right));
            popWindow.backgroundAlpha(SecurityDetailActivity.this, 0.5f);
        });
    }

    /**
     * 删除评论
     */
    private void doDeleteComment(int mCommentId) {
        EanfangHttp.post(NewApiService.SERCURITY_COMMENT_DELETE)
                .params("id", mCommentId)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, bean -> {
                    showToast("删除成功");
                    generalDialog.dismiss();
                    page = 1;
                    getComments();
                }));
    }

    /**
     * 获取评论
     */

    private void getComments() {
        if (mQueryEntry == null) {
            mQueryEntry = new QueryEntry();
        }
        mQueryEntry.setPage(page);
        mQueryEntry.setSize(10);
        mQueryEntry.getEquals().put("id", mId + "");
        EanfangHttp.post(NewApiService.SERCURITY_DETAIL)
                .upJson(JsonUtils.obj2String(mQueryEntry))
                .execute(new EanfangCallback<SecurityDetailBean>(this, true, SecurityDetailBean.class) {
                    @Override
                    public void onSuccess(SecurityDetailBean bean) {
                        if (page == 1) {
                            commentList = bean.getPageUtil().getList();
                            securityCommentAdapter.setNewData(commentList);
                            securityDetailBean = bean.getSpcList();

                            /**
                             * 阅读数  评价数
                             * */
                            if (bean.getSpcList() != null) {
                                tvReadCount.setText(bean.getSpcList().getReadCount() + "");
                                tvCommentCount.setText(bean.getSpcList().getCommentCount() + "");
                            }
                            if (isCommont && isFirstCome && !isReply) {
                                ShowKeyboard();
                            } else {
                                hideKeyboard();
                            }
                            rvComments.scrollToPosition(0);
                            swipeFresh.setRefreshing(false);
                            securityCommentAdapter.loadMoreComplete();
                            if (bean.getPageUtil().getList() != null && bean.getPageUtil().getList().size() < 10) {
                                securityCommentAdapter.loadMoreEnd();
                                //释放对象
                                mQueryEntry = null;
                            }
                            mItenSecurityDetailBean.setCommentCount(bean.getSpcList().getCommentCount());
                            setData(securityDetailBean);
                        } else {
                            securityCommentAdapter.addData(bean.getPageUtil().getList());
                            securityCommentAdapter.loadMoreComplete();
                            if (bean.getPageUtil().getList().size() < 10) {
                                securityCommentAdapter.loadMoreEnd();
                            }
                        }
                    }

                    @Override
                    public void onNoData(String message) {
                        swipeFresh.setRefreshing(false);
                        securityCommentAdapter.loadMoreEnd();
                    }

                    @Override
                    public void onCommitAgain() {
                        swipeFresh.setRefreshing(false);
                    }
                });
    }

    public void setData(SecurityDetailBean.SpcListBean securityDetailBean) {
        mLikeStatus = securityDetailBean.getLikeStatus();
        mLikeCount = securityDetailBean.getLikesCount();
        mItenSecurityDetailBean.setReadCount(securityDetailBean.getReadCount());
        mItenSecurityDetailBean.setAccountEntity(securityDetailBean.getAccountEntity());
        mItenSecurityDetailBean.setReadStatus(securityDetailBean.getReadStatus());
        // 发布人
        tvName.setText(V.v(() -> securityDetailBean.getAccountEntity().getRealName()));
        // 头像
        ivSeucrityHeader.setImageURI((Uri.parse(BuildConfig.OSS_SERVER + V.v(() -> securityDetailBean.getAccountEntity().getAvatar()))));
        // 公司名称
        tvCompany.setText(securityDetailBean.getPublisherOrg().getOrgName());
        //发布的内容
        // 艾特人
        if (V.v(() -> (securityDetailBean.getAtMap() != null))) {
            atName = SecurityItemUtil.getInstance().doJonint(securityDetailBean.getAtMap());
        } else {
            atName = "";
        }
        mContent = securityDetailBean.getSpcContent() + atName;
        CharSequence convertMetionString = mRangeManager.getFormatCharSequence(mContent);
        tvContent.setText(convertMetionString);

        tvTime.setText(ETimeUtils.getTimeFormatText(securityDetailBean.getCreateTime()));
        if (securityDetailBean.getPublisherAccId().equals(EanfangApplication.get().getAccId())) {
            tvIsFocus.setVisibility(View.GONE);
            setRightImageVisible();
            setRightImageResId(R.drawable.icon_right_more);
        } else {
            tvIsFocus.setVisibility(View.VISIBLE);
            setRightImageGone();
        }
        /**
         * 是否是好友 2 好友 1 不是好友
         * */
        if (securityDetailBean.getFriend() == 2) {
            tvFriend.setVisibility(View.VISIBLE);
        } else {
            tvFriend.setVisibility(View.GONE);
        }
        /**
         * 是否认证
         * */
        if (securityDetailBean.getVerifyStatus() == 1) {
            ivCertifi.setVisibility(View.VISIBLE);
        } else {
            ivCertifi.setVisibility(View.GONE);
        }
        /**
         * 0 是关注 1 是未关注
         * */
        if (securityDetailBean.getFollowsStatus() == 0) {
            tvIsFocus.setText("取消关注");
            isFoucus = true;
        } else {
            tvIsFocus.setText("关注");
            isFoucus = false;
        }
        /**
         * 0 点赞 1 未点赞
         * */
        if (securityDetailBean.getLikeStatus() == 0) {
            ivLike.setImageResource(R.mipmap.ic_worker_security_like_pressed);
        } else {
            ivLike.setImageResource(R.mipmap.ic_worker_security_like_unpressed);
        }
        snplPic.setData(null);
        if (!StringUtils.isEmpty(securityDetailBean.getSpcImg())) {
            snplPic.setVisibility(View.VISIBLE);
            String[] pics = securityDetailBean.getSpcImg().split(",");
            picList.addAll(Stream.of(Arrays.asList(pics)).map(url -> (BuildConfig.OSS_SERVER + url).toString()).toList());

            snplPic.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO, REQUEST_CODE_CHOOSE_PHOTO_two));
            snplPic.setData(picList);
            snplPic.setEditable(false);
        } else {
            snplPic.setVisibility(View.GONE);
        }
        if (!StringUtils.isEmpty(securityDetailBean.getSpcVideo())) {
            rlVideo.setVisibility(View.VISIBLE);
            ivShowVideo.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + securityDetailBean.getSpcVideo() + ".jpg"));
        } else {
            rlVideo.setVisibility(View.GONE);
        }
    }


    @OnClick({R.id.ll_like, R.id.ll_comments, R.id.ll_share, R.id.tv_send, R.id.tv_isFocus, R.id.iv_seucrity_header, R.id.rl_video})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_like:
                doLike();
                break;
            case R.id.ll_comments:
                ShowKeyboard();
                break;
            case R.id.ll_share:
                doShare();
                break;
            case R.id.tv_send:
                doComments();
                break;
            case R.id.tv_isFocus:
                doUnFoucus(securityDetailBean);
                break;
            case R.id.rl_video:
                Bundle bundle = new Bundle();
                bundle.putString("videoPath", BuildConfig.OSS_SERVER + securityDetailBean.getSpcVideo() + ".mp4");
                JumpItent.jump(SecurityDetailActivity.this, PlayVideoActivity.class, bundle);
                break;
            case R.id.iv_seucrity_header:
                if (securityDetailBean != null && securityDetailBean.getAccountEntity() != null
                        && securityDetailBean.getAccountEntity().getAccId() != null) {
                    mIsPubUid = true;
                    gotoUserHomeActivity(String.valueOf(securityDetailBean.getAccountEntity().getAccId()));
                }
                break;
            default:
                break;
        }
    }

    /**
     * 跳转用户主页面
     *
     * @param accId
     */
    private void gotoUserHomeActivity(String accId) {
        UserHomeActivity.startActivityForAccId(this, accId);
    }

    /**
     * 关注
     */
    private void doUnFoucus(SecurityDetailBean.SpcListBean listBean) {
        SecurityFoucsBean securityFoucsBean = new SecurityFoucsBean();
        securityFoucsBean.setFollowUserId(EanfangApplication.get().getUserId());
        securityFoucsBean.setFollowCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyId());
        securityFoucsBean.setFollowTopCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getTopCompanyId());

        securityFoucsBean.setAsUserId(listBean.getPublisherUserId());
        securityFoucsBean.setAsCompanyId(listBean.getPublisherCompanyId());
        securityFoucsBean.setAsTopCompanyId(listBean.getPublisherTopCompanyId());
        securityFoucsBean.setAsAccId(listBean.getPublisherUser().getAccId());
        securityFoucsBean.setFollowAccId(EanfangApplication.get().getAccId());
        /**
         * 状态：0 关注 1 未关注
         * */
        securityFoucsBean.setFollowsStatus(listBean.getFollowsStatus() == 0 ? 1 : 0);
        EanfangHttp.post(NewApiService.SERCURITY_FOUCUS)
                .upJson(JSONObject.toJSONString(securityFoucsBean))
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, bean -> {
                    if (isFoucus) {
                        showToast("已取消关注");
                        tvIsFocus.setText("关注");
                        isFoucus = false;
                    } else {
                        showToast("关注成功");
                        tvIsFocus.setText("取消关注");
                        isFoucus = true;
                    }
                    mItenSecurityDetailBean.setFollowsStatus(listBean.getFollowsStatus() == 0 ? 1 : 0);
                    isFoucsEdit = true;
                }));
    }

    /**
     * 分享 分享到好友
     */
    private void doShare() {
        //分享聊天
        if (securityDetailBean != null) {
            Intent intent = new Intent(SecurityDetailActivity.this, SelectIMContactActivity.class);
            Bundle bundle = new Bundle();

            bundle.putString("id", String.valueOf(securityDetailBean.getSpcId()));
            bundle.putString("orderNum", securityDetailBean.getPublisherOrg().getOrgName());
            if (!StringUtils.isEmpty(securityDetailBean.getSpcImg())) {
                bundle.putString("picUrl", securityDetailBean.getSpcImg().split(",")[0]);
            }
            bundle.putString("creatTime", securityDetailBean.getSpcContent());
            bundle.putString("workerName", securityDetailBean.getAccountEntity().getRealName());
            bundle.putString("status", String.valueOf(securityDetailBean.getFollowsStatus()));
            bundle.putString("shareType", "8");
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    /**
     * 评论
     */
    private void doComments() {
        String mComments = etInput.getText().toString().trim();
        if (StringUtils.isEmpty(mComments)) {
            showToast("请输入评论内容");
            return;
        }
        SecurityCommentBean securityCommentBean = new SecurityCommentBean();
        securityCommentBean.setType("0");
        securityCommentBean.setStatus("0");
        securityCommentBean.setCommentsContent(mComments);
        securityCommentBean.setAsId(mId);
        securityCommentBean.setCommentsAnswerId(EanfangApplication.get().getUserId());
        securityCommentBean.setCommentsAnswerAccId(EanfangApplication.get().getUser().getAccount().getAccId());
        securityCommentBean.setCommentsCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyId());
        securityCommentBean.setCommentsTopCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getTopCompanyId());
        securityCommentBean.setTopCommentsId(null);
        securityCommentBean.setParentCommentsId(null);
        EanfangHttp.post(NewApiService.SERCURITY_COMMENT)
                .upJson(JSONObject.toJSONString(securityCommentBean))
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, bean -> {
                    isFirstCome = false;
                    isCommentEdit = true;
                    hideKeyboard();
                    page = 1;
                    getComments();

                }));

    }

    /**
     * 点赞
     */
    private void doLike() {
        SecurityLikeBean securityLikeBean = new SecurityLikeBean();
        securityLikeBean.setAsId(mId);
        securityLikeBean.setType("0");
        /**
         * 状态：0 点赞 1 未点赞
         * */
        if (mLikeStatus == 0) {
            securityLikeBean.setLikeStatus("1");
        } else {
            securityLikeBean.setLikeStatus("0");
        }
        securityLikeBean.setLikeUserId(EanfangApplication.get().getUserId());
        securityLikeBean.setLikeCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyId());
        securityLikeBean.setLikeTopCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getTopCompanyId());
        EanfangHttp.post(NewApiService.SERCURITY_LIKE)
                .upJson(JSONObject.toJSONString(securityLikeBean))
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, bean -> {
                    /**
                     * 0 点赞 1 未点赞
                     * */
                    if (mLikeStatus == 0) {
                        mLikeStatus = 1;
                        ivLike.setImageResource(R.mipmap.ic_worker_security_like_unpressed);
                        mItenSecurityDetailBean.setLikeStatus(1);
                        mItenSecurityDetailBean.setLikesCount(mLikeCount - 1);
                    } else {
                        ivLike.setImageResource(R.mipmap.ic_worker_security_like_pressed);
                        mLikeStatus = 0;
                        mItenSecurityDetailBean.setLikeStatus(0);
                        mItenSecurityDetailBean.setLikesCount(mLikeCount + 1);
                    }
                    isLikeEdit = true;
                }));
    }

    /**
     * 显示布局与键盘
     */
    private void ShowKeyboard() {
        //显示布局
        llEditComments.setVisibility(View.VISIBLE);
        llBottom.setVisibility(View.GONE);
        etInput.setFocusable(true);
        etInput.setFocusableInTouchMode(true);
        etInput.requestFocus();
        StringUtils.showKeyboard(SecurityDetailActivity.this, etInput);
    }


    /**
     * 删除安防圈
     */
    private void doDelete() {
        EanfangHttp.post(NewApiService.SERCURITY_DELETE)
                .params("spcId", mId)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, bean -> {
                    Intent intent = new Intent();
                    intent.putExtra("isDelete", true);
                    setResult(RESULT_OK, intent);
                    finishSelf();
                }));
    }

    /**
     * 隐藏键盘与布局
     */
    private void hideKeyboard() {
        //隐藏布局
        llEditComments.setVisibility(View.GONE);
        llBottom.setVisibility(View.VISIBLE);
        //清空输入
        etInput.setText("");
        View view = this.getCurrentFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 点击其他地方时，将软键盘隐藏
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev, tvSend)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    llEditComments.setVisibility(View.GONE);
                    llBottom.setVisibility(View.VISIBLE);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event, TextView tvSend) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            int[] sendLeftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            tvSend.getLocationInWindow(sendLeftTop);
            int left = leftTop[0], top = leftTop[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            int sendLeft = sendLeftTop[0], sendTop = sendLeftTop[1], sendBottom = sendTop + tvSend.getHeight(), sendRight = sendLeft + tvSend.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else if (event.getX() > sendLeft && event.getX() < sendRight
                    && event.getY() > sendTop && event.getY() < sendBottom) {
                // 点击的是发送按钮区域，保留点击事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            hideKeyboard();
        }
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            intent.putExtra("itemStatus", mItenSecurityDetailBean);
            intent.putExtra("isLikeEdit", isLikeEdit);
            intent.putExtra("isFoucsEdit", isFoucsEdit);
            intent.putExtra("isCommentEdit", isCommentEdit);
            setResult(RESULT_OK, intent);
            finishSelf();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (generalDialog != null) {
            generalDialog.dismiss();
        }
    }

    @Override
    public void onAtClik(Long mUserId) {
        UserHomeActivity.startActivityForUid(this, mUserId);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        mQueryEntry = null;
        page = 1;
        getComments();
    }


    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        page++;
        getComments();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (mIsPubUid) {
                isFoucus = data.getBooleanExtra(UserHomeActivity.RESULT_FOLLOW_STATE, true);
                isFoucsEdit = isFoucus;
                tvIsFocus.setText(isFoucus ? "取消关注" : "关注");
            }
            boolean isEdit = data.getBooleanExtra("isEdit", true);
            if (isEdit) {
                isReply = true;
                isCommentEdit = true;
                page = 1;
                getComments();
            }
        }
    }
}
