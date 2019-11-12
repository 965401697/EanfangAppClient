package net.eanfang.worker.ui.adapter.neworder;

import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.entity.OrderBean;
import com.eanfang.biz.model.entity.WorkerOrderOerationEntity;
import com.eanfang.config.Config;
import com.eanfang.util.GetConstDataUtils;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;

import cn.hutool.core.date.DateUtil;

/**
 * @author guanluocang
 * @data 2019/10/28
 * @description 首页订单Adapter
 */
public class HomeOrderAdapter extends BaseQuickAdapter<OrderBean, BaseViewHolder> {
    public HomeOrderAdapter() {
        super(R.layout.layout_home_new_order_item);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderBean item) {

        //创建订单操作类( 0:待支付，1:待回电，2:待上门，3:待完工，4:待确认，5:订单完成)
        int status = item.getStatus();
        boolean custom = item.getAssigneeUserId() != null && item.getAssigneeUserId().equals(WorkerApplication.get().getUserId());
        WorkerOrderOerationEntity orderOerationEntity = new WorkerOrderOerationEntity(status, custom);

        // 维修 维保
        helper.setText(R.id.tv_order_type_second, Config.get().getBaseNameByCode(1, item.getSysCode(), 1));
        // 电视监控
        helper.setText(R.id.tv_order_type_first, GetConstDataUtils.getNewOrderType().get(item.getType()));
        // 待预约
        if (item.getStatus() == 6) {
            helper.setText(R.id.tv_order_type, "订单取消");
        } else {
            helper.setText(R.id.tv_order_type, GetConstDataUtils.getRepairStatus().get(item.getStatus()));
        }
        // 地址
        helper.setText(R.id.tv_address, item.getAddress());
        // 单位
        helper.setText(R.id.tv_company, item.getContactCompany());
        // 技师 是否是自己的单子
        if (item.getAssigneeUserId() != null) {
            if (item.getAssigneeUserId().equals(WorkerApplication.get().getUserId())) {
                helper.setText(R.id.tv_contactName, "现场联系人：");
                helper.setText(R.id.tv_worder, item.getContactName());
            } else {
                helper.setText(R.id.tv_contactName, "负责技师：");
                helper.setText(R.id.tv_worder, item.getAssigneeUserName());
            }
        }

        // 时间
        helper.setText(R.id.tv_time, DateUtil.date(item.getCreateTime()).toString());


        helper.setText(R.id.tv_do_first, orderOerationEntity.getOperationLeft());
        helper.setText(R.id.tv_do_second, orderOerationEntity.getOperationRight());
        helper.setGone(R.id.tv_do_first, orderOerationEntity.isShowOperationLeft());
        //原代码判断status为5的时候判断item.getClientEvaluateId()<=0展示否则不展示第二按钮
//        helper.setGone(R.id.tv_do_second, orderOerationEntity.isShowOperationRight() || (custom && status == 5 &&
//                (item.getClientEvaluateId() == null || item.getClientEva luateId() <= 0)));

        //将业务类型的图片显示到列表
        helper.addOnClickListener(R.id.tv_do_first);
        helper.addOnClickListener(R.id.tv_do_second);

    }
}
