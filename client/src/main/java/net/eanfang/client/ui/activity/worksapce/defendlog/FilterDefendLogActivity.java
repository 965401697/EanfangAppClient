package net.eanfang.client.ui.activity.worksapce.defendlog;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.eanfang.model.TemplateBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.ui.fragment.SelectTimeDialogFragment;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.oa.OAPersonAdaptet;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author guanluocang
 * @data 2018/12/27
 * @description 筛选布防日志
 */

public class FilterDefendLogActivity extends BaseActivity implements SelectTimeDialogFragment.SelectTimeListener, RadioGroup.OnCheckedChangeListener {

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

    @BindView(R.id.tv_select_person)
    TextView tvSelectPerson;
    @BindView(R.id.rb_device_read)
    RadioButton rbDeviceRead;
    @BindView(R.id.rb_device_unread)
    RadioButton rbDeviceUnread;
    @BindView(R.id.rg_status)
    RadioGroup rgStatus;


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
     * 发送人
     */
    private String mPersonal = "";

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
        if (mType == 0) {// 创建
            mPersonal = "assigneeUserId  ";
        } else {// 处理
            mPersonal = "createUserId";
        }
        recyclerViewPersonal.setLayoutManager(new GridLayoutManager(this, 5));

        oaPersonAdaptet = new OAPersonAdaptet(this, new ArrayList<TemplateBean.Preson>());
        recyclerViewPersonal.setAdapter(oaPersonAdaptet);

        rgStatus.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

    }

    @Override
    public void getData(String time) {

    }
}
