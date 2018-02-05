//package net.eanfang.client.ui.adapter;
//
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.chad.library.adapter.base.BaseViewHolder;
//import com.eanfang.model.PayOrderListBean;
//
////
////
/////**
//// * 工作台--报价与支付列表的adapter
//// * Created by Administrator on 2017/3/15.
//// */
////
//public class CompanyPayOrderListAdapter extends BaseQuickAdapter<PayOrderListBean.AllBean, BaseViewHolder> {
//    @Override
//    protected void convert(BaseViewHolder helper, PayOrderListBean.AllBean item) {
//
//    }
////    public CompanyPayOrderListAdapter(int layoutResId, List data) {
////        super(layoutResId, data);
////    }
////
////    @Override
////    protected void convert(BaseViewHolder helper, PayOrderListBean.AllBean item) {
////        helper.setText(R.id.tv_company_name, item.getWorkercompanyname() + "(" + item.getWorkername() + ")")
////                .setText(R.id.tv_order_id, "单号：" + item.getSelfordernum())
////                .setText(R.id.tv_appointment_time, "下单：" + item.getCreatetime())
////                .setText(R.id.tv_trouble_count, "项目：" + item.getItemname())
////                .setText(R.id.tv_count_money, "¥" + item.getSum())
////                .setText(R.id.tv_worker_name, "技师：" + item.getWorkername())
////                .setText(R.id.tv_client_company_name_wr, "用户：" + item.getClientcompanynamewr());
////        if ("0".equals(item.getConfirm())) {
////            helper.setText(R.id.tv_state, "未确认")
////                    .setText(R.id.tv_do_first, "联系技师")
////                    .setText(R.id.tv_do_second, "同意报价");
//////            View tv_do_second = helper.getView(R.id.tv_do_second);
////        } else {
////            helper.setText(R.id.tv_state, "已确认")
////                    .setText(R.id.tv_do_first, "查看")
////                    .setText(R.id.tv_do_second, "联系技师");
////        }
////        //将业务类型的图片显示到列表
////        ((SimpleDraweeView) helper.getView(R.id.iv_upload)).setImageURI(item.getPic());
////        helper.addOnClickListener(R.id.tv_do_first);
////        helper.addOnClickListener(R.id.tv_do_second);
////
////    }
//}
