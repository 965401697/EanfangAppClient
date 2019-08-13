package net.eanfang.worker.ui.adapter.security;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.bean.security.SecurityCommentListBean;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;

import cn.hutool.core.util.StrUtil;

/**
 * @author guanluocang
 * @data 2019/3/25
 * @description 安防圈评论
 */

public class SecurityCommentListAdapter extends BaseQuickAdapter<SecurityCommentListBean.ListBean, BaseViewHolder> {

    private boolean mIsUnRead = false;

    public SecurityCommentListAdapter(boolean isUnRead) {
        super(R.layout.layout_security_comment_list_item);
        this.mIsUnRead = isUnRead;
    }

    @Override
    protected void convert(BaseViewHolder helper, SecurityCommentListBean.ListBean item) {
        if (item.getAccountEntity().getAccId().equals(WorkerApplication.get().getAccId())) {
            helper.setText(R.id.tv_comment, "我：" + item.getCommentsContent());
        } else {
            helper.setText(R.id.tv_comment, item.getAccountEntity().getRealName() + "：" + item.getCommentsContent());
        }
        if (StrUtil.isEmpty(item.getAskSpCircleEntity().getSpcContent())) {
            helper.setText(R.id.tv_content, "[图片]");
        } else {
            helper.setText(R.id.tv_content, item.getAskSpCircleEntity().getSpcContent());
        }
        if (item.getReadStatus() == 0 && mIsUnRead) {
            helper.setVisible(R.id.tv_unread, true);
        } else {
            helper.setVisible(R.id.tv_unread, false);
        }
        helper.setText(R.id.tv_time, item.getCreateTime());

    }
}
