package com.eanfang.util;

import android.content.Context;
import android.util.Log;

import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.greendao.downloader.FinalDataUtils;
import com.yaf.base.entity.FinalDataEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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

    private static List<String> noticeTypeList;

    private static List<String> dataList;//数据库查询数据集合

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
                }
            }
        }
        return bugDetailList;
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

    public static List<String> getNoticeTypeList() {

        if (noticeTypeList == null) {
            synchronized (GetConstDataUtils.class) {
                if (noticeTypeList == null) {
                    noticeTypeList = Config.get().getConstBean().getData().getNoticeConst().get(Constant.NOTICE_TYPE);
                }
            }
        }
        return noticeTypeList;
    }

    /**
     * 根据条件查询
     *
     * @param value
     * @param value1
     * @return
     */
    public static List<String> getDataUtlis(String value, String value1) {


        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        if (dataList.size() > 0) dataList.clear();

        String sql = "where FILE_NAME=? and LIST=?";
        String[] condition = new String[]{value, value1};
        try {
            List<FinalDataEntity> l = FinalDataUtils.getInstance().queryFinalDataByNativeSql(sql, condition);
            for (FinalDataEntity e : l) {
                dataList.add(e.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * 查询条件的常量
     */
    public static String CONST = "Const";
    public static String WorkReportConstant;
    public static String RepairConstant;
    public static String ShopConstant;
    public static String WorkInspectConstant;
    public static String DESIGNORDERCONSTANT = "DesignOrderConstant";
    public static String MainTainConstant;
    public static String DeviceConstant;
    public static String QuoteOrderConstant;
    public static String InstallOrderConstant;
    public static String TaskPublishConstant;
    public static String NoticeConst;

}
