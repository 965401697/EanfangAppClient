package net.eanfang.client.ui.activity.worksapce.defendlog;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaf.base.entity.LogDetailsEntity;

import net.eanfang.client.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by O u r on 2018/5/23.
 */

public class DefendLogAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context mContext;
    private int mFlag; //1:"填写布防日志" 2：布防日志详情

    private List<DefendLogItemAdapter> mList = new ArrayList<>();


    public List<DefendLogItemAdapter> getmList() {
        return mList;
    }

    public DefendLogAdapter(int layoutResId, Context context, int flag) {
        super(layoutResId);
        this.mContext = context;
        this.mFlag = flag;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        if (mFlag == 1) {
            helper.getView(R.id.tv_defend_add).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.tv_defend_add).setVisibility(View.GONE);
        }

        helper.setText(R.id.tv_title, item);
        helper.addOnClickListener(R.id.tv_defend_add);
        RecyclerView recyclerView = helper.getView(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        DefendLogItemAdapter defendLogItemAdapter = new DefendLogItemAdapter(R.layout.item_item_defend_log, mFlag);
        defendLogItemAdapter.bindToRecyclerView(recyclerView);
        mList.add(defendLogItemAdapter);//将二级的adapter的对象都保存集合

        defendLogItemAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_delect) {
                    adapter.remove(position);
                }
            }
        });

        defendLogItemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int postion;
                String title;
                LogDetailsEntity bean = (LogDetailsEntity) adapter.getData().get(position);
//                (0-旁路,1-闯防,2-误报)
                if (bean.getLogType() == 1) {
                    postion = 1;
                    title = "闯防";
                } else if (bean.getLogType() == 2) {
                    postion = 2;
                    title = "误报";
                } else {
                    postion = 0;
                    title = "旁路";
                }

                Intent intent = new Intent(mContext, DefendLogItemWriteAndDetailActivity.class);
                intent.putExtra("bean", bean);
                intent.putExtra("title", title);
                intent.putExtra("position", position);
                mContext.startActivity(intent);
            }
        });
    }
}
