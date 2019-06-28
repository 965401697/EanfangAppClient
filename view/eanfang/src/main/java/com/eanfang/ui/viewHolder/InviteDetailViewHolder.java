package com.eanfang.ui.viewHolder;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.R;

import lombok.Getter;

/**
 * @author liangkailun
 * Date ：2019-05-25
 * Describe :
 */
@Getter
public class InviteDetailViewHolder extends BaseViewHolder {
    private TextView phone;
    private TextView money;
    private TextView time;
    public InviteDetailViewHolder(View view) {
        super(view);
        phone = convertView.findViewById(R.id.tv_item_invite_phone);
        money = convertView.findViewById(R.id.tv_item_invite_money);
        time = convertView.findViewById(R.id.tv_item_invite_time);
    }
}
