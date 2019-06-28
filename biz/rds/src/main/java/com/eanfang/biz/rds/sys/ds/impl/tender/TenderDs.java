package com.eanfang.biz.rds.sys.ds.impl.tender;

import com.eanfang.base.network.callback.RequestCallback;
import com.eanfang.biz.model.bean.PageBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.IfbOrderEntity;
import com.eanfang.biz.model.entity.tender.TaskApplyEntity;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;
import com.eanfang.biz.model.vo.tender.TenderCommitVo;
import com.eanfang.biz.model.vo.tender.TenderCreateVo;
import com.eanfang.biz.rds.base.BaseRemoteDataSource;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.api.tender.TenderApi;
import com.eanfang.biz.rds.sys.ds.tender.ITenderDs;

import java.lang.reflect.Field;

/**
 * @author guanluocang
 * @data 2019/6/17
 * @description 用工找活
 */
public class TenderDs extends BaseRemoteDataSource implements ITenderDs {
    public TenderDs(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }

    /**
     * 用工找活
     */
    @Override
    public void getTenderFind(QueryEntry queryEntry, RequestCallback<PageBean<TaskPublishEntity>> callback) {
        execute(getService(TenderApi.class).getTenderFindList(queryEntry), callback);
    }

    /**
     * 招标公告
     */
    @Override
    public void getTendeNotice(QueryEntry queryEntry, RequestCallback<PageBean<IfbOrderEntity>> callback) {
        execute(getService(TenderApi.class).getTenderNoticeList(queryEntry), callback);
    }


    /**
     * 用工找活发布
     */
    @Override
    public void setNewTende(TenderCreateVo tenderCreateVo, RequestCallback<TaskPublishEntity> callback) {
        execute(getService(TenderApi.class).setNewTender(tenderCreateVo), callback);
    }

    /**
     * 用工详情
     */
    @Override
    public void getFindDetail(String id, RequestCallback<TaskPublishEntity> callback) {
        execute(getService(TenderApi.class).getTenderDetail(id), callback);
    }

    /**
     * 我要报价
     */
    @Override
    public void setCommitTender(TenderCommitVo tenderCommitVo, RequestCallback<TaskPublishEntity> callback) {
        execute(getService(TenderApi.class).setCommitTender(tenderCommitVo), callback);
    }

    /**
     * 我的发布
     */
    @Override
    public void getMyReleaseTender(QueryEntry queryEntry, RequestCallback<PageBean<TaskPublishEntity>> callback) {
        execute(getService(TenderApi.class).getMyReleaseTendeList(queryEntry), callback);
    }

    /**
     * 我的招标
     */
    @Override
    public void getMyBidTender(QueryEntry queryEntry, RequestCallback<PageBean<TaskApplyEntity>> callback) {
        execute(getService(TenderApi.class).getMyBidTenderList(queryEntry), callback);
    }

    /**
     * 关闭我的发布
     *
     * @param taskPublishEntity
     * @param callback
     */
    @Override
    public void doCloseReleaseTender(TaskPublishEntity taskPublishEntity, RequestCallback<TaskPublishEntity> callback) {
        execute(getService(TenderApi.class).doCloseReleaseTende(taskPublishEntity), callback);
    }

    /**
     * 评标详情
     */
    @Override
    public void getTenderOfferDetail(QueryEntry queryEntry, RequestCallback<PageBean<TaskApplyEntity>> callback) {
        execute(getService(TenderApi.class).getMyReleaseTendeDetail(queryEntry), callback);
    }

    /**
     * 招标详情
     */
    @Override
    public void getTenderBidDetail(String mBidId, RequestCallback<TaskApplyEntity> callback) {
        execute(getService(TenderApi.class).getMyBidTendeDetail(mBidId), callback);
    }

    /**
     * 忽略 中标
     */
    @Override
    public void doChangeOfferStatus(TaskApplyEntity taskApplyEntity, RequestCallback<TaskApplyEntity> callback) {
        execute(getService(TenderApi.class).getMyReleaseTendeDetail(taskApplyEntity), callback);
    }

}
