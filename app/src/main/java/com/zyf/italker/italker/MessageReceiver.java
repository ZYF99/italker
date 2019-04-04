package com.zyf.italker.italker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.igexin.sdk.PushConsts;
import com.zyf.italker.factory.Factory;
import com.zyf.italker.factory.data.helper.AccountHelper;
import com.zyf.italker.factory.persistence.Account;


/**
 * 个推的消息接收器
 *
* */
public class MessageReceiver extends BroadcastReceiver {
    private static final String TAG = MessageReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        Bundle bundle = intent.getExtras();
        //判断当前消息的意图
        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_CLIENTID:
                Log.i(TAG, "GET_CLIENTID :" + bundle.toString());
                //当Id初始化的时候
                //获取设备Id
                onClientInit(bundle.getString("clientid"));
                break;
            case PushConsts.GET_MSG_DATA:
                //常规消息送达
                byte[] payload = bundle.getByteArray("payload");
                if (payload != null) {
                    String message = new String(payload);
                    Log.i(TAG, "GET_MSG_DATA :" + bundle.toString());
                    onMessageArrived(message);
                }
                break;
            default:
                Log.i(TAG, "OTHER :" + bundle.toString());
                break;
        }
    }

    /**
     * 当Id初始化的时候
     *
     * @param cid 设备Id
     */
    private void onClientInit(String cid) {
        //设置设备Id
        Account.setPushId(cid);
        if(Account.isLogin()){
            //账户登录状态，进行一次PushId绑定
            //如果拿到了PushId，没有登陆的情况下不能绑定PushId
            AccountHelper.bindPush(null);
        }
    }

    /**
     * 消息送达时
     *
     * @param message 新消息
     */
    private void onMessageArrived(String message) {
        //交给Factory处理
        Factory.dispatchPush(message);
    }
}
