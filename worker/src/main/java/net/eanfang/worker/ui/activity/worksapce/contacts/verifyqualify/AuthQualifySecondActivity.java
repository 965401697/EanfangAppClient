package net.eanfang.worker.ui.activity.worksapce.contacts.verifyqualify;

import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.eanfang.config.Config;
import com.eanfang.model.GrantChange;
import com.eanfang.model.SystypeBean;
import com.eanfang.ui.base.BaseActivity;
import com.yaf.sys.entity.BaseDataEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.GroupAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/26  15:21
 * @email houzhongzhou@yeah.net
 * @desc 选择业务类型
 */

public class AuthQualifySecondActivity extends BaseActivity {


    @BindView(R.id.elv_area)
    ExpandableListView elvArea;

    @BindView(R.id.tv_confim)
    TextView tvConfim;

    List<BaseDataEntity> areaListBean = Config.get().getRegionList(1);
    private GroupAdapter mAdapter;

    private List<Integer> checkListId;
    private List<Integer> unCheckListId;
    private SystypeBean byNetGrant;
    private GrantChange grantChange = new GrantChange();
    private HashSet<Integer> selDataId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_qualify_second);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        setTitle("技能资质");
        setLeftBack();
    }

    private void initListener() {

    }
    private void doLoadArea() {
        new Thread(() -> {
            //获得全部 地区数据
            List<BaseDataEntity> allAreaList = new ArrayList<>(Config.get().getRegionList());
            for (int i = 0; i < areaListBean.size(); i++) {
                BaseDataEntity provinceEntity = areaListBean.get(i);
                //处理当前省下的所有市
                List<BaseDataEntity> cityList = Stream.of(allAreaList).filter(bean -> bean.getParentId() != null && bean.getParentId().intValue() == provinceEntity.getDataId()).toList();
                //查询出来后，移除，以增加效率
                allAreaList.removeAll(cityList);
                for (int j = 0; j < cityList.size(); j++) {
                    BaseDataEntity cityEntity = cityList.get(j);
                    //处理当前市下所有区县
                    List<BaseDataEntity> countyList = Stream.of(allAreaList).filter(bean -> bean.getParentId() != null && bean.getParentId().intValue() == cityEntity.getDataId()).toList();
                    //查询出来后，移除，以增加效率
                    allAreaList.removeAll(countyList);
                    cityList.get(j).setChildren(countyList);
                }
                areaListBean.get(i).setChildren(cityList);
            }
        }).start();
    }

}
