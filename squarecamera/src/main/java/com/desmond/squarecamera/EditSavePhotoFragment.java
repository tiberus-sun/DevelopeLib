package com.desmond.squarecamera;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 *
 */
public class EditSavePhotoFragment extends Fragment {

    public static final String TAG = EditSavePhotoFragment.class.getSimpleName();
    public static final String BITMAP_KEY = "bitmap_byte_array";
    public static final String ROTATION_KEY = "rotation";
    public static final String IMAGE_INFO = "image_info";

    private static final int REQUEST_STORAGE = 1;

    public static Fragment newInstance(byte[] bitmapByteArray, int rotation,
                                       @NonNull ImageParameters parameters) {
        Fragment fragment = new EditSavePhotoFragment();

        Bundle args = new Bundle();
        args.putByteArray(BITMAP_KEY, bitmapByteArray);
        args.putInt(ROTATION_KEY, rotation);
        args.putParcelable(IMAGE_INFO, parameters);

        fragment.setArguments(args);
        return fragment;
    }

    public EditSavePhotoFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.squarecamera__fragment_edit_save_photo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int rotation = getArguments().getInt(ROTATION_KEY);
        byte[] data = getArguments().getByteArray(BITMAP_KEY);
        ImageParameters imageParameters = getArguments().getParcelable(IMAGE_INFO);

        if (imageParameters == null) {
            return;
        }

        final ImageView photoImageView = (ImageView) view.findViewById(R.id.photo);

        imageParameters.mIsPortrait =
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        final View topView = view.findViewById(R.id.topView);
        if (imageParameters.mIsPortrait) {
            topView.getLayoutParams().height = imageParameters.mCoverHeight;
        } else {
            topView.getLayoutParams().width = imageParameters.mCoverWidth;
        }

        rotatePicture(rotation, data, photoImageView);

        view.findViewById(R.id.save_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePicture();
            }
        });
    }

    //拍完照片 特殊处理 将一张拍好的 图片经过质量压缩 生成一张1080 * 810的照片 进行旋转 从中间截取800*600的图片
    //有些手机 压缩后可能生成是 720*540 图片
    private void rotatePicture(int rotation, byte[] data, ImageView photoImageView) {

        //压缩图片
        Bitmap bitmap = ImageUtility.decodeSampledBitmapFromByte(getActivity(), data);

        /*saveCroppedImage(bitmap);
        int with1= bitmap.getWidth();
        int height2= bitmap.getHeight();*/

        //rotation 值为270 旋转了270 前置摄像头拍完照片 就是斜着的 需要旋转角度
        if (rotation != 0) {
            Bitmap oldBitmap = bitmap;

            Matrix matrix = new Matrix();
            matrix.postRotate(rotation);  //旋转角度

            int xTopLeft = (bitmap.getWidth() - 600) / 2 ;
            int yTopLeft = (bitmap.getHeight() - 800) / 2;

            //有些手机 压缩后可能生成是 720*540 图片 以免报错
            if(xTopLeft<0 || yTopLeft<0) {
                //生成一张 原图大小的图片
                bitmap = Bitmap.createBitmap(oldBitmap, 0, 0, oldBitmap.getWidth(), oldBitmap.getHeight(),  matrix, false );
            }else{
                //生成一张 长宽为800*600的图片
                bitmap = Bitmap.createBitmap(oldBitmap, xTopLeft, yTopLeft, 600, 800, matrix, false );
            }
            //生成固定 800*600 大小的
            //bitmap = Bitmap.createScaledBitmap(bitmap, 800, 600, true);
            oldBitmap.recycle();
        }

        photoImageView.setImageBitmap(bitmap);
    }

    public static  void saveCroppedImage(Bitmap bmp) {
        String dirName = Environment.getExternalStorageDirectory().getAbsolutePath() ;

        File file = new File(dirName+"/Pictures");
        if (!file.exists())
            file.mkdir();
        long time= System.currentTimeMillis();

        file = new File(dirName+"/"+time+".jpg".trim());
        String fileName = file.getName();
        String mName = fileName.substring(0, fileName.lastIndexOf("."));
        String sName = fileName.substring(fileName.lastIndexOf("."));

        String newFilePath = dirName+"/Pictures" + "/" + mName + sName;
        file = new File(newFilePath);
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //展示 保存图片
    private void savePicture() {
       // requestForPermission();
        final View view = getView();
        if ( view != null) {
            ImageView photoImageView = (ImageView) view.findViewById(R.id.photo);

            Bitmap bitmap = ((BitmapDrawable) photoImageView.getDrawable()).getBitmap();

            Uri photoUri = ImageUtility.savePicture(getActivity(), bitmap);

            ((CameraActivity) getActivity()).returnPhotoUri(photoUri);
        }
    }

    private void requestForPermission() {
        RuntimePermissionActivity.startActivity(EditSavePhotoFragment.this,
                REQUEST_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK != resultCode) return;

        if (REQUEST_STORAGE == requestCode && data != null) {
            //final boolean isGranted = data.getBooleanExtra(RuntimePermissionActivity.REQUESTED_PERMISSION, false);
            final View view = getView();
            if ( view != null) {
                ImageView photoImageView = (ImageView) view.findViewById(R.id.photo);

                Bitmap bitmap = ((BitmapDrawable) photoImageView.getDrawable()).getBitmap();
                Uri photoUri = ImageUtility.savePicture(getActivity(), bitmap);

                ((CameraActivity) getActivity()).returnPhotoUri(photoUri);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
