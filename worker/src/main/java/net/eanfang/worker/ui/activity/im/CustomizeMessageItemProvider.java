package net.eanfang.worker.ui.activity.im;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.config.Constant;
import com.eanfang.util.GetConstDataUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.OrderDetailActivity;
import net.eanfang.worker.ui.activity.worksapce.TroubleDetalilListActivity;
import net.eanfang.worker.ui.activity.worksapce.oa.check.DealWithFirstActivity;
import net.eanfang.worker.ui.activity.worksapce.oa.task.TaskDetailActivity;
import net.eanfang.worker.ui.activity.worksapce.oa.workreport.WorkReportDetailActivity;
import net.eanfang.worker.ui.activity.worksapce.security.SecurityDetailActivity;
import net.eanfang.worker.ui.activity.worksapce.worktalk.WorkTalkDetailActivity;
import net.eanfang.worker.ui.activity.worksapce.worktransfer.WorkTransferDetailActivity;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;

/**
 * Created by O u r on 2018/7/2.
 */
@ProviderTag(messageContent = CustomizeMessage.class)
public class CustomizeMessageItemProvider extends IContainerItemProvider.MessageProvider<CustomizeMessage> {

    class ViewHolder {
        TextView title;
        TextView status;
        TextView orderNum;
        TextView creatTime;
        TextView workerName;
        TextView mTime;
        SimpleDraweeView simpleDraweeView;
        LinearLayout ll_custom;
    }

    @Override
    public void bindView(View view, int i, CustomizeMessage customizeMessage, UIMessage uiMessage) {
        ViewHolder holder = (ViewHolder) view.getTag();

        if (TextUtils.isEmpty(customizeMessage.getShareType())) {
            return;
        }

        if (customizeMessage.getShareType().equals("1")) {
            holder.title.setText("报修订单");
            holder.orderNum.setText("单号：  " + customizeMessage.getOrderNum());
            holder.simpleDraweeView.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + customizeMessage.getPicUrl()));
            holder.creatTime.setText("下单时间：  " + customizeMessage.getCreatTime());
            holder.workerName.setText("负责人：  " + customizeMessage.getWorkerName());
            holder.status.setText(GetConstDataUtils.getRepairStatus().get(Integer.parseInt(customizeMessage.getStatus())));
        } else if (customizeMessage.getShareType().equals("2")) {
            holder.title.setText("故障处理");
            holder.orderNum.setText("设备名称：  " + customizeMessage.getOrderNum());
            holder.simpleDraweeView.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + customizeMessage.getPicUrl()));
            holder.creatTime.setText("设备位置：  " + customizeMessage.getCreatTime());
            if (!TextUtils.isEmpty(customizeMessage.getWorkerName())) {
                holder.workerName.setText("维修历史：  " + customizeMessage.getWorkerName());
            } else {
                holder.workerName.setText("维修历史：");
            }
            holder.status.setVisibility(View.GONE);
        } else if (customizeMessage.getShareType().equals("3")) {
            holder.title.setText("工作汇报");
            holder.orderNum.setText("部门：" + customizeMessage.getOrderNum());
            holder.simpleDraweeView.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + customizeMessage.getPicUrl()));
            holder.creatTime.setText("类型：" + GetConstDataUtils.getWorkReportTypeList().get(Integer.parseInt(customizeMessage.getCreatTime())));
            holder.workerName.setText("发布人：" + customizeMessage.getWorkerName());
            holder.status.setText(Integer.parseInt(customizeMessage.getStatus()) == 1 ? "已读" : "未读");
            holder.mTime.setText(customizeMessage.getCreatReleaseTime());
            holder.mTime.setVisibility(View.VISIBLE);
        } else if (customizeMessage.getShareType().equals("4")) {
            holder.title.setText("布置任务");
            holder.orderNum.setText("公司：" + customizeMessage.getOrderNum());
            holder.simpleDraweeView.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + customizeMessage.getPicUrl()));
            holder.creatTime.setText("标题：" + customizeMessage.getCreatTime());
            holder.workerName.setText("发布人：" + customizeMessage.getWorkerName());
            holder.status.setText(Integer.parseInt(customizeMessage.getStatus()) == 1 ? "已读" : "未读");
        } else if (customizeMessage.getShareType().equals("5")) {
            holder.title.setText("设备点检");
            holder.orderNum.setText("检查人：" + customizeMessage.getOrderNum());
            holder.simpleDraweeView.setVisibility(View.GONE);
            holder.creatTime.setText("整改期限：" + customizeMessage.getCreatTime());
            holder.workerName.setText("检查时间：" + customizeMessage.getWorkerName());
            holder.status.setVisibility(View.GONE);
        } else if (customizeMessage.getShareType().equals("6")) {

            holder.title.setText("交接班");
            holder.orderNum.setText("编号：" + customizeMessage.getOrderNum());
            holder.simpleDraweeView.setVisibility(View.GONE);
            holder.creatTime.setText("创建时间：" + customizeMessage.getCreatTime());
            holder.workerName.setText(customizeMessage.getWorkerName());
            if (Integer.parseInt(customizeMessage.getStatus()) == 0) {
                holder.status.setText("待确认");
            } else if (Integer.parseInt(customizeMessage.getStatus()) == 1) {
                holder.status.setText("完成交接");
            } else {
                holder.status.setVisibility(View.GONE);
            }

        } else if (customizeMessage.getShareType().equals("7")) {

            holder.title.setText("面谈员工");
            holder.orderNum.setText("编号：" + customizeMessage.getOrderNum());
            holder.simpleDraweeView.setVisibility(View.GONE);
            holder.creatTime.setText("创建时间：" + customizeMessage.getCreatTime());
            holder.workerName.setText(customizeMessage.getWorkerName());
            holder.status.setText(Integer.parseInt(customizeMessage.getStatus()) == 1 ? "已读" : "未读");

        } else if (customizeMessage.getShareType().equals("8")) {
            holder.title.setText("安防圈");
            holder.orderNum.setText("公司：" + customizeMessage.getOrderNum());
            holder.simpleDraweeView.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + customizeMessage.getPicUrl()));
            holder.creatTime.setText("标题：" + customizeMessage.getCreatTime());
            holder.workerName.setText("发布人：" + customizeMessage.getWorkerName());
            holder.status.setVisibility(View.GONE);
        }


        if (uiMessage.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
            holder.ll_custom.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_custom_right);
        } else {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.simpleDraweeView.getLayoutParams();
            layoutParams.setMargins(50, 0, 0, 0);
            holder.simpleDraweeView.setLayoutParams(layoutParams);


            holder.title.setPadding(20, 10, 0, 0);

            holder.ll_custom.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_custom_left);
        }

    }

    @Override
    public Spannable getContentSummary(CustomizeMessage customizeMessage) {

        if (TextUtils.isEmpty(customizeMessage.getShareType())) {
            return null;
        }

        if (customizeMessage.getShareType().equals("1")) {
            return new SpannableString("报修订单(快去查看吧!)");
        } else if (customizeMessage.getShareType().equals("2")) {
            return new SpannableString("故障处理(快去查看吧!)");
        } else if (customizeMessage.getShareType().equals("3")) {
            return new SpannableString("工作汇报(快去查看吧!)");
        } else if (customizeMessage.getShareType().equals("4")) {
            return new SpannableString("布置任务(快去查看吧!)");
        } else if (customizeMessage.getShareType().equals("5")) {
            return new SpannableString("设备点检(快去查看吧!)");
        } else if (customizeMessage.getShareType().equals("6")) {
            return new SpannableString("交接班(快去查看吧!)");
        } else if (customizeMessage.getShareType().equals("7")) {
            return new SpannableString("面谈员工(快去查看吧!)");
        } else if (customizeMessage.getShareType().equals("8")) {
            return new SpannableString("安防圈(快去查看吧!)");
        }
        return null;
    }

    @Override
    public void onItemClick(View view, int i, CustomizeMessage customizeMessage, UIMessage uiMessage) {

        if (TextUtils.isEmpty(customizeMessage.getShareType())) {
            return;
        }

        if (customizeMessage.getShareType().equals("1")) {
            Intent intent = new Intent(view.getContext(), OrderDetailActivity.class);
            intent.putExtra(Constant.ID, Long.parseLong(customizeMessage.getOrderId()));
            intent.putExtra("title", GetConstDataUtils.getRepairStatus().get(Integer.parseInt(customizeMessage.getStatus())));
            intent.putExtra("orderTime", customizeMessage.getCreatTime());
            intent.putExtra("isVisible", true);
            view.getContext().startActivity(intent);
        } else if (customizeMessage.getShareType().equals("2")) {
            new TroubleDetalilListActivity((Activity) view.getContext(), true, Long.parseLong(customizeMessage.getOrderId()), Integer.parseInt(customizeMessage.getStatus()), true).show();
        } else if (customizeMessage.getShareType().equals("3")) {
//            customizeMessage.setStatus("1");//点击标志位已读
            view.getContext().startActivity(new Intent((Activity) view.getContext(), WorkReportDetailActivity.class).putExtra("id", Long.parseLong(customizeMessage.getOrderId())));
//            new WorkReportInfoView((Activity) view.getContext(), true, Long.parseLong(customizeMessage.getOrderId()), true).show();
        } else if (customizeMessage.getShareType().equals("4")) {
//            new WorkTaskInfoView((Activity) view.getContext(), true, Long.parseLong(customizeMessage.getOrderId()), true).show();
            view.getContext().startActivity(new Intent((Activity) view.getContext(), TaskDetailActivity.class).putExtra("id", Long.parseLong(customizeMessage.getOrderId())));
        } else if (customizeMessage.getShareType().equals("5")) {
//            new WorkCheckInfoView((Activity) view.getContext(), true, Long.parseLong(customizeMessage.getOrderId()), true).show();
            view.getContext().startActivity(new Intent((Activity) view.getContext(), DealWithFirstActivity.class).putExtra("id", Long.parseLong(customizeMessage.getOrderId())));

        } else if (customizeMessage.getShareType().equals("6")) {
            view.getContext().startActivity(new Intent((Activity) view.getContext(), WorkTransferDetailActivity.class).putExtra("itemId", customizeMessage.getOrderId()));
        } else if (customizeMessage.getShareType().equals("7")) {
            view.getContext().startActivity(new Intent((Activity) view.getContext(), WorkTalkDetailActivity.class).putExtra("itemId", customizeMessage.getOrderId()));
        } else if (customizeMessage.getShareType().equals("8")) {
            view.getContext().startActivity(new Intent((Activity) view.getContext(), SecurityDetailActivity.class).putExtra("spcId", Long.parseLong(customizeMessage.getOrderId())));
        }
    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_im_order, null);
        ViewHolder holder = new ViewHolder();
        holder.title = (TextView) view.findViewById(R.id.tv_title);
        holder.status = (TextView) view.findViewById(R.id.tv_state);
        holder.orderNum = (TextView) view.findViewById(R.id.tv_order_id);
        holder.creatTime = (TextView) view.findViewById(R.id.tv_create_time);
        holder.workerName = (TextView) view.findViewById(R.id.tv_person_name);
        holder.simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.iv_upload);
        holder.ll_custom = (LinearLayout) view.findViewById(R.id.ll_custom);
        holder.mTime = view.findViewById(R.id.tv_time);
        view.setTag(holder);
        return view;
    }
}
