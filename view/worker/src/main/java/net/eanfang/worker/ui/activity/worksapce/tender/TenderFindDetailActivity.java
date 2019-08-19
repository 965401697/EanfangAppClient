package net.eanfang.worker.ui.activity.worksapce.tender;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.util.JumpItent;

import net.eanfang.worker.R;
import net.eanfang.worker.databinding.ActivityTenderFindDetailBinding;
import net.eanfang.worker.viewmodle.tender.TenderFindDetailViewModle;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author guanluocang
 * @data 2019/6/21
 * @description 用工详情
 */

public class TenderFindDetailActivity extends BaseActivity {

    private ActivityTenderFindDetailBinding tenderFindDetailBinding;
    @Setter
    @Accessors(chain = true)
    private TenderFindDetailViewModle tenderFindDetailViewModle;

    private boolean isLookDetail = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        tenderFindDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_tender_find_detail);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("用工详情");
        tenderFindDetailViewModle.mTendFindId = getIntent().getLongExtra("tendFindId", 0);
        tenderFindDetailBinding.rlTime.setVisibility(getIntent().getBooleanExtra("isLookDetail", false) ? View.GONE : View.VISIBLE);
        tenderFindDetailViewModle.initData();

        /**
         * 我要报价
         */
        tenderFindDetailBinding.tvOfferTender.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putLong("publishId", getIntent().getLongExtra("tendFindId", 0));
            JumpItent.jump(TenderFindDetailActivity.this, TenderCommitActivity.class, bundle);
        });
        /**
         * 用户信息
         * */
        tenderFindDetailBinding.llReleaseInfo.setOnClickListener((v) -> {
            tenderFindDetailViewModle.doJumpUserHome();
        });
        setRightClick("分享", (v) -> {
            tenderFindDetailViewModle.doShare();
        });
    }

    @Override
    protected ViewModel initViewModel() {
        tenderFindDetailViewModle = LViewModelProviders.of(this, TenderFindDetailViewModle.class);
        tenderFindDetailBinding.setTenderFindDetailVm(tenderFindDetailViewModle);
        tenderFindDetailViewModle.setTenderFindDetailBinding(tenderFindDetailBinding);
        tenderFindDetailViewModle.getTenderLiveData().observe(this, this::getCommenData);
        return tenderFindDetailViewModle;
    }

    private void getCommenData(TaskPublishEntity taskPublishEntity) {
        tenderFindDetailBinding.setTenderCreateVo(taskPublishEntity);
    }


}
