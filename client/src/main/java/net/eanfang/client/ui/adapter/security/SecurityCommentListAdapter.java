package net.eanfang.client.ui.adapter.security;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.application.EanfangApplication;
import com.eanfang.model.security.SecurityCommentListBean;
import com.eanfang.util.StringUtils;

import net.eanfang.client.R;


/**
 * @author guanluocang
 * @data 2019/3/25
 * @description 安防圈评论
 */

public class SecurityCommentListAdapter extends BaseQuickAdapter<SecurityCommentListBean.ListBean, BaseViewHolder> {
    public SecurityCommentListAdapter() {
        super(R.layout.layout_security_comment_list_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, SecurityCommentListBean.ListBean item) {
        if (item.getAccountEntity().getAccId().equals(EanfangApplication.get().getAccId())) {
            helper.setText(R.id.tv_comment, "我：" + item.getCommentsEntity().getCommentsContent());
        } else {
            helper.setText(R.id.tv_comment, item.getAccountEntity().getNickName() + "：" + item.getCommentsEntity().getCommentsContent());
        }
        if (StringUtils.isEmpty(item.getSpcContent())) {
            helper.setText(R.id.tv_content, "[图片]");
        } else {
            helper.setText(R.id.tv_content, item.getSpcContent());
        }
        helper.setText(R.id.tv_time, item.getCreateTime());
    }
}
