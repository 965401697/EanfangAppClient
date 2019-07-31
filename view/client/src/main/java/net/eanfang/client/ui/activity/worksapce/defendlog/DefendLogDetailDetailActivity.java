package net.eanfang.client.ui.activity.worksapce.defendlog;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.DateKit;
import com.eanfang.util.GlideUtil;
import com.eanfang.biz.model.entity.LogDetailsEntity;
import com.eanfang.biz.model.entity.ProtectionLogEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hutool.core.date.DateUtil;

public class DefendLogDetailDetailActivity extends BaseClientActivity {

    @BindView(R.id.tv_open_time)
    TextView tvOpenTime;
    @BindView(R.id.tv_close_time)
    TextView tvCloseTime;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.iv_report_header)
    CircleImageView ivReportHeader;
    @BindView(R.id.tv_report_name)
    TextView tvReportName;
    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.tv_week)
    TextView tvWeek;
    @BindView(R.id.tv_data)
    TextView tvData;
    @BindView(R.id.tv_alarm_time)
    TextView tvAralmxTime;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_accept_preson)
    TextView tvAcceptPreson;
    @BindView(R.id.tv_accept_phone)
    TextView tvAcceptPhone;
    @BindView(R.id.tv_section)
    TextView tvSection;
    @BindView(R.id.tv_company_phone)
    TextView tvCompanyPhone;


    private List<String> mTitleList = new ArrayList<>();
    private List<DefendLogItemAdapter> mAdapterList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_defend_log_detail_detail);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("日志详情");
        setLeftBack();

        initViews();
    }

    private void initViews() {

        String mId = getIntent().getStringExtra("id");
        getDetail(mId);


        mTitleList.add("旁路");
        mTitleList.add("闯防");
        mTitleList.add("误报");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DefendLogAdapter defendLogAdapter = new DefendLogAdapter(R.layout.item_defend_log, this,2);
        defendLogAdapter.setNewData(mTitleList);
        mAdapterList = defendLogAdapter.getmList();//如果是添加数据  这里为空

        defendLogAdapter.bindToRecyclerView(recyclerView);
    }


    /**
     * 获取布防日志详情
     *
     * @param
     */
    private void getDetail(String id) {

        EanfangHttp.get(NewApiService.OA_DEFEND_LOG_DETAIL)
                .params("protectionLogId", id)
                .execute(new EanfangCallback<ProtectionLogEntity>(DefendLogDetailDetailActivity.this, true, ProtectionLogEntity.class, bean -> {


                    if (bean.getOwnerUser() != null && bean.getOwnerUser().getAccountEntity() != null) {
                        GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + bean.getOwnerUser().getAccountEntity().getAvatar()),ivReportHeader);
                        tvCompanyPhone.setText(bean.getOwnerUser().getAccountEntity().getMobile());
                        tvReportName.setText(bean.getOwnerUser().getAccountEntity().getRealName());
                    }
                    tvSection.setText(bean.getOwnerDepartment().getOrgName());


                    String[] date = DateUtil.date(bean.getCreateTime()).toDateStr().split("-");
                    tvYear.setText(date[0] + "-" + date[1]);
                    tvWeek.setText(DateKit.get(bean.getCreateTime()).weekName());
                    tvData.setText(date[2]);

                    tvCompanyName.setText(bean.getOwnerCompany().getOrgName());
                    tvAcceptPreson.setText(bean.getAssigneeUser().getAccountEntity().getRealName());
                    tvAcceptPhone.setText(bean.getAssigneeUser().getAccountEntity().getMobile());


                    tvOpenTime.setText(DateUtil.date(bean.getOpenTime()).toString());
                    tvCloseTime.setText(DateUtil.date(bean.getCloseTime()).toString());

                    long time = bean.getCloseTime().getTime() - bean.getOpenTime().getTime();
                    if (!TextUtils.isEmpty(formatTime(time))) {
                        tvAralmxTime.setText(formatTime(time));
                    } else {
                        tvAralmxTime.setText("太短了");
                    }

                    List<List<LogDetailsEntity>> listBeans = new ArrayList<>();
                    listBeans.add(bean.getBypassList());
                    listBeans.add(bean.getThroughList());
                    listBeans.add(bean.getFalseList());

                    for (int i = 0; i < mAdapterList.size(); i++) {
                        mAdapterList.get(i).setNewData(listBeans.get(i));
                    }
                }));
    }

    /*
* 毫秒转化时分秒毫秒
*/
    public static String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            sb.append(day + "天");
        }
        if (hour > 0) {
            sb.append(hour + "小时");
        }
        if (minute > 0) {
            sb.append(minute + "分");
        }
        if (milliSecond > 0) {
            sb.append(milliSecond + "秒");
        }

        return sb.toString();
    }
}
