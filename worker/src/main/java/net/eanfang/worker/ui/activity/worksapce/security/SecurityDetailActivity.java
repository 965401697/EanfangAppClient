package net.eanfang.worker.ui.activity.worksapce.security;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.security.SecurityDetailBean;
import com.eanfang.model.security.SecurityFoucsListBean;
import com.eanfang.model.security.SecurityHotListBean;
import com.eanfang.model.security.SecurityLikeBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.ETimeUtils;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.photopicker.com.widget.BGANinePhotoLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.SecurityCommentAdapter;
import net.eanfang.worker.ui.widget.DividerItemDecoration;

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
    BGANinePhotoLayout snplPic;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mType = getIntent().getStringExtra("type");
        setLeftBack();
        setTitle("安防圈");
        initData();
        securityCommentAdapter = new SecurityCommentAdapter();
        rvComments.setLayoutManager(new LinearLayoutManager(this));
        securityCommentAdapter.bindToRecyclerView(rvComments);
        rvComments.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
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
        EanfangHttp.post(NewApiService.SERCURITY_DETAIL)
                .params("id", mId)
                .execute(new EanfangCallback<SecurityDetailBean>(this, true, SecurityDetailBean.class, bean -> {
                    commentList = bean.getList();
                    securityCommentAdapter.setNewData(commentList);
                }));

    }

    public void setHotData() {
        // 发布人
        tvName.setText(hotBean.getPublisherUser().getAccountEntity().getRealName());
        // 头像
        ivSeucrityHeader.setImageURI((Uri.parse(BuildConfig.OSS_SERVER + hotBean.getPublisherUser().getAccountEntity().getAvatar())));
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
         * 0 是关注 1 是未关注
         * */
        if (hotBean.getFollowsStatus() == 0) {
            tvIsFocus.setVisibility(View.GONE);
        } else {
            tvIsFocus.setVisibility(View.VISIBLE);
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
            snplPic.setDelegate(new BGANinePhotoLayout.Delegate() {
                @Override
                public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {

                }
            });
            snplPic.init(this);
            snplPic.setData(picList);
        } else {
            snplPic.setVisibility(View.GONE);
        }
    }

    public void setFoucsData() {
        // 发布人
        tvName.setText(foucsBean.getAccountEntity().getNickName());
        // 头像
        ivSeucrityHeader.setImageURI((Uri.parse(BuildConfig.OSS_SERVER + foucsBean.getAccountEntity().getAvatar())));
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
        if (foucsBean.getAskSpCircleEntity().getPublisherUserId().equals(EanfangApplication.get().getUserId())) {
            tvIsFocus.setVisibility(View.GONE);
        } else {
            tvIsFocus.setVisibility(View.VISIBLE);
        }
        /**
         * 0 是关注 1 是未关注
         * */
        if (foucsBean.getFollowsStatus() == 0) {
            tvIsFocus.setVisibility(View.GONE);
        } else {
            tvIsFocus.setVisibility(View.VISIBLE);
        }
        /**
         * 0 点赞 1 未点赞
         * */
        if (foucsBean.getAskSpCircleEntity().getLikeStatus() == 0) {
            ivLike.setImageResource(R.mipmap.ic_worker_security_like_pressed);
        } else {
            ivLike.setImageResource(R.mipmap.ic_worker_security_like_unpressed);
        }
        if (!StringUtils.isEmpty(foucsBean.getAskSpCircleEntity().getSpcImg())) {
            snplPic.setVisibility(View.VISIBLE);
            String[] pics = foucsBean.getAskSpCircleEntity().getSpcImg().split(",");
            picList.addAll(Stream.of(Arrays.asList(pics)).map(url -> (BuildConfig.OSS_SERVER + url).toString()).toList());
            snplPic.setDelegate(new BGANinePhotoLayout.Delegate() {
                @Override
                public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {

                }
            });
            snplPic.init(this);
            snplPic.setData(picList);
        } else {
            snplPic.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.ll_like, R.id.ll_comments, R.id.ll_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_like:
                doLike();
                break;
            case R.id.ll_comments:
                doComments();
                break;
            case R.id.ll_share:
                doShare();
                break;
            default:
                break;
        }
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
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * 隐藏键盘与布局
     */
    private void hideKeyboard() {
        //隐藏布局
        llEditComments.setVisibility(View.GONE);
        etInput.setText("");//清空输入
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    // 捕获返回键的方法
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //隐藏键盘与布局
//        hideKeyboard();
//        return true;
//    }

}
