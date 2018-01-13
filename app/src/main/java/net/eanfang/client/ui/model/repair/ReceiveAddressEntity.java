package net.eanfang.client.ui.model.repair;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户的 收货地址信息表
 *
 * @author jornlin
 * @email jornlin@foxmail.com
 * @date 2017-12-27 11:20:04
 */
@TableName(value = "bus_receive_address")
public class ReceiveAddressEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键自增
    //@TableField(value = "id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    //用户id
    //@TableField(value = "user_id")
    private Long userId;
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
    private Integer isDefault;
    //创建时间
    //@TableField(value = "create_time")
    private Date createTime;

    /**
     * 设置：主键自增
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：主键自增
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取：用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置：收货人姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：收货人姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置：收货人电话号码
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取：收货人电话号码
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置：收获地址
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 获取：收获地址
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置：收获城市
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取：收获城市
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置：收获区县
     */
    public void setCounty(String county) {
        this.county = county;
    }

    /**
     * 获取：收获区县
     */
    public String getCounty() {
        return county;
    }

    /**
     * 设置：收获详细地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取：收获详细地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置：是否默认：0不默认，1默认
     */
    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * 获取：是否默认：0不默认，1默认
     */
    public Integer getIsDefault() {
        return isDefault;
    }

    /**
     * 设置：创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取：创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ReceiveAddressEntity) {
            if (this.id == null || other == null) {
                return false;
            }

            return this.id.equals(((ReceiveAddressEntity) other).id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
}
