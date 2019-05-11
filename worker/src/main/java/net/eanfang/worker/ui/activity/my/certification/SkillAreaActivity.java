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
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.GrantChange;
import com.eanfang.model.SystypeBean;
import com.yaf.sys.entity.BaseDataEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.GroupAdapter;
import net.eanfang.worker.ui.activity.techniciancertification.SubmitSuccessfullyJsActivity;
import net.eanfang.worker.ui.base.BaseWorkerActivity;
import net.eanfang.worker.ui.interfaces.AreaCheckChangeListener;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 技能资质的区域
 */
public class SkillAreaActivity extends BaseWorkerActivity implements AreaCheckChangeListener {

    @BindView(R.id.elv_area)
    ExpandableListView elvArea;
    List<BaseDataEntity> areaListBean;
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
        initData();
    }

    private void initView() {
        setTitle("服务认证");
        setLeftBack();
        mStatus = getIntent().getIntExtra("status", -1);
        isLook = getIntent().getBooleanExtra("isLook", false);
        if (isLook) {
            llTitle.setVisibility(View.GONE);
            tvGo.setVisibility(View.GONE);
        }
    }

    private void initData() {
        //获取国家区域
        if (EanfangApplication.get().sSaveArea == null) {
            showToast("加载服务区域失败！");
            return;
        }
        areaListBean = EanfangApplication.get().sSaveArea.getChildren();
        EanfangHttp.get(UserApi.GET_TECH_WORKER_SYS + userid + "/AREA").execute(new EanfangCallback<SystypeBean>(this, true, SystypeBean.class, (bean) -> {
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
        if ((checkListId.size() == 0) && (byNetGrant.getList().size() == 0)) {
            showToast("请至少选择一个服务区域");
        } else {
            setData();
        }

    }

    private void setData() {
        EanfangHttp.post(UserApi.POST_TECH_WORKER_AREA).upJson(JSONObject.toJSONString(grantChange)).execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
            if (bean.toJSONString().contains("失败原因")) {
//                showToast("请查看认证失败原因并修改后再提交");
            } else {
                Intent intent = new Intent(this, SubmitSuccessfullyJsActivity.class);
                intent.putExtra("status", mStatus);
                intent.putExtra("order", 2);
                startAnimActivity(intent);
                finish();
            }
        }));
    }

    @OnClick(R.id.tv_go)
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
                holder.tv_cb.setText("取消全选");
            } else {
                holder.tv_cb.setText("全选");

            }
            holder.tv.setText(areaListBean.get(onPos).getDataName() + "(" + checkAreaSize + "/" + areaSize + ")");
        }
    }
}