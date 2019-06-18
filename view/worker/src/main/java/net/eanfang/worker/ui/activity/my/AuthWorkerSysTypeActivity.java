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
import com.eanfang.config.Config;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.GrantChange;
import com.eanfang.biz.model.SystypeBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.biz.model.entity.BaseDataEntity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/30  23:04
 * @email houzhongzhou@yeah.net
 * @desc 技师绑定系统类别
 */
@Deprecated
public class AuthWorkerSysTypeActivity extends BaseActivity {

    @BindView(R.id.tag_work_type)
    TagFlowLayout tagWorkType;
    @BindView(R.id.tv_confim)
    TextView tvConfim;
    private Long userid = WorkerApplication.get().getLoginBean().getAccount().getNullUser();
    private int status;
    private SystypeBean byNetGrant;
    private GrantChange grantChange = new GrantChange();
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
        status = getIntent().getIntExtra("status", 0);
        setLeftBack();
    }

    private void initData() {
        EanfangHttp.get(UserApi.GET_TECH_WORKER_SYS + userid + "/SYS_TYPE")
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
        if (status == 1 || status == 2) {
//            tagWorkType.setEnabled(false);
            tvConfim.setText("确定");
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
            EanfangHttp.post(NewApiService.WORKER_AUTH_REVOKE + WorkerApplication.get().getAccId())
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
        List<Integer> checkList = Stream.of(businessOneList)
                .filter(beans -> beans.isCheck() && Stream.of(byNetGrant.getList()).filter(existsBean -> existsBean.getDataId().equals(beans.getDataId())).count() == 0)
                .map(BaseDataEntity::getDataId).distinct().toList();
        List<Integer> unCheckList = Stream.of(businessOneList).filter(beans -> !beans.isCheck()
                && Stream.of(byNetGrant.getList()).filter(existsBean -> existsBean.getDataId().equals(beans.getDataId())).count() > 0)
                .map(BaseDataEntity::getDataId).distinct().toList();

        grantChange.setAddIds(checkList);
        grantChange.setDelIds(unCheckList);
        EanfangHttp.post(UserApi.POST_TECH_WORKER_SYS)
                .upJson(JSONObject.toJSONString(grantChange))
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    finishSelf();
                }));
    }

    public void addRepariResult() {

        tagWorkType.setAdapter(new TagAdapter<BaseDataEntity>(businessOneList) {
            @Override
            public View getView(FlowLayout parent, int position, BaseDataEntity mrepairResult) {
                TextView tv = (TextView) LayoutInflater.from(AuthWorkerSysTypeActivity.this).inflate(R.layout.layout_trouble_result_item, tagWorkType, false);
                tv.setText(mrepairResult.getDataName());
                return tv;
            }

            @Override
            public boolean setSelected(int position, BaseDataEntity baseDataEntity) {
                Long coutn = Stream.of(byNetGrant.getList()).filter(bean -> bean.getDataId().equals(baseDataEntity.getDataId())).count();
                return coutn > 0;
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

}
