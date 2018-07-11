package net.eanfang.client.ui.fragment.worktalk;

import android.os.Bundle;

import com.eanfang.ui.base.BaseFragment;

import net.eanfang.client.R;
import net.eanfang.client.ui.fragment.TemplateItemListFragment;
import net.eanfang.client.ui.fragment.WorkTaskListFragment;
/**
* @date on 2018/7/11  19:08
* @author Guanluocang
* @decision  面谈员工列表
*/
public class WorkTalkListFragment extends TemplateItemListFragment {

    private String mTitle;
    private String mType;


    public static WorkTalkListFragment getInstance(String title, int type) {
        WorkTalkListFragment workTalkListFragment = new WorkTalkListFragment();
        workTalkListFragment.mTitle = title;
        workTalkListFragment.mType = String.valueOf(type);
        return workTalkListFragment;

    }

    public String getmTitle() {
        return mTitle;
    }


    @Override
    protected void initAdapter() {

    }

    @Override
    protected void getData() {

    }
}
