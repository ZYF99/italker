package com.zyf.italker.common.app;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.app.AppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;

public abstract class Activity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在界面未初始化之前调用的初始化数据
        initWindows();
        if (initArgs(getIntent().getExtras())) {
            //得到界面ID并设置到界面中
            int layId = getContentLayoutId();
            setContentView(layId);
            initWidget();
            initData();
        } else {
            finish();
        }


    }

    //初始化窗口
    protected void initWindows() {

    }

    /*初始化相关参数 参数bundle
     参数正确返回true
     错误返回false*/
    protected boolean initArgs(Bundle bundle) {
        return true;
    }

    //得到当前资源文件Id
    protected abstract int getContentLayoutId();

    //初始化控件
    protected void initWidget() {
        ButterKnife.bind(this);
    }

    //初始化数据
    protected void initData() {

    }

    @Override
    public void onBackPressed() {
        //得到当前activity下的所有Fragment
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        //判断是否为空

        if (fragments != null && fragments.size() > 0) {
            for (Fragment fragment : fragments) {
                //判断是否为我们能够处理的Fragment类型
                if (fragment instanceof com.zyf.italker.common.app.Fragment) {
                    //判断是否拦截了返回按钮
                    if (((com.zyf.italker.common.app.Fragment) fragment).onBackPressed()) {
                        //如果有直接Return
                        return;
                    }
                }
            }
        }

        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        //当点击界面导航返回时，Finish当前界面
        return super.onSupportNavigateUp();
    }
}
