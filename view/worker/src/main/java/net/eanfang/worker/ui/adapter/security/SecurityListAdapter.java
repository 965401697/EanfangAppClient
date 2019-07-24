package net.eanfang.worker.ui.adapter.security;

import android.net.Uri;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;

import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.BuildConfig;
import com.eanfang.base.kit.V;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.biz.model.security.SecurityListBean;
import com.eanfang.util.ETimeUtils;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.StringUtils;
import com.eanfang.witget.SecurityCircleImageLayout;
import com.eanfang.witget.mentionedittext.edit.util.FormatRangeManager;
import com.eanfang.witget.mentionedittext.text.MentionTextView;
import com.eanfang.witget.mentionedittext.text.listener.Parser;
import com.eanfang.witget.mentionedittext.util.SecurityItemUtil;

import net.eanfang.worker.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author guanluocang
 * @data 2019/1/28
 * @description 安防圈adapter
 */

public class SecurityListAdapter extends BaseQuickAdapter<SecurityListBean.ListBean, BaseViewHolder> {

    private ArrayList<String> picList = new ArrayList<>();
    private String[] pics = null;
    private boolean mIsUnRead = false;
    private Parser mTagParser = new Parser();
    protected FormatRangeManager mRangeManager = new FormatRangeManager();

    private OnPhotoClickListener onPhotoClickListener;

    public SecurityListAdapter(boolean isUnRead, OnPhotoClickListener mPhotoClickListener) {
        super(R.layout.layout_security_item);
        this.mIsUnRead = isUnRead;
        this.onPhotoClickListener = mPhotoClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, SecurityListBean.ListBean item) {
        CircleImageView ivHeader = helper.getView(R.id.iv_seucrity_header);
        SecurityCircleImageLayout securityImageLayout = helper.getView(R.id.securityImageLayout);
        ImageView ivShowVideo = helper.getView(R.id.iv_show_video);
        MentionTextView mentionTextView = helper.getView(R.id.tv_content);
        mentionTextView.setMovementMethod(new LinkMovementMethod());
        mentionTextView.setParserConverter(mTagParser);

        String mContent = "";
        String atName = "";
        // 发布人
        helper.setText(R.id.tv_name, V.v(() -> item.getAccountEntity().getRealName()));
        // 头像
        GlideUtil.intoImageView(mContext, Uri.parse(BuildConfig.OSS_SERVER + item.getAccountEntity().getAvatar()), ivHeader);
        // 公司名称
        helper.setText(R.id.tv_company, item.getPublisherOrg().getOrgName());
        // 艾特人
        if (V.v(() -> (item.getAtMap() != null))) {
            atName = SecurityItemUtil.getInstance().doJonint(item.getAtMap());
        } else {
            atName = "";
        }
        //发布的内容
        if (item.getType() == 1) {
            mContent = "我提了一个问题，邀请你来回答" + atName;
            helper.setText(R.id.tv_question_content, item.getSpcContent());
            helper.getView(R.id.ll_question).setVisibility(View.VISIBLE);
        } else {
            mContent = item.getSpcContent() + atName;
            helper.getView(R.id.ll_question).setVisibility(View.GONE);
        }
        CharSequence convertMetionString = mRangeManager.getFormatCharSequence(mContent);
        mentionTextView.setText(convertMetionString);
        mentionTextView.setEnabled(false);
        // 点赞数量
        helper.setText(R.id.tv_like_num, item.getLikesCount() + "");
        // 评论数量
        helper.setText(R.id.tv_comments_num, item.getCommentCount() + "");
        helper.setText(R.id.tv_time, ETimeUtils.getTimeFormatText(item.getCreateTime()));
        /**
         * 是否是好友 2 好友 1 不是好友
         * */
        if (item.getFriend() == 2) {
            helper.getView(R.id.tv_friend).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.tv_friend).setVisibility(View.GONE);
        }
        /**
         * 是否认证
         * */
        if (item.getVerifyStatus() == 1) {
            helper.getView(R.id.iv_certifi).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.iv_certifi).setVisibility(View.GONE);
        }
        /**
         * 0 是关注 1 是未关注
         * */
        if (item.getFollowsStatus() == 0) {
            helper.getView(R.id.tv_isFocus).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.tv_isFocus).setVisibility(View.VISIBLE);
        }

        /**
         * 0 点赞 1 未点赞
         * */
        if (item.getLikeStatus() == 0) {
            helper.setImageResource(R.id.iv_like, R.mipmap.ic_worker_security_like_pressed);
        } else {
            helper.setImageResource(R.id.iv_like, R.mipmap.ic_worker_security_like_unpressed);
        }
        /**
         * 阅读数量
         * */
        helper.setText(R.id.tv_readCount, item.getReadCount() + "");
        picList.clear();
        pics = null;
        if (!StringUtils.isEmpty(item.getSpcImg())) {
            securityImageLayout.setVisibility(View.VISIBLE);
            pics = item.getSpcImg().split(",");
            picList.addAll(Stream.of(Arrays.asList(pics)).map(url -> (url)).toList());
            securityImageLayout.setImageUrls(picList);
        } else {
            securityImageLayout.setVisibility(View.GONE);
        }
        /**
         *視頻缩略图
         * */
        if (!StringUtils.isEmpty(item.getSpcVideo())) {
            helper.getView(R.id.rl_video).setVisibility(View.VISIBLE);
            GlideUtil.intoImageView(mContext, Uri.parse(BuildConfig.OSS_SERVER + item.getSpcVideo() + ".jpg"), ivShowVideo);
        } else {
            helper.getView(R.id.rl_video).setVisibility(View.GONE);
        }

        if (item.getReadStatus() == 0 && mIsUnRead) {
            helper.getView(R.id.tv_unread).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.tv_unread).setVisibility(View.GONE);
        }

        helper.addOnClickListener(R.id.rl_video);
        helper.addOnClickListener(R.id.tv_isFocus);
        helper.addOnClickListener(R.id.ll_like);
        helper.addOnClickListener(R.id.ll_comments);
        helper.addOnClickListener(R.id.iv_share);
        helper.addOnClickListener(R.id.ll_question);
        securityImageLayout.setOnPhotoClickListener((position -> {
            onPhotoClickListener.onPhotoClick(helper.getAdapterPosition(), position);
        }));
    }

    public interface OnPhotoClickListener {
        /**
         * 图片点击事件
         *
         * @param position
         */
        void onPhotoClick(int position, int mWhich);
    }
}
