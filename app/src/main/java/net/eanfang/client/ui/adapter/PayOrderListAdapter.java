package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.BuildConfig;
import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.ui.model.PayOrderListBean;
import net.eanfang.client.ui.model.User;
import net.eanfang.client.util.StringUtils;

import java.util.List;


/**
 * 工作台--报价与支付列表的adapter
 * Created by Administrator on 2017/3/15.
 */

public class PayOrderListAdapter extends BaseQuickAdapter<PayOrderListBean.AllBean, BaseViewHolder> {
    public PayOrderListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PayOrderListBean.AllBean item) {
        //当前登陆人
        User user = EanfangApplication.getApplication().getUser();
        //客户端
        if (BuildConfig.APP_TYPE == 0) {
            helper.setText(R.id.tv_company_name, item.getWorkercompanyname() + "(" + item.getWorkername() + ")");
            //如果汇报人等于当前登陆人  则显示 提报人
        } else if (user.getAccount().equals(item.getClientphone())) {
            helper.setText(R.id.tv_company_name, item.getWorkercompanyname() + "(" + item.getWorkername() + ")");
        } else {
            helper.setText(R.id.tv_company_name, (item.getClientcompanyname() != null ? item.getClientcompanyname() : "个人客户") + "(" + item.getClientname() + ")");
        }
        helper.setText(R.id.tv_order_id, "单号:" + item.getSelfordernum())
                .setText(R.id.tv_appointment_time, "下单:" + item.getCreatetime())
                .setText(R.id.tv_trouble_count, "项目:" + item.getItemname())
                .setText(R.id.tv_count_money, "¥" + item.getSum())
                .setText(R.id.tv_worker_name, "技师：" + item.getWorkername())
                .setText(R.id.tv_client_company_name_wr, "用户：" + item.getClientcompanynamewr());
        //未支付
        if ("0".equals(item.getConfirm())) {
            if (StringUtils.isEmpty(item.getClientcompanyname()))
                helper.setText(R.id.tv_state, "未支付");
            else
                helper.setText(R.id.tv_state, "未同意");

            if (BuildConfig.APP_TYPE == 0) {
                helper.setText(R.id.tv_do_first, "联系技师");
                helper.setText(R.id.tv_do_second, "立即支付");
                //如果汇报人等于当前登陆人  则显示联系提报人
            } else if (user.getAccount().equals(item.getClientphone())) {
                helper.setText(R.id.tv_do_first, "联系提报人");
                helper.setVisible(R.id.tv_do_second, false);
            } else {
                helper.setText(R.id.tv_do_first, "联系汇报人");
                helper.setVisible(R.id.tv_do_second, false);
            }
            //已支付
        } else {
            if (StringUtils.isEmpty(item.getClientcompanyname()))
                helper.setText(R.id.tv_state, "已支付");
            else
                helper.setText(R.id.tv_state, "已同意");
            // helper.setText(R.id.tv_do_first, "查看");
            helper.setVisible(R.id.tv_do_second, false);
            if (BuildConfig.APP_TYPE == 0) {
                helper.setText(R.id.tv_do_first, "联系技师");
            } else if (user.getAccount().equals(item.getClientphone())) {
                helper.setText(R.id.tv_do_first, "联系提报人");
            } else {
                helper.setText(R.id.tv_do_first, "联系客户");
            }
        }
        //将业务类型的图片显示到列表
        ((SimpleDraweeView) helper.getView(R.id.iv_upload)).setImageURI(item.getPic());
        helper.addOnClickListener(R.id.tv_do_first);
        helper.addOnClickListener(R.id.tv_do_second);
    }
}
