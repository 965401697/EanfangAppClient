package net.eanfang.client.ui.activity.leave_post.bean;

import com.eanfang.biz.model.TemplateBean;
import com.eanfang.biz.model.entity.AccountEntity;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liangkailun
 * Date ：2019-06-27
 * Describe :图像查岗详情
 */
@NoArgsConstructor
@Data
public class LeavePostDeviceInfoBean {

    private String companyId;
    private int detectId;
    private DeviceEntityBean deviceEntity;
    private int deviceId;
    private String deviceName;
    private String stationArea;
    private String stationCode;
    private int stationId;
    private String stationName;
    private String stationPlaceCode;
    private String stationPlaceName;
    private int status;
    private List<ChargeStaffListBean> chargeStaffList;
    private List<ChargeStaffListBean> dutyStaffList;
    /**
     * chargeStaffList : []
     * createTime : 2019-07-06 11:28:15
     * createUserId : 979993533551525889
     * deviceEntity : {"beginDate":"2019-07-06 00:00:00","belongTo":0,"companyId":"1126289639904178178","createTime":"2019-07-06 11:08:41","createUserId":"979985982335008770","detectId":29,"deviceId":27,"deviceName":"测试离岗设备","endDate":"2019-07-06 00:00:00","isInUse":1,"livePic":"ys7/img/2420190706181251.jpg","orgName":"还是辣的很","status":1,"topCompanyId":"1126289639904178178","updateTime":"2019-07-06 11:08:41","updateUserId":"979985982335008770","ys7Channel":1,"ys7DeviceSerial":"D18812270"}
     * dutyStaffList : []
     * imgHeight : 160
     * imgWidth : 235
     * imgX : 400
     * imgY : 140
     * intervalLength : 2
     * stationAddress : 一个测试的详细的地址
     * topCompanyId : 1126289639904178178
     * updateTime : 2019-07-06 11:29:48
     * updateUserId : 979985982335008770
     */

    private String createTime;
    private String createUserId;
    private DeviceEntityBean deviceEntityX;
    private int imgHeight;
    private int imgWidth;
    private int imgX;
    private int imgY;
    private int intervalLength;
    private String stationAddress;
    private String topCompanyId;
    private String updateTime;
    private String updateUserId;
    private List<?> chargeStaffListX;
    private List<?> dutyStaffListX;


    @NoArgsConstructor
    @Data
    public static class ChargeStaffListBean {
        private AccountEntity accountEntity;
        private String companyId;
        private int id;
        private String mobile;
        private String name;
        private int staffType;
        private int stationId;
        private int status;
        private String userId;
        private TemplateBean.Preson mPerson;

        public TemplateBean.Preson getPerson(){
            mPerson = new TemplateBean.Preson();
            mPerson.setMobile(mobile);
            mPerson.setName(name);
            mPerson.setProtraivat(accountEntity.getAvatar());
            return mPerson;
        }
        /**
         * 布局类型
         */
        private int type = 1;
        /**
         * 标题
         */
        private String title;


    }

    @NoArgsConstructor
    @Data
    public static class DeviceEntityBean {
        /**
         * beginDate : 2019-07-06 00:00:00
         * belongTo : 0
         * companyId : 1126289639904178178
         * createTime : 2019-07-06 11:08:41
         * createUserId : 979985982335008770
         * detectId : 29
         * deviceId : 27
         * deviceName : 测试离岗设备
         * endDate : 2019-07-06 00:00:00
         * isInUse : 1
         * livePic : ys7/img/2420190706181251.jpg
         * orgName : 还是辣的很
         * status : 1
         * topCompanyId : 1126289639904178178
         * updateTime : 2019-07-06 11:08:41
         * updateUserId : 979985982335008770
         * ys7Channel : 1
         * ys7DeviceSerial : D18812270
         */

        private String beginDate;
        private int belongTo;
        private String companyIdX;
        private String createTime;
        private String createUserId;
        private int detectIdX;
        private int deviceIdX;
        private String deviceNameX;
        private String endDate;
        private int isInUse;
        private String livePic;
        private String orgName;
        private int statusX;
        private String topCompanyId;
        private String updateTime;
        private String updateUserId;
        private int ys7Channel;
        private String ys7DeviceSerial;
    }
}
