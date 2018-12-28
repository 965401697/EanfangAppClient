package net.eanfang.worker.ui.activity.worksapce.worktransfer;

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

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.FilterDefendLogActivity;
import net.eanfang.worker.ui.fragment.worktransfer.WorkTransferFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Guanluocang
 * @date on 2018/7/11  16:38
 * @decision 交接班 选择中心
 */
public class WorkTransferControlActivity extends BaseActivity {

    @BindView(R.id.tl_task_list)
    SlidingTabLayout tlTaskList;
    @BindView(R.id.tv_filtrate)
    TextView tvFiltrate;
    @BindView(R.id.vp_task_list)
    ViewPager vpTaskList;


    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"我创建的", "我接收的", "全部"};
    private MyPagerAdapter mAdapter;
    private final int FILTRATE_TYPE_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_transfer_control);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("交接班");
        setLeftBack();
        setRightImageResId(R.mipmap.add);

        setRightImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PermKit.get().getExchangeCreatePrem()) {
                    return;
                }
                startActivityForResult(new Intent(WorkTransferControlActivity.this, WorkTransferCreateActivity.class), FILTRATE_TYPE_CODE);
            }
        });

        mFragments.add(WorkTransferFragment.getInstance("我创建的", 1));
        mFragments.add(WorkTransferFragment.getInstance("我接收的", 2));
        mFragments.add(WorkTransferFragment.getInstance("全部", 0));

        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vpTaskList.setAdapter(mAdapter);
        tlTaskList.setViewPager(vpTaskList, mTitles, this, mFragments);

        vpTaskList.setCurrentItem(0);
    }

    @OnClick(R.id.tv_filtrate)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        if (vpTaskList.getCurrentItem() == 0) {// 我创建的
            bundle.putInt("type", 0);
        } else if (vpTaskList.getCurrentItem() == 1) {// 我接收的
            bundle.putInt("type", 1);
        }
        bundle.putString("modle", "transfer");
        JumpItent.jump(WorkTransferControlActivity.this, FilterDefendLogActivity.class, bundle, FILTRATE_TYPE_CODE);
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
                ((WorkTransferFragment) mFragments.get(currentTab)).getTaskData(queryEntry);
            }
        }
    }
}
