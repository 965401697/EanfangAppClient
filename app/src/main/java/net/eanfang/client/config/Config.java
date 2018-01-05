
package net.eanfang.client.config;

import android.content.Context;

import com.annimon.stream.Stream;
import com.eanfang.util.FileUtils;
import com.eanfang.util.SharePreferenceUtil;

import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.ui.model.BaseDataBean;
import net.eanfang.client.ui.model.ConstAllBean;
import net.eanfang.client.util.ConfigUtils;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


/**
 * @author wen
 *         Created at 2017/3/2
 * @desc app配置信息
 */
public class Config {
    private static Config config = null;
    @Getter
    @Setter
    private ConstAllBean constBean;
    @Getter
    @Setter
    private List<BaseDataBean> baseDataBean;


    private List<BaseDataBean> businessOneList = new ArrayList<>();
    private List<String> repairStatus = new ArrayList<>();
    private List<String> repairStatusClient = new ArrayList<>();
    private List<String> repairStatusWorker = new ArrayList<>();
    private List<String> taskReadStatus = new ArrayList<>();
    private List<String> checkReadStatus = new ArrayList<>();
    private List<String> designOrderStatus = new ArrayList<>();
    private List<String> installStatus = new ArrayList<>();
    private List<String> repairSelectWorker = new ArrayList<>();


    public List<String> getInstallStatus() {
        return installStatus;
    }

    public List<String> getRepairStatus() {
        return repairStatus;
    }

    public List<String> getRepairStatusClient() {
        return repairStatusClient;
    }

    public List<String> getRepairStatusWorker() {
        return repairStatusWorker;
    }

    public List<String> getTaskReadStatus() {
        return taskReadStatus;
    }

    public List<String> getCheckReadStatus() {
        return checkReadStatus;
    }

    public List<String> getDesignOrderStatus() {
        return designOrderStatus;
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static Config getConfig() {
        if (config == null) {
            config = new Config();
            config.init(EanfangApplication.getApplication().getBaseContext());
        }
        return config;
    }

    /**
     * 必须在 application 中需要做初始化
     *
     * @param context
     */

    public void init(Context context) {
        //初始化配置文件读取器
        ConfigUtils.initProperties(context);

        //文件初始化 toto
        FileUtils.init("yianfang");
        SharePreferenceUtil.get().init(context);
        //工作任务 已读/未读
        taskReadStatus = ConfigUtils.getTaskReadStatus();
        //工作检查 状态
        checkReadStatus = ConfigUtils.getCheckReadStatus();
        //报修单状态
        repairStatus = ConfigUtils.getRepairStatus();
        //报修单列表 客户的状态
        repairStatusClient = ConfigUtils.getRepairStatus();
        //报修单列表 技师的状态
        repairStatusWorker = ConfigUtils.getRepairStatus();
        //报装单
        installStatus = ConfigUtils.getInstallStatus();
        getRepairStatusWorker().remove("预付费");

        //免费设计
        designOrderStatus = ConfigUtils.getDesignOrderStatus();
        //选择技师
        repairSelectWorker = ConfigUtils.getRepairSelectWorkers();


    }


    /**
     * 业务类型
     */
    public List<BaseDataBean> getBusinessOneList() {

        if (businessOneList != null && !businessOneList.isEmpty()) {
            return businessOneList;
        }
        return businessOneList = Stream.of(baseDataBean).filter(bean -> bean.getDataType() == Constant.SYS_TYPE && bean.getLevel() == 2).toList();
    }

    /**
     * 根据系统名称获取code
     */
    public String getBusinessCode(String name) {

        List<BaseDataBean> countyList = Stream.of(this.baseDataBean).filter(bean -> bean.getDataType() == Constant.SYS_TYPE && bean.getLevel() == 2 && bean.getDataName().equals(name)).toList();

        if (countyList == null || countyList.isEmpty()) {
            return "";
        } else {
            return countyList.get(0).getDataCode();
        }
    }

    /**
     * 根据系统编码获得id
     */
    public List<String> getBusinessId(List<String> list) {
        return Stream.of(this.getBaseDataBean()).filter(bean -> bean.getDataType() == Constant.SYS_TYPE && list.contains(bean.getDataCode())).map(bean -> bean.getDataId() + "").toList();

    }

    public String getServId(String code) {
        return Stream.of(this.getBaseDataBean()).filter(bean -> bean.getDataType() == Constant.BIZ_TYPE && bean.getDataCode().equals(code)).toList().get(0).getDataId() + "";

    }

    public String getServName(int id, int type) {
        return Stream.of(this.getBaseDataBean()).filter(bean -> bean.getDataType() == type && bean.getDataId() == id).toList().get(0).getDataName() + "";

    }


    /**
     * 根据系统code获取名称
     */
    public String getBusinessName(String code) {
        List<BaseDataBean> countyList = Stream.of(this.baseDataBean).filter(bean -> bean.getDataType() == Constant.SYS_TYPE && bean.getLevel() == 2 && bean.getDataCode().equals(code)).toList();

        if (countyList == null || countyList.isEmpty()) {
            return "";
        }
        if (countyList.size() > 1) {
            List<BaseDataBean> cityList = Stream.of(countyList).filter(bean -> bean.getDataType() == Constant.SYS_TYPE && bean.getLevel() == 2 && bean.getDataCode().equals(code)).toList();
            if (countyList == null || countyList.isEmpty()) {
                return "";
            }
            //特殊情况 如果遇到再加代码
            if (countyList.size() > 1) {
                return "";
            } else {
                return Stream.of(countyList).filter(bean -> bean.getDataName().startsWith(cityList.get(0).getDataName())).toList().get(0).getDataName();
            }
        } else {
            return countyList.get(0).getDataName();
        }
    }

    /**
     * 根据地址取code
     */

    public String getRegCode(String cityStr, String countyStr) {

        List<BaseDataBean> countyList = Stream.of(this.baseDataBean).filter(bean -> bean.getDataType() == Constant.AREA && bean.getLevel() == 4 && bean.getDataName().equals(countyStr)).toList();

        if (countyList == null || countyList.isEmpty()) {
            return "";
        }
        if (countyList.size() > 1) {
            List<BaseDataBean> cityList = Stream.of(this.baseDataBean).filter(
                    bean -> bean.getDataType() == Constant.AREA
                            && bean.getLevel() == 3
                            && bean.getDataName().startsWith(cityStr)
            ).toList();
            if (cityList == null || cityList.isEmpty()) {
                return "";
            }

            for (int i = 0; i < cityList.size(); i++) {
                String cityCode = cityList.get(i).getDataCode();
                List<BaseDataBean> resultList = Stream.of(countyList).filter(county -> county.getDataCode().startsWith(cityCode)).toList();
                if (resultList != null && !resultList.isEmpty()) {
                    return resultList.get(0).getDataCode();
                }
            }
            return "";
        } else {
            return countyList.get(0).getDataCode();
        }

    }

    /**
     * 根据code取地址
     */
    public String[] getAreaName(String code) {

        if (code.split("\\.").length < 3) {
            return new String[3];
        }
        String[] addr = new String[3];
        //区县
        BaseDataBean countyBean = Stream.of(this.baseDataBean).filter(county -> county.getDataCode().equals(code)).toList().get(0);
        addr[0] = countyBean.getDataName();

        BaseDataBean cityBean = Stream.of(this.baseDataBean).filter(county -> county.getDataCode().equals(code.substring(0, code.lastIndexOf(".")))).toList().get(0);
        addr[1] = cityBean.getDataName();

        BaseDataBean perBean = Stream.of(this.baseDataBean).filter(county -> county.getDataCode().equals(cityBean.getDataCode().substring(0, cityBean.getDataCode().lastIndexOf(".")))).toList().get(0);
        addr[2] = perBean.getDataName();

        return addr;
    }

    public String getAddress(String code) {
        String[] area = getAreaName(code);
        StringBuilder builder = new StringBuilder();
        builder.append(area[2]).append("-");
        if (!area[1].contains("市辖区")) {
            builder.append(area[1]).append("-");
        }
        builder.append(area[0]);

        return builder.toString();
    }

    /**
     * 根据id获得code
     */
    public List<String> getCode(List<Integer> list, int type) {
        return Stream.of(this.getBaseDataBean()).filter(bean -> bean.getDataType() == type && list.contains(bean.getDataId())).map(bean -> bean.getDataCode() + "").toList();
    }


}
