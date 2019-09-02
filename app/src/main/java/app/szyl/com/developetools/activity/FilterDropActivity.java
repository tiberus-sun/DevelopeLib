package app.szyl.com.developetools.activity;

import android.widget.TextView;

import com.baiiu.filter.view.scroll.DropDownScrollMenu;
import com.baiiu.filter.interfaces.OnFilterDoneListener;

import app.szyl.com.developetools.R;
import app.szyl.com.developetools.adapter.MyFilterDropMenuAdapter;
import app.szyl.com.developetools.base.BaseActivity;
import butterknife.BindView;

public class FilterDropActivity extends BaseActivity implements OnFilterDoneListener{

    //筛选
    @BindView(R.id.dropDownMenu)
    DropDownScrollMenu dropDownMenu;

    @BindView(R.id.mFilterContentView)
    TextView mFilterContentView;

    MyFilterDropMenuAdapter myDropMenuAdapter;
    @Override
    protected void setMyContentView() {
        setContentView(R.layout.activity_filter_drop);
    }

    @Override
    protected void initViewBehind() {
        initData();
        initView();
    }

    private void initData() {

    }

    private void initView() {
        //筛选初始化
        initFilterDropDownView();
    }

    //筛选条件初始化
    private void initFilterDropDownView() {
        String[] titleList = new String[] { "两级", "时间","单选","单选1","单选2" };
        myDropMenuAdapter=new MyFilterDropMenuAdapter(this, titleList, this);
        dropDownMenu.setMenuAdapter(myDropMenuAdapter);
    }

    @Override
    public void onFilterDone(int position, String positionTitle, String urlValue) {

        //1 为时间 4 更多
        if(position!=1 && position!= 4 && position!= 5 ){
            dropDownMenu.setPositionIndicatorText(position, positionTitle);
        }
        dropDownMenu.close();

        //显示搜索条件
        mFilterContentView.setText(myDropMenuAdapter.getFilterData().toString());
    }
}
