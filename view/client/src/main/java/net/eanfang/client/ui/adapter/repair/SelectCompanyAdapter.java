package net.eanfang.client.ui.adapter.repair;

import android.graphics.Color;
import android.os.Build;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.bean.CompanyBean;
import com.eanfang.config.Config;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;

/**
 * @author guanluocang
 * @data 2019/7/20
 * @description 找安防公司
 */
public class SelectCompanyAdapter extends BaseQuickAdapter<CompanyBean.ListBean, BaseViewHolder> {

    public SelectCompanyAdapter() {
        super(R.layout.item_home_company);
    }

    @Override
    protected void convert(BaseViewHolder helper, CompanyBean.ListBean item) {
        helper.setImageResource(R.id.img_rz, R.drawable.icon_company_rz);
        helper.setGone(R.id.tv_home_company_status, item.getCompanyEntity().getStatus() == 2);
        helper.setGone(R.id.img_rz, item.getCompanyEntity().getStatus() == 2);
        helper.setText(R.id.tv_home_company_status, "已认证");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            helper.setTextColor(R.id.tv_home_company_status, mContext.getColor(R.color.color_home_company_rz));
        }
        helper.setText(R.id.tv_home_company_name, item.getCompanyEntity().getName());
        GlideUtil.intoImageView(mContext, BuildConfig.OSS_SERVER + item.getCompanyEntity().getLogoPic(), helper.getView(R.id.img_home_company_logo));
        helper.setText(R.id.tv_home_company_address, Config.get().getAddressByCode(item.getCompanyEntity().getAreaCode()));

        helper.setText(R.id.tv_home_company_design, toSpanString(mContext.getString(R.string.text_home_company_designCount, item.getDesignCount())));
        helper.setText(R.id.tv_home_company_install, toSpanString(mContext.getString(R.string.text_home_company_installCount, item.getInstallCount())));
        helper.setText(R.id.tv_home_company_repair, toSpanString(mContext.getString(R.string.text_home_company_repairCount, item.getRepairCount())));
        double goodRate = (double) item.getGoodRate() / 100;
        helper.setText(R.id.tv_home_company_rate, toSpanString(mContext.getString(R.string.text_home_company_goodRateCount, goodRate + "")));
        int consultCount = item.getDesignCount() + item.getInstallCount() + item.getRepairCount();
        helper.setText(R.id.tv_home_company_consult, mContext.getString(R.string.text_home_company_consultCount, consultCount));

    }

    private SpannableString toSpanString(String text) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#666666")), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

}
