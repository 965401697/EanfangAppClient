package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.model.MainHistoryBean;
import com.eanfang.util.GetConstDataUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;

import java.util.List;


/**
 * 合作公司的adapter
 * Created by Administrator on 2017/3/15.
 */

public class MainAdapter extends BaseQuickAdapter<MainHistoryBean.ListBean, BaseViewHolder> {
    public Config config;
    public GetConstDataUtils constDataUtils;
    private SimpleDraweeView sdv_pic;

    public MainAdapter(List data) {
        super(R.layout.item_main_list, data);
        config = Config.get(EanfangApplication.get().getApplicationContext());
        constDataUtils = GetConstDataUtils.get(config);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainHistoryBean.ListBean item) {
        helper.setText(R.id.tv_company, item.getClientCompanyName());
        helper.setText(R.id.tv_maintenance_circle, constDataUtils.getCycleList().get(item.getCycle()));
        helper.setText(R.id.tv_time, item.getCreateTime());
        helper.setText(R.id.tv_business, config.getBusinessNameByCode(item.getMaintainDetail().getBusinessThreeCode(), 1));
        helper.addOnClickListener(R.id.tv_select);
        sdv_pic = helper.getView(R.id.sdv_pic);
        if (item.getMaintainDetail().getPictures() != null) {
            String[] urls = item.getMaintainDetail().getPictures().split(",");
            sdv_pic.setImageURI(BuildConfig.OSS_SERVER + urls[0]);
        }
    }
}
