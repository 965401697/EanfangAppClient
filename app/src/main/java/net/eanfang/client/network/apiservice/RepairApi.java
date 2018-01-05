package net.eanfang.client.network.apiservice;

import net.eanfang.client.BuildConfig;

/**
 * Created by MrHou
 *
 * @on 2017/12/26  13:58
 * @email houzhongzhou@yeah.net
 * @desc 报修相关
 */

public interface RepairApi {
    /**1.新增报修*/
    String ADD_CLIENT_REPAIR = BuildConfig.API_HOST + "/yaf_repair/order/create";

    /**2.查看报修列表*/
    String GET_REPAIR_LIST = BuildConfig.API_HOST + "/yaf_repair/order/list";

    /**3.查看报修详情*/
    String GET_REPAIR_DETAIL = BuildConfig.API_HOST + "/repair/order/detail";

    /**4.查看订单流程节点*/
    String GET_REPAIR_FLOW=BuildConfig.API_HOST+"/yaf_repair/order/flow";

    /**5.报修筛选技师*/
    String GET_REPAIR_SEARCH=BuildConfig.API_HOST+"/yaf_shop/worker/search";

    /**6.查看技师详情*/
    String GET_REPAIR_WORKER_DETAIL=BuildConfig.API_HOST+"/yaf_shop/worker/detail";

    /**7.技师电话筛查*/
    String GET_REPAIR_SCREENING=BuildConfig.API_HOST+"/yaf_repair/flow/screening";

    /**8.报修单更改预约时间*/
    String GET_REPAIR_RESCHEDULE=BuildConfig.API_HOST+"/yaf_repair/flow/rebook";

    /**9.技师上门签到*/
    String GET_REPAIR_WORKER_SIGNIN=BuildConfig.API_HOST+"/yaf_repair/flow/signIn";

    /**10.【去完工】技师端点击去完工，查询真实故障列表*/
    String GET_REPAIR_WORKER_PREPARE=BuildConfig.API_HOST+"/yaf_repair/bughandle/prepare";

    /**11.【创建】创建真实故障*/
    String GET_REPAIR_WORKER_CREATE_REAL_TROUBLE=BuildConfig.API_HOST+"/yaf_repair/failure/create";

    /**12.【新增】故障处理明细*/
    String GET_REPAIR_BUGHANDLE_CREATE_DETAIL=BuildConfig.API_HOST+"/yaf_repair/bughandle/createDetail";

    /**13.【详情】查看真实故障详情（包括对应的故障处理明细）*/
    String GET_LOOK_REPAIR_DETAIL=BuildConfig.API_HOST+"/yaf_repair/failure/detail";

    /**14.【完工】报修提交完工信息*/
    String POST_REPAIR_FINISH_WORK=BuildConfig.API_HOST+"/yaf_repair/flow/finishWork";

    /**15.【转单】报修单 转单流程*/
    String GET_REPAIR_TRANFER=BuildConfig.API_HOST+"/yaf_repair/flow/transfer";

    /**16.根据客户绑定的关系，查询是否存在记录并返回对象*/
    String GET_COOPERATION_EXISTS=BuildConfig.API_HOST+"/yaf_shop/cooperation/exists";

    /**17.是否已经收藏过技师*/
    String GET_COLLECT_EXISTS=BuildConfig.API_HOST+"/yaf_shop/collect/worker/exists";

    /**18.取消收藏过技师*/
    String GET_COLLECT_CANCEL=BuildConfig.API_HOST+"/yaf_shop/collect/worker/cancel";

    /**19.收藏技师*/
    String GET_COLLECT_ADD=BuildConfig.API_HOST+"/yaf_shop/collect/worker/create";

    /**20.收藏技师列表*/
    String GET_COLLECT_List=BuildConfig.API_HOST+"/yaf_shop/collect/worker/list";
}
