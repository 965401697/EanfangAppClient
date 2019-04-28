package net.eanfang.client.ui.activity.my;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.UserHomePageBean;
import com.eanfang.util.JumpItent;
import com.eanfang.util.StringUtils;
import com.eanfang.util.ToastUtil;
import com.eanfang.witget.DefaultPopWindow;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.security.SecurityPersonalActivity;
import net.eanfang.client.ui.adapter.UserHomeAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;

import org.json.JSONObject;

import java.text.MessageFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * @author liangkailun
 * Date: 2019/04/03
 * Describe: 用户主页
 */
public class UserHomeActivity extends BaseClientActivity {
    private static final String TAG = "UserHomeActivity";
    private static final String EXTRA_UID = "UserHomeActivity.accId";
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.img_user_header)
    SimpleDraweeView mImgUserHeader;
    @BindView(R.id.tv_nickname)
    TextView mTvNickname;
    @BindView(R.id.tv_userInfo)
    TextView mTvUserInfo;
    @BindView(R.id.img_answer)
    ImageView mImgAnswer;
    @BindView(R.id.img_circle)
    ImageView mImgCircle;
    @BindView(R.id.rec_career_info)
    RecyclerView mRecCareerInfo;
    @BindView(R.id.tv_position_location)
    TextView mTvPositionLocation;
    @BindView(R.id.tv_intro)
    TextView mTvIntro;
    @BindView(R.id.img_noData)
    ImageView mImgNoData;
    @BindView(R.id.tv_noData)
    TextView mTvNoData;
    @BindView(R.id.rl_user_home_concern)
    RelativeLayout mRlUserHomeConcern;
    @BindView(R.id.rl_user_home_friend)
    RelativeLayout mRlUserHomeFriend;
    @BindView(R.id.tv_user_home_concern)
    TextView mTvUserHomeConcern;
    @BindView(R.id.img_user_home_concern)
    ImageView mImgUserHomeConcern;
    @BindView(R.id.tv_user_home_friend)
    TextView mTvUserHomeFriend;
    @BindView(R.id.img_user_home_friend)
    ImageView mImgUserHomeFriend;

    /**
     * 调用聊天传入userInfo对像
     */
    private UserInfo mUserInfo;

    private View mPopWindowContent;
    private TextView mTvAddAndCancelFriend;
    private TextView mTvAddAndCancelFollow;
    private TextView mTvPullBack;
    private UserHomeAdapter mUserHomeAdapter;
    /**
     * 是否是好友
     */
    private boolean mIsFriend;
    /**
     * 是否关注 true 关注  false 未关注
     */
    private boolean mIsFollowed;

    /**
     * 被查看人公司新秀
     */
    private UserHomePageBean.CompanyInfoBean mCompanyInfoBean;

    /**
     * 启动用户主页页面
     *
     * @param context
     * @param accId   被查看用户的accId
     */
    public static void startActivity(Context context, String accId) {
        Intent intent = new Intent(context, UserHomeActivity.class);
        intent.putExtra(EXTRA_UID, accId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        ButterKnife.bind(this);
        String uid = getIntent().getStringExtra(EXTRA_UID);
        initData(uid);
        initView();
    }

    private void initView() {
        setRightImageResId(R.drawable.icon_right_more);
        mPopWindowContent = LayoutInflater.from(this).inflate(R.layout.layout_pop_user_home_select, null);
        mTvAddAndCancelFriend = mPopWindowContent.findViewById(R.id.tv_addAndCancelFriend);
        mTvAddAndCancelFollow = mPopWindowContent.findViewById(R.id.tv_addAndCancelFollow);
        mTvPullBack = mPopWindowContent.findViewById(R.id.tv_pullBack);
        mRecCareerInfo.setLayoutManager(new LinearLayoutManager(this));
        mUserHomeAdapter = new UserHomeAdapter
                (R.layout.item_user_home_careerinfo);
        mUserHomeAdapter.bindToRecyclerView(mRecCareerInfo);
        mImgAnswer.setFocusable(true);
        mImgAnswer.setFocusableInTouchMode(true);
        mImgAnswer.requestFocus();
        setFollowStatus();
        mImgAnswer.post(() -> {
            int width = mImgAnswer.getWidth();
            int height = (int) (width * 0.4);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
            mImgAnswer.setLayoutParams(params);
        });
        mImgCircle.post(() -> {
            int width = mImgCircle.getWidth();
            int height = (int) (width * 0.4);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
            mImgCircle.setLayoutParams(params);
        });
        DefaultPopWindow popWindow = new DefaultPopWindow(mPopWindowContent);
        popWindow.setOnDismissListener(() -> popWindow.backgroundAlpha(UserHomeActivity.this, 1.0f));
        mTvAddAndCancelFriend.setOnClickListener(v -> {
            pushFriendStatus(mIsFriend);
            popWindow.dismiss();
        });
        mTvAddAndCancelFollow.setOnClickListener(v -> {
            if (mCompanyInfoBean != null) {
                changeFollowStatus(mCompanyInfoBean.getAccId(), mCompanyInfoBean.getUserId(), mCompanyInfoBean.getCompanyId(),
                        mCompanyInfoBean.getTopCompanyId(), mIsFollowed);
            } else {
                showToast("用户信息有误！");
            }
            popWindow.dismiss();
        });
        mTvPullBack.setOnClickListener(v -> {
            ToastUtil.get().showToast(UserHomeActivity.this, "跳转拉黑好友");
            popWindow.dismiss();
        });
        setRightImageOnClickListener(v -> {
            popWindow.showAsDropDown(findViewById(R.id.iv_right));
            popWindow.backgroundAlpha(UserHomeActivity.this, 0.5f);
        });
        setLeftBack();
        mRlUserHomeFriend.setOnClickListener(v -> {
            if (mIsFriend) {
                startChat();
            } else {
                pushFriendStatus(false);
            }
        });

        mRlUserHomeConcern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCompanyInfoBean != null) {
                    changeFollowStatus(mCompanyInfoBean.getAccId(), mCompanyInfoBean.getUserId(), mCompanyInfoBean.getCompanyId(),
                            mCompanyInfoBean.getTopCompanyId(), mIsFollowed);
                } else {
                    showToast("用户信息有误！");
                }
            }
        });

        mImgAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.get().showToast(UserHomeActivity.this, "跳转在线问答");

            }
        });

        mImgCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCompanyInfoBean != null) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isLookOther", true);
                    bundle.putLong("mUserId", Long.parseLong(mCompanyInfoBean.getUserId()));
                    JumpItent.jump(UserHomeActivity.this, SecurityPersonalActivity.class, bundle);
                } else {
                    showToast("用户信息有误！");
                }
            }
        });
    }

    private void initData(String accId) {
        EanfangHttp.post(UserApi.USER_HOME_PAGE)
                .params("accId", accId)
                .execute(new EanfangCallback<UserHomePageBean>(UserHomeActivity.this, true, UserHomePageBean.class, bean -> {
                    if (bean == null) {
                        return;
                    }
                    mIsFriend = bean.getFriendStatus();
                    mIsFollowed = bean.getFollowStatus() == 0;
                    setFriendStatus();
                    setFollowStatus();
                    if (bean.getJobList() != null && bean.getJobList().size() > 0) {
                        mUserHomeAdapter.addData(bean.getJobList());
                        mRecCareerInfo.setVisibility(View.VISIBLE);
                        mImgNoData.setVisibility(View.GONE);
                        mTvNoData.setVisibility(View.GONE);
                    } else {
                        mRecCareerInfo.setVisibility(View.GONE);
                        mImgNoData.setVisibility(View.VISIBLE);
                        mTvNoData.setVisibility(View.VISIBLE);
                    }

                    UserHomePageBean.AccountBean accountBean = bean.getAccount();
                    if (accountBean != null) {
                        mUserInfo = new UserInfo(accountBean.getAccId(),
                                accountBean.getNickName(), Uri.parse(BuildConfig.OSS_SERVER + accountBean.getAvatar()));
                        mImgUserHeader.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + accountBean.getAvatar()));
                        mTvNickname.setText(accountBean.getNickName());
                        mTvNickname.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        if (!StringUtils.isEmpty(accountBean.getRealName())) {
                            mTvUserInfo.setVisibility(View.VISIBLE);
                            String sex = accountBean.getGender() == 0 ? "女" : "男";
                            if (StringUtils.isEmpty(accountBean.getBirthMonthDay())) {
                                mTvUserInfo.setText(
                                        MessageFormat.format("{0} · {1}", accountBean.getRealName(), sex));
                            } else {
                                mTvUserInfo.setText(
                                        MessageFormat.format("{0} · {1} · {2}", accountBean.getRealName(), sex, accountBean.getBirthMonthDay()));
                            }
                        } else {
                            mTvUserInfo.setVisibility(View.INVISIBLE);
                        }
                        if (!StringUtils.isEmpty(accountBean.getAreaInfo())) {
                            mTvPositionLocation.setVisibility(View.VISIBLE);
                            mTvPositionLocation.setText(accountBean.getAreaInfo());
                        } else {
                            mTvPositionLocation.setVisibility(View.INVISIBLE);
                        }
                        String intro = accountBean.getIntro();
                        if (!StringUtils.isEmpty(intro)) {
                            mTvIntro.setText(accountBean.getIntro().length() > 18
                                    ? accountBean.getIntro().substring(0, 16) + "..." :
                                    accountBean.getIntro());
                        } else {
                            mTvIntro.setText(getString(R.string.text_user_home_intro_default));
                        }

                    }
                    mCompanyInfoBean = bean.getCompanyInfo();
                    Log.e("UserHomePageBean:", bean.toString());
                }));
    }

    /**
     * 设置好友状态
     */
    private void setFriendStatus() {
        if (mIsFriend) {
            mTvAddAndCancelFriend.setText("删除好友");
            mTvUserHomeFriend.setText("聊天");
            mImgUserHomeFriend.setVisibility(View.GONE);
        } else {
            mTvAddAndCancelFriend.setText("添加好友");
            mTvUserHomeFriend.setText("加好友");
            mImgUserHomeFriend.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置关注状态
     */
    private void setFollowStatus() {
        if (mIsFollowed) {
            mTvAddAndCancelFollow.setText("取消关注");
            mTvUserHomeConcern.setText("已关注");
            mTvUserHomeConcern.setTextColor(getResources().getColor(R.color.color_user_home_add_and_concern));
            mRlUserHomeConcern.setSelected(false);
            mRlUserHomeConcern.setClickable(false);
            mImgUserHomeConcern.setVisibility(View.GONE);
        } else {
            mTvAddAndCancelFollow.setText("添加关注");
            mTvUserHomeConcern.setText("关注");
            mTvUserHomeConcern.setTextColor(Color.WHITE);
            mRlUserHomeConcern.setSelected(true);
            mRlUserHomeConcern.setClickable(true);
            mImgUserHomeConcern.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 发送添加、删除好友请求
     *
     * @param doDelete true：删除好友 false：添加好友
     */
    private void pushFriendStatus(boolean doDelete) {
        if (mUserInfo != null) {
            EanfangHttp.post(doDelete ? UserApi.POST_DELETE_FRIEND_PUSH : UserApi.POST_ADD_FRIEND_PUSH)
                    .params("senderId", EanfangApplication.get().getAccId())
                    .params("targetIds", mUserInfo.getUserId())
                    .execute(new EanfangCallback<org.json.JSONObject>(this, true, org.json.JSONObject.class, (json) -> {
                        ToastUtil.get().showToast(this, doDelete ? "删除成功" : "发送成功");
                        setFriendStatus();
                    }));
        }
    }

    /**
     * 开始聊天
     */
    private void startChat() {
        RongIM.getInstance().refreshUserInfoCache(mUserInfo);
        RongIM.getInstance().startConversation(this, Conversation.ConversationType.PRIVATE,
                mUserInfo.getUserId(), mUserInfo.getName());
    }

    /**
     * 改变用户关注状态
     *
     * @param asAccId        被关注人accId
     * @param asUserId       被关注人id
     * @param asCompanyId    被关注人公司id
     * @param asTopCompanyId 被关注人总公司id
     * @param isFollowed     true：已关注  false：未关注
     */
    private void changeFollowStatus(String asAccId, String asUserId, String asCompanyId,
                                    String asTopCompanyId, boolean isFollowed) {
        Log.d("UserHomeActivity", "changeFollowStatus: asAccId :" + " asUserId:"
                + asUserId + "  asCompanyId:" + asCompanyId
                + "  asTopCompanyId:" + asTopCompanyId + "  isFollow:" + isFollowed);
        EanfangHttp.post(UserApi.POST_CHANGE_FOLLOW_STATUS)
                .params("asAccId", asAccId)
                .params("asUserId", asUserId)
                .params("asCompanyId", asCompanyId)
                .params("asTopCompanyId", asTopCompanyId)
                .params("followStatus", String.valueOf(isFollowed ? 0 : 1))
                .execute(new EanfangCallback(this, true, JSONObject.class, bean -> {
                    Log.d("UserHomeActivity", "changeFollowStatus: 关注状态上传成功");
                    showToast(isFollowed ? "已取消关注" : "关注成功");
                    mIsFollowed = !isFollowed;
                    setFollowStatus();
                }));
    }
}
