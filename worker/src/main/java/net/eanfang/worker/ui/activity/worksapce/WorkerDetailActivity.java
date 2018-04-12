//package net.eanfang.worker.ui.activity.worksapce;
//
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.chad.library.adapter.base.listener.OnItemClickListener;
//import com.eanfang.model.ToRepairBean;
//import com.eanfang.model.WorkerDetailsBean;
//import com.eanfang.util.StringUtils;
//import com.eanfang.util.ToastUtil;
//
//import net.eanfang.worker_logo.R;
//import net.eanfang.worker_logo.ui.adapter.WorkerDetailAdapter;
//import net.eanfang.worker_logo.ui.base.BaseWorkerActivity;
//import net.eanfang.worker_logo.util.ImagePerviewUtil;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//
///**
// * 技师详情
// * Created by Administrator on 2017/4/8.
// */
//
//public class WorkerDetailActivity extends BaseWorkerActivity implements View.OnClickListener {
//
//    private String id;
//    private com.facebook.drawee.view.SimpleDraweeView iv_header;
//    private TextView tv_realname;
//    private TextView tv_company_name;
//    private TextView tv_number;
//    private TextView tv_koubei;
//    private TextView tv_level;
//    private TextView tv_year;
//    private TextView tv_code;
//    private TextView tv_address;
//    private android.support.v7.widget.RecyclerView rv_list1;
//    private android.support.v7.widget.RecyclerView rv_list3;
//    private com.facebook.drawee.view.SimpleDraweeView iv_pic1;
//    private com.facebook.drawee.view.SimpleDraweeView iv_pic2;
//    private com.facebook.drawee.view.SimpleDraweeView iv_pic3;
//    private com.facebook.drawee.view.SimpleDraweeView iv_pic4;
//    private com.daimajia.numberprogressbar.NumberProgressBar iv_haopinglv;
//    private TextView tv_haopinglv;
//    private me.zhanghai.android.materialratingbar.MaterialRatingBar rb_star1;
//    private me.zhanghai.android.materialratingbar.MaterialRatingBar rb_star2;
//    private me.zhanghai.android.materialratingbar.MaterialRatingBar rb_star3;
//    private me.zhanghai.android.materialratingbar.MaterialRatingBar rb_star4;
//    private me.zhanghai.android.materialratingbar.MaterialRatingBar rb_star5;
//    private TextView tv_select;
//
//    private ArrayList<String> mDataList1;
//    private ArrayList<String> mDataList3;
//
//    private WorkerDetailsBean workerDetailsBean;
//    private LinearLayout ll_repair;
//    private LinearLayout ll_install;
//    private ToRepairBean toRepairBean;
//    private String honor1Pic;
//    private String honor2Pic;
//    private String honor3Pic;
//    private String honor4Pic;
//    private ArrayList<String> picList = new ArrayList<>();
//    private LinearLayout ll_area;
//    private ImageView iv_down;
//
//    public static void jumpActivity(Context context) {
//        Intent intent = new Intent();
//        intent.setClass(context, WorkerDetailActivity.class);
////        intent.putExtra("id", EanfangApplication.get().getUser().getPersonId());
//        ((BaseWorkerActivity) context).startAnimActivity(intent);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_worker_detail);
//        getData();
//        initView();
//        setListener();
//        initData();
//        initAdapter();
//
//        setTitle("技师详情");
//        setLeftBack();
//    }
//
//    private void setListener() {
//        if (toRepairBean == null) {
//            tv_select.setVisibility(View.GONE);
////            return;
//        }
//        tv_select.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(WorkerDetailActivity.this, OrderConfirmActivity.class);
//                intent.putExtra("bean", toRepairBean);
//                intent.putExtra("id", id);
//                startActivity(intent);
//            }
//        });
//        iv_pic1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ImagePerviewUtil.perviewImage(WorkerDetailActivity.this, picList);
//            }
//        });
//        iv_pic2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ImagePerviewUtil.perviewImage(WorkerDetailActivity.this, picList);
//            }
//        });
//        iv_pic3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ImagePerviewUtil.perviewImage(WorkerDetailActivity.this, picList);
//            }
//        });
//        iv_pic4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ImagePerviewUtil.perviewImage(WorkerDetailActivity.this, picList);
//            }
//        });
//        ll_area.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (rv_list1.getVisibility() == View.VISIBLE) {
//                    rv_list1.setVisibility(View.GONE);
//                    iv_down.setImageResource(R.mipmap.arrow_down);
//                } else {
//                    rv_list1.setVisibility(View.VISIBLE);
//                    iv_down.setImageResource(R.mipmap.arrow_up);
//                }
//            }
//        });
//    }
//
//    private void initView() {
//        ll_repair = (LinearLayout) findViewById(R.id.install_repair).findViewById(R.id.ll_item2);
//        ll_install = (LinearLayout) findViewById(R.id.install_repair).findViewById(R.id.ll_item1);
//
//        iv_header = (com.facebook.drawee.view.SimpleDraweeView) findViewById(R.id.iv_header);
//        iv_header.setOnClickListener(this);
//        tv_realname = (TextView) findViewById(R.id.tv_realname);
//        tv_company_name = (TextView) findViewById(R.id.tv_company_name);
//        tv_number = (TextView) findViewById(R.id.tv_number);
//        tv_koubei = (TextView) findViewById(R.id.tv_koubei);
//        tv_level = (TextView) findViewById(R.id.tv_level);
//        tv_year = (TextView) findViewById(R.id.tv_year);
//        tv_code = (TextView) findViewById(R.id.tv_code);
//        tv_address = (TextView) findViewById(R.id.tv_address);
//        rv_list1 = (android.support.v7.widget.RecyclerView) findViewById(R.id.rv_list1);
//        rv_list3 = (android.support.v7.widget.RecyclerView) findViewById(R.id.rv_list3);
//        iv_pic1 = (com.facebook.drawee.view.SimpleDraweeView) findViewById(R.id.iv_pic1);
//        iv_pic1.setOnClickListener(this);
//        iv_pic2 = (com.facebook.drawee.view.SimpleDraweeView) findViewById(R.id.iv_pic2);
//        iv_pic2.setOnClickListener(this);
//        iv_pic3 = (com.facebook.drawee.view.SimpleDraweeView) findViewById(R.id.iv_pic3);
//        iv_pic3.setOnClickListener(this);
//        iv_pic4 = (com.facebook.drawee.view.SimpleDraweeView) findViewById(R.id.iv_pic4);
//        iv_pic4.setOnClickListener(this);
//        iv_haopinglv = (com.daimajia.numberprogressbar.NumberProgressBar) findViewById(R.id.iv_haopinglv);
//        tv_haopinglv = (TextView) findViewById(R.id.tv_haopinglv);
//        rb_star1 = (me.zhanghai.android.materialratingbar.MaterialRatingBar) findViewById(R.id.rb_star1);
//        rb_star2 = (me.zhanghai.android.materialratingbar.MaterialRatingBar) findViewById(R.id.rb_star2);
//        rb_star3 = (me.zhanghai.android.materialratingbar.MaterialRatingBar) findViewById(R.id.rb_star3);
//        rb_star4 = (me.zhanghai.android.materialratingbar.MaterialRatingBar) findViewById(R.id.rb_star4);
//        rb_star5 = (me.zhanghai.android.materialratingbar.MaterialRatingBar) findViewById(R.id.rb_star5);
//        tv_select = (TextView) findViewById(R.id.tv_select);
//        rv_list1 = (RecyclerView) findViewById(R.id.rv_list1);
//        rv_list1.setLayoutManager(new GridLayoutManager(this, 2));
//        rv_list3 = (RecyclerView) findViewById(R.id.rv_list3);
//        rv_list3.setLayoutManager(new GridLayoutManager(this, 2));
//        ll_area = (LinearLayout) findViewById(R.id.ll_area);
//        iv_down = (ImageView) findViewById(R.id.iv_down);
//
//        if (toRepairBean != null) {
//            setRightImageResId(R.mipmap.heart);
//        }
//        setRightImageOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                JSONObject json = new JSONObject();
//                try {
////                    json.put("personuid", EanfangApplication.get().getUser().getPersonId());
//                    json.put("workeruid", id);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
////                EanfangHttp.post(ApiService.COLLECTION_WORK)
////                        .tag(this)
////                        .params("json", json.toString())
////                        .execute(new EanfangCallback(WorkerDetailActivity.this, false) {
////                            @Override
////                            public void onSuccess(Object bean) {
////                                super.onSuccess(bean);
////                                showToast("收藏成功");
////                                setRightImageResId(R.mipmap.hearted);
////                                setRightImageOnClickListener((v) -> {
////                                    showToast("已经收藏过该技师");
////                                });
////                            }
////
////                            @Override
////                            public void onFail(Integer code, String message, JSONObject jsonObject) {
////                                super.onFail(code, message, jsonObject);
////                                showToast(message);
////                            }
////                        });
//            }
//        });
//
//    }
//
//    private void initData() {
//
//        JSONObject object = new JSONObject();
//        try {
//            object.put("workeruid", id);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
////        EanfangHttp.get(ApiService.WORKER_DETAIL)
////                .tag(this)
////                .params("json", object.toString())
////                .execute(new EanfangCallback<WorkerDetailsBean>(this, true) {
////                    @Override
////                    public void onSuccess(WorkerDetailsBean bean) {
////                        super.onSuccess(bean);
////                        runOnUiThread(new Runnable() {
////                            @Override
////                            public void run() {
////                                setData(bean);
////                            }
////                        });
////
////                    }
////
////                    @Override
////                    public void onFail(Integer code, String message, JSONObject jsonObject) {
////                        super.onFail(code, message, jsonObject);
////                        showToast(message);
////                    }
////                });
//    }
//
//    private void setData(WorkerDetailsBean bean) {
//        workerDetailsBean = bean;
//        if ("1".equals(workerDetailsBean.getCollected())) {
//            setRightImageResId(R.mipmap.hearted);
//            setRightImageOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showToast("已经收藏过该技师");
//                }
//            });
//        }
//
//        iv_header.setImageURI(Uri.parse(workerDetailsBean.getHeadpic()));
//        tv_realname.setText(workerDetailsBean.getRealname());
//        tv_company_name.setText(workerDetailsBean.getCompanyname());
//        if (!StringUtils.isEmpty(workerDetailsBean.getRepairtimes())) {
//            tv_number.setText(workerDetailsBean.getRepairtimes() + "单");
//        }
//        if (!StringUtils.isEmpty(workerDetailsBean.getPraise())) {
//            tv_koubei.setText(workerDetailsBean.getPraise() + "分");
//        }
//        if (!StringUtils.isEmpty(workerDetailsBean.getWorklevel())) {
//            if ("1".equals(workerDetailsBean.getWorklevel())) {
//                tv_level.setText("一级");
//            } else if ("2".equals(workerDetailsBean.getWorklevel())) {
//                tv_level.setText("二级");
//            } else if ("3".equals(workerDetailsBean.getWorklevel())) {
//                tv_level.setText("三级");
//            }
//        }
//        if (!StringUtils.isEmpty(workerDetailsBean.getWorkexp())) {
//            tv_year.setText(workerDetailsBean.getWorkexp());
//        }
//
//        if ("0".equals(workerDetailsBean.getInstall())) {
//            ll_install.setVisibility(View.GONE);
//        }
//        if ("0".equals(workerDetailsBean.getRepair())) {
//            ll_repair.setVisibility(View.GONE);
//        }
//
//        tv_code.setText(workerDetailsBean.getId() + "");
//
//        tv_address.setText(workerDetailsBean.getWorkdetailplace());
//
//        if (!StringUtils.isEmpty(workerDetailsBean.getGoodpercent())) {
//            Double goodPercent = Double.parseDouble(workerDetailsBean.getGoodpercent());
//            tv_haopinglv.setText(goodPercent * 100 + "%");
//            iv_haopinglv.setProgress((int) (goodPercent * 100));
//        }
//
//        if (!StringUtils.isEmpty(workerDetailsBean.getWorkefficient())) {
//            rb_star1.setRating(Float.parseFloat(workerDetailsBean.getWorkefficient()));
//        }
//        if (!StringUtils.isEmpty(workerDetailsBean.getServiceattitude())) {
//            rb_star2.setRating(Float.parseFloat(workerDetailsBean.getServiceattitude()));
//        }
//        if (!StringUtils.isEmpty(workerDetailsBean.getTechlevel())) {
//            rb_star3.setRating(Float.parseFloat(workerDetailsBean.getTechlevel()));
//        }
//        if (!StringUtils.isEmpty(workerDetailsBean.getResptime())) {
//            rb_star4.setRating(Float.parseFloat(workerDetailsBean.getResptime()));
//        }
//        if (!StringUtils.isEmpty(workerDetailsBean.getProstandard())) {
//            rb_star5.setRating(Float.parseFloat(workerDetailsBean.getProstandard()));
//        }
//
//        mDataList1 = new ArrayList<>();
//        mDataList1.clear();
//        List<WorkerDetailsBean.ServiceplaceBean> serviceplaceBeanList = workerDetailsBean.getServiceplace();
//        for (int i = 0; i < serviceplaceBeanList.size(); i++) {
//            mDataList1.add(serviceplaceBeanList.get(i).getCity() + "-" + serviceplaceBeanList.get(i).getZone());
//        }
//
//        String businessType = workerDetailsBean.getBusinesstype();
//        String[] businessCodeArray = businessType.split(",");
//        mDataList3 = new ArrayList<>();
//        mDataList3.clear();
////        for (int i = 0; i < businessCodeArray.length; i++) {
////            mDataList3.add(GetConstDataUtils.getBugOneNameByCode(businessCodeArray[i]));
////        }
//
//        initAdapter();
//        initHonor();
//    }
//
//    private void initAdapter() {
//        BaseQuickAdapter evaluateAdapter1 = new WorkerDetailAdapter(R.layout.item_worker_detail1, mDataList1);
//        evaluateAdapter1.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
//        rv_list1.addOnItemTouchListener(new OnItemClickListener() {
//            @Override
//            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
////                Intent intent = new Intent(EvaluateWorkerActivity.this, EvaluateDetailActivity.class);
////                startActivity(intent);
//                ToastUtil.get().showToast(WorkerDetailActivity.this, position + "");
//            }
//        });
//        rv_list1.setAdapter(evaluateAdapter1);
//
//        BaseQuickAdapter evaluateAdapter3 = new WorkerDetailAdapter(R.layout.item_worker_detail1, mDataList3);
//        evaluateAdapter3.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
//        rv_list3.addOnItemTouchListener(new OnItemClickListener() {
//            @Override
//            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
////                Intent intent = new Intent(EvaluateWorkerActivity.this, EvaluateDetailActivity.class);
////                startActivity(intent);
//                ToastUtil.get().showToast(WorkerDetailActivity.this, position + "");
//            }
//        });
//        rv_list3.setAdapter(evaluateAdapter3);
//    }
//
//    private void getData() {
//        Intent intent = getIntent();
//        id = intent.getStringExtra("id");
//        toRepairBean = (ToRepairBean) intent.getSerializableExtra("bean");
//    }
//
//
//    private void initHonor() {
//        honor1Pic = workerDetailsBean.getHonor1();
//        honor2Pic = workerDetailsBean.getHonor2();
//        honor3Pic = workerDetailsBean.getHonor3();
//        honor4Pic = workerDetailsBean.getHonor4();
//        picList.clear();
//        if (!StringUtils.isEmpty(honor1Pic)) {
//            picList.add(honor1Pic);
//        }
//        if (!StringUtils.isEmpty(honor2Pic)) {
//            picList.add(honor2Pic);
//        }
//        if (!StringUtils.isEmpty(honor3Pic)) {
//            picList.add(honor3Pic);
//        }
//        if (!StringUtils.isEmpty(honor4Pic)) {
//            picList.add(honor4Pic);
//        }
//
//        if (TextUtils.isEmpty(honor1Pic)) {
//            iv_pic1.setVisibility(View.INVISIBLE);
//        } else {
//            iv_pic1.setImageURI(Uri.parse(honor1Pic));
//        }
//
//        if (TextUtils.isEmpty(honor2Pic)) {
//            iv_pic2.setVisibility(View.INVISIBLE);
//        } else {
//            iv_pic2.setImageURI(Uri.parse(honor2Pic));
//        }
//
//        if (TextUtils.isEmpty(honor3Pic)) {
//            iv_pic3.setVisibility(View.INVISIBLE);
//        } else {
//            iv_pic3.setImageURI(Uri.parse(honor3Pic));
//        }
//
//        if (TextUtils.isEmpty(honor4Pic)) {
//            iv_pic4.setVisibility(View.INVISIBLE);
//        } else {
//            iv_pic4.setImageURI(Uri.parse(honor4Pic));
//        }
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        ArrayList<String> images;
//        switch (v.getId()) {
//
//            //技师头像
//            case R.id.iv_header:
//                images = new ArrayList<String>();
//                if (StringUtils.isEmpty(workerDetailsBean.getHeadpic())) {
//                    break;
//                }
//                images.add(workerDetailsBean.getHeadpic());
//                ImagePerviewUtil.perviewImage(WorkerDetailActivity.this, images);
//                break;
//            //荣誉证书1
//            case R.id.iv_pic1:
//                images = new ArrayList<String>();
//                if (StringUtils.isEmpty(workerDetailsBean.getHonor1())) {
//                    break;
//                }
//                images.add(workerDetailsBean.getHonor1());
//                ImagePerviewUtil.perviewImage(WorkerDetailActivity.this, images);
//                break;
//            //荣誉证书2
//            case R.id.iv_pic2:
//                images = new ArrayList<String>();
//                if (StringUtils.isEmpty(workerDetailsBean.getHonor2())) {
//                    break;
//                }
//                images.add(workerDetailsBean.getHonor2());
//                ImagePerviewUtil.perviewImage(WorkerDetailActivity.this, images);
//                break;
//            //荣誉证书2
//            case R.id.iv_pic3:
//                images = new ArrayList<String>();
//                if (StringUtils.isEmpty(workerDetailsBean.getHonor3())) {
//                    break;
//                }
//                images.add(workerDetailsBean.getHonor3());
//                ImagePerviewUtil.perviewImage(WorkerDetailActivity.this, images);
//                break;
//            //荣誉证书2
//            case R.id.iv_pic4:
//                images = new ArrayList<String>();
//                if (StringUtils.isEmpty(workerDetailsBean.getHonor4())) {
//                    break;
//                }
//                images.add(workerDetailsBean.getHonor4());
//                ImagePerviewUtil.perviewImage(WorkerDetailActivity.this, images);
//                break;
//                default:
//                    break;
//        }
//    }
//}
