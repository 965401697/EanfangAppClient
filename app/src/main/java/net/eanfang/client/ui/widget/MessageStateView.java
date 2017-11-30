package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.eanfang.base.BaseDialog;
import com.eanfang.witget.SwitchButton;
import com.tencent.android.tpush.XGPushManager;

import net.eanfang.client.R;
import net.eanfang.client.util.PrefUtils;

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
        });
        if (sbPersonRepair.isChecked() == false) {
            XGPushManager.unregisterPush(mContext.getApplicationContext());
        }


    }

}
