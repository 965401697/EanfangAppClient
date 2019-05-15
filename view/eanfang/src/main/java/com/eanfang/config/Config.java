package com.eanfang.config;

import android.os.Environment;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.eanfang.application.EanfangApplication;
import com.eanfang.biz.model.bean.BaseDataBean;
import com.eanfang.biz.model.bean.ConstAllBean;
import com.eanfang.biz.model.entity.BaseDataEntity;
import com.eanfang.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author wen
 * Created at 2017/3/2
 * @desc app配置信息
 */
public class Config {
    private static Config config = new Config();

    public static final String VIDEO_STORAGE_DIR = Environment.getExternalStorageDirectory() + "/eanfang/";
    /**
     * 基础数据 分隔字符
     */
    private static final String BASE_CODE_SPLIT_STR = "\\.";
    private static final String BASE_CODE_JOINING_STR = ".";
    /**
     * 解析地址的长度
     */
    private static final int ADDRESS_COUNT = 3;


    private ConstAllBean constBean;
    private BaseDataBean baseDataBean;

    private List<BaseDataEntity> businessList;

    private List<BaseDataEntity> serviceList;

    private List<BaseDataEntity> regionList;

    private List<BaseDataEntity> industryList;

    private List<BaseDataEntity> modelList;

    public ConstAllBean getConstBean() {
        if (constBean == null) {
            synchronized (Config.class) {
                if (constBean == null) {
                    constBean = (ConstAllBean) EanfangApplication.get().get(ConstAllBean.class.getName(), ConstAllBean.class);
                }
            }
        }
        return constBean;
    }

    public void cleanConstBean() {
        this.constBean = null;
    }

    public BaseDataBean getBaseDataBean() {
        if (baseDataBean == null) {
            synchronized (Config.class) {
                if (baseDataBean == null) {
                    baseDataBean = (BaseDataBean) EanfangApplication.get().get(BaseDataBean.class.getName(), BaseDataBean.class);
                }
            }
        }
        return baseDataBean;
    }

    public void cleanBaseDataBean() {
        this.baseDataBean = null;
    }

    public List<BaseDataEntity> getBusinessList() {
        if (businessList == null) {
            synchronized (Config.class) {
                if (businessList == null) {
                    businessList = Stream.of(getBaseDataBean().getData()).filter(bean -> bean.getDataType().equals(Constant.SYS_TYPE)).toList();
                }
            }
        }
        return businessList;
    }

    //系统类别-----级别
    public List<BaseDataEntity> getBusinessList(int level) {
        return Stream.of(getBusinessList()).filter(bean -> bean.getLevel().equals(level + 1)).toList();
    }

    public List<BaseDataEntity> getServiceList() {
        if (serviceList == null) {
            synchronized (Config.class) {
                if (serviceList == null) {
                    serviceList = Stream.of(getBaseDataBean().getData()).filter(bean -> bean.getDataType().equals(Constant.BIZ_TYPE)).toList();
                }
            }
        }
        return serviceList;
    }

    public List<BaseDataEntity> getServiceList(int level) {
        return Stream.of(getServiceList()).filter(bean -> bean.getLevel().equals(level + 1)).toList();
    }

    public List<BaseDataEntity> getRegionList() {
        if (regionList == null) {
            synchronized (Config.class) {
                if (regionList == null) {
                    regionList = Stream.of(getBaseDataBean().getData()).filter(bean -> bean.getDataType().equals(Constant.AREA)).toList();
                }
            }
        }
        return regionList;
    }

    public List<BaseDataEntity> getRegionList(int level) {
        return Stream.of(getRegionList()).filter(bean -> bean.getLevel().equals(level + 1)).toList();
    }

    public List<BaseDataEntity> getIndustryList() {
        if (industryList == null) {
            synchronized (Config.class) {
                if (industryList == null) {
                    industryList = Stream.of(getBaseDataBean().getData()).filter(bean -> bean.getDataType().equals(Constant.INDUSTRY)).toList();
                }
            }
        }
        return industryList;
    }

    public List<BaseDataEntity> getIndustryList(int level) {
        return Stream.of(getIndustryList()).filter(bean -> bean.getLevel().equals(level + 1)).toList();
    }

    public List<BaseDataEntity> getModelList() {
        if (modelList == null) {
            synchronized (Config.class) {
                if (modelList == null) {
                    modelList = Stream.of(getBaseDataBean().getData()).filter(bean -> bean.getDataType().equals(Constant.MODEL)).toList();
                }
            }
        }
        return modelList;
    }

    //品牌-----级别
    public List<BaseDataEntity> getModelList(int level) {
        return Stream.of(getModelList()).filter(bean -> bean.getLevel().equals(level + 1)).toList();
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static Config get() {
        if (config == null) {
            synchronized (Config.class) {
                if (config == null) {
                    config = new Config();
                }
            }
        }
        return config;
    }

    /**
     * 根据
     *
     * @param code
     * @param level
     * @return
     */
    public String getModelNameByCode(String code, int level) {
        return getBaseNameByCode(Constant.MODEL, code, level);
    }

    /**
     * 根据系统名称获取code
     * 暂不考虑名称重复问题
     */
    public String getBusinessCodeByName(String name, int level) {
        List<String> bussList = getBaseCodeByName(name, level, Constant.SYS_TYPE);
        if (bussList == null || bussList.isEmpty()) {
            return null;
        } else {
            return bussList.get(0);
        }
    }

    /**
     * 根据系统编码获得id
     */
    public Integer getBusinessIdByCode(String code, int level) {
        return getBaseIdByCode(code, level, Constant.SYS_TYPE);
    }

    /**
     * 根据三级业务类型code 获取任意 层级 name
     *
     * @param code  编码
     * @param level 获取层级 （从0开始）
     * @return
     */
    public String getBusinessNameByCode(String code, int level) {
        return getBaseNameByCode(Constant.SYS_TYPE, code, level);
    }

    /**
     * 根据三级业务类型 id 获取 name
     *
     * @param id
     * @return
     */
    public String getBusinessNameById(Integer id) {
        return getBaseNameById(id, Constant.SYS_TYPE);
    }

    /**
     * 根据code 获取服务名字
     *
     * @param code
     * @return
     */
    public String getServiceNameByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        return getBaseNameByCode(code, Constant.BIZ_TYPE);
    }

    /**
     * 根据id 获取 服务名字
     *
     * @param id
     * @return
     */
    public String getServiceNameById(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }
        return getBaseNameById(id, Constant.BIZ_TYPE);
    }

    /**
     * 根据系统code获取名称
     */
    //    public String getBusinessName(String code) {
//
//        //code=1.1.1.16
//        List<BaseDataBean> countyList = Stream.of(this.baseDataBean).filter(bean -> bean.getDataType() == Constant.SYS_TYPE && bean.getLevel() == 2 && bean.getDataCode().equals(code)).toList();
//
//        if (countyList == null || countyList.isEmpty()) {
//            return "";
//        }
//        if (countyList.size() > 1) {
//            List<BaseDataBean> cityList = Stream.of(countyList).filter(bean -> bean.getDataType() == Constant.SYS_TYPE && bean.getLevel() == 2 && bean.getDataCode().equals(code)).toList();
//            if (countyList == null || countyList.isEmpty()) {
//                return "";
//            }
//            //特殊情况 如果遇到再加代码
//            if (countyList.size() > 1) {
//                return "";
//            } else {
//                return Stream.of(countyList).filter(bean -> bean.getDataName().startsWith(cityList.get(0).getDataName())).toList().get(0).getDataName();
//            }
//        } else {
//            return countyList.get(0).getDataName();
//        }
//    }


    /**
     * 根据地址取code
     */
    public String getAreaCodeByName(String cityStr, String countyStr) {

        List<String> countyCodeList = getBaseCodeByName(countyStr, 3, Constant.AREA);
        if (countyCodeList == null) {
            return null;
        }
        //区县没有重复  正好查到
        if (countyCodeList.size() == 1) {
            return countyCodeList.get(0);
        }
        //如果重复，查询 city
        for (int i = 0; i < countyCodeList.size(); i++) {
            List<String> cityCodeList = getBaseCodeByName(cityStr, 2, Constant.AREA);
            //没有对应城市，直接干掉
            if (cityCodeList == null) {
                return null;
            }
            List<String> resultList = Stream.of(countyCodeList).filter(county -> Stream.of(cityCodeList).filter(city -> county.startsWith(city)).count() > 0).distinct().toList();
            if (resultList == null) {
                return null;
            } else if (resultList.size() == 1) {
                return resultList.get(0);
            } else {
                // 相同城市名下有相同区县 特殊情况 暂不考虑
                return null;
            }
        }
        return null;
//
//        List<BaseDataBean> countyList = Stream.of(this.baseDataBean).filter(bean -> bean.getDataType() == Constant.AREA && bean.getLevel() == 4 && bean.getDataName().equals(countyStr)).toList();
//
//        if (countyList == null || countyList.isEmpty()) {
//            return null;
//        }
//        if (countyList.size() > 1) {
//            List<BaseDataBean> cityList = Stream.of(this.baseDataBean).filter(
//                    bean -> bean.getDataType() == Constant.AREA
//                            && bean.getLevel() == 3
//                            && bean.getDataName().startsWith(cityStr)
//            ).toList();
//            if (cityList == null || cityList.isEmpty()) {
//                return null;
//            }
//
//            for (int i = 0; i < cityList.size(); i++) {
//                String cityCode = cityList.get(i).getDataCode();
//                List<BaseDataBean> resultList = Stream.of(countyList).filter(county -> county.getDataCode().startsWith(cityCode)).toList();
//                if (resultList != null && !resultList.isEmpty()) {
//                    return resultList.get(0).getDataCode();
//                }
//            }
//            return null;
//        } else {
//            return countyList.get(0).getDataCode();
//        }

    }

    /**
     * 根据code取地址
     */
    public List<String> getAreaNameByCode(String code) {

        List<String> addressList = new ArrayList<>(ADDRESS_COUNT);

        addressList.add(getBaseNameByCode(Constant.AREA, code, 1));
        addressList.add(getBaseNameByCode(Constant.AREA, code, 2));
        addressList.add(getBaseNameByCode(Constant.AREA, code, 3));
        //如果省份和城市同名，则移除
//        if (addressList.get(0).equals(addressList.get(1))) {
//            addressList.remove(0);
//        }
        return addressList;
//
//        if (code.split(BASE_CODE_SPLIT_STR).length < ADDRESS_COUNT) {
//            return new ArrayList<>(ADDRESS_COUNT);
//        }
//        List<String> addressList = new ArrayList<>(ADDRESS_COUNT);
//        //区县
//        BaseDataBean countyBean = Stream.of(this.baseDataBean).filter(county -> county.getDataCode().equals(code)).toList().get(0);
//        addressList.add(countyBean.getDataName());
//
//        BaseDataBean cityBean = Stream.of(this.baseDataBean).filter(county -> county.getDataCode().equals(code.substring(0, code.lastIndexOf(BASE_CODE_SPLIT_STR)))).toList().get(0);
//        addressList.add(cityBean.getDataName());
//
//        BaseDataBean perBean = Stream.of(this.baseDataBean).filter(county -> county.getDataCode().equals(cityBean.getDataCode().substring(0, cityBean.getDataCode().lastIndexOf(BASE_CODE_SPLIT_STR)))).toList().get(0);
//        addressList.add(perBean.getDataName());


    }


    /**
     * 根据code获取 省-市-区县
     *
     * @param code
     * @return
     */
    public String getAddressByCode(String code) {
        return Stream.of(getAreaNameByCode(code)).filter(name -> !StringUtils.isEmpty(name)).collect(Collectors.joining("-"));
    }

    /**
     * 根据id获取 省-市-区县
     *
     * @param id
     * @return
     */
    public String getAddressById(Integer id) {
        return getAddressByCode(getBaseCodeById(id, Constant.AREA));
    }


    /**
     * 根据 指定的code 获取 指定层级数据
     *
     * @param type  类型
     * @param code  code编码
     * @param level 想要获取的层级（从0开始）
     * @return
     */
    public String getBaseNameByCode(int type, String code, int level) {
        //code 长度验证
        if (StringUtils.isEmpty(code) || code.split(BASE_CODE_SPLIT_STR).length <= level) {
            return null;
        }
        List codeList = Arrays.asList(code.split(BASE_CODE_SPLIT_STR));
        String oneCode = Stream.of((codeList.subList(0, level + 1))).collect(Collectors.joining(BASE_CODE_JOINING_STR)).toString();
        return getBaseNameByCode(oneCode, type);
    }

    /**
     * 根据基础数据的code 获取对应的名字
     *
     * @param code
     * @param type
     * @return
     */
    public String getBaseNameByCode(String code, int type) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        return Stream.of(getDataListByType(type)).filter(bean -> bean.getDataType() == type && bean.getDataCode().equals(code)).map(bean -> bean.getDataName() + "").findFirst().orElseGet(() -> null);
    }

    /**
     * 根据基础数据的id 获取对应的名字
     *
     * @param id
     * @param type
     * @return
     */
    public String getBaseNameById(Integer id, int type) {
        if (id == null || id <= 0) {
            return null;
        }
        return Stream.of(getDataListByType(type)).filter(bean -> bean.getDataType() == type && bean.getDataId().intValue() == id).map(bean -> bean.getDataName() + "").findFirst().orElseGet(() -> null);
    }

    /**
     * 根据 名字 获取对应的 code 集合
     *
     * @param name  名字
     * @param level 想要查询的层级
     * @param type  类型
     * @return
     */
    public List<String> getBaseCodeByName(String name, int level, int type) {
        if (StringUtils.isEmpty(name)) {
            return Arrays.asList("");
        }
        return Stream.of(getDataListByType(type)).filter(bean -> bean.getDataType() == type && (level + 1) == bean.getLevel() && bean.getDataName().equals(name))
                .map(bean -> bean.getDataCode()).distinct().toList();

    }

    /**
     * 根据基础数据的id 获取对应的code
     *
     * @param id
     * @param type
     * @return
     */
    public String getBaseCodeById(Integer id, int type) {
        if (id == null || id <= 0) {
            return null;
        }
        return Stream.of(getDataListByType(type)).filter(bean -> bean.getDataType() == type && bean.getDataId().equals(id)).map(bean -> bean.getDataCode() + "").findFirst().orElseGet(() -> null);
    }

    /**
     * 根据下级 code  获得指定 上级 code
     *
     * @param code
     * @param level
     * @return
     */
    public String getBaseCodeByLevel(String code, Integer level) {
//        if (id == null || id <= 0) {
//            return null;
//        }
//        String code = Stream.of(getDataListByType(type)).filter(bean -> bean.getDataType() == type && bean.getDataId().equals(id)).map(bean -> bean.getDataCode() + "").findFirst().orElseGet(() -> null);
        String[] codeArr = code.split(BASE_CODE_SPLIT_STR);
        if (codeArr.length <= level) {
            return null;
        }
        StringBuilder levelCode = new StringBuilder();
        for (int i = 0; i < level + 1; i++) {
            levelCode.append(codeArr[i]);
            if (i != level) {
                levelCode.append(BASE_CODE_JOINING_STR);
            }
        }
        return levelCode.toString();
    }

    /**
     * 根据 code 获取 id
     *
     * @param code code
     * @param type
     * @return
     */
    public Integer getBaseIdByCode(String code, int level, int type) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        return Stream.of(getDataListByType(type)).filter(bean -> bean.getDataType() == type && code.startsWith(bean.getDataCode()) && bean.getLevel() == (level + 1)).
                map(bean -> bean.getDataId().intValue()).findFirst().orElseGet(() -> null);

    }

//    /**
//     * 获得基础数据集合
//     *
//     * @param type
//     * @param level
//     * @return
//     */
//    public List<BaseDataEntity> getBaseDataList(int type, int level) {
//        return Stream.of(this.getBaseDataBean().getData()).filter(data -> data.getDataType().equals(type) && data.getLevel().equals(level)).toList();
//    }

    private List<BaseDataEntity> getDataListByType(int type) {
        if (type == Constant.SYS_TYPE) {
            return getBusinessList();
        } else if (type == Constant.BIZ_TYPE) {
            return getServiceList();
        } else if (type == Constant.AREA) {
            return getRegionList();
        } else if (type == Constant.INDUSTRY) {
            return getIndustryList();
        } else if (type == Constant.MODEL) {
            return getModelList();
        }
        return getBaseDataBean().getData();
    }

//    /**
//     * 根据id获得code
//     */
//    public List<String> getCode(List<Long> list, int type) {
//        return Stream.of(this.getBaseDataBean()).filter(bean -> bean.getDataType() == type && list.contains(bean.getDataId())).map(bean -> bean.getDataCode() + "").toList();
//    }


}
