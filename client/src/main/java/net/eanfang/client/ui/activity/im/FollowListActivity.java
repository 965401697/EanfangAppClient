package net.eanfang.client.ui.activity.im;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.FollowDataBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.my.UserHomeActivity;
import net.eanfang.client.ui.adapter.FollowListAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author liangkailun
 * Date ：2019/4/9
 * Describe : 关注列表页面
 */
public class FollowListActivity extends BaseClientActivity {
    private static final String TAG = "FollowListActivity";
    /**
     * 加载更多数据的最少个数
     */
    private static final int LOAD_MORE_LEAST_SIZE = 10;
    @BindView(R.id.recycler_view_followList)
    RecyclerView mRecyclerViewFollowList;

    private FollowListAdapter mFollowListAdapter;

    /**
     * 当前页数
     */
    private int mCurrPage = 1;

    /**
     * 总页数
     */
    private int mTotalPage;

    /**
     * 关注按钮
     */
    private Button mBtnFollow;

    private FollowDataBean.FollowListBean mFollowListBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_follow_list);
        ButterKnife.bind(this);
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initDate();
    }

    private void initView() {
        setTitle("我关注的人");
        setLeftBack();
        mRecyclerViewFollowList.setLayoutManager(new LinearLayoutManager(this));
        mFollowListAdapter = new FollowListAdapter(R.layout.item_my_follow_list);
        mFollowListAdapter.bindToRecyclerView(mRecyclerViewFollowList);
        mFollowListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.btn_follow_item_addOrCancel:
                    mFollowListBean = (FollowDataBean.FollowListBean) adapter.getItem(position);
                    if (mFollowListBean != null && mFollowListBean.getUserEntity() != null &&
                            mFollowListBean.getUserEntity().getAccountEntity() != null &&
                            mFollowListBean.getUserEntity().getAccountEntity().getAccId() != null) {
                        changeFollowStatus(String.valueOf(
                                mFollowListBean.getUserEntity().getAccountEntity().getAccId()),
                                mFollowListBean.getAsUserId(), mFollowListBean.getAsCompanyId(),
                                mFollowListBean.getAsTopCompanyId(), mFollowListBean.getFollowsStatus());
                    }
                    mBtnFollow = (Button) view;
                    break;
                default:
                    break;
            }
        });

        mFollowListAdapter.setOnItemClickListener((adapter, view, position) -> {
            mFollowListBean = (FollowDataBean.FollowListBean) adapter.getItem(position);
            if (mFollowListBean != null && mFollowListBean.getUserEntity() != null &&
                    mFollowListBean.getUserEntity().getAccountEntity() != null &&
                    mFollowListBean.getUserEntity().getAccountEntity().getAccId() != null) {
                UserHomeActivity.startActivity(this,
                        String.valueOf(mFollowListBean.getUserEntity().getAccountEntity().getAccId()));
            }
        });

        mFollowListAdapter.setOnLoadMoreListener(() -> {
            if (mCurrPage < mTotalPage) {
                mCurrPage++;
                initDate();
            }
        }, mRecyclerViewFollowList);

    }

    private void initDate() {
        QueryEntry entry = new QueryEntry();
        entry.getEquals().put("followAccId",
                String.valueOf(EanfangApplication.get().getAccId()));
        entry.setSize(10);
        entry.setPage(mCurrPage);

        EanfangHttp.post(UserApi.POST_FOLLOWS_LIST)
                .upJson(JsonUtils.obj2String(entry))
                .execute(new EanfangCallback<FollowDataBean>(this, true, FollowDataBean.class, bean -> {
                    if (bean == null) {
                        Log.d(TAG, "initDate: FollowDataBean == null");
                        return;
                    }
                    mTotalPage = bean.getTotalPage();
                    if (mCurrPage == 1) {
                        mFollowListAdapter.getData().clear();
                        mFollowListAdapter.setNewData(bean.getList());
                    } else {
                        mFollowListAdapter.addData(bean.getList());
                    }
                    mFollowListAdapter.loadMoreComplete();
                    if (bean.getList().size() < LOAD_MORE_LEAST_SIZE) {
                        mFollowListAdapter.loadMoreEnd();
                    }
                }));
    }

    /**
     * 改变按钮的展示
     *
     * @param isFollowed true：关注 false：未关注
     */
    private void changeFollowBtnShow(boolean isFollowed) {
        if (isFollowed) {
            mBtnFollow.setSelected(false);
            mBtnFollow.setText("已关注");
            if (mFollowListBean != null) {
                mFollowListBean.setFollowsStatus(0);
            }
        } else {
            mBtnFollow.setSelected(true);
            mBtnFollow.setText("+ 关注");
            if (mFollowListBean != null) {
                mFollowListBean.setFollowsStatus(1);
            }
        }
    }

    /**
     * 改变用户关注状态
     *
     * @param asAccId        被关注人accId
     * @param asUserId       被关注人id
     * @param asCompanyId    被关注人公司id
     * @param asTopCompanyId 被关注人总公司id
     * @param status         0：取消关注  1：关注
     */
    private void changeFollowStatus(String asAccId, String asUserId, String asCompanyId,
                                    String asTopCompanyId, int status) {
        Log.e(TAG, "changeFollowStatus: asAccId" + asAccId + "asUserId:" + asUserId + "  asCompanyId:" + asCompanyId
                + "  asTopCompanyId:" + asTopCompanyId + "  status:" + status);
        EanfangHttp.post(UserApi.POST_CHANGE_FOLLOW_STATUS)
                .params("asAccId", asAccId)
                .params("asUserId", asUserId)
                .params("asCompanyId", asCompanyId)
                .params("asTopCompanyId", asTopCompanyId)
                .params("followStatus", String.valueOf(status))
                .execute(new EanfangCallback(this, true, JSONObject.class, bean -> {
                    Log.d(TAG, "changeFollowStatus: 关注状态上传成功");
                    showToast(status == 0 ? "取消关注成功" : "添加关注成功");
                    changeFollowBtnShow(status != 0);
                }));
    }

}