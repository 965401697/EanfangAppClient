package net.eanfang.worker.ui.activity.worksapce;

import android.os.Bundle;
import android.widget.ExpandableListView;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.eanfang.apiservice.UserApi;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.GrantChange;
import com.eanfang.model.SystypeBean;
import com.eanfang.ui.base.BaseActivity;
import com.yaf.sys.entity.BaseDataEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.GroupAdapter;
import net.eanfang.worker.ui.widget.CommitVerfiyView;

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
    private CommitVerfiyView verfiyView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_auth);
        ButterKnife.bind(this);
        initView();
        initData();
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
        setRightTitle("提交");
        setLeftBack();
        orgid = getIntent().getLongExtra("orgid", 0);
        status = getIntent().getIntExtra("accid", 0);
    }

    private void initAdapter(List<BaseDataEntity> areaListBean) {
        mAdapter = new GroupAdapter(this, areaListBean);
        elvArea.setAdapter(mAdapter);

        setRightTitleOnClickListener((v) -> {
            if (status == 0 || status == 3) {
                commit();
            }else if (status==1){
                showToast("您已经提交认证，审核中。。");
            }else if (status==2){
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
        EanfangHttp.post(UserApi.GET_ORGUNIT_SHOP_ADD_AREA + orgid)
                .upJson(JSONObject.toJSONString(grantChange))
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                        verfiyView = new CommitVerfiyView(this, view -> commitVerfiy(verfiyView));
                        verfiyView.show();

                }));
    }

    private void commitVerfiy(CommitVerfiyView verfiyView) {
        EanfangHttp.post(UserApi.GET_ORGUNIT_SEND_VERIFY + orgid)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    showToast("已提交认证");
                    verfiyView.dismiss();
                }));
    }

}
