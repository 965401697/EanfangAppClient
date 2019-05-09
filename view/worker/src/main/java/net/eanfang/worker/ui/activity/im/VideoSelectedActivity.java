package net.eanfang.worker.ui.activity.im;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.model.VideoBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.ToastUtil;

import net.eanfang.worker.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.FileMessage;

public class VideoSelectedActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private VideoItemAdapter mVideoItemAdapter;
    private List<String> pathList = new ArrayList<>();
    private String mTargetId;
    private Conversation.ConversationType mConversationType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_selected);
        ButterKnife.bind(this);
        initViews();
        setTitle("视频文件选择");
        setLeftBack();
        mTargetId = getIntent().getStringExtra("targetId");
        mConversationType = (Conversation.ConversationType) getIntent().getSerializableExtra("conversationType");

        setRightTitle("发送");
        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFlieMsg();
            }
        });
    }

    private void initViews() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mVideoItemAdapter = new VideoItemAdapter(R.layout.item_video);
        mVideoItemAdapter.bindToRecyclerView(recyclerView);

        mVideoItemAdapter.setNewData(getList(this));

        mVideoItemAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {


                if (view.getId() == R.id.cb_checked) {
                    VideoBean bean = (VideoBean) adapter.getData().get(position);

                    if (bean.getFlag() == 0) {
                        if (pathList.size() >= 9) {
                            ToastUtil.get().showToast(VideoSelectedActivity.this, "最多只能选择9个视频文件");
                        } else {
                            bean.setFlag(1);
                            pathList.add(bean.getName());
                        }
                    } else {
                        bean.setFlag(0);
                        pathList.remove(bean.getName());
                    }

                    if (pathList.size() == 0) {
                        setRightTitle("发送");
                    } else {
                        setRightTitle("发送(" + pathList.size() + "/9)");
                    }


                    adapter.notifyItemChanged(position);
                }
            }
        });
    }

    private void sendFlieMsg() {
        if (pathList.size() == 0) {
            ToastUtil.get().showToast(this, "必须选择一个视频文件");
            return;
        }
        for (String s : pathList) {
            FileMessage fileMessage = FileMessage.obtain(Uri.parse("file://" + s));
            io.rong.imlib.model.Message message = io.rong.imlib.model.Message.obtain(mTargetId, mConversationType, fileMessage);
            RongIM.getInstance().sendMediaMessage(message, null, null, new IRongCallback.ISendMediaMessageCallback() {
                @Override
                public void onProgress(Message message, int i) {

                }

                @Override
                public void onCanceled(Message message) {

                }

                @Override
                public void onAttached(Message message) {

                }

                @Override
                public void onSuccess(Message message) {

                }

                @Override
                public void onError(Message message, RongIMClient.ErrorCode errorCode) {

                }
            });
        }
        VideoSelectedActivity.this.finish();
    }


    private List<VideoBean> getList(Context context) {
        List<VideoBean> sysVideoList = new ArrayList<>();
        // MediaStore.Video.Thumbnails.DATA:视频缩略图的文件路径
        String[] thumbColumns = {MediaStore.Video.Thumbnails.DATA,
                MediaStore.Video.Thumbnails.VIDEO_ID};
        // 视频其他信息的查询条件
        String[] mediaColumns = {MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA, MediaStore.Video.Media.DURATION};

        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns, null, null, null);

        if (cursor == null) {
            return sysVideoList;
        }
        if (cursor.moveToFirst()) {
            do {
                VideoBean videoBean = new VideoBean();
                int id = cursor.getInt(cursor
                        .getColumnIndex(MediaStore.Video.Media._ID));
                Cursor thumbCursor = context.getContentResolver().query(
                        MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
                        thumbColumns, MediaStore.Video.Thumbnails.VIDEO_ID
                                + "=" + id, null, null);
                if (thumbCursor.moveToFirst()) {
                    videoBean.setPath(thumbCursor.getString(thumbCursor
                            .getColumnIndex(MediaStore.Video.Thumbnails.DATA)));
                }
                videoBean.setName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media
                        .DATA)));
//                path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media
//                        .DATA));
//                info.setFileSize(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video
//                        .Media.DURATION)));
                sysVideoList.add(videoBean);
            } while (cursor.moveToNext());
        }
        return sysVideoList;
    }

}
