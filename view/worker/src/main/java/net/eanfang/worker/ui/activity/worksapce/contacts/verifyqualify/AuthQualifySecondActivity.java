package net.eanfang.worker.ui.activity.worksapce.contacts.verifyqualify;

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
import com.eanfang.model.sys.BaseDataEntity;
import com.eanfang.ui.base.BaseActivity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.GroupAdapter;
import net.eanfang.worker.ui.activity.authentication.SubmitSuccessfullyQyActivity;
import net.eanfang.worker.ui.interfaces.AreaCheckChangeListener;

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


public class AuthQualifySecondActivity extends BaseActivity implements AreaCheckChangeListener {

    @BindView(R.id.elv_area)
    ExpandableListView elvArea;
    @BindView(R.id.tv_confim)
    TextView tvConfim;

    List<BaseDataEntity> areaListBean;
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
        initView();
        initData();
    }

    private void initView() {
        setTitle("服务认证");
        setLeftBack();
        orgid = getIntent().getLongExtra("orgid", 0);
        verifyStatus = getIntent().getIntExtra("verifyStatus", 0);
        isLook = getIntent().getBooleanExtra("isLook", false);
        if (isLook) {
            llTitle.setVisibility(View.GONE);
            tvConfim.setVisibility(View.GONE);

        }

    }

    private void initData() {
        //获取国家区域
        if (EanfangApplication.get().sSaveArea == null) {
            showToast("加载服务区域失败！");
            return;
        }
        BaseDataEntity entity = EanfangApplication.get().sSaveArea;
        areaListBean = entity.getChildren();
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
        mAdapter.setListener(this);
        elvArea.setAdapter(mAdapter);
        if ((verifyStatus != 0 && verifyStatus != 3 || isLook)) {
            //  当状态为已认证状态时， 设置为不可点击不可点击
            mAdapter.isAuth = true;
            tvConfim.setText("确定");
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
            //设置所有的区域为不选
            baseDataBean.setCheck(false);
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

            EanfangHttp.post(UserApi.GET_ORGUNIT_SHOP_ADD_AREA + orgid).upJson(JSONObject.toJSONString(grantChange))
                    .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
//                            showToast("认证资料提交成功");
                        commitVerfiy();
                    }));

        }

    }

    private int order = 2;

    private void commitVerfiy() {

        Intent intent = new Intent(this, SubmitSuccessfullyQyActivity.class);
        intent.putExtra("mOrgId", orgid);
        intent.putExtra("status", verifyStatus);
        intent.putExtra("order", order);
        startActivity(intent);
        finish();
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
