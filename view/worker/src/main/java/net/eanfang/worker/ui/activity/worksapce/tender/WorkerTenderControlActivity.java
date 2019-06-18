package net.eanfang.worker.ui.activity.worksapce.tender;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModel;
import androidx.viewpager.widget.ViewPager;

import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.listener.EventListener;
import com.eanfang.util.JumpItent;

import net.eanfang.worker.R;
import net.eanfang.worker.databinding.ActivityWorkerTenderControlBinding;
import net.eanfang.worker.ui.fragment.tender.WorkTenderFindFragment;
import net.eanfang.worker.ui.fragment.tender.WorkTenderFragment;
import net.eanfang.worker.ui.widget.TenderNoticePop;
import net.eanfang.worker.viewmodle.tender.TenderViewModle;

import butterknife.OnClick;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * @author guanluocang
 * @data 2019年5月27日 15:57:16
 * @description 招标信息
 */

public class WorkerTenderControlActivity extends BaseActivity {

    private final int FILTRATE_TYPE_CODE = 1001;
    private ViewPager mTenderViewPager;
    /**
     * 正在公告 0  已过期 1
     */
    private String[] mTitles = {"招标公告", "用工找活"};
    @Setter
    @Accessors(chain = true)
    private TenderViewModle mTenderViewModle;
    private ActivityWorkerTenderControlBinding mWorkerTenderControlBinding;
    private MyPagerAdapter mAdapter;

    private ImageView mIvTenderNotice;
    private ImageView mIvTenderFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mWorkerTenderControlBinding = DataBindingUtil.setContentView(this, R.layout.activity_worker_tender_control);
        super.onCreate(savedInstanceState);
        initView();
        initListener();
    }

    @Override
    protected void initView() {
        setTitle("招标信息");
        setLeftBack(true);
        setRightTitle("我的");
        setRightImageResId(R.mipmap.ic_security_right);
        mIvTenderNotice = mWorkerTenderControlBinding.ivTenderNotice;
        mIvTenderFind = mWorkerTenderControlBinding.ivTenderFind;
        mTenderViewPager = mWorkerTenderControlBinding.vpTenderList;
        mAdapter = new MyPagerAdapter(getSupportFragmentManager(), mTitles);
        mAdapter.getFragments().add(WorkTenderFragment.getInstance(mTenderViewModle));
        mAdapter.getFragments().add(WorkTenderFindFragment.getInstance(mTenderViewModle));

        mTenderViewPager.setAdapter(mAdapter);
        mTenderViewPager.setCurrentItem(0);
        mWorkerTenderControlBinding.tvFiltrate.setOnClickListener((view) -> {
            Bundle bundle = new Bundle();
            if (mTenderViewPager.getCurrentItem() == 0) {
                bundle.putInt("type", 0);
            } else if (mTenderViewPager.getCurrentItem() == 1) {
                bundle.putInt("type", 1);
            }
            JumpItent.jump(WorkerTenderControlActivity.this, FilterTenderActivity.class, bundle, FILTRATE_TYPE_CODE);
        });
        mWorkerTenderControlBinding.ivCreate.setOnClickListener((v) -> {
            JumpItent.jump(this, TenderCreateActivity.class);
        });
    }

    @Override
    protected ViewModel initViewModel() {
        mTenderViewModle = LViewModelProviders.of(this, TenderViewModle.class);
        mWorkerTenderControlBinding.setTenderViewModle(mTenderViewModle);
        mTenderViewModle.setWorkerTenderControlBinding(mWorkerTenderControlBinding);
        return mTenderViewModle;
    }

    private void initListener() {
        mTenderViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mIvTenderNotice.setImageResource(R.drawable.ic_tender_notice_pressed_down);
                        mIvTenderFind.setImageResource(R.drawable.ic_tender_find);
                        mWorkerTenderControlBinding.ivCreate.setVisibility(View.GONE);
                        break;
                    case 1:
                        mIvTenderNotice.setImageResource(R.drawable.ic_tender_notice);
                        mIvTenderFind.setImageResource(R.drawable.ic_tender_find_pressed);
                        mWorkerTenderControlBinding.ivCreate.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setRightTitleOnClickListener((view) -> {
            //TODO 我的页面
//            JumpItent.jump(this,);
        });
    }


    /**
     * 筛选回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int currentTab = mTenderViewPager.getCurrentItem();
        if (resultCode == RESULT_OK && requestCode == FILTRATE_TYPE_CODE) {
            QueryEntry queryEntry = (QueryEntry) data.getSerializableExtra("query");
            if (queryEntry != null) {
                if (currentTab == 0) {
                    ((WorkTenderFragment) mAdapter.getFragments().get(currentTab)).getTenderData(queryEntry);
                } else {
                    ((WorkTenderFindFragment) mAdapter.getFragments().get(currentTab)).getTenderData(queryEntry);
                }
            }
        }
    }
}
