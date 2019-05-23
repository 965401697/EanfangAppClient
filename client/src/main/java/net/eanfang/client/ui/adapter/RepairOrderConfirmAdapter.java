package net.eanfang.client.ui.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
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
        //String bugOne = Config.get().getBusinessNameByCode(item.getBusinessThreeCode(), 1);
        // String bugTwo = Config.get().getBusinessNameByCode(item.getBusinessThreeCode(), 2);
        String bugThree = Config.get().getBusinessNameByCode(item.getBusinessThreeCode(), 3);
        helper.setText(R.id.tv_name, "故障"+(helper.getLayoutPosition() + 1) + "." + item.getSketch())
//                .setText(R.id.tv_model, "品牌型号:" + Config.get().getModelNameByCode(item.getModelCode(), 1))
                .setText(R.id.tv_model, bugThree)
                .setText(R.id.tv_location, item.getBugPosition() != null ? item.getBugPosition() : "")
                .setText(R.id.tv_deviceStatus, "")
                .setText(R.id.tv_devicesHistory, "")
                .setText(R.id.tv_desc, item.getBugDescription() != null ? item.getBugDescription() : "");
        SimpleDraweeView draweeView = helper.getView(R.id.iv_pic);
        if (!StringUtils.isEmpty(item.getPictures())) {
//            Log.e("fresco", item.getPictures());
            draweeView.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(item.getPictures().split(",")[0]));
            helper.addOnClickListener(R.id.ll_item);
        }
        helper.addOnClickListener(R.id.iv_pic);

    }


}
