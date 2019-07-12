package net.eanfang.worker.ui.adapter.worktender;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.entity.tender.TaskApplyEntity;
import com.eanfang.util.GlideUtil;

import net.eanfang.worker.R;

/**
 * @author guanluocang
 * @data 2019/6/27
 * @description 评标详情
 */
public class WorkTenderOfferDetailAdapter extends BaseQuickAdapter<TaskApplyEntity, BaseViewHolder> {

    private Context mContext;

    public WorkTenderOfferDetailAdapter(Context context) {
        super(R.layout.layout_item_tender_offer_detail);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskApplyEntity item) {
        //头像
        GlideUtil.intoImageView(mContext, Uri.parse(BuildConfig.OSS_SERVER + item.getUserEntity().getAccountEntity().getAvatar()), helper.getView(R.id.iv_header));
        //姓名
        helper.setText(R.id.tv_name, item.getApplyContacts());
        //是否认证
        if (item.getVerifyStatus() == 0) {
            helper.getView(R.id.iv_verify_status).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.iv_verify_status).setVisibility(View.GONE);
        }
        // 公司
        helper.setText(R.id.tv_company, item.getApplyCompanyName());
        // 预算金额
        helper.setText(R.id.tv_budget, item.getProjectQuote() + "元/" + item.getBudgetUnit());
        // 施工方案
        helper.setText(R.id.tv_description, item.getDescription());

        /**
         * 被忽略 的 不显示 选择按钮
         * */
        helper.setGone(R.id.ll_select, item.getStatus() != 1 && item.getStatus() != 3);

        helper.addOnClickListener(R.id.tv_ignore);
        helper.addOnClickListener(R.id.tv_select);
    }
}
