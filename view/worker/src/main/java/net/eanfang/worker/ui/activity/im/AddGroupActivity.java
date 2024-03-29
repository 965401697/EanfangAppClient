package net.eanfang.worker.ui.activity.im;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.apiservice.UserApi;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.biz.model.bean.GroupDetailBean;
import com.eanfang.biz.model.entity.SysGroupUserEntity;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.GroupsBean;

import com.eanfang.util.GlideUtil;
import com.eanfang.util.ToastUtil;
import com.eanfang.util.compound.CompoundHelper;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hutool.core.util.StrUtil;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Group;

/**
 * 进群的二维码
 */
public class AddGroupActivity extends BaseWorkerActivity {

    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_mber)
    TextView tvMber;
    private String mGroupId;
    private String mRYGroupId;
    private String mTitle;
    private String mPath;
    private ArrayList<String> mUserIconList = new ArrayList<String>();
    private boolean flag = true;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String path = (String) msg.obj;

            if (!TextUtils.isEmpty(path)) {
                mPath = path;


            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_group);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        mGroupId = getIntent().getStringExtra("groupId");
        setTitle("群组信息");
        setLeftBack();


        EanfangHttp.post(UserApi.POST_GET_GROUP)
                .params("accId", WorkerApplication.get().getAccId())
                .execute(new EanfangCallback<GroupsBean>(this, true, GroupsBean.class, true, (list) -> {
                    if (list.size() > 0) {
                        for (GroupsBean bean : list) {
                            if (Integer.parseInt(mGroupId) == bean.getGroupId()) {
                                flag = false;
                                AddGroupActivity.this.finish();
                                ToastUtil.get().showToast(AddGroupActivity.this, "你已经在群里了，亲");
                                break;
                            } else {
                                flag = true;
                            }
                        }
                    }
                    if (flag) {
                        initData();
                    }
                }));
    }

    @OnClick(R.id.btn_add)
    public void onViewClicked() {
        join();
    }

    private void initData() {

        EanfangHttp.post(UserApi.POST_GROUP_DETAIL)
                .params("groupId", mGroupId)

                .execute(new EanfangCallback<GroupDetailBean>(this, true, GroupDetailBean.class, (bean) -> {

                    if (bean.getGroup() == null) {
                        ToastUtil.get().showToast(AddGroupActivity.this, "群二维码有误");
                        finishSelf();
                        return;
                    }

                    for (SysGroupUserEntity b : bean.getList()) {
                        mUserIconList.add(b.getAccountEntity().getAvatar());
                    }

                    mUserIconList.add(WorkerApplication.get().getLoginBean().getAccount().getAvatar());

                    CompoundHelper.getInstance().sendBitmap(this, handler, mUserIconList);//生成图片
                    mTitle = bean.getGroup().getGroupName();
                    mRYGroupId = bean.getGroup().getRcloudGroupId();
                    GlideUtil.intoImageView(this,Uri.parse(com.eanfang.BuildConfig.OSS_SERVER + bean.getGroup().getHeadPortrait()),ivIcon);
                    tvName.setText(bean.getGroup().getGroupName());
                    tvMber.setText("成员" + bean.getList().size() + "人");
                }));
    }

    /**
     * 添加成员
     */
    private void join() {
        //把生成的图片 传到服务器 更新本地群组头像
        if (!TextUtils.isEmpty(mPath)) {
            String inageKey = StrUtil.uuid() + ".png";
            SDKManager.ossKit(this).asyncPutImage(inageKey,mPath,(isSuccess) -> {
                updataGroupInfo(mTitle, inageKey, "", "");
            });
        }


        JSONArray array = new JSONArray();
        JSONObject object = null;

        object = new JSONObject();
        try {
            object.put("accId", WorkerApplication.get().getAccId());
            object.put("groupId", mGroupId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        array.put(object);

        EanfangHttp.post(UserApi.POST_GROUP_JOIN)
                .upJson(array)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (json) -> {
                    ToastUtil.get().showToast(AddGroupActivity.this, "加入成功");
                    finishSelf();
                }));
    }


    /**
     * 更新群组信息
     */
    public void updataGroupInfo(String title, String imgKey, String transfer, String notice) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("groupId", mGroupId);

            if (!TextUtils.isEmpty(title)) {
                jsonObject.put("groupName", title);
            }
            if (!TextUtils.isEmpty(imgKey)) {
                jsonObject.put("headPortrait", imgKey);
            }
            if (!TextUtils.isEmpty(transfer)) {
                jsonObject.put("create_user", transfer);
            }
            if (!TextUtils.isEmpty(notice)) {
                jsonObject.put("notice", notice);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //创建群组
        EanfangHttp.post(UserApi.POST_UPDATA_GROUP)
                .upJson(jsonObject)
                .execute(new EanfangCallback<JSONObject>(AddGroupActivity.this, false, JSONObject.class, (JSONObject) -> {
                    Group groupInfo = new Group(mRYGroupId, title, Uri.parse(com.eanfang.BuildConfig.OSS_SERVER + imgKey));
                    RongIM.getInstance().refreshGroupInfoCache(groupInfo);
                }));
    }

}