package net.eanfang.worker.ui.adapter.datastatistics;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.datastatistics.DataStatisticsBean;
import com.eanfang.util.GetConstDataUtils;

import net.eanfang.worker.R;

/**
 * 描述：保修统计 报修Adapter
 *
 * @author Guanluocang
 * @date on 2018/7/16$  16:42$
 */
public class DataStatisticsReapirAdapter extends BaseQuickAdapter<DataStatisticsBean.RepairBean, BaseViewHolder> {

    private Context mContext;

    private DataStatisticsReapirClassTwoAdapter dataStatisticsReapirAdapter;

    public DataStatisticsReapirAdapter(Context context) {
        super(R.layout.layout_item_datastatistics_repair_class_one);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, DataStatisticsBean.RepairBean item) {
        ImageView imageView = helper.getView(R.id.iv_pic);
        TextView textView = helper.getView(R.id.tv_title);
        TextView tv_num = helper.getView(R.id.tv_num);
        RecyclerView rv_classTwo = helper.getView(R.id.rv_repair_class_two);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_classTwo.setLayoutManager(linearLayoutManager);
        dataStatisticsReapirAdapter = new DataStatisticsReapirClassTwoAdapter();
        dataStatisticsReapirAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        dataStatisticsReapirAdapter.bindToRecyclerView(rv_classTwo);

        if (item.getBughandleStatus() == 0) { //修复
            imageView.setImageResource(R.mipmap.ic_data_repairing);
            textView.setText(GetConstDataUtils.getBugDetailList().get(item.getBughandleStatus()));
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.color_data_repair));
            tv_num.setText("(" + item.getCount() + ")");
            tv_num.setTextColor(ContextCompat.getColor(mContext, R.color.color_data_repair));
            dataStatisticsReapirAdapter.setNewData(item.getBughandleStatusTwoList());
        } else if (item.getBughandleStatus() == 1) {//维修中
            imageView.setImageResource(R.mipmap.ic_data_repaired);
            textView.setText(GetConstDataUtils.getBugDetailList().get(item.getBughandleStatus()));
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.color_data_statics_repairs));
            tv_num.setText("(" + item.getCount() + ")");
            tv_num.setTextColor(ContextCompat.getColor(mContext, R.color.color_data_statics_repairs));
            dataStatisticsReapirAdapter.setNewData(item.getBughandleStatusTwoList());
        } else if (item.getBughandleStatus() == 2) {//报废
            imageView.setImageResource(R.mipmap.ic_data_scarp);
            textView.setText(GetConstDataUtils.getBugDetailList().get(item.getBughandleStatus()));
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.color_data_statics_repair_two));
            tv_num.setText("(" + item.getCount() + ")");
            tv_num.setTextColor(ContextCompat.getColor(mContext, R.color.color_data_statics_repair_two));
            dataStatisticsReapirAdapter.setNewData(item.getBughandleStatusTwoList());
        } else if (item.getBughandleStatus() == 3) {//放弃
            imageView.setImageResource(R.mipmap.ic_data_giveup);
            textView.setText(GetConstDataUtils.getBugDetailList().get(item.getBughandleStatus()));
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.color_client_menu));
            tv_num.setText("(" + item.getCount() + ")");
            tv_num.setTextColor(ContextCompat.getColor(mContext, R.color.color_client_menu));
            dataStatisticsReapirAdapter.setNewData(item.getBughandleStatusTwoList());
        } else if (item.getBughandleStatus() == 4) {//关闭
            imageView.setImageResource(R.mipmap.ic_data_close);
            textView.setText(GetConstDataUtils.getBugDetailList().get(item.getBughandleStatus()));
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.color_client_menu));
            tv_num.setText("(" + item.getCount() + ")");
            tv_num.setTextColor(ContextCompat.getColor(mContext, R.color.color_client_menu));
            dataStatisticsReapirAdapter.setNewData(item.getBughandleStatusTwoList());
        }

    }
}
