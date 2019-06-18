package com.eanfang.biz.rds.sys.ds.tender;

import com.eanfang.base.network.callback.RequestCallback;
import com.eanfang.biz.model.bean.PageBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.IfbOrderEntity;

/**
 * @author guanluocang
 * @data 2019/6/17
 * @description
 */
public interface ITenderCreateDs {

    /**
     *
     * @param queryEntry
     * @param callback
     */
    void setNewTende(QueryEntry queryEntry, RequestCallback<PageBean<IfbOrderEntity>> callback);
}
