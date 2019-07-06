package net.eanfang.client.ui.activity.leave_post;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.util.Cn2Spell;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.databinding.ActivityChooseAreaListBinding;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostChooseAreaBean;
import net.eanfang.client.ui.activity.leave_post.viewmodel.LeavePostScreenViewModel;
import net.eanfang.client.ui.adapter.ChooseAreaAdapter;

/**
 * @author liangkailun
 * Describe  选择区域
 */
public class LeavePostScreenChooseListActivity extends BaseActivity {
    private ActivityChooseAreaListBinding mBinding;
    private ChooseAreaAdapter mFriendsAdapter;
    private LeavePostScreenViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_choose_area_list);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setLeftBack(true);
        int chooseType = getIntent().getIntExtra("chooseType", 0);
        String placeName = getIntent().getStringExtra("placeName");
        if (chooseType == 0) {
            setTitle("选择地区");
            mViewModel.getAreaList(ClientApplication.get().getCompanyId());
        } else {
            setTitle("选择岗位名称");
            mViewModel.getStationList(placeName);
        }
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFriendsAdapter = new ChooseAreaAdapter(R.layout.item_choose_area_list, chooseType);
        mFriendsAdapter.bindToRecyclerView(mBinding.recyclerView);
        mFriendsAdapter.setOnItemClickListener((adapter, view, position) -> {
            mViewModel.setChooseResult(LeavePostScreenChooseListActivity.this, adapter, position);
        });

        mBinding.sideBar.setOnStrSelectCallBack((index, selectStr) -> {
            for (int i = 0; i < mFriendsAdapter.getData().size(); i++) {
                if (selectStr.equalsIgnoreCase(Cn2Spell.getPinYin(mFriendsAdapter.getData().get(i).getStationPlaceName()).substring(0, 1).toUpperCase())) {
                    // 选择到首字母出现的位置
                    mBinding.recyclerView.scrollToPosition(i);
                    return;
                }
            }
        });
    }

    @Override
    protected ViewModel initViewModel() {
        mViewModel = LViewModelProviders.of(this, LeavePostScreenViewModel.class);

        mViewModel.getChooseData().observe(this, this::setData);
        return mViewModel;
    }

    private void setData(LeavePostChooseAreaBean leavePostChooseAreaBean) {
        mFriendsAdapter.setNewData(leavePostChooseAreaBean.getList());
    }
}
