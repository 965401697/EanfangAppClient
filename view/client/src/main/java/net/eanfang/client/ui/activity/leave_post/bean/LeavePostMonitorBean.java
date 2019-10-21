//package net.eanfang.client.ui.activity.leave_post.bean;
//
//import com.eanfang.biz.model.bean.LeavePostDetailBean;
//import com.eanfang.biz.model.entity.Ys7DevicesEntity;
//
//import java.util.List;
//
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
///**
// * @author liangkailun
// * Date ：2019-07-02
// * Describe :监测设备
// */
//@NoArgsConstructor
//@Data
//public class LeavePostMonitorBean {
//
//    private int currPage;
//    private int pageSize;
//    private int totalCount;
//    private int totalPage;
//    private List<ListBean> list;
//
//    @NoArgsConstructor
//    @Data
//    public static class ListBean {
//        /**
//         * beginDate : 2019-06-29 00:00:00
//         * belongTo : 0
//         * companyId : 1134307027488649218
//         * createTime : 2019-06-25 17:16:46
//         * createUserId : 979985982335008770
//         * detectId : 21
//         * deviceId : 9
//         * deviceName : 张恒的测试设备
//         * endDate : 2019-07-12 00:00:00
//         * isInUse : 0
//         * orgName : 测试太卡了
//         * status : 1
//         * topCompanyId : 1134307027488649218
//         * updateTime : 2019-06-27 13:54:38
//         * updateUserId : 979985982335008770
//         * ys7Channel : 1
//         * ys7DeviceSerial : D18812270
//         */
//
//        private String beginDate;
//        private int belongTo;
//        private String companyId;
//        private String createTime;
//        private String createUserId;
//        private int detectId;
//        private int deviceId;
//        private String deviceName;
//        private String endDate;
//        private String livePic;
//        private int isInUse;
//        private String orgName;
//        private int status;
//        private String topCompanyId;
//        private String updateTime;
//        private String updateUserId;
//        private int ys7Channel;
//        private String ys7DeviceSerial;
//        private LeavePostDetailBean mLeavePostDetailBean;
//
//        public LeavePostDetailBean getLeavePostDetailBean(){
//            mLeavePostDetailBean = new LeavePostDetailBean();
//            mLeavePostDetailBean.setName(deviceName);
//            mLeavePostDetailBean.setImg(livePic);
//            mLeavePostDetailBean.setSerialNum(ys7DeviceSerial);
//            mLeavePostDetailBean.setStatus(status);
//            mLeavePostDetailBean.setUse(isInUse);
//            mLeavePostDetailBean.setPageType(1);
//            Ys7DevicesEntity deviceEntityBean = new Ys7DevicesEntity();
//            deviceEntityBean.setBelongTo(belongTo);
//            deviceEntityBean.setCompanyId(Long.valueOf(companyId));
//            deviceEntityBean.setDeviceId(Long.valueOf(deviceId));
//            deviceEntityBean.setDeviceName(deviceName);
//            deviceEntityBean.setIsInUse(isInUse);
//            mLeavePostDetailBean.setMDeviceEntityBean(deviceEntityBean);
//            return mLeavePostDetailBean;
//        }
//    }
//}
