package net.eanfang.client.ui.activity.worksapce.defendlog;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.DefendLogDetailBean;

import net.eanfang.client.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by O u r on 2018/7/13.
 */

public class DefendLogDetailAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context mContext;


    private List<DefendLogDetailItemAdapter> mList = new ArrayList<>();


    public List<DefendLogDetailItemAdapter> getmList() {
        return mList;
    }

    public DefendLogDetailAdapter(int layoutResId, Context context) {
        super(layoutResId);
        this.mContext = context;

    }

    @Override
    protected void convert(com.chad.library.adapter.base.BaseViewHolder helper, java.lang.String item) {

        helper.setText(R.id.tv_title, item);
        helper.addOnClickListener(R.id.tv_defend_add);
        RecyclerView recyclerView = helper.getView(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        DefendLogDetailItemAdapter defendLogDetailItemAdapter = new DefendLogDetailItemAdapter(R.layout.item_item_defend_log_detail);
        defendLogDetailItemAdapter.bindToRecyclerView(recyclerView);
        mList.add(defendLogDetailItemAdapter);//将二级的adapter的对象都保存集合


    }
}
