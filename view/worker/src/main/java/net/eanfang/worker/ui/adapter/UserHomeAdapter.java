package net.eanfang.worker.ui.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.BuildConfig;
import com.eanfang.model.UserHomePageBean;

import net.eanfang.worker.ui.adapter.viewholder.UserHomePageViewHolder;

import java.text.MessageFormat;


/**
 * @author liangkailun
 * Date ：2019/4/9
 * Describe :用户主页面用户职位信息适配器
 */
public class UserHomeAdapter extends BaseQuickAdapter<UserHomePageBean.JobListBean, UserHomePageViewHolder> {

    public UserHomeAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(UserHomePageViewHolder helper, UserHomePageBean.JobListBean item) {
        helper.mImgCompany.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + item.getCompanyImg()));
        helper.mTvCompany.setText(item.getCompanyName());
        helper.mTvInServiceTime.setText(MessageFormat.format("{0} —— {1}", item.getStartDate(), item.getEndDate()));
        helper.mTvJobPosition.setText(item.getJob());
    }
}