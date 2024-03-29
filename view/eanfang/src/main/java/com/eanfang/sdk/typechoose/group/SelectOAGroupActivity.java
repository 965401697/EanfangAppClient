package com.eanfang.sdk.typechoose.group;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.R;
import com.eanfang.R2;
import com.eanfang.apiservice.UserApi;
import com.eanfang.biz.model.entity.AccountEntity;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.TemplateBean;
import com.eanfang.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

public class SelectOAGroupActivity extends BaseActivity {

    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;

    private List<TemplateBean.Preson> mGroupList = new ArrayList<>();
    private final int REQUSET_OA_CODE = 101;
    private SelectOAGroupAdapter selectOAGroupAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_select_oagroup);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("选择联系人");
        setLeftBack();
        startTransaction(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        initViews();

        RongIM.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
            @Override
            public void onSuccess(List<Conversation> conversations) {

                if (conversations != null && conversations.size() > 0) {
                    List<String> list = new ArrayList<>();

                    for (Conversation s : conversations) {
                        if (s.getConversationType() == Conversation.ConversationType.PRIVATE) {
                            list.add(s.getTargetId());
                        } else {
                            TemplateBean.Preson preson = new TemplateBean.Preson();
                            preson.setName(s.getConversationTitle());
                            preson.setId(s.getTargetId());
                            preson.setProtraivat(s.getPortraitUrl());
                            mGroupList.add(preson);
                        }
                    }


                    if (list.size() > 0) {
                        initData(list);
                    } else {
                        selectOAGroupAdapter.setNewData(mGroupList);
                    }


                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        }, Conversation.ConversationType.GROUP, Conversation.ConversationType.PRIVATE);


    }

    private void initViews() {
        selectOAGroupAdapter = new SelectOAGroupAdapter(R.layout.item_select_oa_group);
        selectOAGroupAdapter.bindToRecyclerView(recyclerView);
        selectOAGroupAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TemplateBean.Preson preson = (TemplateBean.Preson) adapter.getData().get(position);
                Intent intent = new Intent();
                intent.putExtra("bean", preson);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }


    private void initData(List<String> lists) {

        EanfangHttp.post(UserApi.GET_ACC_INFO)
                .upJson(JSON.toJSONString(lists))
                .execute(new EanfangCallback<AccountEntity>(this, true, AccountEntity.class, true, (list) -> {

                    for (AccountEntity accountEntity : list) {

                        TemplateBean.Preson preson = new TemplateBean.Preson();
                        preson.setName(accountEntity.getNickName());
                        preson.setId(String.valueOf(accountEntity.getAccId()));
                        preson.setProtraivat(accountEntity.getAvatar());
                        mGroupList.add(preson);

                    }

                    selectOAGroupAdapter.setNewData(mGroupList);
                }));
    }

    @OnClick(R2.id.ll_my_friends)
    public void onViewClicked() {
        Intent intent = new Intent(this, MyGroupsListActivity.class);
        intent.putExtra("isVisible", true);
        startActivity(intent);
    }

}
