package net.eanfang.client.ui.activity.worksapce.repair;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.biz.model.QueryEntry;
import com.eanfang.biz.model.entity.RepairPersonalInfoEntity;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hutool.core.util.StrUtil;

/**
 * @author guanluocang
 * @data 2019/8/12  18:47
 * @description 电话报修
 */

public class RepairPhoneActivity extends BaseActivity {

    private static final int REQUEST_PERSONAL_INFO = 1008;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_default)
    TextView tvDefault;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_home_type)
    TextView tvHomeType;
    @BindView(R.id.tv_home_address)
    TextView tvHomeAddress;
    @BindView(R.id.iv_info_right)
    ImageView ivInfoRight;
    @BindView(R.id.iv_address)
    ImageView ivAddress;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_createPersonalInfo)
    TextView tvCreatePersonalInfo;
    @BindView(R.id.ll_personalInfoTop)
    LinearLayout llPersonalInfoTop;
    @BindView(R.id.ll_noPersonalInfo)
    LinearLayout llNoPersonalInfo;
    /**
     * 个人信息
     */
    private RepairPersonalInfoEntity.ListBean repairPersonalInfoEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_phone);
        ButterKnife.bind(this);
        initView();
    }

    protected void initView() {
        setTitle("电话报修");
        setLeftBack();
        doChekInfo();
    }

    /**
     * 判断是否有无个人信息
     */
    private void doChekInfo() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("accId", ClientApplication.get().getAccId() + "");
        EanfangHttp.post(NewApiService.REPAIR_PERSONAL_INFO_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<RepairPersonalInfoEntity>(this, true, RepairPersonalInfoEntity.class, bean -> {
                    if (bean.getList() != null && bean.getList().size() > 0) {
                        ivInfoRight.setVisibility(View.VISIBLE);
                        llPersonalInfoTop.setVisibility(View.VISIBLE);
                        llNoPersonalInfo.setVisibility(View.GONE);
                        repairPersonalInfoEntity = bean.getList().get(0);
                        doSetPersonalInfo(repairPersonalInfoEntity);
                    } else {
                        llPersonalInfoTop.setVisibility(View.GONE);
                        llNoPersonalInfo.setVisibility(View.VISIBLE);
                    }
                }));
    }

    public void doSetPersonalInfo(RepairPersonalInfoEntity.ListBean bean) {
        if (bean == null) {
            llPersonalInfoTop.setVisibility(View.GONE);
            llNoPersonalInfo.setVisibility(View.VISIBLE);
            return;
        }
        llPersonalInfoTop.setVisibility(View.VISIBLE);
        llNoPersonalInfo.setVisibility(View.GONE);
        //姓名
        tvName.setText(bean.getName());
        // 性别0女1男
        tvSex.setText(bean.getGender() == 0 ? " (女士) " : " (先生) ");
        // 电话
        tvPhone.setText(bean.getPhone());
        // 单位
        if (!StrUtil.isEmpty(bean.getSelectAddress())) {
            tvHomeType.setText("[" + bean.getSelectAddress() + "]");
        }
        tvHomeAddress.setText(bean.getConmpanyName());
        // 地址
        tvAddress.setText(bean.getAddress());
        tvDefault.setVisibility(bean.getIsDefault() == 1 ? View.VISIBLE : View.GONE);
    }


    @OnClick({R.id.ll_personalInfoTop, R.id.ll_noPersonalInfo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_personalInfoTop:
            case R.id.ll_noPersonalInfo:
                JumpItent.jump(this, RepairPersonInfoListActivity.class, REQUEST_PERSONAL_INFO);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            // 修改当前默认
            case REQUEST_PERSONAL_INFO:
                repairPersonalInfoEntity = (RepairPersonalInfoEntity.ListBean) data.getSerializableExtra("infoEntity");
                doSetPersonalInfo(repairPersonalInfoEntity);
                break;
            default:
                break;
        }
    }
}
