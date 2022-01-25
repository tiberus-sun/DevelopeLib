package app.szyl.com.developetools.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.szyl.szyllibrary.utils.TsDeviceUtil;

import app.szyl.com.developetools.R;
import app.szyl.com.developetools.base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class DeviceInfoActivity extends BaseActivity {


    @BindView(R.id.btn_get_phone_info)
    Button btnGetPhoneInfo;
    @BindView(R.id.tv_device_is_phone)
    TextView tvDeviceIsPhone;
    @BindView(R.id.tv_device_software_phone_type)
    TextView tvDeviceSoftwarePhoneType;
    @BindView(R.id.tv_device_density)
    TextView tvDeviceDensity;
    @BindView(R.id.tv_device_manu_facturer)
    TextView tvDeviceManuFacturer;
    @BindView(R.id.tv_device_width)
    TextView tvDeviceWidth;
    @BindView(R.id.tv_device_height)
    TextView tvDeviceHeight;
    @BindView(R.id.tv_device_version_name)
    TextView tvDeviceVersionName;
    @BindView(R.id.tv_device_version_code)
    TextView tvDeviceVersionCode;
    @BindView(R.id.tv_device_imei)
    TextView tvDeviceImei;
    @BindView(R.id.tv_device_imsi)
    TextView tvDeviceImsi;
    @BindView(R.id.tv_device_software_version)
    TextView tvDeviceSoftwareVersion;
    @BindView(R.id.tv_device_mac)
    TextView tvDeviceMac;
    @BindView(R.id.tv_device_software_mcc_mnc)
    TextView tvDeviceSoftwareMccMnc;
    @BindView(R.id.tv_device_software_mcc_mnc_name)
    TextView tvDeviceSoftwareMccMncName;
    @BindView(R.id.tv_device_software_sim_country_iso)
    TextView tvDeviceSoftwareSimCountryIso;
    @BindView(R.id.tv_device_sim_operator)
    TextView tvDeviceSimOperator;
    @BindView(R.id.tv_device_sim_serial_number)
    TextView tvDeviceSimSerialNumber;
    @BindView(R.id.tv_device_sim_state)
    TextView tvDeviceSimState;
    @BindView(R.id.tv_device_sim_operator_name)
    TextView tvDeviceSimOperatorName;
    @BindView(R.id.tv_device_subscriber_id)
    TextView tvDeviceSubscriberId;
    @BindView(R.id.tv_device_voice_mail_number)
    TextView tvDeviceVoiceMailNumber;
    @BindView(R.id.tv_device_adnroid_id)
    TextView tvDeviceAdnroidId;
    @BindView(R.id.tv_device_build_brand_model)
    TextView tvDeviceBuildBrandModel;
    @BindView(R.id.tv_device_build_manu_facturer)
    TextView tvDeviceBuildManuFacturer;
    @BindView(R.id.tv_device_build_brand)
    TextView tvDeviceBuildBrand;
    @BindView(R.id.tv_device_serial_number)
    TextView tvDeviceSerialNumber;
    @BindView(R.id.tv_device_iso)
    TextView tvDeviceIso;
    @BindView(R.id.tv_device_phone)
    TextView tvDevicePhone;
    @BindView(R.id.ll_info_root)
    LinearLayout llInfoRoot;

    @Override
    protected void setMyContentView() {
        setContentView(R.layout.activity_device_info);
    }

    @Override
    protected void initViewBehind() {
        initData();
        initView();

        initPermission();
    }

    private void initData() {

    }

    private void initView() {

    }

    private void initPermission() {
        //请求读取手机设备 权限
        if (ContextCompat.checkSelfPermission(DeviceInfoActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(DeviceInfoActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }
    }

    @OnClick(R.id.btn_get_phone_info)
    public void onViewClicked() {

        llInfoRoot.setVisibility(View.VISIBLE);
        getPhoneInfo();
        btnGetPhoneInfo.setVisibility(View.GONE);
    }



    private void getPhoneInfo() {
        if (TsDeviceUtil.isPhone(mContext)) {
            tvDeviceIsPhone.setText("是");
        } else {
            tvDeviceIsPhone.setText("否");
        }

        tvDeviceSoftwarePhoneType.setText(TsDeviceUtil.getPhoneType(mContext) + "");
        tvDeviceDensity.setText(TsDeviceUtil.getScreenDensity(mContext) + "");
        tvDeviceManuFacturer.setText(TsDeviceUtil.getUniqueSerialNumber() + "");
        tvDeviceWidth.setText(TsDeviceUtil.getScreenWidth(mContext) + " ");
        tvDeviceHeight.setText(TsDeviceUtil.getScreenHeight(mContext) + " ");
        tvDeviceVersionName.setText(TsDeviceUtil.getAppVersionName(mContext) + "");
        tvDeviceVersionCode.setText(TsDeviceUtil.getAppVersionNo(mContext) + "");
        tvDeviceImei.setText(TsDeviceUtil.getDeviceIdIMEI(mContext) + "");
        tvDeviceImsi.setText(TsDeviceUtil.getIMSI(mContext) + "");
        tvDeviceSoftwareVersion.setText(TsDeviceUtil.getDeviceSoftwareVersion(mContext) + "");
        tvDeviceMac.setText(TsDeviceUtil.getMacAddress(mContext));
        tvDeviceSoftwareMccMnc.setText(TsDeviceUtil.getNetworkOperator(mContext) + "");
        tvDeviceSoftwareMccMncName.setText(TsDeviceUtil.getNetworkOperatorName(mContext) + "");
        tvDeviceSoftwareSimCountryIso.setText(TsDeviceUtil.getNetworkCountryIso(mContext) + "");
        tvDeviceSimOperator.setText(TsDeviceUtil.getSimOperator(mContext) + "");
        tvDeviceSimSerialNumber.setText(TsDeviceUtil.getSimSerialNumber(mContext) + "");
        tvDeviceSimState.setText(TsDeviceUtil.getSimState(mContext) + "");
        tvDeviceSimOperatorName.setText(TsDeviceUtil.getSimOperatorName(mContext) + "");
        tvDeviceSubscriberId.setText(TsDeviceUtil.getSubscriberId(mContext) + "");
        tvDeviceVoiceMailNumber.setText(TsDeviceUtil.getVoiceMailNumber(mContext) + "");
        tvDeviceAdnroidId.setText(TsDeviceUtil.getAndroidId(mContext) + "");
        tvDeviceBuildBrandModel.setText(TsDeviceUtil.getBuildBrandModel() + "");
        tvDeviceBuildManuFacturer.setText(TsDeviceUtil.getBuildMANUFACTURER() + "");
        tvDeviceBuildBrand.setText(TsDeviceUtil.getBuildBrand() + "");
        tvDeviceSerialNumber.setText(TsDeviceUtil.getSerialNumber() + "");
        tvDeviceIso.setText(TsDeviceUtil.getNetworkCountryIso(mContext) + "");
        tvDevicePhone.setText(TsDeviceUtil.getLine1Number(mContext) + "");
    }
}
