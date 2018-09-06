package com.eanfang.util;

import com.eanfang.application.EanfangApplication;

import java.util.List;

/**
 * 用户权限 工具类
 *
 * @author jornl
 * @date 2018年8月10日 15:00:13
 */
public class PermKit {

    private static PermKit permKit = new PermKit();

    public static List<String> permList = EanfangApplication.get().getUser().getPerms();

    private void permList() {
        permList = EanfangApplication.get().getUser().getPerms();
    }


    public static PermKit get() {
        if (permKit == null) {
            synchronized (PermKit.class) {
                if (permKit == null) {
                    permKit = new PermKit();
                }
            }
        }
        return permKit;
    }

    /**
     * 验证是否有权限
     *
     * @param permCode
     * @return
     */
    public boolean getPerm(String permCode) {

        if (permList.size() == 0) permList();

        boolean isPerm = false;
        for (int i = 0; i < permList.size(); i++) {
            if (permList.get(i).contains(permCode)) {
                isPerm = true;
                break;
            }
        }
        if (!isPerm) {
            //如果是技师端  并且
            if (EanfangApplication.AppType.equals("worker") && EanfangApplication.get().getUser().getAccount().getAccountExtInfo() == null) {
                ToastUtil.get().showToast(EanfangApplication.get().getApplicationContext(), "请先进行技师认证。");
            } else if (EanfangApplication.get().getCompanyId() != null && EanfangApplication.get().getCompanyId() != 0) {
                ToastUtil.get().showToast(EanfangApplication.get().getApplicationContext(), "暂无权限访问，联系企业管理员添加权限");
            } else {
                ToastUtil.get().showToast(EanfangApplication.get().getApplicationContext(), "暂无权限访问，请创建或加入企业后再试");
            }
        }
        return isPerm;
    }

    /**
     * 查看报修列表权限
     *
     * @return
     */
    public boolean getRepairListPerm() {
        return getPerm(PermCode.REPAIR_LIST_CODE);
    }

    /**
     * 报修新建权限
     *
     * @return
     */
    public boolean getRepairCreatePerm() {
        return getPerm(PermCode.REPAIR_CREATE_CODE);
    }

    /**
     * 报修单详情权限
     *
     * @return
     */
    public boolean getRepairDetailPerm() {
        return getPerm(PermCode.REPAIR_DETAIL_CODE);
    }

    /**
     * 报修故障处理权限
     *
     * @return
     */
    public boolean getRepairBughandlePerm() {
        return getPerm(PermCode.REPAIR_BUGHANDLE_CODE);
    }

    /**
     * 设备库列表权限
     *
     * @return
     */
    public boolean getDeviceArchiveListPerm() {
        return getPerm(PermCode.DEVICEARCHIVE_LIST_CODE);
    }


    /**
     * 设备库详情权限
     *
     * @return
     */
    public boolean getDeviceArchiveDetailPerm() {
        return getPerm(PermCode.DEVICEARCHIVE_DETAIL_CODE);
    }

    /**
     * 故障列表权限
     *
     * @return
     */
    public boolean getFailureListPerm() {
        return getPerm(PermCode.FAILURE_LIST_CODE);
    }

    /**
     * 故障详情权限
     *
     * @return
     */
    public boolean getFailureDetailPerm() {
        return getPerm(PermCode.FAILURE_DETAIL_CODE);
    }

    /**
     * 公司详情权限
     *
     * @return
     */
    public boolean getCompanyDetailPerm() {
        return getPerm(PermCode.COMPANY_INFO_DETAIL_CODE);
    }

    /**
     * 公司编辑认证权限
     *
     * @return
     */
    public boolean getCompanyVerifyPerm() {
        return getPerm(PermCode.COMPANY_INFO_VERIFY_CODE);
    }

    /**
     * 公司撤回认证权限
     *
     * @return
     */
    public boolean getCompanyBackPerm() {
        return getPerm(PermCode.COMPANY_INFO_BACK_CODE);
    }


    /**
     * 部门管理创建部门权限
     *
     * @return
     */
    public boolean getCompanyDepartmentCreatPerm() {
        return getPerm(PermCode.COMPANY_DEPARTMENT_CREATE_CODE);
    }

    /**
     * 部门管理部门列表权限
     *
     * @return
     */
    public boolean getCompanyDepartmentListPerm() {
        return getPerm(PermCode.COMPANY_DEPARTMENT_LIST_CODE);
    }

    /**
     * 员工管理添加新增员工权限
     *
     * @return
     */
    public boolean getCompanyStaffCreatPerm() {
        return getPerm(PermCode.COMPANY_STAFF_CREATE_CODE);
    }


    /**
     * 员工管理授权角色权限
     *
     * @return
     */
    public boolean getCompanyStaffAssignrolePerm() {
        return getPerm(PermCode.COMPANY_STAFF_ASSIGNROLE_CODE);
    }

    /**
     * 合作业务列表权限
     *
     * @return
     */
    public boolean getCooperationListAllPerm() {
        return getPerm(PermCode.CUSTOMER_COOPERATION_LISTALL_CODE);
    }

    /**
     * 合作业务详情权限
     *
     * @return
     */
    public boolean getCooperationDetailPerm() {
        return getPerm(PermCode.CUSTOMER_COOPERATION_DETAIL_CODE);
    }

    /**
     * 合作业务确定绑定权限
     *
     * @return
     */
    public boolean getCooperationConfirmPerm() {
        return getPerm(PermCode.CUSTOMER_COOPERATION_CONFIRM_CODE);
    }

    //---------------报装-------------------
    public boolean getInstallCreatePrem() {
        return getPerm(PermCode.INSTALL_CREATE_CODE);
    }

    public boolean getInstallListPrem() {
        return getPerm(PermCode.INSTALL_LIST_CODE);
    }

    public boolean getInstallDetailPrem() {
        return getPerm(PermCode.INSTALL_DETAIL_CODE);
    }

    public boolean getInstallFinishPrem() {
        return getPerm(PermCode.INSTALL_FINISH_CODE);
    }

    //------------------设计-------------------------
    public boolean getDesignCreatePrem() {
        return getPerm(PermCode.DESIGN_CREATE_CODE);
    }

    public boolean getDesignListPrem() {
        return getPerm(PermCode.DESIGN_LIST_CODE);
    }

    public boolean getDesignDetailPrem() {
        return getPerm(PermCode.DESIGN_DETAIL_CODE);
    }

    public boolean getDesignFinishPrem() {
        return getPerm(PermCode.DESIGN_FINISH_CODE);
    }

    //------------------维修报价-------------------------
    public boolean getQuoteCreatePrem() {
        return getPerm(PermCode.QUOTE_CREATE_CODE);
    }

    public boolean getQuoteListPrem() {
        return getPerm(PermCode.QUOTE_LIST_CODE);
    }

    public boolean getQuoteDetailPrem() {
        return getPerm(PermCode.QUOTE_DETAIL_CODE);
    }

    public boolean getQuoteAgreePrem() {
        return getPerm(PermCode.QUOTE_AGREE_CODE);
    }

    //------------------维保管控-------------------------


    public boolean getMaintenanceListPrem() {
        return getPerm(PermCode.MAINTENANCE_LIST_CODE);
    }

    public boolean getMaintenanceDetailPrem() {
        return getPerm(PermCode.MAINTENANCE_DETAIL_CODE);
    }

    public boolean getMaintenanceBughandlePrem() {
        return getPerm(PermCode.MAINTENANCE_BUGHANDLE_CODE);
    }

    //------------------oa签到-------------------------

    public boolean getSignInCreatePrem() {
        return getPerm(PermCode.SIGNIN_CREATE_CODE);
    }

    public boolean getSignInListPrem() {
        return getPerm(PermCode.SIGNIN_LIST_CODE);
    }

    public boolean getSignInDetailPrem() {
        return getPerm(PermCode.SIGNIN_DETAIL_CODE);
    }
    //------------------oa签退-------------------------

    public boolean getSignOutCreatePrem() {
        return getPerm(PermCode.SIGNOUT_CREATE_CODE);
    }

    public boolean getSignOutListPrem() {
        return getPerm(PermCode.SIGNOUT_LIST_CODE);
    }

    public boolean getSignOutDetailPrem() {
        return getPerm(PermCode.SIGNOUT_DETAIL_CODE);
    }
    //------------------工作汇报-------------------------

    public boolean getWorkReportCreatePrem() {
        return getPerm(PermCode.WORKREPORT_CREATE_CODE);
    }

    public boolean getWorkReportListPrem() {
        return getPerm(PermCode.WORKREPORT_LIST_CODE);
    }

    public boolean getWorkReportDetailPrem() {
        return getPerm(PermCode.WORKREPORT_DETAIL_CODE);
    }
    //------------------布置任务-------------------------

    public boolean getWorkTaskCreatePrem() {
        return getPerm(PermCode.WORKTASK_CREATE_CODE);
    }

    public boolean getWorkTaskListPrem() {
        return getPerm(PermCode.WORKTASK_LIST_CODE);
    }


    public boolean getWorkTaskDetailPrem() {
        return getPerm(PermCode.WORKTASK_DETAIL_CODE);
    }
    //------------------设备检点-------------------------

    public boolean getWorkInspectCreatePrem() {
        return getPerm(PermCode.WORKINSPECT_CREATE_CODE);
    }

    public boolean getWorkInspectListPrem() {
        return getPerm(PermCode.WORKINSPECT_LIST_CODE);
    }


    public boolean getWorkInspectDetailPrem() {
        return getPerm(PermCode.WORKINSPECT_DETAIL_CODE);
    }

    public boolean getWorkInspectDisposePrem() {
        return getPerm(PermCode.WORKINSPECT_DISPOSE_CODE);
    }

    public boolean getWorkInspectVerifyPrem() {
        return getPerm(PermCode.WORKINSPECT_VERIFY_CODE);
    }

    //------------------交接班-------------------------
    public boolean getExchangeCreatePrem() {
        return getPerm(PermCode.EXCHANGE_CREATE_CODE);
    }

    public boolean getExchangeListPrem() {
        return getPerm(PermCode.EXCHANGE_LIST_CODE);
    }


    public boolean getExchangeDetailPrem() {
        return getPerm(PermCode.EXCHANGE_DETAIL_CODE);
    }

    //------------------面谈员工-------------------------
    public boolean getFaceToWorkerCreatePrem() {
        return getPerm(PermCode.FACETOWORKER_CREATE_CODE);
    }

    public boolean getFaceToWorkerListPrem() {
        return getPerm(PermCode.FACETOWORKER_LIST_CODE);
    }


    public boolean getFaceToWorkerDetailPrem() {
        return getPerm(PermCode.FACETOWORKER_DETAIL_CODE);
    }

    //------------------开店日志-------------------------
    public boolean getOpenShopCreatePrem() {
        return getPerm(PermCode.OPENSHOP_CREATE_CODE);
    }

    public boolean getOpenShopListPrem() {
        return getPerm(PermCode.OPENSHOP_LIST_CODE);
    }


    public boolean getOpenShopDetailPrem() {
        return getPerm(PermCode.OPENSHOP_DETAIL_CODE);
    }

    //------------------布防日志-------------------------
    public boolean getProtectionCreatePrem() {
        return getPerm(PermCode.PROTECTION_CREATE_CODE);
    }

    public boolean getProtectionListPrem() {
        return getPerm(PermCode.PROTECTION_LIST_CODE);
    }


    public boolean getProtectionDetailPrem() {
        return getPerm(PermCode.PROTECTION_DETAIL_CODE);
    }

    //------------------技师端项目发包-------------------------
    public boolean getTenderCreatePrem() {
        return getPerm(PermCode.TENDER_BUGHANDLE_CODE);
    }

    public boolean getTenderListPrem() {
        return getPerm(PermCode.TENDER_LIST_CODE);
    }


    public boolean getTenderDetailPrem() {
        return getPerm(PermCode.TENDER_DETAIL_CODE);
    }

    //------------------技师端项目接包-------------------------


    public boolean getBidListPrem() {
        return getPerm(PermCode.BID_LIST_CODE);
    }


    public boolean getBidDetailPrem() {
        return getPerm(PermCode.BID_DETAIL_CODE);
    }

    /**
     * 权限编码
     */
    public interface PermCode {
        /*
            报修权限
         */
        String REPAIR_LIST_CODE = "business:repair:list";
        String REPAIR_CREATE_CODE = "business:repair:create";
        String REPAIR_DETAIL_CODE = "business:repair:detail";
        String REPAIR_BUGHANDLE_CODE = "business:repair:bughandle";
        /*
           报装
         */

        String INSTALL_CREATE_CODE = "business:install:create";
        String INSTALL_LIST_CODE = "business:install:list";
        String INSTALL_DETAIL_CODE = "business:install:detail";
        String INSTALL_FINISH_CODE = "business:install:finish";
        /*
          我要设计
         */
        String DESIGN_CREATE_CODE = "business:design:create";
        String DESIGN_LIST_CODE = "business:design:list";
        String DESIGN_DETAIL_CODE = "business:design:detail";
        String DESIGN_FINISH_CODE = "business:design:finish";
        /*
          维修报价
         */
        String QUOTE_CREATE_CODE = "business:quote:create";
        String QUOTE_LIST_CODE = "business:quote:list";
        String QUOTE_DETAIL_CODE = "business:quote:detail";
        String QUOTE_AGREE_CODE = "business:quote:agree";
        /*
          维保管控
         */
        String MAINTENANCE_BUGHANDLE_CODE = "business:maintenance:bughandle";
        String MAINTENANCE_LIST_CODE = "business:maintenance:list";
        String MAINTENANCE_DETAIL_CODE = "business:maintenance:detail";

        //====================技师端项目发包=======================
        String TENDER_BUGHANDLE_CODE = "business:tender:create";
        String TENDER_LIST_CODE = "business:tender:list";
        String TENDER_DETAIL_CODE = "business:tender:detail";
        //====================技师端项目接包=======================
        String BID_LIST_CODE = "business:bid:list";
        String BID_DETAIL_CODE = "business:bid:detail";
        /*
              签到权限
             */
        String SIGNIN_CREATE_CODE = "oa:signIn:create";
        String SIGNIN_LIST_CODE = "oa:signIn:list";
        String SIGNIN_DETAIL_CODE = "oa:signIn:detail";

        /*
           签退权限
          */
        String SIGNOUT_CREATE_CODE = "oa:signOut:create";
        String SIGNOUT_LIST_CODE = "oa:signOut:list";
        String SIGNOUT_DETAIL_CODE = "oa:signOut:detail";

        /*
         工作汇报
        */
        String WORKREPORT_CREATE_CODE = "oa:workReport:create";
        String WORKREPORT_LIST_CODE = "oa:workReport:list";
        String WORKREPORT_DETAIL_CODE = "oa:workReport:detail";
        /*
         布置任务
        */
        String WORKTASK_CREATE_CODE = "oa:workTask:create";
        String WORKTASK_LIST_CODE = "oa:workTask:list";
        String WORKTASK_DETAIL_CODE = "oa:workTask:detail";

        /*
         设备点检
        */
        String WORKINSPECT_CREATE_CODE = "oa:workInspect:create";
        String WORKINSPECT_LIST_CODE = "oa:workInspect:list";
        String WORKINSPECT_DETAIL_CODE = "oa:workInspect:detail";
        String WORKINSPECT_DISPOSE_CODE = "oa:workInspect:dispose";
        String WORKINSPECT_VERIFY_CODE = "oa:workInspect:verify";

        /*
          交接班
        */
        String EXCHANGE_CREATE_CODE = "oa:exchange:create";
        String EXCHANGE_LIST_CODE = "oa:exchange:list";
        String EXCHANGE_DETAIL_CODE = "oa:exchange:detail";
        /*
           面谈员工
        */
        String FACETOWORKER_CREATE_CODE = "oa:faceToWorker:create";
        String FACETOWORKER_LIST_CODE = "oa:faceToWorker:list";
        String FACETOWORKER_DETAIL_CODE = "oa:faceToWorker:detail";
        /*
           开店日志
        */
        String OPENSHOP_CREATE_CODE = "oa:openShop:create";
        String OPENSHOP_LIST_CODE = "oa:openShop:list";
        String OPENSHOP_DETAIL_CODE = "oa:openShop:detail";

        /*
            布防日志
       */
        String PROTECTION_CREATE_CODE = "oa:protection:create";
        String PROTECTION_LIST_CODE = "oa:protection:list";
        String PROTECTION_DETAIL_CODE = "oa:protection:detail";
        /*
            设备库
         */

        String DEVICEARCHIVE_LIST_CODE = "company:deviceArchive:list";
        String DEVICEARCHIVE_DETAIL_CODE = "company:deviceArchive:detail";

        /*
            故障权限
         */

        String FAILURE_LIST_CODE = "business:failure:list";
        String FAILURE_DETAIL_CODE = "business:failure:detail";


        /*
         企业管理
         */
        String COMPANY_INFO_DETAIL_CODE = "company:info:detail";
        String COMPANY_INFO_VERIFY_CODE = "company:info:verify";
        String COMPANY_INFO_BACK_CODE = "company:info:back";
        //员工管理
        String COMPANY_STAFF_CREATE_CODE = "company:staff:create";
        String COMPANY_STAFF_ASSIGNROLE_CODE = "company:staff:assignRole";
        //部门管理
        String COMPANY_DEPARTMENT_CREATE_CODE = "company:department:create";
        String COMPANY_DEPARTMENT_LIST_CODE = "company:department:list";
        //合作业务
        String CUSTOMER_COOPERATION_LISTALL_CODE = "customer:cooperation:listAll";
        String CUSTOMER_COOPERATION_DETAIL_CODE = "customer:cooperation:detail";
        String CUSTOMER_COOPERATION_CONFIRM_CODE = "customer:cooperation:confirm";


    }
}
