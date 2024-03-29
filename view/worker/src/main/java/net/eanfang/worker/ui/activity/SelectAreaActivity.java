package net.eanfang.worker.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;
import com.eanfang.base.kit.cache.CacheKit;
import com.eanfang.biz.model.bean.GrantChange;
import com.eanfang.biz.model.bean.SystypeBean;
import com.eanfang.biz.model.entity.BaseDataEntity;
import com.eanfang.config.Constant;

import net.eanfang.worker.R;
import net.eanfang.worker.databinding.ActivitySelectAreaBinding;
import net.eanfang.worker.ui.interfaces.AreaCheckChangeListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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

    private int mSelectSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSelectAreaBinding = DataBindingUtil.setContentView(this, R.layout.activity_select_area);
        super.onCreate(savedInstanceState);
        initView();
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

    private void initData() {
        areaListBean = CacheKit.get().get(Constant.COUNTRY_AREA_LIST, BaseDataEntity.class).getChildren();
        fillData();
    }


    private void fillData() {
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
                holder.getTvCb().setText("取消全选");
                areaListBean.get(onPos).setCheck(true);
            } else {
                holder.getTvCb().setText("全选");
                areaListBean.get(onPos).setCheck(false);
            }
            holder.getTv().setText(areaListBean.get(onPos).getDataName() + "(" + checkAreaSize + "/" + areaSize + ")");
            mSelectSize = checkAreaSize;
        }
    }
}
