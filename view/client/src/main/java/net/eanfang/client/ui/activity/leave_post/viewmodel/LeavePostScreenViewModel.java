package net.eanfang.client.ui.activity.leave_post.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.widget.CheckBox;

import androidx.lifecycle.MutableLiveData;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.base.kit.V;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.util.StringUtils;

import net.eanfang.client.databinding.ActivityLeavePostScreenBinding;
import net.eanfang.client.ui.activity.leave_post.LeavePostScreenChooseListActivity;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostChooseAreaBean;
import net.eanfang.client.ui.activity.leave_post.ds.LeavePostDs;
import net.eanfang.client.ui.activity.leave_post.repo.LeavePostRepo;

import java.util.Arrays;
import java.util.Objects;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * @author liangkailun
 * Date ：2019-06-28
 * Describe :筛选
 */
public class LeavePostScreenViewModel extends BaseViewModel {
    private ActivityLeavePostScreenBinding mBinding;
    private CheckBox[] mChooseStatusView;

    @Getter
    private MutableLiveData<LeavePostChooseAreaBean> chooseData;
    private LeavePostRepo mLeavePostHomeRepo;
    private int mChooseType;
    private int mPosition;
    private int mStatus = -1;

    public LeavePostScreenViewModel() {
        chooseData = new MutableLiveData<>();
        mLeavePostHomeRepo = new LeavePostRepo(new LeavePostDs(this));
    }

    /**
     * 跳转筛选页
     *
     * @param activity
     */
    public void setScreenResult(Activity activity) {
        Intent intent = new Intent();
        if (chooseData.getValue() != null && chooseData.getValue().getList() != null && chooseData.getValue().getList().get(mPosition) != null) {
            intent.putExtra("stationId", chooseData.getValue().getList().get(mPosition).getStationId());
        }
        intent.putExtra("placeName", mBinding.tvLeavePostScreenArea.getText());
        intent.putExtra("status", mStatus);
        activity.setResult(Activity.RESULT_OK, intent);
        finish();
    }

    /**
     * 获取所在地区筛选列表
     */
    public void getAreaList(Long companyId) {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("companyId", String.valueOf(companyId));
        queryEntry.setPage(1);
        queryEntry.setPage(100);
        mLeavePostHomeRepo.chooseAreaData(queryEntry).observe(lifecycleOwner, chooseData::setValue);
    }

    /**
     * 获取所在地区筛选列表
     */
    public void getStationList(String placeName) {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("stationPlaceName", placeName);
        queryEntry.setPage(1);
        queryEntry.setPage(100);
        mLeavePostHomeRepo.choosePostData(queryEntry).observe(lifecycleOwner, chooseData::setValue);
    }

    /**
     * 0:选择区域, 1:选择岗位
     *
     * @param activity
     */
    public void gotoChoosePage(Activity activity, int chooseType) {
        mChooseType = chooseType;
        if (chooseType == 1 && StrUtil.isEmpty(mBinding.tvLeavePostScreenArea.getText())) {
            showToast("请先选择地区");
            return;
        }
        Intent intent = new Intent(activity, LeavePostScreenChooseListActivity.class);
        intent.putExtra("chooseType", chooseType);
        intent.putExtra("placeName", mBinding.tvLeavePostScreenArea.getText());
        activity.startActivityForResult(intent, chooseType);
    }

    /**
     * 选择岗位状态
     *
     * @param i
     */
    public void chooseStatus(int i) {
        mStatus = i - 1;
        for (CheckBox textView : mChooseStatusView) {
            textView.setChecked(i == Arrays.asList(mChooseStatusView).indexOf(textView));
        }
    }

    public void setBinding(ActivityLeavePostScreenBinding binding) {
        this.mBinding = binding;
        mChooseStatusView = new CheckBox[]{
                mBinding.leavePostScreenStatusAll, mBinding.leavePostScreenStatusProcessed, mBinding.leavePostScreenStatusUntreated};
    }

    /**
     * 设置选择回调
     *
     * @param activity
     * @param adapter
     * @param position
     */
    public void setChooseResult(Activity activity, BaseQuickAdapter adapter, int position) {
        LeavePostChooseAreaBean.ListBean bean = (LeavePostChooseAreaBean.ListBean) adapter.getData().get(position);
        Intent intent = new Intent();
        intent.putExtra("placeName", bean.getStationPlaceName());
        mPosition = position;
        intent.putExtra("postName", bean.getStationName());
        activity.setResult(Activity.RESULT_OK, intent);
        activity.finish();
    }

    /**
     * 接收回调
     *
     * @param data
     */
    public void getResult(Intent data) {
        if (mChooseType == 0) {
            String placeName = data.getStringExtra("placeName");
            mBinding.tvLeavePostScreenArea.setText(placeName);
        } else {
            String postName = data.getStringExtra("postName");
            mBinding.tvLeavePostScreenPostName.setText(postName);
        }
    }
}
