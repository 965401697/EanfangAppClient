package net.eanfang.client.ui.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.config.Config;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.RepairBugEntity;

import net.eanfang.client.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * 确认订单中的adapter
 * Created by Administrator on 2017/3/26.
 */

public class OrderConfirmAdapter extends BaseQuickAdapter<RepairBugEntity, BaseViewHolder> {

    private Config config = Config.get(mContext);
    private GetConstDataUtils constDataUtils = GetConstDataUtils.get(config);
    private String business;

    public OrderConfirmAdapter(int layoutResId, List data, String business) {
        super(layoutResId, data);
        this.business = business;
    }

    /**
     * 加载本地图片
     *
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, RepairBugEntity item) {
//        helper.setText(R.id.tv_name, item.getName());

        String bugOne = config.getBusinessNameByCode(item.getBusinessThreeCode(), 1);
        String bugTwo = config.getBusinessNameByCode(item.getBusinessThreeCode(), 2);
        String bugThree = config.getBusinessNameByCode(item.getBusinessThreeCode(), 3);
        helper.setText(R.id.tv_name, (helper.getLayoutPosition() + 1) + "." +bugOne + "-" + bugTwo + "-" + bugThree)
                .setText(R.id.tv_model, "品牌型号:" + config.getModelNameByCode(item.getModelCode(), 1))
                .setText(R.id.tv_location, "故障位置:" + item.getBugPosition())
                .setText(R.id.tv_number, "设备编号:" + item.getDeviceNo())
                .setText(R.id.tv_desc, "故障描述:" + item.getBugDescription());
        SimpleDraweeView draweeView = helper.getView(R.id.iv_pic);
        if (!StringUtils.isEmpty(item.getPictures())) {
            String[] urls = item.getPictures().split(",");
            draweeView.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + urls[0]));
            helper.addOnClickListener(R.id.ll_item);
        }
        helper.addOnClickListener(R.id.iv_pic);
//        Bitmap bitmap = getLoacalBitmap(item.getBugpic1()); //从本地取图片(在cdcard中获取)
//        draweeView.setImageBitmap(bitmap);

    }

}
