package net.eanfang.client.ui.activity.leave_post.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.rds.base.BaseViewModel;

import net.eanfang.client.ui.activity.leave_post.bean.LeavePostDetailBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostMonitorBean;
import net.eanfang.client.ui.activity.leave_post.ds.LeavePostDs;
import net.eanfang.client.ui.activity.leave_post.repo.LeavePostRepo;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :脱岗检测首页
 */
public class LeavePostMonitorViewModel extends BaseViewModel {
    @Getter
    private MutableLiveData<List<LeavePostDetailBean>> leavePostDetailData;
    private LeavePostRepo mLeavePostHomeRepo;
    private int mCurrentPage = 1;
    private Long mCompanyId;

    public LeavePostMonitorViewModel() {
        leavePostDetailData = new MutableLiveData<>();
        mLeavePostHomeRepo = new LeavePostRepo(new LeavePostDs(this));
    }

    /**
     * 获取脱岗首页上部分数据
     */
    public void getMonitorList() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getNotEquals().put("status", "2");
        queryEntry.getOrderBy().put("isInUse", "ASC");
        queryEntry.getOrderBy().put("createTime", "DESC");
        queryEntry.setSize(10);
        queryEntry.setPage(mCurrentPage);
        mLeavePostHomeRepo.postMonitorData(queryEntry).observe(lifecycleOwner, leavePostMonitorBean -> {
            ArrayList<LeavePostDetailBean> list = new ArrayList<>();
            for (LeavePostMonitorBean.ListBean bean : leavePostMonitorBean.getList()) {
                list.add(bean.getLeavePostDetailBean());
            }
            leavePostDetailData.setValue(list);
        });
    }

    /**
     * 加载更多数据
     */
    public void loadMoreData() {
        mCurrentPage++;
        getMonitorList();
    }

}
