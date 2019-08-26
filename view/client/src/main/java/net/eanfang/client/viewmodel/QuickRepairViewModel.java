package net.eanfang.client.viewmodel;

import android.app.Activity;
import android.widget.EditText;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.base.BaseApplication;
import com.eanfang.base.kit.cache.CacheKit;
import com.eanfang.base.kit.rx.RxPerm;
import com.eanfang.biz.model.bean.DesignOrderInfoBean;
import com.eanfang.biz.model.bean.InstallOrderConfirmBean;
import com.eanfang.biz.model.bean.OrderCountBean;
import com.eanfang.biz.model.bean.SelectAddressItem;
import com.eanfang.biz.model.entity.RepairBugEntity;
import com.eanfang.biz.model.entity.RepairOrderEntity;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.QuickRepairDs;
import com.eanfang.biz.rds.sys.repo.QuickRepairRepo;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.ui.base.voice.RecognitionManager;

import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.databinding.FragmentHomeRepairBinding;

import org.json.JSONObject;

import java.util.ArrayList;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @author liangkailun
 * Date ：2019-07-24
 * Describe :快速报修viewmodel
 */
public class QuickRepairViewModel extends BaseViewModel {
    @Setter
    private FragmentHomeRepairBinding mBinding;
    @Getter
    private MutableLiveData<OrderCountBean> mOrderNum;
    @Getter
    private MutableLiveData<JSONObject> mCreateRepair;
    private QuickRepairRepo mQuickRepairRepo;
    /**
     * 报修上报bean
     */
    private RepairOrderEntity mRepairOrderEntity;
    private RepairBugEntity mRepairBugEntity;
    /**
     * 报装上报bean
     */
    private InstallOrderConfirmBean mInstallOrderConfirmBean;
    /**
     * 设计上报bean
     */
    private DesignOrderInfoBean mDesignOrderInfoBean;


    public QuickRepairViewModel() {
        mOrderNum = new MutableLiveData<>();
        mCreateRepair = new MutableLiveData<>();
        mRepairOrderEntity = new RepairOrderEntity();
        mInstallOrderConfirmBean = new InstallOrderConfirmBean();
        mDesignOrderInfoBean = new DesignOrderInfoBean();
        mRepairBugEntity = new RepairBugEntity();
        ArrayList<RepairBugEntity> repairBugEntities = new ArrayList<>();
        repairBugEntities.add(mRepairBugEntity);
        mRepairOrderEntity.setBugEntityList(repairBugEntities);
        mQuickRepairRepo = new QuickRepairRepo(new QuickRepairDs(this));
    }

    /**
     * 获取报修人数
     */
    public void getRepairCount() {
        OrderCountBean orderCount = CacheKit.get().get(OrderCountBean.class.getName(), OrderCountBean.class);
        if (orderCount == null) {
            mQuickRepairRepo.getRepairCount().observe(lifecycleOwner, (bean) -> {
                CacheKit.get().put(OrderCountBean.class.getName(), bean, 1000 * 60 * 60);
                mOrderNum.setValue(bean);
            });
        } else {
            mOrderNum.setValue(orderCount);
        }
    }

    /**
     * 报修订单
     *
     * @param repairOrderEntity
     */
    private void createRepairOrder(RepairOrderEntity repairOrderEntity) {
        mQuickRepairRepo.createRepairOrder(repairOrderEntity).observe(lifecycleOwner, mCreateRepair::setValue);
    }

    /**
     * 快速报装
     *
     * @param installOrderConfirmBean
     */
    private void createInstallOrder(InstallOrderConfirmBean installOrderConfirmBean) {
        mQuickRepairRepo.createInstallOrder(installOrderConfirmBean).observe(lifecycleOwner, mCreateRepair::setValue);
    }

    /**
     * 快速设计
     *
     * @param designOrderInfoBean
     */
    private void createFreeDesign(DesignOrderInfoBean designOrderInfoBean) {
        mQuickRepairRepo.createFreeDesign(designOrderInfoBean).observe(lifecycleOwner, mCreateRepair::setValue);
    }

    /**
     * 提交上报
     *
     * @param status
     */
    public void toCommit(int status) {
        if (status == 0) {
            mRepairOrderEntity.setRepairWay(0);
            mRepairOrderEntity.setOwnerCompanyId(BaseApplication.get().getCompanyId() != 0 ? BaseApplication.get().getCompanyId() : 0);
            mRepairOrderEntity.setOwnerTopCompanyId(BaseApplication.get().getTopCompanyId() != 0 ? BaseApplication.get().getTopCompanyId() : 0);
            mRepairOrderEntity.setOwnerOrgCode(BaseApplication.get().getOrgCode() != null ? BaseApplication.get().getOrgCode() : 0 + "");
            mRepairOrderEntity.setRepairContacts(ClientApplication.get().getAccount().getRealName());
            mRepairBugEntity.setBugDescription(mBinding.etHomeRepairDescribe.getText().toString());
            mRepairOrderEntity.setRepairContactPhone(ClientApplication.get().getAccount().getMobile());
            if (StrUtil.isEmpty(mRepairBugEntity.getBusinessThreeCode())) {
                showToast("请选择系统类别");
                return;
            }
            if (StrUtil.isEmpty(mRepairOrderEntity.getAddress())) {
                showToast("请选择位置");
                return;
            }
            if (StrUtil.isEmpty(mRepairBugEntity.getModelCode())) {
                showToast("请选择品牌");
                return;
            }
            createRepairOrder(mRepairOrderEntity);
        } else if (status == 1) {
            mInstallOrderConfirmBean.setClientCompanyName(BaseApplication.get().getAccount().getDefaultUser().getCompanyEntity().getOrgName());
            mInstallOrderConfirmBean.setDescription(mBinding.etHomeRepairDescribe.getText().toString());
            mInstallOrderConfirmBean.setConnectorPhone(ClientApplication.get().getAccount().getMobile());
            mInstallOrderConfirmBean.setConnector(ClientApplication.get().getAccount().getRealName());
            if (StrUtil.isEmpty(mInstallOrderConfirmBean.getBusinessOneCode())) {
                showToast("请选择系统类别");
                return;
            }
            if (StrUtil.isEmpty(mInstallOrderConfirmBean.getDetailPlace())) {
                showToast("请选择位置");
                return;
            }
            createInstallOrder(mInstallOrderConfirmBean);
        } else {
            mDesignOrderInfoBean.setCreateCompanyId(BaseApplication.get().getCompanyId() != 0 ? BaseApplication.get().getCompanyId() : 0);
            mDesignOrderInfoBean.setCreateTopCompanyId(BaseApplication.get().getTopCompanyId() != 0 ? BaseApplication.get().getTopCompanyId() : 0);
            mDesignOrderInfoBean.setCreateOrgCode(BaseApplication.get().getOrgCode() != null ? BaseApplication.get().getOrgCode() : 0 + "");
            mDesignOrderInfoBean.setContactPhone(ClientApplication.get().getAccount().getMobile());
            mDesignOrderInfoBean.setContactUser(ClientApplication.get().getAccount().getRealName());
            if (StrUtil.isEmpty(mDesignOrderInfoBean.getBusinessOneCode())) {
                showToast("请选择系统类别");
                return;
            }
            if (StrUtil.isEmpty(mDesignOrderInfoBean.getDetailPlace())) {
                showToast("请选择位置");
                return;
            }
            createFreeDesign(mDesignOrderInfoBean);
        }
    }

    /**
     * 设置地址数据
     *
     * @param item
     */
    public void setAddressData(SelectAddressItem item, int status) {
        if (status == 0) {
            mRepairOrderEntity.setLatitude(String.valueOf(item.getLatitude()));
            mRepairOrderEntity.setAddress(item.getName());
            mRepairOrderEntity.setPlaceCode(Config.get().getAreaCodeByName(item.getCity(), item.getAddress()));
        } else if (status == 1) {
            mInstallOrderConfirmBean.setZone(Config.get().getAreaCodeByName(item.getCity(), item.getAddress()));
            mInstallOrderConfirmBean.setDetailPlace(item.getName());
            mInstallOrderConfirmBean.setLongitude(String.valueOf(item.getLongitude()));
            mInstallOrderConfirmBean.setLatitude(String.valueOf(item.getLatitude()));
        } else {
            mDesignOrderInfoBean.setLatitude(String.valueOf(item.getLatitude()));
            mDesignOrderInfoBean.setLongitude(String.valueOf(item.getLongitude()));
            mDesignOrderInfoBean.setZoneCode(Config.get().getAreaCodeByName(item.getCity(), item.getAddress()));
            mDesignOrderInfoBean.setDetailPlace(item.getName());
        }
    }

    public void setRepairId(RepairOrderEntity repairOrderEntity) {
        // 技师id
        mRepairOrderEntity.setAssigneeUserId(repairOrderEntity.getAssigneeUserId());
        // 安防公司 id
        mRepairOrderEntity.setAssigneeCompanyId(repairOrderEntity.getAssigneeCompanyId());
        mRepairOrderEntity.setAssigneeTopCompanyId(repairOrderEntity.getAssigneeTopCompanyId());
        mRepairOrderEntity.setAssigneeOrgCode(repairOrderEntity.getAssigneeOrgCode());
    }

    /**
     * 设置系统数据
     *
     * @param businessOneCode
     */
    public void setSysData(String businessOneCode) {
        mRepairBugEntity.setModelCode(null);
        mRepairBugEntity.setBusinessThreeCode(businessOneCode + ".1.1");
        mInstallOrderConfirmBean.setBusinessOneCode(businessOneCode);
        mDesignOrderInfoBean.setBusinessOneCode(businessOneCode);
    }

    /**
     * 设置品牌数据
     *
     * @param brandName
     */
    public void setBrandData(String brandName) {
        mRepairBugEntity.setModelCode(Config.get().getBaseCodeByName(brandName, 2, Constant.MODEL).get(0));
    }

    /**
     * 设置图片地址
     *
     * @param objectKey
     */
    public void setPicUrl(String objectKey) {
        mRepairBugEntity.setPictures(objectKey);
    }

    /**
     * 语音输入
     *
     * @param activity
     * @param editText
     */
    public void inputVoice(Activity activity, EditText editText) {
        RxPerm.get(activity).voicePerm((isSuccess) -> {
            RecognitionManager.getSingleton().startRecognitionWithDialog(activity, editText);
        });
    }
}
