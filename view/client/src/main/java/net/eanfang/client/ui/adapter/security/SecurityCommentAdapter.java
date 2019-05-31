package net.eanfang.client.ui.adapter.security;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.security.SecurityDetailBean;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.V;

import net.eanfang.client.R;


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
        GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + V.v(() -> item.getCommentUser().getAccountEntity().getAvatar())), helper.getView(R.id.iv_seucrity_header));
        // 评论人
        helper.setText(R.id.tv_name, V.v(() -> item.getCommentUser().getAccountEntity().getRealName()));
        // 公司名称
        helper.setText(R.id.tv_company, item.getCommentOrg().getOrgName());
        //发布的内容
        helper.setText(R.id.tv_content, item.getCommentsContent());
        //时间
        helper.setText(R.id.tv_time, item.getCreateTime());
        // 是否是好友
        /**
         * 是否是好友 2 好友 1 不是好友
         * */
        if (item.getFriend() == 2) {
            helper.setVisible(R.id.tv_friend, true);
        } else {
            helper.setVisible(R.id.tv_friend, false);
        }
        // 是否认证
        /**
         * 是否认证
         * */
        if (item.getVerifyStatus() == 1) {
            helper.setVisible(R.id.iv_certifi, true);
        } else {
            helper.setVisible(R.id.iv_certifi, false);
        }
        helper.addOnClickListener(R.id.iv_seucrity_header);
        helper.addOnClickListener(R.id.tv_name);
    }
}
