package com.eanfang.biz.rds.sys.ds;

import com.eanfang.base.network.callback.RequestCallback;
import com.eanfang.biz.model.bean.PageBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.IfbOrderEntity;

/**
 * @author guanluocang
 * @data 2019/8/20
 * @description
 */
public interface ICompanySelectDs {
    /**
     * 招标大厅 公告中 已过期
     *
     * @param queryEntry
     * @param callback
     */
    void getOrganization(QueryEntry queryEntry, RequestCallback<PageBean<IfbOrderEntity>> callback);
}

