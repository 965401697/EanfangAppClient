package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.Var;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.my.MessageListActivity;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * Created by MrHou
 *
 * @on 2018/3/1  16:23
 * @email houzhongzhou@yeah.net
 * @desc 消息
 */

public class ContactListFragment extends BaseFragment {

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView() {
        findViewById(R.id.ll_msg_list).setOnClickListener(v -> startActivity(new Intent(getActivity(), MessageListActivity.class)));

        if (Var.get("ContactListFragment.messageCount").getVar() > 0) {
            ((TextView) findViewById(R.id.tv_bus_msg_info)).setText("新订单消息");
        } else {
            ((TextView) findViewById(R.id.tv_bus_msg_info)).setText("没有新消息");
        }
        Badge qBadgeView = new QBadgeView(getActivity())
                .bindTarget(findViewById(R.id.tv_bus_msg))
                .setBadgeNumber(Var.get("ContactListFragment.messageCount").getVar())
                .setBadgePadding(2, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(0, 0, true)
                .setBadgeTextSize(11, true)
                .setOnDragStateChangedListener((dragState, badge, targetView) -> {
                    //清除成功
                    if (dragState == Badge.OnDragStateChangedListener.STATE_SUCCEED) {
                        EanfangHttp.get(NewApiService.GET_PUSH_READ_ALL).execute(new EanfangCallback(getActivity(), false));
                        showToast("消息被清空了");
//                        Var.get().setVar(0);
                    }
                });
        //变量监听
        Var.get("ContactListFragment.messageCount").setChangeListener((var) -> {
            qBadgeView.setBadgeNumber(var);
        });
    }

    @Override
    protected void setListener() {
    }
}
