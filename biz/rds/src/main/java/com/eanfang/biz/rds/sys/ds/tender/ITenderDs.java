package com.eanfang.biz.rds.sys.ds.tender;

import com.eanfang.base.network.callback.RequestCallback;
import com.eanfang.biz.model.bean.LoginBean;
import com.eanfang.biz.model.bean.PageBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.IfbOrderEntity;

import retrofit2.http.QueryMap;

/**
 * @author guanluocang
 * @data 2019/5/31
 * @description 招标大厅-招标公告-相关接口
 */
public interface ITenderDs {

    /**
     * 招标大厅 公告中 已过期
     * @param queryEntry
     * @param callback
     */
    void getTendeNotice(QueryEntry queryEntry, RequestCallback<PageBean<IfbOrderEntity>> callback);
}
