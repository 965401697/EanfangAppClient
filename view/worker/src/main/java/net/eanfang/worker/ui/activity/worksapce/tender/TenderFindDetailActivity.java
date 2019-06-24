package net.eanfang.worker.ui.activity.worksapce.tender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import android.net.Uri;
import android.os.Bundle;

import com.eanfang.BuildConfig;
import com.eanfang.base.BaseActivity;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.config.Config;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.JumpItent;

import net.eanfang.worker.R;
import net.eanfang.worker.databinding.ActivityTenderFindDetailBinding;
import net.eanfang.worker.viewmodle.tender.TenderFindDetailViewModle;
import net.eanfang.worker.viewmodle.tender.TenderViewModle;

import lombok.Getter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        tenderFindDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_tender_find_detail);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setTitle("用工详情");
        setLeftBack(true);
        tenderFindDetailViewModle.mTendFindId = getIntent().getLongExtra("tendFindId", 0);
        tenderFindDetailViewModle.initData();

        /**
         * 我要报价
         */
        tenderFindDetailBinding.tvOfferTender.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putLong("publishId", getIntent().getLongExtra("tendFindId", 0));
            JumpItent.jump(TenderFindDetailActivity.this, TenderCommitActivity.class, bundle);
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
