package com.eanfang.biz.rds.sys.api;

import com.eanfang.base.network.model.BaseResponseBody;
import com.eanfang.biz.model.bean.PageBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.IfbOrderEntity;
import com.eanfang.biz.model.entity.tender.TaskApplyEntity;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;
import com.eanfang.biz.model.vo.tender.TenderCommitVo;
import com.eanfang.biz.model.vo.tender.TenderCreateVo;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author guanluocang
 * @data 2019/5/31
 * @description 招标api
 */
public interface TenderApi {

    /**
     * 招标公告
     *
     * @param queryMap
     * @return
     */
    @POST("/yaf_ifb/ifborder/lists")
    Observable<BaseResponseBody<PageBean<IfbOrderEntity>>> getTenderNoticeList(@Body QueryEntry queryMap);

    /**
     * 用工找活
     *
     * @param queryMap
     * @return
     */
    @POST("/yaf_task_publish/taskPublish/laborList")
    Observable<BaseResponseBody<PageBean<TaskPublishEntity>>> getTenderFindList(@Body QueryEntry queryMap);

    /**
     * 用工找活发布
     *
     * @param tenderCreateVo
     * @return
     */
    @POST("/yaf_task_publish/taskPublish/laborInsert")
    Observable<BaseResponseBody<TaskPublishEntity>> setNewTender(@Body TenderCreateVo tenderCreateVo);

    /**
     * 用工详情
     *
     * @param id
     * @return
     */
    @POST("/yaf_task_publish/taskPublish/laborDetail")
    Observable<BaseResponseBody<TaskPublishEntity>> getTenderDetail(@Query("id") String id);

    /**
     * 我要报价
     */
    @POST("/yaf_task_publish/taskApply/bidInsert")
    Observable<BaseResponseBody<TaskPublishEntity>> setCommitTender(@Body TenderCommitVo tenderCommitVo);

    /**
     * 我的招标
     */

    @POST("/yaf_task_publish/taskApply/bidList")
    Observable<BaseResponseBody<PageBean<TaskApplyEntity>>> getMyBidTenderList(@Body QueryEntry queryMap);

    /**
     * 我的发布
     */

    @POST("/yaf_task_publish/taskPublish/releasedList")
    Observable<BaseResponseBody<PageBean<TaskPublishEntity>>> getMyReleaseTendeList(@Body QueryEntry queryMap);

    /**
     * 关闭我的发布
     *
     * @param taskPublishEntity
     * @return
     */
    @POST("/yaf_task_publish/taskPublish/shutDown")
    Observable<BaseResponseBody<TaskPublishEntity>> doCloseReleaseTende(@Body TaskPublishEntity taskPublishEntity);

    /**
     * 我的用工 发布的详情
     *
     * @param queryEntry
     * @return
     */
    @POST("/yaf_task_publish/taskPublish/assessmentList")
    Observable<BaseResponseBody<PageBean<TaskApplyEntity>>> getMyReleaseTendeDetail(@Body QueryEntry queryEntry);

    /**
     * 我的用工 招标的详情
     *
     * @param id
     * @return
     */
    @POST("/yaf_task_publish/taskApply/bidDetail")
    Observable<BaseResponseBody<TaskApplyEntity>> getMyBidTendeDetail(@Query("id") String id);

    /**
     * 忽略 中标
     *
     * @param taskApplyEntity
     * @return
     */
    @POST("/yaf_task_publish/taskApply/bidUpdate")
    Observable<BaseResponseBody<TaskApplyEntity>> getMyReleaseTendeDetail(@Body TaskApplyEntity taskApplyEntity);

}
