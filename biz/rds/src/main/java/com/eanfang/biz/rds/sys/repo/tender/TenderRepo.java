package com.eanfang.biz.rds.sys.repo.tender;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.biz.model.bean.PageBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.IfbOrderEntity;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;
import com.eanfang.biz.model.vo.tender.TenderCreateVo;
import com.eanfang.biz.rds.base.BaseRepo;
import com.eanfang.biz.rds.sys.ds.impl.tender.TenderDs;

/**
 * @author guanluocang
 * @data 2019/6/11
 * @description
 */
public class TenderRepo extends BaseRepo<TenderDs> {

    public TenderRepo(TenderDs remoteDataSource) {
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

    /**
     * 发布用工
     */
    public MutableLiveData<TaskPublishEntity> doSetNewTender(TenderCreateVo tenderCreateVo) {
        MutableLiveData<TaskPublishEntity> setNewTenderLiveData = new MutableLiveData<>();
        remoteDataSource.setNewTende(tenderCreateVo, (val) -> {
            setNewTenderLiveData.setValue(val);
        });
        return setNewTenderLiveData;
    }

    /**
     * 用工详情
     */
    public MutableLiveData<TaskPublishEntity> doGetTenderDetail(String id) {
        MutableLiveData<TaskPublishEntity> getTenderDetailLiveData = new MutableLiveData<>();
        remoteDataSource.getFindDetail(id, (val) -> {
            getTenderDetailLiveData.setValue(val);
        });
        return getTenderDetailLiveData;
    }
}


