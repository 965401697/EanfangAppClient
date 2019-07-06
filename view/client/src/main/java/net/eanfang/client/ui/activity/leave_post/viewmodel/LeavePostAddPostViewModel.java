package net.eanfang.client.ui.activity.leave_post.viewmodel;

import android.app.Activity;
import android.content.Intent;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.biz.model.SelectAddressItem;
import com.eanfang.biz.model.TemplateBean;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.config.Config;
import com.eanfang.ui.activity.SelectAddressActivity;
import com.eanfang.util.StringUtils;

import net.eanfang.client.databinding.ActivityLeavePostAddPostBinding;
import net.eanfang.client.ui.activity.leave_post.LeavePostCheckListActivity;
import net.eanfang.client.ui.activity.leave_post.LeavePostMonitorActivity;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostAddPostPostBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostDeviceInfoBean;
import net.eanfang.client.ui.activity.leave_post.ds.LeavePostDs;
import net.eanfang.client.ui.activity.leave_post.repo.LeavePostRepo;


import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :添加岗位
 */
public class LeavePostAddPostViewModel extends BaseViewModel {
    @Getter
    private LeavePostRepo mLeavePostHomeRepo;
    @Getter
    private MutableLiveData<LeavePostDeviceInfoBean> leavePostDeviceDetail;
    @Getter
    private MutableLiveData<List<TemplateBean.Preson>> chargeStaffList;
    @Getter
    private MutableLiveData<List<TemplateBean.Preson>> dutyStaffList;
    private LeavePostAddPostPostBean addPostPostBean = new LeavePostAddPostPostBean();

    public LeavePostAddPostViewModel() {
        leavePostDeviceDetail = new MutableLiveData<>();
        chargeStaffList = new MutableLiveData<>();
        dutyStaffList = new MutableLiveData<>();
        mLeavePostHomeRepo = new LeavePostRepo(new LeavePostDs(this));
    }

        /**
         * 获取岗位详情
         *
         * @param stationId
         */
        public void getPostInfo(int stationId) {
            mLeavePostHomeRepo.deviceInfoData(String.valueOf(stationId)).observe(lifecycleOwner, leavePostDeviceInfoBean -> {
                leavePostDeviceDetail.setValue(leavePostDeviceInfoBean);
                ArrayList<TemplateBean.Preson> chargePersons = new ArrayList<>();
                for (LeavePostDeviceInfoBean.ChargeStaffListBean chargeStaffListBean: leavePostDeviceInfoBean.getChargeStaffList()){
                    chargePersons.add(chargeStaffListBean.getPerson());
                }
                chargeStaffList.setValue(chargePersons);

                ArrayList<TemplateBean.Preson> dutyStaffPersons = new ArrayList<>();
                for (LeavePostDeviceInfoBean.ChargeStaffListBean chargeStaffListBean: leavePostDeviceInfoBean.getDutyStaffList()){
                    dutyStaffPersons.add(chargeStaffListBean.getPerson());
                }
                dutyStaffList.setValue(dutyStaffPersons);
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

    public void addDevice(Activity activity) {
        activity.startActivity(new Intent(activity, LeavePostMonitorActivity.class));
    }

    public void addPostCommit(ActivityLeavePostAddPostBinding binding) {
        if (checkoutInfo(binding)) {
            addPostPostBean.setStationName(binding.edtLeavePostAddPostName.getText().toString());
            addPostPostBean.setStationArea(binding.edtLeavePostAddPostPosition.getText().toString());
            addPostPostBean.setStationCode(binding.edtLeavePostAddPostNumber.getText().toString());
            mLeavePostHomeRepo.addPost(addPostPostBean).observe(lifecycleOwner, jsonObject -> {
                showToast("提交成功");
            });
        }


    }

    private boolean checkoutInfo(ActivityLeavePostAddPostBinding binding) {
        if (StringUtils.isEmpty(binding.edtLeavePostAddPostName.getText().toString().trim())) {
            showToast("请填写岗位名称");
            return false;
        } else if (StringUtils.isEmpty(binding.edtLeavePostAddPostPosition.getText().toString().trim())) {
            showToast("请填写岗位位置");
            return false;
        } else if (StringUtils.isEmpty(binding.tvLeavePostAddPostArea.getText().toString().trim())) {
            showToast("请选择岗位区域");
            return false;
        } else if (StringUtils.isEmpty(binding.edtLeavePostAddPostNumber.getText().toString().trim())) {
            showToast("请填写岗位编码");
            return false;
        } else if (StringUtils.isEmpty(binding.tvLeavePostAddPostDevice.getText().toString().trim())) {
            showToast("请选择绑定设备");
            return false;
        } else if (StringUtils.isEmpty(binding.tvLeavePostAddPostTime.getText().toString().trim())) {
            showToast("请选择阀值时间");
            return false;
        }
        return true;
    }

    public void gotoChooseArea(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, SelectAddressActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    public void setAreaInfo(SelectAddressItem item) {
        addPostPostBean.setStationAddress(item.getAddress());
        addPostPostBean.setStationPlaceCode(Config.get().getAreaCodeByName(item.getCity(), item.getAddress()));
    }

    public void updatePostInfo() {

    }
}
