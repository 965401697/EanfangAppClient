package net.eanfang.worker.ui.adapter;

import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.model.FriendListBean;
import com.eanfang.model.GroupsBean;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;

import java.util.ArrayList;

/**
 * Created by O u r on 2018/4/18.
 */

public class GroupsDetailAdapter extends BaseQuickAdapter<FriendListBean, BaseViewHolder> {

    private ArrayList<FriendListBean> mList;
    private boolean mIsOwn;//是不是群主

    public GroupsDetailAdapter(int layoutResId, ArrayList<FriendListBean> list, boolean isOwn) {
        super(layoutResId);
        this.mList = list;
        this.mIsOwn = isOwn;
    }

    @Override
    protected void convert(BaseViewHolder helper, FriendListBean item) {
        ((SimpleDraweeView) helper.getView(R.id.iv_icon)).setImageURI(Uri.parse(BuildConfig.OSS_SERVER + item.getAvatar()));
        if (getData().size() - 1 == helper.getAdapterPosition()) {

            if (!mIsOwn) {
                ((SimpleDraweeView) helper.getView(R.id.iv_icon)).setVisibility(View.GONE);
                return;
            } else {
                ((SimpleDraweeView) helper.getView(R.id.iv_icon)).setImageResource(R.mipmap.ic_btn_deleteperson);
                helper.setText(R.id.tv_name, "");
            }
        } else if (getData().size() - 2 == helper.getAdapterPosition()) {

            ((SimpleDraweeView) helper.getView(R.id.iv_icon)).setImageResource(R.mipmap.ic_btn_addperson);
            helper.setText(R.id.tv_name, "");


        } else {

            helper.setText(R.id.tv_name, item.getNickName());
        }

    }

}

