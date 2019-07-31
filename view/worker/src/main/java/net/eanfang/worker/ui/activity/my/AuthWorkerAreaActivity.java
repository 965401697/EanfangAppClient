package net.eanfang.worker.ui.activity.my;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.annimon.stream.Stream;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.config.Config;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.GrantChange;
import com.eanfang.biz.model.bean.SystypeBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.biz.model.entity.BaseDataEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.GroupAdapter;
import net.eanfang.worker.ui.widget.CommitVerfiyView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/31  13:12
 * @email houzhongzhou@yeah.net
 * @desc 技师绑定服务地区
 */
@Deprecated
public class AuthWorkerAreaActivity extends BaseActivity {
    @BindView(R.id.elv_area)
    ExpandableListView elvArea;
    List<BaseDataEntity> areaListBean = Config.get().getRegionList(1);
    @BindView(R.id.tv_confim)
    TextView tvConfim;
    private GroupAdapter mAdapter;
    private Long userid = WorkerApplication.get().getLoginBean().getAccount().getNullUser();
    private List<Integer> checkListId;
    private List<Integer> unCheckListId;
    private SystypeBean byNetGrant;
    private GrantChange grantChange = new GrantChange();
    private HashSet<Integer> selDataId;
    private CommitVerfiyView verfiyView;
    private int status;

    // 是否编辑
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_area_auth);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
        initArea();
        initData();
    }

    private void initView() {
        setTitle("选择服务区域");
        setRightTitle("编辑");
        setLeftBack();
        status = getIntent().getIntExtra("status", status);
    }

    private void initArea() {
//        EanfangHttp.get(NewApiService.GET_BASE_DATA_CACHE_TREE + "0")
//                .tag(this)
//                .execute(new EanfangCallback<String>(this, true, String.class, (str) -> {
//                    if (!StrUtil.isEmpty(str) && !str.contains(Constant.NO_UPDATE)) {
//                        BaseDataBean newDate = JSONObject.parseObject(str, BaseDataBean.class);
//                        areaListBean = Stream.of(newDate.getData()).filter(bean -> bean.getDataCode().equals("3")).toList().get(0).getChildren();
//                        initData();
//                    }
//                }));
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

        if (status != 0 && status != 3) {
            mAdapter.isAuth = true;
            tvConfim.setText("确定");
            elvArea.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                    return true;
                }
            });
        }
        if (status != 2) {
            setRightGone();
        }
        tvConfim.setOnClickListener((v) -> {
            if (status == 0 || status == 3) {
                commit();
            } else if (status == 2) {
                if (isEdit) {
                    doUndoVerify();
                } else {
                    finishSelf();
                }
            } else {
                finishSelf();
            }
        });
        setRightTitleOnClickListener((v) -> {
            showToast("可以进行编辑");
            isEdit = true;
            setRightGone();
            doRevoke();
        });

    }


    /**
     * 进行撤销认证操作
     */
    public void doUndoVerify() {
        new TrueFalseDialog(this, "系统提示", "是否撤销认证并保存信息", () -> {
            EanfangHttp.post(NewApiService.WORKER_AUTH_REVOKE + WorkerApplication.get().getAccId())
                    .execute(new EanfangCallback<JSONPObject>(this, true, JSONPObject.class, bean -> {
                        commit();
                    }));
        }).showDialog();

    }

    /**
     * 重新编辑
     */
    private void doRevoke() {
        mAdapter.isAuth = false;
        elvArea.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return false;
            }
        });
        mAdapter.notifyDataSetChanged();

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
        EanfangHttp.post(UserApi.POST_TECH_WORKER_AREA)
                .upJson(JSONObject.toJSONString(grantChange))
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    finishSelf();
                }));
    }


}
