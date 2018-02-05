package net.eanfang.client.ui.adapter;

import android.net.Uri;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.config.Config;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.RepairBugEntity;

import net.eanfang.client.R;

import java.util.List;


/**
 * 确认订单中的adapter
 * Created by Administrator on 2017/3/26.
 */

public class RepairOrderConfirmAdapter extends BaseQuickAdapter<RepairBugEntity, BaseViewHolder> {


    public RepairOrderConfirmAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RepairBugEntity item) {
        helper.setText(R.id.tv_name, (helper.getLayoutPosition() + 1) + "."
                + Config.get().getBusinessNameByCode(item.getBusinessThreeCode(), 1));
//        helper.setText(R.id.tv_name, (helper.getLayoutPosition() + 1) + "." + business + "-" + item.getBugtwoname() + "-" + item.getBugthreename())
//                .setText(R.id.tv_model, "品牌型号:" + item.getBugfourname())
//                .setText(R.id.tv_location, "故障位置:" + item.getBugposition())
//                .setText(R.id.tv_number, "设备编号:" + item.getEquipnum())
//                .setText(R.id.tv_desc, "故障描述:" + item.getBugdesc());
        SimpleDraweeView draweeView = helper.getView(R.id.iv_pic);
        if (!StringUtils.isEmpty(item.getPictures())) {
            Log.e("fresco", item.getPictures());
            draweeView.setImageURI(Uri.parse(item.getPictures()));
            helper.addOnClickListener(R.id.ll_item);
        }
        helper.addOnClickListener(R.id.iv_pic);


    }


}
