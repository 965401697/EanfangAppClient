package net.eanfang.client.ui.adapter.security;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.bean.security.SecurityDetailBean;
import com.eanfang.util.GlideUtil;
import com.eanfang.base.kit.V;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;


/**
 * @author guanluocang
 * @data 2019/2/27
 * @description 安防圈评论
 */

public class SecurityCommentAdapter extends BaseQuickAdapter<SecurityDetailBean.PageUtilBean.ListBean, BaseViewHolder> {
    public SecurityCommentAdapter() {
        super(R.layout.layout_item_security_comment);
    }

    @Override
    protected void convert(BaseViewHolder helper, SecurityDetailBean.PageUtilBean.ListBean item) {

        LinearLayout mOtherComments = helper.getView(R.id.ll_other_comment);
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
        if (mOtherComments.getChildCount() > 0) {
            mOtherComments.removeAllViews();
        }
        if (item.getCommentsEntityList() != null) {
            for (int i = 0; i < item.getCommentsEntityList().size(); i++) {
                View mCommentView = LayoutInflater.from(ClientApplication.get().getApplicationContext()).inflate(R.layout.layout_security_comment_add_item, null);
                TextView mTvName = mCommentView.findViewById(R.id.tv_name);
                TextView mTvContent = mCommentView.findViewById(R.id.tv_comment_content);
                mTvName.setText(item.getCommentsEntityList().get(i).getCommentUser().getAccountEntity().getRealName());
                mTvContent.setText(item.getCommentsEntityList().get(i).getCommentsContent());
                mOtherComments.addView(mCommentView);
            }
            View mCommentView = LayoutInflater.from(ClientApplication.get().getApplicationContext()).inflate(R.layout.layout_security_comment_add_item, null);
            TextView mTvAllComment = mCommentView.findViewById(R.id.tv_allComment);
            LinearLayout mLlConetent = mCommentView.findViewById(R.id.ll_content);
            mLlConetent.setVisibility(View.GONE);
            mTvAllComment.setVisibility(View.VISIBLE);
            mTvAllComment.setText("查看全部评论(" + item.getReplyCount() + ")");
            mOtherComments.addView(mCommentView);
        }
        helper.addOnClickListener(R.id.iv_seucrity_header);
        helper.addOnClickListener(R.id.tv_name);
    }
}
