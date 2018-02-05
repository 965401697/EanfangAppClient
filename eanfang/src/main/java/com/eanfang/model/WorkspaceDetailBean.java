package com.eanfang.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yaosheng on 2017/5/13.
 */

public class WorkspaceDetailBean implements Serializable {


    /**
     * 订单信息
     */
    private OrderBean order;
    /**
     * 客户报修的故障
     */
    private List<BugsBean> bugs;
    /**
     * 故障处理信息
     */
    private BughandleconfirmBean bughandleconfirm;
    /**
     * 故障处理明细
     */
    private List<BughandledetaillistBean> bughandledetaillist;

    /**
     * 技师的头像
     */
    private String workerHeadPic;
    /**
     * 客户的头像
     */
    private String clientHeadPic;
    /**
     * 技师公司名称
     */
    private String workerCompanyName;
    /**
     * 客户公司名称
     */
    private String clientCompanyName;
    /**
     * 技师真实姓名
     */
    private String workerRealName;
    /**
     * 客户真实姓名
     */
    private String clientRealName;
    /**
     * 技师电话
     */
    private String workerPhone;
    /**
     * 客户电话
     */
    private String clientPhone;


    public BughandleconfirmBean getBughandleconfirm() {
        return bughandleconfirm;
    }

    public void setBughandleconfirm(BughandleconfirmBean bughandleconfirm) {
        this.bughandleconfirm = bughandleconfirm;
    }

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public List<BughandledetaillistBean> getBughandledetaillist() {
        return bughandledetaillist;
    }

    public void setBughandledetaillist(List<BughandledetaillistBean> bughandledetaillist) {
        this.bughandledetaillist = bughandledetaillist;
    }

    public List<BugsBean> getBugs() {
        return bugs;
    }

    public void setBugs(List<BugsBean> bugs) {
        this.bugs = bugs;
    }

    /**
     * 技师的头像
     */
    public String getWorkerHeadPic() {
        return workerHeadPic;
    }

    public void setWorkerHeadPic(String workerHeadPic) {
        this.workerHeadPic = workerHeadPic;
    }

    /**
     * 客户的头像
     */
    public String getClientHeadPic() {
        return clientHeadPic;
    }

    public void setClientHeadPic(String clientHeadPic) {
        this.clientHeadPic = clientHeadPic;
    }

    /**
     * 技师公司名称
     */
    public String getWorkerCompanyName() {
        return workerCompanyName;
    }

    public void setWorkerCompanyName(String workerCompanyName) {
        this.workerCompanyName = workerCompanyName;
    }

    /**
     * 客户公司名称
     */
    public String getClientCompanyName() {
        return clientCompanyName;
    }

    public void setClientCompanyName(String clientCompanyName) {
        this.clientCompanyName = clientCompanyName;
    }

    /**
     * 技师真实姓名
     */
    public String getWorkerRealName() {
        return workerRealName;
    }

    public void setWorkerRealName(String workerRealName) {
        this.workerRealName = workerRealName;
    }

    /**
     * 客户真实姓名
     */
    public String getClientRealName() {
        return clientRealName;
    }

    public void setClientRealName(String clientRealName) {
        this.clientRealName = clientRealName;
    }

    /**
     * 技师电话
     */
    public String getWorkerPhone() {
        return workerPhone;
    }

    public void setWorkerPhone(String workerPhone) {
        this.workerPhone = workerPhone;
    }

    /**
     * 客户电话
     */
    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public static class BughandleconfirmBean implements Serializable {
        /**
         * boxone : 1
         * boxthree : 1
         * boxtwo : 0
         * id : 18
         * ordernum : MO32020170506002914064491
         * overtime : 2017-05-25 23:35:56
         * uid : 30a6588561b744019863ba258024f739
         * worktime : 0小时0分
         * pic1 : sdfsdfsdfsdfsdf
         * pic2 : sdfsdfsdf
         * pic3 : sdfsdfsdfsd
         * pic4 : sdfsdfsdfsdfsdf
         * pic5 : sdfsdfsdf
         * pic6 : sdfsdfsdfsd
         * pic7 : sdfsdfsdfsd
         */

//        private int boxone;
//        private String boxthree;
//        private int boxtwo;
        private int id;
        private String ordernum;
        private String overtime;
        private String uid;
        private String worktime;
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
        //录像机天数
        private String storetime;
        // 报警打印机
        private String printonalarm;
        //设备时间同步
        private String timeright;
        //各类设备报警远传
        private String machinedataremote;
        //遗留问题
        private String remainquestion;
        //协作人员
        private String teamworker;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

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

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getWorktime() {
            return worktime;
        }

        public void setWorktime(String worktime) {
            this.worktime = worktime;
        }

        public String getPic1() {
            return pic1;
        }

        public void setPic1(String pic1) {
            this.pic1 = pic1;
        }

        public String getPic2() {
            return pic2;
        }

        public void setPic2(String pic2) {
            this.pic2 = pic2;
        }

        public String getPic3() {
            return pic3;
        }

        public void setPic3(String pic3) {
            this.pic3 = pic3;
        }

        public String getPic4() {
            return pic4;
        }

        public void setPic4(String pic4) {
            this.pic4 = pic4;
        }

        public String getPic5() {
            return pic5;
        }

        public void setPic5(String pic5) {
            this.pic5 = pic5;
        }

        public String getPic6() {
            return pic6;
        }

        public void setPic6(String pic6) {
            this.pic6 = pic6;
        }

        public String getPic7() {
            return pic7;
        }

        public void setPic7(String pic7) {
            this.pic7 = pic7;
        }

        public String getStoretime() {
            return storetime;
        }

        public void setStoretime(String storetime) {
            this.storetime = storetime;
        }

        public String getPrintonalarm() {
            return printonalarm;
        }

        public void setPrintonalarm(String printonalarm) {
            this.printonalarm = printonalarm;
        }

        public String getTimeright() {
            return timeright;
        }

        public void setTimeright(String timeright) {
            this.timeright = timeright;
        }

        public String getMachinedataremote() {
            return machinedataremote;
        }

        public void setMachinedataremote(String machinedataremote) {
            this.machinedataremote = machinedataremote;
        }

        public String getRemainquestion() {
            return remainquestion;
        }

        public void setRemainquestion(String remainquestion) {
            this.remainquestion = remainquestion;
        }

        public String getTeamworker() {
            return teamworker;
        }

        public void setTeamworker(String teamworker) {
            this.teamworker = teamworker;
        }

        public String getPic8() {
            return pic8;
        }

        public void setPic8(String pic8) {
            this.pic8 = pic8;
        }

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
    }

    public static class OrderBean implements Serializable {
        /**
         * account : 18612130370
         * arrivetime : 两小时内
         * bugone : C
         * bugonename : 电视监控
         * city : 苏州
         * clientcompanyuid : 6fc797c9f2b04b22bab5b163dbfa3d80
         * clientconnector : 五十
         * clientphone : 1587856
         * clientuid : de1d14c618934394a6cf680ae84ab616
         * companyname : 五十
         * confirmtime : 2017-05-26 00:07:07
         * createtime : 2017-05-06 00:29:14
         * detailplace : 星州街与高和路交叉口北150米
         * doorfee : 50
         * endtime : 2017-05-27 20:24:08
         * originordernum : MO320201734344064491
         * audit : 1
         * pass : 2017-05-25 23:24:08
         * jtocreviewstatus : 1
         * pretime : 2017-05-26 00:07:07
         * markdown : 1
         * markdowntime : 2017-05-26 00:07:07
         * applyman : 章姬
         * id : 13
         * invoicefee : 20
         * latitude : 31.293773
         * longitude : 120.681504
         * ordernum : MO32020170506002914064491
         * paytype : 支付宝
         * replytime : 2017-05-25 23:24:08
         * status : 5
         * totalfee : 70
         * workeruid : b556ee600d47491aa49174fc1b323b76
         * zone : 吴中
         */

        private String account;
        private String arrivetime;
        private String bugone;
        private String bugonename;
        private String city;
        private String clientcompanyuid;
        private String clientconnector;
        private String clientphone;
        private String clientuid;
        private String companyname;
        private String confirmtime;
        private String createtime;
        private String detailplace;
        private double doorfee;
        private String endtime;
        private String originordernum;
        private String audit;
        private String pass;
        private String jtocreviewstatus;
        private String pretime;
        private String markdown;
        private String markdowntime;
        private String applyman;
        private int id;
        private double invoicefee;
        private String latitude;
        private String longitude;
        private String ordernum;
        private String paytype;
        private String replytime;
        private String status;
        private double totalfee;
        private String workeruid;
        private String zone;

        /**
         * 是否电话解决
         */
        private String phonesolve;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getArrivetime() {
            return arrivetime;
        }

        public void setArrivetime(String arrivetime) {
            this.arrivetime = arrivetime;
        }

        public String getBugone() {
            return bugone;
        }

        public void setBugone(String bugone) {
            this.bugone = bugone;
        }

        public String getBugonename() {
            return bugonename;
        }

        public void setBugonename(String bugonename) {
            this.bugonename = bugonename;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getClientcompanyuid() {
            return clientcompanyuid;
        }

        public void setClientcompanyuid(String clientcompanyuid) {
            this.clientcompanyuid = clientcompanyuid;
        }

        public String getClientconnector() {
            return clientconnector;
        }

        public void setClientconnector(String clientconnector) {
            this.clientconnector = clientconnector;
        }

        public String getClientphone() {
            return clientphone;
        }

        public void setClientphone(String clientphone) {
            this.clientphone = clientphone;
        }

        public String getClientuid() {
            return clientuid;
        }

        public void setClientuid(String clientuid) {
            this.clientuid = clientuid;
        }

        public String getCompanyname() {
            return companyname;
        }

        public void setCompanyname(String companyname) {
            this.companyname = companyname;
        }

        public String getConfirmtime() {
            return confirmtime;
        }

        public void setConfirmtime(String confirmtime) {
            this.confirmtime = confirmtime;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getDetailplace() {
            return detailplace;
        }

        public void setDetailplace(String detailplace) {
            this.detailplace = detailplace;
        }

        public double getDoorfee() {
            return doorfee;
        }

        public void setDoorfee(double doorfee) {
            this.doorfee = doorfee;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getOriginordernum() {
            return originordernum;
        }

        public void setOriginordernum(String originordernum) {
            this.originordernum = originordernum;
        }

        public String getAudit() {
            return audit;
        }

        public void setAudit(String audit) {
            this.audit = audit;
        }

        public String getPass() {
            return pass;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

        public String getJtocreviewstatus() {
            return jtocreviewstatus;
        }

        public void setJtocreviewstatus(String jtocreviewstatus) {
            this.jtocreviewstatus = jtocreviewstatus;
        }

        public String getPretime() {
            return pretime;
        }

        public void setPretime(String pretime) {
            this.pretime = pretime;
        }

        public String getMarkdown() {
            return markdown;
        }

        public void setMarkdown(String markdown) {
            this.markdown = markdown;
        }

        public String getMarkdowntime() {
            return markdowntime;
        }

        public void setMarkdowntime(String markdowntime) {
            this.markdowntime = markdowntime;
        }

        public String getApplyman() {
            return applyman;
        }

        public void setApplyman(String applyman) {
            this.applyman = applyman;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getInvoicefee() {
            return invoicefee;
        }

        public void setInvoicefee(double invoicefee) {
            this.invoicefee = invoicefee;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getOrdernum() {
            return ordernum;
        }

        public void setOrdernum(String ordernum) {
            this.ordernum = ordernum;
        }

        public String getPaytype() {
            return paytype;
        }

        public void setPaytype(String paytype) {
            this.paytype = paytype;
        }

        public String getReplytime() {
            return replytime;
        }

        public void setReplytime(String replytime) {
            this.replytime = replytime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public double getTotalfee() {
            return totalfee;
        }

        public void setTotalfee(double totalfee) {
            this.totalfee = totalfee;
        }

        public String getWorkeruid() {
            return workeruid;
        }

        public void setWorkeruid(String workeruid) {
            this.workeruid = workeruid;
        }

        public String getZone() {
            return zone;
        }

        public void setZone(String zone) {
            this.zone = zone;
        }

        /**
         * 是否电话解决
         */
        public String getPhonesolve() {
            return phonesolve;
        }

        public void setPhonesolve(String phonesolve) {
            this.phonesolve = phonesolve;
        }
    }

    public static class BughandledetaillistBean implements Serializable {
        /**
         * bughandledetailmaterial : [{"bughandlerdetailuid":"dfa808e3875740bc9a569bf0bfeeac5a","equipmentmodel":"4级故障例1","equipmentname":"模拟枪型摄像机","equipmenttype":"摄像采集","id":7,"memo":"2","num":"2","uid":"a311326d1eb84d4d8fb81b7d66300f98"}]
         * bughandledetailparam : [{"bughandlerdetailuid":"dfa808e3875740bc9a569bf0bfeeac5a","id":3,"name":"是","uid":"6dba05ca7ef543238a762eceae8f7343","value":"无"},{"bughandlerdetailuid":"dfa808e3875740bc9a569bf0bfeeac5a","id":11,"name":"是","uid":"3812568ea3fc4ca497c335fa94955cd9","value":"无"}]
         * detail : {"cause":"楼梯","checkprocess":"体育","description":"某日","equipmentcode":"中午","equipmentposition":"五十","handle":"脱离","id":25,"instrument":"摄像采集-模拟枪型摄像机","modelnum":"4级故障例1","ordernum":"MO32020170506002914064491","sequence":"0","title":"1.电视监控-摄像采集-模拟枪型摄像机","uid":"dfa808e3875740bc9a569bf0bfeeac5a","pic1":"sdfsdfsdfsdfsdf","pic2":"sdfsdfsdf","pic3":"sdfsdfsdfsd","pic4":"sdfsdfsdfsdfsdf","pic5":"sdfsdfsdf","pic6":"sdfsdfsdfsd","pic7":"sdfsdfsdfsd","pic8":"sdfsdfsdfsd","pic9":"sdfsdfsdfsd"}
         */

        private DetailBean detail;
        private List<BughandledetailmaterialBean> bughandledetailmaterial;
        private List<BughandledetailparamBean> bughandledetailparam;

        public DetailBean getDetail() {
            return detail;
        }

        public void setDetail(DetailBean detail) {
            this.detail = detail;
        }

        public List<BughandledetailmaterialBean> getBughandledetailmaterial() {
            return bughandledetailmaterial;
        }

        public void setBughandledetailmaterial(List<BughandledetailmaterialBean> bughandledetailmaterial) {
            this.bughandledetailmaterial = bughandledetailmaterial;
        }

        public List<BughandledetailparamBean> getBughandledetailparam() {
            return bughandledetailparam;
        }

        public void setBughandledetailparam(List<BughandledetailparamBean> bughandledetailparam) {
            this.bughandledetailparam = bughandledetailparam;
        }

        public static class DetailBean implements Serializable {
            /**
             * cause : 楼梯
             * checkprocess : 体育
             * description : 某日
             * equipmentcode : 中午
             * equipmentposition : 五十
             * handle : 脱离
             * id : 25
             * instrument : 摄像采集-模拟枪型摄像机
             * modelnum : 4级故障例1
             * ordernum : MO32020170506002914064491
             * sequence : 0
             * title : 1.电视监控-摄像采集-模拟枪型摄像机
             * uid : dfa808e3875740bc9a569bf0bfeeac5a
             * pic1 : sdfsdfsdfsdfsdf
             * pic2 : sdfsdfsdf
             * pic3 : sdfsdfsdfsd
             * pic4 : sdfsdfsdfsdfsdf
             * pic5 : sdfsdfsdf
             * pic6 : sdfsdfsdfsd
             * pic7 : sdfsdfsdfsd
             * pic8 : sdfsdfsdfsd
             * pic9 : sdfsdfsdfsd
             */

            private String cause;
            private String checkprocess;
            private String description;
            private String equipmentcode;
            private String equipmentposition;
            private String handle;
            private int id;
            private String instrument;
            private String modelnum;
            private String ordernum;
            private String sequence;
            private String title;
            private String uid;
            private String pic1;
            private String pic2;
            private String pic3;
            private String pic4;
            private String pic5;
            private String pic6;
            private String pic7;
            private String pic8;
            private String pic9;
            //2017年7月21日
            private String pic10;
            private String pic11;
            private String pic12;
            private String pic13;
            private String pic14;
            private String pic15;
            private String pic16;
            private String pic17;
            private String pic18;

            //2017年7月20日
            private String repairconclusion;

            public String getCause() {
                return cause;
            }

            public void setCause(String cause) {
                this.cause = cause;
            }

            public String getCheckprocess() {
                return checkprocess;
            }

            public void setCheckprocess(String checkprocess) {
                this.checkprocess = checkprocess;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
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

            public String getHandle() {
                return handle;
            }

            public void setHandle(String handle) {
                this.handle = handle;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public String getOrdernum() {
                return ordernum;
            }

            public void setOrdernum(String ordernum) {
                this.ordernum = ordernum;
            }

            public String getSequence() {
                return sequence;
            }

            public void setSequence(String sequence) {
                this.sequence = sequence;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
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
        }

        public static class BughandledetailmaterialBean implements Serializable {
            /**
             * bughandlerdetailuid : dfa808e3875740bc9a569bf0bfeeac5a
             * equipmentmodel : 4级故障例1
             * equipmentname : 模拟枪型摄像机
             * equipmenttype : 摄像采集
             * id : 7
             * memo : 2
             * num : 2
             * uid : a311326d1eb84d4d8fb81b7d66300f98
             */

            private String bughandlerdetailuid;
            private String equipmentmodel;
            private String equipmentname;
            private String equipmenttype;
            private int id;
            private String memo;
            private String num;
            private String uid;

            public String getBughandlerdetailuid() {
                return bughandlerdetailuid;
            }

            public void setBughandlerdetailuid(String bughandlerdetailuid) {
                this.bughandlerdetailuid = bughandlerdetailuid;
            }

            public String getEquipmentmodel() {
                return equipmentmodel;
            }

            public void setEquipmentmodel(String equipmentmodel) {
                this.equipmentmodel = equipmentmodel;
            }

            public String getEquipmentname() {
                return equipmentname;
            }

            public void setEquipmentname(String equipmentname) {
                this.equipmentname = equipmentname;
            }

            public String getEquipmenttype() {
                return equipmenttype;
            }

            public void setEquipmenttype(String equipmenttype) {
                this.equipmenttype = equipmenttype;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMemo() {
                return memo;
            }

            public void setMemo(String memo) {
                this.memo = memo;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }
        }

        public static class BughandledetailparamBean implements Serializable {
            /**
             * bughandlerdetailuid : dfa808e3875740bc9a569bf0bfeeac5a
             * id : 3
             * name : 是
             * uid : 6dba05ca7ef543238a762eceae8f7343
             * value : 无
             */

            private String bughandlerdetailuid;
            private int id;
            private String name;
            private String uid;
            private String value;

            public String getBughandlerdetailuid() {
                return bughandlerdetailuid;
            }

            public void setBughandlerdetailuid(String bughandlerdetailuid) {
                this.bughandlerdetailuid = bughandlerdetailuid;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }

    public static class BugsBean implements Serializable {
        /**
         * bugdesc : 现象
         * bugfour : 96d4bf43390646a2abf5743811785152
         * bugfourname : 4级故障例1
         * bugone : C
         * bugonename : 电视监控
         * bugposition : 五十
         * bugthree : b178c0b5171d4601aa6a8a110850be75
         * bugthreename : 模拟枪型摄像机
         * bugtwo : c1151267364d44fbaeade6e411db4428
         * bugtwoname : 摄像采集
         * bugpic1 : http://eanfangx.oss-cn-beijing.aliyuncs.com/0b2db5291521473f904cd756e271a4f6.png
         * bugpic2 : http://eanfangx.oss-cn-beijing.aliyuncs.com/23852c10da8e43f4b285c79f3c77c5a3.png
         * bugpic3 : http://eanfangx.oss-cn-beijing.aliyuncs.com/8e7c0460d79c4b8cbe3087263956a62f.png
         * equipnum : 中午
         * id : 15
         * ordernum : MO32020170506002914064491
         * uid : 7f387ecc5dcb49efb8ddafbf76b12c07
         */

        private String bugdesc;
        private String bugfour;
        private String bugfourname;
        private String bugone;
        private String bugonename;
        private String bugposition;
        private String bugthree;
        private String bugthreename;
        private String bugtwo;
        private String bugtwoname;
        private String bugpic1;
        private String bugpic2;
        private String bugpic3;
        private String equipnum;
        private int id;
        private String ordernum;
        private String uid;

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


        public String getBugdesc() {
            return bugdesc;
        }

        public void setBugdesc(String bugdesc) {
            this.bugdesc = bugdesc;
        }

        public String getBugfour() {
            return bugfour;
        }

        public void setBugfour(String bugfour) {
            this.bugfour = bugfour;
        }

        public String getBugfourname() {
            return bugfourname;
        }

        public void setBugfourname(String bugfourname) {
            this.bugfourname = bugfourname;
        }

        public String getBugone() {
            return bugone;
        }

        public void setBugone(String bugone) {
            this.bugone = bugone;
        }

        public String getBugonename() {
            return bugonename;
        }

        public void setBugonename(String bugonename) {
            this.bugonename = bugonename;
        }

        public String getBugposition() {
            return bugposition;
        }

        public void setBugposition(String bugposition) {
            this.bugposition = bugposition;
        }

        public String getBugthree() {
            return bugthree;
        }

        public void setBugthree(String bugthree) {
            this.bugthree = bugthree;
        }

        public String getBugthreename() {
            return bugthreename;
        }

        public void setBugthreename(String bugthreename) {
            this.bugthreename = bugthreename;
        }

        public String getBugtwo() {
            return bugtwo;
        }

        public void setBugtwo(String bugtwo) {
            this.bugtwo = bugtwo;
        }

        public String getBugtwoname() {
            return bugtwoname;
        }

        public void setBugtwoname(String bugtwoname) {
            this.bugtwoname = bugtwoname;
        }

        public String getBugpic1() {
            return bugpic1;
        }

        public void setBugpic1(String bugpic1) {
            this.bugpic1 = bugpic1;
        }

        public String getBugpic2() {
            return bugpic2;
        }

        public void setBugpic2(String bugpic2) {
            this.bugpic2 = bugpic2;
        }

        public String getBugpic3() {
            return bugpic3;
        }

        public void setBugpic3(String bugpic3) {
            this.bugpic3 = bugpic3;
        }

        public String getEquipnum() {
            return equipnum;
        }

        public void setEquipnum(String equipnum) {
            this.equipnum = equipnum;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrdernum() {
            return ordernum;
        }

        public void setOrdernum(String ordernum) {
            this.ordernum = ordernum;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
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
    }
}
