package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.kit.cache.CacheKit;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.witget.SwitchButton;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;

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
    private static final String RECEIVE_MSG_SWITCH_CHECK = "receive_msg_switch_check";
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
        sbPersonRepair.setChecked(CacheKit.get().getBool(RECEIVE_MSG_SWITCH_CHECK, true));
        sbPersonRepair.setOnCheckedChangeListener((view, isChecked) -> {
            CacheKit.get().put(RECEIVE_MSG_SWITCH_CHECK, isChecked);

            if (!isChecked) {
                Log.e("GG", "关闭推送");
                SDKManager.getXGPush(mContext).unregisterPush();
            } else {
                Log.e("GG", "打开推送");
                SDKManager.getXGPush(mContext).registerPush(ClientApplication.get().getLoginBean().getAccount().getMobile());
            }
        });


    }

}
