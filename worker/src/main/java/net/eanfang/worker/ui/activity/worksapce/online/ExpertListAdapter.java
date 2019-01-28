package net.eanfang.worker.ui.activity.worksapce.online;

import android.net.Uri;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.ExpertsCertificationEntity;

import net.eanfang.worker.R;

/**
 * Created by Our on 2019/1/23.
 */

public class ExpertListAdapter extends BaseQuickAdapter<ExpertsCertificationEntity, BaseViewHolder> {

    public ExpertListAdapter() {
        super(R.layout.item_expert_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExpertsCertificationEntity item) {
        if (TextUtils.isEmpty(item.getAvatarPhoto())) {
            ((SimpleDraweeView) helper.getView(R.id.iv_expert_header)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + item.getAvatarPhoto()));
        } else {
            ((SimpleDraweeView) helper.getView(R.id.iv_expert_header)).setImageURI(Uri.parse(""));
        }
        helper.setText(R.id.tv_expert, item.getExpertName());
//        helper.setText(R.id.tv_good, item.getSystemType());
//        helper.setText(R.id.tv_major, item.getSystemType());
        helper.addOnClickListener(R.id.tv_ask);
    }
}
