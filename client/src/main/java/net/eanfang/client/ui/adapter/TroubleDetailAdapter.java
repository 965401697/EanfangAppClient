package net.eanfang.client.ui.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.config.Config;
import com.eanfang.util.StringUtils;
import com.eanfang.util.V;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.BughandleDetailEntity;

import net.eanfang.client.R;

import java.util.List;

import static com.eanfang.util.V.v;


/**
 * Created by wen on 2017/4/23.
 */

public class TroubleDetailAdapter extends BaseQuickAdapter<BughandleDetailEntity, BaseViewHolder> {
    public TroubleDetailAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BughandleDetailEntity item) {
        String bugOne = Config.get().getBusinessNameByCode(item.getFailureEntity().getBusinessThreeCode(), 1);
        String bugTwo = Config.get().getBusinessNameByCode(item.getFailureEntity().getBusinessThreeCode(), 2);
        String bugThree = Config.get().getBusinessNameByCode(item.getFailureEntity().getBusinessThreeCode(), 3);
        // 故障信息
        // 故障简述
        helper.setText(R.id.tv_faultDescribe, item.getFailureEntity().getBugDescription());
        // 设备名称
        helper.setText(R.id.tv_devicesName, item.getFailureEntity().getDeviceName());
        // 设备状态
//        helper.setText(R.id.tv_deviceStatus, bugThree);
        // 维修历史
//        helper.setText(R.id.tv_devicesHistory, bugThree);

        helper.setText(R.id.tv_num, helper.getAdapterPosition() + 1 + "");
        //设备位置
        helper.setText(R.id.tv_devicesAdress, v(() -> item.getFailureEntity().getBugPosition()));
        //将业务类型的图片显示到列表
        String imgUrl = V.v(() -> item.getFailureEntity().getPictures().split(",")[0]);
        if (!StringUtils.isEmpty(imgUrl) && imgUrl.length() > 10) {
            ((SimpleDraweeView) helper.getView(R.id.iv_faultImg)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + imgUrl));
        }

    }
}
