package net.eanfang.client.ui.activity.my;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.BaseActivity;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.biz.model.bean.UserHomePageBean;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.JumpItent;
import com.eanfang.util.ToastUtil;
import com.eanfang.witget.DefaultPopWindow;
import com.lzy.okgo.request.base.Request;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.activity.worksapce.online.ExpertOnlineActivity;
import net.eanfang.client.ui.activity.worksapce.security.SecurityPersonalActivity;
import net.eanfang.client.ui.adapter.UserHomeAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hutool.core.util.StrUtil;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * @author liangkailun
 * Date: 2019/04/03
 * Describe: 用户主页
 */
public class UserHomeActivity extends BaseActivity {
    /**
     * 带返回值请求用户首页code
     */
    public static final int REQUEST_USER_HOME_CODE = 100;
    public static final String EXTRA_ACCID = "UserHomeActivity.accId";
    public static final String EXTRA_UID = "UserHomeActivity.userId";
    public static final String RESULT_FOLLOW_STATE = "UserHomeActivity.followState";
    public static final String RESULT_FRIEND_STATE = "UserHomeActivity.friendState";
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.img_user_header)
    CircleImageView mImgUserHeader;
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
    @BindView(R.id.ll_user_home_concern)
    LinearLayout mLlUserHomeConcern;
    @BindView(R.id.ll_user_home_friend)
    LinearLayout mLlUserHomeFriend;
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
     * 是否是本人
     */
    private boolean mIsSelf;
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
     * accId启动用户主页页面
     *
     * @param activity
     * @param accId    被查看用户的accId
     */
    public static void startActivityForAccId(Activity activity, String accId) {
        Intent intent = new Intent(activity, UserHomeActivity.class);
        intent.putExtra(EXTRA_ACCID, accId);
        activity.startActivityForResult(intent, REQUEST_USER_HOME_CODE);
    }

//    /**
//     * uid启动用户主页页面
//     *
//     * @param activity
//     * @param uid      被查看用户的uid
//     */
//    public static void startActivityForUid(Activity activity, Long uid) {
//        Intent intent = new Intent(activity, UserHomeActivity.class);
//        intent.putExtra(UserHomeActivity.EXTRA_UID, uid);
//        activity.startActivityForResult(intent, REQUEST_USER_HOME_CODE);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_home);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        String accId = getIntent().getStringExtra(EXTRA_ACCID);
        Long userId = getIntent().getLongExtra(EXTRA_UID, 0);
        mIsSelf = (accId != null && accId.equals(String.valueOf(ClientApplication.get().getAccId())))
                || userId.equals(ClientApplication.get().getUserId());
        initData(accId, String.valueOf(userId));
        initView();
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    @Override
    protected void initView() {
        super.initView();
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
        setViewClick();
    }

    /**
     * 设置view点击
     */
    private void setViewClick() {
        DefaultPopWindow popWindow = new DefaultPopWindow(mPopWindowContent);
        popWindow.setOnDismissListener(() -> popWindow.backgroundAlpha(UserHomeActivity.this, 1.0f));
        mTvAddAndCancelFriend.setOnClickListener(v -> {
            if (mUserInfo != null) {
                pushFriendStatus(mIsFriend);
            } else {
                showToast("用户信息有误！");
            }
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
        setRightClick(R.drawable.icon_right_more, v -> {
            popWindow.showAsDropDown(findViewById(R.id.iv_right));
            popWindow.backgroundAlpha(UserHomeActivity.this, 0.5f);
        });
        mLlUserHomeFriend.setOnClickListener(v -> {
            if (mUserInfo != null) {
                if (mIsFriend) {
                    startChat();
                } else {
                    pushFriendStatus(false);
                }
            } else {
                showToast("用户信息有误！");
            }
        });

        mLlUserHomeConcern.setOnClickListener(v -> {
            if (mCompanyInfoBean != null) {
                changeFollowStatus(mCompanyInfoBean.getAccId(), mCompanyInfoBean.getUserId(), mCompanyInfoBean.getCompanyId(),
                        mCompanyInfoBean.getTopCompanyId(), mIsFollowed);
            } else {
                showToast("用户信息有误！");
            }
        });

        mImgAnswer.setOnClickListener(v -> startActivity(new Intent(UserHomeActivity.this, ExpertOnlineActivity.class)));

        mImgCircle.setOnClickListener(v -> {
            if (mCompanyInfoBean != null) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isLookOther", !mIsSelf);
                bundle.putLong("mAccId", Long.parseLong(mCompanyInfoBean.getAccId()));
                bundle.putLong("mUserId", Long.parseLong(mCompanyInfoBean.getUserId()));
                JumpItent.jump(UserHomeActivity.this, SecurityPersonalActivity.class, bundle);
            } else {
                showToast("用户信息有误！");
            }
        });
    }

    private void initData(String accId, String userId) {
        EanfangHttp.post(UserApi.USER_HOME_PAGE)
                .params(!StrUtil.isEmpty(accId) ? "accId" : "userId", !StrUtil.isEmpty(accId) ? accId : userId)
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
                        GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + accountBean.getAvatar()),mImgUserHeader);
                        mTvNickname.setText(accountBean.getNickName());
                        mTvNickname.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        if (!StrUtil.isEmpty(accountBean.getRealName())) {
                            mTvUserInfo.setVisibility(View.VISIBLE);
                            String sex = accountBean.getGender() == 0 ? "女" : "男";
                            if (StrUtil.isEmpty(accountBean.getBirthMonthDay())) {
                                mTvUserInfo.setText(
                                        MessageFormat.format("{0} · {1}", accountBean.getRealName(), sex));
                            } else {
                                mTvUserInfo.setText(
                                        MessageFormat.format("{0} · {1} · {2}", accountBean.getRealName(), sex, accountBean.getBirthMonthDay()));
                            }
                        } else {
                            mTvUserInfo.setVisibility(View.INVISIBLE);
                        }
                        if (!StrUtil.isEmpty(accountBean.getAreaInfo())) {
                            mTvPositionLocation.setVisibility(View.VISIBLE);
                            mTvPositionLocation.setText(accountBean.getAreaInfo());
                        } else {
                            mTvPositionLocation.setVisibility(View.INVISIBLE);
                        }
                        String intro = accountBean.getPersonalNote();
                        if (!StrUtil.isEmpty(intro)) {
                            mTvIntro.setText(intro.length() > 18 ? intro.substring(0, 16) + "..." : intro);
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
            mLlUserHomeConcern.setSelected(false);
            mLlUserHomeConcern.setClickable(false);
            mImgUserHomeConcern.setVisibility(View.GONE);
        } else {
            mTvAddAndCancelFollow.setText("添加关注");
            mTvUserHomeConcern.setText("关注");
            mTvUserHomeConcern.setTextColor(Color.WHITE);
            mLlUserHomeConcern.setSelected(true);
            mLlUserHomeConcern.setClickable(true);
            mImgUserHomeConcern.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 发送添加、删除好友请求
     *
     * @param doDelete true：删除好友 false：添加好友
     */
    private void pushFriendStatus(boolean doDelete) {
        if (!mIsSelf) {
            Request request;
            if (doDelete) {
                request = EanfangHttp.post(UserApi.POST_DELETE_FRIEND).params("ids", mUserInfo.getUserId());
            } else {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("accId", mUserInfo.getUserId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                request = EanfangHttp.post(UserApi.POST_ADD_FRIEND).upJson(jsonObject);
            }
            request.execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                ToastUtil.get().showToast(this, doDelete ? "删除成功" : "发送成功");
                mIsFriend = false;
                setFriendStatus();
                Intent intent = new Intent();
                intent.putExtra(RESULT_FRIEND_STATE, mIsFriend);
                setResult(Activity.RESULT_OK, intent);
            }));
            EanfangHttp.post(doDelete ? UserApi.POST_DELETE_FRIEND_PUSH : UserApi.POST_ADD_FRIEND_PUSH)
                    .params("senderId", ClientApplication.get().getAccId())
                    .params("targetIds", mUserInfo.getUserId())
                    .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (json) -> {
                    }));
        } else {
            showSelfHint();
        }
    }

    /**
     * 开始聊天
     */
    private void startChat() {
        if (!mIsSelf) {
            RongIM.getInstance().refreshUserInfoCache(mUserInfo);
            RongIM.getInstance().startConversation(this, Conversation.ConversationType.PRIVATE,
                    mUserInfo.getUserId(), mUserInfo.getName());
        } else {
            showSelfHint();
        }
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
        if (!mIsSelf) {
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
                        Intent intent = new Intent();
                        intent.putExtra(RESULT_FOLLOW_STATE, mIsFollowed);
                        setResult(Activity.RESULT_OK, intent);
                    }));
        } else {
            showSelfHint();
        }
    }

    /**
     * 自己的个人主页点击按钮提示
     */
    private void showSelfHint() {
        showToast("不能对自己进行操作");
    }
}
