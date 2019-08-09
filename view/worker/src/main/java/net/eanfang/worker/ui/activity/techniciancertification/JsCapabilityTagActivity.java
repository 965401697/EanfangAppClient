package net.eanfang.worker.ui.activity.techniciancertification;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.BaseActivity;
import com.eanfang.biz.model.bean.JsCapabilityTagListBean;
import com.eanfang.biz.model.bean.PostAllTagListBean;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
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
    private Long userId = WorkerApplication.get().getLoginBean().getAccount().getNullUser();
    private List<JsCapabilityTagListBean.ListBean> listBean;
    private List<Integer> addIds = new ArrayList();
    private List<Integer> delIds = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_js_capability_tag);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    @Override
    protected void initView() {
        setLeftBack(true);
        setRightClick(true);
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
        techWorkerVerifyBean.setAccId(WorkerApplication.get().getAccId());
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
