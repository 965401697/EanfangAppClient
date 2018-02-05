package net.eanfang.worker.ui.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.model.GiveEvaluateBean;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * 我的-评价的adapter
 * Created by Administrator on 2017/3/15.
 */
public class GiveEvaluateAdapter extends BaseQuickAdapter<GiveEvaluateBean.ListBean, BaseViewHolder> {
    public GiveEvaluateAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GiveEvaluateBean.ListBean item) {
        SimpleDraweeView iv_header = helper.getView(R.id.iv_header);

        if (!StringUtils.isEmpty(item.getCreateUser().getAccountEntity().getAvatar())) {
            iv_header.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(item.getCreateUser().getAccountEntity().getAvatar()));
        }

        int totalStar = item.getItem1() + item.getItem2() +
                item.getItem3() + item.getItem4() + item.getItem5();
        int average = totalStar / 5;
        helper.setText(R.id.tv_name, item.getCreateUser().getAccountEntity().getRealName())
                .setText(R.id.tv_time, item.getCreateTime());
        MaterialRatingBar rb_star = helper.getView(R.id.rb_star);
        rb_star.setNumStars(average);
        rb_star.setIsIndicator(true);

    }
}
