package net.eanfang.client.ui.activity.worksapce.online;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GlideUtil;
import com.eanfang.biz.model.entity.CommonFaultListBeanEntity;

import net.eanfang.client.R;


/**
 * Created by Our on 2019/1/24.
 */

public class CommonFaultAdapter extends BaseQuickAdapter<CommonFaultListBeanEntity.SimilarQuestionListBean.ListBean, BaseViewHolder> {
    public CommonFaultAdapter() {
        super(R.layout.item_common_fault);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommonFaultListBeanEntity.SimilarQuestionListBean.ListBean item) {
        if (!TextUtils.isEmpty(item.getQuestionSketch())) {
            helper.setText(R.id.tv_fault_title, item.getQuestionSketch());
        } else {
            helper.setText(R.id.tv_fault_title, "数据出错");
        }
        if (!TextUtils.isEmpty(item.getQuestionContent())) {
            helper.setText(R.id.tv_fault_desc, item.getQuestionContent());
        } else {
            helper.setText(R.id.tv_fault_desc, "数据出错");
        }
        if (!TextUtils.isEmpty(item.getQuestionPics())) {                                  //Uri.parse(BuildConfig.OSS_SERVER + item.getAvatarPhoto()
            GlideUtil.intoImageView(mContext, Uri.parse(BuildConfig.OSS_SERVER + item.getQuestionPics().split(",")[0]), helper.getView(R.id.iv_fault_desc));
            Log.i("Tpian", item.getQuestionPics().split(",")[0]);
        } else {
            GlideUtil.intoImageView(mContext, Uri.parse(BuildConfig.OSS_SERVER + "online/b033bd249b694c97bcfaa814c97c30cb.png"), helper.getView(R.id.iv_fault_desc));
        }


    }

}
