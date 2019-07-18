package net.eanfang.worker.viewmodle.tender;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.base.BaseApplication;
import com.eanfang.biz.model.bean.PageBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.tender.TaskApplyEntity;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.tender.TenderDs;
import com.eanfang.biz.rds.sys.repo.tender.TenderRepo;

import net.eanfang.worker.databinding.ActivityTenderPersonControlBinding;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/6/22
 * @description 个人中心
 */
public class TenderPersonControlViewModle extends BaseViewModel {

    private TenderRepo tenderRepo;


    @Getter
    private MutableLiveData<PageBean<TaskPublishEntity>> myReleaseTenderData;
    @Getter
    private MutableLiveData<TaskPublishEntity> myCloseReleaseTenderData;

    @Getter
    private MutableLiveData<PageBean<TaskApplyEntity>> myBidTenderLiveData;

    @Getter
    @Setter
    private ActivityTenderPersonControlBinding tenderPersonControlBinding;


    public QueryEntry mReleaseQueryEntry;
    public int mReleasePage = 1;

    public QueryEntry mBidQueryEntry;
    public int mBidPage = 1;

    public TenderPersonControlViewModle() {
        myReleaseTenderData = new MutableLiveData<>();
        myBidTenderLiveData = new MutableLiveData<>();
        myCloseReleaseTenderData = new MutableLiveData<>();
        tenderRepo = new TenderRepo(new TenderDs(this));
    }

    /**
     * 我招标的
     */
    public void getBidData(int page) {
        if (mBidQueryEntry == null) {
            mBidQueryEntry = new QueryEntry();
        }
        this.mBidPage = page;
        //正在公告 0   已过期 1
        mBidQueryEntry.getEquals().put("createUserId", BaseApplication.get().getAccount().getDefaultUser().getUserId() + "");
        mBidQueryEntry.setPage(page);
        tenderRepo.doGetTenderBidList(mBidQueryEntry).observe(lifecycleOwner, tenderBean -> {
            myBidTenderLiveData.setValue(tenderBean);
        });
    }

    /**
     * 我发布的
     */
    public void getReleaseData(int page) {
        if (mReleaseQueryEntry == null) {
            mReleaseQueryEntry = new QueryEntry();
        }
        this.mReleasePage = page;
        //正在公告 0   已过期 1
        mReleaseQueryEntry.setPage(page);
        tenderRepo.doGetTenderRelaeseList(mReleaseQueryEntry).observe(lifecycleOwner, tenderBean -> {
            myReleaseTenderData.setValue(tenderBean);
        });
    }

    /**
     * 关闭我发布的工单
     */
    public void doCloseMyReleaseTender(TaskPublishEntity taskPublishEntity) {
        TaskPublishEntity mTaskPublishEntity = new TaskPublishEntity();
        mTaskPublishEntity.setId(taskPublishEntity.getId());
        mTaskPublishEntity.setPublishStatus(0);
        tenderRepo.doCloseReleaseTender(mTaskPublishEntity).observe(lifecycleOwner, tenderBean -> {
            myCloseReleaseTenderData.setValue(tenderBean);
        });
    }
}
