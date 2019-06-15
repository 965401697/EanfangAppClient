package net.eanfang.worker.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.annimon.stream.Stream;
import com.eanfang.base.BaseActivity;
import com.eanfang.config.Config;
import com.eanfang.biz.model.GrantChange;
import com.eanfang.biz.model.SystypeBean;
import com.eanfang.biz.model.entity.BaseDataEntity;
import com.eanfang.config.Constant;
import com.eanfang.util.ThreadPoolManager;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.databinding.ActivitySelectAreaBinding;
import net.eanfang.worker.ui.interfaces.AreaCheckChangeListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;

/**
 * @author guanluocang
 * @data 2019/6/13
 * @description 选择地区
 */

public class SelectAreaActivity extends BaseActivity implements AreaCheckChangeListener {

    List<BaseDataEntity> areaListBean;
    private net.eanfang.worker.ui.activity.GroupAdapter mAdapter;
    private List<String> checkListId;
    private List<Integer> unCheckListId;
    private SystypeBean byNetGrant;
    private GrantChange grantChange = new GrantChange();

    private ActivitySelectAreaBinding mSelectAreaBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSelectAreaBinding = DataBindingUtil.setContentView(this, R.layout.activity_select_area);
        super.onCreate(savedInstanceState);
        initView();
        initArea();
        initData();
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    @Override
    protected void initView() {
        setTitle("选择区域");
        setLeftBack(true);
    }

    private void initArea() {

        //获取国家区域
        if (WorkerApplication.getApplication().sSaveArea == null) {
            BaseDataEntity areaJson = (BaseDataEntity) WorkerApplication.get().get(Constant.COUNTRY_AREA_LIST, BaseDataEntity.class);
            if (areaJson != null) {
                showToast("加载服务区域失败！");
            } else {
                ThreadPoolManager manager = ThreadPoolManager.newInstance();
                manager.addExecuteTask(() -> {
                    WorkerApplication.getApplication().sSaveArea = areaJson;
                    runOnUiThread(this::initData);
                });
            }
        } else {
            initData();
        }
        areaListBean = WorkerApplication.getApplication().sSaveArea.getChildren();
    }

    private void initData() {
//        byNetGrant = bean;
        fillData();
    }


    private void fillData() {
//        if (byNetGrant != null) {
//            selDataId = new HashSet<>(byNetGrant.getList().size());
//            selDataId.addAll(Stream.of(byNetGrant.getList()).map(bean -> bean.getDataId()).toList());
//            setListData(areaListBean, true, selDataId);
//        }
        initAdapter(areaListBean);
    }


    private void initAdapter(List<BaseDataEntity> areaListBean) {
        mAdapter = new net.eanfang.worker.ui.activity.GroupAdapter(this, areaListBean);
        mAdapter.setListener(this);
        mSelectAreaBinding.elvArea.setAdapter(mAdapter);
        mSelectAreaBinding.tvConfim.setOnClickListener((v) -> {
            commit();
        });
    }

    private List<String> getListData(List<BaseDataEntity> list, boolean isChecked) {
        List<String> resultList = new ArrayList<>();
        if (list == null) {
            return resultList;
        }

        for (BaseDataEntity baseDataentity : list) {
            if (baseDataentity.isCheck() == isChecked) {
                resultList.add(baseDataentity.getDataCode());
            }
            List<String> resultList2 = getListData(baseDataentity.getChildren(), isChecked);
            resultList.addAll(resultList2);

        }
        return resultList;
    }

    private List<String> setListData(List<BaseDataEntity> list, boolean isChecked, HashSet<Integer> selected) {
        List<String> resultList = new ArrayList<>();
        if (list == null) {
            return resultList;
        }

        for (BaseDataEntity baseDataEntity : list) {
            if (selected.contains(baseDataEntity.getDataCode())) {
                baseDataEntity.setCheck(isChecked);
            }

            List<String> resultList2 = setListData(baseDataEntity.getChildren(), isChecked, selected);
            resultList.addAll(resultList2);

        }
        return resultList;
    }

    private void commit() {
        checkListId = getListData(areaListBean, true);
        Intent intent = new Intent();
        intent.putStringArrayListExtra("projectArea", (ArrayList<String>) checkListId);
        setResult(RESULT_OK, intent);
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
                areaListBean.get(onPos).setCheck(true);
            } else {
                holder.tv_cb.setText("全选");
                areaListBean.get(onPos).setCheck(false);
            }
            holder.tv.setText(areaListBean.get(onPos).getDataName() + "(" + checkAreaSize + "/" + areaSize + ")");
        }
    }
}
