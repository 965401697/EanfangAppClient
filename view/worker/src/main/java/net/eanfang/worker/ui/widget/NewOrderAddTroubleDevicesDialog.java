package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.eanfang.ui.base.BaseDialog;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author guanluocang
 * @data 2019/4/15
 * @description
 */

public class NewOrderAddTroubleDevicesDialog extends BaseDialog {


    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.rl_device_sacn)
    RelativeLayout rlDeviceSacn;
    @BindView(R.id.rl_device_house)
    RelativeLayout rlDeviceHouse;
    @BindView(R.id.rl_device_input)
    RelativeLayout rlDeviceInput;
    private OnSelectListener mOnSelectListener;

    public NewOrderAddTroubleDevicesDialog(Activity context) {
        super(context);
    }

    public NewOrderAddTroubleDevicesDialog(Activity context, OnSelectListener onSelectListener) {
        super(context);
        this.mOnSelectListener = onSelectListener;
    }


    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_repair_select_devices);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_close, R.id.rl_device_sacn, R.id.rl_device_house, R.id.rl_device_input})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.rl_device_sacn:
                mOnSelectListener.onDeviceScan();
                break;
            case R.id.rl_device_house:
                mOnSelectListener.onDeviceHouse();
                break;
            case R.id.rl_device_input:
                mOnSelectListener.onDeviceInput();
                break;
            default:
                break;
        }
    }

    public interface OnSelectListener {

        void onDeviceScan();

        void onDeviceHouse();

        void onDeviceInput();
    }
}
