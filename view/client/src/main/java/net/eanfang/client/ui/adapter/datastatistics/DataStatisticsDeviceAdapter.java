package net.eanfang.client.ui.adapter.datastatistics;

import android.net.Uri;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.biz.model.datastatistics.DataStatisticsBean;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * 描述：
 *
 * @author Guanluocang
 * @date on 2018/7/18$  15:20$
 */
public class DataStatisticsDeviceAdapter extends BaseQuickAdapter<DataStatisticsBean.DeviceBean, BaseViewHolder> {
    public DataStatisticsDeviceAdapter() {
        super(R.layout.layout_item_datastatistics_device_class_one);
    }

    private DataStatisticsDeviceClassTwoAdapter dataStatisticsReapirAdapter;

    @Override
    protected void convert(BaseViewHolder helper, DataStatisticsBean.DeviceBean item) {
        TextView textView = helper.getView(R.id.tv_title);
        RecyclerView rv_classTwo = helper.getView(R.id.rv_device_class_two);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(mContext, 3);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_classTwo.setLayoutManager(linearLayoutManager);
        dataStatisticsReapirAdapter = new DataStatisticsDeviceClassTwoAdapter();
        dataStatisticsReapirAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        dataStatisticsReapirAdapter.bindToRecyclerView(rv_classTwo);

        textView.setText(Config.get().getBusinessNameByCode(item.getBussinessCode(), 1));
        dataStatisticsReapirAdapter.setNewData(item.getBussinessTwoCodeList());
        GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + Constant.DEVICE_PIC_FONT + item.getBussinessCode() + ".png"),helper.getView(R.id.iv_pic));
    }
}
