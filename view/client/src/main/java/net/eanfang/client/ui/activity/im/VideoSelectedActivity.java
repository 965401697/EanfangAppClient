package net.eanfang.client.ui.activity.im;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.biz.model.VideoBean;
import com.eanfang.takevideo.PlayVideoActivity;
import com.eanfang.util.JumpItent;
import com.eanfang.util.ToastUtil;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hutool.core.io.FileUtil;
import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.FileMessage;

public class VideoSelectedActivity extends BaseClientActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private VideoItemAdapter mVideoItemAdapter;
    private List<String> pathList = new ArrayList<>();
    private String mTargetId;
    private Conversation.ConversationType mConversationType;

    /**
     * 其他地方选择视频
     */
    private String mType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_video_selected);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initViews();
        setTitle("视频文件选择");
        setLeftBack();
        mTargetId = getIntent().getStringExtra("targetId");
        mConversationType = (Conversation.ConversationType) getIntent().getSerializableExtra("conversationType");
        mType = getIntent().getStringExtra("type");

        if (("addSecurity").equals(mType)) {
            setRightTitle("确定");
        } else {
            setRightTitle("发送");
        }
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
                    // 安防圈 添加视频
                    if (("addSecurity").equals(mType)) {
                        if (bean.getFlag() == 0) {
                            if (pathList.size() >= 1) {
                                ToastUtil.get().showToast(VideoSelectedActivity.this, "最多只能选择1个视频文件");
                            } else {
                                bean.setFlag(1);
                                pathList.add(bean.getName());
                            }
                        } else {
                            bean.setFlag(0);
                            pathList.remove(bean.getName());
                        }
                    } else {
                        if (bean.getFlag() == 0) {
                            if (pathList.size() >= 9) {
                                ToastUtil.get().showToast(VideoSelectedActivity.this, "最高只能选择9个视频文件");
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
        /**
         * 安防圈 添加视频
         * */
        if (("addSecurity").equals(mType)) {
            Bundle bundle = new Bundle();
            bundle.putString("videoPath", pathList.get(0));
            bundle.putBoolean("isTake", true);
            JumpItent.jump(VideoSelectedActivity.this, PlayVideoActivity.class, bundle);
        } else {
            for (String s : pathList) {
                FileMessage fileMessage = FileMessage.obtain(Uri.parse("file://" + s));
                Message message = Message.obtain(mTargetId, mConversationType, fileMessage);
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
        }
        VideoSelectedActivity.this.finish();
    }


    private List<VideoBean> getList(Context context) {
        List<VideoBean> sysVideoList = new ArrayList<>();
        Cursor c = null;
        try {
            c = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
            while (c.moveToNext()) {
                // 路径
                String path = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                if (!FileUtil.exist(path)) {
                    continue;
                }
                // 视频的id
                int id = c.getInt(c.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                // 视频名称
                String name = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
                //分辨率
                String resolution = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.RESOLUTION));
                // 大小
                long size = c.getLong(c.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
                // 时长
                long duration = c.getLong(c.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                //修改时间
                long date = c.getLong(c.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED));
                VideoBean videoBean = new VideoBean();
                videoBean.setPath(path);
                videoBean.setName(path);
                sysVideoList.add(videoBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return sysVideoList;
    }

}
