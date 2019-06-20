package net.eanfang.client.ui.adapter.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.base.widget.customview.CircleImageView;

import net.eanfang.client.R;

import lombok.Getter;


/**
 * @author liangkailun
 * Date ：2019/4/9
 * Describe :
 */
@Getter
public class FollowListViewHolder extends BaseViewHolder {
    private TextView tvFollowItemName;
    private TextView tvFollowItemCompany;
    private TextView tvFollowItemFriendStatus;
    private ImageView imgFollowItemAuth;
    private Button btnFollowItemAddOrCancel;
    private CircleImageView ivFollowItemHeader;

    public FollowListViewHolder(View view) {
        super(view);
        tvFollowItemName = view.findViewById(R.id.tv_follow_item_name);
        tvFollowItemCompany = view.findViewById(R.id.tv_follow_item_company);
        tvFollowItemFriendStatus = view.findViewById(R.id.tv_follow_item_friendStatus);
        imgFollowItemAuth = view.findViewById(R.id.img_follow_item_auth);
        btnFollowItemAddOrCancel = view.findViewById(R.id.btn_follow_item_addOrCancel);
        ivFollowItemHeader = view.findViewById(R.id.iv_follow_item_header);
        addOnClickListener(R.id.btn_follow_item_addOrCancel);
    }
}
