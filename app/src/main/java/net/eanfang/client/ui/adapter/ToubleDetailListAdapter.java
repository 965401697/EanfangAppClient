package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.util.GetDateUtils;

import net.eanfang.client.R;
import net.eanfang.client.config.Config;
import net.eanfang.client.ui.model.repair.BughandleConfirmEntity;

import java.util.List;


/**
 * Created by Hou on 2017/4/23.
 */

public class ToubleDetailListAdapter extends BaseQuickAdapter<BughandleConfirmEntity, BaseViewHolder> {
    private final List<String> mTitlesWorker = Config.getConfig().getRepairStatusWorker();
    private String[] mTitles;
    public ToubleDetailListAdapter(List data) {

        super(R.layout.item_trouble_detail_list, data);
        mTitles = new String[mTitlesWorker.size()];
        mTitlesWorker.toArray(mTitles);
    }

    @Override
    protected void convert(BaseViewHolder helper, BughandleConfirmEntity item) {
        if (item.getCreateUserEntity().getAccountEntity().getRealName() != null) {
            String first_name = item.getCreateUserEntity().getAccountEntity().getRealName();
            helper.setText(R.id.tv_last_name, first_name);
            if (first_name.length() == 2) {
                helper.setText(R.id.tv_first_name, first_name);
            } else if (first_name.length() == 3) {
                first_name = first_name.substring(1, 2);
                helper.setText(R.id.tv_first_name, first_name);
            } else if (first_name.length() == 4) {
                first_name = first_name.substring(2, 3);
                helper.setText(R.id.tv_first_name, first_name);
            }
        }

        if (item.getCreateTime()!=null){
            helper.setText(R.id.tv_create_time, "创建时间："+ GetDateUtils.dateToDateString(item.getCreateTime()));
        }
        if (item.getOverTime()!=null){
            helper.setText(R.id.tv_end_time,"完工时间："+ GetDateUtils.dateToDateString(item.getOverTime()));
        }

        if (item.getLeftoverProblem() != null) {
            helper.setText(R.id.tv_leave_trouble, "遗留问题："+item.getLeftoverProblem());
        } else {
            helper.setVisible(R.id.tv_leave_trouble, false);
        }


    }
}
