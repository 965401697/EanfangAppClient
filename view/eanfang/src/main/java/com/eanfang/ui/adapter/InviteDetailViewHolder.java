package com.eanfang.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.R;

/**
 * @author liangkailun
 * Date ：2019-05-25
 * Describe :
 */
public class InviteDetailViewHolder extends BaseViewHolder {
    public TextView phone;
    public TextView money;
    public TextView time;
    public InviteDetailViewHolder(View view) {
        super(view);
        phone = convertView.findViewById(R.id.tv_item_invite_phone);
        money = convertView.findViewById(R.id.tv_item_invite_money);
        time = convertView.findViewById(R.id.tv_item_invite_time);
    }
}
