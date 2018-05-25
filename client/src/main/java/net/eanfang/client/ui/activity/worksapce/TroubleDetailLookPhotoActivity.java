package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.config.Constant;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.StringUtils;
import com.photopicker.com.activity.BGAPhotoPickerActivity;
import com.photopicker.com.activity.BGAPhotoPickerPreviewActivity;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;
import com.yaf.base.entity.BughandleDetailEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.LookMaterialAdapter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @date on 2018/5/23  18:23
 * @author Guanluocang
 * @decision 电话未解决 照片墙
 */import butterknife.BindView;
import butterknife.ButterKnife;

public class TroubleDetailLookPhotoActivity extends BaseActivity {

    private static final int REQUEST_CODE_CHOOSE_PHOTO_1 = 1;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_2 = 2;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_3 = 3;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_4 = 4;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_5 = 5;
    private static final int REQUEST_CODE_CHOOSE_PHOTO_6 = 6;

    private static final int REQUEST_CODE_PHOTO_PREVIEW_1 = 7;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_2 = 8;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_3 = 9;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_4 = 10;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_5 = 11;
    private static final int REQUEST_CODE_PHOTO_PREVIEW_6 = 12;
    /**
     * 故障表象 （3张）
     */
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout snpl_moment_add_photos;
    /**
     * 工具及蓝布 （3张）
     */
    @BindView(R.id.snpl_monitor_add_photos)
    BGASortableNinePhotoLayout snpl_monitor_add_photos;
    /**
     * 故障点照片 （3张）
     */
    @BindView(R.id.snpl_tools_package_add_photos)
    BGASortableNinePhotoLayout snpl_tools_package_add_photos;
    /**
     * 处理后现场 （3张）
     */
    @BindView(R.id.snpl_after_processing_locale)
    BGASortableNinePhotoLayout snpl_after_processing_locale;
    /**
     * 设备回装 （3张）
     */
    @BindView(R.id.snpl_machine_fit_back)
    BGASortableNinePhotoLayout snpl_machine_fit_back;
    /**
     * 故障恢复后表象 （3张）
     */
    @BindView(R.id.snpl_failure_recover_phenomena)
    BGASortableNinePhotoLayout snpl_failure_recover_phenomena;

    /**
     * 维修结果
     */
    private TextView tv_repair_conclusion;
    /**
     * 故障表象 （3张）
     */
    private ArrayList<String> picList1 = new ArrayList<>();
    /**
     * 工具及蓝布 （3张）
     */
    private ArrayList<String> picList2 = new ArrayList<>();
    /**
     * 故障点照片 （3张）
     */
    private ArrayList<String> picList3 = new ArrayList<>();
    //2017年7月21日
    /**
     * 处理后现场 （3张）
     */
    private ArrayList<String> picList4 = new ArrayList<>();
    /**
     * 设备回装 （3张）
     */
    private ArrayList<String> picList5 = new ArrayList<>();
    /**
     * 故障恢复后表象 （3张）
     */
    private ArrayList<String> picList6 = new ArrayList<>();


    private LookMaterialAdapter materialAdapter;
    private BughandleDetailEntity bughandleDetailEntity;

    /**
     * 故障明细的id
     */
    private Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trouble_detail_look_photo);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        setLeftBack();
        setTitle("现场照片");
        id = getIntent().getLongExtra(Constant.ID, 0);
    }

    private void initData() {
        EanfangHttp.get(RepairApi.GET_BUGHANDLE_DETAIL_INFO)
                .params("id", id)
                .execute(new EanfangCallback<BughandleDetailEntity>(this, true, BughandleDetailEntity.class, (bean) -> {
                    bughandleDetailEntity = bean;
//                    initData(bean);
                    initImgUrlList();
                    initNinePhoto();
                }));

    }


    /**
     * 初始化存储图片用的List集合
     */
    private void initImgUrlList() {

        //修改小bug 图片读取问题
        if (StringUtils.isValid(bughandleDetailEntity.getPresentationPictures())) {
            String[] prePic = bughandleDetailEntity.getPresentationPictures().split(Constant.IMG_SPLIT);
            picList1.addAll(Stream.of(Arrays.asList(prePic)).map(url -> (BuildConfig.OSS_SERVER + url).toString()).toList());
        }
        if (StringUtils.isValid(bughandleDetailEntity.getToolPictures())) {
            String[] toolPic = bughandleDetailEntity.getToolPictures().split(Constant.IMG_SPLIT);
            picList2.addAll(Stream.of(Arrays.asList(toolPic)).map(url -> (BuildConfig.OSS_SERVER + url).toString()).toList());
        }
        if (StringUtils.isValid(bughandleDetailEntity.getPointPictures())) {
            String[] ponitPic = bughandleDetailEntity.getPointPictures().split(Constant.IMG_SPLIT);
            picList3.addAll(Stream.of(Arrays.asList(ponitPic)).map(url -> (BuildConfig.OSS_SERVER + url).toString()).toList());
        }
        if (StringUtils.isValid(bughandleDetailEntity.getAfterHandlePictures())) {
            String[] afterHandlePic = bughandleDetailEntity.getAfterHandlePictures().split(Constant.IMG_SPLIT);
            picList4.addAll(Stream.of(Arrays.asList(afterHandlePic)).map(url -> (BuildConfig.OSS_SERVER + url).toString()).toList());
        }
        if (StringUtils.isValid(bughandleDetailEntity.getDeviceReturnInstallPictures())) {
            String[] returnInstallPic = bughandleDetailEntity.getDeviceReturnInstallPictures().split(Constant.IMG_SPLIT);
            picList5.addAll(Stream.of(Arrays.asList(returnInstallPic)).map(url -> (BuildConfig.OSS_SERVER + url).toString()).toList());
        }
        if (StringUtils.isValid(bughandleDetailEntity.getRestorePictures())) {
            String[] restorePic = bughandleDetailEntity.getRestorePictures().split(Constant.IMG_SPLIT);
            picList6.addAll(Stream.of(Arrays.asList(restorePic)).map(url -> (BuildConfig.OSS_SERVER + url).toString()).toList());
        }

    }

    private void initNinePhoto() {
        snpl_moment_add_photos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_1, REQUEST_CODE_PHOTO_PREVIEW_1));
        snpl_monitor_add_photos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_2, REQUEST_CODE_PHOTO_PREVIEW_2));
        snpl_tools_package_add_photos.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_3, REQUEST_CODE_PHOTO_PREVIEW_3));
        snpl_after_processing_locale.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_4, REQUEST_CODE_PHOTO_PREVIEW_4));
        snpl_machine_fit_back.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_5, REQUEST_CODE_PHOTO_PREVIEW_5));
        snpl_failure_recover_phenomena.setDelegate(new BGASortableDelegate(this, REQUEST_CODE_CHOOSE_PHOTO_6, REQUEST_CODE_PHOTO_PREVIEW_6));

        snpl_moment_add_photos.setData(picList1);
        snpl_monitor_add_photos.setData(picList2);
        snpl_tools_package_add_photos.setData(picList3);
        //2017年7月21日
        snpl_after_processing_locale.setData(picList4);
        snpl_machine_fit_back.setData(picList5);
        snpl_failure_recover_phenomena.setData(picList6);

        snpl_moment_add_photos.setEditable(false);
        snpl_monitor_add_photos.setEditable(false);
        snpl_tools_package_add_photos.setEditable(false);
        //2017年7月21日
        snpl_after_processing_locale.setEditable(false);
        snpl_machine_fit_back.setEditable(false);
        snpl_failure_recover_phenomena.setEditable(false);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_CHOOSE_PHOTO_1:
                snpl_moment_add_photos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_2:
                snpl_monitor_add_photos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_3:
                snpl_tools_package_add_photos.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            //2017年7月21日
            case REQUEST_CODE_CHOOSE_PHOTO_4:
                snpl_after_processing_locale.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_5:
                snpl_machine_fit_back.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_CHOOSE_PHOTO_6:
                snpl_failure_recover_phenomena.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_1:
                snpl_moment_add_photos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_2:
                snpl_monitor_add_photos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_3:
                snpl_tools_package_add_photos.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_4:
                snpl_after_processing_locale.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_5:
                snpl_machine_fit_back.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
            case REQUEST_CODE_PHOTO_PREVIEW_6:
                snpl_failure_recover_phenomena.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
                break;
//            case 10009:
//                FillRepairInfoBean.BughandledetaillistBean.BughandledetailmateriallistBean bugBean = (FillRepairInfoBean.BughandledetaillistBean.BughandledetailmateriallistBean) data.getSerializableExtra("bean");
////                bean.getBughandledetailmateriallist().add(bugBean);
//                materialAdapter.notifyDataSetChanged();
//                break;
            default:
                break;
        }
    }
}
