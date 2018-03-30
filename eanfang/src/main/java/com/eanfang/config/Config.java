package com.eanfang.config;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.localcache.CacheUtil;
import com.eanfang.model.BaseDataBean;
import com.eanfang.model.ConstAllBean;
import com.eanfang.util.ApkUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.V;
import com.yaf.sys.entity.BaseDataEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * @author wen
 * Created at 2017/3/2
 * @desc app配置信息
 */
public class Config {
    /**
     * 基础数据 分隔字符
     */
    private final String BASE_CODE_SPLIT_STR = "\\.";
    private final String BASE_CODE_JOINING_STR = ".";
    /**
     * 解析地址的长度
     */
    private final int ADDRESS_COUNT = 3;

    private ConstAllBean constAllBean;
    private BaseDataBean baseDataBean;

//    private List<BaseDataEntity> businessList;
//    private List<BaseDataEntity> serviceList;
//    private List<BaseDataEntity> regionList;
//    private List<BaseDataEntity> industryList;
//    private List<BaseDataEntity> modelList;

    private Context context;
    private String packageName;

    /**
     * 获取实例
     *
     * @return
     */
    public static Config get(Context context) {
        return new Config(context);
    }

    private Config(Context context) {
        this.context = context;
        this.packageName = context.getApplicationInfo().packageName;
    }

    private <T> T getBase(String className, Class<T> clazz) {
        String info = CacheUtil.get(context, packageName, className);
        if (StringUtils.isEmpty(info)) {
            return null;
        }
        return JSONObject.parseObject(info, clazz);
    }

    public ConstAllBean getConstBean() {
        if (constAllBean == null) {
            constAllBean = getBase(ConstAllBean.class.getName(), ConstAllBean.class);
        }
        return constAllBean == null ? new ConstAllBean() : constAllBean;
    }

    public BaseDataBean getBaseDataBean() {
        if (baseDataBean == null) {
            baseDataBean = getBase(BaseDataBean.class.getName(), BaseDataBean.class);
        }
        return baseDataBean == null ? new BaseDataBean() : baseDataBean;
    }

    public List<BaseDataEntity> getBusinessList() {
        return Stream.of(getBaseDataBean().getData()).filter(bean -> bean.getDataType().equals(Constant.SYS_TYPE)).toList();
    }

    public List<BaseDataEntity> getBusinessList(int level) {
        return Stream.of(getBaseDataBean().getData()).filter(bean -> bean.getDataType().equals(Constant.SYS_TYPE) && bean.getLevel().equals(level + 1)).toList();
    }

    public List<BaseDataEntity> getServiceList() {
        return Stream.of(getBaseDataBean().getData()).filter(bean -> bean.getDataType().equals(Constant.BIZ_TYPE)).toList();
    }

    public List<BaseDataEntity> getServiceList(int level) {
        return Stream.of(getBaseDataBean().getData()).filter(bean -> bean.getDataType().equals(Constant.BIZ_TYPE) && bean.getLevel().equals(level + 1)).toList();
    }

    public List<BaseDataEntity> getRegionList() {
        return Stream.of(getBaseDataBean().getData()).filter(bean -> bean.getDataType().equals(Constant.AREA)).toList();
    }

    public List<BaseDataEntity> getRegionList(int level) {
        return Stream.of(getBaseDataBean().getData()).filter(bean -> bean.getDataType().equals(Constant.AREA) && bean.getLevel().equals(level + 1)).toList();
    }

    public List<BaseDataEntity> getIndustryList() {
        return Stream.of(getBaseDataBean().getData()).filter(bean -> bean.getDataType().equals(Constant.INDUSTRY)).toList();
    }

    public List<BaseDataEntity> getIndustryList(int level) {
        return Stream.of(getIndustryList()).filter(bean -> bean.getLevel().equals(level + 1)).toList();
    }

    public List<BaseDataEntity> getModelList() {
        return Stream.of(getBaseDataBean().getData()).filter(bean -> bean.getDataType().equals(Constant.MODEL)).toList();
    }

    public List<BaseDataEntity> getModelList(int level) {
        return Stream.of(getBaseDataBean().getData()).filter(bean -> bean.getDataType().equals(Constant.MODEL) && bean.getLevel().equals(level + 1)).toList();
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
        return Stream.of(getDataListByType(type)).filter(bean -> bean.getDataType() == type && bean.getDataId() == id).map(bean -> bean.getDataName() + "").findFirst().orElseGet(() -> null);
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
                map(bean -> bean.getDataId()).findFirst().orElseGet(() -> null);

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

}
