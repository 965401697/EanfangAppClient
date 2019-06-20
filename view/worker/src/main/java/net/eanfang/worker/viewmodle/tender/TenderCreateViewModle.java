package net.eanfang.worker.viewmodle.tender;

import android.app.Activity;
import android.widget.EditText;

import androidx.lifecycle.MutableLiveData;

import com.annimon.stream.Stream;
import com.eanfang.base.BaseApplication;
import com.eanfang.biz.model.bean.PageBean;
import com.eanfang.biz.model.entity.IfbOrderEntity;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;
import com.eanfang.biz.model.vo.tender.TenderCreateVo;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.tender.TenderDs;
import com.eanfang.biz.rds.sys.repo.tender.TenderRepo;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.ui.base.voice.RecognitionManager;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.StringUtils;

import net.eanfang.worker.databinding.ActivityTenderCreateBinding;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/6/13
 * @description 创建用工
 */
public class TenderCreateViewModle extends BaseViewModel {

    /**
     * 经度
     */
    public String lon;
    /**
     * 纬度
     */
    public String lat;
    /**
     * 省份
     */
    public String province;
    /**
     * 城市
     */
    public String city;
    /**
     * 区县
     */
    public String contry;

    /**
     * 详细地址
     */
    public String detailPlace;

    private TenderRepo tenderRepo;

    @Setter
    @Getter
    private ActivityTenderCreateBinding mTenderCreateBinding;

    private TenderCreateVo tenderCreateVo;

    @Getter
    private MutableLiveData<TaskPublishEntity> createTenderLiveData;

    public TenderCreateViewModle() {
        createTenderLiveData = new MutableLiveData<>();
        tenderRepo = new TenderRepo(new TenderDs(this));
        tenderCreateVo = new TenderCreateVo();
    }

    /**
     * 业务类别
     */
    public void doSelcetBusiness() {
        PickerSelectUtil.singleTextPicker((Activity) mTenderCreateBinding.getRoot().getContext(), "", mTenderCreateBinding.tvBusinessType,
                Stream.of(Config.get().getServiceList(1)).map(bus -> bus.getDataName()).toList());
    }

    /**
     * 系统类别
     */
    public void doSelctSystem() {
        PickerSelectUtil.singleTextPicker((Activity) mTenderCreateBinding.getRoot().getContext(), "", mTenderCreateBinding.tvSystemType,
                Stream.of(Config.get().getBusinessList(1)).map(bus -> bus.getDataName()).toList());
    }

    /**
     * 预算单位
     */
    public void doSelectBudgetUnit() {
        PickerSelectUtil.singleTextPicker((Activity) mTenderCreateBinding.getRoot().getContext(), "", mTenderCreateBinding.tvBudgetUnit,
                Stream.of(GetConstDataUtils.getTenderBudgetUnit()).map(bus -> bus).toList());

    }

    /**
     * 环境描述
     */
    public void doInputDescription() {
        doInput(mTenderCreateBinding.etEnvironment);
    }

    /**
     * 用工要求
     */
    public void doInputRequirements() {
        doInput(mTenderCreateBinding.etRequire);
    }

    private void doInput(EditText editText) {
        RecognitionManager.getSingleton().startRecognitionWithDialog(mTenderCreateBinding.getRoot().getContext(), editText);
    }

    /**
     * 完善资料
     */
    public boolean doCheckInfo() {
        if (StringUtils.isEmpty(mTenderCreateBinding.tvProjectAddress.getText())) {
            showToast("请选择地址");
            return false;
        }

        if (StringUtils.isEmpty(mTenderCreateBinding.tvBusinessType.getText())) {
            showToast("请选择业务类型");
            return false;
        }
        if (StringUtils.isEmpty(mTenderCreateBinding.tvSystemType.getText())) {
            showToast("请选择系统类别");
            return false;
        }
        if (StringUtils.isEmpty(mTenderCreateBinding.tvStartTime.getText())) {
            showToast("请选择开始时间");
            return false;
        }
        if (StringUtils.isEmpty(mTenderCreateBinding.etPredictTime.getText())) {
            showToast("请填写预期工期");
            return false;
        }
        if (StringUtils.isEmpty(mTenderCreateBinding.tvBudget.getText())) {
            showToast("请填写预期预算");
            return false;
        }
        doCommitTender();
        return true;
    }

    /**
     * 发布用工
     */
    private void doCommitTender() {
        String zondCode = Config.get().getAreaCodeByName(city, contry);
        int zoneId = Config.get().getBaseIdByCode(zondCode, 3, Constant.AREA);
        String bussinessOneCode = Config.get().getBaseCodeByName(mTenderCreateBinding.tvBusinessType.getText().toString(), 1, Constant.BIZ_TYPE).get(0);
        String systemType = Config.get().getBusinessCodeByName(mTenderCreateBinding.tvSystemType.getText().toString(), 1);
        tenderCreateVo.getContacts().set("管罗苍  ");
        tenderCreateVo.getContactsPhone().set("17600738557");
        tenderCreateVo.getPublishCompanyName().set(BaseApplication.get().getUser().getCompanyEntity().getOrgName());
        tenderCreateVo.getLatitude().set(lat);
        tenderCreateVo.getLongitude().set(lon);
        tenderCreateVo.getCity().set(city);
        tenderCreateVo.getProvince().set(province);
        tenderCreateVo.getCounty().set(contry);
        tenderCreateVo.getZoneCode().set(zondCode);
        tenderCreateVo.getZoneId().set(zoneId);
        tenderCreateVo.getBusinessOneCode().set(bussinessOneCode);
        tenderCreateVo.getSystemType().set(systemType);
//        tenderCreateVo.province+tenderCreateVo.city+tenderCreateVo.city+tenderCreateVo.detailPlace
        tenderCreateVo.getDetailPlace().set(detailPlace);
        tenderCreateVo.getPredicttime().set(mTenderCreateBinding.etPredictTime.getText().toString().trim());
        tenderCreateVo.getEndTime().set(mTenderCreateBinding.tvStartTime.getText().toString().trim());
        tenderCreateVo.getBudget().set(mTenderCreateBinding.tvBudget.getText().toString());
        tenderCreateVo.getBudgetUnit().set(mTenderCreateBinding.tvBudgetUnit.getText().toString());
        tenderCreateVo.getLaborRequirements().set(mTenderCreateBinding.etEnvironment.getText().toString().trim());
        tenderCreateVo.getDescription().set(mTenderCreateBinding.etRequire.getText().toString().trim());

        tenderRepo.doSetNewTender(tenderCreateVo).observe(lifecycleOwner, tenderBean -> {
            createTenderLiveData.setValue(tenderBean);
        });
    }
}
