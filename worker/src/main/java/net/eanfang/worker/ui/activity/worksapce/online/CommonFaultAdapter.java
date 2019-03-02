package net.eanfang.worker.ui.activity.worksapce.online;

import android.net.Uri;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.AskQuestionsEntity;
import com.yaf.base.entity.CommonFaultListBeanEntity;

import net.eanfang.worker.R;

import java.util.List;

/**
 * Created by Our on 2019/1/24.
 */

public class CommonFaultAdapter extends BaseQuickAdapter<CommonFaultListBeanEntity.QuestionListBean.ListBean, BaseViewHolder> {
    public CommonFaultAdapter() {
        super(R.layout.item_common_fault);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommonFaultListBeanEntity.QuestionListBean.ListBean item) {
        if (!TextUtils.isEmpty(item.getQuestionSketch())) {
            helper.setText(R.id.tv_fault_title,item.getQuestionSketch());
        } else {
            helper.setText(R.id.tv_fault_title,"数据出错");
        }
        if (!TextUtils.isEmpty(item.getQuestionContent())) {
            helper.setText(R.id.tv_fault_desc,item.getQuestionContent());
        } else {
            helper.setText(R.id.tv_fault_desc,"数据出错");
        }
        if (!TextUtils.isEmpty(item.getQuestionPics())) {
            ((SimpleDraweeView) helper.getView(R.id.iv_fault_desc)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + item.getQuestionPics()));
        } else {
            ((SimpleDraweeView) helper.getView(R.id.iv_fault_desc)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + "online/b033bd249b694c97bcfaa814c97c30cb.png"));
        }


    }

}
