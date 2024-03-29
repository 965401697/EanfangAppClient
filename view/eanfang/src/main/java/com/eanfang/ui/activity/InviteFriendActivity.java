package com.eanfang.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.alibaba.fastjson.JSON;
import com.eanfang.R;
import com.eanfang.R2;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.base.BaseApplication;
import com.eanfang.dialog.ActiveRuleDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.InviteHistoryBean;
import com.eanfang.biz.model.bean.RewardPerson;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.biz.model.QueryEntry;
import com.eanfang.witget.RollTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hutool.core.util.StrUtil;


/**
 * @author liangkailun
 * Date ：2019/5/13
 * Describe :邀请好友活动页
 */
public class InviteFriendActivity extends BaseActivity {
    private static final String TAG = "InviteFriendActivity";
    @BindView(R2.id.roll_text_invite_friend)
    RollTextView mRollTextInviteFriend;
    @BindView(R2.id.tv_invite_person)
    TextView mTvInvitePerson;
    @BindView(R2.id.tv_invite_getMoney)
    TextView mTvInviteGetMoney;
    @BindView(R2.id.tv_invite_lossMoney)
    TextView mTvInviteLossMoney;
    @BindView(R2.id.btn_could_extract)
    Button mBtnCouldExtract;
    @BindView(R2.id.btn_do_extract)
    Button mBtnDoExtract;
    @BindView(R2.id.btn_to_invite)
    Button mBtnToInvite;
    @BindView(R2.id.tv_activity_description)
    TextView mTvActivityDescription;
    @BindView(R2.id.cl_military_exploits)
    ConstraintLayout mClMilitaryExploits;
    @BindView(R2.id.constraint_all_invite)
    ConstraintLayout constraintAllInvite;

    private int mExtractMoney = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_invite_friend);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setLeftBack();
        setTitle("邀请好友赚现金");
        initView();
        initAllPerson();
        initInviteDetail();
    }

    private void initView() {
        Intent intent = new Intent(InviteFriendActivity.this, InviteDetailActivity.class);
        mClMilitaryExploits.setOnClickListener(v -> {
            intent.putExtra(InviteDetailActivity.EXTRA_STATUS, 0);
            startActivity(intent);
        });
        mBtnDoExtract.setOnClickListener(v -> {
            startActivity(new Intent(this, CashOurActivity.class).putExtra(CashOurActivity.EXTRA_MONEY, mExtractMoney));
        });
        mBtnToInvite.setOnClickListener(v -> {
            showShareDialog();
        });
        mTvActivityDescription.setOnClickListener(v -> {
            activityDialogShow();
        });

    }

    /**
     * 显示分享页面
     */
    private void showShareDialog() {
        startActivity(new Intent(this, InviteShareActivity.class));

    }

    /**
     * 活动弹窗显示
     */
    private void activityDialogShow() {
        ActiveRuleDialog dialog = new ActiveRuleDialog(this);
        dialog.show();
    }

    private void initAllPerson() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.setSize(50);
        EanfangHttp.post(NewApiService.ALL_GET_REWARD_PERSON)
                .upJson(JSON.toJSONString(queryEntry))
                .execute(new EanfangCallback<RewardPerson>(this, true, RewardPerson.class, bean -> {
                    if (bean == null || bean.getList() == null) {
                        return;
                    }
                    initRollTextView(bean);
                }));
    }

    private void initInviteDetail() {
        EanfangHttp.post(NewApiService.ALL_GET_REWARD_PERSON2)
                .params("accId", BaseApplication.get().getAccId())
                .execute(new EanfangCallback<InviteHistoryBean>(this, true, InviteHistoryBean.class, this::setInviteInfo));
    }

    /**
     * 设置UI数据
     *
     * @param bean
     */
    private void setInviteInfo(InviteHistoryBean bean) {
        mExtractMoney = bean.getWithdrawalsAmount();
        mBtnCouldExtract.setText(getString(R.string.text_invite_extra_money, mExtractMoney));
        String person = bean.getCount() + "人";
        mTvInvitePerson.setText(setStringSpan(person, mTvInvitePerson));
        String lossMoney = bean.getInvalidAmount() + "元";
        String getMoney = bean.getWithdrawalsScAmount() + "元";
        mTvInviteLossMoney.setText(setStringSpan(lossMoney, mTvInviteLossMoney));
        mTvInviteGetMoney.setText(setStringSpan(getMoney, mTvInviteGetMoney));
    }

    /**
     * 格式化string
     *
     * @param text
     * @param textView
     * @return
     */
    private SpannableString setStringSpan(String text, TextView textView) {
        SpannableString spanString = new SpannableString(text);
        AbsoluteSizeSpan span = new AbsoluteSizeSpan(30);
        spanString.setSpan(span, text.length() - 1, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        TextPaint paint = textView.getPaint();
        paint.setFakeBoldText(true);
        return spanString;
    }

    /**
     * 初始化rolltext显示的文本
     */
    private void initRollTextView(RewardPerson rewardPerson) {
        List<View> views = new ArrayList<>();
        constraintAllInvite.setVisibility(View.VISIBLE);
        for (int i = 0; i < rewardPerson.getList().size(); i++) {
            View view = View.inflate(this, R.layout.item_invite_friend_scroll, null);
            TextView title = view.findViewById(R.id.tv_invite_friend_scroll);
            String realName = rewardPerson.getList().get(i).getRealName();
            if (!StrUtil.isEmpty(realName)) {
                if (realName.length() > 2) {
                    title.setText(getResources().getString(R.string.text_invite_all_reward_person,
                            realName.substring(0, 1) + "*" + realName.substring(2), rewardPerson.getList().get(i).getWithdrawalMoney()));
                } else {
                    title.setText(getResources().getString(R.string.text_invite_all_reward_person,
                            realName.substring(0, 1) + "*", rewardPerson.getList().get(i).getWithdrawalMoney()));
                }
                title.setTextColor(getResources().getColor(R.color.white));
                views.add(view);
            }
        }
        mRollTextInviteFriend.setViews(views);
    }
}
