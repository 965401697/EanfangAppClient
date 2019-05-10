package net.eanfang.client.ui.activity.worksapce;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.eanfang.model.reapair.RepairPersonalInfoEntity;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.ViewFindUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yaf.base.entity.RepairOrderEntity;
import com.yaf.base.entity.WorkerEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.fragment.selectworker.AllWorkerFragment;
import net.eanfang.client.ui.fragment.selectworker.CollectWorkerFragment;
import net.eanfang.client.ui.fragment.selectworker.ServicedWorkerFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/20  15:04
 * @email houzhongzhou@yeah.net
 * @desc 技师列表
 */

public class SelectWorkerActivity extends BaseActivity implements OnTabSelectListener {


    private final String[] mTitles = {
            "附近技师", "收藏的技师", "服务的技师"
    };
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;

    boolean isDestroy = false;
    private RepairOrderEntity toRepairBean;
    private List<WorkerEntity> selectWorkerList = new ArrayList<>();
    private ArrayList<String> businessIds = new ArrayList<>();

    private int mDoorFee;

    private Long mOwnerOrgId;
    /**
     * 个人信息
     */
    private RepairPersonalInfoEntity.ListBean repairPersonalInfoEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_worker);
        ButterKnife.bind(this);
        initView();
        setListener();
    }

    private void initView() {
        setLeftBack();
        setTitle("选择技师");

        toRepairBean = (RepairOrderEntity) getIntent().getSerializableExtra("bean");
        businessIds = getIntent().getStringArrayListExtra("businessIds");
        mDoorFee = getIntent().getIntExtra("doorFee", 0);
        mOwnerOrgId = getIntent().getLongExtra("mOwnerOrgId", 0);
        repairPersonalInfoEntity = (RepairPersonalInfoEntity.ListBean) getIntent().getSerializableExtra("topInfo");


        mFragments.add(AllWorkerFragment.getInstance(toRepairBean, repairPersonalInfoEntity, businessIds, mDoorFee, mOwnerOrgId));
        mFragments.add(CollectWorkerFragment.getInstance(toRepairBean, repairPersonalInfoEntity, businessIds, mDoorFee, mOwnerOrgId));
        mFragments.add(ServicedWorkerFragment.getInstance(toRepairBean, repairPersonalInfoEntity, businessIds, mDoorFee, mOwnerOrgId));
        View decorView = getWindow().getDecorView();
        ViewPager vp = ViewFindUtils.find(decorView, R.id.vp_selectWork);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);
        /**自定义部分属性*/
        SlidingTabLayout tabLayout_2 = ViewFindUtils.find(decorView, R.id.slidingtablayout);
        tabLayout_2.setViewPager(vp, mTitles, this, mFragments);
        tabLayout_2.setOnTabSelectListener(this);
        vp.setCurrentItem(0);
    }

    private void setListener() {
    }


    /**
     * 滑动复写方法
     */
    @Override
    public void onTabSelect(int position) {

    }

    @Override
    public void onTabReselect(int position) {

    }


    /**
     * viewpager Adapter
     */
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestroy = true;
    }

}
