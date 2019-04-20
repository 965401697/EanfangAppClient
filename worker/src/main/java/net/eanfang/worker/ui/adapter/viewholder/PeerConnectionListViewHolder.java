package net.eanfang.worker.ui.adapter.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;

/**
 * @author liangkailun
 * Date ：2019/4/9
 * Describe :同行人脉viewholder
 */
public class PeerConnectionListViewHolder extends BaseViewHolder {
    public TextView mTvConnectionItemName;
    public TextView mTvConnectionItemCompany;
    public Button mBtnConnectionItemAddOrCancel;
    public SimpleDraweeView mIvConnectionItemHeader;

    public PeerConnectionListViewHolder(View view) {
        super(view);
        mTvConnectionItemName = view.findViewById(R.id.tv_connection_item_name);
        mTvConnectionItemCompany = view.findViewById(R.id.tv_connection_item_company);
        mBtnConnectionItemAddOrCancel = view.findViewById(R.id.btn_connection_item_addOrCancel);
        mIvConnectionItemHeader = view.findViewById(R.id.iv_connection_item_header);
        mBtnConnectionItemAddOrCancel.setSelected(true);
        addOnClickListener(R.id.btn_connection_item_addOrCancel);
    }
}
