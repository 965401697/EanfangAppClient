package com.eanfang.ui.adapter;

import android.app.Activity;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.R;
import com.eanfang.model.InviteDetailBean;

/**
 * @author liangkailun
 * Date ：2019/5/13
 * Describe :
 */
public class InviteDetailArrayAdapter extends BaseQuickAdapter<InviteDetailBean.ListBean, InviteDetailViewHolder> {

    private int mState;
    private Activity mActivity;

    public InviteDetailArrayAdapter(int state, Activity activity) {
        super(R.layout.item_invite_detail);
        this.mState = state;
        this.mActivity = activity;
    }

    @Override
    protected void convert(InviteDetailViewHolder helper, InviteDetailBean.ListBean item) {
        if (item == null) {
            return;
        }
        String phoneNum = item.getAccountPhone();
        String maskNumber = "";
        if (phoneNum != null && phoneNum.length() == 11) {
            maskNumber = phoneNum.substring(0, 3) + "*******" + phoneNum.substring(9);
        }
        if (mState == 0) {
            helper.phone.setText(mActivity.getString(R.string.text_invite_person, maskNumber));
            helper.money.setVisibility(View.GONE);
            helper.money.setText(mActivity.getString(R.string.text_invite_money_enter, item.getWithdrawalMoney() / 100));
            helper.money.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
        } else if (mState == 1) {
            helper.money.setVisibility(View.VISIBLE);
            helper.phone.setText(mActivity.getString(R.string.text_withdraw_person, maskNumber));
            helper.money.setText(mActivity.getString(R.string.text_invite_money_withdraw, item.getWithdrawalMoney() / 100));
            helper.money.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
        } else {
            helper.money.setVisibility(View.VISIBLE);
            helper.phone.setText(mActivity.getString(R.string.text_invite_person, maskNumber));
            helper.money.setText(mActivity.getString(R.string.text_invite_money_lose, item.getWithdrawalMoney() / 100));
            helper.money.setTextColor(mActivity.getResources().getColor(R.color.color_bottom));
            helper.phone.setTextColor(mActivity.getResources().getColor(R.color.color_bottom));
        }

        helper.time.setText(item.getEditTime());

    }
}
