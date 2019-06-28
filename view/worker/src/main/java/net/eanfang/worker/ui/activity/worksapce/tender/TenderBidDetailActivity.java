package net.eanfang.worker.ui.activity.worksapce.tender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import android.os.Bundle;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.biz.rds.sys.repo.tender.TenderRepo;

import net.eanfang.worker.R;
import net.eanfang.worker.databinding.ActivityTenderBidDetailBinding;
import net.eanfang.worker.viewmodle.tender.TenderBidDetailViewModle;
import net.eanfang.worker.viewmodle.tender.TenderOfferDetailViewModle;

/**
 * @author guanluocang
 * @data 2019/6/27
 * @description 我的招标详情
 */

public class TenderBidDetailActivity extends BaseActivity {

    private Long mId;
    private ActivityTenderBidDetailBinding tenderBidDetailBinding;
    private TenderBidDetailViewModle bidDetailViewModle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        tenderBidDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_tender_bid_detail);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        mId = getIntent().getLongExtra("bidId", 0);
        bidDetailViewModle.doGetData(mId);
    }

    @Override
    protected ViewModel initViewModel() {
        bidDetailViewModle = LViewModelProviders.of(this, TenderBidDetailViewModle.class);
        tenderBidDetailBinding.setTenderBidDetailVm(bidDetailViewModle);
        bidDetailViewModle.setTenderBidDetailBinding(tenderBidDetailBinding);
        return bidDetailViewModle;
    }
}
