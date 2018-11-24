package net.eanfang.client.ui.activity.worksapce.oa.check;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.eanfang.model.TemplateBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.ui.fragment.SelectTimeDialogFragment;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.StringUtils;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.oa.OAPersonAdaptet;

import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author guanluocang
 * @data 2018/11/8
 * @description 筛选 设备点检
 */

public class FiltrateCheckActivity extends BaseActivity implements SelectTimeDialogFragment.SelectTimeListener, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.recycler_view_personal)
    RecyclerView recyclerViewPersonal;
    @BindView(R.id.tv_check_time)
    TextView tvCheckTime;
    @BindView(R.id.ll_check_time)
    LinearLayout llCheckTime;
    @BindView(R.id.tv_update_time)
    TextView tvUpdateTime;
    @BindView(R.id.ll_update_time)
    LinearLayout llUpdateTime;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.tv_sure)
    TextView tvSure;

    @BindView(R.id.rb_device_checking)
    RadioButton rbDeviceChecking;
    @BindView(R.id.rb_device_wait)
    RadioButton rbDeviceWait;
    @BindView(R.id.rb_device_again)
    RadioButton rbDeviceAgain;
    @BindView(R.id.rb_device_finfish)
    RadioButton rbDeviceFinfish;
    @BindView(R.id.rg_status)
    RadioGroup rgStatus;
    @BindView(R.id.tv_select_person)
    TextView tvSelectPerson;


    private OAPersonAdaptet oaPersonAdaptet;

    private ArrayList<TemplateBean.Preson> newPresonList = new ArrayList<>();

    private TextView mCurrentText;

    /**
     * 创建  处理  筛选 判断
     */
    private int mType = 0;
    /**
     * 状态选择
     */
    private int mStatus = 100;
    /**
     * 点检人  审核人
     */
    private String mPersonal = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtrate_check);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("筛选");
        setLeftBack();
        mType = getIntent().getIntExtra("type", 0);
        if (mType == 0) {// 创建
            tvSelectPerson.setText("按点检人筛选");
            mPersonal = "assigneeUserId  ";
        } else {// 处理
            tvSelectPerson.setText("按审核人筛选");
            mPersonal = "createUserId";
        }
        recyclerViewPersonal.setLayoutManager(new GridLayoutManager(this, 5));

        oaPersonAdaptet = new OAPersonAdaptet(this, new ArrayList<TemplateBean.Preson>());
        recyclerViewPersonal.setAdapter(oaPersonAdaptet);

        rgStatus.setOnCheckedChangeListener(this);
    }

    @OnClick({R.id.ll_check_time, R.id.ll_update_time, R.id.tv_cancle, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_check_time:
                mCurrentText = tvCheckTime;
                new SelectTimeDialogFragment().show(getSupportFragmentManager(), R.string.app_name + "");
                break;
            case R.id.ll_update_time:
                mCurrentText = tvUpdateTime;
                new SelectTimeDialogFragment().show(getSupportFragmentManager(), R.string.app_name + "");
                break;
            case R.id.tv_cancle:
                finishSelf();
                break;
            case R.id.tv_sure:
                sub();
                break;
        }
    }

    private void sub() {

        QueryEntry queryEntry = new QueryEntry();
        if (mStatus != 100) {
            queryEntry.getEquals().put("status", mStatus + "");
        }
        if (newPresonList.size() != 0) {

            if (newPresonList.size() > 1) {
                List<String> idList = new ArrayList<>();

                for (TemplateBean.Preson p : newPresonList) {
                    idList.add(p.getUserId());
                }
                queryEntry.getIsIn().put(mPersonal, idList);
            } else {
                TemplateBean.Preson p = newPresonList.get(0);
                queryEntry.getEquals().put(mPersonal, p.getUserId());
            }
        }

        if (!TextUtils.isEmpty(tvCheckTime.getText().toString().trim())) {

            if (queryEntry == null) queryEntry = new QueryEntry();

            queryEntry.getEquals().put("createTime", tvCheckTime.getText().toString().trim());
        }
        if (!TextUtils.isEmpty(tvUpdateTime.getText().toString().trim())) {

            if (queryEntry == null) queryEntry = new QueryEntry();

            queryEntry.getEquals().put("changeDeadlineTime", tvUpdateTime.getText().toString().trim());
        }

        Intent intent = new Intent();
        if (queryEntry != null) {
            intent.putExtra("query", queryEntry);
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    @Subscribe
    public void onEvent(List<TemplateBean.Preson> presonList) {

        if (presonList.size() > 0) {


            Set hashSet = new HashSet();
            hashSet.addAll(oaPersonAdaptet.getData());
            hashSet.addAll(presonList);

            if (newPresonList.size() > 0) {
                newPresonList.clear();
            }
            newPresonList.addAll(hashSet);
            oaPersonAdaptet.setNewData(newPresonList);


        }
    }

    /**
     * 时间选择回调
     */
    @Override
    public void getData(String time) {
        if (StringUtils.isEmpty(time) || " ".equals(time)) {
            mCurrentText.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } else {
            mCurrentText.setText(time);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_device_checking:// "点检中",0
                mStatus = 1;
                break;
            case R.id.rb_device_wait://("待审核",1)
                mStatus = 1;// 签退
                break;
            case R.id.rb_device_again://("重新整改",2)
                mStatus = 2;
                break;
            case R.id.rb_device_finfish://("点检完毕",3)
                mStatus = 3;
                break;
            default:
                break;

        }
    }
}
