package net.eanfang.client.ui.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;
import net.eanfang.client.ui.model.WorkspaceDetailBean;
import net.eanfang.client.util.StringUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * 确认订单中的adapter
 * Created by Administrator on 2017/3/26.
 */

public class OrderConfirmAdapter extends BaseQuickAdapter<WorkspaceDetailBean.BugsBean, BaseViewHolder> {

    private String business;

    public OrderConfirmAdapter(int layoutResId, List data, String business) {
        super(layoutResId, data);
        this.business = business;
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkspaceDetailBean.BugsBean item) {
//        helper.setText(R.id.tv_name, item.getName());

        //解决故障明细左上方的一级业务类型读取不出来的问题
       // helper.setText(R.id.tv_name,business+"-"+item.getBugtwoname()+"-"+item.getBugthreename())
        helper.setText(R.id.tv_name,item.getBugonename()+"-"+item.getBugtwoname()+"-"+item.getBugthreename())
                .setText(R.id.tv_model,"品牌型号:"+item.getBugfourname())
                .setText(R.id.tv_location,"故障位置:"+item.getBugposition())
                .setText(R.id.tv_number,"设备编号:"+item.getEquipnum())
                .setText(R.id.tv_desc,"故障描述:"+item.getBugdesc());
        SimpleDraweeView draweeView = helper.getView(R.id.iv_pic);
        if (!StringUtils.isEmpty(item.getBugpic1())){
            Log.e("fresco",item.getBugpic1());
            draweeView.setImageURI(Uri.parse(item.getBugpic1()));
            helper.addOnClickListener(R.id.ll_item);
        }
        helper.addOnClickListener(R.id.iv_pic);
//        Bitmap bitmap = getLoacalBitmap(item.getBugpic1()); //从本地取图片(在cdcard中获取)
//        draweeView.setImageBitmap(bitmap);

    }

    /**
     * 加载本地图片
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

}
