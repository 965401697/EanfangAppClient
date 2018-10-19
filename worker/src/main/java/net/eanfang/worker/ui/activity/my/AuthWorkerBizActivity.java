package net.eanfang.worker.ui.activity.my;

import android.os.Bundle;
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
 * @on 2018/1/31  13:07
 * @email houzhongzhou@yeah.net
 * @desc 技师绑定业务类型
 */

public class AuthWorkerBizActivity extends BaseActivity {

    @BindView(R.id.tag_work_type)
    TagFlowLayout tagWorkType;
    @BindView(R.id.tv_confim)
    TextView tvConfim;
    private Long userId = EanfangApplication.getApplication().getUser().getAccount().getNullUser();
    private SystypeBean byNetGrant;
    private GrantChange grantChange = new GrantChange();
    List<BaseDataEntity> bizTypeList = Config.get().getServiceList(1);
    private int status;

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


    private void initData() {
        EanfangHttp.get(UserApi.GET_TECH_WORKER_SYS + userId + "/BIZ_TYPE")
                .execute(new EanfangCallback<SystypeBean>(this, true, SystypeBean.class, (bean) -> {
                    byNetGrant = bean;
                    fillData();
                }));
    }

    private void initView() {
        setTitle("选择业务类别");
        setRightTitle("编辑");
        setLeftBack();
        status = getIntent().getIntExtra("status", status);
    }

    private void fillData() {
        for (int i = 0; i < bizTypeList.size(); i++) {
            for (int j = 0; j < byNetGrant.getList().size(); j++) {
                if (bizTypeList.get(i).getDataId().equals(byNetGrant.getList().get(j).getDataId())) {
                    bizTypeList.get(i).setCheck(true);
                }
            }
        }
        if (status == 1 || status == 2) {
            tvConfim.setText("确定");
//            tagWorkType.setEnabled(false);
        }
        if (status != 2) {
            setRightGone();
        }
        addRepariResult();
    }

    private void initListener() {

        tvConfim.setOnClickListener((v) -> {
            if (status == 0 || status == 3) {
                commit();
            } else if (status == 2) {
                if (isEdit) {
                    doUndoVerify();
                } else {
                    finishSelf();
                }
            } else {
                finishSelf();
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
            EanfangHttp.post(NewApiService.WORKER_AUTH_REVOKE + EanfangApplication.getApplication().getAccId())
                    .execute(new EanfangCallback<JSONPObject>(this, true, JSONPObject.class, bean -> {
                        commit();
                    }));
        }).showDialog();

    }

    /**
     * 重新编辑
     */
    private void doRevoke() {
//        tagWorkType.setEnabled(true);
    }

    private void commit() {
        List<Integer> checkList = Stream.of(bizTypeList).filter(beans -> beans.isCheck() == true
                && Stream.of(byNetGrant.getList()).filter(existsBean -> existsBean.getDataId().equals(beans.getDataId())).count() == 0)
                .map(beans -> beans.getDataId()).toList();
        List<Integer> unCheckList = Stream.of(bizTypeList).filter(beans -> beans.isCheck() == false
                && Stream.of(byNetGrant.getList()).filter(existsBean -> existsBean.getDataId().equals(beans.getDataId())).count() > 0)
                .map(beans -> beans.getDataId()).toList();

        grantChange.setAddIds(checkList);
        grantChange.setDelIds(unCheckList);

        EanfangHttp.post(UserApi.POST_TECH_WORKER_BIZ)
                .upJson(JSONObject.toJSONString(grantChange))
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    finishSelf();
                }));
    }

    public void addRepariResult() {

        tagWorkType.setAdapter(new TagAdapter<BaseDataEntity>(bizTypeList) {
            @Override
            public View getView(FlowLayout parent, int position, BaseDataEntity mrepairResult) {
                TextView tv = (TextView) LayoutInflater.from(AuthWorkerBizActivity.this).inflate(R.layout.layout_trouble_result_item, tagWorkType, false);
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

}
