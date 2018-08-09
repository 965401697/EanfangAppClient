package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OfferAndPayOrderParentActivity extends BaseWorkerActivity {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_mine_assignment)
    RelativeLayout llMineAssignment;
    @BindView(R.id.ll_mine_accept)
    RelativeLayout llMineAccept;
    @BindView(R.id.ll_mine_company)
    RelativeLayout llMineCompany;
    @BindView(R.id.tv_mine_assignment)
    TextView tvMineAssignment;
    @BindView(R.id.tv_mine_accept)
    TextView tvMineAccept;
    @BindView(R.id.iv_add)
    ImageView ivAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_and_pay_order_parent);
        ButterKnife.bind(this);
        setTitle("报价管控");
        setLeftBack();
        initView();
    }

    private void initView() {

        llMineAccept.setVisibility(View.VISIBLE);
        llMineCompany.setVisibility(View.GONE);
        llMineAssignment.setOnClickListener(v -> jump("我创建的", "1"));
        llMineAccept.setOnClickListener(v -> jump("我负责的", "2"));
        ivAdd.setOnClickListener((v) -> {
            startActivity(new Intent(this, QuotationActivity.class));
        });
    }

    private void jump(String title, String type) {
        Intent intent = new Intent(this, OfferAndPayOrderActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}
