package com.eanfang.model.reapair;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 用户的 收货地址信息表
 *
 * @author grr
 * @email 1204290455@qq.com
 * @date 2019-4-17 11:20:04
 */
@TableName(value = "bus_receive_address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepairPersonalInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键自增
    //@TableField(value = "id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    //用户accid之前是userid
    //@TableField(value = "acc_id")
    private Long accId;
    //收货人姓名
    //@TableField(value = "name")
    private String name;
    //收货人电话号码
    //@TableField(value = "phone")
    private String phone;
    //收获地址
    //@TableField(value = "province")
    private String province;
    //收获城市
    //@TableField(value = "city")
    private String city;
    //收获区县
    //@TableField(value = "county")
    private String county;
    //收获详细地址
    //@TableField(value = "address")
    private String address;
    //是否默认：0不默认，1默认
    //@TableField(value = "is_default")
    private int isDefault;
    //创建时间
    //@TableField(value = "create_time")
    private Date createTime;
    //性别0女1男
    //@TableField(value = "gender")
    private int gender;
    //用户自己设置的标签名
    //@TableField(value = "select_address")
    private String selectAddress;
    //公司名字
    //@TableField(value = "conmpany_name")
    private String conmpanyName;
    //区/县 编码
    //@TableField(value = "place_code")
    private String placeCode;
    //区域code对应的id
    //@TableField(value = "place_id")
    private int placeId;
    //纬度
    //@TableField(value = "latitude")
    private String latitude;
    //经度
    //@TableField(value = "longitude")
    private String longitude;

}
