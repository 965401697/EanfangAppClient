package net.eanfang.client.ui.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;
import net.eanfang.client.ui.model.SelectCompanyBean;
import net.eanfang.client.util.StringUtils;

import java.util.List;

/**
 * 我要报修中的选择技师的adapter
 * Created by Administrator on 2017/3/15.
 */

public class CompanyListAdapter extends BaseQuickAdapter<SelectCompanyBean.All1Bean, BaseViewHolder> {
    public CompanyListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SelectCompanyBean.All1Bean item) {
        if (!StringUtils.isEmpty(item.getLogopic())){
            Uri uri = Uri.parse(item.getLogopic());
            SimpleDraweeView simpleDraweeView = helper.getView(R.id.iv_header);
            simpleDraweeView.setImageURI(uri);
        }

        float starNum = (float) 5.0;
        if (!StringUtils.isEmpty(item.getPraise())){
            starNum = Float.parseFloat(item.getPraise());
        }

        String per = "100%";
        if (!StringUtils.isEmpty(item.getGoodpercent())){
            Double percent = Double.parseDouble(item.getGoodpercent());
            per = percent*100 + "%";
        }


        helper.setText(R.id.tv_name,item.getCompanyname())
                .setText(R.id.tv_koubei,starNum+"分")
                .setText(R.id.tv_haopinglv,per)
                .setText(R.id.tv_address,item.getDetailplace())
                .setText(R.id.tv_install_number,"安装 "+item.getInstallamount()+"单")
                .setText(R.id.tv_repair_number,"维修 "+item.getRepairamount()+"单");

        helper.addOnClickListener(R.id.tv_compare)
                .addOnClickListener(R.id.tv_select);
    }
}
