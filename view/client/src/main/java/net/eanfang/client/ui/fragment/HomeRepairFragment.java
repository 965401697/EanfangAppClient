package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.kit.picture.IPictureCallBack;
import com.eanfang.base.kit.rx.RxPerm;
import com.eanfang.base.kit.utils.GlideUtil;
import com.eanfang.biz.model.DesignOrderInfoBean;
import com.eanfang.biz.model.InstallOrderConfirmBean;
import com.eanfang.biz.model.OrderCountBean;
import com.eanfang.biz.model.SelectAddressItem;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.activity.SelectAddressActivity;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.ui.base.voice.RecognitionManager;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.StringUtils;
import com.eanfang.util.UuidUtil;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.yaf.base.entity.RepairBugEntity;
import com.yaf.base.entity.RepairOrderEntity;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.activity.worksapce.repair.DeviceBrandActivity;
import net.eanfang.client.ui.activity.worksapce.repair.SelectDeviceTypeActivity;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * @author liangkailun
 * Date ：2019-06-19
 * Describe :首页报修、报装、设计页
 */
public class HomeRepairFragment extends BaseFragment {
    private static final String STATUS = "status";
    private static final int SELECT_ADDRESS_CALL_BACK_CODE = 1;
    /**
     * 设备信息 RequestCode
     */
    private static final int REQUEST_FAULTDEVICEINFO = 100;
    private static final int RESULT_DATACODE = 200;

    /**
     * 设备库 RequestCode
     */
    private static final int REQUEST_EQUIPMENT = 3000;
    /**
     * 设备品牌回调 code
     */
    private final int REQUEST_DEVICE_BRAND_CODE = 1001;
    private final int RESULT_DEVICE_BRAND_CODE = 1002;
    @BindView(R.id.tv_home_repair_count)
    TextView mTvHomeRepairCount;
    @BindView(R.id.tv_home_repair_address)
    TextView mTvHomeRepairAddress;
    @BindView(R.id.et_home_repair_sys)
    EditText mEtHomeRepairSys;
    @BindView(R.id.et_home_repair_brand)
    EditText mEtHomeRepairBrand;
    @BindView(R.id.et_home_repair_describe)
    EditText mEtHomeRepairDescribe;
    @BindView(R.id.img_moment_accident)
    ImageView imgMomentAccident;
    @BindView(R.id.btn_home_repair_commit)
    Button mBtnHomeRepairCommit;
    @BindView(R.id.tv_home_repair_more)
    TextView mTvHomeRepairMore;
    @BindView(R.id.tv_commit_text)
    TextView mTvCommitText;
    /**
     * 设备code 设备id
     */
    private String dataCode = "";
    private int mStatus;
    /**
     * 报修上报bean
     */
    private RepairOrderEntity mRepairOrderEntity;
    private RepairBugEntity mRepairBugEntity;

    /**
     * 报装上报bean
     */
    private InstallOrderConfirmBean mInstallOrderConfirmBean;
    /**
     * 设计上报bean
     */
    private DesignOrderInfoBean mDesignOrderInfoBean;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_home_repair;
    }

    public static Fragment getInstance(int status) {
        HomeRepairFragment fragment = new HomeRepairFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(STATUS, status);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected void onLazyLoad() {
        initRepairCount();
    }

    @Override
    protected void initData(Bundle arguments) {
        if (arguments != null) {
            mStatus = arguments.getInt(STATUS, 0);
        }
        mRepairOrderEntity = new RepairOrderEntity();
        mRepairBugEntity = new RepairBugEntity();
        mInstallOrderConfirmBean = new InstallOrderConfirmBean();
        mDesignOrderInfoBean = new DesignOrderInfoBean();
        ArrayList<RepairBugEntity> repairBugEntities = new ArrayList<>();
        repairBugEntities.add(mRepairBugEntity);
        mRepairOrderEntity.setBugEntityList(repairBugEntities);
    }

    @Override
    protected void initView() {
        if (mStatus != 0) {
            imgMomentAccident.setVisibility(View.GONE);
            mTvCommitText.setVisibility(View.GONE);
        }
        mEtHomeRepairSys.setCursorVisible(false);
        mEtHomeRepairSys.setFocusable(false);
        mEtHomeRepairSys.setFocusableInTouchMode(false);
        mEtHomeRepairBrand.setCursorVisible(false);
        mEtHomeRepairBrand.setFocusable(false);
        mEtHomeRepairBrand.setFocusableInTouchMode(false);

        mEtHomeRepairDescribe.setOnTouchListener((v, event) -> {
            // et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
            Drawable drawable = mEtHomeRepairDescribe.getCompoundDrawables()[2];
            //如果右边没有图片，不再处理
            if (drawable == null) {
                return false;
            }
            //如果不是按下事件，不再处理
            if (event.getAction() != MotionEvent.ACTION_UP) {
                return false;
            }
            if (event.getX() > mEtHomeRepairDescribe.getWidth()
                    - mEtHomeRepairDescribe.getPaddingRight()
                    - drawable.getIntrinsicWidth()) {
                inputVoice(mEtHomeRepairDescribe);
            }
            return false;
        });
    }

    private void initRepairCount() {
        EanfangHttp.post(NewApiService.HOME_OREDER_COUNT).upJson(JSON.toJSONString(new QueryEntry())).execute(new EanfangCallback<OrderCountBean>(getActivity(), true, OrderCountBean.class, bean -> {
            setCountText(bean.getOrderNum());
        }));
    }

    private void setCountText(int orderNum) {
        String count = getString(R.string.text_home_repair_count, orderNum);
        SpannableString spannableString = new SpannableString(count);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF6419")), 3, count.length() - 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        AbsoluteSizeSpan span = new AbsoluteSizeSpan(24, true);
        spannableString.setSpan(span, 3, count.length() - 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mTvHomeRepairCount.setText(spannableString);
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == SELECT_ADDRESS_CALL_BACK_CODE) {
            SelectAddressItem item = (SelectAddressItem) data.getSerializableExtra("data");
            Log.e("address", item.toString());
            if (mStatus == 0) {
                mRepairOrderEntity.setLatitude(String.valueOf(item.getLatitude()));
                mRepairOrderEntity.setAddress(item.getName());
                mRepairOrderEntity.setPlaceCode(Config.get().getAreaCodeByName(item.getCity(), item.getAddress()));
            } else if (mStatus == 1) {
                mInstallOrderConfirmBean.setZone(Config.get().getAreaCodeByName(item.getCity(), item.getAddress()));
                mInstallOrderConfirmBean.setDetailPlace(item.getName());
                mInstallOrderConfirmBean.setLongitude(String.valueOf(item.getLongitude()));
                mInstallOrderConfirmBean.setLatitude(String.valueOf(item.getLatitude()));
            } else {
                mDesignOrderInfoBean.setLatitude(String.valueOf(item.getLatitude()));
                mDesignOrderInfoBean.setLongitude(String.valueOf(item.getLongitude()));
                mDesignOrderInfoBean.setZoneCode(Config.get().getAreaCodeByName(item.getCity(), item.getAddress()));
                mDesignOrderInfoBean.setDetailPlace(item.getName());
            }
            mTvHomeRepairAddress.setText(item.getName());
        } else if (requestCode == REQUEST_FAULTDEVICEINFO && resultCode == RESULT_DATACODE) {
            //设备库
            dataCode = data.getStringExtra("dataCode");
            String businessOneCode = data.getStringExtra("businessOneCode");
            mRepairBugEntity.setBusinessThreeCode(businessOneCode);
            String text = Config.get().getBusinessNameByCode(dataCode, 1);
            mEtHomeRepairSys.setText(text);
            mInstallOrderConfirmBean.setBusinessOneCode(businessOneCode);
            mDesignOrderInfoBean.setBusinessOneCode(businessOneCode);
        } else if (resultCode == RESULT_DEVICE_BRAND_CODE && requestCode == REQUEST_DEVICE_BRAND_CODE) {
            // 设备品牌
            String brandName = data.getStringExtra("deviceBrandName");
            mEtHomeRepairBrand.setText(brandName);
            mRepairBugEntity.setModelCode(Config.get().getBaseCodeByName(brandName, 2, Constant.MODEL).get(0));
        }

    }
    @OnClick({R.id.et_home_repair_sys, R.id.et_home_repair_brand, R.id.btn_home_repair_commit, R.id.tv_home_repair_more, R.id.et_home_repair_describe, R.id.tv_home_repair_address, R.id.img_moment_accident})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_home_repair_sys:
                //设备类别
                startActivityForResult(new Intent(getActivity(), SelectDeviceTypeActivity.class), REQUEST_FAULTDEVICEINFO);
                break;
            case R.id.et_home_repair_brand:
                String busOneCode = Config.get().getBaseCodeByName(Config.get().getBusinessNameByCode(dataCode, 1), 1, Constant.MODEL).get(0);
                if (StringUtils.isEmpty(busOneCode)) {
                    showToast("请先选择故障设备");
                    return;
                }
                Bundle bundleDevice = new Bundle();
                bundleDevice.putString("busOneCode", busOneCode);
                startActivityForResult(new Intent(getActivity(), DeviceBrandActivity.class).putExtras(bundleDevice), REQUEST_DEVICE_BRAND_CODE);
                break;
            case R.id.btn_home_repair_commit:
                doCommit();
                break;
            case R.id.tv_home_repair_more:
                break;
            case R.id.tv_home_repair_address:
                Intent intent = new Intent(getActivity(), SelectAddressActivity.class);
                startActivityForResult(intent, SELECT_ADDRESS_CALL_BACK_CODE);
                break;
            case R.id.img_moment_accident:
                RxPerm.get(getActivity()).cameraPerm((isSuccess) -> {
                    accidentImage();
                });
                break;
            default:
                break;
        }
    }
    private void accidentImage() {
        SDKManager.getPicture().create(this).takePhoto(iPictureCallBack);
    }
    IPictureCallBack iPictureCallBack=new IPictureCallBack() {
        @Override
        public void onSuccess(List<LocalMedia> list) {
                GlideUtil.intoImageView(getActivity(), "file://" + list.get(0).getPath(), imgMomentAccident);
                String objectKey = UuidUtil.getUUID() + ".png";
                //上传图片
                SDKManager.ossKit(getActivity()).asyncPutImage(objectKey, list.get(0).getPath(), (isSucess) -> {
                    mRepairBugEntity.setPictures(objectKey);
                });
        }
    };
    private void doCommit() {
        if (mStatus == 0) {
            mRepairOrderEntity.setRepairWay(0);
            mRepairOrderEntity.setOwnerCompanyId(0L);
            mRepairOrderEntity.setRepairContacts(ClientApplication.get().getAccount().getRealName());
            mRepairBugEntity.setBugDescription(mEtHomeRepairDescribe.getText().toString());
            mRepairOrderEntity.setRepairContactPhone(ClientApplication.get().getAccount().getMobile());
            EanfangHttp.post(NewApiService.HOME_QUICK_REPAIR).upJson(JSON.toJSONString(mRepairOrderEntity)).execute(new EanfangCallback(getActivity(), true, JSONObject.class, bean -> {
                showToast("提交成功！");
            }));
        } else if (mStatus == 1) {
            mInstallOrderConfirmBean.setDescription(mEtHomeRepairDescribe.getText().toString());
            mInstallOrderConfirmBean.setConnectorPhone(ClientApplication.get().getAccount().getMobile());
            mInstallOrderConfirmBean.setConnector(ClientApplication.get().getAccount().getRealName());
            EanfangHttp.post(NewApiService.HOME_QUICK_INSTALL).upJson(JSON.toJSONString(mInstallOrderConfirmBean)).execute(new EanfangCallback(getActivity(), true, JSONObject.class, bean -> {
                showToast("提交成功！");
            }));
        } else {
            mDesignOrderInfoBean.setRemarkInfo(mEtHomeRepairDescribe.getText().toString());
            mDesignOrderInfoBean.setContactPhone(ClientApplication.get().getAccount().getMobile());
            mDesignOrderInfoBean.setContactUser(ClientApplication.get().getAccount().getRealName());
            EanfangHttp.post(NewApiService.HOME_FREE_DESIGN).upJson(JSON.toJSONString(mDesignOrderInfoBean)).execute(new EanfangCallback(getActivity(), true, JSONObject.class, bean -> {
                showToast("提交成功！");
            }));
        }
    }

    private void inputVoice(EditText editText) {
        RxPerm.get(getActivity()).voicePerm((isSuccess) -> {
            RecognitionManager.getSingleton().startRecognitionWithDialog(getActivity(), editText);
        });
    }

}
