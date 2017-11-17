package net.eanfang.client.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * homefragment适配
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    //fragment列表
    private List<Fragment>list_frag;
    //tab名列表
    private List<String>list_title;

    public FragmentAdapter(FragmentManager fm, List<Fragment>list_frag, List<String>list_title) {
        super(fm);
        this.list_frag=list_frag;
        this.list_title=list_title;
    }

    public FragmentAdapter(FragmentManager fm, List<Fragment> list_frag) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        return list_frag.get(position);
    }

    @Override
    public int getCount() {
        return list_title.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list_title.get(position%list_title.size());
    }
}
