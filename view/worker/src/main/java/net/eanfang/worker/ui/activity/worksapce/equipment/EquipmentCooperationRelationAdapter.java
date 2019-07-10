package net.eanfang.worker.ui.activity.worksapce.equipment;

import android.net.Uri;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.base.kit.V;
import com.eanfang.util.GlideUtil;
import com.yaf.base.entity.CooperationEntity;

import net.eanfang.worker.R;

/**
 * Created by O u r on 2018/8/10.
 */

public class EquipmentCooperationRelationAdapter extends BaseQuickAdapter<CooperationEntity, BaseViewHolder> {

    private String mOwnerCompanyId;

    public EquipmentCooperationRelationAdapter(int layoutResId, String ownerCompanyId) {
        super(layoutResId);
        this.mOwnerCompanyId = ownerCompanyId;
    }

    @Override
    protected void convert(BaseViewHolder helper, CooperationEntity item) {

        if (String.valueOf(item.getAssigneeOrgId()).equals(mOwnerCompanyId)) {
            ((CheckBox) helper.getView(R.id.cb_check)).setChecked(true);
        } else {
            ((CheckBox) helper.getView(R.id.cb_check)).setChecked(false);
        }


//        if (item.isChecked()) {
//            ((CheckBox) helper.getView(R.id.cb_check)).setChecked(true);
//        } else {
//            ((CheckBox) helper.getView(R.id.cb_check)).setChecked(false);
//
//        }
        GlideUtil.intoImageView(mContext, Uri.parse(BuildConfig.OSS_SERVER + V.v(() -> item.getAssigneeOrg().getOrgUnitEntity().getLogoPic())), helper.getView(R.id.iv_user_header));
        helper.setText(R.id.tv_company_name, item.getAssigneeOrg().getOrgName());
//        helper.setText(R.id.tv_time, GetDateUtils.dateToFormatString(item.getBeginTime(), "yyyy.MM.dd") + " - " + GetDateUtils.dateToFormatString(item.getEndTime(), "yyyy.MM.dd"));
        //0 待审核 1:审核通过 2：失效/拒绝
        helper.addOnClickListener(R.id.cb_check);
    }

    public void setmOwnerCompanyId(String mOwnerCompanyId) {
        this.mOwnerCompanyId = mOwnerCompanyId;
    }
}
