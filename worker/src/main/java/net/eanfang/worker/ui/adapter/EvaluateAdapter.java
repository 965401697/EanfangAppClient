package net.eanfang.worker.ui.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.model.EvaluateBean;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * 我的-评价的adapter
 * Created by Administrator on 2017/3/15.
 */
public class EvaluateAdapter extends BaseQuickAdapter<EvaluateBean.ListBean, BaseViewHolder> {
    public EvaluateAdapter() {
        super(R.layout.item_evaluate);
    }

    @Override
    protected void convert(BaseViewHolder helper, EvaluateBean.ListBean item) {
        SimpleDraweeView iv_header = helper.getView(R.id.iv_header);

        if (!StringUtils.isEmpty(item.getCreateUser().getAccountEntity().getAvatar())) {
            iv_header.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + item.getCreateUser().getAccountEntity().getAvatar()));
        }

        int totalStar = item.getItem1() + item.getItem2() +
                item.getItem3();
        int average = totalStar / 3;
        helper.setText(R.id.tv_name, item.getCreateUser().getAccountEntity().getRealName())
                .setText(R.id.tv_time, item.getCreateTime());
        MaterialRatingBar rb_star = helper.getView(R.id.rb_star);
        rb_star.setNumStars(average);
        rb_star.setIsIndicator(true);

    }
}
