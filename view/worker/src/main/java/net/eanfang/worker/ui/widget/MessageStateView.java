package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.eanfang.application.EanfangApplication;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.witget.SwitchButton;

import net.eanfang.worker.R;
import net.eanfang.worker.util.PrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/25  15:04
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class MessageStateView extends BaseDialog {


    @BindView(R.id.sb_person_repair)
    SwitchButton sbPersonRepair;
    @BindView(R.id.ll_person_repair)
    RelativeLayout llPersonRepair;


    private Activity mContext;

    public MessageStateView(Activity context) {
        super(context);
        this.mContext = context;
    }


    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_message_state);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        sbPersonRepair.setChecked(PrefUtils.getVBoolean(mContext, PrefUtils.RECEIVE_MSG_SWITCH_CHECK));
        sbPersonRepair.setOnCheckedChangeListener((view, isChecked) -> {
            PrefUtils.setBoolean(mContext, PrefUtils.RECEIVE_MSG_SWITCH_CHECK, isChecked);
            if (!isChecked) {
                Log.e("GG", "关闭推送");
                SDKManager.getXGPush(mContext).unregisterPush();
            } else {
                Log.e("GG", "打开推送");
                SDKManager.getXGPush(mContext).registerPush(EanfangApplication.get().getUser().getAccount().getMobile());
            }
        });


    }

}
