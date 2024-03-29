package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.eanfang.biz.model.entity.NoticeEntity;
import com.eanfang.ui.base.BaseDialog;

import net.eanfang.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hutool.core.date.DateUtil;

/**
 * Created by MrHou
 *
 * @on 2017/12/20  10:11
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class MessageDetailView extends BaseDialog {
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_msg_content)
    TextView tvMsgContent;
    private Activity mContext;
    private NoticeEntity listBean;

    //回调函数
    private RefreshListener mRefreshListener;

    public MessageDetailView(Activity context, NoticeEntity listBean) {
        super(context);
        this.mContext = context;
        this.listBean = listBean;
    }

    public MessageDetailView(Activity context, NoticeEntity listBean, RefreshListener refreshListener) {
        super(context);
        this.mRefreshListener = refreshListener;
        this.mContext = context;
        this.listBean = listBean;
    }

    // 回调监听函数
    public interface RefreshListener {
        void refreshData();
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.message_detail_view);
        ButterKnife.bind(this);
        tvType.setText(listBean.getTitle());
        String extInfo = null;
        if (listBean.getExtInfo() != null && !listBean.getExtInfo().toString().contains("{")) {
            extInfo = listBean.getExtInfo().toString();
        }
        tvMsgContent.setText("\n\t" + listBean.getContent() + "\r\n\t" + (extInfo != null ? extInfo : ""));

        tvTime.setText(DateUtil.date(listBean.getCreateTime()).toString());
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mRefreshListener.refreshData();
    }
}
