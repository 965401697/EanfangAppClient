package net.eanfang.client.ui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wen on 2017/5/18.
 */

public class FillRepairInfoBean implements Serializable {

    /**
     * ordernum : 32020170325224954787062
     * overtime : 2017-03-29 20:12:15
     * worktime : 10
     * boxone : 1
     * boxtwo : 1
     * boxthree : 0
     * pic1 : wefew
     * pic2 : wefew
     * pic3 : wefew
     * pic4 : wefew
     * pic5 : wefew
     * pic6 : wefew
     * hangstatus : 0
     * bughandledetaillist : [{"title":"1","instrument":"摄像头设备","modelnum":"海康威视x21","equipmentcode":"GR111","equipmentposition":"在头顶","description":"坏了","checkprocess":"拍了两下","cause":"老化","handle":"换高压包","pic1":"wewff","pic2":"wewff","pic3":"wewff","pic4":"wewff","pic5":"wewff","pic6":"wewff","pic7":"wewff","pic8":"wewff","pic9":"wewff","bughandledetailparamlist":[{"name":"电压","value":"220v"},{"name":"温度","value":"50度"}],"bughandledetailmateriallist":[{"equipmenttype":"监视器","equipmentname":"模拟枪机","equipmentmodel":"OXO11","num":"2","memo":"质量一般就行了"},{"equipmenttype":"锡丝","equipmentname":"有铅","equipmentmodel":"leadxs","num":"1","memo":"用的不多"}]},{"title":"2","instrument":"线路","modelnum":"cat5","equipmentcode":"xxs","equipmentposition":"在脚下","description":"断了","checkprocess":"拉了两下","cause":"老鼠","handle":"接起来","pic1":"wewff","pic2":"wewff","pic3":"wewff","pic4":"wewff","pic5":"wewff","pic6":"wewff","pic7":"wewff","pic8":"wewff","pic9":"wewff","bughandledetailparamlist":[{"name":"线长","value":"20m"},{"name":"直径","value":"5mm"}],"bughandledetailmateriallist":[{"equipmenttype":"剪刀","equipmentname":"小剪子","equipmentmodel":"sss1","num":"2","memo":"很锋利"},{"equipmenttype":"胶布","equipmentname":"电老虎","equipmentmodel":"ddsx","num":"1","memo":"用的多"}]}]
     */

    private String ordernum;
    private String overtime;
    private String worktime;
    //    private int boxone;
//    private int boxtwo;
//    private String boxthree;
    private int hangstatus;
    private List<BughandledetaillistBean> bughandledetaillist;
    /**
     * causeone : 1
     * causetwo : 1
     * causethree : 0
     * causefour : 0
     * receiveworkeruid : c65838b399fb444f95b88a085f4bea1d
     */

    private int causeone;
    private int causetwo;
    private int causethree;
    private int causefour;
    private String receiveworkeruid;
    private String pic1;
    private String pic2;
    private String pic3;
    private String pic4;
    private String pic5;
    private String pic6;
    private String pic7;
    private String pic8;
    private String pic9;
    //2017年10月19日
    private String pic10;
    private String pic11;
    private String pic12;


    //2017年7月20日
    private String storetime;
    private String printonalarm;
    private String timeright;
    private String machinedataremote;
    private String remainquestion;
    //2017年7月21日
    private String teamworker;


    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public String getOvertime() {
        return overtime;
    }

    public void setOvertime(String overtime) {
        this.overtime = overtime;
    }

    public String getWorktime() {
        return worktime;
    }

    public void setWorktime(String worktime) {
        this.worktime = worktime;
    }

//    public int getBoxone() {
//        return boxone;
//    }
//
//    public void setBoxone(int boxone) {
//        this.boxone = boxone;
//    }
//
//    public int getBoxtwo() {
//        return boxtwo;
//    }
//
//    public void setBoxtwo(int boxtwo) {
//        this.boxtwo = boxtwo;
//    }
//
//    public String getBoxthree() {
//        return boxthree;
//    }
//
//    public void setBoxthree(String boxthree) {
//        this.boxthree = boxthree;
//    }

    public int getHangstatus() {
        return hangstatus;
    }

    public void setHangstatus(int hangstatus) {
        this.hangstatus = hangstatus;
    }

    public List<BughandledetaillistBean> getBughandledetaillist() {
        return bughandledetaillist;
    }

    public void setBughandledetaillist(List<BughandledetaillistBean> bughandledetaillist) {
        this.bughandledetaillist = bughandledetaillist;
    }

    public int getCauseone() {
        return causeone;
    }

    public void setCauseone(int causeone) {
        this.causeone = causeone;
    }

    public int getCausetwo() {
        return causetwo;
    }

    public void setCausetwo(int causetwo) {
        this.causetwo = causetwo;
    }

    public int getCausethree() {
        return causethree;
    }

    public void setCausethree(int causethree) {
        this.causethree = causethree;
    }

    public int getCausefour() {
        return causefour;
    }

    public void setCausefour(int causefour) {
        this.causefour = causefour;
    }

    public String getReceiveworkeruid() {
        return receiveworkeruid;
    }

    public void setReceiveworkeruid(String receiveworkeruid) {
        this.receiveworkeruid = receiveworkeruid;
    }

    /**
     * 电视墙/操作台正面全貌 (第一张)
     */
    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    /**
     * 电视墙/操作台正面全貌 (第二张)
     */
    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    /**
     * 电视墙/操作台正面全貌 (第三张)
     */
    public String getPic3() {
        return pic3;
    }

    public void setPic3(String pic3) {
        this.pic3 = pic3;
    }

    /**
     * 电视墙/操作台背面全照(第一张)
     */
    public String getPic4() {
        return pic4;
    }

    public void setPic4(String pic4) {
        this.pic4 = pic4;
    }

    /**
     * 电视墙/操作台背面全照(第二张)
     */
    public String getPic5() {
        return pic5;
    }

    public void setPic5(String pic5) {
        this.pic5 = pic5;
    }

    /**
     * 电视墙/操作台背面全照(第三张)
     */
    public String getPic6() {
        return pic6;
    }

    public void setPic6(String pic6) {
        this.pic6 = pic6;
    }

    /**
     * 机柜正面/背面 （第一张）
     */
    public String getPic7() {
        return pic7;
    }

    public void setPic7(String pic7) {
        this.pic7 = pic7;
    }

    /**
     * 录像机天数
     */
    public String getStoretime() {
        return storetime;
    }

    public void setStoretime(String storetime) {
        this.storetime = storetime;
    }

    /**
     * 报警打印机
     */
    public String getPrintonalarm() {
        return printonalarm;
    }

    public void setPrintonalarm(String printonalarm) {
        this.printonalarm = printonalarm;
    }

    /**
     * 设备时间同步
     */
    public String getTimeright() {
        return timeright;
    }

    public void setTimeright(String timeright) {
        this.timeright = timeright;
    }

    /**
     * 各类设备报警远传
     */
    public String getMachinedataremote() {
        return machinedataremote;
    }

    public void setMachinedataremote(String machinedataremote) {
        this.machinedataremote = machinedataremote;
    }

    /**
     * 遗留问题
     */
    public String getRemainquestion() {
        return remainquestion;
    }

    public void setRemainquestion(String remainquestion) {
        this.remainquestion = remainquestion;
    }

    /**
     * 协作人员
     */
    public String getTeamworker() {
        return teamworker;
    }

    public void setTeamworker(String teamworker) {
        this.teamworker = teamworker;
    }

    /**
     * 机柜正面/背面 （第二张）
     */
    public String getPic8() {
        return pic8;
    }

    public void setPic8(String pic8) {
        this.pic8 = pic8;
    }

    /**
     * 机柜正面/背面 （第三张）
     */
    public String getPic9() {
        return pic9;
    }

    public void setPic9(String pic9) {
        this.pic9 = pic9;
    }

    public String getPic10() {
        return pic10;
    }

    public void setPic10(String pic10) {
        this.pic10 = pic10;
    }

    public String getPic11() {
        return pic11;
    }

    public void setPic11(String pic11) {
        this.pic11 = pic11;
    }

    public String getPic12() {
        return pic12;
    }

    public void setPic12(String pic12) {
        this.pic12 = pic12;
    }


    public static class BughandledetaillistBean implements Serializable {
        /**
         * title : 1
         * <p>
         * sque
         * instrument : 摄像头设备
         * modelnum : 海康威视x21
         * equipmentcode : GR111
         * equipmentposition : 在头顶
         * description : 坏了
         * checkprocess : 拍了两下
         * cause : 老化
         * handle : 换高压包
         * pic1 : wewff
         * pic2 : wewff
         * pic3 : wewff
         * pic4 : wewff
         * pic5 : wewff
         * pic6 : wewff
         * pic7 : wewff
         * pic8 : wewff
         * pic9 : wewff
         * bughandledetailparamlist : [{"name":"电压","value":"220v"},{"name":"温度","value":"50度"}]
         * bughandledetailmateriallist : [{"equipmenttype":"监视器","equipmentname":"模拟枪机","equipmentmodel":"OXO11","num":"2","memo":"质量一般就行了"},{"equipmenttype":"锡丝","equipmentname":"有铅","equipmentmodel":"leadxs","num":"1","memo":"用的不多"}]
         */

        private String title;
        private int sequence;
        private String instrument;
        private String modelnum;
        private String equipmentcode;
        private String equipmentposition;
        private String description;
        private String checkprocess;
        private String cause;
        private String handle;
        /**
         * 故障表象 （第一张）
         */
        private String pic1;
        /**
         * 故障表象 （第二张）
         */
        private String pic2;
        /**
         * 故障表象 （第三张）
         */
        private String pic3;
        /**
         * 工具及蓝布 （第一张）
         */
        private String pic4;
        /**
         * 工具及蓝布 （第二张）
         */
        private String pic5;
        /**
         * 工具及蓝布 （第三张）
         */
        private String pic6;
        /**
         * 故障点照片 （第一张）
         */
        private String pic7;
        /**
         * 故障点照片 （第二张）
         */
        private String pic8;
        /**
         * 故障点照片 （第三张）
         */
        private String pic9;
        //2017年7月21日
        /**
         * 处理后现场 （第一张）
         */
        private String pic10;
        /**
         * 处理后现场 （第二张）
         */
        private String pic11;
        /**
         * 处理后现场 （第三张）
         */
        private String pic12;
        /**
         * 设备回装 （第一张）
         */
        private String pic13;
        /**
         * 设备回装 （第二张）
         */
        private String pic14;
        /**
         * 设备回装 （第三张）
         */
        private String pic15;
        /**
         * 故障恢复后表象 （第一张）
         */
        private String pic16;
        /**
         * 故障恢复后表象 （第二张）
         */
        private String pic17;
        /**
         * 故障恢复后表象 （第三张）
         */
        private String pic18;

        //2017年7月20日
        /**
         * 维修结果
         */
        private String repairconclusion;
        /**
         * 一级业务（设备类别）
         */
        private String businessOne;

        /**
         * 二级业务类型
         */
        private String businessTwo;

        /**
         * 三级业务
         */
        private String businessThree;

        /**
         * 关联的系统故障处理uid（如果客户选择了故障描述参考）
         */
        private String deviceFailureUid;

        /**
         * 关联的系统设备库设备（如果客户设备库选择了系统设备）
         */
        private String deviceUid;

        /**
         * 客户设备库uid（如果报修时选择了客户设备库设备）
         */
        private String customerDeviceUid;

        /**
         * 客户设备库设备名称（如果报修时选择了客户设备库设备）
         */
        private String customerDeviceName;

        /**
         * 所属客户名称
         */
        private String customername;

        private List<BughandledetailparamlistBean> bughandledetailparamlist = new ArrayList<>();
        private List<BughandledetailmateriallistBean> bughandledetailmateriallist = new ArrayList<>();

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getInstrument() {
            return instrument;
        }

        public void setInstrument(String instrument) {
            this.instrument = instrument;
        }

        public String getModelnum() {
            return modelnum;
        }

        public void setModelnum(String modelnum) {
            this.modelnum = modelnum;
        }

        public String getEquipmentcode() {
            return equipmentcode;
        }

        public void setEquipmentcode(String equipmentcode) {
            this.equipmentcode = equipmentcode;
        }

        public String getEquipmentposition() {
            return equipmentposition;
        }

        public void setEquipmentposition(String equipmentposition) {
            this.equipmentposition = equipmentposition;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCheckprocess() {
            return checkprocess;
        }

        public void setCheckprocess(String checkprocess) {
            this.checkprocess = checkprocess;
        }

        public String getCause() {
            return cause;
        }

        public void setCause(String cause) {
            this.cause = cause;
        }

        public String getHandle() {
            return handle;
        }

        public void setHandle(String handle) {
            this.handle = handle;
        }


        public List<BughandledetailparamlistBean> getBughandledetailparamlist() {
            return bughandledetailparamlist;
        }

        public void setBughandledetailparamlist(List<BughandledetailparamlistBean> bughandledetailparamlist) {
            this.bughandledetailparamlist = bughandledetailparamlist;
        }

        public List<BughandledetailmateriallistBean> getBughandledetailmateriallist() {
            return bughandledetailmateriallist;
        }

        public void setBughandledetailmateriallist(List<BughandledetailmateriallistBean> bughandledetailmateriallist) {
            this.bughandledetailmateriallist = bughandledetailmateriallist;
        }

        public int getSequence() {
            return sequence;
        }

        public void setSequence(int sequence) {
            this.sequence = sequence;
        }

        /**
         * 故障表象 （第一张）
         */
        public String getPic1() {
            return pic1;
        }

        public void setPic1(String pic1) {
            this.pic1 = pic1;
        }

        /**
         * 故障表象 （第二张）
         */
        public String getPic2() {
            return pic2;
        }

        public void setPic2(String pic2) {
            this.pic2 = pic2;
        }

        /**
         * 故障表象 （第三张）
         */
        public String getPic3() {
            return pic3;
        }

        public void setPic3(String pic3) {
            this.pic3 = pic3;
        }

        /**
         * 工具及蓝布 （第一张）
         */
        public String getPic4() {
            return pic4;
        }

        public void setPic4(String pic4) {
            this.pic4 = pic4;
        }

        /**
         * 工具及蓝布 （第二张）
         */
        public String getPic5() {
            return pic5;
        }

        public void setPic5(String pic5) {
            this.pic5 = pic5;
        }

        /**
         * 工具及蓝布 （第三张）
         */
        public String getPic6() {
            return pic6;
        }

        public void setPic6(String pic6) {
            this.pic6 = pic6;
        }

        /**
         * 故障点照片 （第一张）
         */
        public String getPic7() {
            return pic7;
        }

        public void setPic7(String pic7) {
            this.pic7 = pic7;
        }

        /**
         * 故障点照片 （第二张）
         */
        public String getPic8() {
            return pic8;
        }

        public void setPic8(String pic8) {
            this.pic8 = pic8;
        }

        /**
         * 故障点照片 （第三张）
         */
        public String getPic9() {
            return pic9;
        }

        public void setPic9(String pic9) {
            this.pic9 = pic9;
        }

        /**
         * 处理后现场 （第一张）
         */
        public String getPic10() {
            return pic10;
        }

        public void setPic10(String pic10) {
            this.pic10 = pic10;
        }

        /**
         * 处理后现场 （第二张）
         */
        public String getPic11() {
            return pic11;
        }

        public void setPic11(String pic11) {
            this.pic11 = pic11;
        }

        /**
         * 处理后现场 （第三张）
         */
        public String getPic12() {
            return pic12;
        }

        public void setPic12(String pic12) {
            this.pic12 = pic12;
        }

        /**
         * 设备回装 （第一张）
         */
        public String getPic13() {
            return pic13;
        }

        public void setPic13(String pic13) {
            this.pic13 = pic13;
        }

        /**
         * 设备回装 （第二张）
         */
        public String getPic14() {
            return pic14;
        }

        public void setPic14(String pic14) {
            this.pic14 = pic14;
        }

        /**
         * 设备回装 （第三张）
         */
        public String getPic15() {
            return pic15;
        }

        public void setPic15(String pic15) {
            this.pic15 = pic15;
        }

        /**
         * 故障恢复后表象 （第一张）
         */
        public String getPic16() {
            return pic16;
        }

        public void setPic16(String pic16) {
            this.pic16 = pic16;
        }

        /**
         * 故障恢复后表象 （第二张）
         */
        public String getPic17() {
            return pic17;
        }

        public void setPic17(String pic17) {
            this.pic17 = pic17;
        }

        /**
         * 故障恢复后表象 （第三张）
         */
        public String getPic18() {
            return pic18;
        }

        public void setPic18(String pic18) {
            this.pic18 = pic18;
        }

        /**
         * 维修结果
         */
        public String getRepairconclusion() {
            return repairconclusion;
        }

        public void setRepairconclusion(String repairconclusion) {
            this.repairconclusion = repairconclusion;
        }

        /**
         * 客户姓名（客户端报修时填写的用户名称）
         */
        public String getCustomername() {
            return customername;
        }

        public void setCustomername(String customername) {
            this.customername = customername;
        }

        public String getBusinessOne() {
            return businessOne;
        }

        public void setBusinessOne(String businessOne) {
            this.businessOne = businessOne;
        }

        public String getBusinessTwo() {
            return businessTwo;
        }

        public void setBusinessTwo(String businessTwo) {
            this.businessTwo = businessTwo;
        }

        public String getBusinessThree() {
            return businessThree;
        }

        public void setBusinessThree(String businessThree) {
            this.businessThree = businessThree;
        }

        /**
         * 关联的系统故障处理uid（如果客户选择了故障描述参考）
         */
        public String getDeviceFailureUid() {
            return deviceFailureUid;
        }

        public void setDeviceFailureUid(String deviceFailureUid) {
            this.deviceFailureUid = deviceFailureUid;
        }

        /**
         * 关联的系统设备库设备（如果客户设备库选择了系统设备）
         */
        public String getDeviceUid() {
            return deviceUid;
        }

        public void setDeviceUid(String deviceUid) {
            this.deviceUid = deviceUid;
        }

        /**
         * 客户设备库uid（如果报修时选择了客户设备库设备）
         */
        public String getCustomerDeviceUid() {
            return customerDeviceUid;
        }

        public void setCustomerDeviceUid(String customerDeviceUid) {
            this.customerDeviceUid = customerDeviceUid;
        }

        /**
         * 客户设备库设备名称（如果报修时选择了客户设备库设备）
         */
        public String getCustomerDeviceName() {
            return customerDeviceName;
        }

        public void setCustomerDeviceName(String customerDeviceName) {
            this.customerDeviceName = customerDeviceName;
        }

        public static class BughandledetailparamlistBean implements Serializable {
            /**
             * name : 电压
             * value : 220v
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class BughandledetailmateriallistBean implements Serializable {
            /**
             * equipmenttype : 监视器
             * equipmentname : 模拟枪机
             * equipmentmodel : OXO11
             * num : 2
             * memo : 质量一般就行了
             */

            private String equipmenttype;
            private String equipmentname;
            private String equipmentmodel;
            private String num;
            private String memo;

            public String getEquipmenttype() {
                return equipmenttype;
            }

            public void setEquipmenttype(String equipmenttype) {
                this.equipmenttype = equipmenttype;
            }

            public String getEquipmentname() {
                return equipmentname;
            }

            public void setEquipmentname(String equipmentname) {
                this.equipmentname = equipmentname;
            }

            public String getEquipmentmodel() {
                return equipmentmodel;
            }

            public void setEquipmentmodel(String equipmentmodel) {
                this.equipmentmodel = equipmentmodel;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getMemo() {
                return memo;
            }

            public void setMemo(String memo) {
                this.memo = memo;
            }
        }
    }

    public void copyFromWorkspaceDetailBean(WorkspaceDetailBean bean) {
        /**
         * title : 1
         * instrument : 摄像头设备
         * modelnum : 海康威视x21
         * equipmentcode : GR111
         * equipmentposition : 在头顶
         * description : 坏了
         * checkprocess : 拍了两下
         * cause : 老化
         * handle : 换高压包
         * pic1 : wewff
         * pic2 : wewff
         * pic3 : wewff
         * pic4 : wewff
         * pic5 : wewff
         * pic6 : wewff
         * pic7 : wewff
         * pic8 : wewff
         * pic9 : wewff
         * bughandledetailparamlist : [{"name":"电压","value":"220v"},{"name":"温度","value":"50度"}]
         * bughandledetailmateriallist : [{"equipmenttype":"监视器","equipmentname":"模拟枪机","equipmentmodel":"OXO11","num":"2","memo":"质量一般就行了"},{"equipmenttype":"锡丝","equipmentname":"有铅","equipmentmodel":"leadxs","num":"1","memo":"用的不多"}]
         */
//        {
//            "bugdesc":"故障现象", "bugfour":"96d4bf43390646a2abf5743811785152", "bugfourname":
//            "4级故障例1", "bugone":"C", "bugonename":"电视监控", "bugpic1":
//            "http://eanfangx.oss-cn-beijing.aliyuncs.com/0b2db5291521473f904cd756e271a4f6.png", "bugpic2":
//            "http://eanfangx.oss-cn-beijing.aliyuncs.com/23852c10da8e43f4b285c79f3c77c5a3.png", "bugpic3":
//            "http://eanfangx.oss-cn-beijing.aliyuncs.com/8e7c0460d79c4b8cbe3087263956a62f.png", "bugposition":
//            "我以前也是", "bugthree":"b178c0b5171d4601aa6a8a110850be75", "bugthreename":
//            "模拟枪型摄像机", "bugtwo":"c1151267364d44fbaeade6e411db4428", "bugtwoname":"摄像采集", "equipnum":
//            "78454549", "id":9, "ordernum":"MO32020170423230854642738", "uid":
//            "80f0c237f3744991aebed875239aef7a"
//        }]
        List<BughandledetaillistBean> list = new ArrayList<>();
        List<WorkspaceDetailBean.BugsBean> bugList = bean.getBugs();
        if (bugList != null) {

            for (int i = 0; i < bugList.size(); i++) {

                WorkspaceDetailBean.BugsBean bugsBean = bugList.get(i);
                BughandledetaillistBean bughandledetaillistBean = new BughandledetaillistBean();
//                  *instrument:
//                摄像头设备
//                        * modelnum :海康威视x21
//                        * equipmentcode :GR111
//                        * equipmentposition :在头顶
                bughandledetaillistBean.setSequence(i);
                bughandledetaillistBean.setTitle(i + 1 + "." + bugsBean.getBugonename() + " - " + bugsBean.getBugtwoname() + " - " + bugsBean.getBugthreename());
                bughandledetaillistBean.setInstrument(bugsBean.getBugthreename());
                bughandledetaillistBean.setModelnum(bugsBean.getBugfourname());
                bughandledetaillistBean.setEquipmentcode(bugsBean.getEquipnum());
                bughandledetaillistBean.setEquipmentposition(bugsBean.getBugposition());
                bughandledetaillistBean.setPic1(bugsBean.getBugpic1());
                bughandledetaillistBean.setPic2(bugsBean.getBugpic2());
                bughandledetaillistBean.setPic3(bugsBean.getBugpic3());


                //2017年9月29日
                bughandledetaillistBean.setDeviceUid(bugsBean.getDeviceUid());
                bughandledetaillistBean.setDeviceFailureUid(bugsBean.getDeviceFailureUid());
                bughandledetaillistBean.setCustomerDeviceUid(bugsBean.getCustomerDeviceUid());
                bughandledetaillistBean.setCustomerDeviceName(bugsBean.getCustomerDeviceName());
                bughandledetaillistBean.setBusinessOne(bugsBean.getBugonename());
                bughandledetaillistBean.setBusinessTwo(bugsBean.getBugtwoname());
                bughandledetaillistBean.setBusinessThree(bugsBean.getBugthreename());
                bughandledetaillistBean.setDescription(bugsBean.getBugdesc());

                list.add(bughandledetaillistBean);
            }
        }
        this.setBughandledetaillist(list);
        this.ordernum = bean.getOrder().getOrdernum();
    }


}
