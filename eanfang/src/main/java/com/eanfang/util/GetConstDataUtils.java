package com.eanfang.util;

import android.content.Context;

import com.eanfang.config.Config;
import com.eanfang.config.Constant;

import java.util.Arrays;
import java.util.List;


public class GetConstDataUtils {

    private Config config;

    private GetConstDataUtils(Config config) {
        this.config = config;
    }

    public static GetConstDataUtils get(Config config) {
        return new GetConstDataUtils(config);
    }

    /**
     * 报修订单状态
     * getRepairConstant
     *
     * @return
     */
    public List<String> getRepairStatus() {
        return config.getConstBean().getData().getRepairConstant().get(Constant.STATUS);
    }

    /**
     * 免费设计状态
     *
     * @return
     */
    public List<String> getDesignStatus() {
        return config.getConstBean().getData().getDesignOrderConstant().get(Constant.STATUS);
    }

    /**
     * 技师工作状态
     */
    public List<String> getWorkerStatus() {
        return config.getConstBean().getData().getShopConstant().get(Constant.WORK_STATUS);
    }

    /**
     * 报装单
     *
     * @return
     */
    public List<String> getInstallStatus() {
        return config.getConstBean().getData().getInstallOrderConstant().get(Constant.STATUS);
    }

    /**
     * 报价状态
     *
     * @return
     */
    public List<String> getQuoteStatus() {
        return config.getConstBean().getData().getQuoteOrderConstant().get(Constant.STATUS);
    }

    /**
     * 合作业务状态
     * getShopConstant
     */
    public List<String> getCooperationStatus() {
        return config.getConstBean().getData().getShopConstant().get(Constant.COOPERATION_STATUS);
    }

    /**
     * 工作检查 状态
     *
     * @return
     */
    public List<String> getWorkInspectStatus() {
        return config.getConstBean().getData().getWorkInspectConstant().get(Constant.STATUS);
    }

    /**
     * 工作汇报状态
     *
     * @return
     */
    public List<String> getWorkReportStatus() {
        return config.getConstBean().getData().getConst().get(Constant.RED_UN_READ);
    }

    /**
     * 工作任务
     *
     * @return
     */
    public List<String> getWorkTaskStatus() {
        return config.getConstBean().getData().getConst().get(Constant.RED_UN_READ);
    }

    public List<String> getTaskPublishStatus() {

        return config.getConstBean().getData().getTaskPublishConstant().get(Constant.STATUS);
    }

    /**
     * 优先级
     * getConst
     *
     * @return
     */
    public List<String> getInstancyList() {

        return config.getConstBean().getData().getConst().get(Constant.INSTANCY_LEVEL);
    }

    /**
     * 首次响应
     * getConst
     *
     * @return
     */
    public List<String> getFirstLookList() {
        return config.getConstBean().getData().getConst().get(Constant.FIRST_LOOK);
    }

    /**
     * 首次汇报
     * getConst
     *
     * @return
     */
    public List<String> getFirstCallbackList() {
        return config.getConstBean().getData().getConst().get(Constant.FIRST_CALLBACK);
    }

    /**
     * 跟踪汇报
     * getConst
     *
     * @return
     */
    public List<String> getThenCallbackList() {

        return config.getConstBean().getData().getConst().get(Constant.THEN_CALLBACK);
    }

    /**
     * 回复时限
     * getDesignOrderConstant
     *
     * @return
     */
    public List<String> getRevertList() {

        return config.getConstBean().getData().getDesignOrderConstant().get(Constant.REVERT_TIME_LIMIT_TYPE);
    }

    /**
     * 预计工期
     * getDesignOrderConstant
     *
     * @return
     */
    public List<String> getPredictList() {

        return config.getConstBean().getData().getDesignOrderConstant().get(Constant.PREDICTTIME_TYPE);
    }

    /**
     * 预算范围
     * getDesignOrderConstant
     *
     * @return
     */
    public List<String> getBudgetList() {

        return config.getConstBean().getData().getDesignOrderConstant().get(Constant.BUDGET_LIMIT_TYPE);
    }

    /**
     * 故障处理明细
     * getRepairConstant
     *
     * @return
     */
    public List<String> getBugDetailList() {

        return config.getConstBean().getData().getRepairConstant().get(Constant.BUGHANDLE_DETAIL_STATUS);
    }

    /**
     * 到达时限
     *
     * @return
     */
    public List<String> getArriveList() {

        return config.getConstBean().getData().getConst().get(Constant.ARRIVE_LIMIT);
    }

    public List<String> getWorkReportTypeList() {

        return config.getConstBean().getData().getWorkReportConstant().get(Constant.REPORTTYPE);
    }

    public List<String> getStoreDayList() {

        return config.getConstBean().getData().getRepairConstant().get(Constant.STORE_DAYS);
    }

    public List<String> getIsNormalList() {

        return config.getConstBean().getData().getRepairConstant().get(Constant.IS_NORMAL);
    }

    /**
     * 工作等级
     * getShopConstant
     */
    public List<String> getWorkingLevelList() {

        return config.getConstBean().getData().getShopConstant().get(Constant.WORKING_LEVEL);
    }

    /**
     * 工作年限
     * getShopConstant
     *
     * @return
     */
    public List<String> getWorkingYearList() {

        return config.getConstBean().getData().getShopConstant().get(Constant.WORKING_YEAR);
    }


    public List<String> getDeviceUnitList() {

        return config.getConstBean().getData().getDeviceConstant().get(Constant.UNIT);
    }

    public List<String> getMaintainLevelList() {

        return config.getConstBean().getData().getMainTainConstant().get(Constant.MAINTAIN_LEVEL);
    }

    public List<String> getCheckResultList() {

        return config.getConstBean().getData().getMainTainConstant().get(Constant.CHECK_RESULT);
    }

    /**
     * 合作业务类型
     */
    public List<String> getCooperationTypeList() {

        return config.getConstBean().getData().getShopConstant().get(Constant.COOPERATION_TYPE);
    }

    public List<String> getTaskPublishTypeList() {

        return config.getConstBean().getData().getTaskPublishConstant().get(Constant.TASK_PUB_TYPE);
    }

    public List<String> getDeviceParamList() {

        return config.getConstBean().getData().getRepairConstant().get(Constant.PARAM);
    }

    /**
     * 公司规模
     */
    public List<String> getOrgUnitScaleList() {

        return config.getConstBean().getData().getConst().get(Constant.ORG_UNIT_SCALE);
    }


    public List<String> getTransferCauseList() {

        return config.getConstBean().getData().getRepairConstant().get(Constant.TRANSFER_CAUSE);
    }

    public List<String> getCycleList() {

        return config.getConstBean().getData().getMainTainConstant().get(Constant.CYCLE);
    }

    public List<String> getPayTypeList() {

        return config.getConstBean().getData().getShopConstant().get(Constant.PAY_TYPE);
    }

    /**
     * 是否误报
     */
    public List<String> getRepairMisinformationList() {

        return Arrays.asList("否", "是");
    }

    public List<String> getNoticeTypeList() {

        return config.getConstBean().getData().getNoticeConst().get(Constant.NOTICE_TYPE);
    }
}
