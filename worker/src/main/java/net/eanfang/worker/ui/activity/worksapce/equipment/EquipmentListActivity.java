package net.eanfang.worker.ui.activity.worksapce.equipment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.eanfang.config.Config;
import com.flyco.tablayout.SlidingTabLayout;
import com.yaf.sys.entity.BaseDataEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EquipmentListActivity extends BaseWorkerActivity {


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
    private final int REQUEST_COMPANY_ID = 101;
    public String ownerCompanyId;
    public String title;
//    public Dialog loadingDialog;
    private EquipmentListFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_list);
        ButterKnife.bind(this);
        setLeftBack();

        setRightTitle("切换客户");
        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EquipmentListActivity.this, EquipmentCooperationCompanyActivity.class);
                intent.putExtra("ownerCompanyId", ownerCompanyId);
                startActivityForResult(intent, REQUEST_COMPANY_ID);
            }
        });
//        loadingDialog = DialogUtil.createLoadingDialog(this);
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
        currentFragment = (EquipmentListFragment) mFragments.get(0);
        vpEquipment.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentFragment = (EquipmentListFragment) mFragments.get(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        currentFragment.onActivityResult(requestCode, resultCode, data);
    }
}

