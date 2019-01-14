package net.eanfang.client.ui.adapter;

import android.net.Uri;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.model.CollectionWorkerListBean;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;

import static com.eanfang.util.V.v;


/**
 * 我的-收藏的技师的adapter
 * Created by Administrator on 2017/3/15.
 */

public class CollectionWorkerListAdapter extends BaseQuickAdapter<CollectionWorkerListBean.ListBean, BaseViewHolder> {

    public CollectionWorkerListAdapter() {
        super(R.layout.item_collection_worker);
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectionWorkerListBean.ListBean item) {
        SimpleDraweeView iv_header = helper.getView(R.id.iv_header);
        if (!StringUtils.isEmpty(item.getAssigneeUserEntity().getAccountEntity().getAvatar())) {
            iv_header.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + item.getAssigneeUserEntity().getAccountEntity().getAvatar()));
        }
        if (item.getAssigneeCompanyEntity() != null) {
            helper.setText(R.id.tv_companyName, item.getAssigneeCompanyEntity().getOrgName());
        }
        if (item.getAssigneeUserEntity() != null) {
            helper.setText(R.id.tv_name, item.getAssigneeUserEntity().getAccountEntity().getRealName());
        }
        // 工作年限
        helper.setText(R.id.tv_workTime, GetConstDataUtils.getWorkingYearList().get(item.getWorkerEntity().getVerifyEntity().getWorkingYear()));
        if (item.getWorkerEntity() != null) {
            if (item.getWorkerEntity().getPublicPraise() != 0) {
                helper.setText(R.id.tv_koubei, item.getWorkerEntity().getPublicPraise() / 100 + "分");
            }
            if (item.getWorkerEntity().getGoodRate() != 0) {
                helper.setText(R.id.tv_haopinglv, item.getWorkerEntity().getGoodRate() + "%");
            }
        }
        if (item.getWorkerEntity().getPublicPraise() != 0) {
            // 口碑
            helper.setText(R.id.tv_koubei, String.valueOf(item.getWorkerEntity().getPublicPraise()));
        }
        if (item.getWorkerEntity().getGoodRate() != 0) {
            // 好评率
            java.text.NumberFormat percentFormat = java.text.NumberFormat.getPercentInstance();

            //自动转换成百分比显示..
            helper.setText(R.id.tv_haopinglv, (SplitAndRound((Double) (item.getWorkerEntity().getGoodRate() * 0.01), 2) + "%"));
        }
        // 认证
        if (v(() -> item.getWorkerEntity().getVerifyStatus()) != null && item.getWorkerEntity().getVerifyStatus() == 1) {
            helper.getView(R.id.tv_auth).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.tv_auth).setVisibility(View.GONE);
        }
        //  培训状态 （0否，1是）
        if (v(() -> item.getWorkerEntity().getTrainStatus()) != null && item.getWorkerEntity().getTrainStatus() == 0) {
            helper.getView(R.id.tv_train).setVisibility(View.GONE);
        } else if (v(() -> item.getWorkerEntity().getTrainStatus()) != null && item.getWorkerEntity().getTrainStatus() == 1) {
            helper.getView(R.id.tv_train).setVisibility(View.VISIBLE);
        }
        // 资质  0否，1是
        if (v(() -> item.getWorkerEntity().getQualification()) != null && item.getWorkerEntity().getQualification() == 0) {
            helper.getView(R.id.tv_qualification).setVisibility(View.GONE);
        } else if (v(() -> item.getWorkerEntity().getQualification()) != null && item.getWorkerEntity().getQualification() == 1) {
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
