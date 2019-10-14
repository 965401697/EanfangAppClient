package net.eanfang.client.ui.adapter.monitor;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.base.BaseApplication;
import com.eanfang.biz.model.entity.Ys7DevicesEntity;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;

/**
 * @author guanluocang
 * @data 2019/9/23
 * @description 搜索设备
 */
public class MonitorSearchAdapter extends BaseQuickAdapter<Ys7DevicesEntity, BaseViewHolder> {

    public MonitorSearchAdapter() {
        super(R.layout.layout_monitor_search_device_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, Ys7DevicesEntity item) {
        // 摄像机名称
        helper.setText(R.id.tv_monitorName, item.getDeviceName());
        GlideUtil.intoImageView(BaseApplication.get().getApplicationContext(), Uri.parse(BuildConfig.OSS_SERVER + item.getLivePic()), helper.getView(R.id.iv_monitorThumbnail));
    }
}
