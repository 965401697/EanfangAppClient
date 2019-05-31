package net.eanfang.worker.ui.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.V;
import com.picker.common.util.DateUtils;
import com.yaf.base.entity.AptitudeCertificateEntity;

import net.eanfang.worker.R;

/**
 * @author guanluocang
 * @data 2018/10/23
 * @description 资质证书adapter
 */

public class QualifyListAdapter extends BaseQuickAdapter<AptitudeCertificateEntity, BaseViewHolder> {
    private boolean isDelete;

    public QualifyListAdapter(boolean mDelete) {
        super(R.layout.layout_item_qualify);
        this.isDelete = mDelete;
    }

    @Override
    protected void convert(BaseViewHolder helper, AptitudeCertificateEntity item) {
        helper.setText(R.id.tv_school_name, "资质名称：" + item.getCertificateName());
        helper.setText(R.id.tv_school_major, "颁发机构：" + item.getAwardOrg());
        helper.setText(R.id.tv_school_time, "有效截止期：" +  V.v(() -> DateUtils.formatDate(item.getEndTime(), "yyyy-MM-dd")));
        if (item.getCertificatePics() != null) {
            String[] urls = V.v(() -> item.getCertificatePics().split(","));
            //将业务类型的图片显示到列表
            GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + urls[0]),helper.getView(R.id.iv_pic));
        } else {
            GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER ),helper.getView(R.id.iv_pic));
        }
    }
}
