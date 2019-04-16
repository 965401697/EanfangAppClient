package net.eanfang.worker.ui.activity.worksapce.security;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.security.SecurityCommentBean;
import com.eanfang.model.security.SecurityDetailBean;
import com.eanfang.model.security.SecurityFoucsBean;
import com.eanfang.model.security.SecurityLikeBean;
import com.eanfang.model.security.SecurityListBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.ETimeUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.V;
import com.facebook.drawee.view.SimpleDraweeView;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.worker.R;
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

public class SecurityDetailActivity extends BaseActivity {


    private static final int REQUEST_CODE_CHOOSE_PHOTO = 1;

    private static final int REQUEST_CODE_CHOOSE_PHOTO_two = 1;
    @BindView(R.id.iv_seucrity_header)
    SimpleDraweeView ivSeucrityHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_isFocus)
    TextView tvIsFocus;
    @BindView(R.id.tv_content)
    TextView tvContent;
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

    private SecurityListBean.ListBean securityBean;
    private ArrayList<String> picList = new ArrayList<>();
    private Long mId;

    private SecurityCommentAdapter securityCommentAdapter;
    private List<SecurityDetailBean.ListBean> commentList = new ArrayList<>();

    /**
     * 点赞状态
     */
    private int mLikeStatus = 100;

    private GeneralSDialog generalDialog;

    /**
     * 是否关注
     */
    private boolean isFoucus = false;
    /**
     * 是否是朋友
     */
    private int mFriend = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_detail);
        ButterKnife.bind(this);
        initView();
        initListener();
    }


    private void initView() {
        setLeftBack();
        setTitle("安防圈");
        securityCommentAdapter = new SecurityCommentAdapter();
        rvComments.setLayoutManager(new LinearLayoutManager(this));
        securityCommentAdapter.bindToRecyclerView(rvComments);
        rvComments.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvComments.setNestedScrollingEnabled(false);
        mFriend = getIntent().getIntExtra("friend", 100);
        initData();
    }

    private void initData() {
        securityBean = (SecurityListBean.ListBean) getIntent().getSerializableExtra("bean");
        mId = securityBean.getSpcId();
        mLikeStatus = securityBean.getLikeStatus();
        setData();
        getComments();
    }

    private void initListener() {
        securityCommentAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            if (!EanfangApplication.get().getUserId().equals(Long.valueOf(securityCommentAdapter.getData().get(position).getCommentsAnswerId()))) {
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
            SecurityDetailBean.ListBean listBean = (SecurityDetailBean.ListBean) adapter.getData().get(position);
            if (listBean != null && listBean.getCommentUser() != null &&
                    listBean.getCommentUser().getAccId()) {
                gotoUserHomeActivity(listBean.getCommentUser().getAccId());
            }
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
                    getComments();
                }));
    }

    /**
     * 获取评论
     */

    private void getComments() {
        EanfangHttp.post(NewApiService.SERCURITY_DETAIL)
                .params("id", mId)
                .execute(new EanfangCallback<SecurityDetailBean>(this, true, SecurityDetailBean.class, bean -> {
                    commentList = bean.getList();
                    securityCommentAdapter.setNewData(commentList);

                    /**
                     * 阅读数  评价数
                     * */
                    if (bean.getSpcList() != null) {
                        tvReadCount.setText(bean.getSpcList().get(0).getReadCount() + "");
                        tvCommentCount.setText(bean.getSpcList().get(0).getCommentCount() + "");
                    }

                    hideKeyboard();
                }));
    }

    public void setData() {
        // 发布人
        tvName.setText(V.v(() -> securityBean.getAccountEntity().getNickName()));
        // 头像
        ivSeucrityHeader.setImageURI((Uri.parse(BuildConfig.OSS_SERVER + V.v(() -> securityBean.getAccountEntity().getAvatar()))));
        // 公司名称
        tvCompany.setText(securityBean.getPublisherOrg().getOrgName());
        //发布的内容
        tvContent.setText(securityBean.getSpcContent());
        tvTime.setText(ETimeUtils.getTimeFormatText(securityBean.getCreateTime()));
        if (securityBean.getPublisherUserId().equals(EanfangApplication.get().getUserId())) {
            tvIsFocus.setVisibility(View.GONE);
        } else {
            tvIsFocus.setVisibility(View.VISIBLE);
        }
        /**
         * 是否是好友 2 好友 1 不是好友
         * */
        if (mFriend == 2) {
            tvFriend.setVisibility(View.VISIBLE);
        } else {
            tvFriend.setVisibility(View.GONE);
        }
        /**
         * 是否认证
         * */
        if (securityBean.getVerifyStatus() == 1) {
            ivCertifi.setVisibility(View.VISIBLE);
        } else {
            ivCertifi.setVisibility(View.GONE);
        }
        /**
         * 0 是关注 1 是未关注
         * */
        if (securityBean.getFollowsStatus() == 0) {
            tvIsFocus.setText("取消关注");
            isFoucus = true;
        } else {
            tvIsFocus.setText("关注");
            isFoucus = false;
        }
        /**
         * 0 点赞 1 未点赞
         * */
        if (securityBean.getLikeStatus() == 0) {
            ivLike.setImageResource(R.mipmap.ic_worker_security_like_pressed);
        } else {
            ivLike.setImageResource(R.mipmap.ic_worker_security_like_unpressed);
        }
        if (!StringUtils.isEmpty(securityBean.getSpcImg())) {
            snplPic.setVisibility(View.VISIBLE);
            String[] pics = securityBean.getSpcImg().split(",");
            picList.addAll(Stream.of(Arrays.asList(pics)).map(url -> (BuildConfig.OSS_SERVER + url).toString()).toList());

            snplPic.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO, REQUEST_CODE_CHOOSE_PHOTO_two));
            snplPic.setData(picList);
            snplPic.setEditable(false);
        } else {
            snplPic.setVisibility(View.GONE);
        }
    }


    @OnClick({R.id.ll_like, R.id.ll_comments, R.id.ll_share, R.id.tv_send, R.id.tv_isFocus, R.id.iv_seucrity_header})
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
                if (isFoucus) {
                    doUnFoucus(securityBean);
                } else {
                    doFoucus(securityBean);
                }
                break;
            case R.id.iv_seucrity_header:
                if (securityBean != null && securityBean.getAccountEntity() != null
                        && securityBean.getAccountEntity().getAccId() != null) {
                    gotoUserHomeActivity(String.valueOf(securityBean.getAccountEntity().getAccId()));
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
        UserHomeActivity.startActivity(this, accId);
    }

    /**
     * 关注
     */
    private void doFoucus(SecurityListBean.ListBean listBean) {
        SecurityFoucsBean securityFoucsBean = new SecurityFoucsBean();
        securityFoucsBean.setFollowUserId(EanfangApplication.get().getUserId());
        securityFoucsBean.setFollowCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyId());
        securityFoucsBean.setFollowTopCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getTopCompanyId());

        securityFoucsBean.setAsUserId(listBean.getPublisherUserId());
        securityFoucsBean.setAsCompanyId(listBean.getPublisherCompanyId());
        securityFoucsBean.setAsTopCompanyId(listBean.getPublisherTopCompanyId());
        EanfangHttp.post(NewApiService.SERCURITY_FOUCUS)
                .upJson(JSONObject.toJSONString(securityFoucsBean))
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, bean -> {
                    showToast("关注成功");
                    tvIsFocus.setText("取消关注");
                    isFoucus = true;
                }));
    }


    /**
     * 取消关注
     */
    private void doUnFoucus(SecurityListBean.ListBean listBean) {
        SecurityFoucsBean securityFoucsBean = new SecurityFoucsBean();
        securityFoucsBean.setFollowUserId(EanfangApplication.get().getUserId());
        securityFoucsBean.setFollowCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyId());
        securityFoucsBean.setFollowTopCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getTopCompanyId());

        securityFoucsBean.setAsUserId(listBean.getPublisherUserId());
        securityFoucsBean.setAsCompanyId(listBean.getPublisherCompanyId());
        securityFoucsBean.setAsTopCompanyId(listBean.getPublisherTopCompanyId());
        EanfangHttp.post(NewApiService.SERCURITY_DELETEFOUCUS)
                .upJson(JSONObject.toJSONString(securityFoucsBean))
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, bean -> {
                    showToast("已取消关注");
                    tvIsFocus.setText("关注");
                    isFoucus = false;
                }));
    }

    /**
     * 分享
     */
    private void doShare() {
    }

    /**
     * 评论
     */
    private void doComments() {
        ShowKeyboard();
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
        securityCommentBean.setCommentsCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyId());
        securityCommentBean.setCommentsTopCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getTopCompanyId());
        EanfangHttp.post(NewApiService.SERCURITY_COMMENT)
                .upJson(JSONObject.toJSONString(securityCommentBean))
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, bean -> {
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
                    } else {
                        ivLike.setImageResource(R.mipmap.ic_worker_security_like_pressed);
                        mLikeStatus = 0;
                    }
                }));
    }

    /**
     * 显示布局与键盘
     */
    private void ShowKeyboard() {
        //显示布局
        llEditComments.setVisibility(View.VISIBLE);
        llBottom.setVisibility(View.GONE);
        etInput.requestFocus();
        etInput.setFocusable(true);
        etInput.setFocusableInTouchMode(true);
        StringUtils.showKeyboard(this, etInput);
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * 隐藏键盘与布局
     */
    private void hideKeyboard() {
        //隐藏布局
        llEditComments.setVisibility(View.GONE);
        llBottom.setVisibility(View.VISIBLE);
        etInput.setText("");//清空输入
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            llEditComments.setVisibility(View.GONE);
            llBottom.setVisibility(View.VISIBLE);
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
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
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (generalDialog != null) {
            generalDialog.dismiss();
        }
    }
}
