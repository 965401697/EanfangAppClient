package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseFragment;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.kit.picture.IPictureCallBack;
import com.eanfang.base.kit.rx.RxPerm;
import com.eanfang.base.kit.utils.GlideUtil;
import com.eanfang.biz.model.bean.OrderCountBean;
import com.eanfang.biz.model.bean.SelectAddressItem;
import com.eanfang.biz.model.entity.RepairOrderEntity;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.ui.activity.SelectAddressActivity;
import com.eanfang.util.JumpItent;
import com.luck.picture.lib.entity.LocalMedia;

import net.eanfang.client.R;
import net.eanfang.client.databinding.FragmentHomeRepairBinding;
import net.eanfang.client.ui.activity.worksapce.repair.AddTroubleActivity;
import net.eanfang.client.ui.activity.worksapce.repair.DeviceBrandActivity;
import net.eanfang.client.ui.activity.worksapce.repair.SelectDeviceTypeActivity;
import net.eanfang.client.viewmodel.QuickRepairViewModel;

import org.json.JSONObject;

import java.util.List;

import cn.hutool.core.util.StrUtil;

/**
 * @author liangkailun
 * Date ：2019-06-19
 * Describe :首页报修、报装、设计页
 */
public class HomeRepairFragment extends BaseFragment {
    private static final String STATUS = "status";
    private static final String DEVICE_BRAND_NAME = "deviceBrandName";
    private static final String BRAND_DATA_CODE = "dataCode";
    private static final String SYSTEM_NAME = "systemName";
    private static final String REPAIRORDERENEITY = "repairOrderEntity";
    private static final int SELECT_ADDRESS_CALL_BACK_CODE = 1;
    /**
     * 设备信息 RequestCode
     */
    private static final int REQUEST_FAULTDEVICEINFO = 100;
    private static final int RESULT_DATACODE = 200;

    /**
     * 设备品牌回调 code
     */
    private final int REQUEST_DEVICE_BRAND_CODE = 1001;
    private final int RESULT_DEVICE_BRAND_CODE = 1002;

    private FragmentHomeRepairBinding mBinding;
    private QuickRepairViewModel mViewModel;
    /**
     * 设备code 设备id
     */
    private String dataCode = "";
    private int mStatus;

    private String mSystemName;
    private RepairOrderEntity mRepairOrderEntity;

    public static Fragment getInstance(int status, String deviceBrandName, String brandName, String systemName, RepairOrderEntity repairOrderEntity) {
        HomeRepairFragment fragment = new HomeRepairFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(STATUS, status);
        arguments.putString(DEVICE_BRAND_NAME, deviceBrandName);
        arguments.putString(BRAND_DATA_CODE, brandName);
        arguments.putString(SYSTEM_NAME, systemName);
        arguments.putSerializable(REPAIRORDERENEITY, repairOrderEntity);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected void onLazyLoad() {
        mViewModel.getRepairCount();
    }

    @Override
    protected ViewModel initViewModel() {
        mViewModel = LViewModelProviders.of(this, QuickRepairViewModel.class);
        mViewModel.getMOrderNum().observe(this, this::initRepairCount);
        mViewModel.getMCreateRepair().observe(this, this::showSuccessToast);
        return mViewModel;
    }

    private void showSuccessToast(JSONObject jsonObject) {
        dataCode = "";
        mBinding.etHomeRepairBrand.setText("");
        mBinding.etHomeRepairSys.setText("");
        mBinding.etHomeRepairDescribe.setText("");
        showToast("提交成功！");
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        mBinding = FragmentHomeRepairBinding.inflate(getLayoutInflater());
        mBinding.setVm(mViewModel);
        mViewModel.setMBinding(mBinding);
        if (getArguments() != null) {
            mStatus = getArguments().getInt(STATUS, 0);
            String deviceBrandName = getArguments().getString(DEVICE_BRAND_NAME);
            String brandDataCode = getArguments().getString(BRAND_DATA_CODE);
            mSystemName = getArguments().getString(SYSTEM_NAME);
            mRepairOrderEntity = (RepairOrderEntity) getArguments().getSerializable(REPAIRORDERENEITY);
            // 全部品牌报修
            if (!StrUtil.isEmpty(deviceBrandName)) {
                String name = Config.get().getModelNameByCode(brandDataCode, 1);
                mBinding.etHomeRepairSys.setText(name);
                String businessOneCode = Config.get().getBusinessCodeByName(name, 1);
                mViewModel.setSysData(businessOneCode);
                mBinding.etHomeRepairBrand.setText(deviceBrandName);
                mViewModel.setBrandData(deviceBrandName);
            } else {// 全部业务报修
                //设备库
                String businessOneCode = Config.get().getBusinessCodeByName(mSystemName, 1);
                dataCode = businessOneCode;
                mBinding.etHomeRepairSys.setText(mSystemName);
                mBinding.etHomeRepairBrand.setText(null);
                mViewModel.setSysData(businessOneCode);
            }
            if (mRepairOrderEntity != null) {
                mViewModel.setRepairId(mRepairOrderEntity);
            }

        }
        mBinding.setStatus(mStatus);
        if (mStatus != 0) {
            mBinding.imgMomentAccident.setVisibility(View.GONE);
            mBinding.tvCommitText.setVisibility(View.GONE);
            mBinding.viewEmpty.setVisibility(View.INVISIBLE);
        } else {
            mBinding.viewEmpty.setVisibility(View.GONE);
        }
        mBinding.etHomeRepairSys.setCursorVisible(false);
        mBinding.etHomeRepairSys.setFocusable(false);
        mBinding.etHomeRepairSys.setFocusableInTouchMode(false);
        mBinding.etHomeRepairBrand.setCursorVisible(false);
        mBinding.etHomeRepairBrand.setFocusable(false);
        mBinding.etHomeRepairBrand.setFocusableInTouchMode(false);

        mBinding.etHomeRepairDescribe.setOnTouchListener((v, event) -> {
            // et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
            Drawable drawable = mBinding.etHomeRepairDescribe.getCompoundDrawables()[2];
            //如果右边没有图片，不再处理
            if (drawable == null) {
                return false;
            }
            //如果不是按下事件，不再处理
            if (event.getAction() != MotionEvent.ACTION_UP) {
                return false;
            }
            if (event.getX() > mBinding.etHomeRepairDescribe.getWidth()
                    - mBinding.etHomeRepairDescribe.getPaddingRight()
                    - drawable.getIntrinsicWidth()) {
                mViewModel.inputVoice(getActivity(), mBinding.etHomeRepairDescribe);
            }
            return false;
        });
        initClick();
        return mBinding.getRoot();
    }

    private void initClick() {
        mBinding.etHomeRepairSys.setOnClickListener(view -> {
            //设备类别
            startActivityForResult(new Intent(getActivity(), SelectDeviceTypeActivity.class), REQUEST_FAULTDEVICEINFO);
        });
        mBinding.etHomeRepairBrand.setOnClickListener(view -> {
            String busOneCode = Config.get().getBaseCodeByName(Config.get().getBusinessNameByCode(dataCode, 1), 1, Constant.MODEL).get(0);
            if (StrUtil.isEmpty(busOneCode)) {
                showToast("请先选择系统类别");
                return;
            }
            Bundle bundleDevice = new Bundle();
            bundleDevice.putString("busOneCode", busOneCode);
            startActivityForResult(new Intent(getActivity(), DeviceBrandActivity.class).putExtras(bundleDevice), REQUEST_DEVICE_BRAND_CODE);
        });

        mBinding.tvHomeRepairAddress.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), SelectAddressActivity.class);
            startActivityForResult(intent, SELECT_ADDRESS_CALL_BACK_CODE);
        });
        mBinding.imgMomentAccident.setOnClickListener(view -> {
            RxPerm.get(getActivity()).cameraPerm((isSuccess) -> {
                accidentImage();
            });
        });
        mBinding.tvHomeRepairMore.setOnClickListener((view) -> {
            JumpItent.jump(getActivity(), AddTroubleActivity.class);
        });
    }

    /**
     * @param bean
     */
    private void initRepairCount(OrderCountBean bean) {
        int orderNum = bean.getOrderNum();
        String count = getString(R.string.text_home_repair_count, orderNum);
        SpannableString spannableString = new SpannableString(count);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF6419")), 3, count.length() - 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        AbsoluteSizeSpan span = new AbsoluteSizeSpan(24, true);
        spannableString.setSpan(span, 3, count.length() - 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mBinding.tvHomeRepairCount.setText(spannableString);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == SELECT_ADDRESS_CALL_BACK_CODE) {
            SelectAddressItem item = (SelectAddressItem) data.getSerializableExtra("data");
            mViewModel.setAddressData(item, mStatus);
            mBinding.tvHomeRepairAddress.setText(item.getName());
        } else if (requestCode == REQUEST_FAULTDEVICEINFO && resultCode == RESULT_DATACODE) {
            //设备库
            dataCode = data.getStringExtra("dataCode");
            String businessOneCode = data.getStringExtra("businessOneCode");
            String text = Config.get().getBusinessNameByCode(dataCode, 1);
            mBinding.etHomeRepairSys.setText(text);
            mBinding.etHomeRepairBrand.setText(null);
            mViewModel.setSysData(businessOneCode);

        } else if (resultCode == RESULT_DEVICE_BRAND_CODE && requestCode == REQUEST_DEVICE_BRAND_CODE) {
            // 设备品牌
            String brandName = data.getStringExtra("deviceBrandName");
            mBinding.etHomeRepairBrand.setText(brandName);
            mViewModel.setBrandData(brandName);
        }

    }

    private void accidentImage() {
        SDKManager.getPicture().create(this).takePhoto(iPictureCallBack);
    }

    IPictureCallBack iPictureCallBack = new IPictureCallBack() {
        @Override
        public void onSuccess(List<LocalMedia> list) {
            GlideUtil.intoImageView(getActivity(), "file://" + list.get(0).getPath(), mBinding.imgMomentAccident);
            String objectKey = StrUtil.uuid() + ".png";
            //上传图片
            SDKManager.ossKit(getActivity()).asyncPutImage(objectKey, list.get(0).getPath(), (isSucess) -> {
                mViewModel.setPicUrl(objectKey);
            });
        }
    };

}
