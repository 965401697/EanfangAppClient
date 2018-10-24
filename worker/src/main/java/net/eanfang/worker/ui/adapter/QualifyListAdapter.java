package net.eanfang.worker.ui.adapter;

import android.net.Uri;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.V;
import com.facebook.drawee.view.SimpleDraweeView;
import com.picker.common.util.DateUtils;
import com.yaf.base.entity.QualificationCertificateEntity;

import net.eanfang.worker.R;

/**
 * @author guanluocang
 * @data 2018/10/23
 * @description 资质证书adapter
 */

public class QualifyListAdapter extends BaseQuickAdapter<QualificationCertificateEntity, BaseViewHolder> {
    private boolean isDelete;

    public QualifyListAdapter(boolean mDelete) {
        super(R.layout.layout_item_qualify);
        this.isDelete = mDelete;
    }

    @Override
    protected void convert(BaseViewHolder helper, QualificationCertificateEntity item) {
        helper.setText(R.id.tv_school_name, "证书名称：" + item.getCertificateName());
        helper.setText(R.id.tv_school_major, "资质等级：" + item.getCertificateLevel());
        helper.setText(R.id.tv_school_time, "起止时间：" + V.v(() -> DateUtils.formatDate(item.getBeginTime(), "yyyy-MM-dd")) + "至" + V.v(() -> DateUtils.formatDate(item.getEndTime(), "yyyy-MM-dd")));

        if (item.getCertificatePics() != null) {
            String[] urls = V.v(() -> item.getCertificatePics().split(","));
            //将业务类型的图片显示到列表
            ((SimpleDraweeView) helper.getView(R.id.iv_pic)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + urls[0]));
        } else {
            ((SimpleDraweeView) helper.getView(R.id.iv_pic)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER));
        }
        if (isDelete) {// 可以删除
            helper.getView(R.id.tv_delete).setVisibility(View.VISIBLE);
        } else {// 不可删除
            helper.getView(R.id.tv_delete).setVisibility(View.GONE);
        }
        helper.addOnClickListener(R.id.tv_delete);
    }
}
