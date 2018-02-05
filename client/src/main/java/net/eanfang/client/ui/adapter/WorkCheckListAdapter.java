package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.WorkCheckListBean;

import net.eanfang.client.R;

import java.util.List;


/**
 * Created by Mr.hou
 *
 * @on 2017/8/30  15:47
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class WorkCheckListAdapter extends BaseQuickAdapter<WorkCheckListBean.ListBean, BaseViewHolder> {

    public WorkCheckListAdapter(List<WorkCheckListBean.ListBean> data) {
        super(R.layout.item_check_list_layout, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, WorkCheckListBean.ListBean item) {
        helper.setText(R.id.tv_company_name, item.getCompanyName());
        helper.setText(R.id.tv_check_person, "检查人：" + item.getCreateUser().getAccountEntity().getRealName());
        helper.setText(R.id.tv_duty_person, "负责人：" + item.getAssigneeUser().getAccountEntity().getRealName());
        helper.setText(R.id.tv_check_time, "检查时间：" + item.getCreateTime());
        helper.setText(R.id.tv_change_tiem, "整改期限：" + item.getChangeDeadlineTime());

//        SimpleDraweeView head_pic = helper.getView(R.id.img_head);
//        if (!StringUtils.isEmpty(item.getPic1())) {
//            head_pic.setImageURI(Uri.parse(item.getPic1()));
//        }

    }

}
