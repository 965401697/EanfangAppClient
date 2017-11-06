package net.eanfang.client.ui.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * User  用户
 *
 * @author wen
 * @desc 用户信息实体类
 * @date 2017/3/2
 */
public class User implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    /**
     * account : 18612130370
     * address : 北京海淀区回龙观1街区coast
     * companyname : 星巴克
     * companyuid : 1f9aa90b68894dac972e39b036fc1deb
     * doorfee : 50.00
     * personuid : 957fb9a7d44c41ff937a974b85f747bf
     * role : 2
     * token : 5e054b6e7667d74bfbfd33e0fcd2775bYjUfwy9F63wNupe2ZajB2DuJCzQHSC+YBnfTvSvqUfw=
     */

    private String account;
    private String address;
    @SerializedName("companyname")
    private String companyName;
    @SerializedName("companyuid")
    private String companyId;
    @SerializedName("personuid")
    private String personId;
    private String role;
    private String token;
    private String infoGood;
    private String headpic;
    private String companyverify;
    //2017年7月25日
    private String departmentname;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public String getCompanyverify() {
        return companyverify;
    }

    public void setCompanyverify(String companyverify) {
        this.companyverify = companyverify;
    }

    /**
     * verify : 1
     */

    private String verify;

    public String getInfoGood() {
        return infoGood;
    }

    public void setInfoGood(String infoGood) {
        this.infoGood = infoGood;
    }

    public String getHeadpic() {
        return headpic;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }


    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "account='" + account + '\'' +
                ", address='" + address + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyId='" + companyId + '\'' +
                ", personId='" + personId + '\'' +
                ", role='" + role + '\'' +
                ", token='" + token + '\'' +
                ", infoGood='" + infoGood + '\'' +
                ", headpic='" + headpic + '\'' +
                ", companyverify='" + companyverify + '\'' +
                ", departmentname='" + departmentname + '\'' +
                ", name='" + name + '\'' +
                ", verify='" + verify + '\'' +
                '}';
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getDepartmentname() {
        return departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname;
    }
}
