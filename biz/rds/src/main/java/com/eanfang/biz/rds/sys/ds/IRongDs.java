package com.eanfang.biz.rds.sys.ds;

import com.eanfang.base.network.callback.RequestCallback;
import com.eanfang.biz.model.bean.GroupDetailBean;
import com.eanfang.biz.model.bean.RongTokenBean;

/**
 * 融云相关
 */
public interface IRongDs {
    /**
     * 取融云token
     *
     * @param userId   userId
     * @param callback callback
     */
    void getRongToken(Long userId, RequestCallback<RongTokenBean> callback);

    /**
     * 取融云 聊天群组详情
     *
     * @param id       群组id
     * @param isUser   是否加载群成员 默认加载
     * @param callback callback
     */
    void getGroupDetail(String id, boolean isUser, RequestCallback<GroupDetailBean> callback);
}
