package net.eanfang.worker.ui.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.NoticeEntity;
import com.eanfang.util.GetDateUtils;

import net.eanfang.worker.R;

import java.util.List;


/**
 * 消息的adapter
 * Created by Administrator on 2017/3/15.
 */

public class MessageListAdapter extends BaseQuickAdapter<NoticeEntity, BaseViewHolder> {
    public MessageListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, NoticeEntity item) {

        helper.setText(R.id.tv_titles, item.getTitle());
        helper.setText(R.id.tv_content, item.getContent());
        helper.setText(R.id.tv_detailTime, GetDateUtils.dateToDateTimeString(item.getCreateTime()));

        // 0 未读  1 已读
        if (item.getStatus() == 0) {
            helper.setTextColor(R.id.tv_titles, Color.parseColor("#333333"));
            helper.setTextColor(R.id.tv_content, Color.parseColor("#333333"));
            helper.setTextColor(R.id.tv_leftBrackets, Color.parseColor("#333333"));
            helper.setTextColor(R.id.tv_rightBrackets, Color.parseColor("#333333"));
            helper.setVisible(R.id.iv_header, true);
            helper.setTextColor(R.id.tv_detailTime, Color.parseColor("#333333"));

        } else if (item.getStatus() == 1) {
            helper.setTextColor(R.id.tv_titles, Color.parseColor("#999999"));
            helper.setTextColor(R.id.tv_content, Color.parseColor("#999999"));
            helper.setTextColor(R.id.tv_leftBrackets, Color.parseColor("#999999"));
            helper.setTextColor(R.id.tv_rightBrackets, Color.parseColor("#999999"));
            helper.setTextColor(R.id.tv_detailTime, Color.parseColor("#999999"));
            helper.setVisible(R.id.iv_header, false);
        }

    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            holder.setTextColor(R.id.tv_titles, Color.parseColor("#999999"));
            holder.setTextColor(R.id.tv_content, Color.parseColor("#999999"));
            holder.setTextColor(R.id.tv_leftBrackets, Color.parseColor("#999999"));
            holder.setTextColor(R.id.tv_rightBrackets, Color.parseColor("#999999"));
            holder.setTextColor(R.id.tv_detailTime, Color.parseColor("#999999"));
            holder.setVisible(R.id.iv_header, false);
        }
    }
}
