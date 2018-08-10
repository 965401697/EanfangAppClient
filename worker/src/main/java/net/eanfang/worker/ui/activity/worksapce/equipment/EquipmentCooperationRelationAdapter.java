package net.eanfang.worker.ui.activity.worksapce.equipment;

import android.net.Uri;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GetDateUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.CooperationEntity;

import net.eanfang.worker.R;

/**
 * Created by O u r on 2018/8/10.
 */

public class EquipmentCooperationRelationAdapter extends BaseQuickAdapter<CooperationEntity, BaseViewHolder> {

    public EquipmentCooperationRelationAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CooperationEntity item) {

        if (item.isChecked()) {
            ((CheckBox) helper.getView(R.id.cb_check)).setChecked(true);
        } else {
            ((CheckBox) helper.getView(R.id.cb_check)).setChecked(false);

        }

        ((SimpleDraweeView) helper.getView(R.id.iv_user_header)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + item.getAssigneeOrg().getOrgUnitEntity().getLogoPic()));
        helper.setText(R.id.tv_company_name, item.getAssigneeOrg().getOrgName());
        helper.setText(R.id.tv_time, GetDateUtils.dateToFormatString(item.getBeginTime(), "yyyy.MM.dd") + " - " + GetDateUtils.dateToFormatString(item.getEndTime(), "yyyy.MM.dd"));
        //0 待审核 1:审核通过 2：失效/拒绝
        helper.addOnClickListener(R.id.cb_check);
    }
}
