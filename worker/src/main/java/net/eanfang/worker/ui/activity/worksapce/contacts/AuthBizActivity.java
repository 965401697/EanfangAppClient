package net.eanfang.worker.ui.activity.worksapce.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
import net.eanfang.worker.ui.adapter.MultipleChoiceAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/26  15:21
 * @email houzhongzhou@yeah.net
 * @desc 选择业务类型
 */

public class AuthBizActivity extends BaseActivity {
    @BindView(R.id.rev_list)
    RecyclerView revList;
    private Long orgid, adminUserId;
    private int status;
    private SystypeBean byNetGrant;
    private GrantChange grantChange = new GrantChange();
    List<BaseDataEntity> bizTypeList = Config.get().getServiceList(1);

    private String mAssign = "";

    private MultipleChoiceAdapter multipleChoiceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        EanfangHttp.get(UserApi.GET_COMPANY_ORG_SYS_INFO + orgid + "/BIZ_TYPE")
                .execute(new EanfangCallback<SystypeBean>(this, true, SystypeBean.class, (bean) -> {
                    byNetGrant = bean;
                    fillData();
                }));
    }

    private void initView() {
        setTitle("选择业务类别");
        setRightTitle("下一步");
        setLeftBack();
        orgid = getIntent().getLongExtra("orgid", 0);
        status = getIntent().getIntExtra("accid", 0);
        adminUserId = getIntent().getLongExtra("adminUserId", 0);
        mAssign = getIntent().getStringExtra("assign");
        revList.setLayoutManager(new LinearLayoutManager(this));
        revList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        multipleChoiceAdapter = new MultipleChoiceAdapter(this);
        revList.setAdapter(multipleChoiceAdapter);
        if ((status != 0 && status != 3) || mAssign.equals("auth")) {
            multipleChoiceAdapter.isAuth = true;
        }
    }

    private void fillData() {
        for (int i = 0; i < bizTypeList.size(); i++) {
            for (int j = 0; j < byNetGrant.getList().size(); j++) {
                if (bizTypeList.get(i).getDataId().equals(byNetGrant.getList().get(j).getDataId())) {
                    bizTypeList.get(i).setCheck(true);
                }
            }
        }
        initAdapter();
    }

    private void initAdapter() {
        multipleChoiceAdapter.refreshData(bizTypeList);
        multipleChoiceAdapter.setOnItemClickListener((view, position, id) -> {
            bizTypeList.get(position).setCheck(!bizTypeList.get(position).isCheck());
        });

        setRightTitleOnClickListener((v) -> {
            if (status == 0 || status == 3) {
                commit();
            } else {
                jump();
            }
        });
    }

    private void commit() {
        List<Integer> checkList = Stream.of(bizTypeList)
                .filter(beans -> beans.isCheck() == true && Stream.of(byNetGrant.getList()).filter(existsBean -> existsBean.getDataId().equals(beans.getDataId())).count() == 0)
                .map(beans -> beans.getDataId())
                .toList();
        List<Integer> unCheckList = Stream.of(bizTypeList).filter(beans -> beans.isCheck() == false && Stream.of(byNetGrant.getList())
                .filter(existsBean -> existsBean.getDataId().equals(beans.getDataId())).count() > 0)
                .map(beans -> beans.getDataId()).toList();

        grantChange.setAddIds(checkList);
        grantChange.setDelIds(unCheckList);
        for (int i = 0; i < bizTypeList.size(); i++) {
            if (bizTypeList.get(i).isCheck()) {
                EanfangHttp.post(UserApi.GET_ORGUNIT_SHOP_ADD_BIZ + orgid)
                        .upJson(JSONObject.toJSONString(grantChange))
                        .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                            jump();
                        }));
                break;
            }
            showToast("请至少选择一种业务类别");
        }
    }

    private void jump() {
        Intent intent = new Intent(AuthBizActivity.this, AuthAreaActivity.class);
        intent.putExtra("orgid", orgid);
        intent.putExtra("accid", status);
        intent.putExtra("adminUserId", adminUserId);
        intent.putExtra("assign", mAssign);
        startActivity(intent);
    }
}
