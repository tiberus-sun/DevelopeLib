package app.szyl.com.developetools.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.szyl.szyllibrary.utils.TsPhotoTool;
import com.yalantis.ucrop.UCrop;

import app.szyl.com.developetools.R;
import app.szyl.com.developetools.base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CropPhotoActivity extends BaseActivity {


    @BindView(R.id.iv_pick)
    ImageView ivPick;

    private View view;
    private PopupWindow mPopupWindow;
    @Override
    protected void setMyContentView() {
        setContentView(R.layout.activity_crop_photo);
    }

    @Override
    protected void initViewBehind() {
        initData();
        initView();
    }

    private void initData() {

    }

    private void initView() {

        //弹出窗口view
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.popwindow_userimg_picker, null);

        //系统
        mPopupWindow = new PopupWindow(view);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setContentView(view);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        //设置进入出去动画
        mPopupWindow.setAnimationStyle(R.style.popbottom_anim);
        // 添加pop窗口关闭 触发事件
        mPopupWindow.setOnDismissListener(new poponDismissListener());

        view.findViewById(R.id.camera_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TsPhotoTool.openCameraImage(CropPhotoActivity.this);
                closePop();
            }
        });
        view.findViewById(R.id.picker_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TsPhotoTool.openLocalImage(CropPhotoActivity.this);
                closePop();
            }
        });

        view.findViewById(R.id.cancel_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePop();
            }
        });

    }

    @OnClick(R.id.iv_pick)
    public void onViewClicked() {
        if(mPopupWindow!=null && mPopupWindow.isShowing()){
            mPopupWindow.dismiss();
        }else{
            backgroundAlpha(0.6f);
            mPopupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
        }
    }

    //关闭窗口
    private void closePop() {
        if(mPopupWindow!=null && mPopupWindow.isShowing()){
            mPopupWindow.dismiss();
        }
    }

    class poponDismissListener implements PopupWindow.OnDismissListener{
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }

    private void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        this.getWindow().setAttributes(lp);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TsPhotoTool.GET_IMAGE_FROM_PHONE://选择相册之后的处理
                if (resultCode == RESULT_OK) {
                    TsPhotoTool.UCropImage(this,data.getData());
                }

                break;
            case TsPhotoTool.GET_IMAGE_BY_CAMERA://选择照相机之后的处理
                if (resultCode == RESULT_OK) {
                    TsPhotoTool.UCropImage(this,TsPhotoTool.imageUriFromCamera);
                }else{
                    TsPhotoTool.deleteUri(CropPhotoActivity.this,TsPhotoTool.imageUriFromCamera);
                }

                break;
            case TsPhotoTool.CROP_IMAGE://普通裁剪后的处理
                //Glide.with(UserInfoEditActivity.this).load(TsPhotoTool.cropImageUri).centerCrop().into(user_icon);
                break;

            case UCrop.REQUEST_CROP://UCrop裁剪之后的处理

                if (resultCode == RESULT_OK) {
                    Uri resultUri = UCrop.getOutput(data);
                    //注意调用相册时用的是 ACTION_GET_CONTENT还是 ACTION_PICK
                    // 调用getImageAbsolutePath 得到真正图片路径String new File()方法便得到file
                    //todo 后续处理 上传到服务器
                    Glide.with(CropPhotoActivity.this).load(resultUri).centerCrop().into(ivPick);
                } else if (resultCode == UCrop.RESULT_ERROR) {
                    final Throwable cropError = UCrop.getError(data);
                }
                break;
            case UCrop.RESULT_ERROR://UCrop裁剪错误之后的处理
                final Throwable cropError = UCrop.getError(data);
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
