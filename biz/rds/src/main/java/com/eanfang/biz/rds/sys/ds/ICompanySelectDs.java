package com.eanfang.biz.rds.sys.ds;

import com.eanfang.base.network.callback.RequestCallback;
import com.eanfang.biz.model.bean.RoleBean;
import com.eanfang.biz.model.bean.SectionBean;
import com.eanfang.biz.model.entity.UserEntity;

import java.util.List;

/**
 * @author guanluocang
 * @data 2019/8/20
 * @description
 */
public interface ICompanySelectDs {

    /**
     * 角色
     *
     * @param callback
     */
    void getRoleType(RequestCallback<List<RoleBean>> callback);

    /**
     * 员工
     *
     * @param companyId
     * @param callback
     */
    void getStaff(String companyId, RequestCallback<List<SectionBean>> callback);

    /**
     * 添加第一步
     * @param userEntity
     * @param callback
     */
    void doSubmitFirst(UserEntity userEntity, RequestCallback<UserEntity> callback);

    /**
     * 添加第二步
     * @param userId
     * @param roleIdList
     * @param callback
     */
    void doSubmitSecond(String userId,List<String> roleIdList, RequestCallback<Object> callback);
}

