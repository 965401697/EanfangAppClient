//package net.eanfang.client.ui.activity.leave_post.bean;
//
//import java.util.List;
//
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
///**
// * @author liangkailun
// * Date ：2019-06-27
// * Describe :岗位列表页
// */
//@NoArgsConstructor
//@Data
//public class LeavePostDeviceListBean {
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
//
//        private String companyId;
//        private int detectId;
//        private DeviceEntityBean deviceEntity;
//        private int deviceId;
//        private String deviceName;
//        private String stationArea;
//        private String stationCode;
//        private int stationId;
//        private String stationName;
//        private String stationPlaceCode;
//        private String stationPlaceName;
//        private int status;
//        private UpdateAccountBean updateAccount;
//        private String updateTime;
//        private String updateUserId;

//        private LeavePostDetailBean mLeavePostDetailBean;
//
//        public LeavePostDetailBean getLeavePostDetailBean() {
//            mLeavePostDetailBean = new LeavePostDetailBean();
//            mLeavePostDetailBean.setName(stationName);
//            mLeavePostDetailBean.setAreaCode(stationPlaceCode);
//            mLeavePostDetailBean.setPosition(stationArea);
//            mLeavePostDetailBean.setStatus(status);
//            if (deviceEntity != null) {
//                mLeavePostDetailBean.setImg(deviceEntity.livePic);
//            }
//            mLeavePostDetailBean.setPageType(0);
//            mLeavePostDetailBean.setStationId(stationId);
//            return mLeavePostDetailBean;
//        }
//
//        @NoArgsConstructor
//        @Data
//        public static class DeviceEntityBean {
//
//            private String beginDate;
//            private int belongTo;
//            private String companyId;
//            private String createTime;
//            private String createUserId;
//            private int detectId;
//            private int deviceId;
//            private String deviceName;
//            private String endDate;
//            private int isInUse;
//            private String livePic;
//            private String orgName;
//            private int status;
//            private String topCompanyId;
//            private String updateTime;
//            private String updateUserId;
//            private int ys7Channel;
//            private String ys7DeviceSerial;
//        }
//
//        @NoArgsConstructor
//        @Data
//        public static class UpdateAccountBean {
//            /**
//             * accId : 979985982284677121
//             * accType : 2
//             * avatar : default/default_avatar.png
//             * email : 13800138002@qq.com
//             * mobile : 13800138002
//             * nickName : 管理员
//             * realName : 管理员
//             * simplePwd : false
//             * status : 0
//             */
//
//            private String accId;
//            private int accType;
//            private String avatar;
//            private String email;
//            private String mobile;
//            private String nickName;
//            private String realName;
//            private boolean simplePwd;
//            private int status;
//        }
//    }
//}
