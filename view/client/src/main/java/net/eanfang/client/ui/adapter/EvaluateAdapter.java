package net.eanfang.client.ui.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.biz.model.bean.EvaluateBean;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;

import cn.hutool.core.util.StrUtil;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * 我的-评价的adapter
 * Created by Administrator on 2017/3/15.
 */
public class EvaluateAdapter extends BaseQuickAdapter<EvaluateBean.ListBean, BaseViewHolder> {
    private String mTag = "";

    public EvaluateAdapter(String tag) {
        super(R.layout.item_evaluate);
        this.mTag = tag;
    }

    @Override
    protected void convert(BaseViewHolder helper, EvaluateBean.ListBean item) {
        CircleImageView iv_header = helper.getView(R.id.iv_header);
        int totalStar;
        int average;
        /**
         * receive 收到的评价
         *
         * give  给出的评价
         * */
        if (mTag.equals("receive")) {
            if (!StrUtil.isEmpty(item.getCreateUser().getAccountEntity().getAvatar())) {
                GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + item.getCreateUser().getAccountEntity().getAvatar()),iv_header);
            }
            totalStar = item.getItem1() + item.getItem2() +
                    item.getItem3();
            average = totalStar / 3;
            helper.setText(R.id.tv_name, item.getCreateUser().getAccountEntity().getRealName())
                    .setText(R.id.tv_time, item.getCreateTime());
        } else {
            if (!StrUtil.isEmpty(item.getCreateUser().getAccountEntity().getAvatar())) {
                GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + item.getOwnerUser().getAccountEntity().getAvatar()),iv_header);
            }
            totalStar = item.getItem1() + item.getItem2() +
                    item.getItem3() + item.getItem4() + item.getItem5();
            average = totalStar / 5;
            helper.setText(R.id.tv_name, item.getOwnerUser().getAccountEntity().getRealName())
                    .setText(R.id.tv_time, item.getCreateTime());
        }

        MaterialRatingBar rb_star = helper.getView(R.id.rb_star);
        rb_star.setNumStars(average);
        rb_star.setIsIndicator(true);

    }
}
