package net.eanfang.worker.ui.activity.worksapce.online;

import android.net.Uri;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.model.ExpertListBean;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.CommonFaultListBeanEntity;
import com.yaf.base.entity.ExpertsCertificationEntity;

import net.eanfang.worker.R;

import java.util.List;

/**
 * Created by Our on 2019/1/23.
 */

public class ExpertListAdapter extends BaseQuickAdapter<ExpertListBean.ListBean, BaseViewHolder> {

    public ExpertListAdapter() {
        super(R.layout.item_expert_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExpertListBean.ListBean item) {
        ((SimpleDraweeView) helper.getView(R.id.iv_expert_header)).setImageURI(Uri.parse(item.getAvatarPhoto()));
        helper.setText(R.id.tv_expert, item.getExpertName());
        helper.setText(R.id.tv_good, "好评率:  "+item.getFavorableRate()*100+"%");
        helper.setText(R.id.tv_major, "擅长专业:  "+item.getSystemType());
        if (item.getPrice()>0) {
            helper.setText(R.id.tv_money, item.getPrice()+"元一次");
        }else{
            helper.setText(R.id.tv_money, "免费");
        }

        helper.addOnClickListener(R.id.tv_ask);
    }

}
