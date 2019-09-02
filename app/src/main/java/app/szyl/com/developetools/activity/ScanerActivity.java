package app.szyl.com.developetools.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.szyl.szyllibrary.tools.zxscaner.activity.ActivityScanerCode;
import com.szyl.szyllibrary.tools.zxscaner.tool.RxBarCode;
import com.szyl.szyllibrary.tools.zxscaner.tool.RxQRCode;
import com.szyl.szyllibrary.view.TsToastView;

import app.szyl.com.developetools.R;
import app.szyl.com.developetools.base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScanerActivity extends BaseActivity {


    @BindView(R.id.content_et)
    EditText contentEt;
    @BindView(R.id.scaner_bt)
    Button scanerBt;
    @BindView(R.id.qrcode_bt)
    Button qrcodeBt;
    @BindView(R.id.linecode_bt)
    Button linecodeBt;
    @BindView(R.id.iv_bar_code)
    ImageView ivBarCode;
    @BindView(R.id.tv_reset)
    TextView tvReset;

    @Override
    protected void setMyContentView() {
        setContentView(R.layout.activity_scaner);
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

    @OnClick({R.id.scaner_bt, R.id.qrcode_bt, R.id.linecode_bt})
    public void onViewClicked(View view) {

        String str=contentEt.getText().toString();
        switch (view.getId()) {
            case R.id.scaner_bt:

                Intent intent = new Intent(ScanerActivity.this, ActivityScanerCode.class);
                startActivityForResult(intent, 5002);

                break;
            case R.id.qrcode_bt:

                if(TextUtils.isEmpty(str)){

                    TsToastView.info(ScanerActivity.this,"请输入内容").show();
                }else{

                    //二维码生成方式一  推荐此方法
                    RxQRCode.builder(str).
                            backColor(0xFFFFFFFF).
                            codeColor(0xFF000000).
                            codeSide(600).
                            into(ivBarCode);

                    //二维码生成方式二 默认宽和高都为800 背景为白色 二维码为黑色
                    // RxQRCode.createQRCode(str,mIvQrCode);

                    ivBarCode.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.linecode_bt:

                if(TextUtils.isEmpty(str)){
                    TsToastView.normal(ScanerActivity.this,"请输入内容").show();
                }else{

                    //条形码生成方式一  推荐此方法
                    RxBarCode.builder(str).
                            backColor(0x00000000).
                            codeColor(0xFF000000).
                            codeWidth(1000).
                            codeHeight(300).
                            into(ivBarCode);

                    //条形码生成方式二  默认宽为1000 高为300 背景为白色 二维码为黑色
                    //mIvBarCode.setImageBitmap(RxBarCode.createBarCode(str1, 1000, 300));
                    ivBarCode.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 5002) {

                Bundle bundle = data.getExtras();
                String scaner_result = bundle.getString("scaner_result");
                tvReset.setText("扫码结果："+scaner_result);
            }
        }
    }
}
