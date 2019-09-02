package app.szyl.com.developetools.activity;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import app.szyl.com.developetools.R;
import app.szyl.com.developetools.base.BaseActivity;
import app.szyl.com.developetools.util.ToolUtils;
import butterknife.BindView;
import butterknife.OnClick;

public class PickerOptionActivity extends BaseActivity {

    @BindView(R.id.option_time)
    TextView option_time;
    @BindView(R.id.option_data)
    TextView option_data;

    @Override
    protected void setMyContentView() {
        setContentView(R.layout.activity_picker_option);
    }

    @Override
    protected void initViewBehind() {
        initData();
        initView();
    }

    private void initData() {

    }

    private void initView() {

    }

    @OnClick({R.id.option_data, R.id.option_time})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.option_time:

                ToolUtils.popTimePicker(option_time, this);
                break;
            case R.id.option_data:

                ArrayList options = new ArrayList();
                for (int i = 0; i <= 5; i++) {
                    options.add("数据" + i);
                }

                ToolUtils.popOptionPicker(option_data, options, this, "选择数据");
                break;

        }
    }


}
