package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eanfang.ui.base.BaseActivity;
import com.eanfang.biz.model.entity.OrgEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.ConstactsAdapter;
import net.eanfang.worker.ui.adapter.StaffAdapter;

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
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
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


        StaffAdapter staffAdapter = new StaffAdapter(list);
        revLista.setLayoutManager(new LinearLayoutManager(this));
        staffAdapter.setOnItemChildClickListener((adapter1, view, position) -> {
            showToast("用户信息");
        });
//        revLista.addItemDecoration(new DividerItemDecoration(this,
//                DividerItemDecoration.VERTICAL));
        revLista.setAdapter(staffAdapter);

        ConstactsAdapter adapter = new ConstactsAdapter(    list);
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
