package net.eanfang.client.ui.activity.pay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.JsonUtils;
import com.eanfang.biz.model.QueryEntry;
import com.eanfang.biz.model.entity.InvoiceEntity;
import com.eanfang.biz.model.entity.ReceiveAddressEntity;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.base.BaseClientActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressListActivity extends BaseClientActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_no_datas)
    TextView mTvNoData;
    @BindView(R.id.swipre_fresh)
    SwipeRefreshLayout mSwipeRefreshLayout;


    private int mPage = 1;
    private AddressListAdapter mAdapter;
    private final int ADD_ADDRESS_REQUST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_address_list);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("地址列表");
        setLeftBack();
        setRightImageResId(R.mipmap.ic_news_add);
        setRightImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(AddressListActivity.this, AddAddressActivity.class), ADD_ADDRESS_REQUST_CODE);
            }
        });
        mPage = 1;

        initView();
    }

    private void initView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        rvList.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new AddressListAdapter(R.layout.item_address_list);
        mAdapter.bindToRecyclerView(rvList);
        mAdapter.setOnLoadMoreListener(this);

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ReceiveAddressEntity bean = (ReceiveAddressEntity) adapter.getData().get(position);
                if (view.getId() == R.id.cb_checked) {

                    EanfangHttp.post(NewApiService.SET_DEFAULT_ADDRESS)
                            .params("id", bean.getId())
                            .execute(new EanfangCallback(AddressListActivity.this, true, InvoiceEntity.class) {
                                @Override
                                public void onSuccess(Object bean) {
                                    super.onSuccess(bean);
                                    showToast("设置成功");
                                    refresh();//设置成功
                                }

                            });

                } else if (view.getId() == R.id.iv_edit) {
                    startActivityForResult(new Intent(AddressListActivity.this, AddAddressActivity.class).putExtra("bean", bean), ADD_ADDRESS_REQUST_CODE);
                }
            }

        });
        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

                new TrueFalseDialog(AddressListActivity.this, "系统提示", "您确定删除当前地址？", () -> {


                    ReceiveAddressEntity b = (ReceiveAddressEntity) adapter.getData().get(position);

                    EanfangHttp.post(NewApiService.DELETE_ADDRESS)
                            .params("id", b.getId())
                            .execute(new EanfangCallback(AddressListActivity.this, true, InvoiceEntity.class) {
                                @Override
                                public void onSuccess(Object bean) {
                                    super.onSuccess(bean);
                                    adapter.getData().remove(b);
                                    adapter.notifyDataSetChanged();
                                    showToast("删除成功");
                                }

                            });
                }).showDialog();
                return false;
            }
        });

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //选择地址
                ReceiveAddressEntity b = (ReceiveAddressEntity) adapter.getData().get(position);
                Intent intent = new Intent();
                intent.putExtra("bean", b);
                setResult(RESULT_OK, intent);
                finishSelf();
            }
        });

        mSwipeRefreshLayout.setRefreshing(true);
        getData();
    }


    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        refresh();
    }

    public void refresh() {
        mPage = 1;//下拉永远第一页
        getData();
    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        mPage++;
        getData();
    }


    private void getData() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.setSize(10);
        queryEntry.setPage(mPage);
        queryEntry.getEquals().put("userId", String.valueOf(ClientApplication.get().getUserId()));

        EanfangHttp.post(NewApiService.GET_ADDRESS_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<ReceiveAddressEntity>(this, true, ReceiveAddressEntity.class, true, (list) -> {


                    if (mPage == 1) {
                        mAdapter.getData().clear();
                        mAdapter.setNewData(list);
                        mAdapter.loadMoreComplete();
                        if (list.size() < 10) {
                            mAdapter.loadMoreEnd();
                        }

                        if (list.size() > 0) {
                            mTvNoData.setVisibility(View.GONE);
                        } else {
                            mTvNoData.setVisibility(View.VISIBLE);
                        }


                    } else {
                        mAdapter.addData(list);
                        mAdapter.loadMoreComplete();
                        if (list.size() < 10) {
                            mAdapter.loadMoreEnd();
                        }
                    }

                    mSwipeRefreshLayout.setRefreshing(false);

                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ADD_ADDRESS_REQUST_CODE) {
            refresh();
        }
    }
}
