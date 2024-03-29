package net.eanfang.worker.viewmodle.tender;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.BuildConfig;
import com.eanfang.biz.model.PageBean;
import com.eanfang.biz.model.entity.WorkerEntity;
import com.eanfang.biz.model.entity.tender.TaskApplyEntity;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.tender.TenderDs;
import com.eanfang.biz.rds.sys.repo.TenderRepo;
import com.eanfang.config.Config;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.JumpItent;

import net.eanfang.worker.databinding.ActivityTenderOfferDetailBinding;
import net.eanfang.worker.ui.activity.worksapce.WorkDetailActivity;
import net.eanfang.worker.ui.activity.worksapce.tender.TenderFindDetailActivity;
import net.eanfang.worker.ui.activity.worksapce.tender.TenderOfferDetailActivity;

import cn.hutool.core.date.DateUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/6/26
 * @description 评标详情
 */
public class TenderOfferDetailViewModle extends BaseViewModel {

    private TenderRepo tenderRepo;
    @Setter
    @Getter
    private ActivityTenderOfferDetailBinding tenderOfferDetailBinding;

    @Getter
    private MutableLiveData<PageBean<TaskApplyEntity>> offerDetailLiveData;

    private Long mOfferId;
    private TaskApplyEntity mTaskApplyEntity;

    public TenderOfferDetailViewModle() {
        offerDetailLiveData = new MutableLiveData<>();
        tenderRepo = new TenderRepo(new TenderDs(this));
    }

    /**
     * 获取数据
     *
     * @param taskPublishEntity
     */
    public void doGetData(TaskPublishEntity taskPublishEntity) {
        doSetTopData(taskPublishEntity);
        mOfferId = taskPublishEntity.getId();
        tenderRepo.doGetTenderOfferDetail(mOfferId + "").observe(lifecycleOwner, val -> {
            offerDetailLiveData.setValue(val);
        });
    }

    public void doSetTopData(TaskPublishEntity taskPublishEntity) {
        //名称
        tenderOfferDetailBinding.tvTenderName.setText(Config.get().getBusinessNameByCode(taskPublishEntity.getSystemType(), 1));
        //类型
        tenderOfferDetailBinding.tvTenderType.setText(Config.get().getBaseNameByCode(taskPublishEntity.getBusinessOneCode(), 2));
        // 时间
        tenderOfferDetailBinding.tvTenderTime.setText(DateUtil.date(taskPublishEntity.getCreateTime()).toString());
        //地点
        tenderOfferDetailBinding.tvTenderAddress.setText(taskPublishEntity.getProvince() + taskPublishEntity.getCity() + taskPublishEntity.getCounty());
        //工期
        tenderOfferDetailBinding.tvTenderLimitTime.setText(taskPublishEntity.getPredicttime() + "天");
        // 预算
        tenderOfferDetailBinding.tvTenderBudget.setText(taskPublishEntity.getBudget() + "元" + "/" + taskPublishEntity.getBudgetUnit());
    }


    /**
     * 忽略 ：1  中标选他 ：3
     */
    public void doChangeOffer(Long id, Long shopTaskPublishId, int status) {
        TaskApplyEntity taskApplyEntity = new TaskApplyEntity();
        taskApplyEntity.setId(id);
        taskApplyEntity.setShopTaskPublishId(shopTaskPublishId);
        taskApplyEntity.setStatus(status);
        tenderRepo.doChangeOfferStatus(taskApplyEntity).observe(lifecycleOwner, val -> {
            tenderRepo.doGetTenderOfferDetail(mOfferId + "").observe(lifecycleOwner, bean -> {
                offerDetailLiveData.setValue(bean);
            });
        });
    }

    /**
     * 中标人信息
     */
    public void doSetWinData(TaskApplyEntity taskApplyEntity) {
        mTaskApplyEntity = taskApplyEntity;
        //头像
        GlideUtil.intoImageView(tenderOfferDetailBinding.getRoot().getContext(), Uri.parse(BuildConfig.OSS_SERVER + taskApplyEntity.getUserEntity().getAccountEntity().getAvatar()), tenderOfferDetailBinding.ivHeader);
        //姓名
        tenderOfferDetailBinding.tvName.setText(taskApplyEntity.getApplyContacts());
        //是否认证
        if (taskApplyEntity.getVerifyStatus() == 1) {
            tenderOfferDetailBinding.ivVerifyStatus.setVisibility(View.VISIBLE);
        } else {
            tenderOfferDetailBinding.ivVerifyStatus.setVisibility(View.GONE);
        }
        // 公司
        tenderOfferDetailBinding.tvCompany.setText(taskApplyEntity.getApplyCompanyName());
        // 预算金额
        tenderOfferDetailBinding.tvBudget.setText(taskApplyEntity.getProjectQuote() + "元/" + taskApplyEntity.getBudgetUnit());
        // 施工方案
        tenderOfferDetailBinding.tvDescription.setText(taskApplyEntity.getDescription());
    }

    /**
     * 查看技师详情
     */
    public void doJumpWorkDetail() {
        WorkerEntity workerEntity = new WorkerEntity();
        workerEntity.setId(mTaskApplyEntity.getWorkerEntity().getId());
        workerEntity.setCompanyUserId(mTaskApplyEntity.getCreateUserId());
        Bundle bundle = new Bundle();
        bundle.putSerializable("workEntriy", workerEntity);
        JumpItent.jump((TenderOfferDetailActivity) tenderOfferDetailBinding.getRoot().getContext(), WorkDetailActivity.class, bundle);
    }

    /**
     * 查看用工详情
     */
    public void doJumpOfferDetail() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("tendFindId", mOfferId);
        bundle.putBoolean("isLookDetail", true);
        JumpItent.jump((TenderOfferDetailActivity) tenderOfferDetailBinding.getRoot().getContext(), TenderFindDetailActivity.class, bundle);
    }
}
