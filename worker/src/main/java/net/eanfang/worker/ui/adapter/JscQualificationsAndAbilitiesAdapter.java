package net.eanfang.worker.ui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.picker.common.util.DateUtils;
import com.yaf.base.entity.EducationExperienceEntity;

import net.eanfang.worker.R;

/**
 * @author guanluocang
 * @data 2018/10/23
 * @description 资质证书adapter
 */

public class JscQualificationsAndAbilitiesAdapter extends BaseQuickAdapter<EducationExperienceEntity, BaseViewHolder> {
    private boolean isDelete;

    public JscQualificationsAndAbilitiesAdapter(boolean mDelete) {
        super(R.layout.layout_item_jsc);
        this.isDelete = mDelete;
    }

    @Override
    protected void convert(BaseViewHolder helper, EducationExperienceEntity item) {
        if (!TextUtils.isEmpty(item.getSchoolName())) {
            helper.setText(R.id.a_tv, "教育培训机构：" + item.getSchoolName());
        } else {
            helper.setText(R.id.a_tv, "教育培训机构：");
        }
        if (!TextUtils.isEmpty(item.getMajorName())) {
            helper.setText(R.id.b_tv, "专业或培训内容：" + item.getMajorName());
        } else {
            helper.setText(R.id.b_tv, "专业或培训内容：");
        }

        if (item.getBeginTime() != null && item.getEndTime() != null) {

            helper.setText(R.id.c_tv, "起止时间：" + DateUtils.formatDate(item.getBeginTime(), "yyyy-MM-dd") + " 至 " + DateUtils.formatDate(item.getEndTime(), "yyyy-MM-dd"));

        } else {
            helper.setText(R.id.c_tv, "起止时间：" + " 至");
        }
    }
}
