package com.eanfang.ui.activity.kpbs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.R;
import com.eanfang.R2;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.ToastUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by O u r on 2018/7/3.
 */

public class KPBSDayFragment extends BaseFragment {

    @BindView(R2.id.et_vidicon_num)
    EditText etVidiconNum;
    @BindView(R2.id.et_day)
    EditText etDay;
    @BindView(R2.id.ll_day)
    LinearLayout llDay;
    @BindView(R2.id.et_sd_num)
    EditText etSdNum;
    @BindView(R2.id.ll_sd)
    LinearLayout llSd;
    @BindView(R2.id.tv_kpbs)
    TextView tvKpbs;
    @BindView(R2.id.ll_kpbs)
    LinearLayout llKpbs;
    @BindView(R2.id.et_custom_kpbs)
    EditText etCustomKpbs;
    @BindView(R2.id.ll_custom)
    LinearLayout llCustom;
    @BindView(R2.id.tv_desc)
    TextView tvDesc;
    @BindView(R2.id.tv_result)
    TextView tvResult;
    @BindView(R2.id.tv_clean)
    TextView tvClean;
    @BindView(R2.id.tv_count)
    TextView tvCount;
    @BindView(R2.id.tv_condition)
    TextView tvCondition;
    @BindView(R2.id.rg_condition)
    RadioGroup rgCondition;
    Unbinder unbinder;
    @BindView(R2.id.rg_ruselt_type)
    RadioGroup rgRuseltType;
    @BindView(R2.id.ll_ruselt_type)
    LinearLayout llRuseltType;
    @BindView(R2.id.ll_count_condition)
    LinearLayout llCountCondition;
    @BindView(R2.id.rb_ruselt_dis)
    RadioButton rbRuseltDis;
    @BindView(R2.id.rb_ruselt_kpbs)
    RadioButton rbRuseltKpbs;
    @BindView(R2.id.rb_dis)
    RadioButton rbDis;
    @BindView(R2.id.rb_kpbs)
    RadioButton rbKpbs;


    private int mType;
    private PopupWindow mPopWindow;
    private KPBSAdapter mKPBSAdapter;


    private String[] kpbs = {"0.9Mb/s", "1.8Mb/s", "2.1Mb/s", "3Mb/s", "4.2Mb/s", "4.8Mb/s", "6Mb/s", "7.2Mb/s", "9.6Mb/s", "自定义"};
    private String[] distinguishability = {"D1(4CIF 704*576)", "720P(1280*720)", "960P(1280*960)", "1080P(1920*1080)",
            "3MP(2048*1536)", "4MP(2560*1440)", "5MP(2592*2048)", "8MP(3264*2448)", "4K(3840*2160)"};
    private List<String> currentLists = new ArrayList<>();
    private String currentKpbs;

    public static KPBSDayFragment getInstance(String title, int type) {
        KPBSDayFragment f = new KPBSDayFragment();
        f.mType = type;
        return f;

    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_kpbs_day;
    }

    @Override
    protected void initData(Bundle arguments) {
        currentLists.addAll(Arrays.asList(distinguishability));
    }

    @Override
    protected void initView() {
        etVidiconNum.clearFocus();
        etDay.clearFocus();
        etSdNum.clearFocus();
        etCustomKpbs.clearFocus();
        if (mType == 0) {
            llDay.setVisibility(View.GONE);
            llRuseltType.setVisibility(View.GONE);

        } else if (mType == 1) {
            llSd.setVisibility(View.GONE);
            llRuseltType.setVisibility(View.GONE);
            tvDesc.setText("储存容量：");
        } else {
            llCountCondition.setVisibility(View.GONE);
            llKpbs.setVisibility(View.GONE);
            tvDesc.setText("分辨率：");
        }
    }

    @Override
    protected void setListener() {
        llKpbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });

        tvClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etVidiconNum.setText("");
                etDay.setText("");
                etSdNum.setText("");
                tvResult.setText("");
                tvKpbs.setText("");
            }
        });

        tvCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (llCustom.getVisibility() == View.VISIBLE) {
                    currentKpbs = etCustomKpbs.getText().toString().trim();
                }

                int vidiconNum = 0;
                int sdNum = 0;
                int day = 0;
                float kpbs = 0.0f;
                if (!TextUtils.isEmpty(currentKpbs)) {
                    kpbs = Float.parseFloat(currentKpbs.split("M")[0]);
                }
                if (!TextUtils.isEmpty(etVidiconNum.getText().toString().trim())) {
                    vidiconNum = Integer.parseInt(etVidiconNum.getText().toString().trim());
                } else {
                    ToastUtil.get().showToast(getActivity(), "请输入摄像机数量");
                    return;
                }

                if ((!TextUtils.isEmpty(etSdNum.getText().toString().trim()) && mType == 0) || (!TextUtils.isEmpty(etSdNum.getText().toString().trim()) && mType == 2)) {
                    sdNum = Integer.parseInt(etSdNum.getText().toString().trim());
                } else {
                    if (mType == 0 || mType == 2) {
                        ToastUtil.get().showToast(getActivity(), "请输入硬盘容量");
                        return;
                    }
                }
                if ((!TextUtils.isEmpty(etDay.getText().toString().trim()) && mType == 1) || (!TextUtils.isEmpty(etDay.getText().toString().trim()) && mType == 2)) {
                    day = Integer.parseInt(etDay.getText().toString().trim());
                } else {
                    if (mType == 1 || mType == 2) {
                        ToastUtil.get().showToast(getActivity(), "请输入储存天数");
                        return;
                    }
                }

                if (mType != 2) {
                    if (rbDis.isChecked()) {
                        if (TextUtils.isEmpty(tvKpbs.getText().toString().trim())) {
                            ToastUtil.get().showToast(getActivity(), "请输选择分辨率");
                            return;
                        }
                    } else {
                        if (TextUtils.isEmpty(tvKpbs.getText().toString().trim())) {
                            ToastUtil.get().showToast(getActivity(), "请输选择码率");
                            return;
                        }
                    }
                }


                if (mType == 0) {
                    //算存储时间
                    double temp = new BigDecimal((String.valueOf((sdNum * 1024 * 1024 * 1024)))).multiply(new BigDecimal("8")).doubleValue();
                    double time = Double.parseDouble(String.valueOf(temp / kpbs / 60 / 60 / 24 / vidiconNum));
                    double dTime = SplitAndRound(time, 2);
                    //计算储存时间
                    long temp1 = new BigDecimal((String.valueOf((dTime * 24 * 60 * 60)))).multiply(new BigDecimal("1000")).longValue();
                    tvResult.setText(formatTime(temp1));
                } else if (mType == 1) {
                    double d = SplitAndRound(Double.parseDouble(String.valueOf((kpbs * 60 * 60 * 24 * day * vidiconNum / 8 / 1024 / 1024 / 1024))), 2);
                    if (d < 0.99d) {
                        tvResult.setText(String.valueOf(d * 1024) + "GB");
                    } else if (d < 1.00d) {
                        tvResult.setText(String.valueOf(d) + "TB");
                    } else {
                        tvResult.setText(String.valueOf(Math.rint(d)) + "TB");
                    }
                } else {
                    double temp = new BigDecimal((String.valueOf((sdNum * 1024 * 1024 * 1024)))).multiply(new BigDecimal("8")).doubleValue();
//                    new BigDecimal((String.valueOf((sdNum * 1024 * 1024 * 1024)))).multiply(new BigDecimal("8")).doubleValue()
                    double kp = Double.parseDouble(String.valueOf(temp / day / 60 / 60 / 24 / vidiconNum / 1024));
                    if (rbRuseltDis.isChecked()) {
                        int o = Integer.parseInt((String.valueOf(SplitAndRound(kp, 1))).split("\\.")[0]);
                        int t = Integer.parseInt((String.valueOf(SplitAndRound(kp, 1))).split("\\.")[1]);

                        tvResult.setText(findDis(o, t));
                    } else {
                        tvResult.setText(String.valueOf(SplitAndRound(kp, 1)) + "    Mbps");
                    }
                }
            }
        });
        rgCondition.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                currentLists.clear();
                if (checkedId == R.id.rb_dis) {
                    currentLists.addAll(Arrays.asList(distinguishability));
                    tvCondition.setText("分辨率");
                    tvKpbs.setText("");
                } else {
                    currentLists.addAll(Arrays.asList(kpbs));
                    tvCondition.setText("码率");
                    tvKpbs.setText("");
                }
                tvResult.setText("");
            }
        });

        rgRuseltType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                currentLists.clear();
                if (checkedId == R.id.rb_ruselt_dis) {
                    tvDesc.setText("分辨率：");
                } else {
                    tvDesc.setText("储存码率：");
                }
                tvResult.setText("");
            }
        });
    }

    /**
     * 保留几位小数
     *
     * @param a
     * @param n
     * @return
     */
    public double SplitAndRound(double a, int n) {
        a = a * Math.pow(10, n);
        return (Math.round(a)) / (Math.pow(10, n));
    }

    /**
     * 根据码率拿屏幕分辨率
     *
     * @param o
     * @param t
     * @return
     */
    private String findDis(int o, int t) {
        String dis = "";
        switch (o) {
            case 0:
                dis = distinguishability[o];
                break;
            case 1:
                if (t < 4) {
                    dis = distinguishability[0];
                } else {
                    dis = distinguishability[o];
                }
                break;
            case 2:
                if (t < 5) {
                    dis = distinguishability[1];
                } else {
                    dis = distinguishability[o];
                }
                break;
            case 3:
                dis = distinguishability[o];
                break;
            case 4:
                if (t >= 5) {
                    dis = distinguishability[5];
                } else {
                    dis = distinguishability[4];
                }

                break;
            case 6:
                dis = distinguishability[6];
                break;
            case 7:
                dis = distinguishability[7];
                break;
            case 9:
                dis = distinguishability[8];
                break;
            default:
                dis = "未知分辨率";
                break;
        }
        return dis;
    }

    private void showPopupWindow() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_layout, null);

        if (mPopWindow == null) {
            // 创建PopupWindow对象，其中：
            // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
            // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
            mPopWindow = new PopupWindow(contentView, tvKpbs.getWidth(), LinearLayout.LayoutParams.WRAP_CONTENT);
            // 设置PopupWindow的背景
            mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            // 设置PopupWindow是否能响应外部点击事件
            mPopWindow.setOutsideTouchable(true);
            // 设置PopupWindow是否能响应点击事件
            mPopWindow.setTouchable(true);
            // 或者也可以调用此方法显示PopupWindow，其中：
            // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
            // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
            // window.showAtLocation(parent, gravity, x, y);


            RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mKPBSAdapter = new KPBSAdapter();
            mKPBSAdapter.bindToRecyclerView(recyclerView);


            mKPBSAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (kpbs[9].equals((String) adapter.getData().get(position))) {
                        llCustom.setVisibility(View.VISIBLE);
                    } else {
                        llCustom.setVisibility(View.GONE);
                    }
                    tvKpbs.setText((String) adapter.getData().get(position));
                    currentKpbs = kpbs[position];
                    mPopWindow.dismiss();
                }
            });

        }
        mKPBSAdapter.setNewData(currentLists);
        // 显示PopupWindow，其中：
        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
        mPopWindow.showAsDropDown(tvKpbs, 0, 2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
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

        return sb.toString();
    }


    class KPBSAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public KPBSAdapter() {
            super(R.layout.item_kpbs);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv_name, item);
        }
    }
}
