package net.eanfang.client.ui.activity.worksapce.online;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.AskQuestionsListBean;

import net.eanfang.client.R;


/**
 * Created by Our on 2019/1/26.
 */

public class CommentFaultSearchAdapter extends BaseQuickAdapter<AskQuestionsListBean.ListBean, BaseViewHolder> {
   // List<AskQuestionsListBean> listBean;
    public CommentFaultSearchAdapter() {
        super(R.layout.item_comment_fault_search);
    }

    @Override
    protected void convert(BaseViewHolder helper, AskQuestionsListBean.ListBean item) {
        if (item.getExpertsCertification() != null) {
            helper.setText(R.id.tv_fault_desc,item.getQuestionSketch());
            //名字
            helper.setText(R.id.tv_expert_name, item.getExpertsCertification().getExpertName());
            //头像
            ((SimpleDraweeView) helper.getView(R.id.iv_expert_header)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + item.getExpertsCertification().getAvatarPhoto()));
            //好评
            helper.setText(R.id.tv_desc, "防盗报警专家  好评率： " + item.getExpertsCertification().getFavorableRate() * 100 + "%");
        } else {
            //故障问题
            helper.setText(R.id.tv_fault_desc,item.getQuestionSketch());
        }

        helper.setVisible(R.id.tv_expert_name, item.getExpertsCertification() != null);
        helper.setVisible(R.id.iv_expert_header, item.getExpertsCertification() != null);
        helper.setVisible(R.id.tv_desc, item.getExpertsCertification() != null);
        helper.setVisible(R.id.tv_picture, item.getExpertsCertification() != null);
    }
    /*public void addAll(List<AskQuestionsListBean.ListBean> moreList) {
        int lastIndex = listBean.size();
        if (list.addAll(moreList)) {
            notifyItemRangeInserted(lastIndex, moreList.size());
        }

        Log.d("HomeRecAdapter", "list.size():" + list.size());
    }*/

}
