package com.yaf.base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yaf.sys.entity.AccountEntity;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


/**
 * 技师认证信息
 *
 * @author jornlin
 * @email jornlin@foxmail.com
 * @date 2017-11-30 20:02:44
 */
@TableName(value = "tech_worker_verify")
@Getter
@Setter
public class WorkerVerifyEntity extends TechWorkerVerifyEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 所属的account
     */
    @TableField(exist = false)
    private AccountEntity accountEntity;
    /**
     * 技师服务的 业务类型 id
     */
    @TableField(exist = false)
    private List<Long> businessList;
    /**
     * 技师的 服务类型 id
     */
    @TableField(exist = false)
    private List<Long> serviceList;
    /**
     * 技师服务的 区域 id
     */
    @TableField(exist = false)
    private List<Long> regionList;




    /*
     *===================================================================================================================================================
     *-----------------------------------------------------------------华丽的分割线------------------------------------------------------------------------
     *===================================================================================================================================================
     */


}
