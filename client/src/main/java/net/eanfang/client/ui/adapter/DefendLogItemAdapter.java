package net.eanfang.client.ui.adapter;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.DefendLogDetailBean;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.V;

import net.eanfang.client.R;

/**
 * Created by O u r on 2018/5/23.
 */

public class DefendLogItemAdapter extends BaseQuickAdapter<DefendLogDetailBean.ListBean, BaseViewHolder> {
    public DefendLogItemAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, DefendLogDetailBean.ListBean item) {

        if (TextUtils.isEmpty(item.getId())) {
            helper.getView(R.id.tv_delect).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.tv_delect).setVisibility(View.GONE);
        }

        helper.setText(R.id.tv_desc, (helper.getAdapterPosition() + 1) + "," + item.getPlayLocaltion());
//        if (item.getLogType() == 1)
//            helper.setText(R.id.tv_cause, (helper.getAdapterPosition() + 1) + "" + item.getPlayLocaltion());

        if (item.getLogType() == 1) {
            helper.setText(R.id.tv_cause, GetConstDataUtils.getThroughCause().get(item.getAlarmReason()) + "(" + item.getAlarmNum() + ")");
        } else if (item.getLogType() == 2) {
            helper.setText(R.id.tv_cause, GetConstDataUtils.getFlaseCause().get(item.getAlarmReason()) + "(" + item.getAlarmNum() + ")");
        } else {
            helper.setText(R.id.tv_cause, GetConstDataUtils.getBypassCause().get(item.getAlarmReason()) + "(" + item.getAlarmNum() + ")");
        }

        helper.addOnClickListener(R.id.tv_delect);

    }
}
