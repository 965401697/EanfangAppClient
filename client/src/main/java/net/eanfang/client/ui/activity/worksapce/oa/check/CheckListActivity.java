package net.eanfang.client.ui.activity.worksapce.oa.check;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermKit;
import com.eanfang.util.QueryEntry;
import com.flyco.tablayout.SlidingTabLayout;

import net.eanfang.client.R;
import net.eanfang.client.ui.fragment.WorkCheckListFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author guanluocang
 * @data 2018/11/7
 * @description 设备点检 列表
 */
public class CheckListActivity extends BaseActivity {

    @BindView(R.id.tl_task_list)
    SlidingTabLayout tlTaskList;
    @BindView(R.id.tv_filtrate)
    TextView tvFiltrate;
    @BindView(R.id.vp_task_list)
    ViewPager vpTaskList;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"我创建的", "我处理的", "全部"};
    private MyPagerAdapter mAdapter;
    private final int FILTRATE_TYPE_CODE = 101;
    private final int REFRESH_CODE = 102;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list_device);
        ButterKnife.bind(this);
        initView();
    }
    private void initView() {
        setTitle("设备点检");
        setLeftBack();
        setRightImageResId(R.mipmap.add);

        setRightImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PermKit.get().getWorkInspectCreatePrem()) return;
                startActivityForResult(new Intent(CheckListActivity.this, AddNewCheckActivity.class), REFRESH_CODE);
            }
        });

        mFragments.add(WorkCheckListFragment.getInstance("我创建的", 1));
        mFragments.add(WorkCheckListFragment.getInstance("我处理的", 2));
        mFragments.add(WorkCheckListFragment.getInstance("全部", 0));

        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vpTaskList.setAdapter(mAdapter);
        tlTaskList.setViewPager(vpTaskList, mTitles, this, mFragments);

        vpTaskList.setCurrentItem(0);
    }

    /**
     * 筛选
     */
    @OnClick(R.id.tv_filtrate)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        if (vpTaskList.getCurrentItem() == 0) {// 我创建的
            bundle.putInt("type", 0);
        } else if (vpTaskList.getCurrentItem() == 1) {// 我处理的
            bundle.putInt("type", 1);
        }
        JumpItent.jump(CheckListActivity.this, FiltrateCheckActivity.class, bundle, FILTRATE_TYPE_CODE);
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

    /**
     * 筛选回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int currentTab = tlTaskList.getCurrentTab();
        if (resultCode == RESULT_OK && requestCode == FILTRATE_TYPE_CODE) {

            QueryEntry queryEntry = (QueryEntry) data.getSerializableExtra("query");
            if (queryEntry != null) {
                ((WorkCheckListFragment) mFragments.get(currentTab)).getTaskData(queryEntry);
            }
        }
    }

}
