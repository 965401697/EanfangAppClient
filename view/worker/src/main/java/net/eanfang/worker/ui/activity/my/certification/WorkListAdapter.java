package net.eanfang.worker.ui.activity.my.certification;

import android.net.Uri;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GlideUtil;
import com.eanfang.base.kit.V;
import com.eanfang.biz.model.entity.JobExperienceEntity;

import net.eanfang.worker.R;

import cn.qqtheme.framework.util.DateUtils;

/**
 * Created by O u r on 2018/10/16.
 */

public class WorkListAdapter extends BaseQuickAdapter<JobExperienceEntity, BaseViewHolder> {
    public WorkListAdapter() {
        super(R.layout.item_certificate_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, JobExperienceEntity item) {
        if (!TextUtils.isEmpty(item.getCompanyName())) {
            helper.setText(R.id.tv_school_name, "公司：" + item.getCompanyName());
        } else {
            helper.setText(R.id.tv_school_name, "公司：");
        }
        if (!TextUtils.isEmpty(item.getJob())) {
            helper.setText(R.id.tv_school_major, "职位：" + item.getJob());
        } else {
            helper.setText(R.id.tv_school_major, "职位：");
        }

        if (item.getBeginTime() != null && item.getEndTime() != null) {

            helper.setText(R.id.tv_school_time, "起止时间：" + DateUtils.formatDate(item.getBeginTime(), "yyyy-MM-dd") + " 至 " + DateUtils.formatDate(item.getEndTime(), "yyyy-MM-dd"));

        } else {
            helper.setText(R.id.tv_school_time, "起止时间：" + " 至");
        }

        if (item.getCardPics() != null) {
            String[] urls = V.v(() -> item.getCardPics().split(","));
            //将业务类型的图片显示到列表
            GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + urls[0]),helper.getView(R.id.iv_certificate_pic));
        } else {
            GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER),helper.getView(R.id.iv_certificate_pic));
        }

        helper.addOnClickListener(R.id.tv_delete);
    }
}
