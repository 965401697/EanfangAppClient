package net.eanfang.worker.viewmodle.tender;

import android.net.Uri;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.BuildConfig;
import com.eanfang.base.BaseApplication;
import com.eanfang.biz.model.bean.PageBean;
import com.eanfang.biz.model.entity.tender.TaskApplyEntity;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.tender.TenderDs;
import com.eanfang.biz.rds.sys.repo.tender.TenderRepo;
import com.eanfang.config.Config;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.GlideUtil;

import net.eanfang.worker.databinding.ActivityTenderBidDetailBinding;
import net.eanfang.worker.databinding.ActivityTenderOfferDetailBinding;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/6/26
 * @description 招标详情
 */
public class TenderBidDetailViewModle extends BaseViewModel {

    private TenderRepo tenderRepo;
    @Setter
    @Getter
    private ActivityTenderBidDetailBinding tenderBidDetailBinding;

    public TenderBidDetailViewModle() {
        tenderRepo = new TenderRepo(new TenderDs(this));
    }

    /**
     * 获取数据
     *
     * @param mId
     */
    public void doGetData(Long mId) {
        tenderRepo.doGetTenderBidDetail(mId + "").observe(lifecycleOwner, val -> {
            doSetTopData(val);
        });
    }

    public void doSetTopData(TaskApplyEntity taskApplyEntity) {
        //名称
        tenderBidDetailBinding.tvTenderName.setText(Config.get().getBusinessNameByCode(taskApplyEntity.getTaskPublishEntity().getSystemType(), 1));
        //类型
        tenderBidDetailBinding.tvTenderType.setText(Config.get().getBaseNameByCode(taskApplyEntity.getTaskPublishEntity().getBusinessOneCode(), 2));
        // 时间
        tenderBidDetailBinding.tvTenderTime.setText(GetDateUtils.dateToDateTimeString(taskApplyEntity.getCreateTime()));
        //地点
        tenderBidDetailBinding.tvTenderAddress.setText(taskApplyEntity.getTaskPublishEntity().getProvince() + taskApplyEntity.getTaskPublishEntity().getCity() + taskApplyEntity.getTaskPublishEntity().getCounty());
        //工期
        tenderBidDetailBinding.tvTenderLimitTime.setText(taskApplyEntity.getTaskPublishEntity().getPredicttime() + "天");
        // 预算
        tenderBidDetailBinding.tvTenderBudget.setText(taskApplyEntity.getTaskPublishEntity().getBudget() + "元" + "/" + taskApplyEntity.getBudgetUnit());
        //头像
        GlideUtil.intoImageView(tenderBidDetailBinding.getRoot().getContext(), Uri.parse(BuildConfig.OSS_SERVER + BaseApplication.get().getAccount().getAvatar()), tenderBidDetailBinding.ivHeader);
        //姓名
        tenderBidDetailBinding.tvName.setText(taskApplyEntity.getApplyContacts());
        //是否认证
        if (taskApplyEntity.getVerifyStatus() == 1) {
            tenderBidDetailBinding.ivVerifyStatus.setVisibility(View.VISIBLE);
        } else {
            tenderBidDetailBinding.ivVerifyStatus.setVisibility(View.GONE);
        }
        // 公司
        tenderBidDetailBinding.tvCompany.setText(taskApplyEntity.getApplyCompanyName());
        // tenderBidDetailBinding
        tenderBidDetailBinding.tvBudget.setText(taskApplyEntity.getProjectQuote() + "元/" + taskApplyEntity.getBudgetUnit());
        // 施工方案
        tenderBidDetailBinding.tvPlan.setText(taskApplyEntity.getDescription());
        // 附件
    }

}
