package net.eanfang.worker.ui.activity.worksapce.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.annimon.stream.Stream;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.AuthCompanyBaseInfoBean;
import com.eanfang.model.GrantChange;
import com.eanfang.model.SystypeBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.StringUtils;
import com.yaf.sys.entity.BaseDataEntity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.my.AuthWorkerSysTypeActivity;
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

    @BindView(R.id.tag_work_type)
    TagFlowLayout tagWorkType;
    @BindView(R.id.tv_confim)
    TextView tvConfim;

    private Long orgid;
    private int verifyStatus;

    private SystypeBean byNetGrant;
    private GrantChange grantChange = new GrantChange();
    // 获取系统类别
    List<BaseDataEntity> businessOneList = Config.get().getBusinessList(1);


    // 是否编辑
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        setTitle("选择系统类别");
        setRightTitle("编辑");
        setLeftBack();
        orgid = getIntent().getLongExtra("orgid", 0);
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
                    // 已认证 或者 认证中
                    if ((verifyStatus != 0 && verifyStatus != 3)) {
                        tagWorkType.setEnabled(false);
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
        if (verifyStatus == 1 || verifyStatus == 2) {
            tagWorkType.setEnabled(false);
            tvConfim.setText("查看业务类别");
        }
        if (verifyStatus != 2) {
            setRightGone();
        }
        addRepariResult();
    }


    private void initListener() {

        tvConfim.setOnClickListener((v) -> {
            if (verifyStatus == 0 || verifyStatus == 3) {
                commit();
            } else if (verifyStatus == 2) {
                if (isEdit) {
                    doUndoVerify();
                } else {
                    finishSelf();
                }
            } else {
                jump();
            }
        });
        setRightTitleOnClickListener((v) -> {
            showToast("可以进行编辑");
            isEdit = true;
            setRightGone();
            doRevoke();
        });
    }

    /**
     * 进行撤销认证操作
     */
    public void doUndoVerify() {
        new TrueFalseDialog(this, "系统提示", "是否撤销认证并保存信息", () -> {
            EanfangHttp.post(NewApiService.COMPANY_SECURITY_AUTH_REVOKE + orgid)
                    .execute(new EanfangCallback<JSONPObject>(this, true, JSONPObject.class, bean -> {
                        commit();
                    }));
        }).showDialog();
    }

    /**
     * 重新编辑
     */
    private void doRevoke() {
        tagWorkType.setEnabled(true);
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

        if ((unCheckList.size() == 0) && (checkList.size() == 0) && (byNetGrant.getList().size() <= 0)) {
            showToast("请选择一种系统类别");
        } else {
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


            }
        }

    }

    public void addRepariResult() {

        tagWorkType.setAdapter(new TagAdapter<BaseDataEntity>(businessOneList) {
            @Override
            public View getView(FlowLayout parent, int position, BaseDataEntity mrepairResult) {
                TextView tv = (TextView) LayoutInflater.from(AuthSystemTypeActivity.this).inflate(R.layout.layout_trouble_result_item, tagWorkType, false);
                tv.setText(mrepairResult.getDataName());
                return tv;
            }

            @Override
            public boolean setSelected(int position, BaseDataEntity baseDataEntity) {
                Long coutn = Stream.of(byNetGrant.getList()).filter(bean -> bean.getDataId().equals(baseDataEntity.getDataId())).count();
                if (coutn > 0) {
                    return true;
                }
                return false;
            }
        });
        tagWorkType.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                businessOneList.get(position).setCheck(!businessOneList.get(position).isCheck());
                return true;
            }
        });

    }

    private void jump() {
        Intent intent = new Intent(AuthSystemTypeActivity.this, AuthBizActivity.class);
        intent.putExtra("orgid", orgid);
        intent.putExtra("verifyStatus", verifyStatus);
        startActivity(intent);
    }
}
