package net.eanfang.worker.viewmodle.tender;

import android.net.Uri;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.BuildConfig;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.tender.TenderDs;
import com.eanfang.biz.rds.sys.repo.tender.TenderRepo;
import com.eanfang.config.Config;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.StringUtils;

import net.eanfang.worker.databinding.ActivityTenderFindDetailBinding;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/6/21
 * @description
 */
public class TenderFindDetailViewModle extends BaseViewModel {

    public Long mTendFindId;
    private TenderRepo tenderRepo;
    @Getter
    private MutableLiveData<TaskPublishEntity> tenderLiveData;
    @Setter
    @Getter
    private ActivityTenderFindDetailBinding tenderFindDetailBinding;


    public TenderFindDetailViewModle() {
        tenderLiveData = new MutableLiveData<>();
        tenderRepo = new TenderRepo(new TenderDs(this));
    }

    public void initData() {
        tenderRepo.doGetTenderDetail(mTendFindId.toString()).observe(lifecycleOwner, tenderBean -> {
            tenderLiveData.setValue(tenderBean);
            // 发布时间
            tenderFindDetailBinding.tvTime.setText(GetDateUtils.dateToDateTimeString(tenderBean.getCreateTime()));
            // 头像
            GlideUtil.intoImageView(tenderFindDetailBinding.getRoot().getContext(), Uri.parse(BuildConfig.OSS_SERVER + tenderBean.getUserEntity().getAccountEntity().getAvatar()), tenderFindDetailBinding.ivHeader);
            //是否认证
            if (tenderBean.getWorkerEntity().getVerifyStatus() == 1) {
                tenderFindDetailBinding.ivVerifyStatus.setVisibility(View.VISIBLE);
            } else {
                tenderFindDetailBinding.ivVerifyStatus.setVisibility(View.GONE);
            }
            // 项目地址
            tenderFindDetailBinding.tvProjectAddress.setText(tenderBean.getProvince() + tenderBean.getCity() + tenderBean.getCounty() + tenderBean.getDetailPlace());
            // 业务类型
            tenderFindDetailBinding.tvBussnessType.setText(Config.get().getBaseNameByCode(tenderBean.getBusinessOneCode(), 2));
            // 系统类别
            tenderFindDetailBinding.tvSystemType.setText(Config.get().getBaseNameByCode(tenderBean.getSystemType(), 1));
            //开始时间
            tenderFindDetailBinding.tvStartTime.setText(GetDateUtils.dateToDateString(tenderBean.getEndTime()));
            //预算
            tenderFindDetailBinding.tvBudget.setText(tenderBean.getBudget() + "元/" + tenderBean.getBudgetUnit());
            // 天  时  分
            String endTime = GetDateUtils.dateToDateTimeString(tenderBean.getEndTime());
            if (!StringUtils.isEmpty(endTime)) {
                //剩余时间
                long currentTime = System.currentTimeMillis() / 1000;
                long remainTime = GetDateUtils.convertDateToSecond(endTime) - currentTime;
                if (remainTime > 0) {
                    int oneDay = 24 * 60 * 60;
                    int day = (int) (remainTime / oneDay);
                    int oneHour = 60 * 60;
                    int hour = (int) ((remainTime % oneDay) / oneHour);
                    int oneMin = 60;
                    int min = (int) (((remainTime % oneDay) % oneHour)) / oneMin;
                    tenderFindDetailBinding.tvDay.setText(String.valueOf(day));
                    tenderFindDetailBinding.tvHour.setText(String.valueOf(hour));
                    tenderFindDetailBinding.tvMinute.setText(String.valueOf(min));
                }
            }

        });
    }

}
