package net.eanfang.client.ui.model;

import java.io.Serializable;

/**
 * Created by yaosheng on 2017/6/17.
 */

public class InfoBackBean implements Serializable {

    /**
     * birthday : 1968-02-03 00:00:00
     * city : 北京市
     * headpic : http://eanfangx.oss-cn-beijing.aliyuncs.com/e4ab3b904454459ead789fe68f9004da.png
     * identity : 453326196512132345
     * nickname : 2
     * realname : 赵要生
     * sex : 女
     * street : 第一大道1号
     * zone : 海淀区
     */

    private String birthday;
    private String city;
    private String headpic;
    private String identity;
    private String nickname;
    private String realname;
    private String sex;
    private String street;
    private String zone;
    //2017年7月17日
    private String departmentname;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHeadpic() {
        return headpic;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getDepartmentname() {
        return departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname;
    }
}
