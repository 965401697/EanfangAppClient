package com.eanfang.biz.rds.sys.ds.tender;

import com.eanfang.base.network.callback.RequestCallback;
import com.eanfang.biz.model.bean.PageBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;

/**
 * @author guanluocang
 * @data 2019/6/11
 * @description 招标大厅-用工找活-相关接口
 */
public interface ITenderFindDs {

    /**
     *
     * @param queryEntry
     * @param callback
     */
    void getTendeFind(QueryEntry queryEntry, RequestCallback<PageBean<TaskPublishEntity>> callback);
}
