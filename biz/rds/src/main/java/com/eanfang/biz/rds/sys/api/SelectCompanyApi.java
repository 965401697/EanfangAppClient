package com.eanfang.biz.rds.sys.api;

import com.eanfang.base.network.model.BaseResponseBody;
import com.eanfang.biz.model.bean.RoleBean;
import com.eanfang.biz.model.bean.SectionBean;
import com.eanfang.biz.model.entity.UserEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author guanluocang
 * @data 2019/8/21
 * @description 选择人
 */
public interface SelectCompanyApi {

    /**
     * 选择角色
     *
     * @return
     */
    @GET("/yaf_sys/role/listmyrole")
    Observable<BaseResponseBody<List<RoleBean>>> getRoleTypeList();

    /**
     * 选择员工
     *
     * @param id
     * @return
     */
    @POST("/yaf_sys/org/departmentByCompany/listtreeall")
    Observable<BaseResponseBody<List<SectionBean>>> getStaffList(@Query("companyId") String id);

    /**
     * 添加第一步
     * @param userEntity
     * @return
     */
    @POST("/yaf_sys/staff/insert")
    Observable<BaseResponseBody<UserEntity>> doSubmitFirst(@Body UserEntity userEntity);

    /**
     * 添加第二步
     * @param userId
     * @param roleIdList
     * @return
     */
    @POST("/yaf_sys/staff/grant{userId}")
    Observable<BaseResponseBody<Object>> doSubmitSecond(@Path("userId") String userId, @Body List<String> roleIdList);
}
