package net.eanfang.worker.ui.activity.worksapce.equipment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.ToastUtil;
import com.flyco.tablayout.SlidingTabLayout;
import com.yaf.base.entity.CooperationEntity;

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
    //    private MyPagerAdapter mAdapter;
    private List<CooperationEntity> allmTitles = new ArrayList<>();
    private List<String> mTitlesList = new ArrayList<>();
    private String[] mTitles;
    private final int REQUEST_COMPANY_ID = 101;
    public String ownerCompanyId;
    private String title;

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
        startTransaction(true);

        CooperationEntity c = (CooperationEntity) getIntent().getSerializableExtra("bean");
        if (c != null) {
            ownerCompanyId = String.valueOf(c.getAssigneeOrgId());
            title = String.valueOf(c.getAssigneeOrg().getOrgName());
            if (title.length() > 5) {
                setTitle(title.substring(0, 6) + "...设备列表");
            } else {
                setTitle(title + "设备列表");
            }
            getCooperationDetail(c);
        } else {
            getCooperationCompanyList();
        }
    }


    private void getCooperationCompanyList() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.setSize(5);
        queryEntry.getEquals().put("status", "1");
        queryEntry.setPage(1);
        queryEntry.getEquals().put("ownerOrgId", String.valueOf(EanfangApplication.getApplication().getCompanyId()));
        EanfangHttp.post(NewApiService.GET_COOPERATION_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<CooperationEntity>(this, true, CooperationEntity.class, true, (list) -> {
                    if (list.size() > 0) {
                        ownerCompanyId = String.valueOf(((CooperationEntity) list.get(0)).getAssigneeOrgId());
                        title = String.valueOf(((CooperationEntity) list.get(0)).getAssigneeOrg().getOrgName());
                        if (title.length() > 5) {
                            setTitle(title.substring(0, 6) + "...设备列表");
                        } else {
                            setTitle(title + "设备列表");
                        }
                        getCooperationDetail((CooperationEntity) list.get(0));
                    } else {
                        tlEquipment.setVisibility(View.GONE);
                        setTitle("设备列表");
                        ToastUtil.get().showToast(EquipmentListActivity.this, "暂无数据");
                    }
                }));
    }

    private void getCooperationDetail(CooperationEntity cooperationEntity) {

        QueryEntry queryEntry = new QueryEntry();

        queryEntry.getEquals().put("assigneeOrgId", String.valueOf(cooperationEntity.getAssigneeOrgId()));
        queryEntry.getEquals().put("ownerOrgId", String.valueOf(cooperationEntity.getOwnerOrgId()));
        queryEntry.getEquals().put("beginTime", GetDateUtils.dateToFormatString(cooperationEntity.getBeginTime(), "yyyy.MM.dd"));
        queryEntry.getEquals().put("endTime", GetDateUtils.dateToFormatString(cooperationEntity.getEndTime(), "yyyy.MM.dd"));
        queryEntry.getEquals().put("status", String.valueOf(cooperationEntity.getStatus()));

        EanfangHttp.post(NewApiService.GET_COOPERATION_DETAIL)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<CooperationEntity>(this, true, CooperationEntity.class, true, (list) -> {

//                    allmTitles.clear();
//                    mTitlesList.clear();
//                    mFragments.clear();


                    if (list.size() > 0) {


                        for (CooperationEntity bean : list) {

                            //系统类型
                            String business = Config.get().getBusinessNameByCode(bean.getBusinessOneCode(), 1);

                            allmTitles.add(bean);

                            mTitlesList.add(business);
                        }
                        initView();
                    } else {
                        tlEquipment.setVisibility(View.GONE);
                        ToastUtil.get().showToast(EquipmentListActivity.this, "暂无数据");
                    }

                }));
    }


    private void initView() {
        mTitles = new String[allmTitles.size()];
        for (CooperationEntity cooperationEntity : allmTitles) {
            mFragments.add(EquipmentListFragment.getInstance(String.valueOf(cooperationEntity.getBusinessOneCode()), ownerCompanyId, this));
            Log.e("zzw00000000000 === ", ownerCompanyId);
        }
        mTitlesList.toArray(mTitles);
        MyPagerAdapter mAdapter = new MyPagerAdapter(getSupportFragmentManager());

        vpEquipment.setAdapter(mAdapter);
        vpEquipment.setOffscreenPageLimit(allmTitles.size());
        tlEquipment.setViewPager(vpEquipment, mTitles, this, mFragments);
        vpEquipment.setCurrentItem(0);
    }


    private class MyPagerAdapter extends FragmentStatePagerAdapter {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_COMPANY_ID) {
            CooperationEntity c = (CooperationEntity) data.getSerializableExtra("bean");

            ownerCompanyId = String.valueOf(c.getAssigneeOrgId());
            title = String.valueOf(c.getAssigneeOrg().getOrgName());
            if (title.length() > 5) {
                setTitle(title.substring(0, 6) + "...设备列表");
            } else {
                setTitle(title + "设备列表");
            }
            getCooperationDetail(c);
        }

    }
}

