package com.eanfang.biz.rds.sys.repo.tender;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.biz.model.bean.PageBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.IfbOrderEntity;
import com.eanfang.biz.model.entity.tender.TaskApplyEntity;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;
import com.eanfang.biz.model.vo.tender.TenderCommitVo;
import com.eanfang.biz.model.vo.tender.TenderCreateVo;
import com.eanfang.biz.rds.base.BaseRepo;
import com.eanfang.biz.rds.sys.ds.impl.tender.TenderDs;

import lombok.val;

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

    /**
     * 我要报价
     */
    public MutableLiveData<TaskPublishEntity> doSetCommitTender(TenderCommitVo tenderCommitVo) {
        MutableLiveData<TaskPublishEntity> getTenderDetailLiveData = new MutableLiveData<>();
        remoteDataSource.setCommitTender(tenderCommitVo, (val) -> {
            getTenderDetailLiveData.setValue(val);
        });
        return getTenderDetailLiveData;
    }


    /**
     * 我发布的
     */
    public MutableLiveData<PageBean<TaskPublishEntity>> doGetTenderRelaeseList(QueryEntry queryEntry) {
        MutableLiveData<PageBean<TaskPublishEntity>> tenderMyReleaseLiveData = new MutableLiveData<>();
        queryEntry.setSize(10);
        remoteDataSource.getMyReleaseTender(queryEntry, (val) -> {
            tenderMyReleaseLiveData.setValue(val);
        });
        return tenderMyReleaseLiveData;
    }

    /**
     * 我招标的
     */
    public MutableLiveData<PageBean<TaskApplyEntity>> doGetTenderBidList(QueryEntry queryEntry) {
        MutableLiveData<PageBean<TaskApplyEntity>> tenderMyBidLiveData = new MutableLiveData<>();
        queryEntry.setSize(10);
        remoteDataSource.getMyBidTender(queryEntry, (val) -> {
            tenderMyBidLiveData.setValue(val);
        });
        return tenderMyBidLiveData;
    }

    /**
     * 关闭我的发布
     */
    public MutableLiveData<TaskPublishEntity> doCloseReleaseTender(TaskPublishEntity taskPublishEntity) {
        MutableLiveData<TaskPublishEntity> closeRelaeseTenderLiveData = new MutableLiveData<>();
        remoteDataSource.doCloseReleaseTender(taskPublishEntity, (val) -> {
            closeRelaeseTenderLiveData.setValue(val);
        });
        return closeRelaeseTenderLiveData;
    }

    /**
     * 评标详情
     */
    public MutableLiveData<PageBean<TaskApplyEntity>> doGetTenderOfferDetail(String shopTaskPublishId) {
        MutableLiveData<PageBean<TaskApplyEntity>> offerDetailTenderLiveData = new MutableLiveData<>();
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("shopTaskPublishId", shopTaskPublishId);
        remoteDataSource.getTenderOfferDetail(queryEntry, (val) -> {
            offerDetailTenderLiveData.setValue(val);
        });
        return offerDetailTenderLiveData;
    }

    /**
     * 招标详情
     */
    public MutableLiveData<TaskApplyEntity> doGetTenderBidDetail(String mId) {
        MutableLiveData<TaskApplyEntity> bidDetailTenderLiveData = new MutableLiveData<>();
        remoteDataSource.getTenderBidDetail(mId, (val) -> {
            bidDetailTenderLiveData.setValue(val);
        });
        return bidDetailTenderLiveData;
    }

    /**
     * 忽略 中标
     */
    public MutableLiveData<TaskApplyEntity> doChangeOfferStatus(TaskApplyEntity taskApplyEntity) {
        MutableLiveData<TaskApplyEntity> changeOfferStatusTenderLiveData = new MutableLiveData<>();
        remoteDataSource.doChangeOfferStatus(taskApplyEntity, (val) -> {
            changeOfferStatusTenderLiveData.setValue(val);
        });
        return changeOfferStatusTenderLiveData;
    }
}


