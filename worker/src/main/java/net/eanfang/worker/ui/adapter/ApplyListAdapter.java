package net.eanfang.worker.ui.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.model.ApplyTaskListBean;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;

import java.util.List;


/**
 * Created by jornl on 2017/7/4.
 */

public class ApplyListAdapter extends BaseQuickAdapter<ApplyTaskListBean.ListBean, BaseViewHolder> {


    public Config config;
    public GetConstDataUtils constDataUtils;

    public ApplyListAdapter(List<ApplyTaskListBean.ListBean> data) {
        super(R.layout.item_apply_task, data);
        config = Config.get(EanfangApplication.get().getApplicationContext());
        constDataUtils = GetConstDataUtils.get(config);
    }

    @Override
    protected void convert(BaseViewHolder helper, ApplyTaskListBean.ListBean item) {

        helper.setText(R.id.tv_apply_time, item.getConfirmTime());
        helper.setText(R.id.tv_state, item.getApplyCompanyName());
        helper.setText(R.id.tv_appointment_time, item.getToDoorTime());
        helper.setText(R.id.tv_appointment_day, constDataUtils.getPredictList().get(item.getPredictTime()));
        helper.setText(R.id.tv_apply_name, item.getApplyContacts());
        helper.setText(R.id.tv_apply_phone, item.getApplyConstactsPhone());
        helper.setText(R.id.tv_count_money, item.getProjectQuote() + "");

        SimpleDraweeView sdv_pic = helper.getView(R.id.sdv_pic);
        if (!StringUtils.isEmpty(item.getPictures())) {
            String[] urls = item.getPictures().split(",");
            sdv_pic.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + urls[0]));
        }
        switch (item.getStatus()) {
            case 0:
                helper.setVisible(R.id.tv_ignore, true);
                helper.setText(R.id.tv_select, "中标");
                break;
            case 1:
                helper.setVisible(R.id.tv_ignore, false);
                helper.setText(R.id.tv_select, "查看详情");
                break;
            default:
                break;
        }


        helper.addOnClickListener(R.id.tv_select);
        helper.addOnClickListener(R.id.tv_ignore);
        helper.addOnClickListener(R.id.tv_state);

    }
}
