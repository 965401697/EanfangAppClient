package net.eanfang.client.ui.adapter;

import android.graphics.Color;
import android.os.Build;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostDefaultRankingBean;


/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :排名
 */
public class LeavePostRankingAdapter extends BaseQuickAdapter<LeavePostDefaultRankingBean, LeavePostRankingAdapter.LeavePostHomeViewHolder> {
    private String[] changePromptingText = new String[]{"较去年\t", "较上季\t", "较上月\t", "较上周\t", "较昨天\t"};

    public LeavePostRankingAdapter() {
        super(R.layout.item_leave_post_ranking);
    }

    @Override
    protected void convert(LeavePostHomeViewHolder helper, LeavePostDefaultRankingBean item) {
        if (item == null) {
            return;
        }
        if (helper.getLayoutPosition() > 2) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                helper.tvRank.setTextColor(mContext.getColor(R.color.color_client_neworder));
            }
        }
        helper.tvRank.setText(String.valueOf(mData.indexOf(item) + 1));
        helper.tvPostName.setText(item.getAlertName());
        helper.tvAlertCount.setText(item.getAlertsCount());
        String compare;
        if (item.getCompareValue() > 0) {
            compare = changePromptingText[item.getRankingType()] + "+" + item.getCompareValue();
        } else {
            compare = changePromptingText[item.getRankingType()] + item.getCompareValue();
        }
        SpannableString spannableString = new SpannableString(compare);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF6419")), 3, compare.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        helper.tvAlertChange.setText(spannableString);
    }

    class LeavePostHomeViewHolder extends BaseViewHolder {
        private TextView tvRank;
        private TextView tvPostName;
        private TextView tvAlertCount;
        private TextView tvAlertChange;

        public LeavePostHomeViewHolder(View view) {
            super(view);
            tvRank = view.findViewById(R.id.tv_rank);
            tvPostName = view.findViewById(R.id.tv_post_name);
            tvAlertCount = view.findViewById(R.id.tv_alert_count);
            tvAlertChange = view.findViewById(R.id.tv_alert_change);
        }
    }
}
