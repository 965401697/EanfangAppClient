package com.eanfang.biz.rds.sys.ds.impl.tender;

import com.eanfang.base.network.callback.RequestCallback;
import com.eanfang.biz.model.bean.PageBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.IfbOrderEntity;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;
import com.eanfang.biz.rds.base.BaseRemoteDataSource;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.api.tender.TenderApi;
import com.eanfang.biz.rds.sys.ds.tender.ITenderDs;
import com.eanfang.biz.rds.sys.ds.tender.ITenderFindDs;

/**
 * @author guanluocang
 * @data 2019/6/11
 * @description
 */
public class TenderFindDs extends BaseRemoteDataSource implements ITenderFindDs {

    public TenderFindDs(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }

    @Override
    public void getTendeFind(QueryEntry queryEntry, RequestCallback<PageBean<TaskPublishEntity>> callback) {
        execute(getService(TenderApi.class).getTenderFindList(queryEntry), callback);
    }
}
