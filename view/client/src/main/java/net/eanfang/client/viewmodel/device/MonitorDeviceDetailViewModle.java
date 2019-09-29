package net.eanfang.client.viewmodel.device;

import android.util.Log;

import com.eanfang.biz.rds.base.BaseViewModel;

import net.eanfang.client.databinding.ActivityMonitorDeviceDetailBinding;
import net.eanfang.client.ui.widget.MonitorTimeLineView;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/9/27
 * @description
 */
public class MonitorDeviceDetailViewModle extends BaseViewModel implements MonitorTimeLineView.DoGetValueListener {

    @Getter
    @Setter
    private ActivityMonitorDeviceDetailBinding monitorDeviceDetailBinding;

    public String mDeviceSerial;

    public MonitorDeviceDetailViewModle() {

    }

    public void init() {
        monitorDeviceDetailBinding.viewTimeLine.setOnScrollListener(this);
    }

    @Override
    public void doGetValue(String mValue) {
        Log.e("GG", "value" + mValue);
    }
}
