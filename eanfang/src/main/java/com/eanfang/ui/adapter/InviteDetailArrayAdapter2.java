package com.eanfang.ui.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.eanfang.R;
import com.eanfang.model.InviteDetailBean;

import java.util.List;

/**
 * @author liangkailun
 * Date ：2019/5/13
 * Describe :
 */
public class InviteDetailArrayAdapter2 extends ArrayAdapter<InviteDetailBean.ListBean> {

    private Activity mActivity;
    private List<InviteDetailBean.ListBean> mDetailBeans;
    private int mState;

    public InviteDetailArrayAdapter2(@NonNull Activity activity, @NonNull List<InviteDetailBean.ListBean> objects, int state) {
        super(activity, 0, objects);
        mActivity = activity;
        mDetailBeans = objects;
        mState = state;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = mActivity.getLayoutInflater().inflate(R.layout.item_invite_detail, null);
        }
        TextView phone = convertView.findViewById(R.id.tv_item_invite_phone);
        TextView money = convertView.findViewById(R.id.tv_item_invite_money);
        TextView time = convertView.findViewById(R.id.tv_item_invite_time);
        phone.setText(mDetailBeans.get(position).getAccountPhone());
        time.setText(mDetailBeans.get(position).getEditTime());
        money.setText(String.valueOf(mDetailBeans.get(position).getWithdrawalMoney() / 100));
        return convertView;
    }
}
