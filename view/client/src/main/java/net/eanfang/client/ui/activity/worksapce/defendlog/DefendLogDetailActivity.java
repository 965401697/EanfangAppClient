package net.eanfang.client.ui.activity.worksapce.defendlog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GetDateUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.base.entity.LogDetailsEntity;
import com.yaf.base.entity.ProtectionLogEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DefendLogDetailActivity extends BaseClientActivity {

    @BindView(R.id.tv_open_time)
    TextView tvOpenTime;
    @BindView(R.id.tv_close_time)
    TextView tvCloseTime;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.iv_report_header)
    SimpleDraweeView ivReportHeader;
    @BindView(R.id.tv_report_name)
    TextView tvReportName;
    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.tv_week)
    TextView tvWeek;
    @BindView(R.id.tv_data)
    TextView tvData;
    @BindView(R.id.tv_one)
    TextView tvOne;
    @BindView(R.id.tv_two)
    TextView tvTwo;
    @BindView(R.id.tv_three)
    TextView tvThree;
    @BindView(R.id.tv_four)
    TextView tvFour;
    @BindView(R.id.tv_five)
    TextView tvFive;
    @BindView(R.id.tv_time)
    TextView tvTime;
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
    private List<DefendLogDetailItemAdapter> mAdapterList;
    private String mId;

    private ProtectionLogEntity protectionLogEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defend_log_detail);
        ButterKnife.bind(this);
        setTitle("布防日志");
        setLeftBack();

        initViews();

        boolean isVisible = getIntent().getBooleanExtra("isVisible", false);

        if (isVisible) {
            setRightTitle("查看详情");
            setRightTitleOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DefendLogDetailActivity.this, DefendLogDetailDetailActivity.class).putExtra("id", mId));
                }
            });
        } else {

            setRightImageResId(R.mipmap.more);
            setRightImageOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DefendMorePopWindow defendMorePopWindow = new DefendMorePopWindow(DefendLogDetailActivity.this, protectionLogEntity, isVisible);
                    defendMorePopWindow.showPopupWindow(findViewById(R.id.ll_right));
                }
            });
        }
    }

    private void initViews() {

        mId = getIntent().getStringExtra("id");
        getDetail(mId);


        mTitleList.add("旁路");
        mTitleList.add("闯防");
        mTitleList.add("误报");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        DefendLogDetailAdapter defendLogDetailAdapter = new DefendLogDetailAdapter(R.layout.item_defend_log_detail, this);
        defendLogDetailAdapter.setNewData(mTitleList);
        mAdapterList = defendLogDetailAdapter.getmList();//如果是添加数据  这里为空

        defendLogDetailAdapter.bindToRecyclerView(recyclerView);
        setLeftBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finishSelf();
            }
        });
    }


    /**
     * 获取布防日志详情
     *
     * @param
     */
    private void getDetail(String id) {

        EanfangHttp.get(NewApiService.OA_DEFEND_LOG_DETAIL)
                .params("protectionLogId", id)
                .execute(new EanfangCallback<ProtectionLogEntity>(DefendLogDetailActivity.this, true, ProtectionLogEntity.class, bean -> {

                    protectionLogEntity = bean;

                    if (bean.getOwnerUser() != null && bean.getOwnerUser().getAccountEntity() != null) {
                        ivReportHeader.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + bean.getOwnerUser().getAccountEntity().getAvatar()));
                        tvCompanyPhone.setText(bean.getOwnerUser().getAccountEntity().getMobile());
                        tvReportName.setText(bean.getOwnerUser().getAccountEntity().getRealName());
                    }
                    tvSection.setText(bean.getOwnerDepartment().getOrgName());


                    String[] date = GetDateUtils.dateToDateString(bean.getCreateTime()).split("-");
                    tvYear.setText(date[0] + "-" + date[1]);
                    tvWeek.setText(GetDateUtils.dateToWeek(GetDateUtils.dateToDateString(bean.getCreateTime())));
                    tvData.setText(date[2]);

                    tvCompanyName.setText(bean.getOwnerCompany().getOrgName());
                    tvAcceptPreson.setText(bean.getAssigneeUser().getAccountEntity().getRealName());
                    tvAcceptPhone.setText(bean.getAssigneeUser().getAccountEntity().getMobile());


                    tvOpenTime.setText(GetDateUtils.dateToDateTimeString(bean.getOpenTime()));
                    tvCloseTime.setText(GetDateUtils.dateToDateTimeString(bean.getCloseTime()));

                    long time = bean.getCloseTime().getTime() - bean.getOpenTime().getTime();
                    if (!TextUtils.isEmpty(formatTime(time))) {
                        tvTime.setText(formatTime(time));
                    } else {
                        tvTime.setText("太短了");
                    }

                    List<List<LogDetailsEntity>> listBeans = new ArrayList<>();
                    listBeans.add(bean.getBypassList());
                    listBeans.add(bean.getThroughList());
                    listBeans.add(bean.getFalseList());
//                    误闯=员工闯防+顾客闯防+其他闯防
//:
//                    入寝=真实报警
//:
//                    火警=火警误报
                    tvOne.setText(sumCount(bean.getBypassList(), ""));
                    int sum = Integer.parseInt(sumCount(bean.getFalseList(), "动物闯防")) + Integer.parseInt(sumCount(bean.getThroughList(), "设备故障")) + Integer.parseInt(sumCount(bean.getThroughList(), "其他误报"));
                    tvTwo.setText(String.valueOf(sum));
                    int sum1 = Integer.parseInt(sumCount(bean.getThroughList(), "员工闯防")) + Integer.parseInt(sumCount(bean.getThroughList(), "顾客闯防")) + Integer.parseInt(sumCount(bean.getThroughList(), "其他闯防"));
                    tvThree.setText(String.valueOf(sum1));
                    tvFour.setText(sumCount(bean.getFalseList(), "真实报警"));
                    tvFive.setText(sumCount(bean.getFalseList(), "火警误报"));

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

    /**
     * 求集合的报警次数
     *
     * @param list
     * @param name
     */
    private String sumCount(List<LogDetailsEntity> list, String name) {

        int sum = 0;

        if (!TextUtils.isEmpty(name)) {
            for (LogDetailsEntity bean : list) {
                if (bean.getLogType() == 1) {

                    if (GetConstDataUtils.getThroughCause().get(bean.getAlarmReason()).equals(name)) {

                        sum += bean.getAlarmNum();
                    }
                } else if (bean.getLogType() == 2) {
                    if (GetConstDataUtils.getFlaseCause().get(bean.getAlarmReason()).equals(name)) {

                        sum += bean.getAlarmNum();
                    }
                }
            }
            return String.valueOf(sum);
        } else {
            for (LogDetailsEntity bean : list) {
                sum += bean.getAlarmNum();
            }
            return String.valueOf(sum);
        }

    }
    /**
     * 监听 返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            setResult(RESULT_OK);
            finishSelf();
        }
        return false;
    }
}

