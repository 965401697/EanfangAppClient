package net.eanfang.client.ui.activity.leave_post.viewmodel;

import android.app.Activity;
import android.content.Intent;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.util.GetDateUtils;

import net.eanfang.client.ui.activity.leave_post.LeavePostCheckDetailActivity;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostAlertInfoDetailBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostKeyValueBean;
import net.eanfang.client.ui.activity.leave_post.ds.LeavePostDs;
import net.eanfang.client.ui.activity.leave_post.repo.LeavePostRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Getter;

/**
 * @author liangkailun
 * Date ：2019-06-28
 * Describe :报警详情
 */
public class LeavePostDetailViewModel extends BaseViewModel {
    private String[] keys = new String[]{
            "事件名称", "报警时间", "岗位名称", "位置编号", "设备名称", "离岗时间", "回岗时间", "脱岗时长"};
    private String[] values = new String[8];
    private ArrayList<LeavePostKeyValueBean> mKeyValueList;
    @Getter
    private MutableLiveData<LeavePostAlertInfoDetailBean> leavePostAlertInfoData;
    @Getter
    private MutableLiveData<List<LeavePostKeyValueBean>> leavePostAlertList;
    private LeavePostRepo mLeavePostRepo;

    public LeavePostDetailViewModel() {
        leavePostAlertInfoData = new MutableLiveData<>();
        leavePostAlertList = new MutableLiveData<>();
        mLeavePostRepo = new LeavePostRepo(new LeavePostDs(this));
        mKeyValueList = new ArrayList<>();
    }

    public void getAlertInfoData(String alertId) {
        mLeavePostRepo.leavePostAlertInfoDetail(alertId).observe(lifecycleOwner, leavePostAlertInfoDetailBean -> {
            leavePostAlertInfoData.setValue(leavePostAlertInfoDetailBean);
            values[0] = leavePostAlertInfoDetailBean.getAlertName();
            values[1] = GetDateUtils.dateToDateString(GetDateUtils.getDate(leavePostAlertInfoDetailBean.getAlertTime()));
            values[2] = leavePostAlertInfoDetailBean.getAlertName();
            values[3] = leavePostAlertInfoDetailBean.getAlertName();
            values[4] = leavePostAlertInfoDetailBean.getAlertName();
            values[5] = leavePostAlertInfoDetailBean.getLeaveTime();
            values[6] = leavePostAlertInfoDetailBean.getBackTime();
            values[7] = leavePostAlertInfoDetailBean.getAbsencePeriod() + "min";
            for (int i = 0; i < keys.length; i++) {
                LeavePostKeyValueBean bean = new LeavePostKeyValueBean();
                bean.setName(keys[i]);
                bean.setValue(values[i]);
                mKeyValueList.add(bean);
            }
            leavePostAlertList.setValue(mKeyValueList);
        });


    }

    public void gotoCallPersonPage(Activity activity) {
        Intent intent = new Intent(activity, LeavePostCheckDetailActivity.class);
        intent.putExtra("mAlertId", Objects.requireNonNull(leavePostAlertInfoData.getValue()).getAlertId());
        intent.putExtra("isShowTopContent", false);
        activity.startActivity(intent);

    }
}
