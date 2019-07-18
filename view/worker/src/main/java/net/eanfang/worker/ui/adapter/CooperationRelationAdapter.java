package net.eanfang.worker.ui.adapter;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GlideUtil;
import com.yaf.base.entity.CooperationEntity;

import net.eanfang.worker.R;

import cn.hutool.core.date.DateUtil;

/**
 * Created by O u r on 2018/6/11.
 */

public class CooperationRelationAdapter extends BaseQuickAdapter<CooperationEntity, BaseViewHolder> {

    public CooperationRelationAdapter(int layoutResId) {
        super(layoutResId);
    }

    @SuppressLint("ResourceType")
    @Override
    protected void convert(BaseViewHolder helper, CooperationEntity item) {

        if (item.getAssigneeOrg().getOrgUnitEntity() != null && item.getAssigneeOrg().getOrgUnitEntity().getLogoPic() != null) {
            GlideUtil.intoImageView(mContext, Uri.parse(BuildConfig.OSS_SERVER + item.getAssigneeOrg().getOrgUnitEntity().getLogoPic()), helper.getView(R.id.iv_user_header));
        }
        helper.setText(R.id.tv_company_name, item.getAssigneeOrg().getOrgName());
        helper.setText(R.id.tv_time, DateUtil.date(item.getBeginTime()).toString("yyyy.MM.dd") + " - " + DateUtil.date(item.getEndTime()).toString("yyyy.MM.dd"));
        //0 待审核 1:审核通过 2：失效/拒绝
        if (item.getStatus() == 0) {
            helper.setText(R.id.tv_status, "待审核");
            helper.getView(R.id.tv_status).setBackground(helper.getView(R.id.tv_status).getContext().getResources().getDrawable(R.drawable.shape_cooperation__relation_audit));
            ((TextView) helper.getView(R.id.tv_status)).setTextColor(helper.getView(R.id.tv_status).getContext().getResources().getColor(R.color.white));
        } else if (item.getStatus() == 1) {
            helper.getView(R.id.tv_status).setBackground(helper.getView(R.id.tv_status).getContext().getResources().getDrawable(R.drawable.shape_cooperation__relation_succee));
            ((TextView) helper.getView(R.id.tv_status)).setTextColor(helper.getView(R.id.tv_status).getContext().getResources().getColor(R.color.colorPrimary));
            helper.setText(R.id.tv_status, "审核通过");
        } else if (item.getStatus() == 2) {
            helper.setText(R.id.tv_status, "失效/拒绝");
            helper.getView(R.id.tv_status).setBackground(helper.getView(R.id.tv_status).getContext().getResources().getDrawable(R.drawable.shape_cooperation__relation_reject));
            ((TextView) helper.getView(R.id.tv_status)).setTextColor(helper.getView(R.id.tv_status).getContext().getResources().getColor(R.color.color_bottom));
        }
    }
}
