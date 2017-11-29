package net.eanfang.client.ui.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;
import net.eanfang.client.ui.model.SelectWorkerListBean;
import net.eanfang.client.util.StringUtils;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * 我要报修中的选择技师的adapter
 * Created by Administrator on 2017/3/15.
 */

public class WorkerListAdapter extends BaseQuickAdapter<SelectWorkerListBean.All1Bean, BaseViewHolder> {
    public WorkerListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SelectWorkerListBean.All1Bean item) {
        if (!StringUtils.isEmpty(item.getHeadpic())) {
            Uri uri = Uri.parse(item.getHeadpic());
            SimpleDraweeView simpleDraweeView = helper.getView(R.id.iv_header);
            simpleDraweeView.setImageURI(uri);
        }

        float starNum = (float) 5.0;
        if (!StringUtils.isEmpty(item.getPraise())) {
            starNum = Float.parseFloat(item.getPraise());
        }

        String per = "100%";
        if (!StringUtils.isEmpty(item.getGoodpercent())) {
            Double percent = Double.parseDouble(item.getGoodpercent());
            per = percent * 100 + "%";
        }

        helper.setText(R.id.tv_name, item.getRealname())
                .setText(R.id.tv_company, item.getCompanyname())
                .setText(R.id.tv_koubei, starNum + "分")
                .setText(R.id.tv_haopinglv, per)
                .setText(R.id.tv_years, item.getWorkexp());

        MaterialRatingBar rb_star = helper.getView(R.id.rb_star);
        rb_star.setRating(starNum);
        helper.addOnClickListener(R.id.btn_select);
    }
}
