package net.eanfang.client.ui.activity.leave_post.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.biz.rds.base.BaseViewModel;

import net.eanfang.client.ui.activity.leave_post.ImagesLook;
import net.eanfang.client.ui.activity.leave_post.LeavePostCheckDetailActivity;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostAlertInfoDetailBean;
import net.eanfang.client.ui.activity.leave_post.ds.LeavePostDs;
import net.eanfang.client.ui.activity.leave_post.repo.LeavePostRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.hutool.core.date.DateUtil;
import lombok.Getter;

/**
 * @author liangkailun
 * Date ：2019-06-28
 * Describe :报警详情
 */
public class LeavePostDetailViewModel extends BaseViewModel {
    private String[] eventKeys = new String[]{
            "事件名称:\t", "岗位名称:\t", "岗位位置:\t", "位置编号:\t"};
    private String[] leaveKeys = new String[]{
            "设备名称:\t", "报警时间:\t", "离岗时间:\t", "回岗时间:\t", "脱岗时长:\t"
    };

    private ArrayList<String> mKeyValueEventList;
    private ArrayList<String> mKeyValueLeaveList;
    @Getter
    private MutableLiveData<LeavePostAlertInfoDetailBean> leavePostAlertInfoData;
    @Getter
    private MutableLiveData<List<String>> leavePostAlertEventList;
    @Getter
    private MutableLiveData<List<String>> leavePostAlertLeaveList;
    private LeavePostRepo mLeavePostRepo;

    public LeavePostDetailViewModel() {
        leavePostAlertInfoData = new MutableLiveData<>();
        leavePostAlertEventList = new MutableLiveData<>();
        leavePostAlertLeaveList = new MutableLiveData<>();
        mLeavePostRepo = new LeavePostRepo(new LeavePostDs(this));
        mKeyValueEventList = new ArrayList<>();
        mKeyValueLeaveList = new ArrayList<>();
    }

    public void getAlertInfoData(String alertId) {
        mLeavePostRepo.leavePostAlertInfoDetail(alertId).observe(lifecycleOwner, leavePostAlertInfoDetailBean -> {
            if (leavePostAlertInfoDetailBean != null) {
                leavePostAlertInfoData.setValue(leavePostAlertInfoDetailBean);
                mKeyValueEventList.add(eventKeys[0] + leavePostAlertInfoDetailBean.getAlertName());
                if (leavePostAlertInfoDetailBean.getStationsEntity() != null) {
                    mKeyValueEventList.add(eventKeys[1] + leavePostAlertInfoDetailBean.getStationsEntity().getStationName());
                    mKeyValueEventList.add(eventKeys[2] + leavePostAlertInfoDetailBean.getStationsEntity().getStationPlaceName());
                    mKeyValueEventList.add(eventKeys[3] + leavePostAlertInfoDetailBean.getStationsEntity().getStationCode());
                }
                if (leavePostAlertInfoDetailBean.getDevicesEntity() != null) {
                    mKeyValueLeaveList.add(leaveKeys[0] + leavePostAlertInfoDetailBean.getDevicesEntity().getDeviceName());
                }
                mKeyValueLeaveList.add(leaveKeys[1] + DateUtil.parse(leavePostAlertInfoDetailBean.getAlertTime()).toDateStr());
                mKeyValueLeaveList.add(leaveKeys[2] + leavePostAlertInfoDetailBean.getLeaveTime());
                mKeyValueLeaveList.add(leaveKeys[3] + leavePostAlertInfoDetailBean.getBackTime());
                mKeyValueLeaveList.add(leaveKeys[4] + leavePostAlertInfoDetailBean.getAbsencePeriod() + "min");
            }
            leavePostAlertEventList.setValue(mKeyValueEventList);
            leavePostAlertLeaveList.setValue(mKeyValueLeaveList);
        });
    }

    public void gotoCallPersonPage(Activity activity) {
        Intent intent = new Intent(activity, LeavePostCheckDetailActivity.class);
        intent.putExtra("mAlertId", Objects.requireNonNull(leavePostAlertInfoData.getValue()).getAlertId());
        intent.putExtra("isShowTopContent", false);
        activity.startActivity(intent);
    }

    public void gotoAudioPlay(Activity activity) {
        Intent intent = new Intent(activity, LeavePostCheckDetailActivity.class);
        intent.putExtra("isShowTopContent", true);
        if (leavePostAlertInfoData.getValue() != null) {
            intent.putExtra("stationId", leavePostAlertInfoData.getValue().getStationId());
        }
        activity.startActivity(intent);
    }

    /**
     * 查看图片页面
     * @param activity
     * @param adapter
     * @param view
     * @param position
     */
    public void lookImage(Activity activity, BaseQuickAdapter adapter, View view, int position) {
        ImagesLook.getInstance().show(activity, view, (ArrayList<String>) adapter.getData(), position);

    }
}
