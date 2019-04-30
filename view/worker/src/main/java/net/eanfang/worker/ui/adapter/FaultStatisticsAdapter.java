package net.eanfang.worker.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.config.Config;
import com.eanfang.model.FaultTotleBean;

import net.eanfang.worker.R;


/**
 * Created by O u r on 2018/6/19.
 */

public class FaultStatisticsAdapter extends BaseQuickAdapter<FaultTotleBean, BaseViewHolder> {
    public FaultStatisticsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, FaultTotleBean item) {

        String name = Config.get().getBusinessNameByCode(item.getBugOneCode(), 1);
        if (name.equals("电视监控")) {
            helper.setText(R.id.tv_classfiy_name, name);
            ((ImageView) helper.getView(R.id.iv_classfiy_pic)).setImageDrawable(helper.getView(R.id.iv_classfiy_pic).getResources().getDrawable(R.mipmap.ic_monitoring));
        } else if (name.equals("防盗报警")) {
            helper.setText(R.id.tv_classfiy_name, name);
            ((ImageView) helper.getView(R.id.iv_classfiy_pic)).setImageDrawable(helper.getView(R.id.iv_classfiy_pic).getResources().getDrawable(R.mipmap.ic_guard));
        } else if (name.equals("公共报警")) {
            helper.setText(R.id.tv_classfiy_name, name);
            ((ImageView) helper.getView(R.id.iv_classfiy_pic)).setImageDrawable(helper.getView(R.id.iv_classfiy_pic).getResources().getDrawable(R.mipmap.ic_commonality));
        } else if (name.contains("门禁")) {
            helper.setText(R.id.tv_classfiy_name, name);
            ((ImageView) helper.getView(R.id.iv_classfiy_pic)).setImageDrawable(helper.getView(R.id.iv_classfiy_pic).getResources().getDrawable(R.mipmap.ic_entrance));
        } else if (name.equals("可视对讲")) {
            helper.setText(R.id.tv_classfiy_name, name);
            ((ImageView) helper.getView(R.id.iv_classfiy_pic)).setImageDrawable(helper.getView(R.id.iv_classfiy_pic).getResources().getDrawable(R.mipmap.ic_talkback));
        } else if (name.equals("停车场")) {
            helper.setText(R.id.tv_classfiy_name, name);
            ((ImageView) helper.getView(R.id.iv_classfiy_pic)).setImageDrawable(helper.getView(R.id.iv_classfiy_pic).getResources().getDrawable(R.mipmap.ic_park));
        } else if (name.equals("EAS")) {
            helper.setText(R.id.tv_classfiy_name, name);
            ((ImageView) helper.getView(R.id.iv_classfiy_pic)).setImageDrawable(helper.getView(R.id.iv_classfiy_pic).getResources().getDrawable(R.mipmap.ic_eas));
        } else if (name.equals("公共广播")) {
            helper.setText(R.id.tv_classfiy_name, name);
            ((ImageView) helper.getView(R.id.iv_classfiy_pic)).setImageDrawable(helper.getView(R.id.iv_classfiy_pic).getResources().getDrawable(R.mipmap.ic_broadcast));
        }


        helper.setText(R.id.tv_wait_num, String.valueOf(item.getStatus0()));
        helper.setText(R.id.tv_compelet_num, String.valueOf(item.getStatus1()));
        helper.setText(R.id.tv_down_num, String.valueOf(item.getStatus2()));

        helper.addOnClickListener(R.id.ll_wait);
        helper.addOnClickListener(R.id.ll_compelet);
        helper.addOnClickListener(R.id.ll_down);
    }
}
