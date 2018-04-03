package net.eanfang.client.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.config.FastjsonConfig;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.BaseDataBean;
import com.eanfang.model.ConstAllBean;
import com.eanfang.model.LoginBean;
import com.eanfang.util.ExecuteUtils;
import com.eanfang.util.OkGoUpdateHttpUtil;
import com.eanfang.util.StringUtils;
import com.eanfang.util.Var;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.ui.fragment.ContactListFragment;
import net.eanfang.client.ui.fragment.ContactsFragment;
import net.eanfang.client.ui.fragment.HomeFragment;
import net.eanfang.client.ui.fragment.MyFragment;
import net.eanfang.client.ui.fragment.WorkspaceFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class MainActivity extends BaseClientActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    protected FragmentTabHost mTabHost;
    View redPoint;
    private LoginBean user;
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        user = EanfangApplication.get().getUser();
        setHeaders();

        initXinGe();
        initFragment();
        getBaseData();
        getConst();


    }


    private void initFragment() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        View indicator = getLayoutInflater().inflate(R.layout.indicator_main_home, null);
        mTabHost.addTab(mTabHost.newTabSpec("home").setIndicator(indicator), HomeFragment.class, null);

        indicator = getLayoutInflater().inflate(R.layout.indicator_main_contact, null);
        mTabHost.addTab(mTabHost.newTabSpec("contactList").setIndicator(indicator), ContactListFragment.class, null);
        initMessageCount(indicator);

        indicator = getLayoutInflater().inflate(R.layout.indicator_main_work, null);
        mTabHost.addTab(mTabHost.newTabSpec("work").setIndicator(indicator), WorkspaceFragment.class, null);

        indicator = getLayoutInflater().inflate(R.layout.indicator_org_work, null);
        mTabHost.addTab(mTabHost.newTabSpec("contact").setIndicator(indicator), ContactsFragment.class, null);

        indicator = getLayoutInflater().inflate(R.layout.indicator_main_config, null);


        mTabHost.addTab(mTabHost.newTabSpec("config").setIndicator(indicator), MyFragment.class, null);
        redPoint = indicator.findViewById(R.id.redPoint);
    }

    private void initMessageCount(View indicator) {

        Badge qBadgeView = new QBadgeView(this)
                .bindTarget(indicator.findViewById(R.id.tabImg))
                .setBadgeNumber(Var.get("MainActivity.initMessageCount").getVar())
                .setBadgePadding(5, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(-2, -2, true)
                .setBadgeTextSize(10, true)
                .setOnDragStateChangedListener((dragState, badge, targetView) -> {
                    //清除成功
                    if (dragState == Badge.OnDragStateChangedListener.STATE_SUCCEED) {
                        EanfangHttp.get(NewApiService.GET_PUSH_READ_ALL).execute(new EanfangCallback(this, false));
                        showToast("消息被清空了");
//                        Var.get().setVar(0);
                    }
                });
        //变量监听
        Var.get("MainActivity.initMessageCount").setChangeListener((var) -> {
            qBadgeView.setBadgeNumber(var);
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
//        getInfoBytoken();
    }


    /**
     * 点击两次返回键退出
     */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();

                mExitTime = System.currentTimeMillis();
            } else {
                Intent intent = new Intent(getPackageName() + ".ExitListenerReceiver");
                sendBroadcast(intent);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 请求基础数据
     */
    private void getBaseData() {
        String url;
        BaseDataBean dataBean = Config.get().getBaseDataBean();
        if (dataBean == null || StringUtils.isEmpty(dataBean.getMD5())) {
            url = NewApiService.GET_BASE_DATA_CACHE + "0";
        } else {
            url = NewApiService.GET_BASE_DATA_CACHE + dataBean.getMD5();
        }
        EanfangHttp.get(url)
                .tag(this)
                .execute(new EanfangCallback<String>(this, false, String.class, (str) -> {
                    if (!str.contains(Constant.NO_UPDATE)) {
                        BaseDataBean newDate = JSONObject.parseObject(str, BaseDataBean.class);
                        EanfangApplication.get().set(BaseDataBean.class.getName(), JSONObject.toJSONString(newDate, FastjsonConfig.config));
                    }
                }));

    }

    /**
     * 请求静态常量
     */
    private void getConst() {
        String url;
        ConstAllBean constBean = Config.get().getConstBean();
        if (constBean == null || StringUtils.isEmpty(constBean.getMD5())) {
            url = NewApiService.GET_CONST_CACHE + "0";
        } else {
            url = NewApiService.GET_CONST_CACHE + constBean.getMD5();
        }
        EanfangHttp.get(url)
                .tag(this)
                .execute(new EanfangCallback<String>(this, false, String.class, (str) -> {
                    if (!str.contains(Constant.NO_UPDATE)) {
                        ConstAllBean newDate = JSONObject.parseObject(str, ConstAllBean.class);
                        EanfangApplication.get().set(ConstAllBean.class.getName(), JSONObject.toJSONString(newDate, FastjsonConfig.config));
                    }
//                    runOnUiThread(() -> {
//                        PermissionUtils.get(this).getStoragePermission(() -> {
//                            UpdateManager manager = new UpdateManager(this, BuildConfig.TYPE);
//                            manager.checkUpdate();
//                        });
//                    });

                    new UpdateAppManager
                            .Builder()
                            //当前Activity
                            .setActivity(this)
                            //更新地址
                            .setUpdateUrl("https://raw.githubusercontent.com/WVector/AppUpdateDemo/master/json/json.txt?appKey=ab55ce55Ac4bcP408cPb8c1Aaeac179c5f6f&version=0.1.0")
                            //实现httpManager接口的对象
                            .setHttpManager(new OkGoUpdateHttpUtil())
                            .build()
                            .update();


//                    String path = Environment.getExternalStorageDirectory().getAbsolutePath();
//
//                    Map<String, String> params = new HashMap<String, String>();
//
//                    params.put("appKey", "ab55ce55Ac4bcP408cPb8c1Aaeac179c5f6f");
//                    params.put("appVersion", "300000");
//                    params.put("key1", "value2");
//                    params.put("key2", "value3");
//
//                    new UpdateAppManager
//                            .Builder()
//                            //必须设置，当前Activity
//                            .setActivity(this)
//                            //必须设置，实现httpManager接口的对象
//                            .setHttpManager(new OkGoUpdateHttpUtil())
//                            //必须设置，更新地址
//                            .setUpdateUrl("https://raw.githubusercontent.com/WVector/AppUpdateDemo/master/json/json.txt?appKey=ab55ce55Ac4bcP408cPb8c1Aaeac179c5f6f&version=0.1.0")
//
//                            //以下设置，都是可选
//                            //设置请求方式，默认get
//                            .setPost(false)
//                            //添加自定义参数，默认version=1.0.0（app的versionName）；apkKey=唯一表示（在AndroidManifest.xml配置）
//                            .setParams(params)
//                            //设置点击升级后，消失对话框，默认点击升级后，对话框显示下载进度
//                            .hideDialogOnDownloading(false)
//                            //设置头部，不设置显示默认的图片，设置图片后自动识别主色调，然后为按钮，进度条设置颜色
////                            .setTopPic(R.mipmap.top_8)
//                            //为按钮，进度条设置颜色，默认从顶部图片自动识别。
//                            //.setThemeColor(ColorUtil.getRandomColor())
//                            //设置apk下砸路径，默认是在下载到sd卡下/Download/1.0.0/test.apk
//                            .setTargetPath(path)
//                            //设置appKey，默认从AndroidManifest.xml获取，如果，使用自定义参数，则此项无效
//                            //.setAppKey("ab55ce55Ac4bcP408cPb8c1Aaeac179c5f6f")
//                            //不显示通知栏进度条
////                            .dismissNotificationProgress()
//                            //是否忽略版本
//                            //.showIgnoreVersion()
//
//                            .build()
//                            //检测是否有新版本
//                            .checkNewApp(new UpdateCallback() {
//                                /**
//                                 * 解析json,自定义协议
//                                 *
//                                 * @param json 服务器返回的json
//                                 * @return UpdateAppBean
//                                 */
//                                @Override
//                                protected UpdateAppBean parseJson(String json) {
//                                    UpdateAppBean updateAppBean = new UpdateAppBean();
//                                    try {
//                                        JSONObject jsonObject =   JSON.parseObject(json);
//                                        updateAppBean
//                                                //（必须）是否更新Yes,No
//                                                .setUpdate(jsonObject.getString("update"))
//                                                //（必须）新版本号，
//                                                .setNewVersion(jsonObject.getString("new_version"))
//                                                //（必须）下载地址
//                                                .setApkFileUrl(jsonObject.getString("apk_file_url"))
//                                                //（必须）更新内容
//                                                .setUpdateLog(jsonObject.getString("update_log"))
//                                                //大小，不设置不显示大小，可以不设置
//                                                .setTargetSize(jsonObject.getString("target_size"))
//                                                //是否强制更新，可以不设置
//                                                .setConstraint(false)
//                                                //设置md5，可以不设置
//                                                .setNewMd5(jsonObject.getString("new_md51"));
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                    return updateAppBean;
//                                }
//
//                                /**
//                                 * 网络请求之前
//                                 */
//                                @Override
//                                public void onBefore() {
////                                    CProgressDialogUtils.showProgressDialog(JavaActivity.this);
//                                }
//
//                                /**
//                                 * 网路请求之后
//                                 */
//                                @Override
//                                public void onAfter() {
////                                    CProgressDialogUtils.cancelProgressDialog(JavaActivity.this);
//                                }
//
//                                /**
//                                 * 没有新版本
//                                 */
//                                @Override
//                                protected void hasNewApp(UpdateAppBean updateApp, UpdateAppManager updateAppManager) {
//                                    super.hasNewApp(updateApp, updateAppManager);
//                                }
//
//                                @Override
//                                protected void noNewApp(String error) {
//                                    super.noNewApp(error);
//                                }
//                            });

                }));
    }

    public void initXinGe() {
        Var.get("MainActivity.initXinGe").setChangeListener((var) -> {

            ExecuteUtils.execute(
                    () -> Var.get("MainActivity.initXinGe").getVar()
                    , 1, 0,
                    () -> Var.remove("MainActivity.initXinGe")
                    , () -> {
                        registerXinGe();
                    });

        });
        Var.get("MainActivity.initXinGe").setVar(0);
    }


    private void registerXinGe() {
        new Thread(() -> {
            //开启信鸽日志输出
            XGPushConfig.enableDebug(this, false);
            //信鸽注册代码
            XGPushManager.registerPush(this, user.getAccount().getMobile(), new XGIOperateCallback() {
                @Override
                public void onSuccess(Object data, int flag) {
                    Log.d("TPush", "注册成功，设备token为：" + data);
                    Var.get("MainActivity.initXinGe").setVar(1);
                }

                @Override
                public void onFail(Object data, int errCode, String msg) {
                    Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
                    Var.get("MainActivity.initXinGe").setVar(0);
                }
            });
        }).start();

    }

    public void setHeaders() {
        if (EanfangApplication.get().getUser() != null) {
            EanfangHttp.setToken(EanfangApplication.get().getUser().getToken());
        }
        EanfangHttp.setClient();
    }

    /**
     * 首页，工作台，我的，通讯录等未查找控件点击事件
     */
    public void noOpen(View v) {
        showToast("暂缓开通");
    }
}

