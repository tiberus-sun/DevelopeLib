package app.szyl.com.developetools.activity;

import android.content.Intent;
import android.view.View;

import com.szyl.szyllibrary.tools.pickimage.ImageGridView;
import com.szyl.szyllibrary.tools.pickimage.MultiImageSelectorActivity;
import com.szyl.szyllibrary.tools.pickimage.adapter.PubSelectedImgsAdapter;

import java.util.ArrayList;

import app.szyl.com.developetools.R;
import app.szyl.com.developetools.base.BaseActivity;
import butterknife.BindView;

public class PickerImageActivity extends BaseActivity {

    private static final int REQUEST_IMAGE = 2;
    @BindView(R.id.show_gridView)
    ImageGridView showGridView;
    @BindView(R.id.add_gridView)
    ImageGridView addGridView;
    @BindView(R.id.showadd_gridView)
    ImageGridView showaddGridView;

    private PubSelectedImgsAdapter showImgsAdapter;

    private PubSelectedImgsAdapter addImgsAdapter;

    private PubSelectedImgsAdapter showaddImgsAdapter;

    //一部分返回的 一部分新增的
    private ArrayList<String> showaddAll = new ArrayList<>();

    private ArrayList<String> mNewPaths;

    //只增模式
    private ArrayList<String> mAddPaths;

     @Override
    protected void setMyContentView() {
        setContentView(R.layout.activity_picker_image);
    }

    @Override
    protected void initViewBehind() {

        setData();
    }

    private void setData() {
        showGridView.setActivity(PickerImageActivity.this);
        addGridView.setActivity(PickerImageActivity.this);
        showaddGridView.setActivity(PickerImageActivity.this);

        //展示
        ArrayList<String> koufens = new ArrayList<String>();
        koufens.add("http://i5.hexunimg.cn/2014-05-29/165234568.jpg");
        koufens.add("http://imgsrc.baidu.com/imgad/pic/item/d439b6003af33a87c7dd1084cc5c10385243b5ff.jpg");
        showImgsAdapter = new PubSelectedImgsAdapter("show", this, koufens);
        showGridView.setAdapter(showImgsAdapter);
        showGridView.setSelectPath("show", koufens, koufens);

        //新增+展示
        showaddAll = new ArrayList<String>();
        showaddAll.add("http://i5.hexunimg.cn/2014-05-29/165234568.jpg");
        showaddAll.add("http://imgsrc.baidu.com/imgad/pic/item/d439b6003af33a87c7dd1084cc5c10385243b5ff.jpg");

        showaddImgsAdapter = new PubSelectedImgsAdapter("showadd", this, showaddAll,
                new PubSelectedImgsAdapter.OnItemClickClass() {
                    @Override
                    public void OnItemClick(View v, String filepath) {
                        mNewPaths.remove(filepath);
                        showaddAll.remove(filepath);
                        showaddImgsAdapter.notifyDataSetChanged();
                    }
                });
        showaddGridView.setAdapter(showaddImgsAdapter);
        showaddGridView.setSelectPath("showadd", mNewPaths, showaddAll);


        //新增
        mAddPaths=new ArrayList<String>();
        addImgsAdapter = new PubSelectedImgsAdapter("add",PickerImageActivity.this, mAddPaths,
                new PubSelectedImgsAdapter.OnItemClickClass() {
                    @Override
                    public void OnItemClick(View v, String filepath) {
                        mAddPaths.remove(filepath);
                        addImgsAdapter.notifyDataSetChanged();
                    }
                });
        addGridView.setAdapter(addImgsAdapter);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {

                String module= data.getStringExtra("module");
                if ("add".equals(module)) {
                    //图片本身路径
                    ArrayList<String> mnewAddPaths = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    mAddPaths.clear();
                    mAddPaths.addAll(mnewAddPaths);
                    addImgsAdapter.notifyDataSetChanged();
                    addGridView.setSelectPath("add", mAddPaths, mAddPaths);

                } else if ("showadd".equals(module)) {
                    //图片本身路径
                    mNewPaths = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    //防止重复追加
                    for (int i = 0; i < mNewPaths.size(); i++) {
                        if (showaddAll.contains(mNewPaths.get(i))) {

                        } else {
                            showaddAll.add(mNewPaths.get(i));
                        }
                    }
                    showaddImgsAdapter.notifyDataSetChanged();
                    showaddGridView.setSelectPath("showadd", mNewPaths, showaddAll);
                }
            }
        }
    }

}
