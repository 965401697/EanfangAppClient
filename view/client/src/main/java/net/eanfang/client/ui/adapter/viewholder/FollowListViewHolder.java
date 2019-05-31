package net.eanfang.client.ui.adapter.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.base.widget.customview.CircleImageView;

import net.eanfang.client.R;


/**
 * @author liangkailun
 * Date ：2019/4/9
 * Describe :
 */
public class FollowListViewHolder extends BaseViewHolder {
    public TextView mTvFollowItemName;
    public TextView mTvFollowItemCompany;
    public TextView mTv_Follow_Item_FriendStatus;
    public ImageView mImgFollowItemAuth;
    public Button mBtnFollowItemAddOrCancel;
    public CircleImageView mIvFollowItemHeader;

    public FollowListViewHolder(View view) {
        super(view);
        mTvFollowItemName = view.findViewById(R.id.tv_follow_item_name);
        mTvFollowItemCompany = view.findViewById(R.id.tv_follow_item_company);
        mTv_Follow_Item_FriendStatus = view.findViewById(R.id.tv_follow_item_friendStatus);
        mImgFollowItemAuth = view.findViewById(R.id.img_follow_item_auth);
        mBtnFollowItemAddOrCancel = view.findViewById(R.id.btn_follow_item_addOrCancel);
        mIvFollowItemHeader = view.findViewById(R.id.iv_follow_item_header);
        addOnClickListener(R.id.btn_follow_item_addOrCancel);
    }
}
