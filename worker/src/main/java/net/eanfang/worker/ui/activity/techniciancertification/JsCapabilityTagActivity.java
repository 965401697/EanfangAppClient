package net.eanfang.worker.ui.activity.techniciancertification;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.JsCapabilityTagListBean;
import com.eanfang.model.PostAllTagListBean;
import com.eanfang.ui.base.BaseActivity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.JseQualificationsAndAbilitiesGetListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author WQ
 */
public class JsCapabilityTagActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private JseQualificationsAndAbilitiesGetListAdapter jseQualificationsAndAbilitiesGetListAdapter;
    private Long userId = EanfangApplication.getApplication().getUser().getAccount().getNullUser();
    private List<JsCapabilityTagListBean.ListBean> listBean;
    private List<Integer> addIds = new ArrayList();
    private List<Integer> delIds = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js_capability_tag);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        setLeftBack();
        setTitle("能力标签");
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setNestedScrollingEnabled(false);
        jseQualificationsAndAbilitiesGetListAdapter = new JseQualificationsAndAbilitiesGetListAdapter(true);
        jseQualificationsAndAbilitiesGetListAdapter.bindToRecyclerView(recyclerView);
        setRightTitle("保存");
        setRightTitleOnClickListener(view -> setData());
    }

    private void initData() {
        EanfangHttp.post(UserApi.ADD_JS_NL).params("userId", userId).execute(new EanfangCallback<JsCapabilityTagListBean>(this, true, JsCapabilityTagListBean.class, (bean) -> {
            listBean = bean.getList();
            jseQualificationsAndAbilitiesGetListAdapter.setNewData(listBean);
        }));
    }


    @SuppressLint("LongLogTag")
    private void setData() {
        PostAllTagListBean postAllTagListBean = new PostAllTagListBean();
        PostAllTagListBean.TechWorkerVerifyBean techWorkerVerifyBean = new PostAllTagListBean.TechWorkerVerifyBean();
        PostAllTagListBean.WorkerTagGrantChangeBean workerTagGrantChangeBean = new PostAllTagListBean.WorkerTagGrantChangeBean();
        techWorkerVerifyBean.setAccId(EanfangApplication.get().getAccId());
        techWorkerVerifyBean.setUserId(userId);
        postAllTagListBean.setTechWorkerVerify(techWorkerVerifyBean);
        for (JsCapabilityTagListBean.ListBean bean : listBean) {
            if (bean.isSelected()) {
                addIds.add(bean.getDataId());
            } else {
                delIds.add(bean.getDataId());
            }
        }
        workerTagGrantChangeBean.setAddIds(addIds);
        workerTagGrantChangeBean.setDelIds(delIds);
        postAllTagListBean.setWorkerTagGrantChange(workerTagGrantChangeBean);
        EanfangHttp.post(UserApi.TECH_WORKER_VERIFY).upJson(JSONObject.toJSONString(postAllTagListBean)).execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, bean -> {
            finish();
        }));
    }
}
