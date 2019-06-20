package net.eanfang.worker.ui.activity.im;

import android.app.Activity;
import android.net.Uri;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.biz.model.TemplateBean;
import com.eanfang.biz.model.device.User;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.StringUtils;

import net.eanfang.worker.R;

/**
 * Created by O u r on 2018/11/15.
 */

public class OrganizationPersonAdapter extends BaseQuickAdapter<TemplateBean.Preson, BaseViewHolder> {


    public OrganizationPersonAdapter(int layoutResId) {
        super(R.layout.item_two_level);

    }

    @Override
    protected void convert(BaseViewHolder helper, TemplateBean.Preson item) {
        if (StringUtils.isEmpty(item.getName())) {
            EanfangHttp.get(UserApi.POST_USER_INFO + item.getId())
                    .execute(new EanfangCallback<User>((Activity) mContext, false, User.class, (bean) -> {
                        if (bean != null) {
                            helper.setText(R.id.tv_name, bean.getNickName());
                            if (bean.getAvatar().startsWith("http:")) {
                                GlideUtil.intoImageView(mContext,bean.getAvatar(),helper.getView(R.id.iv_user_header));
                            } else {
                                GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + bean.getAvatar()),helper.getView(R.id.iv_user_header));
                            }
                        }
                    }));
        } else {
            helper.setText(R.id.tv_name, item.getName());
            GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + item.getProtraivat()),helper.getView(R.id.iv_user_header));
            if (item.getProtraivat().startsWith("http:")) {
                GlideUtil.intoImageView(mContext,item.getProtraivat(),helper.getView(R.id.iv_user_header));
            } else {
                GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + item.getProtraivat()),helper.getView(R.id.iv_user_header));
            }
        }
        if (item.isChecked()) {
            ((CheckBox) helper.getView(R.id.cb_check)).setChecked(true);
        } else {
            ((CheckBox) helper.getView(R.id.cb_check)).setChecked(false);
        }


    }
}
