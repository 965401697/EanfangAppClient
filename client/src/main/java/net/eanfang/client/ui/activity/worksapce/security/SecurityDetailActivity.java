package net.eanfang.client.ui.activity.worksapce.security;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
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
import com.eanfang.model.security.SecurityFoucsListBean;
import com.eanfang.model.security.SecurityHotListBean;
import com.eanfang.model.security.SecurityLikeBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.ETimeUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.V;
import com.facebook.drawee.view.SimpleDraweeView;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.security.SecurityCommentAdapter;
import net.eanfang.client.ui.widget.GeneralSDialog;

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
    @BindView(R.id.tv_like_num)
    TextView tvLikeNum;
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

    private SecurityHotListBean.ListBean hotBean;
    private SecurityFoucsListBean.ListBean foucsBean;
    private ArrayList<String> picList = new ArrayList<>();
    private String mType = "";
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
        mType = getIntent().getStringExtra("type");
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
        if (mType.equals("hot")) {
            hotBean = (SecurityHotListBean.ListBean) getIntent().getSerializableExtra("bean");
            mId = hotBean.getSpcId();
            mLikeStatus = hotBean.getLikeStatus();
            setHotData();
        } else {
            foucsBean = (SecurityFoucsListBean.ListBean) getIntent().getSerializableExtra("bean");
            mId = foucsBean.getAskSpCircleEntity().getSpcId();
            mLikeStatus = foucsBean.getAskSpCircleEntity().getLikeStatus();
            setFoucsData();
        }
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
                     * 阅读数
                     * */
                    if (bean.getSpcList() != null) {
                        tvReadCount.setText(bean.getSpcList().get(0).getReadCount() + "");
                    }
                    hideKeyboard();
                }));
    }

    public void setHotData() {
        // 发布人
        tvName.setText(V.v(() -> hotBean.getAccountEntity().getNickName()));
        // 头像
        ivSeucrityHeader.setImageURI((Uri.parse(BuildConfig.OSS_SERVER + V.v(() -> hotBean.getAccountEntity().getAvatar()))));
        // 公司名称
        tvCompany.setText(hotBean.getPublisherOrg().getOrgName());
        //发布的内容
        tvContent.setText(hotBean.getSpcContent());
        // 点赞数量
//        tvLikeNum.setText(hotBean.getLikesCount() + "");
        // 评论数量
//        tvCommentsNum.setText(hotBean.getCommentCount() + "");
        tvTime.setText(ETimeUtils.getTimeFormatText(hotBean.getCreateTime()));
        if (hotBean.getPublisherUserId().equals(EanfangApplication.get().getUserId())) {
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
        if (hotBean.getVerifyStatus() == 1) {
            ivCertifi.setVisibility(View.VISIBLE);
        } else {
            ivCertifi.setVisibility(View.GONE);
        }
        /**
         * 0 是关注 1 是未关注
         * */
        if (hotBean.getFollowsStatus() == 0) {
            tvIsFocus.setText("取消关注");
            isFoucus = true;
        } else {
            tvIsFocus.setText("关注");
            isFoucus = false;
        }
        /**
         * 0 点赞 1 未点赞
         * */
        if (hotBean.getLikeStatus() == 0) {
            ivLike.setImageResource(R.mipmap.ic_worker_security_like_pressed);
        } else {
            ivLike.setImageResource(R.mipmap.ic_worker_security_like_unpressed);
        }
        if (!StringUtils.isEmpty(hotBean.getSpcImg())) {
            snplPic.setVisibility(View.VISIBLE);
            String[] pics = hotBean.getSpcImg().split(",");
            picList.addAll(Stream.of(Arrays.asList(pics)).map(url -> (BuildConfig.OSS_SERVER + url).toString()).toList());

            snplPic.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO, REQUEST_CODE_CHOOSE_PHOTO_two));
//            snplPic.init(this);
            snplPic.setData(picList);
            snplPic.setEditable(false);
        } else {
            snplPic.setVisibility(View.GONE);
        }
    }

    public void setFoucsData() {
        // 发布人
        tvName.setText(V.v(() -> foucsBean.getAccountEntity().getNickName()));
        // 头像
        ivSeucrityHeader.setImageURI((Uri.parse(BuildConfig.OSS_SERVER + V.v(() -> foucsBean.getAccountEntity().getAvatar()))));
        // 公司名称
        if (StringUtils.isEmpty(foucsBean.getOrgUnitEntity().getName())) {
            tvCompany.setText("个人");
        } else {
            tvCompany.setText(foucsBean.getOrgUnitEntity().getName());
        }
        //发布的内容
        tvContent.setText(foucsBean.getAskSpCircleEntity().getSpcContent());
        // 点赞数量
//        tvLikeNum.setText(foucsBean.getAskSpCircleEntity().getLikesCount() + "");
        // 评论数量
//        tvCommentsNum.setText(foucsBean.getAskSpCircleEntity().getCommentCount() + "");
        tvTime.setText(ETimeUtils.getTimeFormatText(foucsBean.getAskSpCircleEntity().getCreateTime()));
        /**
         * 0 是关注 1 是未关注
         * */
        if (foucsBean.getAskSpCircleEntity().getPublisherUserId().equals(EanfangApplication.get().getUserId()) || foucsBean.getFollowsStatus() == 0) {
            tvIsFocus.setText("取消关注");
            isFoucus = true;
        } else {
            tvIsFocus.setText("关注");
            isFoucus = false;
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
        if (foucsBean.getAskSpCircleEntity().getVerifyStatus() == 1) {
            ivCertifi.setVisibility(View.VISIBLE);
        } else {
            ivCertifi.setVisibility(View.GONE);
        }
        /**
         * 0 点赞 1 未点赞
         * */
        if (foucsBean.getAskSpCircleEntity().getLikeStatus() == 0) {
            ivLike.setImageResource(R.mipmap.ic_worker_security_like_pressed);
        } else {
            ivLike.setImageResource(R.mipmap.ic_worker_security_like_unpressed);
        }
        /**
         * 阅读数
         * */
        tvReadCount.setText(foucsBean.getAskSpCircleEntity().getReadCount() + "");
        if (!StringUtils.isEmpty(foucsBean.getAskSpCircleEntity().getSpcImg())) {
            snplPic.setVisibility(View.VISIBLE);
            String[] pics = foucsBean.getAskSpCircleEntity().getSpcImg().split(",");
            picList.addAll(Stream.of(Arrays.asList(pics)).map(url -> (BuildConfig.OSS_SERVER + url).toString()).toList());
            snplPic.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO, REQUEST_CODE_CHOOSE_PHOTO_two));
//            snplPic.init(this);
            snplPic.setData(picList);
            snplPic.setEditable(false);
        } else {
            snplPic.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.ll_like, R.id.ll_comments, R.id.ll_share, R.id.tv_send, R.id.tv_isFocus})
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
                    if (mType.equals("hot")) {
                        doUnHotFoucus(hotBean);
                    } else {
                        doUnFoucsFoucus(foucsBean);
                    }
                } else {
                    if (mType.equals("hot")) {
                        doHotFoucus(hotBean);
                    } else {
                        doFoucusFoucus(foucsBean);
                    }
                }
                break;

            default:
                break;
        }
    }

    /**
     * 关注
     */
    private void doHotFoucus(SecurityHotListBean.ListBean listBean) {
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

    private void doFoucusFoucus(SecurityFoucsListBean.ListBean listBean) {
        SecurityFoucsBean securityFoucsBean = new SecurityFoucsBean();
        securityFoucsBean.setFollowUserId(EanfangApplication.get().getUserId());
        securityFoucsBean.setFollowCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyId());
        securityFoucsBean.setFollowTopCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getTopCompanyId());

        securityFoucsBean.setAsUserId(listBean.getAskSpCircleEntity().getPublisherUserId());
        securityFoucsBean.setAsCompanyId(listBean.getAskSpCircleEntity().getPublisherCompanyId());
        securityFoucsBean.setAsTopCompanyId(listBean.getAskSpCircleEntity().getPublisherTopCompanyId());
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
    /**
     * 取消关注
     */
    private void doUnHotFoucus(SecurityHotListBean.ListBean listBean) {
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

    private void doUnFoucsFoucus(SecurityFoucsListBean.ListBean listBean) {
        SecurityFoucsBean securityFoucsBean = new SecurityFoucsBean();
        securityFoucsBean.setFollowUserId(EanfangApplication.get().getUserId());
        securityFoucsBean.setFollowCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyId());
        securityFoucsBean.setFollowTopCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getTopCompanyId());

        securityFoucsBean.setAsUserId(listBean.getAskSpCircleEntity().getPublisherUserId());
        securityFoucsBean.setAsCompanyId(listBean.getAskSpCircleEntity().getPublisherCompanyId());
        securityFoucsBean.setAsTopCompanyId(listBean.getAskSpCircleEntity().getPublisherTopCompanyId());
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
