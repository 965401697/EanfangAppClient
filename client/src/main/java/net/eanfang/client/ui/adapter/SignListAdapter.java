package net.eanfang.client.ui.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.SignListBean;

import net.eanfang.client.R;


/**
 * Created by wen on 2017/4/23.
 */

public class SignListAdapter extends BaseQuickAdapter<SignListBean, BaseViewHolder> {

    private onSecondClickListener onSecondClickListener;

    public SignListAdapter(onSecondClickListener mOnSecondClickListener) {
        super(R.layout.item_sign_list);
        this.onSecondClickListener = mOnSecondClickListener;
    }

    private SignSecondAdapter signListSecondAdapter;

    public interface onSecondClickListener {
        void onSecondClick(int position);
    }

    @Override
    protected void convert(BaseViewHolder helper, SignListBean item) {
        String[] times = item.getSignDay().split("-");
        helper.setText(R.id.tv_month, times[1]);
        helper.setText(R.id.tv_year, times[0] + "年");

        RecyclerView rv_footer = helper.getView(R.id.rv_footer);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_footer.setLayoutManager(linearLayoutManager);
        signListSecondAdapter = new SignSecondAdapter();
        signListSecondAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        signListSecondAdapter.bindToRecyclerView(rv_footer);
        signListSecondAdapter.setNewData(item.getList());
        rv_footer.addOnItemTouchListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                onSecondClickListener.onSecondClick(position);
            }
        });
    }
}
