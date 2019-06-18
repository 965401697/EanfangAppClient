package net.eanfang.worker.ui.activity.worksapce.contacts;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.eanfang.apiservice.UserApi;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.GrantChange;
import com.eanfang.biz.model.Message;
import com.eanfang.biz.model.SystypeBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;
import com.eanfang.biz.model.entity.BaseDataEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.GroupAdapter;
import net.eanfang.worker.ui.activity.worksapce.StateChangeActivity;
import net.eanfang.worker.ui.activity.worksapce.contacts.baseinfo.AuthCompanyDataActivity;
import net.eanfang.worker.ui.activity.worksapce.contacts.verifyqualify.AuthQualifyFirstActivity;
import net.eanfang.worker.ui.activity.worksapce.contacts.verifyqualify.AuthQualifySecondActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/26  15:58
 * @email houzhongzhou@yeah.net
 * @desc 填写服务区域
 */

public class AuthAreaActivity extends BaseActivity {
    @BindView(R.id.elv_area)
    ExpandableListView elvArea;
    @BindView(R.id.tv_confim)
    TextView tvConfim;

    List<BaseDataEntity> areaListBean = Config.get().getRegionList(1);
    private GroupAdapter mAdapter;
    private Long orgid;
    private int verifyStatus;
    private List<Integer> checkListId;
    private List<Integer> unCheckListId;
    private SystypeBean byNetGrant;
    private GrantChange grantChange = new GrantChange();
    private HashSet<Integer> selDataId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_auth);
        ButterKnife.bind(this);
        doLoadArea();
        initView();
        initData();
        // initArea();
    }

    private void initView() {
        setTitle("选择服务区域");
        setLeftBack();
        orgid = getIntent().getLongExtra("orgid", 0);
        verifyStatus = getIntent().getIntExtra("verifyStatus", 0);

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
        if ((verifyStatus != 0 && verifyStatus != 3)) {
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
            if (verifyStatus == 0 || verifyStatus == 3) {
                commit();
            } else {
                finishSelf();
                closeActivity();
            }
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
        EanfangHttp.post(UserApi.GET_ORGUNIT_SEND_VERIFY + orgid)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    showToast("已提交认证");
                    closeActivity();
                    doJumpConfirm();
                    finishSelf();
                }));
    }

    private void closeActivity() {
        WorkerApplication.get().closeActivity(AuthCompanyDataActivity.class);
        WorkerApplication.get().closeActivity(AuthQualifyFirstActivity.class);
        WorkerApplication.get().closeActivity(AuthQualifySecondActivity.class);
    }

    public void doJumpConfirm() {
        Bundle bundle = new Bundle();
        Message message = new Message();
        message.setMsgContent("认证提交成功");
        message.setTip("确定");
        bundle.putSerializable("message", message);
        JumpItent.jump(AuthAreaActivity.this, StateChangeActivity.class, bundle);
        finishSelf();
    }

}
