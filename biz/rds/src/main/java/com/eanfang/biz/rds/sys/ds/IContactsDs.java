package com.eanfang.biz.rds.sys.ds;

import com.eanfang.base.network.callback.RequestCallback;
import com.eanfang.biz.model.bean.LoginBean;

/**
 * 通讯录相关接口
 *
 * @author jornl
 * @date 2019年8月8日
 */
public interface IContactsDs {
    /**
     * 加入默认公司
     *
     * @param callback callback
     */
    void joinDefCompany(RequestCallback<LoginBean> callback);

    /**
     * 退出组织
     *
     * @param userId   userId
     * @param callback callback
     */
    void quitCompany(Long userId, RequestCallback<LoginBean> callback);
}

