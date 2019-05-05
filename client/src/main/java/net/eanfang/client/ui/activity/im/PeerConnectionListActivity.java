package net.eanfang.client.ui.activity.im;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.PeerConnectionDataBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.my.UserHomeActivity;
import net.eanfang.client.ui.adapter.PeerConnectionListAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author liangkailun
 * Date ：2019/4/9
 * Describe : 同行人脉列表页面
 */
public class PeerConnectionListActivity extends BaseClientActivity {
    private static final String TAG = "ConnectionListActivity";
    /**
     * 带返回值请求用户首页code
     */
    private static final int REQUEST_USER_HOME_CODE = 1;

    @BindView(R.id.recycler_view_connectionList)
    RecyclerView mRecyclerViewConnectionList;

    private PeerConnectionListAdapter mPeerConnectionListAdapter;

    /**
     * 当前页数
     */
    private int mCurrPage = 1;

    /**
     * 总页数
     */
    private int mTotalPage;

    /**
     * 同行人脉基础bean
     */
    private PeerConnectionDataBean.ListBean mListBean;

    /**
     * 关注状态按钮
     */
    private Button mBtnFollow;

    /**
     * 当前item位置
     */
    private int mClickPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peer_connection_list);
        ButterKnife.bind(this);
        initView();
        initDate();
    }

    private void initView() {
        setTitle("同行人脉");
        setLeftBack();
        mRecyclerViewConnectionList.setLayoutManager(new LinearLayoutManager(this));
        mPeerConnectionListAdapter = new PeerConnectionListAdapter(R.layout.item_peer_connection_list);
        mPeerConnectionListAdapter.bindToRecyclerView(mRecyclerViewConnectionList);
        mPeerConnectionListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.btn_connection_item_addOrCancel:
                    mBtnFollow = (Button) view;
                    mListBean = (PeerConnectionDataBean.ListBean) adapter.getItem(position);
                    if (mListBean != null && mListBean.getDefaultUser() != null) {
                        PeerConnectionDataBean.ListBean.DefaultUserBean defaultUserBean = mListBean.getDefaultUser();
                        changeFollowStatus(defaultUserBean.getAccId(), defaultUserBean.getUserId(), defaultUserBean.getCompanyId(),
                                defaultUserBean.getTopCompanyId(), defaultUserBean.getFollowStatus(), position);
                    }
                    break;
                default:
                    break;
            }
        });

        mPeerConnectionListAdapter.setOnItemClickListener((adapter, view, position) -> {
            mClickPosition = position;
            PeerConnectionDataBean.ListBean bean = (PeerConnectionDataBean.ListBean)
                    adapter.getItem(position);
            if (bean != null) {
                Intent intent = new Intent(PeerConnectionListActivity.this, UserHomeActivity.class);
                intent.putExtra(UserHomeActivity.EXTRA_ACCID, bean.getAccId());
                startActivityForResult(intent, REQUEST_USER_HOME_CODE);
            }
        });

        mPeerConnectionListAdapter.setOnLoadMoreListener(() -> {
            if (mCurrPage < mTotalPage) {
                mCurrPage++;
                initDate();
            }
        }, mRecyclerViewConnectionList);

    }

    private void initDate() {
        QueryEntry entry = new QueryEntry();
        entry.setSize(50);
        entry.setPage(mCurrPage);
        EanfangHttp.post(UserApi.POST_CONNECTIONS_LIST)
                .upJson(JsonUtils.obj2String(entry))
                .execute(new EanfangCallback<PeerConnectionDataBean>(this, true, PeerConnectionDataBean.class, bean -> {
                    if (bean == null) {
                        Log.d(TAG, "initDate: PeerConnectionDataBean == null");
                        return;
                    }
                    mTotalPage = bean.getTotalPage();
                    if (mCurrPage == 1) {
                        mPeerConnectionListAdapter.getData().clear();
                        mPeerConnectionListAdapter.setNewData(bean.getList());
                    } else {
                        mPeerConnectionListAdapter.addData(bean.getList());
                    }
                    mPeerConnectionListAdapter.loadMoreComplete();
                    if (bean.getCurrPage() >= mTotalPage) {
                        mPeerConnectionListAdapter.loadMoreEnd();
                    }
                }));
    }

    /**
     * 改变关注按钮显示
     *
     * @param doFollow    1 加关注  0 取消关注
     * @param changeIndex 修改item的下标
     */
    private void changeBtnShow(int doFollow, int changeIndex) {
        if (doFollow == 1) {
            mBtnFollow.setSelected(false);
            mBtnFollow.setText("已关注");
            if (mListBean != null && mListBean.getDefaultUser() != null) {
                mListBean.getDefaultUser().setFollowStatus(0);
            }
            mPeerConnectionListAdapter.remove(changeIndex);
        } else {
            mBtnFollow.setSelected(true);
            mBtnFollow.setText("+ 关注");
            if (mListBean != null && mListBean.getDefaultUser() != null) {
                mListBean.getDefaultUser().setFollowStatus(1);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            boolean isFollow = data.getBooleanExtra(UserHomeActivity.RESULT_FOLLOW_STATE, true);
            if (isFollow) {
                mPeerConnectionListAdapter.remove(mClickPosition);
                mPeerConnectionListAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 改变用户关注状态
     *
     * @param asUserId       被关注人id
     * @param asCompanyId    被关注人公司id
     * @param asTopCompanyId 被关注人总公司id
     * @param doFollow       1：添加关注  0：取消关注
     * @param changeIndex    修改item的下标
     */
    private void changeFollowStatus(String asAccId, String asUserId, String asCompanyId,
                                    String asTopCompanyId, int doFollow, int changeIndex) {
        Log.d(TAG, "changeFollowStatus: asAccId " + asAccId + "asUserId:" + asUserId + "  asCompanyId:" + asCompanyId
                + "  asTopCompanyId:" + asTopCompanyId + "  doFollow:" + doFollow);
        EanfangHttp.post(UserApi.POST_CHANGE_FOLLOW_STATUS)
                .params("asAccId", asAccId)
                .params("asUserId", asUserId)
                .params("asCompanyId", asCompanyId)
                .params("asTopCompanyId", asTopCompanyId)
                .params("followStatus", String.valueOf(doFollow))
                .execute(new EanfangCallback(this, true, JSONObject.class, bean -> {
                    Log.d(TAG, "changeFollowStatus: 关注状态上传成功");
                    changeBtnShow(doFollow, changeIndex);
                    showToast(doFollow == 0 ? "取消关注成功" : "添加关注成功");
                }));
    }

}
