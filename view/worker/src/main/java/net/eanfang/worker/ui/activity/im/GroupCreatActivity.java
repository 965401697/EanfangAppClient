package net.eanfang.worker.ui.activity.im;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.GroupCreatBean;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.ui.base.BaseActivityWithTakePhoto;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.ToastUtil;
import com.eanfang.util.UuidUtil;
import com.eanfang.util.compound.CompoundHelper;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Group;

public class GroupCreatActivity extends BaseActivityWithTakePhoto {

    @BindView(R.id.et_group_name)
    EditText etGroupName;
    @BindView(R.id.btn_created)
    Button btnCreated;
    @BindView(R.id.iv_icon)
    SimpleDraweeView ivIcon;
    private final int HEADER_PIC = 107;
    private String imgKey;
    private String path;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            path = (String) msg.obj;

            if (!TextUtils.isEmpty(path)) {
                ivIcon.setImageURI("file://" + path);

                imgKey = "im/group/" + UuidUtil.getUUID() + ".png";

            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_creat);
        ButterKnife.bind(this);
        setTitle("创建群组");
        setLeftBack();

        ArrayList<String> userIconList = getIntent().getStringArrayListExtra("userIconList");
        userIconList.add(WorkerApplication.get().getLoginBean().getAccount().getAvatar());//添加自己的头像
        //合成头像

        CompoundHelper.getInstance().sendBitmap(this, handler, userIconList);//生成图片
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
        list.add(String.valueOf(WorkerApplication.get().getAccId()));
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
        //头像上传成功后提交数据
        OSSUtils.initOSS(GroupCreatActivity.this).asyncPutImage(imgKey, path, new OSSCallBack(GroupCreatActivity.this, false) {

            @Override
            public void onOssSuccess() {
                super.onOssSuccess();

                //创建群组
                EanfangHttp.post(UserApi.POST_CREAT_GROUP)
                        .upJson(jsonObject)
                        .execute(new EanfangCallback<GroupCreatBean>(GroupCreatActivity.this, true, GroupCreatBean.class, (bean) -> {
                            ToastUtil.get().showToast(GroupCreatActivity.this, "创建成功");
                            Group groupInfo = new Group(bean.getRcloudGroupId(), bean.getGroupName(), Uri.parse(com.eanfang.BuildConfig.OSS_SERVER + imgKey));
                            RongIM.getInstance().refreshGroupInfoCache(groupInfo);

                            WorkerApplication.get().set(bean.getRcloudGroupId(), bean.getGroupId());
                            RongIM.getInstance().startGroupChat(GroupCreatActivity.this, bean.getRcloudGroupId(), bean.getGroupName());
                            GroupCreatActivity.this.finish();
                        }));

            }
        });


    }

    @OnClick(R.id.btn_created)
    public void onViewClicked() {
        submit();

    }

    @OnClick(R.id.iv_icon)
    public void onImageClicked() {
        PermissionUtils.get(GroupCreatActivity.this).getCameraPermission(() -> takePhoto(GroupCreatActivity.this, HEADER_PIC));
    }


    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);

        if (result == null || result.getImage() == null) {
            return;
        }
        TImage image = result.getImage();
        imgKey = "im/group/" + UuidUtil.getUUID() + ".png";

        ivIcon.setImageURI("file://" + image.getOriginalPath());

        OSSUtils.initOSS(this).asyncPutImage(imgKey, image.getOriginalPath(), new OSSCallBack(this, true) {
        });

    }

}
