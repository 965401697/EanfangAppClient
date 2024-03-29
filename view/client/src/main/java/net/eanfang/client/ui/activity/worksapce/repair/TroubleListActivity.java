package net.eanfang.client.ui.activity.worksapce.repair;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.eanfang.biz.model.entity.RepairBugEntity;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.adapter.ToRepairAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author guanluocang
 * @data 2019/4/15
 * @description 故障列表
 */

public class TroubleListActivity extends BaseActivity {

    /**
     * 添加故障明细回调
     */
    private final int ADD_TROUBLE_CALLBACK_CODE = 2;
    /**
     * 故障列表
     */
    private List<RepairBugEntity> beanList = new ArrayList<>();
    /**
     * 故障列表
     */
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private ToRepairAdapter evaluateAdapter = null;


    private Long mOwnerOrgId;

    /**
     * 直接添加报修
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_trouble_list);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
        initAdapter();
        initListener();
    }

    private void initView() {
        setTitle("故障列表");
        setRightTitle("添加");
    }

    private void initListener() {
        setRightTitleOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("beanList", (Serializable) beanList);
            bundle.putBoolean("fromTroubleList", true);
            JumpItent.jump(this, AddTroubleActivity.class, bundle, ADD_TROUBLE_CALLBACK_CODE);
        });
    }

    private void initAdapter() {
        evaluateAdapter = new ToRepairAdapter(R.layout.item_trouble, beanList);
        evaluateAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                beanList.remove(position);
                evaluateAdapter.notifyDataSetChanged();
            }
        });
        rvList.setAdapter(evaluateAdapter);
        getTroubleInfo(getIntent());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            /**
             * 右上角添加
             * */
            case ADD_TROUBLE_CALLBACK_CODE:
                getTroubleInfo(data);
                break;
            default:
                break;

        }
    }

    private void getTroubleInfo(Intent data) {
        if (data != null) {
            List<RepairBugEntity> mBeanList = (List<RepairBugEntity>) data.getSerializableExtra("beanList");
            beanList = mBeanList;
            evaluateAdapter.setNewData(beanList);
            evaluateAdapter.notifyDataSetChanged();
            mOwnerOrgId = getIntent().getLongExtra("mOwnerOrgId", 0);
        }
    }


    /**
     * 放弃报修
     */
    private void giveUp() {
        new TrueFalseDialog(this, "系统提示", "是否放弃报修？", () -> {
            ClientApplication.get().closeActivity(RepairTypeActivity.class);
            finish();
        }).showDialog();
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

    @OnClick({R.id.iv_left, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                giveUp();
                break;
            case R.id.tv_next:
                Bundle bundle = new Bundle();
                bundle.putLong("mOwnerOrgId", mOwnerOrgId);
                bundle.putSerializable("troubleList", (Serializable) evaluateAdapter.getData());
                bundle.putString("qrcode", getIntent().getStringExtra("qrcode"));
                bundle.putSerializable("repairbean", getIntent().getSerializableExtra("repairbean"));
                bundle.putString("headUrl", getIntent().getStringExtra("headUrl"));
                bundle.putString("workName", getIntent().getStringExtra("workName"));
                bundle.putString("companyName", getIntent().getStringExtra("companyName"));
                JumpItent.jump(TroubleListActivity.this, RepairActivity.class, bundle);
                break;
            default:
                break;
        }
    }
}
