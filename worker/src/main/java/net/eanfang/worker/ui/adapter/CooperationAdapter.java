package net.eanfang.worker.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.config.Config;
import com.eanfang.model.PartnerBean;
import com.eanfang.util.GetConstDataUtils;

import net.eanfang.worker.R;

import java.util.List;

import static com.eanfang.util.V.v;

/**
 * 合作公司的adapter
 * Created by Administrator on 2017/3/15.
 */

public class CooperationAdapter extends BaseQuickAdapter<PartnerBean.ListBean, BaseViewHolder> {


    public CooperationAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PartnerBean.ListBean item) {
        final View header = helper.getView(R.id.rl_header);
        final ImageView iv_down = helper.getView(R.id.iv_down);
        final View ll_second = helper.getView(R.id.ll_second);
        header.setOnClickListener(v -> {
            if (ll_second.getVisibility() == View.VISIBLE) {
                ll_second.setVisibility(View.GONE);
                iv_down.setImageResource(R.drawable.ic_down);
            } else {
                ll_second.setVisibility(View.VISIBLE);
                iv_down.setImageResource(R.drawable.ic_up);
            }
        });

        //公司名
        helper.setText(R.id.tv_company, v(() -> Config.get().getBusinessNameByCode(item.getBusinessOneCode(), 1)));
        //绑定，未绑定
        helper.setText(R.id.btn_unconfirm, v(() -> GetConstDataUtils.getCooperationStatus().get(item.getStatus())));
        //业务类型
        helper.setText(R.id.tv_bugone, v(() -> Config.get().getBusinessNameByCode(item.getBusinessOneCode(), 1)));
        helper.setText(R.id.tv_time_limit, v(() -> item.getBeginTime()) + "  到  " + v(() -> item.getEndTime()));
        //安装
        helper.setText(R.id.tv_repair_install, v(() -> GetConstDataUtils.getCooperationTypeList().get(item.getBusType())));
    }
}
