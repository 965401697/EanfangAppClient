package net.eanfang.worker.ui.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.config.FastjsonConfig;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.BaseDataBean;
import com.eanfang.model.GrantChange;
import com.eanfang.model.Message;
import com.eanfang.model.SystypeBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.StringUtils;
import com.yaf.sys.entity.BaseDataEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.GroupAdapter;
import net.eanfang.worker.ui.activity.worksapce.CheckActivity;
import net.eanfang.worker.ui.activity.worksapce.StateChangeActivity;
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

public class AuthWorkerAreaActivity extends BaseActivity {
    @BindView(R.id.elv_area)
    ExpandableListView elvArea;
    List<BaseDataEntity> areaListBean = Config.get().getRegionList(1);
    private GroupAdapter mAdapter;
    private Long userid = EanfangApplication.getApplication().getUser().getAccount().getNullUser();
    private List<Integer> checkListId;
    private List<Integer> unCheckListId;
    private SystypeBean byNetGrant;
    private GrantChange grantChange = new GrantChange();
    private HashSet<Integer> selDataId;
    private CommitVerfiyView verfiyView;
    private int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_auth);
        ButterKnife.bind(this);
        initView();
        initArea();
        initData();
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

    private void initView() {
        setTitle("选择服务区域");
        setRightTitle("提交");
        setLeftBack();
        status = getIntent().getIntExtra("status", status);
    }

    private void initAdapter(List<BaseDataEntity> areaListBean) {
        mAdapter = new GroupAdapter(this, areaListBean);
        elvArea.setAdapter(mAdapter);

        setRightTitleOnClickListener(v -> commit());
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
                    verfiyView = new CommitVerfiyView(this, view -> commitVerfiy(verfiyView));
                    verfiyView.show();
                }));
    }

    private void commitVerfiy(CommitVerfiyView verfiyView) {
        EanfangHttp.post(UserApi.POST_TECH_WORKER_SEND_VERIFY)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    verfiyView.dismiss();
                    closeActivity();
                    doJumpConfirm();
                }));
    }

    public void doJumpConfirm() {
        Intent intent = new Intent(AuthWorkerAreaActivity.this, StateChangeActivity.class);
        Bundle bundle = new Bundle();
        Message message = new Message();
        message.setTitle("认证提交成功");
        message.setMsgTitle("您的技师认证资料已经提交成功");
        message.setMsgContent("我们会在72小时内进行审核");
        message.setMsgHelp("如需修改认证资料");
        message.setShowOkBtn(true);
        message.setShowLogo(true);
        message.setTip("");
        bundle.putSerializable("message", message);
        intent.putExtras(bundle);
        startActivity(intent);
        finishSelf();
    }

    private void closeActivity() {
        EanfangApplication.get().closeActivity(AuthWorkerInfoActivity.class.getName());
        EanfangApplication.get().closeActivity(AuthWorkerSysTypeActivity.class.getName());
        EanfangApplication.get().closeActivity(AuthWorkerBizActivity.class.getName());
    }

}
