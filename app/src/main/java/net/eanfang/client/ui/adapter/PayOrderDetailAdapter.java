package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;
import net.eanfang.client.ui.widget.PayOrderDetailBean;

import java.util.List;



/**
 * 工作台--报价与支付详情的adapter
 * Created by Administrator on 2017/3/15.
 */

public class PayOrderDetailAdapter extends BaseQuickAdapter<PayOrderDetailBean.DetailListBean, BaseViewHolder> {
    public PayOrderDetailAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PayOrderDetailBean.DetailListBean item) {
        helper.setText(R.id.tv_name, (helper.getLayoutPosition() + 1) + "." + item.getBugonename() + "-" + item.getBugtwoname() + "-" + item.getBugthreename())
                .setText(R.id.tv_count_money, "¥" + item.getSumx())
                .setText(R.id.tv_model, "品牌型号:" + item.getBugfourname())
                .setText(R.id.tv_price, "单位单价:" + item.getUnitprice() + "元/" + item.getUnit())
                .setText(R.id.tv_number, "数量:" + item.getAmount())
                .setText(R.id.tv_city, "产地:" + item.getFactory())
                .setText(R.id.tv_desc, "备注：" + item.getDescription());

        //将业务类型的图片显示到列表
        ((SimpleDraweeView) helper.getView(R.id.iv_pic)).setImageURI(item.getPic());

        helper.addOnClickListener(R.id.ll_item);
    }
}
