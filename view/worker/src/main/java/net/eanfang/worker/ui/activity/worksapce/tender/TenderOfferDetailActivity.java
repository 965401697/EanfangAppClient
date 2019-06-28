package net.eanfang.worker.ui.activity.worksapce.tender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.model.bean.PageBean;
import com.eanfang.biz.model.entity.tender.TaskApplyEntity;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;
import com.eanfang.biz.rds.base.LViewModelProviders;

import net.eanfang.worker.R;
import net.eanfang.worker.databinding.ActivityTenderOfferDetailBinding;
import net.eanfang.worker.ui.adapter.worktender.WorkTenderOfferDetailAdapter;
import net.eanfang.worker.viewmodle.tender.TenderCreateViewModle;
import net.eanfang.worker.viewmodle.tender.TenderOfferDetailViewModle;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author guanluocang
 * @data 2019/6/26
 * @description 评标详情
 */

public class TenderOfferDetailActivity extends BaseActivity {

    private ActivityTenderOfferDetailBinding tenderOfferDetailBinding;
    @Setter
    @Accessors(chain = true)
    private TenderOfferDetailViewModle tenderOfferDetailViewModle;

    public WorkTenderOfferDetailAdapter workTenderOfferDetailAdapter;

    private TaskPublishEntity taskPublishEntity;
    /**
     *
     */
    private boolean isOffing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        tenderOfferDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_tender_offer_detail);
        super.onCreate(savedInstanceState);
    }


    @Override
    protected ViewModel initViewModel() {
        tenderOfferDetailViewModle = LViewModelProviders.of(this, TenderOfferDetailViewModle.class);
        tenderOfferDetailBinding.setTenderOfferDetailVm(tenderOfferDetailViewModle);
        tenderOfferDetailViewModle.setTenderOfferDetailBinding(tenderOfferDetailBinding);
        tenderOfferDetailViewModle.getOfferDetailLiveData().observe(this, this::getData);
        return tenderOfferDetailViewModle;
    }

    @Override
    protected void initView() {
        isOffing = getIntent().getBooleanExtra("offing", false);
        taskPublishEntity = (TaskPublishEntity) getIntent().getSerializableExtra("tenderDetail");
        setTitle(isOffing ? "评标" : "评标历史");
        setLeftBack(true);
        tenderOfferDetailBinding.rvOffer.setLayoutManager(new LinearLayoutManager(this));
        tenderOfferDetailBinding.rvOffer.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        workTenderOfferDetailAdapter = new WorkTenderOfferDetailAdapter(TenderOfferDetailActivity.this);
        workTenderOfferDetailAdapter.bindToRecyclerView(tenderOfferDetailBinding.rvOffer);
        tenderOfferDetailViewModle.doGetData(taskPublishEntity);
        initListener();
    }

    private void initListener() {
        workTenderOfferDetailAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_ignore:
                    tenderOfferDetailViewModle.doChangeOffer(workTenderOfferDetailAdapter.getData().get(position).getId(), workTenderOfferDetailAdapter.getData().get(position).getShopTaskPublishId(), 1);
                    break;
                case R.id.tv_select:
                    tenderOfferDetailViewModle.doChangeOffer(workTenderOfferDetailAdapter.getData().get(position).getId(), workTenderOfferDetailAdapter.getData().get(position).getShopTaskPublishId(), 3);
                    break;
                default:
                    break;
            }
        });
    }

    private void getData(PageBean<TaskApplyEntity> taskApplyEntityPageBean) {
        // 如果是3 就是中标人
        if (taskApplyEntityPageBean.getList().size() > 0 && taskApplyEntityPageBean.getList().get(0).getStatus() == 3) {
            tenderOfferDetailBinding.rlWin.setVisibility(View.VISIBLE);
            tenderOfferDetailViewModle.doSetWinData(taskApplyEntityPageBean.getList().get(0));
        }
        tenderOfferDetailBinding.tvOfferNum.setText("(" + taskApplyEntityPageBean.getList().size() + ")");
        workTenderOfferDetailAdapter.getData().clear();
        workTenderOfferDetailAdapter.setNewData(taskApplyEntityPageBean.getList());
    }

}
