package net.eanfang.worker.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.annimon.stream.Stream;
import com.eanfang.BuildConfig;
import com.eanfang.delegate.BGASortableDelegate;
import com.eanfang.model.security.SecurityHotBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.ETimeUtils;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import net.eanfang.worker.R;

import java.util.ArrayList;
import java.util.Arrays;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGARecyclerViewHolder;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * @author guanluocang
 * @data 2019/1/28
 * @description 安防圈adapter
 */

public class HomeSecurityAdapter extends BGARecyclerViewAdapter<SecurityHotBean.ListBean> {
    private Context context;
    private ArrayList<String> picList = new ArrayList<>();

    public HomeSecurityAdapter(RecyclerView recyclerView, Context mContext) {
        super(recyclerView, R.layout.layout_security_item);
        this.context = mContext;
    }


    @Override
    public void onViewRecycled(BGARecyclerViewHolder holder) {
        super.onViewRecycled(holder);
        BGASortableNinePhotoLayout ninePhotoLayout = holder.getViewHolderHelper().getView(R.id.snpl_pic);
        ninePhotoLayout.setData(null);
        ninePhotoLayout.setDelegate(null);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, SecurityHotBean.ListBean item) {
        SimpleDraweeView ivHeader = helper.getView(R.id.iv_seucrity_header);
        BGASortableNinePhotoLayout ninePhotoLayout = helper.getView(R.id.snpl_pic);
        // 发布人
        helper.setText(R.id.tv_name, item.getPublisherUser().getAccountEntity().getRealName());
        // 头像
        ivHeader.setImageURI((Uri.parse(BuildConfig.OSS_SERVER + item.getPublisherUser().getAccountEntity().getAvatar())));
        // 公司名称
        helper.setText(R.id.tv_company, item.getPublisherOrg().getOrgName());
        //发布的内容
        helper.setText(R.id.tv_content, item.getSpcContent());
        // 点赞数量
        helper.setText(R.id.tv_like_num, item.getLikesCount() + "");
        // 评论数量
        helper.setText(R.id.tv_comments_num, item.getFollowCount() + "");
        helper.setText(R.id.tv_time, ETimeUtils.getTimeFormatText(item.getCreateTime()));
        picList.clear();
        if (!StringUtils.isEmpty(item.getSpcImg())) {
            ninePhotoLayout.setVisibility(View.VISIBLE);
            String[] pics = item.getSpcImg().split(",");
            for (int i = 0; i < pics.length; i++) {
                picList.addAll(Stream.of(Arrays.asList(pics)).map(url -> (BuildConfig.OSS_SERVER + url).toString()).toList());
            }
        } else {
            ninePhotoLayout.setVisibility(View.GONE);
        }
        ninePhotoLayout.setDelegate(new BGASortableDelegate((BaseActivity) context));
        ninePhotoLayout.setData(picList);
    }
}
