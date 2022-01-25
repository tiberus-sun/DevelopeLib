package app.szyl.com.developetools.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog pd;

    public BaseActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext=this;

        setMyContentView();
        ButterKnife.bind(this);

        initBaseView();
        initViewBehind();
    }

    protected abstract void setMyContentView();


    protected abstract void initViewBehind();


    private void initBaseView() {

        pd = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        //pd.setTitle("请稍候");
        pd.setMessage("正在连接服务器......");
        pd.setCancelable(false);// 设置是否可以通过点击Back键取消
        pd.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
    }


    /**
     * 是否显示后退按钮,默认显示,可在子类重写该方法.
     *
     * @return
     */
    protected boolean isShowBacking() {
        return true;
    }

    /**
     * 展示progress
     */

    public void showProgress() {
        if (pd != null) {
            pd.show();
        }
    }

    public void dismissProgress() {
        if (null != pd && pd.isShowing()) {
            pd.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
