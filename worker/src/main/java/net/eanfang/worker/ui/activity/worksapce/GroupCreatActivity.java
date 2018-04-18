package net.eanfang.worker.ui.activity.worksapce;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.GroupCreatBean;
import com.eanfang.model.GroupsBean;
import com.eanfang.util.ToastUtil;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Group;

public class GroupCreatActivity extends BaseWorkerActivity {

    @BindView(R.id.et_group_name)
    EditText etGroupName;
    @BindView(R.id.btn_created)
    Button btnCreated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_creat);
        ButterKnife.bind(this);
        setTitle("创建群组");
        setLeftBack();
        startTransaction(true);
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
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("accId", s);
                array.put(jsonObject2);
            }

            jsonObject1.put("groupName", etGroupName.getText().toString().trim());
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
                    EanfangApplication.get().set(bean.getRcloudGroupId(), bean.getGroupId());
                    RongIM.getInstance().startGroupChat(GroupCreatActivity.this, bean.getRcloudGroupId(), bean.getGroupName());
                    endTransaction(true);
                }));
    }

    @OnClick(R.id.btn_created)
    public void onViewClicked() {
        submit();
    }
}
