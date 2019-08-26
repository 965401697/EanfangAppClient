package com.eanfang.biz.model.bean;

import com.eanfang.biz.model.entity.AptitudeCertificateEntity;
import com.eanfang.biz.model.entity.GloryCertificateEntity;
import com.eanfang.biz.model.entity.OrgUnitEntity;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/8/23
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SecurityCompanyDetailBean implements Serializable {

    /**
     * cases : {"currPage":1,"list":[{"assigneeCompanyId":"979995434422681602","assigneeOrgCode":"c","assigneeTopCompanyId":"979995434422681602","assigneeUserId":"980024975105769473","budget":0,"budgetCost":"1千以内","businessOneCode":"1.1","businessOneId":10,"businessOneName":"电视监控","clientCompanyName":"个人","connector":"杨测","connectorPhone":"18600000015","createTime":"2019-08-21 15:08:49","createUserId":"1064807882139119617","description":"同","detailPlace":"太古产业孵化器(经济技术开发区洋河道10-11号标准厂房)","editTime":"2019-08-21 15:10:11","editUserId":"1064807882139119617","endTime":"2019-08-21 15:10:12","id":"1164071839479181314","latitude":"39.927629","longitude":"119.473138","newOrder":0,"orderNo":"EO1908211508215","orderTag":0,"ownerCompanyId":0,"ownerOrgCode":"c","ownerTopCompanyId":0,"ownerUserId":"1064807882139119617","predictTime":0,"revertTimeLimit":0,"status":3,"zone":"3.13.3.2"},{"assigneeCompanyId":"979995434422681602","assigneeOrgCode":"c","assigneeTopCompanyId":"979995434422681602","assigneeUserId":"1049116551874121729","budget":0,"budgetCost":"1千以内","businessOneCode":"1.1","businessOneId":10,"businessOneName":"电视监控","clientCompanyName":"测试团队","connector":"杨测","connectorPhone":"18600000015","createTime":"2019-08-21 15:05:33","createUserId":"1112599639845027842","description":"来来来","detailPlace":"秦皇岛经济技术开发区建设工程质量检测中心(永定河道2-10号)","editTime":"2019-08-21 15:07:58","editUserId":"1112599639845027842","endTime":"2019-08-21 15:07:58","id":"1164071020830732289","latitude":"39.927404","longitude":"119.472499","newOrder":0,"orderNo":"EO1908211505295","orderTag":0,"ownerCompanyId":"1112599639845027841","ownerOrgCode":"c","ownerTopCompanyId":"1112599639845027841","ownerUserId":"1112599639845027842","predictTime":4,"revertTimeLimit":0,"status":3,"zone":"3.13.3.2"},{"assigneeCompanyId":"979995434422681602","assigneeOrgCode":"c","assigneeTopCompanyId":"979995434422681602","assigneeUserId":"1128124319783751682","budget":0,"budgetCost":"1千以内","businessOneCode":"1.6","businessOneName":"停车场","clientCompanyName":"测试小丫丫","connector":"杨宇宇","connectorPhone":"15699909990","createTime":"2019-08-16 09:37:37","createUserId":"1156771814822715394","description":"年轻嘛","detailPlace":"茗茶岸坊(洋河道北100米)","editTime":"2019-08-16 09:40:25","editUserId":"1156771814822715394","endTime":"2019-08-16 09:40:25","id":"1162176554641498114","latitude":"39.927612","longitude":"119.473333","newOrder":0,"orderNo":"EO1908160937220","orderTag":0,"ownerCompanyId":"1156771814822715393","ownerOrgCode":"c","ownerTopCompanyId":"1156771814822715393","ownerUserId":"1156771814822715394","predictTime":0,"revertTimeLimit":0,"status":3,"zone":"3.13.3.2"}],"pageSize":3,"totalCount":92,"totalPage":31}
     * verifyStatus : 2
     * sysList : ["电视监控","防盗报警","门禁一卡通","可视对讲","公共广播","停车场","EAS","其他"]
     * aptitudeList : [{"awardOrg":"颁发机构","beginDate":"2019-04-27","beginTime":"2019-04-27 00:00:00","certificateName":"证书名称","certificatePics":"default/default_avatar.png,default/default_avatar.png,default/default_avatar.png","endDate":"2019-04-27","endTime":"2019-04-27 00:00:00"},{"awardOrg":"今后","beginDate":"2019-07-13","beginTime":"2019-07-13 00:00:00","certificateName":"嘻嘻嘻","certificatePics":"a5f311d1cbf9471e90a7be7b248883b2.png","endDate":"2019-07-26","endTime":"2019-07-26 00:00:00"},{"awardOrg":"行吗","beginDate":"2019-07-25","beginTime":"2019-07-25 00:00:00","certificateName":"明年","certificatePics":"480b30a5-9814-4ef0-9d83-1877f538ecfe.png","endDate":"2019-07-23","endTime":"2019-07-23 00:00:00"}]
     * orgUnit : {"intro":"北京法安视科技有限公司","logoPic":"62fb9a88b0bb4e2a9b131e1170be7f5c.png","name":"北京法安视科技有限公司"}
     * areaList : ["北京","北京市","东城区","西城区","朝阳区","丰台区","石景山区","海淀区","门头沟区","房山区","通州区","顺义区","昌平区","大兴区","怀柔区","平谷区","密云区","延庆区","天津","天津市","和平区","河东区","河西区","南开区","河北区","红桥区","东丽区","西青区","津南区","北辰区","武清区","宝坻区","滨海新区","宁河区","静海区","蓟州区","河北省","石家庄市","石家庄市","长安区","桥西区","新华区","井陉矿区","裕华区","藁城区","鹿泉区","栾城区","井陉县","正定县","行唐县","灵寿县","高邑县","深泽县","赞皇县","无极县","平山县","元氏县","赵县","辛集市","晋州市","新乐市","唐山市","唐山市","路南区","路北区","古冶区","开平区","丰南区","丰润区","曹妃甸区","滦县","滦南县","乐亭县","迁西县","玉田县","遵化市","迁安市","秦皇岛市","秦皇岛市","海港区","山海关区","北戴河区","青龙满族自治县","昌黎县","抚宁区","卢龙县","邯郸市","邯郸市","邯山区","丛台区","复兴区","峰峰矿区","临漳县","成安县","大名县","涉县","磁县","肥乡区","永年区","邱县","鸡泽县","广平县","馆陶县","魏县","曲周县","武安市","邢台市","邢台市","桥东区","桥西区","邢台县","临城县","内丘县","柏乡县","隆尧县","任县","南和县","宁晋县","巨鹿县","新河县","广宗县","平乡县","威县","清河县","临西县","南宫市","沙河市","保定市","保定市","竞秀区","莲池区","满城区","清苑区","涞水县","阜平县","徐水区","定兴县","唐县","高阳县","容城县","涞源县","望都县","安新县","易县","曲阳县","蠡县","顺平县","博野县","雄县","涿州市","定州市","安国市","高碑店市","张家口市","张家口市","桥东区","桥西区","宣化区","下花园区","张北县","康保县","沽源县","尚义县","蔚县","阳原县","怀安县","万全区","怀来县","涿鹿县","赤城县","崇礼区","承德市","承德市","双桥区","双滦区","鹰手营子矿区","承德县","兴隆县","平泉市","滦平县","隆化县","丰宁满族自治县","宽城满族自治县","围场满族蒙古族自治县","沧州市","沧州市","新华区","运河区","沧县","青县","东光县","海兴县","盐山县","肃宁县","南皮县","吴桥县","献县","孟村回族自治县","泊头市","任丘市","黄骅市","河间市","廊坊市","廊坊市","安次区","广阳区","固安县","永清县","香河县","大城县","文安县","大厂回族自治县","霸州市","三河市","衡水市","衡水市","桃城区","枣强县","武邑县","武强县","饶阳县","安平县","故城县","景县","阜城县","冀州区","深州市","山西省","太原市","太原市","小店区","迎泽区","杏花岭区","尖草坪区","万柏林区","晋源区","清徐县","阳曲县","娄烦县","古交市","大同市","大同市","城区","矿区","南郊区","新荣区","阳高县","天镇县","广灵县","灵丘县","浑源县","左云县","大同县","阳泉市","阳泉市","城区","矿区","郊区","平定县","盂县","长治市","长治市","城区","郊区","长治县","襄垣县","屯留县","平顺县","黎城县","壶关县","长子县","武乡县","沁县","沁源县","潞城市","晋城市","晋城市","城区","沁水县","阳城县","陵川县","泽州县","高平市","朔州市","朔州市","朔城区","平鲁区","山阴县","应县","右玉县","怀仁县","晋中市","晋中市","榆次区","榆社县","左权县","和顺县","昔阳县","寿阳县","太谷县","祁县","平遥县","灵石县","介休市","运城市","运城市","盐湖区","临猗县","万荣县","闻喜县","稷山县","新绛县","绛县","垣曲县","夏县","平陆县","芮城县","永济市","河津市","忻州市","忻州市","忻府区","定襄县","五台县","代县","繁峙县","宁武县","静乐县","神池县","五寨县","岢岚县","河曲县","保德县","偏关县","原平市","临汾市","临汾市","尧都区","曲沃县","翼城县","襄汾县","洪洞县","古县","安泽县","浮山县","吉县","乡宁县","大宁县","隰县","永和县","蒲县","汾西县","侯马市","霍州市","吕梁市","吕梁市","离石区","文水县","交城县","兴县","临县","柳林县","石楼县","岚县","方山县","中阳县","交口县","孝义市","汾阳市","内蒙古自治区","呼和浩特市","呼和浩特市","新城区","回民区","玉泉区","赛罕区","土默特左旗","托克托县","和林格尔县","清水河县","武川县","包头市","包头市","东河区","昆都仑区","青山区","石拐区","白云鄂博矿区","九原区","土默特右旗","固阳县","达尔罕茂明安联合旗","乌海市","乌海市","海勃湾区","海南区","乌达区","赤峰市","赤峰市","红山区","元宝山区","松山区","阿鲁科尔沁旗","巴林左旗","巴林右旗","林西县","克什克腾旗","翁牛特旗","喀喇沁旗","宁城县","敖汉旗","通辽市","通辽市","科尔沁区","科尔沁左翼中旗","科尔沁左翼后旗","开鲁县","库伦旗","奈曼旗","扎鲁特旗","霍林郭勒市","鄂尔多斯市","鄂尔多斯市","东胜区","康巴什区","达拉特旗","准格尔旗","鄂托克前旗","鄂托克旗","杭锦旗","乌审旗","伊金霍洛旗","呼伦贝尔市","呼伦贝尔市","海拉尔区","扎赉诺尔区","阿荣旗","莫力达瓦达斡尔族自治旗","鄂伦春自治旗","鄂温克族自治旗","陈巴尔虎旗","新巴尔虎左旗","新巴尔虎右旗","满洲里市","牙克石市","扎兰屯市","额尔古纳市","根河市","巴彦淖尔市","巴彦淖尔市","临河区","五原县","磴口县","乌拉特前旗","乌拉特中旗","乌拉特后旗","杭锦后旗","乌兰察布市","乌兰察布市","集宁区","卓资县","化德县","商都县","兴和县","凉城县","察哈尔右翼前旗","察哈尔右翼中旗","察哈尔右翼后旗","四子王旗","丰镇市","兴安盟","乌兰浩特市","阿尔山市","科尔沁右翼前旗","科尔沁右翼中旗","扎赉特旗","突泉县","锡林郭勒盟","二连浩特市","锡林浩特市","阿巴嘎旗","苏尼特左旗","苏尼特右旗","东乌珠穆沁旗","西乌珠穆沁旗","太仆寺旗","镶黄旗","正镶白旗","正蓝旗","多伦县","阿拉善盟","阿拉善左旗","阿拉善右旗","额济纳旗","福建省","江西省","山东省","河南省","湖北省","湖南省","广东省","广西壮族自治区","海南省","重庆","四川省","贵州省","云南省","西藏自治区","陕西省","甘肃省","青海省","宁夏回族自治区","新疆维吾尔自治区","台湾省","香港特别行政区","澳门特别行政区"]
     * toolList : [{"companyId":"979995434422681602","dataId":4682,"dataName":"熔纤机","dataType":8,"id":6743,"remark":"3","status":0,"units":"台"},{"companyId":"979995434422681602","dataId":4683,"dataName":"小汽车","dataType":8,"id":6744,"remark":"4","status":0,"units":"台"}]
     * bizList : ["维修","安装","设计","监理","分发包","保养"]
     * abilityList : [{"companyId":"979995434422681602","dataId":4677,"dataName":"注册建造师","dataType":7,"id":6728,"remark":"11","status":0,"units":"人"},{"companyId":"979995434422681602","dataId":4678,"dataName":"初级安防员","dataType":7,"id":6729,"remark":"22","status":0,"units":"人"}]
     * orderCounts : {"aptitudePics":"logo/66ab414f8ae348028a2c9f446644d1e4.jpg","designCount":74,"evaluateNum":77,"goodRate":17142,"installCount":68,"publicPraise":196,"repairCount":254}
     * gloryList : [{"awardDate":"2019-04-27","awardOrg":"测试颁发机构","awardTime":"2019-04-27 00:00:00","honorName":"测试荣誉名称","honorPics":"default/default_avatar.png,default/default_avatar.png"}]
     */

    private CasesBean cases;
    private int verifyStatus;
    private OrgUnitEntity orgUnit;
    private OrderCountsBean orderCounts;
    private List<String> sysList;
    // 资质
    private List<AptitudeCertificateEntity> aptitudeList;
    private List<String> areaList;
    private List<SgZzNlBean.ListBean> toolList;
    private List<String> bizList;
    private List<SgZzNlBean.ListBean> abilityList;
    // 荣誉
    private List<GloryCertificateEntity> gloryList;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public class CasesBean implements Serializable {

        private List<ListBean> list;

        @NoArgsConstructor
        @AllArgsConstructor
        @Getter
        @Setter
        public class ListBean implements Serializable {
            private String assigneeCompanyId;
            private String assigneeOrgCode;
            private String assigneeTopCompanyId;
            private String assigneeUserId;
            private int budget;
            private String budgetCost;
            private String businessOneCode;
            private int businessOneId;
            private String businessOneName;
            private String clientCompanyName;
            private String connector;
            private String connectorPhone;
            private String createTime;
            private String createUserId;
            private String description;
            private String detailPlace;
            private String editTime;
            private String editUserId;
            private String endTime;
            private String id;
            private String latitude;
            private String longitude;
            private int newOrder;
            private String orderNo;
            private int orderTag;
            private Long ownerCompanyId;
            private String ownerOrgCode;
            private Long ownerTopCompanyId;
            private String ownerUserId;
            private int predictTime;
            private int revertTimeLimit;
            private int status;
            private String zone;

        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public class OrderCountsBean implements Serializable {

        private String aptitudePics;
        private int designCount;
        private int evaluateNum;
        private int goodRate;
        private int installCount;
        private int publicPraise;
        private int repairCount;

    }

}
