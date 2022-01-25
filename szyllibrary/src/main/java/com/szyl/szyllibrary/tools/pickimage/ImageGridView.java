package com.szyl.szyllibrary.tools.pickimage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.szyl.szyllibrary.view.picdialog.PicShowDialog;

import java.util.ArrayList;

/**
 * 实现图片上传界面用到
 */
public class ImageGridView extends GridView implements AdapterView.OnItemClickListener {

    private Activity myactivity;
    public static final int REQUEST_IMAGE = 2;
    private String module = "add";

    private ArrayList<String> mNewPath;
    private ArrayList<String> mAllPath;

    private OnItemClick mOnItemClick;

    //权限是否ok
    private boolean permissionOk;

    public ImageGridView(Context context) {
        super(context);
    }

    public ImageGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void init() {
        setSelector(new ColorDrawable(Color.TRANSPARENT));
        setOnItemClickListener(this);
    }


    public void setActivity(Activity activity) {
        this.myactivity = activity;
        init();
    }

    public void setSelectPath(String module, ArrayList<String> newPath, ArrayList<String> allPath) {
        this.module = module;
        this.mNewPath = newPath;
        this.mAllPath = allPath;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (module.equals("show")) {
            PicShowDialog dialog = new PicShowDialog(myactivity, mAllPath, position);
            dialog.show();
        } else {

            if (position == getDataSize()) {

                if (permissionOk) {
                    startImageselect();
                } else {
                    if (mOnItemClick != null) {
                        mOnItemClick.OnAddClick(false);
                    }
                }
            } else {
                PicShowDialog dialog = new PicShowDialog(myactivity, mAllPath, position);
                dialog.show();
            }
        }
    }

    private void startImageselect() {
        Intent intent = new Intent(myactivity, MultiImageSelectorActivity.class);
        //解决在同一个界面中 既有add 又有showadd 选择相册后 无法判单在哪里显示
        intent.putExtra("module", module);

        // 是否显示拍摄图片
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大可选择图片数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 3);
        // 选择模式
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
        // 默认选择
        if (mNewPath != null && mNewPath.size() > 0) {
            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mNewPath);
        }

        myactivity.startActivityForResult(intent, REQUEST_IMAGE);
    }


    private int getDataSize() {
        return mAllPath == null ? 0 : mAllPath.size();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


    public interface OnItemClick {
        public void OnAddClick(boolean isOk);
    }

    public OnItemClick getmOnItemClick() {
        return mOnItemClick;
    }

    public void setmOnItemClick(OnItemClick mOnItemClick) {
        this.mOnItemClick = mOnItemClick;
    }

    public void setPermissionOk(boolean permissionOk) {
        this.permissionOk = permissionOk;
        int positon = getDataSize();
        //有权限后 弹出选择图片界面
        performItemClick(getChildAt(positon), positon, getChildAt(positon).getId());
    }
}
