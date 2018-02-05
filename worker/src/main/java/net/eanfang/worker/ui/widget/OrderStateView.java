//package net.eanfang.worker.ui.widget;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.RelativeLayout;
//
//import BaseDialog;
//import com.eanfang.witget.SwitchButton;
//
//import net.eanfang.worker.R;
//import net.eanfang.worker.util.PrefUtils;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
///**
// * Created by MrHou
// *
// * @on 2017/11/25  15:04
// * @email houzhongzhou@yeah.net
// * @desc
// */
//
//public class OrderStateView extends BaseDialog {
//
//
//    @BindView(R.id.sb_person_repair)
//    SwitchButton sbPersonRepair;
//    @BindView(R.id.ll_person_repair)
//    RelativeLayout llPersonRepair;
//    @BindView(R.id.sb_company_repair)
//    SwitchButton sbCompanyRepair;
//    @BindView(R.id.ll_company_repair)
//    RelativeLayout llCompanyRepair;
//    @BindView(R.id.sb_company_install)
//    SwitchButton sbCompanyInstall;
//    @BindView(R.id.ll_company_install)
//    RelativeLayout llCompanyInstall;
//    private Activity mContext;
//
//    public OrderStateView(Activity context) {
//        super(context);
//        this.mContext = context;
//    }
//
//
//    @Override
//    protected void initCustomView(Bundle savedInstanceState) {
//        setContentView(R.layout.activity_stop_order);
//        ButterKnife.bind(this);
//        initView();
//    }
//
//    private void initView() {
//        sbPersonRepair.setChecked(PrefUtils.getVBoolean(mContext, PrefUtils.PERSON_REPAIR_SWITCH_CHECK));
//        Log.e("lookcheck", String.valueOf(PrefUtils.getVBoolean(mContext, PrefUtils.PERSON_REPAIR_SWITCH_CHECK)));
//        sbPersonRepair.setOnCheckedChangeListener((view, isChecked) -> {
//            PrefUtils.setBoolean(mContext, PrefUtils.PERSON_REPAIR_SWITCH_CHECK, isChecked);
//        });
//        sbCompanyRepair.setChecked(PrefUtils.getVBoolean(mContext, PrefUtils.COMPANY_REPAIR_SWITCH_CHECK));
//        sbCompanyRepair.setOnCheckedChangeListener((view, isChecked) -> {
//            PrefUtils.setBoolean(mContext, PrefUtils.COMPANY_REPAIR_SWITCH_CHECK, isChecked);
//        });
//        sbCompanyInstall.setChecked(PrefUtils.getVBoolean(mContext, PrefUtils.COMPANY_INSTALL_SWITCH_CHECK));
//        sbCompanyInstall.setOnCheckedChangeListener((view, isChecked) -> {
//            PrefUtils.setBoolean(mContext, PrefUtils.COMPANY_INSTALL_SWITCH_CHECK, isChecked);
//        });
//
//
//    }
//
//}
