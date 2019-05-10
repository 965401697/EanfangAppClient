package net.eanfang.worker.ui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.picker.common.util.DateUtils;
import com.yaf.base.entity.JobExperienceEntity;

import net.eanfang.worker.R;

/**
 * @author guanluocang
 * @data 2018/10/23
 * @description 资质证书adapter
 */

public class JsdQualificationsAndAbilitiesAdapter extends BaseQuickAdapter<JobExperienceEntity, BaseViewHolder> {
    private boolean isDelete;

    public JsdQualificationsAndAbilitiesAdapter(boolean mDelete) {
        super(R.layout.layout_item_jsd);
        this.isDelete = mDelete;
    }

    @Override
    protected void convert(BaseViewHolder helper, JobExperienceEntity item) {
        if (!TextUtils.isEmpty(item.getCompanyName())) {
            helper.setText(R.id.a_tv, "任职机构：" + item.getCompanyName());
        } else {
            helper.setText(R.id.a_tv, "任职机构：");
        }
        if (!TextUtils.isEmpty(item.getJob())) {
            helper.setText(R.id.b_tv, "岗位：" + item.getJob());
        } else {
            helper.setText(R.id.b_tv, "岗位：");
        }
        if (item.getBeginTime() != null && item.getEndTime() != null) {
            helper.setText(R.id.c_tv, "起止时间：" + DateUtils.formatDate(item.getBeginTime(), "yyyy-MM-dd") + " 至 " + DateUtils.formatDate(item.getEndTime(), "yyyy-MM-dd"));
        } else {
            helper.setText(R.id.c_tv, "起止时间：" + " 至");
        }
    }
}
