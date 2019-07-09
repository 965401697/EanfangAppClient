package net.eanfang.client.ui.activity.leave_post.bean;

import com.eanfang.biz.model.entity.AccountEntity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author liangkailun
 * Date ：2019-07-04
 * Describe :添加岗位上传
 */
@Getter
@Setter
public class LeavePostAddPostPostBean implements Serializable {
    private String stationName;
    private String stationArea;
    private String stationCode;
    private String stationAddress;
    private String stationPlaceCode;
    private String status;
    private int intervalLength;
    private DeviceEntityBean deviceEntity;
    private List<ChargeStaffListBean> dutyUserList;
    private List<ChargeStaffListBean> chargeUserList;

    @NoArgsConstructor
    @Data
    public static class DeviceEntityBean implements Serializable {
        private int belongTo;
        private String companyId;
        private int deviceId;
        private String deviceName;
        private int isInUse;
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
