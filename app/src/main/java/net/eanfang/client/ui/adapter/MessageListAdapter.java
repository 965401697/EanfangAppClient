package net.eanfang.client.ui.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;
import net.eanfang.client.ui.model.MessageListBean;

import java.util.List;


/**
 * 消息的adapter
 * Created by Administrator on 2017/3/15.
 */

public class MessageListAdapter extends BaseQuickAdapter<MessageListBean.ListBean, BaseViewHolder> {
    public MessageListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageListBean.ListBean item) {

        helper.setText(R.id.tv_titles, item.getTitle());
        helper.setText(R.id.tv_content, item.getContentInfo());
        if (item.getStatus() == 0) {
            helper.setText(R.id.tv_status, "未读");
        } else if (item.getStatus() == 1) {
            helper.setText(R.id.tv_status, "已读");
            helper.setTextColor(R.id.tv_status, Color.parseColor("#0000ff"));
        }

    }
}
