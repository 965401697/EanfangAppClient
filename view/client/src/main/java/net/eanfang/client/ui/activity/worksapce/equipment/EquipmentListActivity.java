package net.eanfang.client.ui.activity.worksapce.equipment;

import android.os.Bundle;

import com.eanfang.config.Config;
import com.flyco.tablayout.SlidingTabLayout;
import com.eanfang.model.sys.BaseDataEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
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
    private Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_list);
        ButterKnife.bind(this);
        setTitle("设备列表");
        setLeftBack();
        initView();
        mBundle = getIntent().getExtras();
    }

    private void initView() {
        mTitles = new String[allmTitles.size()];
        for (BaseDataEntity baseDataEntity : allmTitles) {
            mFragments.add(EquipmentListFragment.getInstance(baseDataEntity.getDataCode()));
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

    public Bundle getmBundle() {
        return mBundle;
    }
}

