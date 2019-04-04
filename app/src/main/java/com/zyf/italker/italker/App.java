package com.zyf.italker.italker;

import com.igexin.sdk.PushManager;
import com.zyf.italker.common.app.Application;
import com.zyf.italker.factory.Factory;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //调用Factory的初始化
        Factory.setup();
        //推送进行初始化
        PushManager.getInstance().initialize(this,null);
    }
}
