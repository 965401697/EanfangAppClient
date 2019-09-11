package net.eanfang.client.ui.adapter.monitor;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.base.BaseApplication;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;

/**
 * @author guanluocang
 * @data 2019/9/9
 * @description
 */
public class MonitorListRightAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public MonitorListRightAdapter() {
        super(R.layout.layout_monitor_right_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        // 摄像机名称
        helper.setText(R.id.tv_monitorName, "摄像机名称");
        GlideUtil.intoImageView(BaseApplication.get().getApplicationContext(), Uri.parse(""), helper.getView(R.id.iv_monitorThumbnail));
        helper.addOnClickListener(R.id.iv_monitorSetting);
    }
}
