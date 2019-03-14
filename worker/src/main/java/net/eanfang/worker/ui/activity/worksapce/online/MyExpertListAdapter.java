package net.eanfang.worker.ui.activity.worksapce.online;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.CommonFaultListBeanEntity;
import com.yaf.base.entity.ExpertsCertificationEntity;

import net.eanfang.worker.R;

/**
 * Created by Our on 2019/1/23.
 */

public class MyExpertListAdapter extends BaseQuickAdapter<CommonFaultListBeanEntity.ExpertListBean.ListBeanX, BaseViewHolder> {

    public MyExpertListAdapter() {
        super(R.layout.item_expert_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommonFaultListBeanEntity.ExpertListBean.ListBeanX item) {
        if (!TextUtils.isEmpty(item.getAvatarPhoto())) {
            ((SimpleDraweeView) helper.getView(R.id.iv_expert_header)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + item.getAvatarPhoto()));
            Log.i("Tpian",item.getAvatarPhoto());
        } else {
            ((SimpleDraweeView) helper.getView(R.id.iv_expert_header)).setImageURI(Uri.parse(""));
        }
        helper.setText(R.id.tv_expert, item.getExpertName());

        helper.setText(R.id.tv_good, "好评率: "+item.getFavorableRate() * 100 + "%");
        helper.setText(R.id.tv_major, "擅长专业:  "+item.getSystemType());
        helper.setText(R.id.tv_money, item.getPrice()+"元一次");
        helper.addOnClickListener(R.id.tv_ask);
    }

}
