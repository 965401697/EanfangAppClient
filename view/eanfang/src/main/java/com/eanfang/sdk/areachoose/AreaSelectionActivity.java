package com.eanfang.sdk.areachoose;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.eanfang.R;
import com.eanfang.R2;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.BaseActivity;
import com.eanfang.base.BaseApplication;
import com.eanfang.base.kit.cache.CacheKit;
import com.eanfang.biz.model.bean.GrantChange;
import com.eanfang.biz.model.bean.SystypeBean;
import com.eanfang.biz.model.entity.BaseDataEntity;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;

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
public class AreaSelectionActivity extends BaseActivity implements AreaCheckChangeListener {

    @BindView(R2.id.elv_area)
    ExpandableListView elvArea;
    List<BaseDataEntity> areaListBean;
    @BindView(R2.id.ll_title)
    LinearLayout llTitle;
    @BindView(R2.id.tv_go)
    TextView tvGo;
    private GroupAdapter mAdapter;
    private Long userid = BaseApplication.get().getLoginBean().getAccount().getNullUser();
    private List<Integer> checkListId;
    private List<Integer> unCheckListId;
    private SystypeBean byNetGrant;
    private GrantChange grantChange = new GrantChange();
    private HashSet<Integer> selDataId;

    private int mStatus;
    private boolean isLook = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_skill_area);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
//        startTransaction(true);
        initData();
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    public void initView() {
        setTitle("服务认证");
        setLeftBack(true);
        mStatus = getIntent().getIntExtra("status", -1);
        isLook = getIntent().getBooleanExtra("isLook", false);
        if (isLook) {
            llTitle.setVisibility(View.GONE);
            tvGo.setVisibility(View.GONE);
        }
    }

    private void initData() {
        dismissLoading();
        areaListBean = CacheKit.get().get(Constant.COUNTRY_AREA_LIST, BaseDataEntity.class).getChildren();
        EanfangHttp.get(UserApi.GET_TECH_WORKER_AREA + userid + "/AREA").execute(new EanfangCallback<SystypeBean>(this, true, SystypeBean.class, (bean) -> {
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
        mAdapter.setListener(this);
        elvArea.setAdapter(mAdapter);
        if (isLook) {
            mAdapter.isAuth = true;
        }
    }

    private List<Integer> getListData(List<BaseDataEntity> list, boolean isChecked) {
        List<Integer> resultList = new LinkedList<>();
        if (list == null) {
            return resultList;
        }

        for (BaseDataEntity baseDataentity : list) {
            if (baseDataentity.isCheck() == isChecked) {
                if (isChecked && !selDataId.contains(baseDataentity.getDataId())) {
                    selDataId.add(baseDataentity.getDataId());
                    resultList.add(baseDataentity.getDataId());
                }
                if (!isChecked && selDataId.contains(baseDataentity.getDataId())) {
                    selDataId.remove(baseDataentity.getDataId());
                    resultList.add(baseDataentity.getDataId());
                }
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
            //设置所有的区域为不选
            baseDataEntity.setCheck(false);
            if (selected.contains(baseDataEntity.getDataId())) {
                baseDataEntity.setCheck(isChecked);
            }
            List<Integer> resultList2 = setListData(baseDataEntity.getChildren(), isChecked, selected);
            resultList.addAll(resultList2);
        }
        return resultList;
    }


    private void commit() {
        getListData(areaListBean, true);
        getListData(areaListBean, false);

        grantChange.setAddIds(new ArrayList<>(selDataId));
        grantChange.setDelIds(null);
        if (selDataId.size() == 0) {
            showToast("请至少选择一个服务区域");
        } else {
            setData();
        }

    }

    private void setData() {
        EanfangHttp.post(UserApi.POST_TECH_WORKER_AREA_V3).upJson(JSONObject.toJSONString(grantChange)).execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
//            Intent intent = new Intent(this, SubmitSuccessfullyJsActivity.class);
            Intent intent = new Intent();
            intent.putExtra("status", mStatus);
            intent.putExtra("order", 2);
//            startAnimActivity(intent);
            setResult(1013, intent);
            finish();
        }));
    }

    @OnClick(R2.id.tv_go)
    public void onViewClicked() {
        commit();
    }

    @Override
    public void onCheckAreaChange(int onPos, int secPos, int thirdPos, boolean isCheck) {
        if (thirdPos != -1) {
            areaListBean.get(onPos).getChildren().get(secPos).getChildren().get(thirdPos).setCheck(isCheck);
        } else if (secPos != -1) {
            areaListBean.get(onPos).getChildren().get(secPos).setCheck(isCheck);
        } else {
            areaListBean.get(onPos).setCheck(isCheck);
        }
        int checkAreaSize = 0;
        int areaSize = 0;
        for (BaseDataEntity entity2 : areaListBean.get(onPos).getChildren()) {
            if (entity2.getChildren().size() == 0) {
                if (entity2.isCheck()) {
                    checkAreaSize += 1;
                }
            } else {
                for (BaseDataEntity entity3 : entity2.getChildren()) {
                    if (entity3.isCheck()) {
                        checkAreaSize += 1;
                    }
                }
            }
            areaSize += entity2.getChildren().size() == 0 ? 1 : entity2.getChildren().size();
        }
        GroupAdapter.FirstHolder holder = mAdapter.getChangeTextView(onPos);
        if (holder != null) {
            if (areaSize == checkAreaSize) {
                holder.getTv_cb().setText("取消全选");
                areaListBean.get(onPos).setCheck(true);
            } else {
                holder.getTv_cb().setText("全选");
                areaListBean.get(onPos).setCheck(false);
            }
            holder.getTv().setText(areaListBean.get(onPos).getDataName() + "(" + checkAreaSize + "/" + areaSize + ")");
        }
    }
}