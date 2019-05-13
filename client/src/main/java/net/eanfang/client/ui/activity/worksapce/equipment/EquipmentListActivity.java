package net.eanfang.client.ui.activity.worksapce.equipment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.eanfang.config.Config;
import com.flyco.tablayout.SlidingTabLayout;
import com.yaf.sys.entity.BaseDataEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EquipmentListActivity extends BaseClientActivity {


    @BindView(R.id.tl_equipment)
    SlidingTabLayout tlEquipment;
    @BindView(R.id.vp_equipment)
    ViewPager vpEquipment;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;
    private List<BaseDataEntity> allmTitles = Config.get().getBusinessList(1);
    private List<String> mTitlesList = new ArrayList<>();
    private String[] mTitles;

    /**
     * 是否报修
     */
    private Boolean isRepair = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_list);
        ButterKnife.bind(this);
        setTitle("设备列表");
        setLeftBack();
        initView();
    }

    private void initView() {
        isRepair = getIntent().getBooleanExtra("repair", false);
        mTitles = new String[allmTitles.size()];
        for (BaseDataEntity baseDataEntity : allmTitles) {
            mFragments.add(EquipmentListFragment.getInstance(baseDataEntity.getDataCode(), isRepair));
            mTitlesList.add(baseDataEntity.getDataName());
        }
        mTitlesList.toArray(mTitles);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vpEquipment.setAdapter(mAdapter);
        tlEquipment.setViewPager(vpEquipment, mTitles, this, mFragments);
        vpEquipment.setCurrentItem(0);
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

}

