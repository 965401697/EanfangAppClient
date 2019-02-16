package com.yaf.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;


/**
 * 基础数据
 *
 * @author xuding
 * @email 29698868@qq.com
 * @date 2017-11-30 12:44:27
 */

@TableName(value = "sys_base_data")
@Getter
@Setter
public class BaseDataEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //数据标识
    //@TableField(value = "data_id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER，普通自增长使用IdType.ID_AUTO
    @TableId(value = "data_id", type = IdType.AUTO)
    private Integer dataId;

    //编码
    //@TableField(value = "data_code")
    @Size(min = 0, max = 20)
    private String dataCode;

    //数据名
    //@TableField(value = "data_name")
    @Size(min = 0, max = 20)
    private String dataName;

    //排序号
    //@TableField(value = "sort_num")
    @NotNull
    @Digits(integer = 5, fraction = 0)
    private Integer sortNum;

    //备注
    //@TableField(value = "remark_info")
    @Size(min = 0, max = 200)
    private String remarkInfo;
    /**
     * 归属部门
     */

    @TableField(exist = false)
    private Integer dataType;
    @TableField(exist = false)
    private Long parentId;
    @TableField(exist = false)
    private List<BaseDataEntity> children = null;
    @TableField(exist = false)
    private Integer level;
    @TableField(exist = false)
    private boolean isLeaf;
    @TableField(exist = false)
    private boolean isCheck;

    /**
     * 如果对象类型是User,先比较hashcode，一致的场合再比较每个属性的值
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (obj instanceof BaseDataEntity) {
            BaseDataEntity b = (BaseDataEntity) obj;

            // 比较每个属性的值 一致时才返回true
//                if (preson.id.equals(this.id) && preson.name.equals(this.name))
            if (b.getDataId().equals(this.getDataId()))
                return true;
        }
        return false;
    }

    /**
     * 重写hashcode 方法，返回的hashCode不一样才再去比较每个属性的值
     */
    @Override
    public int hashCode() {
        return this.getDataId().hashCode();
    }
}
