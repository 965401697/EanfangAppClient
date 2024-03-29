package com.eanfang.biz.model.entity;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;


/**
 * 数据绑定
 *
 * @author xuding
 * @email 29698868@qq.com
 * @date 2017-11-27 16:46:38
 */

public class BaseData2userEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //数据绑定ID
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER，普通自增长使用IdType.ID_AUTO
    private Long id;

    //用户ID
    private Long userId;

    //数据标识
    private Long dataId;

    //归属公司
    private Long companyId;

    //数据类型
    private Integer dataType;

    /**
     * @Author yorkz
     * @Description 用户实体类
     * @Date 15:30 2019/1/22
     * @param
     * @return
     **/
    private UserEntity userEntity;

    /**
     * @Author yorkz
     * @Description BaseData实体类
     * @Date 15:30 2019/1/22
     * @param
     * @return
     **/
    private BaseDataEntity baseDataEntity;

    /**
     * 获取：数据绑定ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：数据绑定ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置：用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取：数据标识
     */
    public Long getDataId() {
        return dataId;
    }

    /**
     * 设置：数据标识
     */
    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    /**
     * 获取：归属公司
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * 设置：归属公司
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /*手工代码写在下面*/

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof BaseData2userEntity) {
            if (this.id == null || other == null) {
                return false;
            }

            return this.id.equals(((BaseData2userEntity) other).id);
        }
        return false;
    }

    public Integer getDataType() {
        return this.dataType;
    }

    public UserEntity getUserEntity() {
        return this.userEntity;
    }

    public BaseDataEntity getBaseDataEntity() {
        return this.baseDataEntity;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public void setBaseDataEntity(BaseDataEntity baseDataEntity) {
        this.baseDataEntity = baseDataEntity;
    }
}
