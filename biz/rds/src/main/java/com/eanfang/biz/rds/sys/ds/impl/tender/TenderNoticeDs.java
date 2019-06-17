package com.eanfang.biz.rds.sys.ds.impl.tender;

import com.eanfang.base.network.callback.RequestCallback;
import com.eanfang.biz.model.bean.PageBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.IfbOrderEntity;
import com.eanfang.biz.rds.base.BaseRemoteDataSource;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.api.LoginApi;
import com.eanfang.biz.rds.sys.api.tender.TenderApi;
import com.eanfang.biz.rds.sys.ds.tender.ITenderDs;

/**
 * @author guanluocang
 * @data 2019/5/31
 * @description
 */
public class TenderNoticeDs extends BaseRemoteDataSource implements ITenderDs {

    public TenderNoticeDs(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }

    @Override
    public void getTendeNotice(QueryEntry queryEntry, RequestCallback<PageBean<IfbOrderEntity>> callback) {
        execute(getService(TenderApi.class).getTenderNoticeList(queryEntry), callback);
    }
}
