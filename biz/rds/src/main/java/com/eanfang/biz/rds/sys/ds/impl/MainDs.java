package com.eanfang.biz.rds.sys.ds.impl;

import com.eanfang.base.network.callback.RequestCallback;
import com.eanfang.biz.model.bean.BaseDataBean;
import com.eanfang.biz.model.bean.ConstAllBean;
import com.eanfang.biz.model.entity.AccountEntity;
import com.eanfang.biz.rds.base.BaseRemoteDataSource;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.base.CacheModel;
import com.eanfang.biz.rds.sys.api.MainApi;
import com.eanfang.biz.rds.sys.ds.IMainDs;
import com.zchu.rxcache.stategy.CacheStrategy;

public class MainDs extends BaseRemoteDataSource implements IMainDs {


    public MainDs(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }


    @Override
    public void getBaseData(String md5, RequestCallback<BaseDataBean> callback) {
        execute(getService(MainApi.class).getBaseData(md5), callback, new CacheModel().setClazz(MainApi.class).setMethod("getBaseData").setValues(md5).setCacheStrategy(CacheStrategy.firstCacheTimeout(1000 * 60 * 60 * 24)));
    }

    @Override
    public void getConstData(String md5, RequestCallback<ConstAllBean> callback) {
        execute(getService(MainApi.class).getConstData(md5), callback, new CacheModel().setClazz(MainApi.class).setMethod("getConstData").setValues(md5).setCacheStrategy(CacheStrategy.firstCacheTimeout(1000 * 60 * 60 * 24)));
    }

    @Override
    public void getAccountInfo(String id, RequestCallback<AccountEntity> callback) {
        execute(getService(MainApi.class).getAccountInfo(id), callback);
    }
}
