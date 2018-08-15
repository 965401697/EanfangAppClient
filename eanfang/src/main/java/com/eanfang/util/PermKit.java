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

//    private static List<String> permList;

    private List<String> permList() {
        return EanfangApplication.get().getUser().getPerms();
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
        boolean isPerm = false;
        for (int i = 0; i < permKit.permList().size(); i++) {
            if (permKit.permList().get(i).contains(permCode)) {
                isPerm = true;
                break;
            }
        }
        if (!isPerm) {
            if (EanfangApplication.get().getCompanyId() != null && EanfangApplication.get().getCompanyId() != 0) {
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
     * 故障列表权限
     *
     * @return
     */
    public boolean getFailureListPerm() {
        return getPerm(PermCode.FAILURE_LIST_CODE);
    }

    public boolean getFailureDetailPerm() {
        return getPerm(PermCode.FAILURE_DETAIL_CODE);
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
            故障权限
         */

        String FAILURE_LIST_CODE = "business:failure:list";
        String FAILURE_DETAIL_CODE = "business:failure:detail";

    }
}
