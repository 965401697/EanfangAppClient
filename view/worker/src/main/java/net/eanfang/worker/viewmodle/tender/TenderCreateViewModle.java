package net.eanfang.worker.viewmodle.tender;

import android.app.Activity;
import android.widget.EditText;

import androidx.lifecycle.MutableLiveData;

import com.annimon.stream.Stream;
import com.eanfang.base.BaseApplication;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.kit.picture.picture.PictureRecycleView;
import com.eanfang.biz.model.entity.tender.TaskPublishEntity;
import com.eanfang.biz.model.vo.tender.TenderCreateVo;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.tender.TenderDs;
import com.eanfang.biz.rds.sys.repo.TenderRepo;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.ui.base.voice.RecognitionManager;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.PickerSelectUtil;
import com.luck.picture.lib.entity.LocalMedia;

import net.eanfang.worker.databinding.ActivityTenderCreateBinding;
import net.eanfang.worker.ui.activity.worksapce.tender.TenderCreateActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;

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

    @Getter
    private ActivityTenderCreateBinding mTenderCreateBinding;

    public void setTenderCreateBinding(ActivityTenderCreateBinding tenderCreateBinding) {
        this.mTenderCreateBinding = tenderCreateBinding;
        tenderCreateBinding.setTenderCreateVo(tenderCreateVo);
    }

    private TenderCreateVo tenderCreateVo;

    @Getter
    private MutableLiveData<TaskPublishEntity> createTenderLiveData;

    public Map<String, String> uploadMap = new HashMap<>();
    /**
     * 选择图片
     */
    private List<LocalMedia> mPicList = new ArrayList<>();
    public PictureRecycleView.ImageListener listener = list -> mPicList = list;

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
        if (StrUtil.isEmpty(mTenderCreateBinding.tvProjectAddress.getText())) {
            showToast("请选择地址");
            return false;
        }

        if (StrUtil.isEmpty(mTenderCreateBinding.tvBusinessType.getText())) {
            showToast("请选择业务类型");
            return false;
        }
        if (StrUtil.isEmpty(mTenderCreateBinding.tvSystemType.getText())) {
            showToast("请选择系统类别");
            return false;
        }
        if (StrUtil.isEmpty(mTenderCreateBinding.tvStartTime.getText())) {
            showToast("请选择开始时间");
            return false;
        }
        if (StrUtil.isEmpty(mTenderCreateBinding.etPredictTime.getText())) {
            showToast("请填写预期工期");
            return false;
        }
        if (StrUtil.isEmpty(mTenderCreateBinding.tvBudget.getText())) {
            showToast("请填写预期预算");
            return false;
        }
        if (StrUtil.isEmpty(mTenderCreateBinding.etEnvironment.getText())) {
            showToast("请填写环境描述");
            return false;
        }
        if (StrUtil.isEmpty(mTenderCreateBinding.etRequire.getText())) {
            showToast("请填写用工要求");
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
        tenderCreateVo.getContacts().set(BaseApplication.get().getAccount().getRealName());
        tenderCreateVo.getContactsPhone().set(BaseApplication.get().getAccount().getMobile());
        tenderCreateVo.getPublishCompanyName().set(BaseApplication.get().getCompanyEntity().getOrgName());
        tenderCreateVo.getLatitude().set(lat);
        tenderCreateVo.getLongitude().set(lon);
        tenderCreateVo.getCity().set(city);
        tenderCreateVo.getProvince().set(province);
        tenderCreateVo.getCounty().set(contry);
        tenderCreateVo.getZoneCode().set(zondCode);
        tenderCreateVo.getZoneId().set(zoneId);
        tenderCreateVo.getBusinessOneCode().set(bussinessOneCode);
        tenderCreateVo.getSystemType().set(systemType);
        tenderCreateVo.getPredicttime().set(mTenderCreateBinding.etPredictTime.getText().toString().trim());
        tenderCreateVo.getDetailPlace().set(detailPlace);
        tenderCreateVo.getEndTime().set(mTenderCreateBinding.tvStartTime.getText().toString().trim());
        tenderCreateVo.getBudget().set(mTenderCreateBinding.tvBudget.getText().toString());
        tenderCreateVo.getBudgetUnit().set(mTenderCreateBinding.tvBudgetUnit.getText().toString());
        tenderCreateVo.getDescription().set(mTenderCreateBinding.etEnvironment.getText().toString().trim());
        tenderCreateVo.getLaborRequirements().set(mTenderCreateBinding.etRequire.getText().toString().trim());
        String mImages = PhotoUtils.getPhotoUrl("biz/tender/", mPicList, uploadMap, true);
        tenderCreateVo.getPictures().set(mImages);
        if (uploadMap.size() != 0) {
            SDKManager.ossKit((TenderCreateActivity) mTenderCreateBinding.getRoot().getContext()).asyncPutImages(uploadMap, (isSuccess) -> {
                tenderRepo.doSetNewTender(tenderCreateVo).observe(lifecycleOwner, tenderBean -> {
                    createTenderLiveData.setValue(tenderBean);
                });
            });
        } else if (tenderCreateVo.getPictures() != null) {
            tenderRepo.doSetNewTender(tenderCreateVo).observe(lifecycleOwner, tenderBean -> {
                createTenderLiveData.setValue(tenderBean);
            });

        }

    }

    /**
     * 再次发布
     */
    public void doReleaseAgain(TaskPublishEntity taskPublishEntity) {
        province = taskPublishEntity.getProvince();
        city = taskPublishEntity.getCity();
        contry = taskPublishEntity.getCounty();
        lat = taskPublishEntity.getLatitude();
        lon = taskPublishEntity.getLongitude();
        mTenderCreateBinding.tvProjectAddress.setText(taskPublishEntity.getProvince() + city + contry);
        mTenderCreateBinding.tvBusinessType.setText(Config.get().getBaseNameByCode(taskPublishEntity.getBusinessOneCode(), 2));
        mTenderCreateBinding.tvSystemType.setText(Config.get().getBaseNameByCode(taskPublishEntity.getSystemType(), 1));
        mTenderCreateBinding.tvStartTime.setText(DateUtil.date(taskPublishEntity.getEndTime()).toString());
        mTenderCreateBinding.etPredictTime.setText(taskPublishEntity.getPredicttime() + "");
        mTenderCreateBinding.tvBudget.setText(taskPublishEntity.getBudget() + "");
        mTenderCreateBinding.tvBudgetUnit.setText(taskPublishEntity.getBudgetUnit());
        mTenderCreateBinding.etEnvironment.setText(taskPublishEntity.getDescription());
        mTenderCreateBinding.etRequire.setText(taskPublishEntity.getLaborRequirements());

        mPicList = mTenderCreateBinding.rvSelectPic.setData(taskPublishEntity.getPictures());
        mTenderCreateBinding.rvSelectPic.showImagev(mPicList, listener);
        mTenderCreateBinding.rvSelectPic.isShow(true, mPicList);
    }
}
