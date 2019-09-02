package app.szyl.com.developetools.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.szyl.szyllibrary.utils.TsPictureUtil;

import app.szyl.com.developetools.R;
import app.szyl.com.developetools.base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class CameraActivity extends BaseActivity {

    @BindView(R.id.photo_tv)
    TextView photoTv;
    @BindView(R.id.show_iv)
    ImageView showIv;
    @BindView(R.id.cancel_iv)
    ImageView cancelIv;

    // 请求加载系统照相机
    private static final int REQUEST_CAMERA = 100;

    private String urlPath;

    @Override
    protected void setMyContentView() {
        setContentView(R.layout.activity_camera);
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


    @OnClick({R.id.photo_tv, R.id.cancel_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.photo_tv:
                // 跳转到系统照相机 1
				/*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra("android.intent.extras.CAMERA_FACING_FRONT", 1); // 调用前置摄像头  Camera.CameraInfo.CAMERA_FACING_FRONT
				intent.putExtra("camerasensortype", 2); // 调用前置摄像头
				startActivityForResult(intent, REQUEST_CAMERA);*/
                //法2
				/*mTmpFile = FileUtils.createTmpFile(getActivity());
				if(intent.resolveActivity(getActivity().getPackageManager()) != null){
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
					startActivityForResult(intent, REQUEST_CAMERA);
				}*/

                //自定义相机
                Intent startCustomCameraIntent = new Intent(CameraActivity.this, com.desmond.squarecamera.CameraActivity.class);
                startActivityForResult(startCustomCameraIntent, REQUEST_CAMERA);

                break;
            case R.id.cancel_iv:

                showIv.setVisibility(View.GONE);
                cancelIv.setVisibility(View.GONE);
                photoTv.setVisibility(View.VISIBLE);

                //删除原图片
                TsPictureUtil.deleteTempFile(urlPath);
                urlPath = null;
                break;

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 相机拍照完成后，返回图片路径
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                // 获取相机返回的数据，并转换为图片格式 法1
				/*Bitmap bitmap = (Bitmap) data.getExtras().get("data");
				bitmap= ToolUtils.drawMoreTextToBitmap(mContext,bitmap,placeTv.getText().toString(),0,0); //

				showIv.setImageBitmap(bitmap);
				showIv.setVisibility(View.VISIBLE);
				cancelIv.setVisibility(View.VISIBLE);
				submitBt.setVisibility(View.VISIBLE);
				photoTv.setVisibility(View.GONE);*/

                //法2
				/*if (mTmpFile != null) {
					String path=mTmpFile.getAbsolutePath();

					Bitmap bitmap =BitmapFactory.decodeFile(path);
					bitmap=ToolUtils.scaleImg(bitmap,800,600);
					ToolUtils.saveCroppedImage(bitmap);
					//bitmap= ToolUtils.drawTextToBitmap(mContext,bitmap,"国际科技园",ScreenUtils.getScreenWidth(mContext));
					show_iv.setImageBitmap(bitmap);
					show_iv.setVisibility(View.VISIBLE);
					photoRl.setVisibility(View.GONE);
				}*/

                urlPath = data.getData().getPath();
                Bitmap bitmap = BitmapFactory.decodeFile(urlPath);

                showIv.setImageBitmap(bitmap);
                showIv.setVisibility(View.VISIBLE);
                cancelIv.setVisibility(View.VISIBLE);
                photoTv.setVisibility(View.GONE);

            }
        }
    }
}
