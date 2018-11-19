package net.eanfang.worker.ui.activity.my.certification;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.GrantChange;
import com.eanfang.model.SystypeBean;
import com.yaf.sys.entity.BaseDataEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.GroupAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 技能资质的区域
 */
public class SkillAreaActivity extends BaseWorkerActivity {

    @BindView(R.id.elv_area)
    ExpandableListView elvArea;
    List<BaseDataEntity> areaListBean = Config.get().getRegionList(1);
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.tv_go)
    TextView tvGo;
    private GroupAdapter mAdapter;
    private Long userid = EanfangApplication.getApplication().getUser().getAccount().getNullUser();
    private List<Integer> checkListId;
    private List<Integer> unCheckListId;
    private SystypeBean byNetGrant;
    private GrantChange grantChange = new GrantChange();
    private HashSet<Integer> selDataId;

    private int mStatus;
    private boolean isLook = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_area);
        ButterKnife.bind(this);
        startTransaction(true);
        initView();
        initArea();
        initData();
    }

    private void initView() {
        setTitle("技能资质");
        setLeftBack();

        mStatus = getIntent().getIntExtra("status", -1);

        isLook = getIntent().getBooleanExtra("isLook", false);


        if (isLook) {
            llTitle.setVisibility(View.GONE);
            tvGo.setVisibility(View.GONE);
        }
    }

    private void initArea() {
        //获得全部 地区数据
        List<BaseDataEntity> allAreaList = new ArrayList<>(Config.get().getRegionList());
        for (int i = 0; i < areaListBean.size(); i++) {
            BaseDataEntity provinceEntity = areaListBean.get(i);
            //处理当前省下的所有市
            List<BaseDataEntity> cityList = Stream.of(allAreaList).filter(bean -> bean.getParentId() != null && bean.getParentId().intValue() == provinceEntity.getDataId()).toList();
            //查询出来后，移除，以增加效率
            allAreaList.removeAll(cityList);
            for (int j = 0; j < cityList.size(); j++) {
                BaseDataEntity cityEntity = cityList.get(j);
                //处理当前市下所有区县
                List<BaseDataEntity> countyList = Stream.of(allAreaList).filter(bean -> bean.getParentId() != null && bean.getParentId().intValue() == cityEntity.getDataId()).toList();
                //查询出来后，移除，以增加效率
                allAreaList.removeAll(countyList);
                cityList.get(j).setChildren(countyList);
            }
            areaListBean.get(i).setChildren(cityList);


        }
    }

    private void initData() {
        EanfangHttp.get(UserApi.GET_TECH_WORKER_SYS + userid + "/AREA")
                .execute(new EanfangCallback<SystypeBean>(this, true, SystypeBean.class, (bean) -> {
                    byNetGrant = bean;
                    fillData();
                }));
    }


    private void fillData() {
        selDataId = new HashSet<>(byNetGrant.getList().size());
        selDataId.addAll(Stream.of(byNetGrant.getList()).map(bean -> bean.getDataId()).toList());
        setListData(areaListBean, true, selDataId);
        initAdapter(areaListBean);
    }


    private void initAdapter(List<BaseDataEntity> areaListBean) {
        mAdapter = new GroupAdapter(this, areaListBean);
        elvArea.setAdapter(mAdapter);
        if (isLook) {
            mAdapter.isAuth = true;
            elvArea.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                    return true;
                }
            });
        }
    }

    private List<Integer> getListData(List<BaseDataEntity> list, boolean isChecked) {
        List<Integer> resultList = new LinkedList<>();
        if (list == null) {
            return resultList;
        }

        for (BaseDataEntity baseDataentity : list) {
            if (baseDataentity.isCheck() == isChecked) {
                if (isChecked && selDataId.contains(baseDataentity.getDataId())) {
                    continue;
                }
                if (!isChecked && !selDataId.contains(baseDataentity.getDataId())) {
                    continue;
                }
                resultList.add(baseDataentity.getDataId());
            }
            List<Integer> resultList2 = getListData(baseDataentity.getChildren(), isChecked);
            resultList.addAll(resultList2);

        }
        return resultList;
    }

    private List<Integer> setListData(List<BaseDataEntity> list, boolean isChecked, HashSet<Integer> selected) {
        List<Integer> resultList = new LinkedList<>();
        if (list == null) {
            return resultList;
        }

        for (BaseDataEntity baseDataEntity : list) {
            if (selected.contains(baseDataEntity.getDataId())) {
                baseDataEntity.setCheck(isChecked);
            }

            List<Integer> resultList2 = setListData(baseDataEntity.getChildren(), isChecked, selected);
            resultList.addAll(resultList2);

        }
        return resultList;
    }

    private void commit() {
        checkListId = getListData(areaListBean, true);
        unCheckListId = getListData(areaListBean, false);

        grantChange.setAddIds(checkListId);
        grantChange.setDelIds(unCheckListId);
        //判断取消后的 是不是还有选中的状态   
        // TODO: 2018/11/6     集合加集合填补进去
        SystypeBean grant = new SystypeBean();
        grant.getList().addAll(byNetGrant.getList());
        for (int i = 0; i < byNetGrant.getList().size(); i++) {
            for (Integer j : unCheckListId) {
                if (byNetGrant.getList().get(i).getDataId() == j) {
                    grant.getList().remove(i);
                }
            }
        }

        if ((checkListId.size() == 0) && (byNetGrant.getList().size() == 0)) {
            showToast("请至少选择一个服务区域");
        } else {
            EanfangHttp.post(UserApi.POST_TECH_WORKER_AREA)
                    .upJson(JSONObject.toJSONString(grantChange))
                    .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                        Intent intent = new Intent(this, SkillCertificafeListActivity.class);
                        intent.putExtra("status", mStatus);
                        startAnimActivity(intent);
                    }));
        }
    }

    @OnClick(R.id.tv_go)
    public void onViewClicked() {
        commit();
    }
}