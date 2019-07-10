package net.eanfang.client.ui.activity.leave_post.viewmodel;

import android.app.Activity;
import android.content.Intent;

import androidx.lifecycle.MutableLiveData;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.rds.base.BaseViewModel;

import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.activity.leave_post.LeavePostAddPostActivity;
import net.eanfang.client.ui.activity.leave_post.LeavePostManageDetailActivity;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostManageListBean;
import net.eanfang.client.ui.activity.leave_post.ds.LeavePostDs;
import net.eanfang.client.ui.activity.leave_post.repo.LeavePostRepo;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * @author liangkailun
 * Date ：2019-06-28
 * Describe :
 */
public class LeavePostManageListViewModel extends BaseViewModel {
    @Getter
    private MutableLiveData<List<String>> leavePostHomeData;

    private int mCurrentPage = 1;

    @Getter
    private MutableLiveData<LeavePostManageListBean> leavePostManageListData;
    private LeavePostRepo mLeavePostHomeRepo;

    public LeavePostManageListViewModel() {
        leavePostHomeData = new MutableLiveData<>();
        leavePostManageListData = new MutableLiveData<>();
        mLeavePostHomeRepo = new LeavePostRepo(new LeavePostDs(this));
    }


    public void getPostManageList() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("companyId", String.valueOf(ClientApplication.get().getCompanyId()));
        queryEntry.setSize(10);
        queryEntry.setPage(mCurrentPage);
        mLeavePostHomeRepo.postManageListData(queryEntry).observe(lifecycleOwner, leavePostManageListData::setValue);
    }

    public void setItemClick(Activity activity, BaseQuickAdapter adapter, int position) {
        LeavePostManageListBean.ListBean bean = (LeavePostManageListBean.ListBean) adapter.getData().get(position);
        activity.startActivity(new Intent(activity, LeavePostManageDetailActivity.class).putExtra("areaName", bean.getPlaceName()));
    }

    public void gotoAddPostPage(Activity activity) {
        activity.startActivity(new Intent(activity, LeavePostAddPostActivity.class).putExtra("postType", 0));
    }

    public void loadMoreData(){

        if (leavePostManageListData.getValue() != null && mCurrentPage < leavePostManageListData.getValue().getTotalPage()) {
            mCurrentPage ++;
            getPostManageList();
        }
    }

}
