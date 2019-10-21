package net.eanfang.client.ui.activity.leave_post.viewmodel;

import android.app.Activity;
import android.content.Intent;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.base.BaseApplication;
import com.eanfang.biz.model.bean.SelectAddressItem;
import com.eanfang.biz.model.bean.TemplateBean;
import com.eanfang.biz.model.entity.AccountEntity;
import com.eanfang.biz.model.entity.Ys7DevicesEntity;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.config.Config;
import com.eanfang.ui.activity.SelectAddressActivity;

import net.eanfang.client.databinding.ActivityLeavePostAddPostBinding;
import net.eanfang.client.ui.activity.leave_post.LeavePostCheckListActivity;
import net.eanfang.client.ui.activity.leave_post.LeavePostMonitorActivity;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostAddPostPostBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostDeviceInfoBean;
import net.eanfang.client.ui.activity.leave_post.ds.LeavePostDs;
import net.eanfang.client.ui.activity.leave_post.repo.LeavePostRepo;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :添加岗位
 */
public class LeavePostAddPostViewModel extends BaseViewModel {
    private LeavePostRepo mLeavePostHomeRepo;
    @Getter
    private MutableLiveData<List<String>> leavePostInfo;
    @Getter
    private MutableLiveData<List<TemplateBean.Preson>> chargeStaffList;
    @Getter
    private MutableLiveData<List<TemplateBean.Preson>> dutyStaffList;
    @Getter
    private MutableLiveData<LeavePostDeviceInfoBean> deviceInfo;

    private List<LeavePostAddPostPostBean.ChargeStaffListBean> mChargeStaffListBeans;
    private List<LeavePostAddPostPostBean.ChargeStaffListBean> mDutyStaffListBeans;

    private LeavePostAddPostPostBean addPostPostBean = new LeavePostAddPostPostBean();
    private String[] infos = new String[]{
            "岗位名称:\t", "岗位位置:\t", "岗位编号:\t", "所在地区:\t", "绑定设备:\t", "报警阀值:\t", "岗位状态:\t",
    };
    private List<String> mListInfo;

    public LeavePostAddPostViewModel() {
        chargeStaffList = new MutableLiveData<>();
        dutyStaffList = new MutableLiveData<>();
        leavePostInfo = new MutableLiveData<>();
        deviceInfo = new MutableLiveData<>();
        mLeavePostHomeRepo = new LeavePostRepo(new LeavePostDs(this));
        mListInfo = new ArrayList<>();
        mChargeStaffListBeans = new ArrayList<>();
        mDutyStaffListBeans = new ArrayList<>();
    }

    /**
     * 获取岗位详情
     *
     * @param stationId
     */
    public void getPostInfo(long stationId) {
        mLeavePostHomeRepo.deviceInfoData(String.valueOf(stationId)).observe(lifecycleOwner, leavePostDeviceInfoBean -> {
            if (leavePostDeviceInfoBean == null) {
                return;
            }
            deviceInfo.setValue(leavePostDeviceInfoBean);
            ArrayList<TemplateBean.Preson> chargePersons = new ArrayList<>();
            for (LeavePostDeviceInfoBean.ChargeStaffListBean chargeStaffListBean : leavePostDeviceInfoBean.getChargeStaffList()) {
                chargePersons.add(chargeStaffListBean.getPerson());
            }
            chargeStaffList.setValue(chargePersons);
            ArrayList<TemplateBean.Preson> dutyStaffPersons = new ArrayList<>();
            for (LeavePostDeviceInfoBean.ChargeStaffListBean chargeStaffListBean : leavePostDeviceInfoBean.getDutyStaffList()) {
                dutyStaffPersons.add(chargeStaffListBean.getPerson());
            }
            dutyStaffList.setValue(dutyStaffPersons);
            setChargeStaff(chargePersons, 0);
            setChargeStaff(dutyStaffPersons, 1);
            mListInfo.add(infos[0] + leavePostDeviceInfoBean.getStationName());
            mListInfo.add(infos[1] + leavePostDeviceInfoBean.getStationArea());
            mListInfo.add(infos[2] + leavePostDeviceInfoBean.getStationCode());
            mListInfo.add(infos[3] + Config.get().getAddressByCode(leavePostDeviceInfoBean.getStationPlaceCode()));
            mListInfo.add(infos[4] + (StrUtil.isEmpty(leavePostDeviceInfoBean.getDeviceName()) ? "" : leavePostDeviceInfoBean.getDeviceName()));
            mListInfo.add(infos[5] + leavePostDeviceInfoBean.getIntervalLength() + "分钟");
            mListInfo.add(infos[6] + (leavePostDeviceInfoBean.getStatus() == 0 ? "未开启" : "已启用"));
            leavePostInfo.setValue(mListInfo);
        });
    }

    /**
     * 跳转图像查岗页面
     *
     * @param activity
     */
    public void gotoCheckListPage(Activity activity) {
        activity.startActivity(new Intent(activity, LeavePostCheckListActivity.class));
    }

    public void addDevice(Activity activity, int requestCode) {
        activity.startActivityForResult(new Intent(activity, LeavePostMonitorActivity.class), requestCode);
    }

    /**
     * 添加岗位上传
     *
     * @param binding
     */
    public void addPostCommit(ActivityLeavePostAddPostBinding binding, int type, Long stationId, Long configId) {
        if (checkoutInfo(binding)) {
            if (stationId != null) {
                addPostPostBean.setStationId(stationId);
            }
            if (configId != null) {
                addPostPostBean.setConfigId(configId);
            }
            addPostPostBean.setCompanyId(BaseApplication.get().getCompanyId());
            addPostPostBean.setStationName(binding.edtLeavePostAddPostName.getText().toString());
            addPostPostBean.setStationArea(binding.edtLeavePostAddPostPosition.getText().toString());
            addPostPostBean.setStationCode(binding.edtLeavePostAddPostNumber.getText().toString());
            addPostPostBean.setStatus(binding.switchBtnLeavePostAddPostStatus.isChecked() ? "1" : "0");
            addPostPostBean.setIntervalLength(Integer.parseInt(binding.tvLeavePostAddPostTime.getText().toString().replace("分钟", "")));
            addPostPostBean.setChargeUserList(mChargeStaffListBeans);
            addPostPostBean.setDutyUserList(mDutyStaffListBeans);
            addPostPostBean.setNowUser(new LeavePostAddPostPostBean.NowUserBean().setUserId(BaseApplication.get().getUserId()).setCompanyId(BaseApplication.get().getCompanyId()));
            if (type == 0) {
                mLeavePostHomeRepo.addPost(addPostPostBean).observe(lifecycleOwner, jsonObject -> {
                    showToast("提交成功");
                    finish();
                });
            } else {
                mLeavePostHomeRepo.updatePost(addPostPostBean).observe(lifecycleOwner, jsonObject -> {
                    showToast("保存成功");
                    finish();
                });
            }
        }
    }

    private boolean checkoutInfo(ActivityLeavePostAddPostBinding binding) {
        if (StrUtil.isEmpty(binding.edtLeavePostAddPostName.getText().toString().trim())) {
            showToast("请填写岗位名称！");
            return false;
        } else if (StrUtil.isEmpty(binding.edtLeavePostAddPostPosition.getText().toString().trim())) {
            showToast("请填写岗位位置！");
            return false;
        } else if (StrUtil.isEmpty(binding.tvLeavePostAddPostArea.getText().toString().trim())) {
            showToast("请选择岗位区域！");
            return false;
        } else if (StrUtil.isEmpty(binding.edtLeavePostAddPostNumber.getText().toString().trim())) {
            showToast("请填写岗位编码！");
            return false;
        } else if (StrUtil.isEmpty(binding.tvLeavePostAddPostTime.getText().toString().trim())) {
            showToast("请选择阀值时间！");
            return false;
        } else if (mChargeStaffListBeans.size() == 0 || mDutyStaffListBeans.size() == 0) {
            showToast("请选择值班人和负责人！");
            return false;
        }
        return true;
    }

    public void gotoChooseArea(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, SelectAddressActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    public void setAreaInfo(SelectAddressItem item) {
        addPostPostBean.setStationAddress(item.getName());
        addPostPostBean.setStationPlaceCode(Config.get().getAreaCodeByName(item.getCity(), item.getAddress()));
    }

    /**
     * 设置设备返回结果
     */
    public void setDeviceResult(Ys7DevicesEntity deviceEntityBean) {
        addPostPostBean.setDeviceEntity(deviceEntityBean);
    }

    /**
     * 上传负责人
     *
     * @param whoList
     */
    public void setChargeStaff(List<TemplateBean.Preson> whoList, int type) {
        for (TemplateBean.Preson preson : whoList) {
            LeavePostAddPostPostBean.ChargeStaffListBean bean = new LeavePostAddPostPostBean.ChargeStaffListBean();
            AccountEntity accountEntity = new AccountEntity();
            if (preson.getId() != null) {
                accountEntity.setAccId(Long.valueOf(preson.getId()));
            }
            accountEntity.setRealName(preson.getName());
            accountEntity.setMobile(preson.getMobile());
            accountEntity.setAvatar(preson.getProtraivat());
            bean.setUserId(preson.getUserId());
            bean.setAccountEntity(accountEntity);
            if (type == 0) {
                mChargeStaffListBeans.add(bean);
            } else {
                mDutyStaffListBeans.add(bean);
            }
        }
    }

}
