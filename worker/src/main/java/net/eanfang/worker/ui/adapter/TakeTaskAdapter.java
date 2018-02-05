package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.config.Config;
import com.eanfang.model.MineTaskListBean;
import com.eanfang.util.GetConstDataUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;

import java.util.List;


public class TakeTaskAdapter extends BaseQuickAdapter<MineTaskListBean.ListBean, BaseViewHolder> {


    public TakeTaskAdapter(List<MineTaskListBean.ListBean> data) {
        super(R.layout.item_take_task, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, MineTaskListBean.ListBean item) {

        helper.setText(R.id.tv_publish_time, item.getPublishCompanyName());
        helper.setText(R.id.tv_state, GetConstDataUtils.getCooperationTypeList().get(item.getType()));

        helper.setText(R.id.tv_appointment_time, item.getToDoorTime());
        helper.setText(R.id.tv_appointment_day, GetConstDataUtils.getPredictList().get(item.getPredicttime()));
        helper.setText(R.id.tv_business, Config.get().getBusinessNameByCode(item.getBusinessOneCode(), 1));
        helper.setText(R.id.tv_project_address, item.getDetailPlace());
        helper.setText(R.id.tv_count_money, GetConstDataUtils.getBudgetList().get(item.getBudget()));
        helper.setVisible(R.id.tv_ignore, false);
        helper.setText(R.id.tv_select, "查看详情");

        SimpleDraweeView sdv_pic = helper.getView(R.id.sdv_pic);
        if (item.getPictures() != null) {
            String[] urls = item.getPictures().split(",");
            if (urls[0].length() != 0) {
                sdv_pic.setImageURI(BuildConfig.OSS_SERVER + urls[0]);
            }

        }

        helper.addOnClickListener(R.id.tv_state);
        helper.addOnClickListener(R.id.tv_select);
        helper.addOnClickListener(R.id.tv_ignore);

    }
}
