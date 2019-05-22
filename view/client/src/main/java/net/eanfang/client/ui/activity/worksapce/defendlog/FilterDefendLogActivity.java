package net.eanfang.client.ui.activity.worksapce.defendlog;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.eanfang.model.TemplateBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.sdk.selecttime.SelectTimeDialogFragment;
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

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author guanluocang
 * @data 2018/12/27
 * @description 筛选布防日志
 */

public class FilterDefendLogActivity extends BaseActivity implements SelectTimeDialogFragment.SelectTimeListener, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerViewPersonal;
    @BindView(R.id.tv_start)
    TextView tvCheckTime;
    @BindView(R.id.ll_start)
    LinearLayout llCheckTime;
    @BindView(R.id.tv_end)
    TextView tvUpdateTime;
    @BindView(R.id.ll_end)
    LinearLayout llUpdateTime;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.tv_sure)
    TextView tvSure;

    @BindView(R.id.rb_device_read)
    RadioButton rbDeviceRead;
    @BindView(R.id.rb_device_unread)
    RadioButton rbDeviceUnread;
    @BindView(R.id.rg_status)
    RadioGroup rgStatus;
    /**
     * 面谈对象筛选
     */
    @BindView(R.id.recycler_talk)
    RecyclerView recyclerTalk;
    @BindView(R.id.ll_talk)
    LinearLayout llTalk;

    public int mFlag;//人员选择器的标志位


    private OAPersonAdaptet oaPersonAdaptet;

    private ArrayList<TemplateBean.Preson> newPresonList = new ArrayList<>();
    /**
     * 面谈对象
     */
    private OAPersonAdaptet oaPersonAdaptet_talk;

    private ArrayList<TemplateBean.Preson> newPresonList_talk = new ArrayList<>();


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
     * 发送人
     */
    private String mPersonal = "";
    /**
     * 面谈对象
     */
    private String mPersonal_talk = "";

    /**
     * 面谈对象筛选
     */
    private String mModle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_defend_log);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("筛选");
        setLeftBack();
        mType = getIntent().getIntExtra("type", 0);
        mModle = getIntent().getStringExtra("modle");
        if (mType == 0) {// 创建
            mPersonal = "assigneeUserId  ";
            mPersonal_talk = "assigneeUserId  ";
        } else {// 处理
            mPersonal = "createUserId";
            mPersonal_talk = "createUserId";
        }
        recyclerViewPersonal.setLayoutManager(new GridLayoutManager(this, 5));
        oaPersonAdaptet = new OAPersonAdaptet(this, new ArrayList<TemplateBean.Preson>(), 2);
        recyclerViewPersonal.setAdapter(oaPersonAdaptet);

        recyclerTalk.setLayoutManager(new GridLayoutManager(this, 5));
        oaPersonAdaptet_talk = new OAPersonAdaptet(this, new ArrayList<TemplateBean.Preson>(), 1);
        recyclerTalk.setAdapter(oaPersonAdaptet_talk);

        rgStatus.setOnCheckedChangeListener(this);
        if (mModle != null) {
            if (mModle.equals("talk")) {
                llTalk.setVisibility(View.VISIBLE);
            } else if (mModle.equals("transfer")) {
                rbDeviceRead.setText("完成交接");
                rbDeviceUnread.setText("待确认");
            }
        } else {
            llTalk.setVisibility(View.GONE);
        }

    }

    @OnClick({R.id.ll_start, R.id.ll_end, R.id.tv_cancle, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_start:
                mCurrentText = tvCheckTime;
                new SelectTimeDialogFragment().show(getSupportFragmentManager(), R.string.app_name + "");
                break;
            case R.id.ll_end:
                mCurrentText = tvUpdateTime;
                new SelectTimeDialogFragment().show(getSupportFragmentManager(), R.string.app_name + "");
                break;
            case R.id.tv_cancle:
                finishSelf();
                break;
            case R.id.tv_sure:
                sub();
                break;
            default:
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
        if (newPresonList_talk.size() != 0) {

            if (newPresonList_talk.size() > 1) {
                List<String> idList = new ArrayList<>();

                for (TemplateBean.Preson p : newPresonList_talk) {
                    idList.add(p.getUserId());
                }
                queryEntry.getIsIn().put(mPersonal_talk, idList);
            } else {
                TemplateBean.Preson p = newPresonList_talk.get(0);
                queryEntry.getEquals().put(mPersonal_talk, p.getUserId());
            }
        }

        if (!TextUtils.isEmpty(tvCheckTime.getText().toString().trim())) {

            if (queryEntry == null) {
                queryEntry = new QueryEntry();
            }

            queryEntry.getGtEquals().put("createTime", tvCheckTime.getText().toString().trim());
        }
        if (!TextUtils.isEmpty(tvUpdateTime.getText().toString().trim())) {

            if (queryEntry == null) {
                queryEntry = new QueryEntry();
            }

            queryEntry.getLtEquals().put("createTime", tvUpdateTime.getText().toString().trim());
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
            if (mFlag == 1) {
                Set hashSet_talk = new HashSet();
                hashSet_talk.addAll(oaPersonAdaptet_talk.getData());
                hashSet_talk.addAll(presonList);
                if (newPresonList_talk.size() > 0) {
                    newPresonList_talk.clear();
                }
                newPresonList_talk.addAll(hashSet_talk);
                oaPersonAdaptet_talk.setNewData(newPresonList_talk);
            } else if (mFlag == 2) {
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
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_device_read://("已读",1)
                mStatus = 1;
                break;
            case R.id.rb_device_unread:// "未读",0
                mStatus = 0;
                break;
            default:
                break;

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

    public void setFlag(int flag) {
        this.mFlag = flag;
    }
}
