package net.eanfang.client.ui.adapter;

import android.net.Uri;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.bean.TechnicianDetailsBean;
import com.eanfang.util.GlideUtil;
import com.eanfang.base.kit.V;

import net.eanfang.client.R;


/**
 * @author wq
 * @data 2018/10/23
 */

public class TechnicianDetailsdAdapter extends BaseQuickAdapter<TechnicianDetailsBean.QualificationListBean, BaseViewHolder> {
    private boolean isDelete;

    public TechnicianDetailsdAdapter(boolean mDelete) {
        super(R.layout.layout_item_jsa);
        this.isDelete = mDelete;
    }

    @Override
    protected void convert(BaseViewHolder helper, TechnicianDetailsBean.QualificationListBean item) {
        if (!TextUtils.isEmpty(item.getCertificateName())) {
            helper.setText(R.id.tv_school_name, "资质名称：" + item.getCertificateName());
        } else {
            helper.setText(R.id.tv_school_name, "资质名称：");

        }
        if (!TextUtils.isEmpty(item.getAwardOrg())) {
            helper.setText(R.id.tv_school_major, "颁发机构：" + item.getAwardOrg());
        } else {
            helper.setText(R.id.tv_school_major, "颁发机构：");
        }
        if (item.getEndTime() != null) {
            helper.setText(R.id.tv_school_time, "有效截止期：" + item.getEndTime());
        } else {
            helper.setText(R.id.tv_school_time, "有效截止期：");
        }
        if (item.getCertificatePics() != null) {
            String[] urls = V.v(() -> item.getCertificatePics().split(","));
            //将业务类型的图片显示到列表
            GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + urls[0]),helper.getView(R.id.iv_pic));
        } else {
            GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER ),helper.getView(R.id.iv_pic));
        }
    }

}
