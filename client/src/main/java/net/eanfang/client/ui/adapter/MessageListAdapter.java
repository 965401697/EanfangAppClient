package net.eanfang.client.ui.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.NoticeEntity;
import com.eanfang.util.GetConstDataUtils;

import net.eanfang.client.R;

import java.util.List;


/**
 * 消息的adapter
 * Created by Administrator on 2017/3/15.
 */

public class MessageListAdapter extends BaseQuickAdapter<NoticeEntity, BaseViewHolder> {
    public MessageListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NoticeEntity item) {

        helper.setText(R.id.tv_titles, GetConstDataUtils.getNoticeTypeList().get(item.getNoticeType()));
        helper.setText(R.id.tv_content, item.getContent());
        if (item.getStatus() == 0) {
            helper.setTextColor(R.id.tv_titles, Color.parseColor("#333333"));
            helper.setTextColor(R.id.tv_content, Color.parseColor("#333333"));
            helper.setTextColor(R.id.tv_leftBrackets, Color.parseColor("#333333"));
            helper.setTextColor(R.id.tv_rightBrackets, Color.parseColor("#333333"));
            helper.setVisible(R.id.iv_header, true);
        } else if (item.getStatus() == 1) {
            helper.setTextColor(R.id.tv_titles, Color.parseColor("#999999"));
            helper.setTextColor(R.id.tv_content,Color.parseColor("#999999"));
            helper.setTextColor(R.id.tv_leftBrackets, Color.parseColor("#999999"));
            helper.setTextColor(R.id.tv_rightBrackets, Color.parseColor("#999999"));
            helper.setVisible(R.id.iv_header, false);
        }

    }
}