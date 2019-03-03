package net.eanfang.worker.ui.adapter.security;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.application.EanfangApplication;
import com.eanfang.model.security.SecurityFoucsListBean;
import com.eanfang.util.ETimeUtils;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.photopicker.com.widget.BGANinePhotoLayout;

import net.eanfang.worker.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author guanluocang
 * @data 2019/1/28
 * @description 安防圈adapter
 */

public class SecurityFocusListAdapter extends BaseQuickAdapter<SecurityFoucsListBean.ListBean, BaseViewHolder> {

    private Context context;
    private ArrayList<String> picList = new ArrayList<>();

    public SecurityFocusListAdapter(Context mContext) {
        super(R.layout.layout_security_item);
        this.context = mContext;
    }

    @Override
    public void onViewRecycled(BaseViewHolder holder) {
        super.onViewRecycled(holder);
//        BGANinePhotoLayout ninePhotoLayout = holder.getView(R.id.snpl_pic);
//        picList.clear();
//        ninePhotoLayout.setData(picList);
//        ninePhotoLayout.setDelegate(null);
    }

    @Override
    protected void convert(BaseViewHolder helper, SecurityFoucsListBean.ListBean item) {
        SimpleDraweeView ivHeader = helper.getView(R.id.iv_seucrity_header);
        BGANinePhotoLayout ninePhotoLayout = helper.getView(R.id.snpl_pic);
        // 发布人
        helper.setText(R.id.tv_name, item.getAccountEntity().getNickName());
        // 头像
        ivHeader.setImageURI((Uri.parse(BuildConfig.OSS_SERVER + item.getAccountEntity().getAvatar())));
        // 公司名称
        if (StringUtils.isEmpty(item.getOrgUnitEntity().getName())) {
            helper.setText(R.id.tv_company, "个人");
        } else {
            helper.setText(R.id.tv_company, item.getOrgUnitEntity().getName());
        }
        //发布的内容
        helper.setText(R.id.tv_content, item.getAskSpCircleEntity().getSpcContent());
        // 点赞数量
        helper.setText(R.id.tv_like_num, item.getAskSpCircleEntity().getLikesCount() + "");
        // 评论数量
        helper.setText(R.id.tv_comments_num, item.getAskSpCircleEntity().getCommentCount() + "");
        helper.setText(R.id.tv_time, ETimeUtils.getTimeFormatText(item.getAskSpCircleEntity().getCreateTime()));
        /**
         * 0 是关注 1 是未关注
         * */
        if (item.getAskSpCircleEntity().getPublisherUserId().equals(EanfangApplication.get().getUserId()) || item.getFollowsStatus() == 0) {
            helper.setVisible(R.id.tv_isFocus, true);
            helper.setText(R.id.tv_isFocus, "取消关注");
        } else {

            helper.setVisible(R.id.tv_isFocus, false);
        }
        /**
         * 0 点赞 1 未点赞
         * */
        if (item.getAskSpCircleEntity().getLikeStatus() == 0) {
            helper.setImageResource(R.id.iv_like, R.mipmap.ic_worker_security_like_pressed);
        } else {
            helper.setImageResource(R.id.iv_like, R.mipmap.ic_worker_security_like_unpressed);
        }
        picList.clear();
        ninePhotoLayout.init(context);
        ninePhotoLayout.setData(picList);
        if (!StringUtils.isEmpty(item.getAskSpCircleEntity().getSpcImg())) {
            ninePhotoLayout.setVisibility(View.VISIBLE);
            String[] pics = item.getAskSpCircleEntity().getSpcImg().split(",");
            picList.addAll(com.annimon.stream.Stream.of(Arrays.asList(pics)).map(url -> (BuildConfig.OSS_SERVER + url).toString()).toList());
            ninePhotoLayout.setDelegate(new BGANinePhotoLayout.Delegate() {
                @Override
                public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {

                }
            });
            ninePhotoLayout.setData(picList);
        } else {
            ninePhotoLayout.setVisibility(View.GONE);
        }

        helper.addOnClickListener(R.id.tv_isFocus);
        helper.addOnClickListener(R.id.ll_like);
        helper.addOnClickListener(R.id.ll_comments);
        helper.addOnClickListener(R.id.iv_share);
    }


}
