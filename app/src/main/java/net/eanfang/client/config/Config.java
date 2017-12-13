
package net.eanfang.client.config;

import android.content.Context;

import com.eanfang.util.FileUtils;
import com.eanfang.util.SharePreferenceUtil;

import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.ui.model.BaseDataBean;
import net.eanfang.client.ui.model.BusinessOne;
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
    private  ConstAllBean constBean;
    @Getter
    @Setter
    private  List<BaseDataBean> baseDataBean;
    //Context mContext;
    //public String mSession = "";
    //String mCV = "1.0"; // 客户端版本号
    //int mCS = 1;        // 客户端型号
    //int mScreenHight, mScreenWidth;    // 屏幕宽高
    //float mDensity;                      // 屏幕密度
    // String mImei = "";
    //String mUa = "";        // 手机ua
    //String mVersion = "";        //配置版本号
    //  String mServer = "";
    // boolean mDebug = false;
    //boolean mPrint = false;
    // int mMode;
    //String appId;
    /**
     * 业务类型
     */
    private List<BusinessOne> businessOneList = new ArrayList<>();
    /**
     * 保养周期
     */
    private List<String> circleList = new ArrayList<>();
    /**
     * 报修状态
     */
    private List<String> repairStatus = new ArrayList<>();
    private List<String> repairStatusClient = new ArrayList<>();
    private List<String> repairStatusWorker = new ArrayList<>();
    /**
     * 工作任务 已读/未读
     */
    private List<String> taskReadStatus = new ArrayList<>();
    /**
     * 工作检查 订单状态
     */
    private List<String> checkReadStatus = new ArrayList<>();

    /**
     * 预算范围 选择器
     */
    private List<String> budgetLimit = new ArrayList<>();
    /**
     * 预计工期 选择器
     */
    private List<String> planLimit = new ArrayList<>();
    /**
     * 回复时限 选择器
     */
    private List<String> replyLimit = new ArrayList<>();
    /**
     * 免费设计 订单状态
     */
    private List<String> designOrderStatus = new ArrayList<>();
    /**
     * 能力等级
     */
    private List<String> workLevel = new ArrayList<>();
    /**
     * 工作年限
     */
    private List<String> workYear = new ArrayList<>();
    /**
     * 企业规模
     */
    private List<String> companyScale = new ArrayList<>();
    /**
     * 维保等级
     */
    private List<String> maintLevel = new ArrayList<>();
    /**
     * 维保结果
     */
    private List<String> maintResult = new ArrayList<>();

    /**
     * 到达实现
     */
    private List<String> arriveTime = new ArrayList<>();
    /**
     * 拍照类型
     */
    private List<String> photoType = new ArrayList<>();
    /**
     * 设备参数类型
     */
    private List<String> parameterType = new ArrayList<>();
    private List<String> repairConslusion = new ArrayList<>();
    private List<String> payType = new ArrayList<>();
    private List<String> fistFre = new ArrayList<>();
    private List<String> secondFre = new ArrayList<>();
    private List<String> thirdFre = new ArrayList<>();
    private List<String> orderList = new ArrayList<>();


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
        //业务类型初始化
        businessOneList = ConfigUtils.getBusinessOne();
        //报修单状态
        repairStatus = ConfigUtils.getRepairStatus();
        //报修单列表 客户的状态
        repairStatusClient = ConfigUtils.getRepairStatus();
        //报修单列表 技师的状态
        repairStatusWorker = ConfigUtils.getRepairStatus();
        getRepairStatusWorker().remove("预付费");
        //预算范围
        budgetLimit = ConfigUtils.getBudgetLimit();
        //预计工期
        planLimit = ConfigUtils.getPlanLimit();
        //回复时限
        replyLimit = ConfigUtils.getReplyLimit();
        //免费设计
        designOrderStatus = ConfigUtils.getDesignOrderStatus();
        //能力等级
        workLevel = ConfigUtils.getWorkLevel();
        //工作年限
        workYear = ConfigUtils.getWorkYear();
        //公司规模
        companyScale = ConfigUtils.getCompanyScale();
        //维保等级
        maintLevel = ConfigUtils.getMaintLevel();
        //维保结果
        maintResult = ConfigUtils.getMaintResult();
        //到达时限
        arriveTime = ConfigUtils.getArriveTime();
        //拍照类型
        photoType = ConfigUtils.getPhotoType();
        //设备类型
        parameterType = ConfigUtils.getParameterType();
        //设备类型
        repairConslusion = ConfigUtils.getRepairConslusion();
        //保养周期
        circleList = ConfigUtils.getCircle();
        //支付方式
        payType = ConfigUtils.getPayType();
        fistFre = ConfigUtils.getFirstFre();
        secondFre = ConfigUtils.getSecondFre();
        thirdFre = ConfigUtils.getThirdFre();
        orderList = ConfigUtils.getOrderList();

//        constBean = JSONObject.toJavaObject(EanfangApplication.getApplication().get(ConstAllBean.class.getName()), ConstAllBean.class);
//        baseDataBean = JSONArray.parseArray(EanfangApplication.getApplication().get(BaseDataBean.class.getName()).toJSONString(), BaseDataBean.class);
    }

    public List<String> getArriveTime() {
        return arriveTime;
    }

    public List<String> getFistFre() {
        return fistFre;
    }

    public List<String> getSecondFre() {
        return secondFre;
    }

    public List<String> getThirdFre() {
        return thirdFre;
    }

    public List<String> getOrderList() {
        return thirdFre;
    }

    public List<String> getPhotoType() {
        return photoType;
    }

    /**
     * 业务类型
     */
    public List<BusinessOne> getBusinessOneList() {
        return businessOneList;
    }

    /**
     * 保养周期
     */
    public List<String> getCircleList() {
        return circleList;
    }

    /**
     * 报修状态
     */
    public List<String> getRepairStatus() {
        return repairStatus;
    }

    public List<String> getRepairStatusClient() {
        return repairStatusClient;
    }

    public List<String> getRepairStatusWorker() {
        return repairStatusWorker;
    }

    public List<String> getPayType() {
        return payType;
    }

    /**
     * 工作任务 已读/未读
     */
    public List<String> getTaskReadStatus() {
        return taskReadStatus;
    }

    /**
     * 工作检查 订单状态
     */
    public List<String> getCheckReadStatus() {
        return checkReadStatus;
    }

    /**
     * 预算范围 选择性
     */
    public List<String> getBudgetLimit() {
        return budgetLimit;
    }

    /**
     * 预计工期 选择性
     */
    public List<String> getPlanLimit() {
        return planLimit;
    }

    /**
     * 回复时限 选择器
     */
    public List<String> getReplyLimit() {
        return replyLimit;
    }

    /**
     * 免费设计 订单状态
     */
    public List<String> getDesignOrderStatus() {
        return designOrderStatus;
    }

    /**
     * 能力等级
     */
    public List<String> getWorkLevel() {
        return workLevel;
    }

    /**
     * 工作年限
     */
    public List<String> getWorkYear() {
        return workYear;
    }

    /**
     * 企业规模
     */
    public List<String> getCompanyScale() {
        return companyScale;
    }

    /**
     * 维保等级
     */
    public List<String> getMaintLevel() {
        return maintLevel;
    }

    /**
     * 维保结果
     */
    public List<String> getMaintResult() {
        return maintResult;
    }

    public List<String> getParameterType() {
        return parameterType;
    }

    /**
     * 维修结论
     */
    public List<String> getRepairConslusion() {
        return repairConslusion;
    }
}
