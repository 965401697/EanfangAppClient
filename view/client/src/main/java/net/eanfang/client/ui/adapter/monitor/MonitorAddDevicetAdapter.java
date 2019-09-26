package net.eanfang.client.ui.adapter.monitor;

import android.net.Uri;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.base.BaseApplication;
import com.eanfang.biz.model.entity.Ys7DevicesEntity;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guanluocang
 * @data 2019/9/9
 * @description 添加设备
 */
public class MonitorAddDevicetAdapter extends BaseQuickAdapter<Ys7DevicesEntity, BaseViewHolder> {


    private List<Ys7DevicesEntity> mAddDeviceList = new ArrayList<>();
    public OnAddDeviceItemClickListener onFirstItemClickListener;

    public MonitorAddDevicetAdapter(OnAddDeviceItemClickListener mOnFirstItemClickListener) {
        super(R.layout.layout_monitor_add_device_item);
        this.onFirstItemClickListener = mOnFirstItemClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, Ys7DevicesEntity item) {
        CheckBox cbFirst = helper.getView(R.id.cb_first);
        cbFirst.setTag(helper.getLayoutPosition());
        LinearLayout llItem = helper.getView(R.id.ll_item);
        // 摄像机名称
        helper.setText(R.id.tv_monitorName, item.getDeviceName());
        GlideUtil.intoImageView(BaseApplication.get().getApplicationContext(), Uri.parse(BuildConfig.OSS_SERVER + item.getLivePic()), helper.getView(R.id.iv_monitorThumbnail));

        llItem.setOnClickListener((v) -> {
            item.setChecked(!item.isChecked());
            if (item.isChecked()) {
                cbFirst.setChecked(true);
                mAddDeviceList.add(item);
            } else {
                cbFirst.setChecked(false);
                mAddDeviceList.remove(item);
            }
            onFirstItemClickListener.onItemClick(helper.getLayoutPosition(), mAddDeviceList);
            notifyDataSetChanged();
        });

    }

    public interface OnAddDeviceItemClickListener {
        /**
         * @param posititon
         * @param mAddDeviceList
         */
        void onItemClick(int posititon, List<Ys7DevicesEntity> mAddDeviceList);
    }

}
