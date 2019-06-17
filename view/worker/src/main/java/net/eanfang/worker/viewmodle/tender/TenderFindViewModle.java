package net.eanfang.worker.viewmodle.tender;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.biz.model.bean.PageBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.tender.TenderFindDs;
import com.eanfang.biz.rds.sys.repo.tender.TenderFindRepo;

import lombok.Getter;

/**
 * @author guanluocang
 * @data 2019/6/13
 * @description 用工找活
 */
public class TenderFindViewModle extends BaseViewModel {
    private TenderFindRepo tenderFindRepo;
    @Getter
    private MutableLiveData<PageBean<TaskPublishEntity>> tenderFindLiveData;
    public QueryEntry mFindQueryEntry;
    public int mFindPage = 1;

    public TenderFindViewModle() {
        tenderFindLiveData = new MutableLiveData<>();
        tenderFindRepo = new TenderFindRepo(new TenderFindDs(this));
    }
}
