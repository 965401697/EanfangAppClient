package com.eanfang.biz.rds.sys.api;

import com.eanfang.base.network.model.BaseResponseBody;
import com.eanfang.biz.model.bean.GroupDetailBean;
import com.eanfang.biz.model.bean.RongTokenBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 融云 API
 */
public interface RongApi {

    /**
     * 获取融云 token
     *
     * @param userId userId
     * @return RongTokenBean
     */
    @GET("/yaf_im/im/getToken")
    Observable<BaseResponseBody<RongTokenBean>> getRongToken(@Query("userId") Long userId);

    /**
     * 获取群组详情
     *
     * @param id     ryGroupId
     * @param isUser 是否加载群成员
     * @return GroupDetailBean
     */
    @POST("/yaf_im/sysgroup/detail/ry")
    Observable<BaseResponseBody<GroupDetailBean>> getGroupDetail(@Query("ryGroupId") String id, @Query("isUser") boolean isUser);
}
