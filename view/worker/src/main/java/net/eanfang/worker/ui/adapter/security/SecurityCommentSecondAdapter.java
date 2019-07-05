package net.eanfang.worker.ui.adapter.security;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.model.security.SecurityCommentDetailBean;
import com.eanfang.util.GlideUtil;
import com.eanfang.base.kit.V;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;

/**
 * @author guanluocang
 * @data 2019/5/22
 * @description 安防圈评论详情列表
 */

public class SecurityCommentSecondAdapter extends BaseQuickAdapter<SecurityCommentDetailBean.ListBean, BaseViewHolder> {
    public SecurityCommentSecondAdapter() {
        super(R.layout.layout_security_second_comment);
    }

    @Override
    protected void convert(BaseViewHolder helper, SecurityCommentDetailBean.ListBean item) {
        // 头像
        GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + V.v(() ->
                item.getCommentUser().getAccountEntity().getAvatar())),helper.getView(R.id.iv_seucrity_header));
        // 评论人
        helper.setText(R.id.tv_name, V.v(() -> item.getCommentUser().getAccountEntity().getRealName()));
        // 公司名称
        helper.setText(R.id.tv_company, item.getCommentOrg().getOrgName());
        //发布的内容
        // 当前评论的ParentCommentsId 和回复人的
        if (item.getParentCommentsEntity() != null) {
            helper.setText(R.id.tv_content, item.getCommentsContent() + " // 回复了" +
                    item.getParentCommentsEntity().getCommentUser().getAccountEntity().getRealName() + "：" +
                    item.getParentCommentsEntity().getCommentsContent());
        } else {
            helper.setText(R.id.tv_content, item.getCommentsContent());
        }
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
        /**
         * 是否删除
         * */
        if (item.getCommentUser().getAccId().equals(WorkerApplication.get().getAccId().toString())) {
            helper.setVisible(R.id.tv_deleteComment, true);
        } else {
            helper.setVisible(R.id.tv_deleteComment, false);
        }

        helper.addOnClickListener(R.id.iv_seucrity_header);
        helper.addOnClickListener(R.id.tv_name);
        helper.addOnClickListener(R.id.tv_deleteComment);
    }
}
