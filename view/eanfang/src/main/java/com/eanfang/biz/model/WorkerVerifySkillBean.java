package com.eanfang.biz.model;

import com.eanfang.biz.model.entity.BaseDataEntity;
import com.yaf.base.entity.TechWorkerVerifyEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by O u r on 2018/11/5.
 */

public class WorkerVerifySkillBean implements Serializable {
    private TechWorkerVerifyEntity workerVerify;
    private List<BaseDataEntity> baseData2userList;


    public TechWorkerVerifyEntity getWorkerVerify() {
        return workerVerify;
    }

    public List<BaseDataEntity> getBaseData2userList() {
        return baseData2userList;
    }

    public void setWorkerVerify(TechWorkerVerifyEntity workerVerify) {
        this.workerVerify = workerVerify;
    }

    public void setBaseData2userList(List<BaseDataEntity> baseData2userList) {
        this.baseData2userList = baseData2userList;
    }


}
