package net.eanfang.worker.ui.adapter.security;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.model.security.SecurityDetailBean;
import com.eanfang.util.V;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;

/**
 * @author guanluocang
 * @data 2019/2/27
 * @description 安防圈评论
 */

public class SecurityCommentAdapter extends BaseQuickAdapter<SecurityDetailBean.ListBean, BaseViewHolder> {
    public SecurityCommentAdapter() {
        super(R.layout.layout_item_security_comment);
    }

    @Override
    protected void convert(BaseViewHolder helper, SecurityDetailBean.ListBean item) {

        // 头像
        SimpleDraweeView ivHeader = helper.getView(R.id.iv_seucrity_header);
        ivHeader.setImageURI((Uri.parse(BuildConfig.OSS_SERVER + V.v(() -> item.getCommentUser().getAccountEntity().getAvatar()))));
        // 评论人
        helper.setText(R.id.tv_name, V.v(() -> item.getCommentUser().getAccountEntity().getNickName()));
        // 公司名称
        helper.setText(R.id.tv_company, item.getCommentOrg().getOrgName());
        //发布的内容
        helper.setText(R.id.tv_content, item.getCommentsContent());
        //时间
        helper.setText(R.id.tv_time, item.getCreateTime());

    }
}
