package net.eanfang.client.ui.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;
import net.eanfang.client.ui.model.GiveEvaluateBean;
import net.eanfang.client.util.StringUtils;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * 我的-评价的adapter
 * Created by Administrator on 2017/3/15.
 */

public class GivenEvaluateAdapter extends BaseQuickAdapter<GiveEvaluateBean.AllBean, BaseViewHolder> {
    public GivenEvaluateAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GiveEvaluateBean.AllBean item) {
        SimpleDraweeView iv_header = helper.getView(R.id.iv_header);
        if (!StringUtils.isEmpty(item.getHeadpic())) {
            iv_header.setImageURI(Uri.parse(item.getHeadpic()));
        }

        helper.setText(R.id.tv_type, "技师");

        helper.setText(R.id.tv_name, item.getRealname())
                .setText(R.id.tv_company, item.getCompanyname())
                .setText(R.id.tv_time, item.getCreatetime());
        MaterialRatingBar rb_star = helper.getView(R.id.rb_star);
        if (!StringUtils.isEmpty(item.getTotalreview())) {
            rb_star.setNumStars((int) Float.parseFloat(item.getTotalreview()));
        }

    }
}
