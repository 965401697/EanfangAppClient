package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.ui.base.BaseDialog;

import net.eanfang.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author guanluocang
 * @data 2019/4/15
 * @description
 */

public class RepairSelectDevicesDialog extends BaseDialog {

    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_device_type)
    TextView tvDeviceType;
    @BindView(R.id.tv_device_warehouse)
    TextView tvDeviceWarehouse;

    private OnSelectListener mOnSelectListener;

    public RepairSelectDevicesDialog(Activity context) {
        super(context);
    }

    public RepairSelectDevicesDialog(Activity context, OnSelectListener onSelectListener) {
        super(context);
        this.mOnSelectListener = onSelectListener;
    }


    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_repair_select_devices);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_close, R.id.tv_device_type, R.id.tv_device_warehouse})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.tv_device_type:
                mOnSelectListener.onDeviceType();
                break;
            case R.id.tv_device_warehouse:
                mOnSelectListener.onDeviceWareHouse();
                break;
            default:
                break;
        }
    }

    public interface OnSelectListener {

        void onDeviceType();

        void onDeviceWareHouse();
    }
}
