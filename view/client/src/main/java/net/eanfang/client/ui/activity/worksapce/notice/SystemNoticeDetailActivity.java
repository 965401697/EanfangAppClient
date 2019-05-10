package net.eanfang.client.ui.activity.worksapce.notice;

import android.os.Bundle;
import android.widget.TextView;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.NoticeEntity;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GetDateUtils;

import net.eanfang.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Guanluocang
 * @date on 2018/4/23  18:23
 * @decision 系统通知详情
 */
public class SystemNoticeDetailActivity extends BaseActivity {

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_notice_detail);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    /**
     * @date on 2018/4/20  13:30
     * @decision 初始化视图
     */
    private void initView() {
        setTitle("系统通知详情");
        setLeftBack();
        mInfoId = getIntent().getLongExtra("infoId", 0);
    }

    /**
     * @date on 2018/4/20  13:33
     * @decision 获取数据
     */
    private void initData() {
        EanfangHttp.post(NewApiService.GET_PUSH_MSG_INFO + mInfoId)
                .execute(new EanfangCallback<NoticeEntity>(SystemNoticeDetailActivity.this, true, NoticeEntity.class, (bean -> {
                    runOnUiThread(() -> {
                        tvDetailTitle.setText(bean.getTitle());
                        String extInfo = null;
                        if (bean.getExtInfo() != null && !bean.getExtInfo().toString().contains("{")) {
                            extInfo = bean.getExtInfo().toString();
                        }
                        tvDetailContent.setText(bean.getContent() + "\r\n\t" + "\r\n\t" + (extInfo != null ? extInfo : ""));
                        tvDetailTime.setText(GetDateUtils.dateToDateTimeString(bean.getCreateTime()));
                    });
                })));
    }
}
