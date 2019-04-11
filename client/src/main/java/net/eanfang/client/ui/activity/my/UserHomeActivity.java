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
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.UserHomePageBean;
import com.eanfang.util.ToastUtil;
import com.eanfang.witget.DefaultPopWindow;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;
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
    private static final String EXTRA_UID = "UserHomeActivity.uid";
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
    @BindView(R.id.btn_concern)
    Button mBtnConcern;
    @BindView(R.id.btn_add)
    Button mBtnAdd;
    @BindView(R.id.tv_position_location)
    TextView mTvPositionLocation;
    @BindView(R.id.tv_intro)
    TextView mTvIntro;
    @BindView(R.id.img_noData)
    ImageView mImgNoData;
    @BindView(R.id.tv_noData)
    TextView mTvNoData;

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
     * @param uid     被查看用户的uId
     */
    public static void startActivity(Context context, String uid) {
        Intent intent = new Intent(context, UserHomeActivity.class);
        intent.putExtra(EXTRA_UID, uid);
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
        DefaultPopWindow popWindow = new DefaultPopWindow(mPopWindowContent);
        popWindow.setOnDismissListener(() -> popWindow.backgroundAlpha(UserHomeActivity.this, 1.0f));
        mTvAddAndCancelFriend.setOnClickListener(v -> {
            pushFriendStatus(mIsFriend);
            popWindow.dismiss();
        });
        mTvAddAndCancelFollow.setOnClickListener(v -> {
            if (mCompanyInfoBean != null) {
                changeFollowStatus(mCompanyInfoBean.getUserId(), mCompanyInfoBean.getCompanyId(),
                        mCompanyInfoBean.getTopCompanyId(),mIsFollowed);
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
        mBtnAdd.setOnClickListener(v -> {
            if (mIsFriend) {
                startChat();
            } else {
                pushFriendStatus(false);
            }
        });

        mBtnConcern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCompanyInfoBean != null) {
                    changeFollowStatus(mCompanyInfoBean.getUserId(), mCompanyInfoBean.getCompanyId(),
                            mCompanyInfoBean.getTopCompanyId(),mIsFollowed);
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
                ToastUtil.get().showToast(UserHomeActivity.this, "跳转圈子动态");

            }
        });
    }

    private void initData(String uid) {
        EanfangHttp.post(UserApi.USER_HOME_PAGE)
                .params("userId", uid)
                .execute(new EanfangCallback<UserHomePageBean>(UserHomeActivity.this, true, UserHomePageBean.class, bean -> {
                    if (bean == null) {
                        return;
                    }
                    mIsFriend = bean.getFriendStatus();
                    mIsFollowed = bean.getFollowStatus() == 1;
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
                        String sex = accountBean.getGender() == 0 ? "女" : "男";
                        mTvUserInfo.setText(
                                MessageFormat.format("{0} · {1} · {2}", accountBean.getRealName(), sex, accountBean.getBirthMonthDay()));
                        mTvPositionLocation.setText(accountBean.getAreaInfo());
                        mTvIntro.setText(accountBean.getIntro().length() > 18
                                ? accountBean.getIntro().substring(0, 16) + "..." :
                                accountBean.getIntro());
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
            mBtnAdd.setText("聊天");
        } else {
            mTvAddAndCancelFriend.setText("添加好友");
            mBtnAdd.setText("+ 好友");
        }
    }

    /**
     * 设置关注状态
     */
    private void setFollowStatus() {
        if (mIsFollowed) {
            mTvAddAndCancelFollow.setText("取消关注");
            mBtnConcern.setText("已关注");
            mBtnConcern.setTextColor(getResources().getColor(R.color.colorPrimary));
            mBtnConcern.setSelected(false);
            mBtnConcern.setClickable(false);
        } else {
            mTvAddAndCancelFollow.setText("添加关注");
            mBtnConcern.setText("+ 关注");
            mBtnConcern.setTextColor(Color.WHITE);
            mBtnConcern.setSelected(true);
        }
    }

    /**
     * 发送添加、删除好友请求
     *
     * @param doDelete true：删除好友 false：添加好友
     */
    private void pushFriendStatus(boolean doDelete) {
        EanfangHttp.post(doDelete ? UserApi.POST_DELETE_FRIEND_PUSH : UserApi.POST_ADD_FRIEND_PUSH)
                .params("senderId", EanfangApplication.get().getAccId())
                .params("targetIds", mUserInfo.getUserId())
                .execute(new EanfangCallback<org.json.JSONObject>(this, true, org.json.JSONObject.class, (json) -> {
                    ToastUtil.get().showToast(this, doDelete ? "删除成功" : "发送成功");
                    setFriendStatus();
                }));
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
     * @param asUserId       被关注人id
     * @param asCompanyId    被关注人公司id
     * @param asTopCompanyId 被关注人总公司id
     * @param isFollowed     false：关注  true：取消关注
     */
    private void changeFollowStatus(String asUserId, String asCompanyId,
                                    String asTopCompanyId, boolean isFollowed) {
        Log.e("UserHomeActivity", "changeFollowStatus: asUserId:" + asUserId + "  asCompanyId:" + asCompanyId
                + "  asTopCompanyId:" + asTopCompanyId + "  isFollow:" + isFollowed);
        EanfangHttp.post(UserApi.POST_CHANGE_FOLLOW_STATUS)
                .params("asUserId", asUserId)
                .params("asCompanyId", asCompanyId)
                .params("asTopCompanyId", asTopCompanyId)
                .params("followsStatus", String.valueOf(isFollowed ? 1 : 0))
                .execute(new EanfangCallback(this, true, JSONObject.class, bean -> {
                    Log.d("UserHomeActivity", "changeFollowStatus: 关注状态上传成功");
                    showToast(isFollowed ? "已取消关注" : "关注成功");
                    mIsFollowed = !isFollowed;
                    setFollowStatus();
                }));
    }
}
