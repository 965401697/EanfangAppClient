package net.eanfang.client.ui.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.util.GetDateUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;
import net.eanfang.client.ui.model.WorkCheckListBean;
import net.eanfang.client.util.StringUtils;

import java.util.List;


/**
 * Created by Mr.hou
 *
 * @on 2017/8/30  15:47
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class WorkCheckListAdapter extends BaseQuickAdapter<WorkCheckListBean.AllBean, BaseViewHolder> {

    public WorkCheckListAdapter(List<WorkCheckListBean.AllBean> data) {
        super(R.layout.item_check_list_layout, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, WorkCheckListBean.AllBean item) {
        helper.setText(R.id.tv_company_name, item.getCompanyName());
        helper.setText(R.id.tv_check_person, "检查人：" + item.getCreatUserName());
        helper.setText(R.id.tv_duty_person, "负责人：" + item.getReceiveUserName());
        helper.setText(R.id.tv_check_time, "检查时间：" + item.getCreateDate());
        String changeLine = item.getChangeDeadline();

        helper.setText(R.id.tv_change_tiem, "整改期限：" + GetDateUtils.dateToDateString(GetDateUtils.getDate(changeLine)));

        SimpleDraweeView head_pic = helper.getView(R.id.img_head);
        if (!StringUtils.isEmpty(item.getPic1())) {
            head_pic.setImageURI(Uri.parse(item.getPic1()));
        }

    }

}
