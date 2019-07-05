package net.eanfang.worker.ui.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.biz.model.EvaluateBean;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.StringUtils;
import com.eanfang.base.kit.V;

import net.eanfang.worker.R;

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
         * receive 收到的评价 create 評價人
         *
         * give  给出的评价 owner 被评价人
         * */
        if (mTag.equals("receive")) {
            if (!StringUtils.isEmpty(V.v(()->item.getCreateUser().getAccountEntity().getAvatar()))) {
                GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + item.getCreateUser().getAccountEntity().getAvatar()),iv_header);
            }
            totalStar = item.getItem1() + item.getItem2() +
                    item.getItem3() + item.getItem4() + item.getItem5();
            average = totalStar / 5;
            helper.setText(R.id.tv_name,V.v(()-> item.getCreateUser().getAccountEntity().getRealName()))
                    .setText(R.id.tv_time, item.getCreateTime());
        } else {
            if (!StringUtils.isEmpty(V.v(()->item.getCreateUser().getAccountEntity().getAvatar()))) {
                iv_header.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + V.v(()->item.getOwnerUser().getAccountEntity().getAvatar())));
            }
            totalStar = item.getItem1() + item.getItem2() +
                    item.getItem3();
            average = totalStar / 3;
            helper.setText(R.id.tv_name, V.v(()->item.getOwnerUser().getAccountEntity().getRealName()))
                    .setText(R.id.tv_time, item.getCreateTime());
        }



        MaterialRatingBar rb_star = helper.getView(R.id.rb_star);
        rb_star.setNumStars(average);
        rb_star.setIsIndicator(true);

    }
}
