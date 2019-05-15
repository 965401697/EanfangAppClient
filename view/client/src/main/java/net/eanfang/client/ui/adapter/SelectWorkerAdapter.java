package net.eanfang.client.ui.adapter;

import android.net.Uri;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.WorkerEntity;

import net.eanfang.client.R;

import static com.eanfang.util.V.v;

/**
 * Created by admin on 2018/4/27.
 */

public class SelectWorkerAdapter extends BaseQuickAdapter<WorkerEntity, BaseViewHolder> {


    public SelectWorkerAdapter() {
        super(R.layout.item_collection_worker);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkerEntity item) {
        // 头像
        SimpleDraweeView iv_header = helper.getView(R.id.iv_header);
        if (!StringUtils.isEmpty(item.getVerifyEntity().getAvatarPhoto())) {
            iv_header.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + item.getVerifyEntity().getAvatarPhoto()));
        }
        // 公司名称
        helper.setText(R.id.tv_companyName, item.getCompanyEntity().getOrgName());
        // 工作年限
        helper.setText(R.id.tv_workTime, GetConstDataUtils.getWorkingYearList().get(item.getVerifyEntity().getWorkingYear()));
        //姓名
        helper.setText(R.id.tv_name, item.getAccountEntity().getRealName());
        if (item.getPublicPraise() != 0) {
            // 口碑
            helper.setText(R.id.tv_koubei, String.valueOf(item.getPublicPraise()));
        }
        if (item.getGoodRate() != 0) {
            // 好评率
            java.text.NumberFormat percentFormat = java.text.NumberFormat.getPercentInstance();

            //自动转换成百分比显示..
            helper.setText(R.id.tv_haopinglv, (SplitAndRound((item.getGoodRate() * 0.01), 2) + "%"));
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