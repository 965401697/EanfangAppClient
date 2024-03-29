package net.eanfang.client.ui.activity.im;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.lifecycle.ViewModel;

import com.eanfang.apiservice.UserApi;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.kit.picture.IPictureCallBack;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.GroupCreatBean;

import com.eanfang.util.GlideUtil;
import com.eanfang.base.kit.rx.RxPerm;
import com.eanfang.util.ToastUtil;
import com.eanfang.util.compound.CompoundHelper;
import com.luck.picture.lib.entity.LocalMedia;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.base.BaseClienActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hutool.core.util.StrUtil;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Group;

public class GroupCreatActivity extends BaseClienActivity {

    @BindView(R.id.et_group_name)
    EditText etGroupName;
    @BindView(R.id.btn_created)
    Button btnCreated;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    private final int HEADER_PIC = 107;
    private String imgKey;
    private String path;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            path = (String) msg.obj;

            if (!TextUtils.isEmpty(path)) {
                GlideUtil.intoImageView(GroupCreatActivity.this,"file://" + path,ivIcon);

                imgKey = "im/group/"+ StrUtil.uuid() + ".png";

            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_group_creat);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("创建群组");
        setLeftBack(true);

        ArrayList<String> userIconList = getIntent().getStringArrayListExtra("userIconList");
        userIconList.add(ClientApplication.get().getLoginBean().getAccount().getAvatar());//添加自己的头像
        //合成头像

        CompoundHelper.getInstance().sendBitmap(this, handler, userIconList);//生成图片
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    /**
     * 提交群组的名字
     */
    private void submit() {

        if (TextUtils.isEmpty(etGroupName.getText().toString().trim())) {
            ToastUtil.get().showToast(this, "群组名称不能为空");
            return;
        }

        if (TextUtils.isEmpty(imgKey)) {
            ToastUtil.get().showToast(this, "请上传群头像");
            return;
        }

        ArrayList<String> list = getIntent().getStringArrayListExtra("userIdList");
        list.add(String.valueOf(ClientApplication.get().getAccId()));
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();

        JSONArray array = new JSONArray();
        try {
            for (String s : list) {
                JSONObject jsonObject3 = new JSONObject();
                jsonObject3.put("accId", s);
                array.put(jsonObject3);
            }

            jsonObject1.put("groupName", etGroupName.getText().toString().trim());
            jsonObject1.put("headPortrait", imgKey);
            jsonObject.put("sysGroup", jsonObject1);
            jsonObject.put("sysGroupUsers", array);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //头像上传成功后  提交数据
        SDKManager.ossKit(this).asyncPutImage(imgKey, path,(isSuccess) -> {
            //创建群组
            EanfangHttp.post(UserApi.POST_CREAT_GROUP)
                    .upJson(jsonObject)
                    .execute(new EanfangCallback<GroupCreatBean>(GroupCreatActivity.this, true, GroupCreatBean.class, (bean) -> {
                        ToastUtil.get().showToast(GroupCreatActivity.this, "创建成功");
                        Group groupInfo = new Group(bean.getRcloudGroupId(), bean.getGroupName(), Uri.parse(com.eanfang.BuildConfig.OSS_SERVER + imgKey));
                        RongIM.getInstance().refreshGroupInfoCache(groupInfo);

                        ClientApplication.get().set(bean.getRcloudGroupId(), bean.getGroupId());
                        RongIM.getInstance().startGroupChat(GroupCreatActivity.this, bean.getRcloudGroupId(), bean.getGroupName());
                        GroupCreatActivity.this.finish();
                    }));
        });
    }

    @OnClick(R.id.btn_created)
    public void onViewClicked() {
        submit();

    }

    @OnClick(R.id.iv_icon)
    public void onImageClicked() {
        RxPerm.get(this).cameraPerm((isSuccess)->imageV());
    }

    private void imageV() {
        SDKManager.getPicture().create(this).takePhoto(iPictureCallBack);
    }

    IPictureCallBack iPictureCallBack = new IPictureCallBack() {
        @Override
        public void onSuccess(List<LocalMedia> list) {
            imgKey = "im/group/"+StrUtil.uuid() + ".png";
            GlideUtil.intoImageView(GroupCreatActivity.this,"file://" + list.get(0).getPath(),ivIcon);
            SDKManager.ossKit(GroupCreatActivity.this).asyncPutImage(imgKey, list.get(0).getPath(),(isSuccess) -> {});
        }
    };


}
