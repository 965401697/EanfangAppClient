package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eanfang.ui.base.BaseActivity;
import com.yaf.sys.entity.OrgEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.ConstactsAdapter;
import net.eanfang.client.ui.adapter.StaffAdapter;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/22  10:18
 * @email houzhongzhou@yeah.net
 * @desc 组织结构
 */

public class ConstansActivity extends BaseActivity {
    @BindView(R.id.rev_lista)
    RecyclerView revLista;
    @BindView(R.id.rev_depart)
    RecyclerView revDepart;
    private OrgEntity orgEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("组织结构");
        setLeftBack();
        orgEntity = (OrgEntity) getIntent().getSerializableExtra("data");
        LinkedList<Object> list = new LinkedList<>();
        if (orgEntity.getChildren() != null) {
            list.addAll(orgEntity.getChildren());
        }
        if (orgEntity.getStaff() != null) {
            list.addAll(orgEntity.getStaff());
        }

        revLista.setLayoutManager(new LinearLayoutManager(this));
        StaffAdapter staffAdapter = new StaffAdapter(list);
        staffAdapter.setOnItemChildClickListener((adapter1, view, position) -> {
            showToast("用户信息");
        });
        revLista.setAdapter(staffAdapter);

        ConstactsAdapter adapter = new ConstactsAdapter(list);
        revDepart.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnItemChildClickListener((adapter12, view, position) -> {
            Object tag = list.get(position);
            if (tag instanceof OrgEntity) {
                startActivity(new Intent(this, ConstansActivity.class).putExtra("data", (OrgEntity) tag));
            }
        });
        revDepart.setAdapter(adapter);
    }

}
