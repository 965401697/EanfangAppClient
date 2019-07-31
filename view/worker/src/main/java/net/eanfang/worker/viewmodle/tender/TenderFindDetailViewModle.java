package net.eanfang.worker.viewmodle.tender;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.BuildConfig;
import com.eanfang.base.kit.picture.picture.PictureRecycleView;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.tender.TenderDs;
import com.eanfang.biz.rds.sys.repo.tender.TenderRepo;
import com.eanfang.config.Config;
import com.eanfang.util.DateKit;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.StringUtils;
import com.luck.picture.lib.entity.LocalMedia;

import net.eanfang.worker.databinding.ActivityTenderFindDetailBinding;
import net.eanfang.worker.ui.activity.im.SelectIMContactActivity;
import net.eanfang.worker.ui.activity.my.UserHomeActivity;
import net.eanfang.worker.ui.activity.worksapce.tender.TenderFindDetailActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
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

    private List<LocalMedia> mPicList = new ArrayList<>();
    public PictureRecycleView.ImageListener listener = list -> mPicList = list;

    public TenderFindDetailViewModle() {
        tenderLiveData = new MutableLiveData<>();
        tenderRepo = new TenderRepo(new TenderDs(this));
    }

    public void initData() {
        tenderRepo.doGetTenderDetail(mTendFindId.toString()).observe(lifecycleOwner, tenderBean -> {
            tenderLiveData.setValue(tenderBean);
            // 发布时间
            tenderFindDetailBinding.tvTime.setText(DateUtil.date(tenderBean.getCreateTime()).toString());
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
            tenderFindDetailBinding.tvStartTime.setText(DateUtil.date(tenderBean.getEndTime()).toDateStr());
            //预算
            tenderFindDetailBinding.tvBudget.setText(tenderBean.getBudget() + "元/" + tenderBean.getBudgetUnit());
            // 附件
            mPicList = tenderFindDetailBinding.rvSelectPic.setData(tenderBean.getPictures());
            tenderFindDetailBinding.rvSelectPic.showImagev(mPicList, listener);
            tenderFindDetailBinding.rvSelectPic.isShow(false, mPicList);

            // 天  时  分
            String endTime = DateUtil.date(tenderBean.getEndTime()).toString();

            if (!StrUtil.isEmpty(endTime)) {
                if (DateUtil.parse(endTime).getTime() - DateUtil.date().getTime() > -0) {
                    int day = (int) DateUtil.date().between(DateUtil.parse(endTime), DateUnit.DAY);
                    int hour = (int) DateUtil.date().between(DateKit.get(endTime).offset(DateField.DAY_OF_YEAR, -day).date, DateUnit.HOUR);
                    int min = (int) DateUtil.date().between(DateKit.get(endTime).offset(DateField.DAY_OF_YEAR, -day).offset(DateField.HOUR, -hour).date, DateUnit.MINUTE);
                    tenderFindDetailBinding.tvDay.setText(String.valueOf(day));
                    tenderFindDetailBinding.tvHour.setText(String.valueOf(hour));
                    tenderFindDetailBinding.tvMinute.setText(String.valueOf(min));
                }

//                //剩余时间
//                long currentTime = System.currentTimeMillis() / 1000;
//                long remainTime = DateUtil.parse(endTime).getTime() - currentTime;
//                if (remainTime > 0) {
//                    int oneDay = 24 * 60 * 60;
//                    int day = (int) (remainTime / oneDay);
//                    int oneHour = 60 * 60;
//                    int hour = (int) ((remainTime % oneDay) / oneHour);
//                    int oneMin = 60;
//                    int min = (int) (((remainTime % oneDay) % oneHour)) / oneMin;
//                    tenderFindDetailBinding.tvDay.setText(String.valueOf(day));
//                    tenderFindDetailBinding.tvHour.setText(String.valueOf(hour));
//                    tenderFindDetailBinding.tvMinute.setText(String.valueOf(min));
//                }
            }

        });
    }

    /**
     * 跳转用户主页
     */
    public void doJumpUserHome() {
        UserHomeActivity.startActivityForUid((TenderFindDetailActivity) tenderFindDetailBinding.getRoot().getContext(), tenderLiveData.getValue().getUserEntity().getUserId());
    }

    /**
     * 分享
     */
    public void doShare() {
        Intent intent = new Intent(tenderFindDetailBinding.getRoot().getContext(), SelectIMContactActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("id", String.valueOf(tenderLiveData.getValue().getId()));
        bundle.putString("orderNum", tenderLiveData.getValue().getPublishCompanyName());
        if (!StrUtil.isEmpty(tenderLiveData.getValue().getPictures())) {
            bundle.putString("picUrl", tenderLiveData.getValue().getPictures().split(",")[0]);
        }
        bundle.putString("creatTime", tenderLiveData.getValue().getLaborRequirements());
        bundle.putString("workerName", tenderLiveData.getValue().getContacts());
        bundle.putString("shareType", "11");
        intent.putExtras(bundle);
        tenderFindDetailBinding.getRoot().getContext().startActivity(intent);
    }
}
