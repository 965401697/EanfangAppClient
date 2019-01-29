package net.eanfang.worker.ui.activity.worksapce.online;

import android.net.Uri;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.AskQuestionsEntity;

import net.eanfang.worker.R;

/**
 * Created by Our on 2019/1/26.
 */

public class CommentFaultSearchAdapter extends BaseQuickAdapter<AskQuestionsEntity, BaseViewHolder> {
    public CommentFaultSearchAdapter() {
        super(R.layout.item_comment_fault_search);
    }

    @Override
    protected void convert(BaseViewHolder helper, AskQuestionsEntity item) {
        if (item.getExpertsCertification() != null && !TextUtils.isEmpty(item.getExpertsCertification().getAvatarPhoto())) {
            ((SimpleDraweeView) helper.getView(R.id.iv_expert_header)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + item.getExpertsCertification().getAvatarPhoto()));
        } else {
            ((SimpleDraweeView) helper.getView(R.id.iv_expert_header)).setImageURI(Uri.parse(""));
        }
        helper.setText(R.id.tv_fault_desc, item.getQuestionContent());
        if (item.getExpertsCertification() != null) {
            helper.setText(R.id.tv_expert_name, item.getExpertsCertification().getExpertName());
        } else {
            helper.setText(R.id.tv_expert_name, "");
        }
        if (item.getExpertsCertification() != null) {
            helper.setText(R.id.tv_desc, item.getExpertsCertification().getSystemType() + "专家    " + "好评率" + item.getExpertsCertification().getFavorableRate());
        } else {
            helper.setText(R.id.tv_desc, "");
        }

    }
}
