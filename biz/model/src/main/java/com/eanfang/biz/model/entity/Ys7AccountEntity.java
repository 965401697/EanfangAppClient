package com.eanfang.biz.model.entity;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * 萤石云账户表
 *
 * @author lixu
 * @email 15940525612@163.com
 * @date 2019-09-02 11:39:44
 */
@Getter
@Setter
@Accessors(chain = true)
public class Ys7AccountEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    private Long accountId;
    //子账号id
    //@TableField(value = "ys7_account_id")
    private String ys7AccountId;
    //子账户名
    //@TableField(value = "ys7_account_name")
    private String ys7AccountName;
    //公司登陆密码
    //@TableField(value = "ys7_passwd")
    private String ys7Passwd;
    //公司名称
    //@TableField(value = "org_name")
    private String orgName;
    //公司id
    //@TableField(value = "company_id")
    private Long companyId;
    //总公司id
    //@TableField(value = "top_company_id")
    private Long topCompanyId;
    //管理员userId
    //@TableField(value = "admin_user_id")
    private Long adminUserId;
    //创建时间
    //@TableField(value = "create_time")
    private Date createTime;
    //创建人user_id
    //@TableField(value = "create_user_id")
    private Long createUserId;
    //子账户状态，0为禁用，1为启用, 2失效
    //@TableField(value = "status")
    private Integer status;
    //修改时间
    //@TableField(value = "update_time")
    private Date updateTime;
    //修改人user_id
    //@TableField(value = "update_user_id")
    private Long updateUserId;

    /**
     * 脱岗监测配置
     */
//    private StationDetectConfigEntity stationConfigEntity;

    /**
     * 实时监控配置
     */
//    private RealTimeConfigEntity realTimeConfigEntity;

    /**
     * 人脸检测配置
     */
//    private FaceDetectConfigEntity faceDetectConfigEntity;

    /**
     * 管理员账户
     */
    private AccountEntity adminAccount;

    /**
     * 创建人账户
     */
    private AccountEntity createAccount;

    /**
     * 子账号token
     **/
    private String subAccountToken;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Ys7AccountEntity) {
            if (this.accountId == null || other == null) {
                return false;
            }
            return this.accountId.equals(((Ys7AccountEntity) other).accountId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((accountId == null) ? 0 : accountId.hashCode());
        return result;
    }
}
