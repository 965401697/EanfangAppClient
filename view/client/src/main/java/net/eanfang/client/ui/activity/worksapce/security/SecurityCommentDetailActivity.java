package net.eanfang.client.ui.activity.worksapce.security;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.biz.model.security.SecurityCommentBean;
import com.eanfang.biz.model.security.SecurityDetailBean;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.security.SecurityCommentDetailBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.StringUtils;
import com.eanfang.base.kit.V;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.adapter.security.SecurityCommentSecondAdapter;
import net.eanfang.client.ui.widget.GeneralSDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author guanluocang
 * @data 2019/5/22
 * @description 安防圈评论详情
 */
public class SecurityCommentDetailActivity extends BaseActivity implements
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.iv_seucrity_header)
    CircleImageView ivSeucrityHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_friend)
    TextView tvFriend;
    @BindView(R.id.iv_certifi)
    ImageView ivCertifi;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_comment_count)
    TextView tvCommentCount;
    @BindView(R.id.rv_comments)
    RecyclerView rvComments;
    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.swipe_fresh)
    SwipeRefreshLayout swipeFresh;
    private int mCommentId;
    private Long mAsId;
    private SecurityDetailBean.PageUtilBean.ListBean mCommentBean;
    private SecurityCommentSecondAdapter securityCommentAdapter;
    private List<SecurityCommentDetailBean.ListBean> commentList = new ArrayList<>();
    /**
     * 点击哪个评论进行回复
     */
    private int mParentCommentId = 0;
    private GeneralSDialog generalDialog;
    /**
     * 当前页面是否进行 评论删除操作
     */
    private boolean isEdit = false;

    private QueryEntry mQueryEntry;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_security_comment_detail);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        setTitle("评论详情");
        securityCommentAdapter = new SecurityCommentSecondAdapter();
        rvComments.setLayoutManager(new LinearLayoutManager(this));
        securityCommentAdapter.bindToRecyclerView(rvComments);
        rvComments.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
//        rvComments.setNestedScrollingEnabled(false);
        swipeFresh.setOnRefreshListener(this);
        securityCommentAdapter.setOnLoadMoreListener(this, rvComments);
        securityCommentAdapter.disableLoadMoreIfNotFullPage();
        mCommentId = getIntent().getIntExtra("mCommentId", 0);
        mAsId = getIntent().getLongExtra("mAsId", 0);
        mCommentBean = (SecurityDetailBean.PageUtilBean.ListBean) getIntent().getSerializableExtra("mComment");
    }

    private void initData() {
        // 头像
        GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + V.v(() -> mCommentBean.getCommentUser().getAccountEntity().getAvatar())),ivSeucrityHeader);
        // 评论人
        tvName.setText(mCommentBean.getCommentUser().getAccountEntity().getRealName());
        // 公司名称
        tvCompany.setText(mCommentBean.getCommentOrg().getOrgName());
        //发布的内容
        tvContent.setText(mCommentBean.getCommentsContent());
        //时间
        tvTime.setText(mCommentBean.getCreateTime());
        // 是否是好友
        /**
         * 是否是好友 2 好友 1 不是好友
         * */
        if (mCommentBean.getFriend() == 2) {
            tvFriend.setVisibility(View.VISIBLE);
        } else {
            tvFriend.setVisibility(View.GONE);
        }
        /**
         * 是否认证
         * */
        if (mCommentBean.getVerifyStatus() == 1) {
            ivCertifi.setVisibility(View.VISIBLE);
        } else {
            ivCertifi.setVisibility(View.GONE);
        }
        doGetCommentList();
        showKeyboard();
    }

    private void initListener() {
        setLeftBack(v -> {
            if (isEdit) {
                doFinish();
            }
            finishSelf();
        });
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etInput.setCompoundDrawables(null, null, null, null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        securityCommentAdapter.setOnItemClickListener(((adapter, view, position) -> {
            mParentCommentId = securityCommentAdapter.getData().get(position).getId();
            showKeyboard();
        }));
        securityCommentAdapter.setOnItemChildClickListener(((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_deleteComment:
                    generalDialog = new GeneralSDialog(this, "确定删除此评论吗", new GeneralSDialog.OnConfimClickListener() {
                        @Override
                        public void onConfim() {
                            doDeleteComment(securityCommentAdapter.getData().get(position).getId());
                        }
                    });
                    generalDialog.show();
                    break;
                default:
                    break;
            }
        }));
    }

    private void doDeleteComment(int mDeleteId) {
        EanfangHttp.post(NewApiService.SERCURITY_COMMENT_DELETE)
                .params("id", mDeleteId)
                .execute(new EanfangCallback<com.alibaba.fastjson.JSONObject>(this, true, com.alibaba.fastjson.JSONObject.class, bean -> {
                    showToast("删除成功");
                    isEdit = true;
                    generalDialog.dismiss();
                    page = 1;
                    doGetCommentList();
                }));
    }

    private void doGetCommentList() {
        if (mQueryEntry == null) {
            mQueryEntry = new QueryEntry();
        }
        mQueryEntry.setPage(page);
        mQueryEntry.setSize(10);
        mQueryEntry.getEquals().put("id", mCommentId + "");
        EanfangHttp.post(NewApiService.SERCURITY_COMMENT_DETAIL)
                .upJson(JsonUtils.obj2String(mQueryEntry))
                .execute(new EanfangCallback<SecurityCommentDetailBean>(this, true, SecurityCommentDetailBean.class) {
                    @Override
                    public void onSuccess(SecurityCommentDetailBean bean) {
                        if (page == 1) {
                            if (commentList != null && commentList.size() > 0) {
                                commentList.clear();
                            }
                            if (bean.getList() != null && bean.getList().size() > 0) {
                                tvCommentCount.setText(bean.getList().get(0).getReplyCount() + "");
                            } else {
                                tvCommentCount.setText("0");
                            }
                            commentList = bean.getList();
                            securityCommentAdapter.getData().clear();
                            securityCommentAdapter.setNewData(commentList);
                            rvComments.scrollToPosition(0);

                            swipeFresh.setRefreshing(false);
                            securityCommentAdapter.loadMoreComplete();
                            if (bean.getList() != null && bean.getList().size() < 10) {
                                securityCommentAdapter.loadMoreEnd();
                                //释放对象
                                mQueryEntry = null;
                            }

                        } else {
                            securityCommentAdapter.addData(bean.getList());
                            securityCommentAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                securityCommentAdapter.loadMoreEnd();
                            }
                        }
                    }

                    @Override
                    public void onNoData(String message) {
                        swipeFresh.setRefreshing(false);
                        securityCommentAdapter.loadMoreEnd();
                    }

                    @Override
                    public void onCommitAgain() {
                        swipeFresh.setRefreshing(false);
                    }
                });
    }

    /**
     * 显示布局与键盘
     */
    private void showKeyboard() {
        etInput.requestFocus();
        etInput.setFocusable(true);
        etInput.setFocusableInTouchMode(true);
        StringUtils.showKeyboard(SecurityCommentDetailActivity.this, etInput);
    }

    /**
     * 隐藏键盘与布局
     */
    private void hideKeyboard() {
        //清空输入
        etInput.setText("");
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 点击其他地方时，将软键盘隐藏
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev, tvSend)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event, TextView tvSend) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            int[] sendLeftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            tvSend.getLocationInWindow(sendLeftTop);
            int left = leftTop[0], top = leftTop[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            int sendLeft = sendLeftTop[0], sendTop = sendLeftTop[1], sendBottom = sendTop + tvSend.getHeight(), sendRight = sendLeft + tvSend.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else return !(event.getX() > sendLeft) || !(event.getX() < sendRight)
                    || !(event.getY() > sendTop) || !(event.getY() < sendBottom);
        }
        return false;
    }

    @OnClick(R.id.tv_send)
    public void onViewClicked() {
        doSend();
    }

    private void doSend() {
        String mComments = etInput.getText().toString().trim();
        if (StringUtils.isEmpty(mComments)) {
            showToast("请输入评论内容");
            return;
        }
        SecurityCommentBean securityCommentBean = new SecurityCommentBean();
        securityCommentBean.setType("0");
        securityCommentBean.setStatus("0");
        securityCommentBean.setCommentsContent(mComments);
        securityCommentBean.setAsId(mAsId);
        securityCommentBean.setCommentsAnswerId(ClientApplication.get().getUserId());
        securityCommentBean.setCommentsAnswerAccId(ClientApplication.get().getLoginBean().getAccount().getAccId());
        securityCommentBean.setCommentsCompanyId(ClientApplication.get().getLoginBean().getAccount().getDefaultUser().getCompanyId());
        securityCommentBean.setCommentsTopCompanyId(ClientApplication.get().getLoginBean().getAccount().getDefaultUser().getTopCompanyId());
        securityCommentBean.setTopCommentsId(mCommentId);
        if (mParentCommentId != 0) {
            securityCommentBean.setParentCommentsId(mParentCommentId);
        } else {
            securityCommentBean.setParentCommentsId(mCommentId);
        }
        EanfangHttp.post(NewApiService.SERCURITY_COMMENT)
                .upJson(com.alibaba.fastjson.JSONObject.toJSONString(securityCommentBean))
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, bean -> {
                    mParentCommentId = 0;
                    isEdit = true;
                    page = 1;
                    doGetCommentList();
                    hideKeyboard();
                }));

    }

    private void doFinish() {
        Intent intent = new Intent();
        intent.putExtra("isEdit", true);
        setResult(RESULT_OK, intent);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        mQueryEntry = null;
        page = 1;
        doGetCommentList();
    }


    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        page++;
        doGetCommentList();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && isEdit) {
            hideKeyboard();
            doFinish();
        }
        finishSelf();
        return super.onKeyDown(keyCode, event);
    }

}
