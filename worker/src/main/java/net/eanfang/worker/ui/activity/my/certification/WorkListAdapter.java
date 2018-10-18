package net.eanfang.worker.ui.activity.my.certification;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.V;
import com.facebook.drawee.view.SimpleDraweeView;
import com.picker.common.util.DateUtils;
import com.yaf.base.entity.JobExperienceEntity;

import net.eanfang.worker.R;

/**
 * Created by O u r on 2018/10/16.
 */

public class WorkListAdapter extends BaseQuickAdapter<JobExperienceEntity, BaseViewHolder> {
    public WorkListAdapter() {
        super(R.layout.item_certificate_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, JobExperienceEntity item) {
        helper.setText(R.id.tv_school_name, "公司：" + item.getCompanyName());
        helper.setText(R.id.tv_school_major, "职位：" + item.getJob());
        helper.setText(R.id.tv_school_time, "起止时间：" + DateUtils.formatDate(item.getBeginTime(), "yyyy-MM-dd")+" 至 "+DateUtils.formatDate(item.getEndTime(), "yyyy-MM-dd"));

        if (item.getCardPics() != null) {
            String[] urls = V.v(() -> item.getCardPics().split(","));
            //将业务类型的图片显示到列表
            ((SimpleDraweeView) helper.getView(R.id.iv_certificate_pic)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + urls[0]));
        } else {
            ((SimpleDraweeView) helper.getView(R.id.iv_certificate_pic)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER));
        }

        helper.addOnClickListener(R.id.tv_delete);
    }
}
