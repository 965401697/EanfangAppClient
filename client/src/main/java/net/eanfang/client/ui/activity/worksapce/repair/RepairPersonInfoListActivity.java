package net.eanfang.client.ui.activity.worksapce.repair;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.reapair.RepairPersonalInfoEntity;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.repair.RepairPersonalInfoAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author guanluocang
 * @data 2019/4/22
 * @description 报修个人信息页面
 */

public class RepairPersonInfoListActivity extends BaseActivity {

    @BindView(R.id.rv_personalInfo)
    RecyclerView rvPersonalInfo;
    private RepairPersonalInfoAdapter repairPersonalInfoAdapter;
    private List<RepairPersonalInfoEntity> repairPersonalInfoEntities;

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

        rvPersonalInfo.setLayoutManager(new LinearLayoutManager(this));
        repairPersonalInfoAdapter = new RepairPersonalInfoAdapter();
        repairPersonalInfoAdapter.bindToRecyclerView(rvPersonalInfo);
    }

    private void initListener() {
        repairPersonalInfoAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                // 设为默认
                case R.id.cb_default:

                    break;
                // 删除
                case R.id.tv_delete:

                    break;
                // 编辑
                case R.id.tv_edit:

                    break;
                default:
                    break;
            }
        });

        repairPersonalInfoAdapter.setOnItemClickListener((adapter, view, position) -> {

        });

    }

    private void initData() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("accId", EanfangApplication.get().getAccId() + "");
        EanfangHttp.post(NewApiService.REPAIR_PERSONAL_INFO_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<RepairPersonalInfoEntity>(this, true, RepairPersonalInfoEntity.class, bean -> {
                }));
    }


}
