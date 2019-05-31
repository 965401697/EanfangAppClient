package net.eanfang.worker.ui.activity.worksapce.online;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GlideUtil;

import net.eanfang.worker.R;

/**
 * Created by on 2019/3/12.
 */

public class CommonQuestionsAdapter extends BaseQuickAdapter<CommonQuestionsBean.ListBean, BaseViewHolder> {
    public CommonQuestionsAdapter() {
        super(R.layout.item_common_questions);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommonQuestionsBean.ListBean item) {
        helper.setText(R.id.tv_fault_desc, item.getQuestionSketch());
        helper.setText(R.id.tv_expert_name, item.getExpertsCertification().getExpertName());
        //头像
        GlideUtil.intoImageView(mContext, Uri.parse(BuildConfig.OSS_SERVER + item.getExpertsCertification().getAvatarPhoto()), helper.getView(R.id.iv_expert_header));
        //好评
        helper.setText(R.id.tv_desc, "防盗报警专家  好评率： " + item.getExpertsCertification().getFavorableRate() * 100 + "%");
    }
}
