package net.eanfang.worker.ui.adapter.datastatistics;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.bean.datastatistics.DataInstallBean;
import com.eanfang.util.GetConstDataUtils;

import net.eanfang.worker.R;


/**
 * 描述：保修统计 报修Adapter
 *
 * @author Guanluocang
 * @date on 2018/7/16$  16:42$
 */
public class DataStatisticsInstallAdapter extends BaseQuickAdapter<DataInstallBean.InstallBean, BaseViewHolder> {

    private Context mContext;

    public DataStatisticsInstallAdapter(Context context) {
        super(R.layout.layout_item_datastatistice_install);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, DataInstallBean.InstallBean item) {
        helper.setText(R.id.tv_install_name, GetConstDataUtils.getInstallStatus().get(item.getStatus()));
        helper.setText(R.id.tv_install_count, item.getNum() + "");
    }
}
