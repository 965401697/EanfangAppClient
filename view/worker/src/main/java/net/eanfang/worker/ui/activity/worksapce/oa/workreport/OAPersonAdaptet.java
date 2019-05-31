package net.eanfang.worker.ui.activity.worksapce.oa.workreport;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.biz.model.TemplateBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.V;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.im.CreateGroupActivity;
import net.eanfang.worker.ui.activity.im.CreateGroupOrganizationActivity;
import net.eanfang.worker.ui.activity.im.NewSelectIMContactActivity;
import net.eanfang.worker.ui.activity.worksapce.oa.SelectOAGroupActivity;
import net.eanfang.worker.ui.activity.worksapce.oa.check.AddDealwithInfoActivity;
import net.eanfang.worker.ui.activity.worksapce.oa.check.AddNewCheckActivity;
import net.eanfang.worker.ui.activity.worksapce.oa.task.TaskAssignmentCreationActivity;
import net.eanfang.worker.ui.activity.worksapce.sign.SignFiltrateActivity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by O u r on 2018/10/18.
 */

public class OAPersonAdaptet extends RecyclerView.Adapter<OAPersonAdaptet.ViewHolder> {


    private Context mContext;
    private List<TemplateBean.Preson> mData;
    private int mFlag;
    private Handler mHandler;

    public OAPersonAdaptet(Context context, List<TemplateBean.Preson> data) {
        this.mContext = context;
        this.mData = data;
    }

    public OAPersonAdaptet(Context context, List<TemplateBean.Preson> data, int flag) {
        this.mContext = context;
        this.mData = data;
        this.mFlag = flag;
    }

    public OAPersonAdaptet(Context context, List<TemplateBean.Preson> data, int flag, Handler handler) {
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
        if (getItemCount() - 1 == position) {
            holder.name.setVisibility(View.INVISIBLE);
            holder.ivSub.setVisibility(View.INVISIBLE);
            holder.userHeader.setImageResource(R.mipmap.im_add);
            holder.userHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mFlag != 0 && mFlag != 6) {
                        if (mContext instanceof CreationWorkReportActivity) {
                            ((CreationWorkReportActivity) mContext).setFlag(mFlag);
                        } else if (mContext instanceof TaskAssignmentCreationActivity) {
                            ((TaskAssignmentCreationActivity) mContext).setFlag(mFlag);
                        } else if (mContext instanceof AddNewCheckActivity) {
                            ((AddNewCheckActivity) mContext).setFlag(mFlag);
                        } else if (mContext instanceof SignFiltrateActivity) {
                            ((SignFiltrateActivity) mContext).setFlag(mFlag);
                        } else if (mContext instanceof AddDealwithInfoActivity) {
                            ((AddDealwithInfoActivity) mContext).setFlag(mFlag);
                        }

                    }
                    if (mFlag == 4) {//选择群组
                        ((BaseActivity) mContext).startActivityForResult(new Intent(mContext, SelectOAGroupActivity.class), 101);
                    } else if (mFlag == 6) {
                        Intent intent = new Intent(new Intent(mContext, NewSelectIMContactActivity.class));
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("list", (Serializable) getData());
                        intent.putExtras(bundle);
                        intent.putExtra("flag", 1);
                        intent.putExtra("groupName", ((CreateGroupActivity) mContext).etGroupName.getText().toString());
                        intent.putExtra("headPortrait", ((CreateGroupActivity) mContext).imgKey);
                        intent.putExtra("locationPortrait", ((CreateGroupActivity) mContext).locationUrl);
//                        ((CreateGroupActivity) mContext).finishSelf();
                        mContext.startActivity(intent);
                    } else {
                        //选择协同人员
//                        ((BaseActivity) mContext).startActivity(new Intent(mContext, SelectOAPresonActivity.class).putExtra("IM", "IM"));

                        Intent intent = new Intent(mContext, CreateGroupOrganizationActivity.class);
                        intent.putExtra("isFrom", "OA");
                        intent.putExtra("companyId", String.valueOf(WorkerApplication.get().getCompanyId()));
                        intent.putExtra("companyName", WorkerApplication.get().getLoginBean().getAccount().getDefaultUser().getCompanyEntity().getOrgName());
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("list", (Serializable) mData);
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);
                    }
                }
            });
        } else {
            holder.name.setVisibility(View.VISIBLE);
            holder.ivSub.setVisibility(View.VISIBLE);
            TemplateBean.Preson preson = mData.get(position);
            holder.name.setText(V.v(() -> preson.getName()));
            if (preson.getProtraivat().startsWith("http")) {
                GlideUtil.intoImageView(mContext,preson.getProtraivat(),holder.userHeader);
            } else {
                GlideUtil.intoImageView(mContext,BuildConfig.OSS_SERVER + preson.getProtraivat(),holder.userHeader);
            }
            holder.userHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mHandler != null) {
                        mHandler.sendEmptyMessage(1);
                    }
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
