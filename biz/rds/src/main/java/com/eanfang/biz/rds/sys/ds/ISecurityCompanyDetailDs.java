package com.eanfang.biz.rds.sys.ds;

import com.eanfang.base.network.callback.RequestCallback;
import com.eanfang.biz.model.bean.SecurityCompanyDetailBean;

/**
 * @author guanluocang
 * @data 2019/8/23
 * @description
 */
public interface ISecurityCompanyDetailDs {
    /**
     * 获取详情
     *
     * @param mOrgId
     * @param callback
     */
    void getCompanyDetail(String mOrgId, RequestCallback<SecurityCompanyDetailBean> callback);
}
