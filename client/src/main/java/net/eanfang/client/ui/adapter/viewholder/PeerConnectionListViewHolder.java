package net.eanfang.client.ui.adapter.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;

import lombok.Getter;


/**
 * @author liangkailun
 * Date ：2019/4/9
 * Describe :同行人脉viewholder
 */
@Getter
public class PeerConnectionListViewHolder extends BaseViewHolder {
    private TextView tvConnectionItemName;
    private TextView tvConnectionItemCompany;
    private Button btnConnectionItemAddOrCancel;
    private SimpleDraweeView ivConnectionItemHeader;

    public PeerConnectionListViewHolder(View view) {
        super(view);
        tvConnectionItemName = view.findViewById(R.id.tv_connection_item_name);
        tvConnectionItemCompany = view.findViewById(R.id.tv_connection_item_company);
        btnConnectionItemAddOrCancel = view.findViewById(R.id.btn_connection_item_addOrCancel);
        ivConnectionItemHeader = view.findViewById(R.id.iv_connection_item_header);
        btnConnectionItemAddOrCancel.setSelected(true);
        addOnClickListener(R.id.btn_connection_item_addOrCancel);
    }
}
