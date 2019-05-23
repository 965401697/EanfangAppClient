package com.eanfang.biz.rds.sys.ds;


import com.eanfang.base.network.callback.RequestCallback;
import com.eanfang.biz.model.bean.BaseDataBean;
import com.eanfang.biz.model.bean.ConstAllBean;
import com.eanfang.biz.model.entity.AccountEntity;

/**
 * main 相关接口
 */
public interface IMainDs {

    /**
     * 获取基础数据
     * 24小时内取缓存
     *
     * @param md5      md5
     * @param callback callback
     */
    void getBaseData(String md5, RequestCallback<BaseDataBean> callback);

    /**
     * 获取常量数据
     * 24小时内取缓存
     *
     * @param md5      md5
     * @param callback callback
     */
    void getConstData(String md5, RequestCallback<ConstAllBean> callback);

    /**
     * 账号详情
     *
     * @param id       id
     * @param callback callback
     */
    void getAccountInfo(String id, RequestCallback<AccountEntity> callback);
}
