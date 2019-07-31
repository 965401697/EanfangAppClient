package net.eanfang.worker.ui.activity.im;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.biz.model.entity.AccountEntity;
import com.eanfang.biz.model.entity.SysGroupUserEntity;
import com.eanfang.config.EanfangConst;
import com.eanfang.util.Cn2Spell;
import com.eanfang.util.ToastUtil;
import com.eanfang.witget.SideBar;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Date: 2018/11/21  15:53
 * @Author: O u r
 * @QQ:373946991
 * @Describe: GroupMoreMemberActivity 群组更多成员的界面展示
 */
public class GroupMoreMemberActivity extends BaseWorkerActivity {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private MoreMemberAdapter mMoreMemberAdapter;
    @BindView(R.id.side_bar)
    SideBar sideBar;

    private List<SysGroupUserEntity> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_group_more_mumber);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("群组成员");
        setLeftBack();
        mList = (ArrayList<SysGroupUserEntity>) getIntent().getSerializableExtra("list");
        initViews();
        initData();
    }


    private void initData() {


        if (mList.size() > 0) {

            List<AccountEntity> list = new ArrayList<>(mList.size());

            for (SysGroupUserEntity bean : mList) {
                // 根据姓名获取拼音
                bean.getAccountEntity().setPinyin(bean.getAccountEntity().getNickName());
                bean.getAccountEntity().setFirstLetter(Cn2Spell.getPinYin(bean.getAccountEntity().getNickName()).substring(0, 1).toUpperCase()); // 获取拼音首字母并转成大写
                if (!Cn2Spell.getPinYin(bean.getAccountEntity().getNickName()).substring(0, 1).toUpperCase().matches("[A-Z]")) { // 如果不在A-Z中则默认为“#”
                    bean.getAccountEntity().setFirstLetter("#");
                }

                list.add(bean.getAccountEntity());
            }

            List<AccountEntity> groupMemberList = list;

            Collections.sort(groupMemberList, (o1, o2) -> {
                if (o1 == null || o1.getFirstLetter() == null || o2 == null || o2.getFirstLetter() == null) {
                    return -1;
                }
                //这里主要是用来对数据里面的数据根据ABCDEFG...来排序
                if ("#".equals(o2.getFirstLetter())) {
                    return -1;
                } else if ("#".equals(o1.getFirstLetter())) {
                    return 1;
                } else {
                    return o1.getFirstLetter().compareTo(o2.getFirstLetter());
                }
            });

            mMoreMemberAdapter.setNewData(groupMemberList);
        }

    }

    private void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMoreMemberAdapter = new MoreMemberAdapter(R.layout.item_more_member);
        mMoreMemberAdapter.bindToRecyclerView(recyclerView);
        mMoreMemberAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AccountEntity bean = (AccountEntity) adapter.getData().get(position);
                if (!bean.getAccId().equals(String.valueOf(WorkerApplication.get().getAccId()))) {
                    Intent intent = new Intent(GroupMoreMemberActivity.this, IMPresonInfoActivity.class);
                    intent.putExtra(EanfangConst.RONG_YUN_ID, bean.getAccId());
                    startActivity(intent);
                } else {
                    ToastUtil.get().showToast(GroupMoreMemberActivity.this, "自己不能查看自己");
                }
            }
        });

        sideBar.setOnStrSelectCallBack(new SideBar.ISideBarSelectCallBack() {
            @Override
            public void onSelectStr(int index, String selectStr) {
                for (int i = 0; i < mMoreMemberAdapter.getData().size(); i++) {
                    if (selectStr.equalsIgnoreCase(mMoreMemberAdapter.getData().get(i).getFirstLetter())) {
                        recyclerView.scrollToPosition(i); // 选择到首字母出现的位置
                        return;
                    }
                }
            }
        });
    }

}
