package net.eanfang.worker.ui.adapter;

import android.net.Uri;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.entity.RepairFailureEntity;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GlideUtil;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;

import java.util.List;

import cn.hutool.core.date.DateUtil;

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
        if (WorkerApplication.get().getCompanyId() == 0) {
            helper.setText(R.id.tv_company, item.getOwnerUserEntity().getAccountEntity().getRealName());
        } else {
            helper.setText(R.id.tv_company, item.getOwnerOrgEntity().getOrgName());
        }

        helper.setText(R.id.tv_state, GetConstDataUtils.getBugDetailList().get(item.getStatus()));
        helper.setText(R.id.tv_create_time, DateUtil.date(item.getCreateTime()).toDateStr());
        helper.setText(R.id.tv_instrument, item.getDeviceName());
        helper.setText(R.id.tv_equipmentposition, item.getBugPosition());
        helper.setText(R.id.tv_description, item.getBugDescription());
        ImageView imageView = helper.getView(R.id.sdv_pic);
        //将业务类型的图片显示到列表
        if (item.getPictures() != null) {
            String[] urls = item.getPictures().split(",");
            GlideUtil.intoImageView(mContext, Uri.parse(BuildConfig.OSS_SERVER + urls[0]), imageView);
        }

        helper.addOnClickListener(R.id.tv_select);
    }
}
