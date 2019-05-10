package net.eanfang.worker.ui.activity.worksapce.contacts.verifyqualify;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.eanfang.apiservice.UserApi;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.GrantChange;
import com.eanfang.model.SystypeBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;
import com.eanfang.model.sys.BaseDataEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.GroupAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author guanluocang
 * @data 2018/10/23
 * @description 安防公司认证 选择区域
 */


public class AuthQualifySecondActivity extends BaseActivity {

    @BindView(R.id.elv_area)
    ExpandableListView elvArea;
    @BindView(R.id.tv_confim)
    TextView tvConfim;

    List<BaseDataEntity> areaListBean = Config.get().getRegionList(1);
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    private GroupAdapter mAdapter;
    private Long orgid;
    private int verifyStatus;
    private List<Integer> checkListId;
    private List<Integer> unCheckListId;
    private SystypeBean byNetGrant;
    private GrantChange grantChange = new GrantChange();
    private HashSet<Integer> selDataId;

    // 查看资质认证选择服务区域
    private boolean isLook = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_qualify_second);
        ButterKnife.bind(this);
        doLoadArea();
        initView();
        initData();
    }

    private void initView() {
        setTitle("技能资质");
        setLeftBack();
        orgid = getIntent().getLongExtra("orgid", 0);
        verifyStatus = getIntent().getIntExtra("verifyStatus", 0);
        isLook = getIntent().getBooleanExtra("isLook", false);

        if (isLook) {
            llTitle.setVisibility(View.GONE);
            tvConfim.setVisibility(View.GONE);
        }

    }

    private void doLoadArea() {
        new Thread(() -> {
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
        }).start();
    }

    private void initData() {
        EanfangHttp.get(UserApi.GET_COMPANY_ORG_SYS_INFO + orgid + "/AREA")
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
        if ((verifyStatus != 0 && verifyStatus != 3 || isLook)) {
            //  当状态为已认证状态时， 设置为不可点击不可点击
            mAdapter.isAuth = true;
            tvConfim.setText("确定");
            elvArea.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                    return true;
                }
            });
        }

        tvConfim.setOnClickListener((v) -> {
            commit();
        });
    }

    private List<Integer> getListData(List<BaseDataEntity> list, boolean isChecked) {
        List<Integer> resultList = new LinkedList<>();
        if (list == null) {
            return resultList;
        }

        for (BaseDataEntity baseDataBean : list) {
            if (baseDataBean.isCheck() == isChecked) {
                if (isChecked && selDataId.contains(baseDataBean.getDataId())) {
                    continue;
                }
                if (!isChecked && !selDataId.contains(baseDataBean.getDataId())) {
                    continue;
                }
                resultList.add(baseDataBean.getDataId());
            }
            List<Integer> resultList2 = getListData(baseDataBean.getChildren(), isChecked);
            resultList.addAll(resultList2);

        }
        return resultList;
    }

    private List<Integer> setListData(List<BaseDataEntity> list, boolean isChecked, HashSet<Integer> selected) {
        List<Integer> resultList = new LinkedList<>();
        if (list == null) {
            return resultList;
        }

        for (BaseDataEntity baseDataBean : list) {
            if (selected.contains(baseDataBean.getDataId())) {
                baseDataBean.setCheck(isChecked);
            }

            List<Integer> resultList2 = setListData(baseDataBean.getChildren(), isChecked, selected);
            resultList.addAll(resultList2);

        }
        return resultList;
    }

    private void commit() {
        checkListId = getListData(areaListBean, true);
        unCheckListId = getListData(areaListBean, false);
        grantChange.setAddIds(checkListId);
        grantChange.setDelIds(unCheckListId);

        if ((checkListId.size() == 0) && (unCheckListId.size() == 0) && (byNetGrant.getList().size() <= 0)) {
            showToast("请至少选择一个服务区域");
        } else {
            for (int i = 0; i < areaListBean.size(); i++) {
                if (areaListBean.get(i).isCheck()) {

                    EanfangHttp.post(UserApi.GET_ORGUNIT_SHOP_ADD_AREA + orgid)
                            .upJson(JSONObject.toJSONString(grantChange))
                            .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
//                            showToast("认证资料提交成功");
                                commitVerfiy();
                            }));
                    break;
                }

            }
        }

    }

    private void commitVerfiy() {

        Bundle bundle = new Bundle();
        bundle.putLong("orgid", orgid);
        bundle.putInt("verifyStatus", verifyStatus);
        JumpItent.jump(AuthQualifySecondActivity.this, AuthQualifyListActivity.class, bundle);

    }
}
