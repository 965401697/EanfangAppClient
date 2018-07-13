package net.eanfang.client.ui.activity.worksapce.defendlog;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.DefendLogDetailBean;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.V;
import com.yaf.base.entity.LogDetailsEntity;

import net.eanfang.client.R;

/**
 * Created by O u r on 2018/5/23.
 */

public class DefendLogItemAdapter extends BaseQuickAdapter<LogDetailsEntity, BaseViewHolder> {

    private int mFlag;

    public DefendLogItemAdapter(int layoutResId, int flag) {
        super(layoutResId);
        this.mFlag = flag;
    }

    @Override
    protected void convert(BaseViewHolder helper, LogDetailsEntity item) {

        helper.setText(R.id.tv_desc, (helper.getAdapterPosition() + 1) + "," + item.getPlayLocaltion());


        if (item.getLogType() == 1) {
            helper.setText(R.id.tv_cause, GetConstDataUtils.getThroughCause().get(item.getAlarmReason()) + "(" + item.getAlarmNum() + ")");
        } else if (item.getLogType() == 2) {
            helper.setText(R.id.tv_cause, GetConstDataUtils.getFlaseCause().get(item.getAlarmReason()) + "(" + item.getAlarmNum() + ")");
        } else {
            helper.setText(R.id.tv_cause, GetConstDataUtils.getBypassCause().get(item.getAlarmReason()) + "(" + item.getAlarmNum() + ")");
        }

        if (mFlag == 1) {
            helper.getView(R.id.iv_delect).setVisibility(View.VISIBLE);
            helper.getView(R.id.iv_detail).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.iv_delect).setVisibility(View.GONE);
            helper.getView(R.id.iv_detail).setVisibility(View.VISIBLE);
        }
        helper.addOnClickListener(R.id.iv_delect);

    }
}
