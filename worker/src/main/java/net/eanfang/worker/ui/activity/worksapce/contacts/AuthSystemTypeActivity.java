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
import com.eanfang.model.AuthCompanyBaseInfoBean;
import com.eanfang.model.GrantChange;
import com.eanfang.model.SystypeBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.StringUtils;
import com.yaf.sys.entity.BaseDataEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.MultipleChoiceAdapter;
import net.eanfang.worker.ui.adapter.ParentAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/25  16:41
 * @email houzhongzhou@yeah.net
 * @desc 系统类别
 */

public class AuthSystemTypeActivity extends BaseActivity {
    @BindView(R.id.rev_list)
    RecyclerView revList;

    private Long orgid;
    private int verifyStatus;

    private SystypeBean byNetGrant;
    private GrantChange grantChange = new GrantChange();
    // 获取系统类别
    List<BaseDataEntity> businessOneList = Config.get().getBusinessList(1);

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

        EanfangHttp.get(UserApi.GET_COMPANY_ORG_INFO + orgid)
                .execute(new EanfangCallback<AuthCompanyBaseInfoBean>(this, true, AuthCompanyBaseInfoBean.class, (beans) -> {
                    if (beans == null || StringUtils.isEmpty(beans.getLicenseCode())) {
                        showToast("请先完善企业资料");
                        finishSelf();
                        return;
                    }
                    verifyStatus = beans.getStatus();
                    if ((verifyStatus != 0 && verifyStatus != 3)) {
                        multipleChoiceAdapter.isAuth = true;
                    }
                    initSystemData();
                }));
    }

    private void initSystemData() {
        EanfangHttp.get(UserApi.GET_COMPANY_ORG_SYS_INFO + orgid + "/SYS_TYPE")
                .execute(new EanfangCallback<SystypeBean>(this, true, SystypeBean.class, (bean) -> {
                    byNetGrant = bean;
                    fillData();
                }));
    }

    private void fillData() {
        for (int i = 0; i < businessOneList.size(); i++) {
            for (int j = 0; j < byNetGrant.getList().size(); j++) {
                if (businessOneList.get(i).getDataId().equals(byNetGrant.getList().get(j).getDataId())) {
                    businessOneList.get(i).setCheck(true);
                }
            }
        }
        initAdapter();
    }


    private void initView() {
        setTitle("选择系统类别");
        setRightTitle("下一步");
        setLeftBack();
        orgid = getIntent().getLongExtra("orgid", 0);

        revList.setLayoutManager(new LinearLayoutManager(this));
        revList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        multipleChoiceAdapter = new MultipleChoiceAdapter(this);
        revList.setAdapter(multipleChoiceAdapter);


    }

    private void initAdapter() {
        multipleChoiceAdapter.refreshData(businessOneList);
        multipleChoiceAdapter.setOnItemClickListener((view, position, id) -> {
            businessOneList.get(position).setCheck(!businessOneList.get(position).isCheck());
        });
        setRightTitleOnClickListener((v) -> {
            if (verifyStatus == 0 || verifyStatus == 3) {
                commit();
            } else {
                jump();
            }
        });
    }


    private void commit() {
        List<Integer> checkList = Stream.of(businessOneList)
                .filter(beans -> beans.isCheck() == true &&
                        Stream.of(byNetGrant.getList())
                                .filter(existsBean -> existsBean.getDataId().equals(beans.getDataId())).count() == 0)
                .map(beans -> beans.getDataId()).toList();
        List<Integer> unCheckList = Stream.of(businessOneList)
                .filter(beans -> beans.isCheck() == false &&
                        Stream.of(byNetGrant.getList())
                                .filter(existsBean -> existsBean.getDataId().equals(beans.getDataId())).count() > 0)
                .map(beans -> beans.getDataId()).toList();

        for (int i = 0; i < businessOneList.size(); i++) {
            if (businessOneList.get(i).isCheck()) {
                grantChange.setAddIds(checkList);
                grantChange.setDelIds(unCheckList);
                EanfangHttp.post(UserApi.GET_ORGUNIT_SHOP_ADD_SYS + orgid)
                        .upJson(JSONObject.toJSONString(grantChange))
                        .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                            jump();
                        }));
                break;
            }
            showToast("请选择一种业务类别");
        }
    }

    private void jump() {
        Intent intent = new Intent(AuthSystemTypeActivity.this, AuthBizActivity.class);
        intent.putExtra("orgid", orgid);
        intent.putExtra("verifyStatus", verifyStatus);
        startActivity(intent);
    }
}
