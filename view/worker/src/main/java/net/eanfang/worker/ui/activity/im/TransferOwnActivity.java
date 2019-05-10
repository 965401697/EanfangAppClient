package net.eanfang.worker.ui.activity.im;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.GroupDetailBean;
import com.eanfang.util.ToastUtil;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.TransferOwnAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 转让群主
 */
public class TransferOwnActivity extends BaseWorkerActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private String mGroupId;
    private TransferOwnAdapter mTransferOwnAdapter;
    private String ownId = "";//群主id
    private int mOldPosition = -1;//标志位

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_own);
        ButterKnife.bind(this);
        setTitle("转让群主");
        setLeftBack();
        setRightTitle("确定");
        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确定转让
                transfer();

            }
        });
        mGroupId = getIntent().getStringExtra("groupId");
        initData();
    }

    private void initData() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTransferOwnAdapter = new TransferOwnAdapter(R.layout.item_transfer_own);
        mTransferOwnAdapter.bindToRecyclerView(mRecyclerView);

        //查找群内成员
        EanfangHttp.post(UserApi.POST_GROUP_NUM)
                .params("groupId", mGroupId)
                .execute(new EanfangCallback<GroupDetailBean.ListBean>(this, true, GroupDetailBean.ListBean.class, true, (list) -> {
                    if (list.size() > 0) {
                        ArrayList<GroupDetailBean.ListBean> friendListBeanArrayList = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {


                            if (!(String.valueOf(EanfangApplication.getApplication().getAccId()).equals(list.get(i).getAccountEntity().getAccId()))) {
                                friendListBeanArrayList.add(list.get(i));
                            }
                        }

                        mTransferOwnAdapter.setNewData(friendListBeanArrayList);
                    }
                }));

        mTransferOwnAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, final View view, int position) {
                if (view.getId() == R.id.rb_checked) {
                    GroupDetailBean.ListBean bean = ( GroupDetailBean.ListBean) adapter.getData().get(position);

                    if (mOldPosition == -1) {
                        mOldPosition = position;
                        ownId = bean.getAccId();
                        bean.setFlag(1);
                        return;
                    }

                    if (position == mOldPosition) {
                        mOldPosition = -1;
                        ownId = "";
                        bean.setFlag(0);
                    } else {
                        ((GroupDetailBean.ListBean) adapter.getData().get(mOldPosition)).setFlag(0);
                        mOldPosition = position;
                        ownId = bean.getAccId();
                        bean.setFlag(1);
                    }
                    adapter.notifyDataSetChanged();

                }
            }
        });
    }

    /**
     * 转让群主
     */
    private void transfer() {

        if (TextUtils.isEmpty(ownId)) {
            ToastUtil.get().showToast(this, "请选择新群主");
            return;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("groupId", mGroupId);
            jsonObject.put("create_user", ownId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //转让群主
        EanfangHttp.post(UserApi.POST_UPDATA_GROUP)
                .upJson(jsonObject)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (JSONObject) -> {
                    ToastUtil.get().showToast(this, "转让成功");
                    setResult(RESULT_OK);
                    finish();
                }));

    }
}
