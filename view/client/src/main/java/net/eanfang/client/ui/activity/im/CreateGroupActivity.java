package net.eanfang.client.ui.activity.im;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.kit.loading.LoadKit;
import com.eanfang.base.kit.picture.IPictureCallBack;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.GroupCreatBean;
import com.eanfang.biz.model.bean.TemplateBean;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.ToastUtil;
import com.eanfang.util.compound.CompoundHelper;
import com.luck.picture.lib.entity.LocalMedia;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.activity.worksapce.oa.workreport.OAPersonAdaptet;
import net.eanfang.client.ui.base.BaseClienActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hutool.core.util.StrUtil;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Group;

public class CreateGroupActivity extends BaseClienActivity {

    @BindView(R.id.iv_group_pic)
    CircleImageView ivGroupPic;
    @BindView(R.id.tv_group_name)
    public EditText etGroupName;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.rv_team)
    RecyclerView rvTeam;
    @BindView(R.id.btn_create)
    Button btnCreate;


    private Dialog dialog;
    private String path;
    private List<TemplateBean.Preson> presonList = new ArrayList<>();
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object message = msg.obj;

            path = (String) message;
            if (!TextUtils.isEmpty(path)) {
                imgKey = "im/group/" + StrUtil.uuid() + ".png";
                dialog.dismiss();
                //头像上传成功后  提交数据
                SDKManager.ossKit(CreateGroupActivity.this).asyncPutImage(imgKey, path,(isSuccess) -> {
                    submit();
                });
            }
        }
    };
    public String groupName = "";
    public String imgKey = "";
    public String locationUrl = "";

    private OAPersonAdaptet oaPersonAdaptet;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tvNum.setText(oaPersonAdaptet.getData().size() + "人");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_create_group);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("设置群组信息");
        setLeftBack(v -> giveUp());

        Bundle bundle = getIntent().getExtras();
        presonList = (List<TemplateBean.Preson>) bundle.getSerializable("list");

        groupName = getIntent().getStringExtra("groupName");
        imgKey = getIntent().getStringExtra("imgKey");
        locationUrl = getIntent().getStringExtra("locationPortrait");


        initViews();
        NewSelectIMContactActivity.transactionActivities.add(this);


    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    private void initViews() {
        if (!TextUtils.isEmpty(groupName)) {
            etGroupName.setText(groupName);
        }

        if (!TextUtils.isEmpty(locationUrl)) {
            GlideUtil.intoImageView(this,locationUrl,ivGroupPic);
        }

        tvNum.setText(presonList.size() + "人");

        rvTeam.setLayoutManager(new GridLayoutManager(this, 5));


        oaPersonAdaptet = new OAPersonAdaptet(this, new ArrayList<TemplateBean.Preson>(), 6, mHandler);//说明是创建群组


        rvTeam.setAdapter(oaPersonAdaptet);

        oaPersonAdaptet.setNewData(presonList);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(imgKey)) {
                    submit();
                } else {
                    compoundPhoto();
                }
            }
        });
        dialog = LoadKit.dialog(this);
    }

    @OnClick(R.id.ll_header)
    public void onViewClicked() {
        headImage();
    }
    private void headImage() {
        SDKManager.getPicture().create(this).takePhoto(iPictureCallBack);
    }
    IPictureCallBack iPictureCallBack = new IPictureCallBack() {
        @Override
        public void onSuccess(List<LocalMedia> list) {
            imgKey = "im/group/CUSTOM_" + StrUtil.uuid() + ".png";
            locationUrl = "file://" +list.get(0).getPath();
            GlideUtil.intoImageView(CreateGroupActivity.this,"file://" + list.get(0).getPath(),ivGroupPic);
            SDKManager.ossKit(CreateGroupActivity.this).asyncPutImage(imgKey,list.get(0).getPath(),(isSuccess) -> {});
        }
    };
    /**
     * 合唱头像
     */
    private void compoundPhoto() {
        if (oaPersonAdaptet.getData().size() <= 1) {
            ToastUtil.get().showToast(this, "最少选两个好友");
            return;
        }


        dialog.show();
        ArrayList<String> userIconList = new ArrayList<>();

        if (oaPersonAdaptet.getData().size() > 3) {

            for (int i = 0; i < 3; i++) {
                userIconList.add(oaPersonAdaptet.getData().get(i).getProtraivat());
            }

        } else {
            for (TemplateBean.Preson preson : oaPersonAdaptet.getData()) {
                userIconList.add(preson.getProtraivat());
            }
        }

        if (!userIconList.contains(ClientApplication.get().getLoginBean().getAccount().getAvatar())) {
            userIconList.add(ClientApplication.get().getLoginBean().getAccount().getAvatar());//添加自己的头像
        }
        //防止创建群组的人员一个头像图片都没有 造成的空指针崩溃
        if (userIconList.size() == 0) {
            ToastUtil.get().showToast(CreateGroupActivity.this, "请先上传群头像");
            return;
        }
        //合成头像

        CompoundHelper.getInstance().sendBitmap(this, handler, userIconList);//生成图片
    }

    private void handleNames(int len) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < len; i++) {
            if (i == 0) {
                stringBuffer.append(oaPersonAdaptet.getData().get(i).getName());
            } else if (i == len - 1) {
                if (i < 2) {
                    stringBuffer.append("," + oaPersonAdaptet.getData().get(i).getName());
                } else {
                    stringBuffer.append("," + oaPersonAdaptet.getData().get(i).getName() + "...等");
                }
            } else {
                stringBuffer.append("," + oaPersonAdaptet.getData().get(i).getName());
            }
        }
        groupName = ClientApplication.get().getLoginBean().getAccount().getNickName() + "," + stringBuffer.toString();
    }

    /**
     * 提交群组的名字
     */
    private void submit() {

        if (oaPersonAdaptet.getData().size() <= 1) {
            ToastUtil.get().showToast(this, "最少选两个好友");
            return;
        }

        if (TextUtils.isEmpty(etGroupName.getText().toString().trim())) {
            if (oaPersonAdaptet.getData().size() > 3) {
                handleNames(3);
            } else {
                handleNames(oaPersonAdaptet.getData().size());
            }
        } else {
            groupName = etGroupName.getText().toString().trim();
        }


        ArrayList<String> list = new ArrayList<>();
        for (
                TemplateBean.Preson p : oaPersonAdaptet.getData())

        {
            list.add(p.getId());
        }
        if (!list.contains(String.valueOf(ClientApplication.get().

                getAccId())))

        {
            list.add(String.valueOf(ClientApplication.get().getAccId()));
        }

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();

        JSONArray array = new JSONArray();
        try

        {
            for (String s : list) {
                JSONObject jsonObject3 = new JSONObject();
                jsonObject3.put("accId", s);
                array.put(jsonObject3);
            }

            jsonObject1.put("groupName", groupName);
            jsonObject1.put("headPortrait", imgKey);
            jsonObject.put("sysGroup", jsonObject1);
            jsonObject.put("sysGroupUsers", array);

        } catch (
                JSONException e)

        {
            e.printStackTrace();
        }


        //创建群组
        EanfangHttp.post(UserApi.POST_CREAT_GROUP).
                upJson(jsonObject).execute(new EanfangCallback<GroupCreatBean>(CreateGroupActivity.this, true, GroupCreatBean.class, (bean) -> {
            ToastUtil.get().showToast(CreateGroupActivity.this, "创建成功");
            Group groupInfo = new Group(bean.getRcloudGroupId(), bean.getGroupName(), Uri.parse(BuildConfig.OSS_SERVER + imgKey));
            RongIM.getInstance().refreshGroupInfoCache(groupInfo);

            ClientApplication.get().set(bean.getRcloudGroupId(), bean.getGroupId());
            RongIM.getInstance().startGroupChat(CreateGroupActivity.this, bean.getRcloudGroupId(), bean.getGroupName());
            NewSelectIMContactActivity.transactionActivities.remove(this);
            CreateGroupActivity.this.finish();
        }));

    }

    /**
     * 监听 返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            giveUp();
        }
        return false;
    }

    /**
     * 放弃新建汇报
     */
    private void giveUp() {
        new TrueFalseDialog(this, "系统提示", "是否放弃创建群组？", () -> {
            NewSelectIMContactActivity.transactionActivities.remove(this);
            finish();
        }).showDialog();
    }


}
