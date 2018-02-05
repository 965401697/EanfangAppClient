package net.eanfang.worker.ui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
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
    private TextView tv_company;
    private TextView tv_maintenance_circle;
    private SimpleDraweeView sdv_pic;
    private TextView tv_time;
    private TextView tv_business;
    private TextView tv_select;

    public MainAdapter(List data) {
        super(R.layout.item_main_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainHistoryBean.ListBean item) {
        helper.setText(R.id.tv_company, item.getClientCompanyName());
        helper.setText(R.id.tv_maintenance_circle, GetConstDataUtils.getCycleList().get(item.getCycle()));
        helper.setText(R.id.tv_time, item.getCreateTime());
        helper.setText(R.id.tv_business, item.getMaintainDetail().getCount() + "");
        helper.addOnClickListener(R.id.tv_select);
        sdv_pic = helper.getView(R.id.sdv_pic);
        if (item.getMaintainDetail().getPictures() != null) {
            sdv_pic.setImageURI(BuildConfig.OSS_SERVER + item.getMaintainDetail().getPictures());
        }
    }
}
