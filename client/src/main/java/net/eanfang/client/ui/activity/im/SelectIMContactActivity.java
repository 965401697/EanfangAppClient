package net.eanfang.client.ui.activity.im;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.GroupCreatBean;
import com.eanfang.model.TemplateBean;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.ui.activity.SelectOrganizationActivity;
import com.eanfang.util.DialogUtil;
import com.eanfang.util.StringUtils;
import com.eanfang.util.ToastUtil;
import com.eanfang.util.UuidUtil;
import com.eanfang.util.compound.CompoundHelper;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;

/**
 * 选择联系人
 */
public class SelectIMContactActivity extends BaseClientActivity {


    @BindView(R.id.rl_selected)
    RelativeLayout rlSelected;
    @BindView(R.id.recycler_view_hori)
    RecyclerView recyclerViewHori;
    private HeaderIconAdapter mHeaderIconAdapter;
    private Bundle bundle;

    private List<TemplateBean.Preson> newPresonList = new ArrayList<>();
    private Dialog dialog;
    private String imgKey;
    private String path;
    private String groupName;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object message = msg.obj;
            if (message == null) {
                ToastUtil.get().showToast(SelectIMContactActivity.this, "发送成功");
            } else {
                path = (String) message;
                if (!TextUtils.isEmpty(path)) {
                    imgKey = UuidUtil.getUUID() + ".png";
                    creatGroup();
                }
            }
        }
    };

    private int mFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_im_contact);
        ButterKnife.bind(this);
        supprotToolbar();
        setTitle("选择联系人");

        dialog = DialogUtil.createLoadingDialog(SelectIMContactActivity.this);

        bundle = getIntent().getExtras();

        mFlag = getIntent().getIntExtra("flag", 0);

        if (mFlag == 1) {
            //创建群组
            setRightTitle("创建");
            findViewById(R.id.rl_my_group).setVisibility(View.GONE);
        } else {
            setRightTitle("发送");
        }


        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFlag == 1) {
                    compoundPhoto();
                } else {
                    //发送分享的群组
                    handler.post(runnable);//立马发送
                }
            }
        });

        findViewById(R.id.rl_organization).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectIMContactActivity.this, SelectOrganizationActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.ll_my_friends).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("net.eanfang.client.action.SELECTFRIENDS");
                intent.putExtra("flag", 3);
                startActivity(intent);
            }
        });
        findViewById(R.id.rl_my_group).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("net.eanfang.client.action.MYGROUPLIST");
                intent.putExtra("isVisible", true);
                startActivity(intent);
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

        if (presonList.size() > 0) {

            if (rlSelected.getVisibility() != View.VISIBLE) {
                rlSelected.setVisibility(View.VISIBLE);
            }
            Set hashSet = new HashSet();
            hashSet.addAll(mHeaderIconAdapter.getData());
            hashSet.addAll(presonList);

            if (newPresonList.size() > 0) {
                newPresonList.clear();
            }
            newPresonList.addAll(hashSet);
            mHeaderIconAdapter.setNewData(newPresonList);
        }

    }

    /**
     * 循环发送
     */
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            //要做的事情，这里再次调用此Runnable对象，以实现每一秒实现一次的定时器操作
            if (!dialog.isShowing()) {
                dialog.show();
            }
            if (newPresonList.size() > 5) {

                handler.postDelayed(runnable, 1500);
                List<TemplateBean.Preson> newRongIdLists = new ArrayList<>();

                newRongIdLists.addAll(newPresonList);
                List<TemplateBean.Preson> newLists = newPresonList.subList(0, 5);

                for (TemplateBean.Preson preson : newLists) {
                    newRongIdLists.remove(preson);
                    sendCheckedMsg(preson.getId());
                }
                newPresonList = newRongIdLists;

            } else if (newPresonList.size() <= 5) {
                for (TemplateBean.Preson preson : newPresonList) {
                    sendCheckedMsg(preson.getId());
                }
                handler.sendEmptyMessage(1);
                handler.removeCallbacks(runnable);
                dialog.dismiss();
                SelectIMContactActivity.this.finishSelf();
            }

        }
    };


    class HeaderIconAdapter extends BaseQuickAdapter<TemplateBean.Preson, BaseViewHolder> {

        public HeaderIconAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, TemplateBean.Preson item) {
            ((ImageView) helper.getView(R.id.iv_user_header)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + item.getProtraivat()));
        }
    }

    /**
     * 发送分享
     *
     * @param id
     */
    private void sendCheckedMsg(String id) {
        if (bundle != null) {
            String shareType = bundle.getString("shareType");//区分消息的类型
            Conversation.ConversationType conversationType;

            CustomizeMessage customizeMessage = new CustomizeMessage();
            customizeMessage.setPicUrl(bundle.getString("picUrl"));
            customizeMessage.setWorkerName(bundle.getString("workerName"));
            customizeMessage.setCreatTime(bundle.getString("creatTime"));
            customizeMessage.setOrderNum(bundle.getString("orderNum"));
            customizeMessage.setOrderId(bundle.getString("id"));
            customizeMessage.setStatus(bundle.getString("status"));
            customizeMessage.setShareType(bundle.getString("shareType"));
            if (isInteger(id)) {
                conversationType = Conversation.ConversationType.PRIVATE;
            } else {
                conversationType = Conversation.ConversationType.GROUP;
            }

            RongIM.getInstance().sendMessage(conversationType, id, customizeMessage, "报修订单", "报修订单", new RongIMClient.SendMessageCallback() {
                @Override
                public void onError(Integer integer, RongIMClient.ErrorCode errorCode) {
                    Log.e("zzw", "发送失败=" + integer + "=" + errorCode);
                }

                @Override
                public void onSuccess(Integer integer) {
                    Log.e("zzw", "发送成功=" + integer);
                }
            });
        }
    }

    /**
     * 合唱头像
     */
    private void compoundPhoto() {

        if (newPresonList.size() <= 1) {
            ToastUtil.get().showToast(this, "最少选两个好友");
            return;
        }
        dialog.show();
        ArrayList<String> userIconList = new ArrayList<>();

        if (newPresonList.size() > 3) {

            for (int i = 0; i < 3; i++) {
                userIconList.add(newPresonList.get(i).getProtraivat());
            }
            handleNames(3);
        } else {
            for (TemplateBean.Preson preson : newPresonList) {
                userIconList.add(preson.getProtraivat());
            }
            handleNames(newPresonList.size());
        }


        userIconList.add(EanfangApplication.get().getUser().getAccount().getAvatar());//添加自己的头像
        //合成头像

        CompoundHelper.getInstance().sendBitmap(this, handler, userIconList);//生成图片
    }

    private void handleNames(int len) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < len; i++) {
            if (i == 0) {
                stringBuffer.append(newPresonList.get(i).getName());
            } else if (i == len - 1) {
                if (i < 2) {
                    stringBuffer.append("," + newPresonList.get(i).getName());
                } else {
                    stringBuffer.append("," + newPresonList.get(i).getName() + "...等");
                }
            } else {
                stringBuffer.append("," + newPresonList.get(i).getName());
            }
        }
        groupName = EanfangApplication.get().getUser().getAccount().getNickName() + "," + stringBuffer.toString();
    }

    /**
     * 创建群组
     */
    private void creatGroup() {

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();

        JSONArray array = new JSONArray();
        try {
            for (TemplateBean.Preson preson : newPresonList) {
                JSONObject jsonObject3 = new JSONObject();
                jsonObject3.put("accId", preson.getId());
                array.put(jsonObject3);
            }
            //把自己的id 加进去
            JSONObject jsonObject3 = new JSONObject();
            jsonObject3.put("accId", EanfangApplication.get().getAccId());
            array.put(jsonObject3);

            jsonObject1.put("groupName", groupName);
            jsonObject1.put("headPortrait", imgKey);
            jsonObject.put("sysGroup", jsonObject1);
            jsonObject.put("sysGroupUsers", array);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //头像上传成功后  提交数据
        OSSUtils.initOSS(SelectIMContactActivity.this).asyncPutImage(imgKey, path, new OSSCallBack(SelectIMContactActivity.this, false) {

            @Override
            public void onOssSuccess() {
                super.onOssSuccess();


                //创建群组
                EanfangHttp.post(UserApi.POST_CREAT_GROUP)
                        .upJson(jsonObject)
                        .execute(new EanfangCallback<GroupCreatBean>(SelectIMContactActivity.this, false, GroupCreatBean.class) {
                            @Override
                            public void onSuccess(GroupCreatBean bean) {
                                super.onSuccess(bean);
                                ToastUtil.get().showToast(SelectIMContactActivity.this, "创建成功");
                                Group groupInfo = new Group(bean.getRcloudGroupId(), bean.getGroupName(), Uri.parse(com.eanfang.BuildConfig.OSS_SERVER + imgKey));
                                RongIM.getInstance().refreshGroupInfoCache(groupInfo);

                                EanfangApplication.get().set(bean.getRcloudGroupId(), bean.getGroupId());
                                RongIM.getInstance().startGroupChat(SelectIMContactActivity.this, bean.getRcloudGroupId(), bean.getGroupName());
                                dialog.dismiss();
                                SelectIMContactActivity.this.finish();
                            }

                            @Override
                            public void onFail(Integer code, String message, com.alibaba.fastjson.JSONObject jsonObject) {
                                super.onFail(code, message, jsonObject);
                                ToastUtil.get().showToast(SelectIMContactActivity.this, "创建失败");
                                dialog.dismiss();
                            }
                        });

            }
        });

    }

    /**
     * 判断字符串是不是数字
     *
     * @param str
     * @return
     */

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
