package net.eanfang.client.ui.adapter;

import android.graphics.Color;
import android.os.Build;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.biz.model.security.HomeCompanyBean;
import com.eanfang.config.Config;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;

/**
 * @author liangkailun
 * Date ：2019-06-18
 * Describe :首页安防公司、找技师adapter
 */
public class FragmentHomeCompanyAdapter extends BaseQuickAdapter<HomeCompanyBean, FragmentHomeCompanyAdapter.FragmentHomeCompanyViewHolder> {

    public FragmentHomeCompanyAdapter() {
        super(R.layout.item_home_company);
    }

    @Override
    protected void convert(FragmentHomeCompanyViewHolder helper, HomeCompanyBean item) {
        if (item.getPageType() == 0) {
            helper.imgRz.setImageResource(R.drawable.icon_company_rz);
            helper.llHomeCompany.setVisibility(View.VISIBLE);
            helper.tvHomeCompanyWorkerYear.setVisibility(View.INVISIBLE);
            helper.tvHomeCompanyStatus.setVisibility(item.getStatus() == 2 ? View.VISIBLE : View.GONE);
            helper.imgRz.setVisibility(item.getStatus() == 2 ? View.VISIBLE : View.GONE);
            helper.tvHomeCompanyStatus.setText("已认证");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                helper.tvHomeCompanyStatus.setTextColor(mContext.getColor(R.color.color_home_company_rz));
            }
        } else {
            helper.imgRz.setImageResource(R.drawable.icon_worker_level);
            helper.llHomeCompany.setVisibility(View.GONE);
            helper.tvHomeCompanyWorkerYear.setVisibility(View.VISIBLE);
            helper.tvHomeCompanyWorkerYear.setText(mContext.getString(R.string.text_home_company_worker_year, GetConstDataUtils.getWorkingYearList().get(item.getPractitionerYears())));
            helper.tvHomeCompanyStatus.setVisibility(View.VISIBLE);
            helper.tvHomeCompanyStatus.setText(mContext.getString(R.string.text_home_company_level, item.getLevel() + 1));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                helper.tvHomeCompanyStatus.setTextColor(mContext.getColor(R.color.color_home_company_level));
            }
        }
        helper.tvHomeCompanyName.setText(item.getName());
        GlideUtil.intoImageView(mContext, BuildConfig.OSS_SERVER + item.getLogoPic(), helper.imgHomeCompanyLogo);
        helper.tvHomeCompanyAddress.setText(Config.get().getAddressByCode(item.getAreaCode()));

        helper.tvHomeCompanyDesign.setText(toSpanString(mContext.getString(R.string.text_home_company_designCount, item.getDesignCount())));
        helper.tvHomeCompanyInstall.setText(toSpanString(mContext.getString(R.string.text_home_company_installCount, item.getInstallCount())));
        helper.tvHomeCompanyRepair.setText(toSpanString(mContext.getString(R.string.text_home_company_repairCount, item.getRepairCount())));
        double goodRate = (double) item.getGoodRate() / 100;
        helper.tvHomeCompanyRate.setText(toSpanString(mContext.getString(R.string.text_home_company_goodRateCount, goodRate + "")));
        int consultCount = item.getDesignCount() + item.getInstallCount() + item.getRepairCount();
        helper.tvHomeCompanyConsult.setText(mContext.getString(R.string.text_home_company_consultCount, consultCount));

    }

    private SpannableString toSpanString(String text) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#666666")), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    class FragmentHomeCompanyViewHolder extends BaseViewHolder {
        private ImageView imgRz;
        private ImageView imgHomeCompanyLogo;
        private TextView tvHomeCompanyName;
        private TextView tvHomeCompanyDesign;
        private TextView tvHomeCompanyRate;
        private TextView tvHomeCompanyInstall;
        private TextView tvHomeCompanyRepair;
        private TextView tvHomeCompanyAddress;
        private TextView tvHomeCompanyConsult;
        private TextView tvHomeCompanyStatus;
        private TextView tvHomeCompanyWorkerYear;
        private LinearLayout llHomeCompany;


        public FragmentHomeCompanyViewHolder(View view) {
            super(view);
            imgRz = view.findViewById(R.id.img_rz);
            imgHomeCompanyLogo = view.findViewById(R.id.img_home_company_logo);
            tvHomeCompanyName = view.findViewById(R.id.tv_home_company_name);
            tvHomeCompanyDesign = view.findViewById(R.id.tv_home_company_design);
            tvHomeCompanyRate = view.findViewById(R.id.tv_home_company_rate);
            tvHomeCompanyInstall = view.findViewById(R.id.tv_home_company_install);
            tvHomeCompanyRepair = view.findViewById(R.id.tv_home_company_repair);
            tvHomeCompanyAddress = view.findViewById(R.id.tv_home_company_address);
            tvHomeCompanyConsult = view.findViewById(R.id.tv_home_company_consult);
            tvHomeCompanyStatus = view.findViewById(R.id.tv_home_company_status);
            tvHomeCompanyWorkerYear = view.findViewById(R.id.tv_home_company_worker_year);
            llHomeCompany = view.findViewById(R.id.ll_home_company);
            addOnClickListener(R.id.btn_home_company_repair);
            addOnClickListener(R.id.btn_home_company_install);
        }
    }
}
