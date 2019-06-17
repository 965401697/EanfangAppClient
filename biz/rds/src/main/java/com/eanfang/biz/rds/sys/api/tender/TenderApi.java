package com.eanfang.biz.rds.sys.api.tender;

import com.eanfang.base.network.model.BaseResponseBody;
import com.eanfang.biz.model.bean.PageBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.IfbOrderEntity;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;
import com.eanfang.biz.model.vo.LoginVo;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

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
    @POST("/yaf_ifb/ifborder/list")
    Observable<BaseResponseBody<PageBean<IfbOrderEntity>>> getTenderNoticeList(@Body QueryEntry queryMap);

    /**
     * 用工找活
     * @param queryMap
     * @return
     */
    @POST("/yaf_task_publish/taskPublish/laborList")
    Observable<BaseResponseBody<PageBean<TaskPublishEntity>>> getTenderFindList(@Body QueryEntry queryMap);
}
