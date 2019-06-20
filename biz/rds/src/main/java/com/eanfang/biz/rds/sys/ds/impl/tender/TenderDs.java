package com.eanfang.biz.rds.sys.ds.impl.tender;

import com.eanfang.base.network.callback.RequestCallback;
import com.eanfang.biz.model.bean.PageBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.IfbOrderEntity;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;
import com.eanfang.biz.model.vo.tender.TenderCreateVo;
import com.eanfang.biz.rds.base.BaseRemoteDataSource;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.api.tender.TenderApi;
import com.eanfang.biz.rds.sys.ds.tender.ITenderDs;

/**
 * @author guanluocang
 * @data 2019/6/17
 * @description 用工找活
 */
public class TenderDs extends BaseRemoteDataSource implements ITenderDs {
    public TenderDs(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }

    @Override
    public void getTenderFind(QueryEntry queryEntry, RequestCallback<PageBean<TaskPublishEntity>> callback) {
        execute(getService(TenderApi.class).getTenderFindList(queryEntry), callback);
    }

    @Override
    public void getTendeNotice(QueryEntry queryEntry, RequestCallback<PageBean<IfbOrderEntity>> callback) {
        execute(getService(TenderApi.class).getTenderNoticeList(queryEntry), callback);
    }


    @Override
    public void setNewTende(TenderCreateVo tenderCreateVo, RequestCallback<TaskPublishEntity> callback) {
        execute(getService(TenderApi.class).setNewTender(tenderCreateVo), callback);
    }
}
