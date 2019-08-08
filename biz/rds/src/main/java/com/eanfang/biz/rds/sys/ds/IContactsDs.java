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
    void joinDefCompany(RequestCallback<LoginBean> callback);
}

