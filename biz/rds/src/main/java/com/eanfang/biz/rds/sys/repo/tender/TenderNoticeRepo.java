package com.eanfang.biz.rds.sys.repo.tender;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.biz.model.bean.PageBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.IfbOrderEntity;
import com.eanfang.biz.rds.base.BaseRepo;
import com.eanfang.biz.rds.sys.ds.impl.tender.TenderNoticeDs;


/**
 * @author guanluocang
 * @data 2019/5/31
 * @description
 */
public class TenderNoticeRepo extends BaseRepo<TenderNoticeDs> {
    public TenderNoticeRepo(TenderNoticeDs remoteDataSource) {
        super(remoteDataSource);
    }

    /**
     * 招标公告
     */
    public MutableLiveData<PageBean<IfbOrderEntity>> doGetTenderNoticeList(QueryEntry queryEntry) {
        MutableLiveData<PageBean<IfbOrderEntity>> tenderNoticeMutableLiveData = new MutableLiveData<>();
        queryEntry.setSize(10);
        remoteDataSource.getTendeNotice(queryEntry, (val) -> {
            tenderNoticeMutableLiveData.setValue(val);
        });
        return tenderNoticeMutableLiveData;
    }
}
