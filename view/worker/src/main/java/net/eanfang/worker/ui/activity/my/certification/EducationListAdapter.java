package net.eanfang.worker.ui.activity.my.certification;

import android.net.Uri;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.V;
import com.picker.common.util.DateUtils;
import com.yaf.base.entity.EducationExperienceEntity;

import net.eanfang.worker.R;

/**
 * Created by O u r on 2018/10/16.
 */

public class EducationListAdapter extends BaseQuickAdapter<EducationExperienceEntity, BaseViewHolder> {
    public EducationListAdapter() {
        super(R.layout.item_certificate_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, EducationExperienceEntity item) {
        if (!TextUtils.isEmpty(item.getSchoolName())) {
            helper.setText(R.id.tv_school_name, "学校：" + item.getSchoolName());
        } else {
            helper.setText(R.id.tv_school_name, "学校：");
        }
        if (!TextUtils.isEmpty(item.getMajorName())) {
            helper.setText(R.id.tv_school_major, "专业：" + item.getMajorName());
        } else {
            helper.setText(R.id.tv_school_major, "专业：");
        }

        if (item.getBeginTime() != null && item.getEndTime() != null) {

            helper.setText(R.id.tv_school_time, "起止时间：" + DateUtils.formatDate(item.getBeginTime(), "yyyy-MM-dd") + " 至 " + DateUtils.formatDate(item.getEndTime(), "yyyy-MM-dd"));

        } else {
            helper.setText(R.id.tv_school_time, "起止时间：" + " 至");
        }

        if (item.getCertificatePics() != null) {
            String[] urls = V.v(() -> item.getCertificatePics().split(","));
            //将业务类型的图片显示到列表
            GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + urls[0]),helper.getView(R.id.iv_certificate_pic));
        } else {
            GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER),helper.getView(R.id.iv_certificate_pic));
        }

        helper.addOnClickListener(R.id.tv_delete);
    }
}
