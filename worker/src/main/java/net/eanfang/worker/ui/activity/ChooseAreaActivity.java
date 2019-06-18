package net.eanfang.worker.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Constant;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.SharePreferenceUtil;
import com.eanfang.util.StringUtils;
import com.eanfang.util.ThreadPoolManager;
import com.yaf.sys.entity.BaseDataEntity;


import net.eanfang.worker.R;
import net.eanfang.worker.ui.interfaces.AreaCheckChangeListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 技能资质的区域
 *
 * @author liangkailun
 */
public class ChooseAreaActivity extends BaseActivity implements AreaCheckChangeListener {

    @BindView(R.id.elv_area)
    ExpandableListView elvArea;
    List<BaseDataEntity> areaListBean;
    ArrayList<String> areaList;
    int chooseCitySize = 0;
    @BindView(R.id.tv_go)
    TextView tvGo;
    private GroupAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_area);
        ButterKnife.bind(this);
        startTransaction(true);
        initView();
        initAreaData();
        areaList = new ArrayList<>();
    }

    private void initView() {
        setTitle("区域选择");
        setLeftBack();
        areaList = getIntent().getStringArrayListExtra("chooseCode");
    }

    private void initAreaData() {
        //获取国家区域
        if (EanfangApplication.sSaveArea == null) {
            String areaJson = SharePreferenceUtil.get().getString(Constant.COUNTRY_AREA_LIST, "");
            if (StringUtils.isEmpty(areaJson)) {
                showToast("加载服务区域失败！");
                tvGo.setClickable(false);
            } else {
                loadingDialog.show();
                ThreadPoolManager manager = ThreadPoolManager.newInstance();
                manager.addExecuteTask(() -> {
                    EanfangApplication.sSaveArea = JSONObject.toJavaObject(JSONObject.parseObject(areaJson), BaseDataEntity.class);
                    runOnUiThread(this::initData);
                });
            }
        } else {
            initData();
        }

    }

    private void initData() {
        loadingDialog.dismiss();
        areaListBean = EanfangApplication.get().sSaveArea.getChildren();
        setListData(areaListBean);
        initAdapter(areaListBean);
    }

    private void initAdapter(List<BaseDataEntity> areaListBean) {
        mAdapter = new GroupAdapter(this, areaListBean);
        mAdapter.setListener(this);
        elvArea.setAdapter(mAdapter);
    }

    private List<String> getListData(List<BaseDataEntity> list) {
        List<String> resultList = new LinkedList<>();
        if (list == null) {
            return resultList;
        }
        for (BaseDataEntity baseDataentity : list) {
            if (baseDataentity.isCheck()) {
                if (baseDataentity.getChildren() == null) {
                    chooseCitySize ++;
                }
                resultList.add(baseDataentity.getDataCode());
            }
            List<String> resultList2 = getListData(baseDataentity.getChildren());
            resultList.addAll(resultList2);
        }
        return resultList;
    }

    private void setListData(List<BaseDataEntity> list) {
        if (list == null) {
            return;
        }
        for (BaseDataEntity baseDataEntity : list) {
            //设置所有的区域为不选
            baseDataEntity.setCheck(false);
            if (areaList.contains(baseDataEntity.getDataCode())) {
                baseDataEntity.setCheck(true);
            }
            setListData(baseDataEntity.getChildren());
        }
    }


    @OnClick(R.id.tv_go)
    public void onViewClicked() {
        setResult();
        finishSelf();
    }

    @Override
    protected void onStop() {
        super.onStop();
        setResult();
    }

    private void setResult() {
        Intent data = new Intent();
        areaList.clear();
        areaList.addAll(getListData(areaListBean));
        data.putExtra("areaCodeList", areaList);
        data.putExtra("cityChooseSize", chooseCitySize);
        setResult(RESULT_OK, data);
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
        }
    }
}