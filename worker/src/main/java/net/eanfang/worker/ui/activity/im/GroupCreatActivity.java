package net.eanfang.worker.ui.activity.im;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.GroupCreatBean;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.ui.base.BaseActivityWithTakePhoto;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.ToastUtil;
import com.eanfang.util.UuidUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;

import net.eanfang.worker.BuildConfig;
import net.eanfang.worker.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_creat);
        ButterKnife.bind(this);
        setTitle("创建群组");
        setLeftBack();
    }

    /**
     * 提交群组的名字
     */
    private void submit() {
        ArrayList<String> list = getIntent().getStringArrayListExtra("userIdList");
        list.add(String.valueOf(EanfangApplication.get().getAccId()));
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
        //创建群组
        EanfangHttp.post(UserApi.POST_CREAT_GROUP)
                .upJson(jsonObject)
                .execute(new EanfangCallback<GroupCreatBean>(GroupCreatActivity.this, true, GroupCreatBean.class, (bean) -> {
                    ToastUtil.get().showToast(GroupCreatActivity.this, "创建成功");
                    Group groupInfo = new Group(bean.getRcloudGroupId(), bean.getGroupName(), Uri.parse(com.eanfang.BuildConfig.OSS_SERVER + imgKey));
                    RongIM.getInstance().refreshGroupInfoCache(groupInfo);

                    EanfangApplication.get().set(bean.getRcloudGroupId(), bean.getGroupId());
                    RongIM.getInstance().startGroupChat(GroupCreatActivity.this, bean.getRcloudGroupId(), bean.getGroupName());
                    GroupCreatActivity.this.finish();
                }));
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
        imgKey = UuidUtil.getUUID() + ".png";

        ivIcon.setImageURI("file://" + image.getOriginalPath());

        OSSUtils.initOSS(this).asyncPutImage(imgKey, image.getOriginalPath(), new OSSCallBack(this, true) {
        });

    }

}
