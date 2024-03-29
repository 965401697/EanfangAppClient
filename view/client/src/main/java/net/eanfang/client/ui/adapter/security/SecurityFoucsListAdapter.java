package net.eanfang.client.ui.adapter.security;

import android.content.Context;
import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.bean.security.SecurityFoucsListBean;
import com.eanfang.util.GlideUtil;
import com.eanfang.base.kit.V;

import net.eanfang.client.R;


/**
 * @author guanluocang
 * @data 2019/4/3
 * @description
 */

public class SecurityFoucsListAdapter extends BaseQuickAdapter<SecurityFoucsListBean.ListBean, BaseViewHolder> {

    private Context mContext;
    private boolean isCreate;

    public SecurityFoucsListAdapter(Context context, boolean mIsCreate) {
        super(R.layout.layout_security_foucs_item);
        this.mContext = context;
        this.isCreate = mIsCreate;
    }

    @Override
    protected void convert(BaseViewHolder helper, SecurityFoucsListBean.ListBean item) {
        // 发布人
        helper.setText(R.id.tv_name, V.v(() -> item.getUserEntity().getAccountEntity().getRealName()));
        // 头像
        GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + V.v(() -> item.getUserEntity().getAccountEntity().getAvatar())),helper.getView(R.id.iv_header));
        // 公司名称
        helper.setText(R.id.tv_company, V.v(() -> item.getOrgEntity().getOrgName()));
        /**
         * 是否是好友 2 好友 1 不是好友
         * */
        if (item.getFriend() == 2) {
            helper.setVisible(R.id.tv_friend, true);
        } else {
            helper.setVisible(R.id.tv_friend, false);
        }
        /**
         * 是否认证
         * */
        if (item.getWorkerEntity() != null) {
            if (item.getWorkerEntity() != null && item.getWorkerEntity().getVerifyStatus() != null && item.getWorkerEntity().getVerifyStatus() == 1) {
                helper.setVisible(R.id.iv_certifi, true);
            } else {
                helper.setVisible(R.id.iv_certifi, false);
            }
        }

        /**
         * 创建安防圈 不显示关注人
         * */
        if (isCreate) {
            helper.setVisible(R.id.tv_isFocus, false);
        } else {
            helper.setVisible(R.id.tv_isFocus, true);
        }
        /**
         * 0 是关注 1 是未关注
         * */
        if (item.getFollowsStatus() == 0) {
            helper.setTextColor(R.id.tv_isFocus, mContext.getResources().getColor(R.color.color_bottom));
            helper.setText(R.id.tv_isFocus, "已关注");
            helper.setBackgroundRes(R.id.tv_isFocus, R.drawable.bg_security_unfoucs_back);
        } else {
            helper.setTextColor(R.id.tv_isFocus, mContext.getResources().getColor(R.color.colorPrimary));
            helper.setText(R.id.tv_isFocus, "+ 关注");
            helper.setBackgroundRes(R.id.tv_isFocus, R.drawable.bg_security_foucs_back);
        }
        helper.addOnClickListener(R.id.tv_isFocus);
    }
}
