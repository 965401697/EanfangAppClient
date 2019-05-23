package net.eanfang.client.ui.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GetDateUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.RepairFailureEntity;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;

import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2018/2/28  14:13
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class LeaveBugAdapter extends BaseQuickAdapter<RepairFailureEntity, BaseViewHolder> {

    public LeaveBugAdapter(List<RepairFailureEntity> data) {
        super(R.layout.item_leave_bug_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RepairFailureEntity item) {
        if (ClientApplication.get().getCompanyId() == 0) {
            helper.setText(R.id.tv_company, item.getAssigneeUserEntity().getAccountEntity().getRealName());
        } else {
            helper.setText(R.id.tv_company, item.getAssigneeOrgEntity().getOrgName());
        }

        helper.setText(R.id.tv_state, GetConstDataUtils.getBugDetailList().get(item.getStatus()));
        helper.setText(R.id.tv_create_time, GetDateUtils.dateToDateString(item.getCreateTime()));
        helper.setText(R.id.tv_instrument, item.getDeviceName());
        helper.setText(R.id.tv_equipmentposition, item.getBugPosition());
        helper.setText(R.id.tv_description, item.getBugDescription());
        SimpleDraweeView simpleDraweeView = helper.getView(R.id.sdv_pic);
        //将业务类型的图片显示到列表
        if (item.getPictures()!=null){
            String[] urls = item.getPictures().split(",");
            simpleDraweeView.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + urls[0]));
        }

        helper.addOnClickListener(R.id.tv_select);
    }
}
