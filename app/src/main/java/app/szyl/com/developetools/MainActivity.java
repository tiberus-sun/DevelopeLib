package app.szyl.com.developetools;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import app.szyl.com.developetools.activity.CameraActivity;
import app.szyl.com.developetools.activity.CropPhotoActivity;
import app.szyl.com.developetools.activity.DeviceInfoActivity;
import app.szyl.com.developetools.activity.FilterDropActivity;
import app.szyl.com.developetools.activity.PickerImageActivity;
import app.szyl.com.developetools.activity.PickerOptionActivity;
import app.szyl.com.developetools.activity.ScanerActivity;
import app.szyl.com.developetools.activity.WarterMarkActivity;
import app.szyl.com.developetools.adapter.AdapterRecyclerViewMain;
import app.szyl.com.developetools.base.BaseActivity;
import app.szyl.com.developetools.model.ModelMainItem;
import app.szyl.com.developetools.util.RxRecyclerViewDividerTool;
import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private List<ModelMainItem> mData;
    private int mColumnCount = 3;

     @Override
    protected void setMyContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initViewBehind() {
        initData();
        initView();
    }

    private void initData() {
        x.view().inject(this);
        x.Ext.init(getApplication());
        x.Ext.setDebug(true);
        mData = new ArrayList<>();
        mData.add(new ModelMainItem("图片选择，兼容只展示，新增，展示和新增3种模式", R.mipmap.circle_elves_ball, PickerImageActivity.class));
        mData.add(new ModelMainItem("条件筛选，两级联动，单选，自定义", R.mipmap.circle_up_down, FilterDropActivity.class));
        mData.add(new ModelMainItem("自定义相机,可默认开启前置摄像头", R.mipmap.circle_picture_location, CameraActivity.class));
        mData.add(new ModelMainItem("增加水印，文字和图片", R.mipmap.circle_text, WarterMarkActivity.class));
        mData.add(new ModelMainItem("底部弹框，选择器", R.mipmap.circle_wrap_text, PickerOptionActivity.class));
        mData.add(new ModelMainItem("二维码/条形码扫码", R.mipmap.circle_rotate, ScanerActivity.class));
        mData.add(new ModelMainItem("替换头像", R.mipmap.circle_dialog, CropPhotoActivity.class));
        mData.add(new ModelMainItem("设备信息", R.mipmap.circle_device_info, DeviceInfoActivity.class));


    }

    private void initView() {
        if (mColumnCount <= 1) {
            recyclerview.setLayoutManager(new LinearLayoutManager(this));
        } else {
            recyclerview.setLayoutManager(new GridLayoutManager(this, mColumnCount));
        }

        recyclerview.addItemDecoration(new RxRecyclerViewDividerTool(10));

        AdapterRecyclerViewMain recyclerViewMain = new AdapterRecyclerViewMain(mData);

        recyclerview.setAdapter(recyclerViewMain);
    }
}
