package net.eanfang.worker.ui.activity.worksapce.security;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.security.SecurityCommentBean;
import com.eanfang.model.security.SecurityCommentDetailBean;
import com.eanfang.model.security.SecurityDetailBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.StringUtils;
import com.eanfang.util.V;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.security.SecurityCommentAdapter;
import net.eanfang.worker.ui.adapter.security.SecurityCommentSecondAdapter;
import net.eanfang.worker.ui.widget.DividerItemDecoration;
import net.eanfang.worker.ui.widget.GeneralSDialog;


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

public class SecurityCommentDetailActivity extends BaseActivity {

    @BindView(R.id.iv_seucrity_header)
    SimpleDraweeView ivSeucrityHeader;
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
    private int mCommentId;
    private Long mAsId;
    private SecurityDetailBean.ListBean mCommentBean;
    private SecurityCommentSecondAdapter securityCommentAdapter;
    private List<SecurityCommentDetailBean.CommentsEntityListBean> commentList = new ArrayList<>();
    /**
     * 点击哪个评论进行回复
     */
    private int mParentCommentId = 0;
    private GeneralSDialog generalDialog;
    /**
     * 当前页面是否进行 评论删除操作
     */
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_comment_detail);
        ButterKnife.bind(this);
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
        rvComments.setNestedScrollingEnabled(false);
        mCommentId = getIntent().getIntExtra("mCommentId", 0);
        mAsId = getIntent().getLongExtra("mAsId", 0);
        mCommentBean = (SecurityDetailBean.ListBean) getIntent().getSerializableExtra("mComment");
    }

    private void initData() {
        // 头像
        ivSeucrityHeader.setImageURI((Uri.parse(BuildConfig.OSS_SERVER + V.v(() -> mCommentBean.getCommentUser().getAccountEntity().getAvatar()))));
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
                    doGetCommentList();
                }));
    }

    private void doGetCommentList() {
        EanfangHttp.post(NewApiService.SERCURITY_COMMENT_DETAIL)
                .params("id", mCommentId)
                .execute(new EanfangCallback<SecurityCommentDetailBean>(this, true, SecurityCommentDetailBean.class, bean -> {
                    if (commentList != null && commentList.size() > 0) {
                        commentList.clear();
                    }
                    if (bean.getCommentsEntityList() != null && bean.getCommentsEntityList().size() > 0) {
                        tvCommentCount.setText(bean.getCommentsEntityList().size() + "");
                    }
                    commentList = bean.getCommentsEntityList();
                    securityCommentAdapter.getData().clear();
                    securityCommentAdapter.setNewData(commentList);
                }));
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
        View view = getWindow().getDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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
        securityCommentBean.setCommentsAnswerId(EanfangApplication.get().getUserId());
        securityCommentBean.setCommentsAnswerAccId(EanfangApplication.get().getUser().getAccount().getAccId());
        securityCommentBean.setCommentsCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyId());
        securityCommentBean.setCommentsTopCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getTopCompanyId());
        securityCommentBean.setTopCommentsId(mCommentId);
        if (mParentCommentId != 0) {
            securityCommentBean.setParentCommentsId(mParentCommentId);
        } else {
            securityCommentBean.setParentCommentsId(mCommentId);
        }
        EanfangHttp.post(NewApiService.SERCURITY_COMMENT)
                .upJson(com.alibaba.fastjson.JSONObject.toJSONString(securityCommentBean))
                .execute(new EanfangCallback<org.json.JSONObject>(this, true, JSONObject.class, bean -> {
                    mParentCommentId = 0;
                    isEdit = true;
                    doGetCommentList();
                    hideKeyboard();
                }));

    }

    private void doFinish() {
        Intent intent = new Intent();
        intent.putExtra("isEdit", true);
        setResult(RESULT_OK, intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && isEdit) {
            doFinish();
        }
        finishSelf();
        return super.onKeyDown(keyCode, event);
    }

}
