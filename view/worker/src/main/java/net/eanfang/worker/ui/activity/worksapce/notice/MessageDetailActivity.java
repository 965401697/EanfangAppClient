package net.eanfang.worker.ui.activity.worksapce.notice;

import android.os.Bundle;
import android.widget.TextView;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.entity.NoticeEntity;
import com.eanfang.ui.base.BaseActivity;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hutool.core.date.DateUtil;

/**
 * @author Guanluocang
 * @date on 2018/4/20  11:51
 * @decision 业务通知详情
 */
public class MessageDetailActivity extends BaseActivity {
    @BindView(R.id.tv_detailTitle)
    TextView tvDetailTitle;
    @BindView(R.id.tv_detailContent)
    TextView tvDetailContent;
    @BindView(R.id.tv_orderNum)
    TextView tvOrderNum;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_worker)
    TextView tvWorker;
    @BindView(R.id.tv_finishTime)
    TextView tvFinishTime;
    @BindView(R.id.tv_detailTime)
    TextView tvDetailTime;

    //消息ID
    private Long mInfoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_meaasge_detail);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    /**
     * @date on 2018/4/20  11:47
     * @decision 初始化视图
     */
    private void initView() {
        setTitle("业务通知详情");
        setLeftBack();
        mInfoId = getIntent().getLongExtra("infoId", 0);
    }

    /**
     * @date on 2018/4/20  13:33
     * @decision 获取数据
     */
    private void initData() {
        EanfangHttp.post(NewApiService.GET_PUSH_MSG_INFO + mInfoId)
                .execute(new EanfangCallback<NoticeEntity>(MessageDetailActivity.this, true, NoticeEntity.class, (bean -> {
                    runOnUiThread(() -> {
                        tvDetailTitle.setText(bean.getTitle());
                        String extInfo = null;
                        if (bean.getExtInfo() != null && !bean.getExtInfo().toString().contains("{")) {
                            extInfo = bean.getExtInfo().toString();
                        }
                        tvDetailContent.setText(bean.getContent() + "\r\n\t" + "\r\n\t" + (extInfo != null ? extInfo : ""));
                        tvDetailTime.setText(DateUtil.date(bean.getCreateTime()).toString());
                    });
                })));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
