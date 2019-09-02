package app.szyl.com.developetools.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.szyl.szyllibrary.utils.TsPictureUtil;

import app.szyl.com.developetools.R;
import app.szyl.com.developetools.base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class WarterMarkActivity extends BaseActivity {


    @BindView(R.id.show_iv)
    ImageView picter;

    @BindView(R.id.tv_wenzi)
    TextView tv_wenzi;
    @BindView(R.id.tv_picter)
    TextView tv_picter;

    @Override
    protected void setMyContentView() {
        setContentView(R.layout.activity_watermark);
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


    @OnClick({R.id.tv_wenzi, R.id.tv_picter})
    public void onViewClicked(View view) {

        Bitmap srcbitmap;
        switch (view.getId()) {
            case R.id.tv_wenzi:

                srcbitmap = ((BitmapDrawable) picter.getDrawable()).getBitmap();

                srcbitmap = TsPictureUtil.drawMoreTextToBitmap(WarterMarkActivity.this, srcbitmap, "苏州云联智慧信息技术应用有限公司".toString(), 0, 0);

                picter.setImageBitmap(srcbitmap);

                tv_wenzi.setEnabled(false);


                break;
            case R.id.tv_picter:
                srcbitmap = ((BitmapDrawable) picter.getDrawable()).getBitmap();

                Bitmap watermark = ((BitmapDrawable) getResources().getDrawable(R.mipmap.circle_captcha)).getBitmap();

                srcbitmap = TsPictureUtil.drawBitmapToBitmap(srcbitmap, watermark, 0);

                picter.setImageBitmap(srcbitmap);

                tv_picter.setEnabled(false);

                break;

        }
    }


}
