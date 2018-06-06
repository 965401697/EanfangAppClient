package net.eanfang.worker.ui.activity.worksapce.contacts;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.BaseDataBean;
import com.eanfang.model.GrantChange;
import com.eanfang.model.SystypeBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.StringUtils;
import com.yaf.sys.entity.BaseDataEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.GroupAdapter;
import net.eanfang.worker.ui.widget.CommitVerfiyView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    List<BaseDataEntity> areaListBean = Config.get().getRegionList(1);
    private GroupAdapter mAdapter;
    private Long orgid;
    private int status;
    private List<Integer> checkListId;
    private List<Integer> unCheckListId;
    private SystypeBean byNetGrant;
    private GrantChange grantChange = new GrantChange();
    private HashSet<Integer> selDataId;

    private String mAssign = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_auth);
        ButterKnife.bind(this);
        doLoadArea();
        initView();
        initArea();
        initData();
        // initArea();
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

    private void initArea() {
//        EanfangHttp.get(NewApiService.GET_BASE_DATA_CACHE_TREE + "0")
//                .tag(this)
//                .execute(new EanfangCallback<String>(this, true, String.class, (str) -> {
//                    if (!StringUtils.isEmpty(str) && !str.contains(Constant.NO_UPDATE)) {
//                        BaseDataBean newDate = JSONObject.parseObject(str, BaseDataBean.class);
//                        areaListBean = Stream.of(newDate.getData()).filter(bean -> bean.getDataCode().equals("3")).toList().get(0).getChildren();
//                        initData();
//                    }
//                }));
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

    private void initView() {
        setTitle("选择服务区域");
        setRightTitle("完善");
        setLeftBack();
        orgid = getIntent().getLongExtra("orgid", 0);
        status = getIntent().getIntExtra("accid", 0);
        mAssign = getIntent().getStringExtra("assign");

    }

    private void initAdapter(List<BaseDataEntity> areaListBean) {
        mAdapter = new GroupAdapter(this, areaListBean);
        elvArea.setAdapter(mAdapter);
        if ((status != 0 && status != 3) || mAssign.equals("auth")) {
            //  当状态为已认证状态时， 设置为不可点击不可点击
            mAdapter.isAuth = true;
            elvArea.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                    return true;
                }
            });
        }
        setRightTitleOnClickListener((v) -> {
            if (status == 0 || status == 3) {
                commit();
            } else if (status == 1) {
                showToast("您已经提交认证，审核中。。");
            } else if (status == 2) {
                showToast("已认证成功，请勿重复认证，如需需要请联系后台人员");
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
        for (int i = 0; i < areaListBean.size(); i++) {
            if (areaListBean.get(i).isCheck()) {
                EanfangHttp.post(UserApi.GET_ORGUNIT_SHOP_ADD_AREA + orgid)
                        .upJson(JSONObject.toJSONString(grantChange))
                        .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                            showToast("资料保存成功");
                            closeActivity();
                            finishSelf();
//                        verfiyView = new CommitVerfiyView(this, view -> commitVerfiy(verfiyView));
//                        verfiyView.show();

                        }));
                break;
            }
            showToast("请至少选择一个服务区域");
        }
    }

    private void closeActivity() {
        EanfangApplication.get().closeActivity(AuthCompanyActivity.class.getName());
        EanfangApplication.get().closeActivity(AuthSystemTypeActivity.class.getName());
        EanfangApplication.get().closeActivity(AuthBizActivity.class.getName());
    }

}