package net.eanfang.client.ui.activity.worksapce;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.eanfang.biz.model.entity.RepairOrderEntity;
import com.eanfang.biz.model.entity.RepairPersonalInfoEntity;
import com.eanfang.biz.model.entity.WorkerEntity;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.ViewFindUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

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
    /**
     * 首页进入技师列表
     */
    private boolean isFromHome = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_select_worker);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
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
        isFromHome = getIntent().getBooleanExtra("isHome", false);
        repairPersonalInfoEntity = (RepairPersonalInfoEntity.ListBean) getIntent().getSerializableExtra("topInfo");


        mFragments.add(AllWorkerFragment.getInstance(toRepairBean, repairPersonalInfoEntity, businessIds, mDoorFee, mOwnerOrgId,isFromHome));
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
