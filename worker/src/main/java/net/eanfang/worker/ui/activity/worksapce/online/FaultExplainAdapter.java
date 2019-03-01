package net.eanfang.worker.ui.activity.worksapce.online;

import android.net.Uri;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.AnswerListWithQuestionBean;

import net.eanfang.worker.R;

import java.util.List;

/**
 * Created by Our on 2019/1/24.
 */

public class FaultExplainAdapter extends BaseQuickAdapter<AnswerListWithQuestionBean.AnswersBean, BaseViewHolder> {
    public FaultExplainAdapter() {
        super(R.layout.item_fault_explain);
    }
    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;

    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";
    @Override
    protected void convert(BaseViewHolder helper, AnswerListWithQuestionBean.AnswersBean item) {

        //头像
        ((SimpleDraweeView) helper.getView(R.id.iv_expert_header)).setImageURI(item.getExpertsCertificationEntity().getAvatarPhoto());
        //名字
        helper.setText(R.id.tv_expert_name,item.getExpertsCertificationEntity().getApproveUserName());
        //品牌专家
        helper.setText(R.id.tv_major, item.getExpertsCertificationEntity().getCompany()+"专家");
        //时间
        helper.setText(R.id.tv_time, format(item.getAnswerCreateTimeLong()));
        //描述
        helper.setText(R.id.tv_answer, item.getAnswerContent());

        //点赞量
        helper.setText(R.id.tv_like, item.getAnswerLikes()+"");

        if (item.getLikeStatus()%2==0){
            helper.setVisible(R.id.tu_like,true);
            helper.setVisible(R.id.tu_no_like,false);
        }else {
            helper.setVisible(R.id.tu_no_like,true);
            helper.setVisible(R.id.tu_like,false);
        }
        //留言数量
        helper.setText(R.id.tv_message, item.getReplyCount()+"");
        helper.addOnClickListener(R.id.ll_zan);

        helper.addOnClickListener(R.id.ll_comment);
    }


    //时间转化
    public static String format(long millis) {
        long delta = System.currentTimeMillis() - millis;
        if (delta < 1L * ONE_MINUTE) {
//            long seconds = toSeconds(delta);
//            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
            return 1 + ONE_MINUTE_AGO;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天";
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        }
        if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
        } else {
            long years = toYears(delta);
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }

    public void getOnItemChildClickListener(int ll_zan) {

    }
}
