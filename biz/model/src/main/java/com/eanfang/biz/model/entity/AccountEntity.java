package com.eanfang.biz.model.entity;


import android.os.Build;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;


/**
 * 账号实体
 */
@Accessors(chain = true)
@Getter
@Setter
public class AccountEntity implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER，普通自增长使用IdType.ID_AUTO
    private Long accId;

    //账号类型：0普通账号，1内置账号，2技师账号，3已认证技师，4专家账号，5已认证专家
    private Integer accType;

    //用户名
    private String userName;
    //手机
    private String mobile;

    //昵称
    private String nickName;

    //电邮
    private String email;

    //头像
    private String avatar;

    //密码
    private String passwd;

    private String qrCode;

    //真实姓名
    private String realName;

    //注册时间
    private Date regTime;

    //状态 0：正常  1：禁用  2：删除 3:禁用且删除
    private Integer status;

    //登录次数
    private Integer loginCount;

    //最后登录时间
    private Date lastLoginTime;

    //性别0女1男
    private Integer gender;

    //生日
    private Date birthday;

    //证件号码
    private String idCard;

    //所在城市编号
    private String areaCode;

    //详细地址
    private String address;

    //融云token，如果为null表示没有注册过
    private String rcloudToken;
    private String openId;
    private Integer regFrom;
    private Integer mpFrom;
    //是否为新用户，0不新 1新用户
    private Integer isNew;
    //个人简介
    private String personalNote;


    /*手工代码写在下面*/
    /**
     * 用户信息扩展
     */
    private Object accountExtInfo;

    private UserEntity defaultUser;

    private Long nullUser;

    private AccountEntity inviteAccount;
    /**
     * 邀请人手机号
     */
    private String invitePhone;

    /*归属的公司列表*/
    List<OrgEntity> belongCompanys;

    /*归属的部门列表*/
    Set<Long> belongDepartments;

    /*适合当前域名的公司id列表*/
    Set<Long> allowCurDomainCompanys;

    /**
     * 判断当前密码是否是简单密码（空 || 默认手机后六位 || 默认用户名）
     */
    private boolean simplePwd;

    /**
     * 给前端返回生日日期的字符串
     */
    private String birthMonthDay;
    /**
     * 技师认证
     */
    private WorkerEntity workerEntity;
    /**
     * 个人简介
     */
    private String intro;
    /**
     * 所在省市
     */
    private String areaInfo;
    /**
     * 公司信息
     */
    private OrgEntity orgEntity;
    /**
     * 技师认证 0是1否
     */
    private int realVerify;

    /**
     * 用户信息是否发生改变（注意：调用此方法调用者的值会发生改变）
     *
     * @param other
     * @return true: 发生改变，并把变化值赋值到调用者上 false：未发生改变
     */
    public boolean isChanged(AccountEntity other) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (!Objects.equals(this.avatar, other.avatar)
                    || !Objects.equals(this.nickName, other.nickName)
                    || !Objects.equals(this.realName, other.realName)
                    || !Objects.equals(this.birthday, other.birthday)
                    || !Objects.equals(this.gender, other.gender)
                    || !Objects.equals(this.address, other.address)
                    || !Objects.equals(this.personalNote, other.personalNote)
                    || !Objects.equals(this.areaCode, other.areaCode)) {
                this.avatar = other.avatar;
                this.nickName = other.nickName;
                this.realName = other.realName;
                this.birthday = other.birthday;
                this.gender = other.gender;
                this.address = other.address;
                this.personalNote = other.personalNote;
                this.areaCode = other.areaCode;
                return true;
            }
        }
        return false;
    }

    private String firstLetter; // 拼音的首字母
    private String pinyin; // 姓名对应的拼音
}
