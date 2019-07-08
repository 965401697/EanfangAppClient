package com.eanfang.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.R;
import com.eanfang.R2;
import com.eanfang.biz.model.TemplateBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.ToastUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择联系人
 */
public class SelectContactActivity extends BaseActivity {


    @BindView(R2.id.rl_selected)
    RelativeLayout rlSelected;
    @BindView(R2.id.recycler_view_hori)
    RecyclerView recyclerViewHori;
    @BindView(R2.id.tv_sure)
    TextView tvSure;
    private HeaderIconAdapter mHeaderIconAdapter;

    List<TemplateBean.Preson> presonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contact);
        ButterKnife.bind(this);
        supprotToolbar();
        setTitle("选择联系人");

        findViewById(R.id.rl_organization).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectContactActivity.this, SelectOrganizationActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.ll_my_friends).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("net.eanfang.client.action.SELECTFRIENDS");
                intent.putExtra("flag", 1);
                startActivity(intent);
            }
        });
        findViewById(R.id.rl_attention).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent("net.eanfang.client.action.MYGROUPLIST");
//                intent.putExtra("isVisible", true);
//                startActivity(intent);
            }
        });

        initViews();
    }

    private void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewHori.setLayoutManager(linearLayoutManager);
        mHeaderIconAdapter = new HeaderIconAdapter(R.layout.item_header_icon);
        mHeaderIconAdapter.bindToRecyclerView(recyclerViewHori);
    }

    @Subscribe
    public void onEvent(List<TemplateBean.Preson> presonList) {

        if (presonList.size() > 0) {//管理员设置 单选 就一个
            this.presonList = presonList;
        }
    }

    @OnClick(R2.id.tv_sure)
    public void onViewClicked() {
        ToastUtil.get().showToast(this, "待开发");
    }

    class HeaderIconAdapter extends BaseQuickAdapter<TemplateBean.Preson, BaseViewHolder> {

        public HeaderIconAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, TemplateBean.Preson item) {
            GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + item.getProtraivat()),helper.getView(R.id.iv_user_header));
        }
    }

    /**
     * 创建群组
     */
//    private void submit() {
//
//        if (TextUtils.isEmpty(etGroupName.getText().toString().trim())) {
//            ToastUtil.get().showToast(this, "群组名称不能为空");
//            return;
//        }
//
//        if (TextUtils.isEmpty(imgKey)) {
//            ToastUtil.get().showToast(this, "请上传群头像");
//            return;
//        }
//
//        ArrayList<String> list = getIntent().getStringArrayListExtra("userIdList");
//        list.add(String.valueOf(EanfangApplication.get().getAccId()));
//        JSONObject jsonObject = new JSONObject();
//        JSONObject jsonObject1 = new JSONObject();
//
//        JSONArray array = new JSONArray();
//        try {
//            for (String s : list) {
//                JSONObject jsonObject3 = new JSONObject();
//                jsonObject3.put("accId", s);
//                array.put(jsonObject3);
//            }
//
//            jsonObject1.put("groupName", etGroupName.getText().toString().trim());
//            jsonObject1.put("headPortrait", imgKey);
//            jsonObject.put("sysGroup", jsonObject1);
//            jsonObject.put("sysGroupUsers", array);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        //头像上传成功后  提交数据
//       (imgKey, path, new OSSCallBack(SelectContactActivity.this, false) {
//
//            @Override
//            public void onOssSuccess() {
//                super.onOssSuccess();
//
//
//                //创建群组
//                EanfangHttp.post(UserApi.POST_CREAT_GROUP)
//                        .upJson(jsonObject)
//                        .execute(new EanfangCallback<GroupCreatBean>(SelectContactActivity.this, true, GroupCreatBean.class, (bean) -> {
//                            ToastUtil.get().showToast(SelectContactActivity.this, "创建成功");
//                            Group groupInfo = new Group(bean.getRcloudGroupId(), bean.getGroupName(), Uri.parse(com.eanfang.BuildConfig.OSS_SERVER + imgKey));
//                            RongIM.getInstance().refreshGroupInfoCache(groupInfo);
//
//                            EanfangApplication.get().set(bean.getRcloudGroupId(), bean.getGroupId());
//                            RongIM.getInstance().startGroupChat(SelectContactActivity.this, bean.getRcloudGroupId(), bean.getGroupName());
//                            endTransaction(true);
//                        }));
//
//            }
//        });
//
//
//    }
}
