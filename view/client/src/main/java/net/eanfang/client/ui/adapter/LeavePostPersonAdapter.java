package net.eanfang.client.ui.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.biz.model.TemplateBean;
import com.eanfang.util.GlideUtil;
import com.eanfang.base.kit.V;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.activity.im.CreateGroupOrganizationActivity;
import net.eanfang.client.ui.activity.leave_post.LeavePostAddPostActivity;

import java.io.Serializable;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import lombok.NonNull;
import lombok.Setter;

/**
 * Created by O u r on 2018/10/18.
 */

public class LeavePostPersonAdapter extends RecyclerView.Adapter<LeavePostPersonAdapter.ViewHolder> {


    private Context mContext;
    private List<TemplateBean.Preson> mData;
    private int mFlag;
    private Handler mHandler;
    @Setter
    private boolean canClick = true;

    public LeavePostPersonAdapter(Context context, List<TemplateBean.Preson> data) {
        this.mContext = context;
        this.mData = data;
    }

    public LeavePostPersonAdapter(Context context, List<TemplateBean.Preson> data, int flag) {
        this.mContext = context;
        this.mData = data;
        this.mFlag = flag;
    }

    public LeavePostPersonAdapter(Context context, List<TemplateBean.Preson> data, int flag, Handler handler) {
        this.mContext = context;
        this.mData = data;
        this.mFlag = flag;
        this.mHandler = handler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_oa_person, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (canClick && getItemCount() - 1 == position && mData.size() < 5) {
            holder.name.setVisibility(View.INVISIBLE);
            holder.ivSub.setVisibility(View.INVISIBLE);
            holder.userHeader.setVisibility(View.VISIBLE);
            holder.userHeader.setImageResource(R.drawable.icon_add_staff_person_);
            holder.userHeader.setOnClickListener(v -> {
                ((LeavePostAddPostActivity) mContext).setFlag(mFlag);
                Intent intent = new Intent(mContext, CreateGroupOrganizationActivity.class);
                intent.putExtra("isFrom", "OA");
                intent.putExtra("companyId", String.valueOf(ClientApplication.get().getCompanyId()));
                intent.putExtra("companyName", ClientApplication.get().getLoginBean().getAccount().getDefaultUser().getCompanyEntity().getOrgName());
                Bundle bundle = new Bundle();
                bundle.putSerializable("list", (Serializable) mData);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            });
            return;
        }

        holder.name.setVisibility(View.VISIBLE);
        holder.ivSub.setVisibility(canClick ? View.VISIBLE : View.GONE);
        TemplateBean.Preson preson = mData.get(position);
        holder.name.setText(V.v(() -> preson.getName()));
        if (preson.getProtraivat().startsWith("http")) {
            GlideUtil.intoImageView(mContext, preson.getProtraivat(), holder.userHeader);
        } else {
            GlideUtil.intoImageView(mContext, BuildConfig.OSS_SERVER + preson.getProtraivat(), holder.userHeader);
        }
        if (canClick) {
            holder.userHeader.setOnClickListener(v -> {
                if (mHandler != null) {
                    mHandler.sendEmptyMessage(1);
                }
                mData.remove(position);
                notifyDataSetChanged();
            });
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (canClick) {
            count = mData.size() < 5 ? mData.size() + 1 : 5;
        } else {
            count = mData.size() < 5 ? mData.size() : 5;
        }
        return count;
    }

    public List<TemplateBean.Preson> getData() {
        return mData;
    }

    public void setNewData(List<TemplateBean.Preson> data) {
        this.mData.clear();
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private CircleImageView userHeader;
        private ImageView ivSub;
        private RelativeLayout rlInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            userHeader = itemView.findViewById(R.id.iv_user_header);
            ivSub = itemView.findViewById(R.id.iv_sub);
            rlInfo = itemView.findViewById(R.id.rl_info);


        }
    }
}
