package com.eanfang.biz.model.bean;

import java.io.Serializable;

/**
 * Created by O u r on 2018/6/1.
 */

public class RoleBean implements Serializable {

    /**
     * roleId : 959265127091822593
     * roleName : CEO
     * roleType : 3
     */

    private String roleId;
    private String roleName;
    private int roleType;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getRoleType() {
        return roleType;
    }

    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }
}
