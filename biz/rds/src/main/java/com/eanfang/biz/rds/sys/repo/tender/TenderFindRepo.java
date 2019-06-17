package com.eanfang.biz.rds.sys.repo.tender;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.biz.model.bean.PageBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;
import com.eanfang.biz.rds.base.BaseRepo;
import com.eanfang.biz.rds.sys.ds.impl.tender.TenderFindDs;

import lombok.val;

/**
 * @author guanluocang
 * @data 2019/6/11
 * @description
 */
public class TenderFindRepo extends BaseRepo<TenderFindDs> {

    public TenderFindRepo(TenderFindDs remoteDataSource) {
        super(remoteDataSource);
    }

    /**
     * 用工找活
     */
    public MutableLiveData<PageBean<TaskPublishEntity>> doGetTenderFinderList(QueryEntry queryEntry) {
        MutableLiveData<PageBean<TaskPublishEntity>> tenderFindMutableLiveData = new MutableLiveData<>();
        queryEntry.setSize(10);
        remoteDataSource.getTenderFind(queryEntry, (val) -> {
            tenderFindMutableLiveData.setValue(val);
        });
        return tenderFindMutableLiveData;
    }
}


