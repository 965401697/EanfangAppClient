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
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GetDateUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.RepairOrderEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.MainActivity;
import net.eanfang.client.ui.activity.worksapce.OrderDetailActivity;
import net.eanfang.client.ui.activity.worksapce.TroubleDetalilListActivity;

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

//        if (uiMessage.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
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
            holder.status.setVisibility(View.INVISIBLE);
        }
//        }
    }

    @Override
    public Spannable getContentSummary(CustomizeMessage customizeMessage) {
        if (customizeMessage.getShareType().equals("1")) {
            return new SpannableString("报修订单(快去查看吧!)");
        } else if (customizeMessage.getShareType().equals("2")) {
            return new SpannableString("故障处理(快去查看吧!)");
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