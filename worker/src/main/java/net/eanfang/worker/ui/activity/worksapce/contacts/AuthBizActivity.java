package net.eanfang.worker.ui.activity.worksapce.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

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
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import net.eanfang.worker.R;

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


    @BindView(R.id.tag_work_type)
    TagFlowLayout tagWorkType;
    @BindView(R.id.tv_confim)
    TextView tvConfim;
    private Long orgid;
    private int verifyStatus;
    private SystypeBean byNetGrant;
    private GrantChange grantChange = new GrantChange();
    List<BaseDataEntity> bizTypeList = Config.get().getServiceList(1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
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
        setLeftBack();
        orgid = getIntent().getLongExtra("orgid", 0);
        verifyStatus = getIntent().getIntExtra("verifyStatus", 0);
        if ((verifyStatus != 0 && verifyStatus != 3)) {
            tagWorkType.setEnabled(false);
        }
    }

    private void initListener() {

        tvConfim.setOnClickListener((v) -> {
            if (verifyStatus == 0 || verifyStatus == 3) {
                commit();
            } else {
                jump();
            }
        });
    }

    private void fillData() {
        for (int i = 0; i < bizTypeList.size(); i++) {
            for (int j = 0; j < byNetGrant.getList().size(); j++) {
                if (bizTypeList.get(i).getDataId().equals(byNetGrant.getList().get(j).getDataId())) {
                    bizTypeList.get(i).setCheck(true);
                }
            }
        }
        if (verifyStatus == 1 || verifyStatus == 2) {
            tagWorkType.setEnabled(false);
            tvConfim.setText("查看服务区域");
        }
        addRepariResult();
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
        if ((unCheckList.size() == 0) && (checkList.size() == 0) && (byNetGrant.getList().size() <= 0)) {
            showToast("请至少选择一种业务类别");
        } else {
            for (int i = 0; i < bizTypeList.size(); i++) {
                if (bizTypeList.get(i).isCheck()) {
                    EanfangHttp.post(UserApi.GET_ORGUNIT_SHOP_ADD_BIZ + orgid)
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

        tagWorkType.setAdapter(new TagAdapter<BaseDataEntity>(bizTypeList) {
            @Override
            public View getView(FlowLayout parent, int position, BaseDataEntity mrepairResult) {
                TextView tv = (TextView) LayoutInflater.from(AuthBizActivity.this).inflate(R.layout.layout_trouble_result_item, tagWorkType, false);
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
                bizTypeList.get(position).setCheck(!bizTypeList.get(position).isCheck());
                return true;
            }
        });

    }

    private void jump() {
        Intent intent = new Intent(AuthBizActivity.this, AuthAreaActivity.class);
        intent.putExtra("orgid", orgid);
        intent.putExtra("verifyStatus", verifyStatus);
        startActivity(intent);
    }
}
