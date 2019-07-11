package com.eanfang.util;

import com.eanfang.config.Config;
import com.eanfang.config.Constant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GetConstDataUtils {
    /**
     * 报修单状态
     */
    private static List<String> repairStatus;
    /**
     * 设计单 状态
     */
    private static List<String> designStatus;
    /**
     * 报装单 状态
     */
    private static List<String> installStatus;
    /**
     * 报价 状态
     */
    private static List<String> quoteStatus;
    private static List<String> cooperationStatus;

    private static List<String> workInspectStatus;

    private static List<String> workReportStatus;

    private static List<String> workTaskStatus;

    private static List<String> taskPublishStatus;
    private static List<String> tenderBudgetUnit;

    /**
     * 面谈员工
     */
    private static List<String> workTalkStatus;
    /**
     * 交接班详情 当前状态
     */
    private static List<String> workTransferDetailStatus;
    /**
     * 阀值时间
     */
    private static List<String> detectTime;
    /**
     * 交接班
     */
    private static List<String> workTransfer;
    /**
     * 交接班 创建 班次 状态
     */
    private static List<String> workTransferCreateClass;
    /**
     * 交接班 列表(待确认 完成交接) 状态
     */
    private static List<String> workTransferListStatus;
    /**
     * 优先级
     */
    private static List<String> instancyList;
    /**
     * 首次响应
     */
    private static List<String> firstLookList;
    /**
     * 首次响应
     */
    private static List<String> firstCallbackList;
    /**
     * 首次响应
     */
    private static List<String> thenCallbackList;
    /**
     * 回复时限
     */
    private static List<String> revertList;
    /**
     * 预计工期
     */
    private static List<String> predictList;
    /**
     * 预算范围
     */
    private static List<String> budgetList;
    /**
     * 故障处理明细状态
     */
    private static List<String> bugDetailList;
    /**
     * 首页数据同居
     */
    private static List<String> homeRepairList;
    /**
     * 故障明细状态二级 的 key
     */
    private static List<String> bugDetailKeyList;

    /**
     * 故障明细 二级
     */
    private static Map<String, List<String>> bugDetailTwoList;
    /**
     * 到达时限
     */
    private static List<String> arriveList;
    /**
     * 汇报类型
     */
    private static List<String> workReportTypeList;
    /**
     * 录像机天数
     */
    private static List<String> storeDayList;
    /**
     * 是否正常
     */
    private static List<String> isNormalList;
    /**
     * 工作等级
     */
    private static List<String> workingLevelList;

    /**
     * 专家的认证类型
     */
    private static List<String> expertTypeList;

    private static List<String> workingYearList;
    /**
     * 设备单位
     */
    private static List<String> deviceUnitList;
    /**
     * 维保标准
     */
    private static List<String> maintainLevelList;
    /**
     * 维保订单的状态
     */
    private static List<String> maintainStatusList;
    /**
     * 维保增加处理结果的条件
     */
    private static List<String> maintainConditionList;
    /**
     * 维保处理的处理结论
     */
    private static List<String> maintainOsRuntimeList;
    /**
     * 维保 检查结果
     */
    private static List<String> checkResultList;
    private static List<String> cooperationTypeList;
    private static List<String> taskPublishTypeList;

    private static List<String> deviceParamList;
    private static List<String> orgUnitScaleList;

    private static List<String> transferCauseList;

    private static List<String> cycleList;

    private static List<String> payTypeList;

    private static List<String> workerStatus;

    private static List<String> repairMisinformationList;

    private static List<String> diplomaList;//学历

//    private static List<String> noticeTypeList;

    private static List<String> dataList;//数据库查询数据集合
    /**
     * 误报的报警原因
     */
    private static List<String> flaseCauseList;
    /**
     * 旁路的报警原因
     */
    private static List<String> bypassCauseList;
    /**
     * 闯防的报警原因
     */
    private static List<String> throughCauseList;


    /**
     * 报修订单状态
     * getRepairConstant
     *
     * @return
     */
    public static List<String> getRepairStatus() {
        if (repairStatus == null) {
            synchronized (GetConstDataUtils.class) {
                if (repairStatus == null) {
                    repairStatus = Config.get().getConstBean().getData().getRepairConstant().get(Constant.STATUS);
                    repairStatus.add("全部");
                    repairStatus.remove("订单取消");
                }
            }
        }
        return repairStatus;
    }

    /**
     * 免费设计状态
     *
     * @return
     */
    public static List<String> getDesignStatus() {
        if (designStatus == null) {
            synchronized (GetConstDataUtils.class) {
                if (designStatus == null) {
                    designStatus = Config.get().getConstBean().getData().getDesignOrderConstant().get(Constant.STATUS);
                }
            }
        }
        return designStatus;
    }

    /**
     * 技师工作状态
     */
    public static List<String> getWorkerStatus() {
        if (workerStatus == null) {
            synchronized (GetConstDataUtils.class) {
                if (workerStatus == null) {
                    workerStatus = Config.get().getConstBean().getData().getShopConstant().get(Constant.WORK_STATUS);
                }
            }
        }
        return workerStatus;
    }

    /**
     * 报装单
     *
     * @return
     */
    public static List<String> getInstallStatus() {
        if (installStatus == null) {
            synchronized (GetConstDataUtils.class) {
                if (installStatus == null) {
                    installStatus = Config.get().getConstBean().getData().getInstallOrderConstant().get(Constant.STATUS);
                }
            }
        }
        return installStatus;
    }

    /**
     * 报价状态
     *
     * @return
     */
    public static List<String> getQuoteStatus() {
        if (quoteStatus == null) {
            synchronized (GetConstDataUtils.class) {
                if (quoteStatus == null) {
                    quoteStatus = Config.get().getConstBean().getData().getQuoteOrderConstant().get(Constant.STATUS);
                }
            }
        }
        return quoteStatus;
    }

    /**
     * 合作业务状态
     * getShopConstant
     */
    public static List<String> getCooperationStatus() {
        if (cooperationStatus == null) {
            synchronized (GetConstDataUtils.class) {
                if (cooperationStatus == null) {
                    cooperationStatus = Config.get().getConstBean().getData().getShopConstant().get(Constant.COOPERATION_STATUS);
                }
            }
        }
        return cooperationStatus;
    }

    /**
     * 工作检查 状态
     *
     * @return
     */
    public static List<String> getWorkInspectStatus() {
        if (workInspectStatus == null) {
            synchronized (GetConstDataUtils.class) {
                if (workInspectStatus == null) {
                    workInspectStatus = Config.get().getConstBean().getData().getWorkInspectConstant().get(Constant.STATUS);
                }
            }
        }
        return workInspectStatus;
    }

    /**
     * 工作汇报状态
     *
     * @return
     */
    public static List<String> getWorkReportStatus() {
        if (workReportStatus == null) {
            synchronized (GetConstDataUtils.class) {
                if (workReportStatus == null) {
                    workReportStatus = Config.get().getConstBean().getData().getConst().get(Constant.RED_UN_READ);
                }
            }
        }
        return workReportStatus;
    }

    /**
     * 面谈员工状态
     *
     * @return
     */
    public static List<String> getWorkTalkStatus() {
        if (workTalkStatus == null) {
            synchronized (GetConstDataUtils.class) {
                if (workTalkStatus == null) {
                    workTalkStatus = Config.get().getConstBean().getData().getOAConst().get(Constant.WORKTALK_STATUS);
                    workTalkStatus.add("全部");
                    workTalkStatus.remove("已删除");
                }
            }
        }
        return workTalkStatus;
    }

    /**
     * 获取阀值时间
     *
     * @return
     */
    public static List<String> getDetectTime() {
        if (detectTime == null) {
            synchronized (GetConstDataUtils.class) {
                if (detectTime == null) {
                    detectTime = Config.get().getConstBean().getData().getConst().get(Constant.DETECT_TIME);
                }
            }
        }
        return detectTime;
    }

    /**
     * 交接班 详情(完好 破损) 状态
     *
     * @return
     */
    public static List<String> getWokrTransferDetailStatus() {
        if (workTransferDetailStatus == null) {
            synchronized (GetConstDataUtils.class) {
                if (workTransferDetailStatus == null) {
                    workTransferDetailStatus = Config.get().getConstBean().getData().getExchangeLogConstant().get(Constant.WORK_TRANSFER_DETAIL);
                }
            }
        }
        return workTransferDetailStatus;
    }


    /**
     * 交接班状态
     *
     * @return
     */
    public static List<String> getWorkTransfer() {
        if (workTransfer == null) {
            synchronized (GetConstDataUtils.class) {
                if (workTransfer == null) {
                    workTransfer = Config.get().getConstBean().getData().getOAConst().get(Constant.WORKTRANSFER_STATUS);
                    workTransfer.add("全部");
                    workTransfer.remove("已删除");
                }
            }
        }
        return workTransfer;
    }

    /**
     * 交接班 创建 班次 状态
     *
     * @return
     */
    public static List<String> getWorkTransferCreateClass() {
        if (workTransferCreateClass == null) {
            synchronized (GetConstDataUtils.class) {
                if (workTransferCreateClass == null) {
                    workTransferCreateClass = Config.get().getConstBean().getData().getExchangeLogConstant().get(Constant.WORK_TRANSFER_CREATE);
                }
            }
        }
        return workTransferCreateClass;
    }

    /**
     * 交接班 列表(待确认 完成交接) 状态
     *
     * @return
     */
    public static List<String> getWorkTransferList() {
        if (workTransferListStatus == null) {
            synchronized (GetConstDataUtils.class) {
                if (workTransferListStatus == null) {
                    workTransferListStatus = Config.get().getConstBean().getData().getExchangeLogConstant().get(Constant.WORK_TRANSFER_LIST);
                }
            }
        }
        return workTransferListStatus;
    }

    /**
     * 工作任务
     *
     * @return
     */
    public static List<String> getWorkTaskStatus() {
        if (workTaskStatus == null) {
            synchronized (GetConstDataUtils.class) {
                if (workTaskStatus == null) {
                    workTaskStatus = Config.get().getConstBean().getData().getConst().get(Constant.RED_UN_READ);
                }
            }
        }
        return workTaskStatus;
    }

    public static List<String> getTaskPublishStatus() {
        if (taskPublishStatus == null) {
            synchronized (GetConstDataUtils.class) {
                if (taskPublishStatus == null) {
                    taskPublishStatus = Config.get().getConstBean().getData().getTaskPublishConstant().get(Constant.STATUS);
                }
            }
        }
        return taskPublishStatus;
    }

    /**
     * 用工预算单位
     */
    public static List<String> getTenderBudgetUnit() {
        if (tenderBudgetUnit == null) {
            synchronized (GetConstDataUtils.class) {
                if (tenderBudgetUnit == null) {
                    tenderBudgetUnit = Config.get().getConstBean().getData().getConst().get(Constant.TENDERAPPLYBUDGETUNIT);
                }
            }
        }
        return tenderBudgetUnit;
    }

    /**
     * 优先级
     * getConst
     *
     * @return
     */
    public static List<String> getInstancyList() {
        if (instancyList == null) {
            synchronized (GetConstDataUtils.class) {
                if (instancyList == null) {
                    instancyList = Config.get().getConstBean().getData().getConst().get(Constant.INSTANCY_LEVEL);
                }
            }
        }
        return instancyList;
    }

    /**
     * 首次响应
     * getConst
     *
     * @return
     */
    public static List<String> getFirstLookList() {
        if (firstLookList == null) {
            synchronized (GetConstDataUtils.class) {
                if (firstLookList == null) {
                    firstLookList = Config.get().getConstBean().getData().getConst().get(Constant.FIRST_LOOK);
                }
            }
        }
        return firstLookList;
    }

    /**
     * 首次汇报
     * getConst
     *
     * @return
     */
    public static List<String> getFirstCallbackList() {
        if (firstCallbackList == null) {
            synchronized (GetConstDataUtils.class) {
                if (firstCallbackList == null) {
                    firstCallbackList = Config.get().getConstBean().getData().getConst().get(Constant.FIRST_CALLBACK);
                }
            }
        }
        return firstCallbackList;
    }

    /**
     * 跟踪汇报
     * getConst
     *
     * @return
     */
    public static List<String> getThenCallbackList() {
        if (thenCallbackList == null) {
            synchronized (GetConstDataUtils.class) {
                if (thenCallbackList == null) {
                    thenCallbackList = Config.get().getConstBean().getData().getConst().get(Constant.THEN_CALLBACK);
                }
            }
        }
        return thenCallbackList;
    }

    /**
     * 回复时限
     * getDesignOrderConstant
     *
     * @return
     */
    public static List<String> getRevertList() {
        if (revertList == null) {
            synchronized (GetConstDataUtils.class) {
                if (revertList == null) {
                    revertList = Config.get().getConstBean().getData().getDesignOrderConstant().get(Constant.REVERT_TIME_LIMIT_TYPE);
                }
            }
        }
        return revertList;
    }

    /**
     * 预计工期
     * getDesignOrderConstant
     *
     * @return
     */
    public static List<String> getPredictList() {
        if (predictList == null) {
            synchronized (GetConstDataUtils.class) {
                if (predictList == null) {
                    predictList = Config.get().getConstBean().getData().getDesignOrderConstant().get(Constant.PREDICTTIME_TYPE);
                }
            }
        }
        return predictList;
    }

    /**
     * 预算范围
     * getDesignOrderConstant
     *
     * @return
     */
    public static List<String> getBudgetList() {
        if (budgetList == null) {
            synchronized (GetConstDataUtils.class) {
                if (budgetList == null) {
                    budgetList = Config.get().getConstBean().getData().getDesignOrderConstant().get(Constant.BUDGET_LIMIT_TYPE);
                }
            }
        }
        return budgetList;
    }

    /**
     * 首页报修统计
     * getRepairConstant
     *
     * @return
     */
    public static List<String> getHomeRepairStatuslList() {
        if (homeRepairList == null) {
            synchronized (GetConstDataUtils.class) {
                if (homeRepairList == null) {
                    homeRepairList = Config.get().getConstBean().getData().getRepairConstant().get(Constant.HOME_DATASTATISTICS);
                }
            }
        }
        return homeRepairList;
    }


    /**
     * 故障处理明细
     * getRepairConstant
     *
     * @return
     */
    public static List<String> getBugDetailList() {
        if (bugDetailList == null) {
            synchronized (GetConstDataUtils.class) {
                if (bugDetailList == null) {
                    bugDetailList = Config.get().getConstBean().getData().getRepairConstant().get(Constant.BUGHANDLE_DETAIL_STATUS);
                    bugDetailKeyList = Config.get().getConstBean().getData().getRepairConstant().get(Constant.BUGHANDLE_DETAIL_STATUS_KEY);
                    bugDetailTwoList = new HashMap<>();
                    for (int i = 0; i < bugDetailKeyList.size(); i++) {
                        bugDetailTwoList.put(bugDetailKeyList.get(i), Config.get().getConstBean().getData().getRepairConstant().get(bugDetailKeyList.get(i)));
                    }
                }
            }
        }
        return bugDetailList;
    }


    /**
     * 故障处理明细 二级
     *
     * @param oneIndex 故障明细 1级 下标
     * @return
     */
    public static List<String> getBugDetailTwoList(int oneIndex) {
        return bugDetailTwoList.get(bugDetailKeyList.get(oneIndex));
    }


    /**
     * 到达时限
     *
     * @return
     */
    public static List<String> getArriveList() {
        if (arriveList == null) {
            synchronized (GetConstDataUtils.class) {
                if (arriveList == null) {
//                    arriveList = getDataUtlis(GetConstDataUtils.CONST, Constant.ARRIVE_LIMIT);
                    arriveList = Config.get().getConstBean().getData().getConst().get(Constant.ARRIVE_LIMIT);
                }
            }
        }
        return arriveList;
    }


    public static List<String> getWorkReportTypeList() {
        if (workReportTypeList == null) {
            synchronized (GetConstDataUtils.class) {
                if (workReportTypeList == null) {
                    workReportTypeList = Config.get().getConstBean().getData().getWorkReportConstant().get(Constant.REPORTTYPE);
                }
            }
        }
        return workReportTypeList;
    }

    public static List<String> getStoreDayList() {
        if (storeDayList == null) {
            synchronized (GetConstDataUtils.class) {
                if (storeDayList == null) {
                    storeDayList = Config.get().getConstBean().getData().getRepairConstant().get(Constant.STORE_DAYS);
                }
            }
        }
        return storeDayList;
    }

    public static List<String> getIsNormalList() {
        if (isNormalList == null) {
            synchronized (GetConstDataUtils.class) {
                if (isNormalList == null) {
                    isNormalList = Config.get().getConstBean().getData().getRepairConstant().get(Constant.IS_NORMAL);
                }
            }
        }
        return isNormalList;
    }

    /**
     * 工作等级
     * getShopConstant
     */
    public static List<String> getWorkingLevelList() {
        if (workingLevelList == null) {
            synchronized (GetConstDataUtils.class) {
                if (workingLevelList == null) {
                    workingLevelList = Config.get().getConstBean().getData().getShopConstant().get(Constant.WORKING_LEVEL);
                }
            }
        }
        return workingLevelList;
    }

    /**
     * 专家认证的类型
     * getShopConstant
     */
    public static List<String> getExpertTypeList() {
        if (expertTypeList == null) {
            synchronized (GetConstDataUtils.class) {
                if (expertTypeList == null) {
                    expertTypeList = Config.get().getConstBean().getData().getConst().get(Constant.EXPERT);
                }
            }
        }
        return expertTypeList;
    }

    /**
     * 工作年限
     * getShopConstant
     *
     * @return
     */
    public static List<String> getWorkingYearList() {
        if (workingYearList == null) {
            synchronized (GetConstDataUtils.class) {
                if (workingYearList == null) {
                    workingYearList = Config.get().getConstBean().getData().getShopConstant().get(Constant.WORKING_YEAR);
                }
            }
        }
        return workingYearList;
    }


    public static List<String> getDeviceUnitList() {
        if (deviceUnitList == null) {
            synchronized (GetConstDataUtils.class) {
                if (deviceUnitList == null) {
                    deviceUnitList = Config.get().getConstBean().getData().getDeviceConstant().get(Constant.UNIT);
                }
            }
        }
        return deviceUnitList;
    }

    public static List<String> getMaintainLevelList() {
        if (maintainLevelList == null) {
            synchronized (GetConstDataUtils.class) {
                if (maintainLevelList == null) {
                    maintainLevelList = Config.get().getConstBean().getData().getMainTainConstant().get(Constant.MAINTAIN_LEVEL);
                }
            }
        }
        return maintainLevelList;
    }

    public static List<String> getMaintainStatusList() {
        if (maintainStatusList == null) {
            synchronized (GetConstDataUtils.class) {
                if (maintainStatusList == null) {
                    maintainStatusList = Config.get().getConstBean().getData().getMainTainConstant().get(Constant.MAINTAIN_CONSTANT_STATUS);
                }
            }
        }
        return maintainStatusList;
    }

    public static List<String> getDiplomaList() {
        if (diplomaList == null) {
            synchronized (GetConstDataUtils.class) {
                if (diplomaList == null) {
                    diplomaList = Config.get().getConstBean().getData().getConst().get(Constant.DIPLOMA);
                }
            }
        }
        return diplomaList;
    }


    public static List<String> getMaintainConditionList() {
        if (maintainConditionList == null) {
            synchronized (GetConstDataUtils.class) {
                if (maintainConditionList == null) {
                    maintainConditionList = Config.get().getConstBean().getData().getMainTainConstant().get(Constant.MAINTAIN_ADD_CHECK_CONDITION);
                }
            }
        }
        return maintainConditionList;
    }

    public static List<String> getMaintainOsRuntimeList() {
        if (maintainOsRuntimeList == null) {
            synchronized (GetConstDataUtils.class) {
                if (maintainOsRuntimeList == null) {
                    maintainOsRuntimeList = Config.get().getConstBean().getData().getMainTainConstant().get(Constant.MAINTAIN_OS_RUNTIME);
                }
            }
        }
        return maintainOsRuntimeList;
    }

    public static List<String> getCheckResultList() {
        if (checkResultList == null) {
            synchronized (GetConstDataUtils.class) {
                if (checkResultList == null) {
                    checkResultList = Config.get().getConstBean().getData().getMainTainConstant().get(Constant.CHECK_RESULT);
                }
            }
        }
        return checkResultList;
    }

    /**
     * 合作业务类型
     */
    public static List<String> getCooperationTypeList() {
        if (cooperationTypeList == null) {
            synchronized (GetConstDataUtils.class) {
                if (cooperationTypeList == null) {
                    cooperationTypeList = Config.get().getConstBean().getData().getShopConstant().get(Constant.COOPERATION_TYPE);
                }
            }
        }
        return cooperationTypeList;
    }

    public static List<String> getTaskPublishTypeList() {
        if (taskPublishTypeList == null) {
            synchronized (GetConstDataUtils.class) {
                if (taskPublishTypeList == null) {
                    taskPublishTypeList = Config.get().getConstBean().getData().getTaskPublishConstant().get(Constant.TASK_PUB_TYPE);
                }
            }
        }
        return taskPublishTypeList;
    }

    public static List<String> getDeviceParamList() {
        if (deviceParamList == null) {
            synchronized (GetConstDataUtils.class) {
                if (deviceParamList == null) {
                    deviceParamList = Config.get().getConstBean().getData().getRepairConstant().get(Constant.PARAM);
                }
            }
        }
        return deviceParamList;
    }

    /**
     * 公司规模
     */
    public static List<String> getOrgUnitScaleList() {
        if (orgUnitScaleList == null) {
            synchronized (GetConstDataUtils.class) {
                if (orgUnitScaleList == null) {
                    orgUnitScaleList = Config.get().getConstBean().getData().getConst().get(Constant.ORG_UNIT_SCALE);
                }
            }
        }
        return orgUnitScaleList;
    }


    public static List<String> getTransferCauseList() {
        if (transferCauseList == null) {
            synchronized (GetConstDataUtils.class) {
                if (transferCauseList == null) {
                    transferCauseList = Config.get().getConstBean().getData().getRepairConstant().get(Constant.TRANSFER_CAUSE);
                }
            }
        }
        return transferCauseList;
    }

    public static List<String> getCycleList() {
        if (cycleList == null) {
            synchronized (GetConstDataUtils.class) {
                if (cycleList == null) {
                    cycleList = Config.get().getConstBean().getData().getMainTainConstant().get(Constant.CYCLE);
                }
            }
        }
        return cycleList;
    }

    public static List<String> getPayTypeList() {
        if (payTypeList == null) {
            synchronized (GetConstDataUtils.class) {
                if (payTypeList == null) {
                    payTypeList = Config.get().getConstBean().getData().getShopConstant().get(Constant.PAY_TYPE);
                }
            }
        }
        return payTypeList;
    }

    /**
     * 是否误报
     */
    public static List<String> getRepairMisinformationList() {
        if (repairMisinformationList == null) {
            synchronized (GetConstDataUtils.class) {
                if (repairMisinformationList == null) {
                    repairMisinformationList = Arrays.asList("否", "是");
                }
            }
        }
        return repairMisinformationList;
    }

//    public static List<String> getNoticeTypeList() {
//
//        if (noticeTypeList == null) {
//            synchronized (GetConstDataUtils.class) {
//                if (noticeTypeList == null) {
//                    noticeTypeList = Config.get().getConstBean().getData().getNoticeConst().get(Constant.NOTICE_TYPE);
//                }
//            }
//        }
//        return noticeTypeList;
//    }

    /**
     * 旁路报警
     * getConst
     *
     * @return
     */
    public static List<String> getBypassCause() {
        if (bypassCauseList == null) {
            synchronized (GetConstDataUtils.class) {
                if (bypassCauseList == null) {
                    bypassCauseList = Config.get().getConstBean().getData().getConst().get(Constant.BYPASS_CAUSE);
                }
            }
        }
        return bypassCauseList;
    }

    /**
     * 闯防的报警原因
     * getConst
     *
     * @return
     */
    public static List<String> getThroughCause() {
        if (throughCauseList == null) {
            synchronized (GetConstDataUtils.class) {
                if (throughCauseList == null) {
                    throughCauseList = Config.get().getConstBean().getData().getConst().get(Constant.THROUGH_CAUSE);
                }
            }
        }
        return throughCauseList;
    }

    /**
     * .误报的报警原因
     * getConst
     *
     * @return
     */
    public static List<String> getFlaseCause() {
        if (flaseCauseList == null) {
            synchronized (GetConstDataUtils.class) {
                if (flaseCauseList == null) {
                    flaseCauseList = Config.get().getConstBean().getData().getConst().get(Constant.FALSE_CAUSE);
                }
            }
        }
        return flaseCauseList;
    }

}
