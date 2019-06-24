package net.eanfang.worker.viewmodle.tender;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.biz.model.bean.PageBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.IfbOrderEntity;
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
    private MutableLiveData<PageBean<TaskPublishEntity>> myBidTenderLiveData;

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
        tenderRepo = new TenderRepo(new TenderDs(this));
    }

}
