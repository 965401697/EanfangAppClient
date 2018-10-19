package net.eanfang.worker.ui.activity.worksapce.oa.workreport;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.model.TemplateBean;
import com.eanfang.ui.activity.SelectOAPresonActivity;
import com.eanfang.ui.base.BaseActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;

import java.util.List;

/**
 * Created by O u r on 2018/10/18.
 */

public class OAPersonAdaptet extends RecyclerView.Adapter<OAPersonAdaptet.ViewHolder> {


    private Context mContext;
    private List<TemplateBean.Preson> mData;

    public OAPersonAdaptet(Context context, List<TemplateBean.Preson> data) {
        this.mContext = context;
        this.mData = data;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_oa_person, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (getItemCount() - 1 == position) {
            holder.name.setVisibility(View.INVISIBLE);
            holder.ivSub.setVisibility(View.INVISIBLE);
            holder.userHeader.setImageResource(R.mipmap.im_add);
            holder.userHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //选择协同人员
                    ((BaseActivity) mContext).startActivity(new Intent(mContext, SelectOAPresonActivity.class));
                }
            });
        } else {
            holder.name.setVisibility(View.VISIBLE);
            holder.ivSub.setVisibility(View.VISIBLE);
            TemplateBean.Preson preson = mData.get(position);
            holder.name.setText(preson.getName());
            holder.userHeader.setImageURI(BuildConfig.OSS_SERVER + preson.getProtraivat());
            holder.userHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mData.remove(position);
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (mData.size() + 1);
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
        private SimpleDraweeView userHeader;
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
