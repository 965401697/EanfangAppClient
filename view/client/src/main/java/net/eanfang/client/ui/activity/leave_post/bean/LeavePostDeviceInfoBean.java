package net.eanfang.client.ui.activity.leave_post.bean;

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

    @NoArgsConstructor
    @Data
    public static class DeviceEntityBean {
        /**
         * beginDate : 2019-06-24 16:37:02
         * belongTo : 0
         * companyId : 1134307027488649218
         * createTime : 2019-06-24 16:29:40
         * createUserId : 979993533551525889
         * detectId : 21
         * deviceId : 5
         * deviceName : 测试绑定设备
         * endDate : 2019-06-24 16:37:05
         * isInUse : 0
         * livePic : http://img.ys7.com/group9/M00/26/D4/CmGCnF0R9viABDsZAAQAAKfuF0k065.jpg
         * status : 2
         * topCompanyId : 1134307027488649218
         * updateTime : 2019-06-25 16:48:02
         * updateUserId : 979985982335008770
         * ys7Channel : 1
         * ys7DeviceSerial : C86980223
         */

        private String beginDate;
        private int belongTo;
        private String companyId;
        private String createTime;
        private String createUserId;
        private int detectId;
        private int deviceId;
        private String deviceName;
        private String endDate;
        private int isInUse;
        private String livePic;
        private int status;
        private String topCompanyId;
        private String updateTime;
        private String updateUserId;
        private int ys7Channel;
        private String ys7DeviceSerial;
    }

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
        /**
         * 布局类型
         */
        private int type = 1;
        /**
         * 标题
         */
        private String title;
    }

}
