package net.eanfang.client.ui.adapter;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GetDateUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.CooperationEntity;

import net.eanfang.client.R;

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
        ((SimpleDraweeView) helper.getView(R.id.iv_user_header)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + item.getOwnerOrg().getOrgUnitEntity().getLogoPic()));
        helper.setText(R.id.tv_company_name, item.getOwnerOrg().getOrgName());
        helper.setText(R.id.tv_time, GetDateUtils.dateToFormatString(item.getBeginTime(), "yyyy.MM.dd") + " - " + GetDateUtils.dateToFormatString(item.getEndTime(), "yyyy.MM.dd"));
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
