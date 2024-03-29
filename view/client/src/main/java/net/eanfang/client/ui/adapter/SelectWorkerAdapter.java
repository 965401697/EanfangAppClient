package net.eanfang.client.ui.adapter;

import android.net.Uri;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.entity.SelectWorkEntitity;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;

import cn.hutool.core.util.StrUtil;

import static com.eanfang.base.kit.V.v;

/**
 * Created by admin on 2018/4/27.
 */

public class SelectWorkerAdapter extends BaseQuickAdapter<SelectWorkEntitity.ListBean, BaseViewHolder> {


    private boolean mIsFromHome = false;

    public SelectWorkerAdapter(boolean isFromHome) {
        super(R.layout.item_collection_worker);
        this.mIsFromHome = isFromHome;
    }

    @Override
    protected void convert(BaseViewHolder helper, SelectWorkEntitity.ListBean item) {
        // 从首页显示 报修 和报装 按钮
        if (mIsFromHome) {
            helper.setGone(R.id.tv_install, true);
            helper.setGone(R.id.tv_repair, true);
        } else {
            helper.setGone(R.id.tv_install, false);
            helper.setGone(R.id.tv_repair, false);

        }
        // 头像
        if (!StrUtil.isEmpty(item.getVerifyEntity().getAvatarPhoto())) {
            GlideUtil.intoImageView(mContext, Uri.parse(BuildConfig.OSS_SERVER + item.getVerifyEntity().getAvatarPhoto()), helper.getView(R.id.iv_header));
        }
        // 公司名称
        helper.setText(R.id.tv_companyName, item.getCompanyEntity().getOrgName());
        // 工作年限
        helper.setText(R.id.tv_workTime, GetConstDataUtils.getWorkingYearList().get(item.getVerifyEntity().getWorkingYear()));
        //姓名
        helper.setText(R.id.tv_name, item.getAccountEntity().getRealName());
        // 维修
        helper.setText(R.id.tv_repair_count, item.getRepairCount() + "");
        // 施工
        helper.setText(R.id.tv_construction_count, item.getInstallNum() + "");
        //评价
        helper.setText(R.id.tv_evaluate_count, item.getEvaluateNum() + "");
        int consultCount = item.getDesignNum() + item.getInstallNum() + item.getRepairCount();
        // 咨询
        helper.setText(R.id.tv_consultation_count, consultCount + "");
        if (item.getPublicPraise() != 0) {
            // 口碑
            helper.setText(R.id.tv_koubei, item.getPublicPraise() + "分");
        }
        if (item.getGoodRate() != 0) {
            // 好评率
            java.text.NumberFormat percentFormat = java.text.NumberFormat.getPercentInstance();

            //自动转换成百分比显示..
            helper.setText(R.id.tv_haopinglv, (SplitAndRound((item.getGoodRate() * 0.01), 2) + "%"));
        } else {
            helper.setText(R.id.tv_haopinglv, "0");
        }
        // 认证
        if (v(() -> item.getVerifyEntity().getStatus()) != null && item.getVerifyEntity().getStatus() == 2) {
            helper.getView(R.id.tv_auth).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.tv_auth).setVisibility(View.GONE);
        }
        //  培训状态 （0否，1是）
        if (v(() -> item.getTrainStatus()) != null && item.getTrainStatus() == 0) {
            helper.getView(R.id.tv_train).setVisibility(View.GONE);
        } else if (v(() -> item.getTrainStatus()) != null && item.getTrainStatus() == 1) {
            helper.getView(R.id.tv_train).setVisibility(View.VISIBLE);
        }
        // 资质  0否，1是
        if (v(() -> item.getQualification()) != null && item.getQualification() == 0) {
            helper.getView(R.id.tv_qualification).setVisibility(View.GONE);
        } else if (v(() -> item.getQualification()) != null && item.getQualification() == 1) {
            helper.getView(R.id.tv_qualification).setVisibility(View.VISIBLE);
        }
        helper.addOnClickListener(R.id.tv_install);
        helper.addOnClickListener(R.id.tv_repair);
    }

    /**
     * 保留几位小数
     *
     * @param a
     * @param n
     * @return
     */
    public double SplitAndRound(double a, int n) {
        a = a * Math.pow(10, n);
        return (Math.round(a)) / (Math.pow(10, n));
    }

}
