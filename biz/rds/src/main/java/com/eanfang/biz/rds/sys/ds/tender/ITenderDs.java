package com.eanfang.biz.rds.sys.ds.tender;

import com.eanfang.base.network.callback.RequestCallback;
import com.eanfang.biz.model.bean.LoginBean;
import com.eanfang.biz.model.bean.PageBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.IfbOrderEntity;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;
import com.eanfang.biz.model.vo.tender.TenderCreateVo;

import retrofit2.http.QueryMap;

/**
 * @author guanluocang
 * @data 2019/5/31
 * @description 招标大厅-招标公告-相关接口
 */
public interface ITenderDs {

    /**
     * 招标大厅 公告中 已过期
     *
     * @param queryEntry
     * @param callback
     */
    void getTendeNotice(QueryEntry queryEntry, RequestCallback<PageBean<IfbOrderEntity>> callback);

    /**
     * 用工找活列表
     *
     * @param queryEntry
     * @param callback
     */
    void getTenderFind(QueryEntry queryEntry, RequestCallback<PageBean<TaskPublishEntity>> callback);

    /**
     * 发布用工
     *
     * @param tenderCreateVo
     * @param callback
     */
    void setNewTende(TenderCreateVo tenderCreateVo, RequestCallback<TaskPublishEntity> callback);


    /**
     * 用工详情
     * @param id
     * @param callback
     */
    void getFindDetail(String id,RequestCallback<TaskPublishEntity> callback);

}
