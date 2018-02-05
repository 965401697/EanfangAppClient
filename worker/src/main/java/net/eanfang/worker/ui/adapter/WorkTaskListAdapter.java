package net.eanfang.worker.ui.adapter;

import android.graphics.Color;
import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.config.EanfangConst;
import com.eanfang.model.WorkTaskListBean;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;

import java.util.List;


/**
 * Created by Mr.hou
 *
 * @on 2017/8/30  15:47
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class WorkTaskListAdapter extends BaseQuickAdapter<WorkTaskListBean.ListBean, BaseViewHolder> {

    public WorkTaskListAdapter(List<WorkTaskListBean.ListBean> data) {
        super(R.layout.item_work_list_layout, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, WorkTaskListBean.ListBean item) {
        helper.setText(R.id.tv_company_name, item.getCreateCompany().getOrgName());
        helper.setText(R.id.tv_depart_name, "部门：" + item.getCreateOrg().getOrgName());
        if (item.getStatus() == EanfangConst.WORK_TASK_STATUS_READ) {
            helper.setText(R.id.tv_read_ns, "已读");
        } else {
            helper.setText(R.id.tv_read_ns, "未读");
            helper.setTextColor(R.id.tv_read_ns, Color.parseColor("#0000ff"));
        }
        helper.setText(R.id.tv_title_name, "标题：" + item.getTitle());
        helper.setText(R.id.tv_pub_time, "发布时间：" + item.getCreateTime());
        helper.setText(R.id.tv_pub_person, "发布人：" + item.getCreateUser().getAccountEntity().getRealName());
        helper.setText(R.id.tv_rev_person, "接收人：" + item.getAssigneeUser().getAccountEntity().getRealName());

        SimpleDraweeView head_pic = helper.getView(R.id.img_head);
        if (!StringUtils.isEmpty(item.getWorkTaskDetail().getPictures())) {
            String[] urls = item.getWorkTaskDetail().getPictures().split(",");
            head_pic.setImageURI(Uri.parse(urls[0]));
        }

    }

}
