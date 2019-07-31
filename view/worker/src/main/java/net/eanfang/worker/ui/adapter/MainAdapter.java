package net.eanfang.worker.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.config.Config;
import com.eanfang.biz.model.bean.MainHistoryBean;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GlideUtil;

import net.eanfang.worker.R;


/**
 * 合作公司的adapter
 * Created by Administrator on 2017/3/15.
 */

public class MainAdapter extends BaseQuickAdapter<MainHistoryBean.ListBean, BaseViewHolder> {
    private ImageView sdv_pic;

    public MainAdapter() {
        super(R.layout.item_main_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainHistoryBean.ListBean item) {
        helper.setText(R.id.tv_company, item.getClientCompanyName());
        helper.setText(R.id.tv_maintenance_circle, GetConstDataUtils.getCycleList().get(item.getCycle()));
        helper.setText(R.id.tv_time, item.getCreateTime());
        helper.setText(R.id.tv_business, Config.get().getBusinessNameByCode(item.getMaintainDetail().getBusinessThreeCode(), 1));
        helper.addOnClickListener(R.id.tv_select);
        sdv_pic = helper.getView(R.id.sdv_pic);
        if (item.getMaintainDetail().getPictures() != null) {
            String[] urls = item.getMaintainDetail().getPictures().split(",");
            GlideUtil.intoImageView(mContext,BuildConfig.OSS_SERVER + urls[0],sdv_pic);
        }
    }
}
