package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.util.GetDateUtils;
import com.yaf.base.entity.RepairOrderEntity;

import net.eanfang.worker.R;

import java.util.List;


/**
 * 合作公司的adapter
 * Created by Administrator on 2017/3/15.
 */

public class OrderAdapter extends BaseQuickAdapter<RepairOrderEntity, BaseViewHolder> {
    public OrderAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RepairOrderEntity item) {
        String status = "";
        if (item.getStatus().equals("1")) {
            status = "待回电";
        } else if (item.getStatus().equals("2")) {
            status = "待上门";
        } else if (item.getStatus().equals("3")) {
            status = "待完工";
        } else if (item.getStatus().equals("4")) {
            status = "待确认";
        }

        helper.setText(R.id.tv_company, item.getOwnerOrg().getBelongCompany().getOrgName()
                + "  (" + item.getOwnerUser().getAccountEntity().getRealName() + ")");
        helper.setText(R.id.tv_state, status);
        helper.setText(R.id.tv_ordernum, item.getOrderNum());
        helper.setText(R.id.tv_pretime, GetDateUtils.dateToDateString(item.getBookTime()));
        helper.setText(R.id.tv_bugonename, GetDateUtils.dateToDateString(item.getCreateTime()));
        //将业务类型的图片显示到列表
//        if (!StringUtils.isEmpty(item.getBugEntityList().get(helper.getPosition()).getPictures())) {
//            String[] urls = item.getBugEntityList().get(helper.getPosition()).getPictures().split(",");
//            //将业务类型的图片显示到列表
//            ((SimpleDraweeView) helper.getView(R.id.iv_upload)).setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[0]));
//        }


        helper.addOnClickListener(R.id.tv_select);
    }
}
