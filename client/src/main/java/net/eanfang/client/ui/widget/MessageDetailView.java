package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.eanfang.model.NoticeEntity;
import com.eanfang.ui.base.BaseDialog;

import net.eanfang.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    public MessageDetailView(Activity context, NoticeEntity listBean) {
        super(context);
        this.mContext = context;
        this.listBean = listBean;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.message_detail_view);
        ButterKnife.bind(this);
        tvType.setText("类型：" + listBean.getNoticeType());
        tvTime.setText("时间：" + listBean.getCreateTime());
        tvMsgContent.setText("内容：" + "\n" + listBean.getParams());
    }
}
