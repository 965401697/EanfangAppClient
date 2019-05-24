package com.eanfang.biz.rds.sys.ds.impl;

import com.eanfang.base.network.callback.RequestCallback;
import com.eanfang.biz.model.bean.GroupDetailBean;
import com.eanfang.biz.model.bean.RongTokenBean;
import com.eanfang.biz.rds.base.BaseRemoteDataSource;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.api.RongApi;
import com.eanfang.biz.rds.sys.ds.IRongDs;

public class RongDs extends BaseRemoteDataSource implements IRongDs {

    public RongDs(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }

    @Override
    public void getRongToken(Long userId, RequestCallback<RongTokenBean> callback) {
        execute(getService(RongApi.class).getRongToken(userId), callback);
    }

    @Override
    public void getGroupDetail(String id, boolean isUser, RequestCallback<GroupDetailBean> callback) {
        execute(getService(RongApi.class).getGroupDetail(id, isUser), callback);
    }
}
