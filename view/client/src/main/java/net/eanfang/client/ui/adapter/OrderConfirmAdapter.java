package net.eanfang.client.ui.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.entity.RepairBugEntity;
import com.eanfang.config.Config;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import cn.hutool.core.util.StrUtil;

/**
 * 确认订单中的adapter
 * Created by Administrator on 2017/3/26.
 */

public class OrderConfirmAdapter extends BaseQuickAdapter<RepairBugEntity, BaseViewHolder> {

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
        String bugOne = Config.get().getBusinessNameByCode(item.getBusinessThreeCode(), 1);
        String bugTwo = Config.get().getBusinessNameByCode(item.getBusinessThreeCode(), 2);
        String bugThree = Config.get().getBusinessNameByCode(item.getBusinessThreeCode(), 3);
        if (item.getSketch() != null) {
            helper.setText(R.id.tv_name, (helper.getLayoutPosition() + 1) + "." + item.getSketch());
        }
        helper.setText(R.id.tv_model, bugThree);
        helper.setText(R.id.tv_location, item.getBugPosition());
        helper.setText(R.id.tv_desc, item.getBugDescription());
        //位置编号
        helper.setText(R.id.tv_deviceNumber, item.getLocationNumber());

        ImageView draweeView = helper.getView(R.id.iv_pic);
        if (!StrUtil.isEmpty(item.getPictures())) {
            String[] urls = item.getPictures().split(",");
            GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + urls[0]),draweeView);
            helper.addOnClickListener(R.id.ll_item);
        }
        helper.addOnClickListener(R.id.iv_pic);
//        Bitmap bitmap = getLoacalBitmap(item.getBugpic1()); //从本地取图片(在cdcard中获取)
//        draweeView.setImageBitmap(bitmap);

    }

}
