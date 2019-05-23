package net.eanfang.client.ui.activity.worksapce.repair;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.reapair.RepairPersonalInfoEntity;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.QueryEntry;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.adapter.repair.RepairPersonalInfoAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author guanluocang
 * @data 2019/4/22
 * @description 报修个人信息页面
 */

public class RepairPersonInfoListActivity extends BaseActivity {

    private static final int REPAIR_ADDINFO_CALLBACK_CODE = 100;
    @BindView(R.id.rv_personalInfo)
    RecyclerView rvPersonalInfo;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    private RepairPersonalInfoAdapter repairPersonalInfoAdapter;
    private List<RepairPersonalInfoEntity.ListBean> repairPersonalInfoEntities;
    private List<RepairPersonalInfoEntity.ListBean> mDeleteList = new ArrayList();
    private List<RepairPersonalInfoEntity.ListBean> mAllList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_person_info_list);
        ButterKnife.bind(this);
        initView();
        initListener();
        initData();
    }


    private void initView() {
        setLeftBack();
        setTitle("用户信息管理");
        setRightTitle("添加");

        rvPersonalInfo.setLayoutManager(new LinearLayoutManager(this));
        repairPersonalInfoAdapter = new RepairPersonalInfoAdapter();
        rvPersonalInfo.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        repairPersonalInfoAdapter.bindToRecyclerView(rvPersonalInfo);
    }

    private void initListener() {
        setRightTitleOnClickListener((v) -> {
            JumpItent.jump(this, RepairInfoEditActivity.class, REPAIR_ADDINFO_CALLBACK_CODE);
        });
        setLeftBack((V) -> {
            //默认返回第一条
            doReturn(0);
        });
        repairPersonalInfoAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                // 设为默认
                case R.id.cb_default:
                    doChangeAddress(repairPersonalInfoAdapter.getData().get(position).getId(), position);
                    break;
                // 删除
                case R.id.tv_delete:
                    doDeleteInfo(repairPersonalInfoAdapter.getData().get(position).getId());
                    break;
                // 编辑
                case R.id.tv_edit:
                    Bundle bundleDelete = new Bundle();
                    bundleDelete.putBoolean("isEdit", true);
                    bundleDelete.putSerializable("infoEntity", repairPersonalInfoAdapter.getData().get(position));
                    JumpItent.jump(this, RepairInfoEditActivity.class, bundleDelete, REPAIR_ADDINFO_CALLBACK_CODE);
                    break;
                default:
                    break;
            }
        });

        repairPersonalInfoAdapter.setOnItemClickListener((adapter, view, position) -> {
            doReturn(position);
        });

    }

    /**
     * 删除
     */
    private void doDeleteInfo(Long id) {
        EanfangHttp.post(NewApiService.REPAIR_PERSONAL_INFO_DELETE)
                .params("id", id)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class) {
                    @Override
                    public void onSuccess(JSONObject bean) {
                        super.onSuccess(bean);
                        initData();
                    }
                });
    }

    /**
     * 设为默认
     */
    private void doChangeAddress(Long id, int position) {
        EanfangHttp.post(NewApiService.REPAIR_PERSONAL_INFO_DEFAULT)
                .params("id", id)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class) {
                    @Override
                    public void onSuccess(JSONObject bean) {
                        super.onSuccess(bean);
                        runOnUiThread(() -> {
                            RepairPersonalInfoEntity.ListBean listBean = repairPersonalInfoAdapter.getData().get(position);
                            mAllList = repairPersonalInfoAdapter.getData();
                            // 0不默认，1默认
                            if (listBean.getIsDefault() == 0) {
                                mDeleteList.clear();
                                repairPersonalInfoAdapter.getData().get(0).setIsDefault(0);
                                listBean.setIsDefault(1);
                                mDeleteList.add(listBean);
                                repairPersonalInfoAdapter.getData().remove(position);
                                repairPersonalInfoAdapter.addData(0, mDeleteList);
                                repairPersonalInfoAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
    }

    private void initData() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("accId", ClientApplication.get().getAccId() + "");
        EanfangHttp.post(NewApiService.REPAIR_PERSONAL_INFO_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<RepairPersonalInfoEntity>(this, true, RepairPersonalInfoEntity.class, bean -> {
                    if (bean.getList() != null && bean.getList().size() > 0) {
                        rvPersonalInfo.setVisibility(View.VISIBLE);
                        tvNodata.setVisibility(View.GONE);
                        repairPersonalInfoEntities = bean.getList();
                        repairPersonalInfoAdapter.getData().clear();
                        repairPersonalInfoAdapter.setNewData(repairPersonalInfoEntities);
                    } else {
                        repairPersonalInfoAdapter.getData().clear();
                        rvPersonalInfo.setVisibility(View.GONE);
                        tvNodata.setVisibility(View.VISIBLE);
                    }
                }));
    }

    public void doReturn(int position) {
        Bundle bundleBack = new Bundle();
        if (repairPersonalInfoAdapter.getData() != null && repairPersonalInfoAdapter.getData().size() > 0) {
            bundleBack.putSerializable("infoEntity", repairPersonalInfoAdapter.getData().get(position));
        }
        setResult(RESULT_OK, new Intent().putExtras(bundleBack));
        finishSelf();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            //默认返回第一条
            doReturn(0);
        }
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //添加信息
            case REPAIR_ADDINFO_CALLBACK_CODE:
                initData();
                break;
            default:
                break;
        }
    }
}
