package com.szyl.szyllibrary.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.szyl.szyllibrary.utils.TsNetworkUtil;

/**
 * 网络监听receiver
 */
public class NetworkStateReceiver extends BroadcastReceiver {

    private NetChangeListener mNetChangeListener;

    public NetworkStateReceiver(){

    }
    public NetworkStateReceiver(NetChangeListener netChangeListener){
        this.mNetChangeListener=netChangeListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int netWorkState = TsNetworkUtil.getNetWorkSimpleType(context);
            // 当网络发生变化，判断当前网络状态，并通过NetEvent回调当前网络状态
            if (mNetChangeListener != null) {
                mNetChangeListener.onChangeListener(netWorkState);
            }
        }
    }

   public interface NetChangeListener{
       void onChangeListener(int status);
   }
}
