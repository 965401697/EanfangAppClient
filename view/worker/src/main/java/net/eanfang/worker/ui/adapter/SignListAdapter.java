package net.eanfang.worker.ui.adapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.SignListBean;

import net.eanfang.worker.R;


/**
 * Created by wen on 2017/4/23.
 */

public class SignListAdapter extends BaseQuickAdapter<SignListBean, BaseViewHolder> {

    private onSecondClickListener onSecondClickListener;

    /**
     * 判断时间
     */
    private String mTime = "";

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
//        String[] times = item.getSignDay().split("-");
//        helper.setText(R.id.tv_month, times[1] + "月");
//        helper.setText(R.id.tv_year, times[0] + "年");


        RecyclerView rv_footer = helper.getView(R.id.rv_footer);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_footer.setLayoutManager(linearLayoutManager);

        signListSecondAdapter = new SignSecondAdapter();
        signListSecondAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        signListSecondAdapter.bindToRecyclerView(rv_footer);
        signListSecondAdapter.setNewData(item.getList());
        signListSecondAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                onSecondClickListener.onSecondClick(position);
            }
        });

    }


}