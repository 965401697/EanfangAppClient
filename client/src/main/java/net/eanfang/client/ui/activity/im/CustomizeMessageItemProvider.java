package net.eanfang.client.ui.activity.im;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.config.Constant;
import com.eanfang.model.WorkCheckListBean;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GetDateUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.RepairOrderEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.MainActivity;
import net.eanfang.client.ui.activity.worksapce.OrderDetailActivity;
import net.eanfang.client.ui.activity.worksapce.TroubleDetalilListActivity;
import net.eanfang.client.ui.activity.worksapce.defendlog.DefendLogDetailActivity;
import net.eanfang.client.ui.activity.worksapce.openshop.OpenShopLogDetailActivity;
import net.eanfang.client.ui.widget.WorkCheckInfoView;
import net.eanfang.client.ui.widget.WorkReportInfoView;
import net.eanfang.client.ui.widget.WorkTaskInfoView;

import io.rong.imkit.emoticon.AndroidEmoji;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.SingleChoiceAdapter;
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
        SimpleDraweeView simpleDraweeView;
    }

    @Override
    public void bindView(View view, int i, CustomizeMessage customizeMessage, UIMessage uiMessage) {
        ViewHolder holder = (ViewHolder) view.getTag();

        if (customizeMessage.getShareType().equals("1")) {
            holder.title.setText("报修订单");
            holder.orderNum.setText("单号：" + customizeMessage.getOrderNum());
            holder.simpleDraweeView.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + customizeMessage.getPicUrl()));
            holder.creatTime.setText("下单时间：" + customizeMessage.getCreatTime());
            holder.workerName.setText("负责人：" + customizeMessage.getWorkerName());
            holder.status.setText(GetConstDataUtils.getRepairStatus().get(Integer.parseInt(customizeMessage.getStatus())));
        } else if (customizeMessage.getShareType().equals("2")) {
            holder.title.setText("故障处理");
            holder.orderNum.setText("设备名称：" + customizeMessage.getOrderNum());
            holder.simpleDraweeView.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + customizeMessage.getPicUrl()));
            holder.creatTime.setText("设备位置：" + customizeMessage.getCreatTime());
            if (!TextUtils.isEmpty(customizeMessage.getWorkerName())) {
                holder.workerName.setText("维修历史：" + customizeMessage.getWorkerName());
            } else {
                holder.workerName.setText("维修历史：");
            }
            holder.status.setVisibility(View.INVISIBLE);
        } else if (customizeMessage.getShareType().equals("3")) {
            holder.title.setText("工作汇报");
            holder.orderNum.setText("部门：" + customizeMessage.getOrderNum());
            holder.simpleDraweeView.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + customizeMessage.getPicUrl()));
            holder.creatTime.setText("类型：" + GetConstDataUtils.getWorkReportTypeList().get(Integer.parseInt(customizeMessage.getCreatTime())));
            holder.workerName.setText("发布人：" + customizeMessage.getWorkerName());
            holder.status.setText(Integer.parseInt(customizeMessage.getStatus()) == 1 ? "已读" : "未读");
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
            holder.creatTime.setText("负责人：" + customizeMessage.getCreatTime());
            holder.workerName.setText("整改期限：" + customizeMessage.getWorkerName());
            holder.status.setVisibility(View.INVISIBLE);
        } else if (customizeMessage.getShareType().equals("6")) {

        } else if (customizeMessage.getShareType().equals("7")) {

        } else if (customizeMessage.getShareType().equals("8")) {
            holder.title.setText("开店日志");
            holder.orderNum.setText("单号编号：" + customizeMessage.getOrderNum());
            holder.simpleDraweeView.setVisibility(View.GONE);
            holder.creatTime.setText("创建时间：" + customizeMessage.getCreatTime());
            holder.workerName.setText("接收人：" + customizeMessage.getWorkerName());
            holder.status.setText(Integer.parseInt(customizeMessage.getStatus()) == 1 ? "已读" : "未读");
        } else if (customizeMessage.getShareType().equals("9")) {
            holder.title.setText("布防日志");
            holder.orderNum.setText("单号编号：" + customizeMessage.getOrderNum());
            holder.simpleDraweeView.setVisibility(View.GONE);
            holder.creatTime.setText("创建时间：" + customizeMessage.getCreatTime());
            holder.workerName.setText("接收人：" + customizeMessage.getWorkerName());
            holder.status.setText(Integer.parseInt(customizeMessage.getStatus()) == 1 ? "已读" : "未读");
        }
    }

    @Override
    public Spannable getContentSummary(CustomizeMessage customizeMessage) {
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

        } else if (customizeMessage.getShareType().equals("7")) {

        } else if (customizeMessage.getShareType().equals("8")) {
            return new SpannableString("开店日志(快去查看吧!)");
        } else if (customizeMessage.getShareType().equals("9")) {
            return new SpannableString("布防日志(快去查看吧!)");
        }
        return null;
    }

    @Override
    public void onItemClick(View view, int i, CustomizeMessage customizeMessage, UIMessage uiMessage) {
        if (customizeMessage.getShareType().equals("1")) {
            Intent intent = new Intent(view.getContext(), OrderDetailActivity.class);
            intent.putExtra(Constant.ID, Long.parseLong(customizeMessage.getOrderId()));
            intent.putExtra("title", GetConstDataUtils.getRepairStatus().get(Integer.parseInt(customizeMessage.getStatus())));
            intent.putExtra("orderTime", customizeMessage.getCreatTime());
            intent.putExtra("isVisible", true);
            view.getContext().startActivity(intent);
        } else if (customizeMessage.getShareType().equals("2")) {
            new TroubleDetalilListActivity((Activity) view.getContext(), true, Long.parseLong(customizeMessage.getOrderId()), Integer.parseInt(customizeMessage.getStatus()), "完成", true).show();
        } else if (customizeMessage.getShareType().equals("3")) {
            new WorkReportInfoView((Activity) view.getContext(), true, Long.parseLong(customizeMessage.getOrderId()), true).show();
        } else if (customizeMessage.getShareType().equals("4")) {
            new WorkTaskInfoView((Activity) view.getContext(), true, Long.parseLong(customizeMessage.getOrderId()), true).show();
        } else if (customizeMessage.getShareType().equals("5")) {
            new WorkCheckInfoView((Activity) view.getContext(), true,  Long.parseLong(customizeMessage.getOrderId()),true).show();

        } else if (customizeMessage.getShareType().equals("6")) {

        } else if (customizeMessage.getShareType().equals("7")) {

        } else if (customizeMessage.getShareType().equals("8")) {
            view.getContext().startActivity(new Intent((Activity) view.getContext(), OpenShopLogDetailActivity.class).putExtra("id", customizeMessage.getOrderId()).putExtra("isVisible", true));
        } else if (customizeMessage.getShareType().equals("9")) {
            view.getContext().startActivity(new Intent((Activity) view.getContext(), DefendLogDetailActivity.class).putExtra("id", customizeMessage.getOrderId()).putExtra("isVisible", true));

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
        view.setTag(holder);
        return view;
    }
}
