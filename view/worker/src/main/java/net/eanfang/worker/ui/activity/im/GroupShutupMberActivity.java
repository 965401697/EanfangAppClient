package net.eanfang.worker.ui.activity.im;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.UserApi;
import com.eanfang.biz.model.entity.SysGroupUserEntity;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.ToastUtil;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupShutupMberActivity extends BaseWorkerActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private ShutupMberAdapter mShutupMberAdapter;


    private ArrayList<SysGroupUserEntity> mFriendListBeanArrayList;
    private String mGroupId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_group_shutup_mber);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("禁言成员");

        setLeftBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("list", mFriendListBeanArrayList);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        mFriendListBeanArrayList = (ArrayList<SysGroupUserEntity>) getIntent().getSerializableExtra("list");
        mGroupId = getIntent().getStringExtra("groupId");

        initViews();
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mShutupMberAdapter = new ShutupMberAdapter(R.layout.item_transfer_own);//标志位 就是为了显示checkbox
        mShutupMberAdapter.bindToRecyclerView(mRecyclerView);

        mShutupMberAdapter.setNewData(mFriendListBeanArrayList);

        mShutupMberAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, final View view, int position) {
                if (view.getId() == R.id.rb_checked) {
                    SysGroupUserEntity bean = (SysGroupUserEntity) adapter.getData().get(position);
                    if (bean.getStatus() == 0) {
                        shutupOne(bean.getAccId().toString());
                        bean.setStatus(1);
                    } else {
                        offShutupOne(bean.getAccId().toString());
                        bean.setStatus(0);
                    }
                    mShutupMberAdapter.notifyItemChanged(position);
                }
            }
        });
    }

    /**
     * 禁言一个好友
     */
    private void shutupOne(String accId) {
        //禁言
        EanfangHttp.post(UserApi.POST_GROUP_GAG)
                .params("groupId", mGroupId)
                .params("accId", accId)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (JSONObject) -> {
                    ToastUtil.get().showToast(this, "禁言成功");
                }));

    }

    private void offShutupOne(String accId) {
        //解禁
        EanfangHttp.post(UserApi.POST_GROUP_NOGAG)
                .params("groupId", mGroupId)
                .params("accId", accId)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (JSONObject) -> {
                    ToastUtil.get().showToast(this, "解禁成功");
                }));

    }
}
