package net.eanfang.client.ui.activity.worksapce.repair;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.model.entity.RepairOrderEntity;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityQuickRepairBinding;
import net.eanfang.client.ui.fragment.HomeRepairFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liangkailun
 * Date ：2019-07-23
 * Describe :快速报修、报装、设计页面
 */
public class QuickRepairActivity extends BaseActivity {

    private ActivityQuickRepairBinding mBinding;


    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_quick_repair);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setLeftBack(true);
        String deviceName = getIntent().getStringExtra("deviceBrandName");
        String brandName = getIntent().getStringExtra("dataCode");
        String systemName = getIntent().getStringExtra("systemName");
        RepairOrderEntity mRepairOrderEntity = (RepairOrderEntity) getIntent().getSerializableExtra("mRepairOrderEntity");
        String mType = getIntent().getStringExtra("type");
        setTitle("报修/报装/设计");
        List<Fragment> fragments = new ArrayList<>();
        String[] titles = {"我要报修", "我要报装", "免费设计"};
        fragments.clear();
        fragments.add(HomeRepairFragment.getInstance(0, deviceName, brandName, systemName, mRepairOrderEntity));
        fragments.add(HomeRepairFragment.getInstance(1, deviceName, brandName, systemName, mRepairOrderEntity));
        fragments.add(HomeRepairFragment.getInstance(2, deviceName, brandName, systemName, mRepairOrderEntity));
        mBinding.vpQuickRepair.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        mBinding.tlQuickRepair.setViewPager(mBinding.vpQuickRepair, titles);
        // 设置不可滑动
        mBinding.vpQuickRepair.setScanScroll(false);
        if (mType.equals("repair")) {
            mBinding.tlQuickRepair.setCurrentTab(0);
        } else {
            mBinding.tlQuickRepair.setCurrentTab(1);
        }
    }


}
